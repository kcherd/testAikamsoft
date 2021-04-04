import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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
//            HashMap<String, String> sizeArr = JsonPath.read(jsonString, "$.criterias[1]");
//            System.out.println(sizeArr);
//
//            try(FileReader reader = new FileReader(inputFineName)){
//                int c;
//                StringBuilder stringBuilder = new StringBuilder();
//                while((c=reader.read())!=-1){
//                    stringBuilder.append((char)c);
//                }
//                System.out.println(stringBuilder.toString());
//                int criteriaCount = JsonPath.read(stringBuilder.toString(), "$.criterias.length()");
//                System.out.println(criteriaCount);
//            }catch(IOException ex){
//                ex.printStackTrace();
//            }


//            Object obj = new JSONParser().parse(jsonString);
//            JSONObject jo = (JSONObject) obj;
//            JSONArray temp = (JSONArray) jo.get("criterias");
//            for (Object o : temp) {
//                JSONObject currObj = (JSONObject) o;
//            }
//            System.out.println(temp.size());
//
//            System.out.println();

            if (type.equals("stat")) {
                Stat stat = new Stat(inputFineName, outputFileName);
            } else if(type.equals("search")){
                Search search = new Search(inputFineName, outputFileName);
            }
        }
    }
}
