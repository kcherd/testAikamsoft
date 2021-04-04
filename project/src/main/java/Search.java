import com.jayway.jsonpath.JsonPath;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Search {
    private String inputFileName;
    private String outputFileName;

    private List<OutputSearch.Criteria> criteriaList = new ArrayList<>();

    public Search(String inputFileName, String outputFileName){
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;

        parseJson();
    }

    private void parseJson(){
        try(FileReader reader = new FileReader(inputFileName)){
            int c;
            StringBuilder stringBuilder = new StringBuilder();
            while((c=reader.read())!=-1){
                stringBuilder.append((char)c);
            }
            System.out.println(stringBuilder.toString());
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
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
