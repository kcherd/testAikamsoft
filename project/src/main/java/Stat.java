import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Stat {
    private String inputFileName;
    private String outputFileName;

    private InputStat inputStat;
    private Connection connection;

    public Stat(String inputFileName, String outputFileName) throws FileNotFoundException, SQLException {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;

        collectingStatistics();
    }

    private void collectingStatistics() throws FileNotFoundException, SQLException {
        parseJsonToObject();
        dbConnection();
        getDataFromDB();
    }

    private void parseJsonToObject() throws FileNotFoundException {
        FileReader fileReader = new FileReader(inputFileName);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        inputStat = gson.fromJson(fileReader, InputStat.class);//создали из json файла объект класса InputStat

        System.out.println(inputStat.getStartDate() + " " + inputStat.getEndDate());
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String selectSQL = "select stat('" + dateFormat.format(inputStat.getStartDate()) + "', '" + dateFormat.format(inputStat.getEndDate()) + "')";
        //String selectSQL = "select * from customer";
        System.out.println(selectSQL);


        ResultSet resultSet = connection.createStatement().executeQuery(selectSQL);
        while (resultSet.next()) {
            String username = resultSet.getString(1);

            System.out.println("userName : " + username);
        }
        System.out.println(resultSet);
    }
}
