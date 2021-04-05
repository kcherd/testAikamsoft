public class Project {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/shop";
    public static final String USER = "admin";
    public static final String PASS = "1234";

    public static void main(String[] args) {
        String type;
        String inputFineName;
        String outputFileName;

        if(args.length == 3){
            type = args[0];
            inputFineName = args[1];
            outputFileName = args[2];

            try {
                if (type.equals("stat")) {
                    new Stat(inputFineName, outputFileName);
                } else if(type.equals("search")){
                    new Search(inputFineName, outputFileName);
                } else{
                    new OutputErr("Неверная операция", outputFileName);
                }
            }catch (Exception e){
                new OutputErr(e.getMessage(), outputFileName);
            }
        } else{
            new OutputErr("Неверные параметры запуска программы", "out.txt");
        }
    }
}