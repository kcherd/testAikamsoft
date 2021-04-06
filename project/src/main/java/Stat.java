import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stat {
    private final String inputFileName;
    private final String outputFileName;

    private InputStat inputStat;
    private Connection connection;
    private OutputStat outputStat;

    public Stat(String inputFileName, String outputFileName) throws Exception {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;

        parseJsonToObject();
        dbConnection();
        getDataFromDB();
        writeToFile();
        closeConn();
    }

    private void parseJsonToObject() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try(FileReader reader = new FileReader(inputFileName)){
            inputStat = gson.fromJson(reader, InputStat.class);//создали из json файла объект класса InputStat
            if(inputStat.getStartDate() == null || inputStat.getEndDate() == null){
                throw new Exception("Данные входного файла не соответствуют формату");
            }
        }catch(IOException ex){
            throw new Exception("Ошибка в входном файле: " + ex.getMessage());
        }
    }

    private void dbConnection() throws Exception {
        connection = DBConnection.connect();
    }

    private void getDataFromDB() throws Exception {
        outputStat = new OutputStat();
        outputStat.setType("stat");
        outputStat.setTotalDays((int)(inputStat.getEndDate().getTime() - inputStat.getStartDate().getTime())/86400000);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String selectSQL = "select * from stat('" + dateFormat.format(inputStat.getStartDate()) + "', '" + dateFormat.format(inputStat.getEndDate()) + "')";

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(selectSQL);

            float totalCustomer = 0;
            float totalAll = 0;

            OutputStat.Customers customer = new OutputStat.Customers();
            OutputStat.Customers.Purchases purchase = new OutputStat.Customers.Purchases();
            List<OutputStat.Customers.Purchases> purchasesList = new ArrayList<>();
            List<OutputStat.Customers> customersList = new ArrayList<>();

            if (resultSet.next()) {
                customer.setName(resultSet.getString(1) + " " + resultSet.getString(2));
                purchase.setName(resultSet.getString(3));
                purchase.setExpenses(Float.parseFloat(resultSet.getString(4)));
                purchasesList.add(purchase);
                totalCustomer += purchase.getExpenses();
                purchase = new OutputStat.Customers.Purchases();
            }

            while (resultSet.next()) {
                String userSurname = resultSet.getString(1);
                String userName = resultSet.getString(2);
                String surnameName = userSurname + " " + userName;

                if (!surnameName.equals(customer.getName())) {
                    customer.setPurchases(purchasesList);
                    customer.setTotalExpenses(totalCustomer);
                    customersList.add(customer);
                    totalAll += totalCustomer;
                    totalCustomer = 0;
                    purchasesList = new ArrayList<>();

                    customer = new OutputStat.Customers();
                    customer.setName(surnameName);
                }
                purchase.setName(resultSet.getString(3));
                purchase.setExpenses(Float.parseFloat(resultSet.getString(4)));
                purchasesList.add(purchase);
                totalCustomer += purchase.getExpenses();
                purchase = new OutputStat.Customers.Purchases();
            }

            //для последнего покупателя
            customer.setPurchases(purchasesList);
            customer.setTotalExpenses(totalCustomer);
            customersList.add(customer);
            totalAll += totalCustomer;

            //сортируем и записываем в объект outputStat
            Collections.sort(customersList, Collections.reverseOrder());
            outputStat.setCustomers(customersList);
            outputStat.setTotalExpenses(totalAll);
            outputStat.setAvgExpenses(totalAll / customersList.size());
        } catch (SQLException e){
            throw new Exception("Ошибка запроса к базе данных: " + e.getMessage());
        }
    }

    private void writeToFile() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try(FileWriter writer = new FileWriter(outputFileName, false)){
            writer.write(gson.toJson(outputStat));

            writer.flush();
        }catch(IOException ex){
            throw new Exception("Ошибка при записи данных в выходной файл: " + ex.getMessage());
        }
    }

    private void closeConn() throws Exception {
        DBConnection.closeDB(connection);
    }
}