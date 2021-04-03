import java.util.ArrayList;

public class InputSearch {
    private ArrayList<Criteria> criterias;

    public ArrayList<Criteria> getCriterias() {
        return criterias;
    }

    public class Criteria{

    }

    public class Surname extends Criteria{
        private String lastName;

        public Surname(String lastName) {
            this.lastName = lastName;
        }

        public String getLastName() {
            return lastName;
        }
    }

    public class Product extends Criteria{
        private String productName;
        private int minTimes;

        public Product(String productName, int minTimes){
            this.productName = productName;
            this.minTimes = minTimes;
        }

        public String getProductName() {
            return productName;
        }

        public int getMinTimes() {
            return minTimes;
        }
    }

    public class Costs extends Criteria{
        private float minExpenses;
        private float maxExpenses;

        public Costs(float minExpenses, float maxExpenses){
            this.maxExpenses = maxExpenses;
            this.minExpenses = minExpenses;
        }

        public float getMaxExpenses() {
            return maxExpenses;
        }

        public float getMinExpenses() {
            return minExpenses;
        }
    }

    public class BadCustomers extends Criteria{
        private int badCustomers;

        public BadCustomers(int badCustomers){
            this.badCustomers = badCustomers;
        }

        public int getBadCustomers() {
            return badCustomers;
        }
    }
}

