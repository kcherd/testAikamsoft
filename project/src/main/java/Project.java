import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Project {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/shop";
    public static final String USER = "admin";
    public static final String PASS = "1234";

    public static void main(String[] args) throws ParseException, com.google.gson.JsonSyntaxException, SQLException {
        String type;
        String inputFineName;
        String outputFileName;

        if(args.length == 3){
            type = args[0];
            inputFineName = args[1];
            outputFileName = args[2];

//            String jsonString = "{\n" +
//                    "\t\"criterias\": [ \n" +
//                    "\t    {\"lastName\": \"Иванов\"},\n" +
//                    "\t\t          {\"productName\": \"Минеральная вода\", \"minTimes\": 5},\n" +
//                    "\t    {\"minExpenses\": 112, \"maxExpenses\": 4000},\n" +
//                    "\t          {\"badCustomers\": 3}\n" +
//                    "      ]\n" +
//                    "}\n";
//
//            String jsonString2 = "{\n" +
//                    "    \"startDate\": \"2020-01-14\",\n" +
//                    "    \"endDate\": \"2020-01-26\"\n" +
//                    "}";
//            Object obj = new JSONParser().parse(jsonString);
//            JSONObject jo = (JSONObject) obj;
//            JSONArray temp = (JSONArray) jo.get("criterias");
//            for (Object o : temp) {
//                JSONObject currObj = (JSONObject) o;
//            }
//            System.out.println(temp.size());
//
//
//            GsonBuilder builder = new GsonBuilder();
//            Gson gson = builder.create();
//            InputStat is = gson.fromJson(jsonString2, InputStat.class);
//
//            System.out.println();

            if (type.equals("stat")) {
                Stat stat = new Stat(inputFineName, outputFileName);
            }
        }
    }
}
