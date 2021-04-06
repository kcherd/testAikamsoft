import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Search {
    private final String inputFileName;
    private final String outputFileName;

    private Connection connection;

    private final List<OutputSearch.Criteria> criteriaList = new ArrayList<>();
    private final OutputSearch outputSearch = new OutputSearch();
    List<OutputSearch.Result> results = new ArrayList<>();

    public Search(String inputFileName, String outputFileName) throws Exception {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;

        outputSearch.setType("search");

        parseJson();
        dbConnection();
        search();
        writeToFile();
        closeConn();
    }

    private void parseJson() throws Exception {
        try(FileReader reader = new FileReader(inputFileName)){
            StringBuilder stringBuilder = new StringBuilder();
            int c = reader.read();
            if (c == -1) {
                throw new IOException("Входной файл пуст");
            }
            while(c!=-1){
                stringBuilder.append((char)c);
                c=reader.read();
            }

            int criteriaCount = JsonPath.read(stringBuilder.toString(), "$.criterias.length()");

            for(int i = 0; i < criteriaCount; i++){
                HashMap<String, Object> criteriaMap = JsonPath.read(stringBuilder.toString(), "$.criterias[" + i + "]");
                String [] keys = criteriaMap.keySet().toArray(new String[0]);
                switch (keys[0]) {
                    case ("lastName") -> criteriaList.add(new OutputSearch.LastName(criteriaMap.get("lastName").toString()));
                    case ("productName") -> criteriaList.add(new OutputSearch.Product(criteriaMap.get("productName").toString(), (Integer) criteriaMap.get("minTimes")));
                    case ("minExpenses") -> criteriaList.add(new OutputSearch.PurchaseValue(criteriaMap.get("minExpenses").toString(), criteriaMap.get("maxExpenses").toString()));
                    case ("badCustomers") -> criteriaList.add(new OutputSearch.BadCustomers((Integer) criteriaMap.get("badCustomers")));
                }
            }
        }catch(IOException | PathNotFoundException ex){
            throw new Exception("Ошибка в входном файле: " + ex.getMessage());
        }
    }

    private void dbConnection() throws Exception {
        connection = DBConnection.connect();
    }

    private void search() throws Exception {
        for (OutputSearch.Criteria criteria : criteriaList) {
            if (criteria instanceof OutputSearch.LastName) {
                lastNameSearch((OutputSearch.LastName) criteria);
            } else if (criteria instanceof OutputSearch.Product) {
                productSearch((OutputSearch.Product) criteria);
            } else if (criteria instanceof OutputSearch.PurchaseValue) {
                purchaseValueSearch((OutputSearch.PurchaseValue) criteria);
            } else if (criteria instanceof OutputSearch.BadCustomers) {
                badCustomersSearch((OutputSearch.BadCustomers) criteria);
            }
        }

        outputSearch.setResults(results);
    }

    private void lastNameSearch(OutputSearch.LastName lastName) throws Exception {
        String selectSQL = "select surname, name from customer where surname = '" + lastName.getLastName() + "'";
        getData(selectSQL, lastName);
    }

    private void productSearch(OutputSearch.Product product) throws Exception {
        String selectSQL = "select customer.surname, customer.name from customer join purchases on customer.id = purchases.id_c " +
                "join goods on goods.id = purchases.id_g where goods.name = '" + product.getProductName() + "' " +
                "group by customer.name, customer.surname having count(customer.name) > " + (product.getMinTimes() - 1);
        getData(selectSQL, product);
    }

    private void purchaseValueSearch(OutputSearch.PurchaseValue purchaseValue) throws Exception {
        String selectSQL = "select customer.surname, customer.name from customer join purchases on " +
                "customer.id = purchases.id_c join goods on goods.id = purchases.id_g " +
                "group by customer.surname, customer.name having sum(goods.price) >"
                +  purchaseValue.getMinExpenses() + "and sum(goods.price) < " + purchaseValue.getMaxExpenses();
        getData(selectSQL, purchaseValue);
    }

    private void badCustomersSearch(OutputSearch.BadCustomers badCustomers) throws Exception {
        String selectSQL = "select customer.surname, customer.name, count(customer.name) from customer " +
                "join purchases on customer.id = purchases.id_c join goods on goods.id = purchases.id_g " +
                "group by customer.surname, customer.name order by count(customer.name) limit " + badCustomers.getBadCustomers();
        getData(selectSQL, badCustomers);
    }

    private void getData(String query, OutputSearch.Criteria criteria) throws Exception {
        OutputSearch.Customer customer = new OutputSearch.Customer();
        List<OutputSearch.Customer> listCustomer = new ArrayList<>();

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            while (resultSet.next()){
                customer.setLastName(resultSet.getString(1));
                customer.setFirstName(resultSet.getString(2));
                listCustomer.add(customer);
                customer = new OutputSearch.Customer();
            }

            //добавляем результаты поиска в общий список результатов
            OutputSearch.Result lastNameSearchResult = new OutputSearch.Result();
            lastNameSearchResult.setCriteria(criteria);
            lastNameSearchResult.setResults(listCustomer);
            results.add(lastNameSearchResult);
        } catch (SQLException e){
            throw new Exception("Ошибка запроса к базе данных: " + e.getMessage());
        }
    }

    private void writeToFile() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try(FileWriter writer = new FileWriter(outputFileName, false)){
            writer.write(gson.toJson(outputSearch));

            writer.flush();
        }catch(IOException ex){
            throw new Exception("Ошибка при записи данных в выходной файл: " + ex.getMessage());
        }
    }

    private void closeConn() throws Exception {
        DBConnection.closeDB(connection);
    }
}