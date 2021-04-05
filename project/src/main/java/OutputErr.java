import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class OutputErr {
    private final String type = "error";
    private String message;

    public OutputErr(String message, String fileName){
        this.message = message;
        writeOutFile(fileName);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeOutFile(String fileName){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try(FileWriter writer = new FileWriter(fileName, false)){
            writer.write(gson.toJson(this));

            writer.flush();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
