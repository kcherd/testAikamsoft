import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stat {
    private String inputFileName;
    private String outputFileName;

    private InputStat inputStat;
    private Connection connection;
    private OutputStat outputStat;

    public Stat(String inputFileName, String outputFileName) throws SQLException {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;

        collectingStatistics();
    }

    private void collectingStatistics() throws SQLException {
        parseJsonToObject();
        dbConnection();
        getDataFromDB();
        writeToFile();
    }

    private void parseJsonToObject() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try(FileReader reader = new FileReader(inputFileName)){
            inputStat = gson.fromJson(reader, InputStat.class);//создали из json файла объект класса InputStat
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void dbConnection(){
        connection = null;

        try {
            connection = DriverManager.getConnection(Project.DB_URL, Project.USER, Project.PASS);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
    }

    private void getDataFromDB() throws SQLException {
        outputStat = new OutputStat();
        outputStat.setType("stat");
        outputStat.setTotalDays((int)(inputStat.getEndDate().getTime() - inputStat.getStartDate().getTime())/86400000);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String selectSQL = "select * from stat('" + dateFormat.format(inputStat.getStartDate()) + "', '" + dateFormat.format(inputStat.getEndDate()) + "')";

        ResultSet resultSet = connection.createStatement().executeQuery(selectSQL);

        float totalCustomer = 0;
        float totalAll = 0;

        OutputStat.Customers customer = new OutputStat.Customers();
        OutputStat.Customers.Purchases purchase = new OutputStat.Customers.Purchases();
        List<OutputStat.Customers.Purchases> purchasesList = new ArrayList<>();
        List<OutputStat.Customers> customersList = new ArrayList<>();

        if(resultSet.next()){
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
        Collections.sort(customersList,  Collections.reverseOrder());
        outputStat.setCustomers(customersList);
        outputStat.setTotalExpenses(totalAll);
        outputStat.setAvgExpenses(totalAll/customersList.size());
    }

    private void writeToFile(){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try(FileWriter writer = new FileWriter(outputFileName, false)){
            writer.write(gson.toJson(outputStat));

            writer.flush();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
