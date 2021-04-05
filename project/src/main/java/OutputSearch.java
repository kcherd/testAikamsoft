import java.util.List;

public class OutputSearch {
    private String type;
    private List<Result> results;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result{
        private Criteria criteria;
        private List<Customer> results;

        public Criteria getCriteria() {
            return criteria;
        }

        public void setCriteria(Criteria criteria) {
            this.criteria = criteria;
        }

        public List<Customer> getResults() {
            return results;
        }

        public void setResults(List<Customer> results) {
            this.results = results;
        }
    }

    public interface Criteria{ }

    public static class LastName implements Criteria{
        private String lastName;

        public LastName(String lastName) {
            this.lastName = lastName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    public static class Product implements Criteria{
        private String productName;
        private int minTimes;

        public Product(String productName, int minTimes) {
            this.productName = productName;
            this.minTimes = minTimes;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getMinTimes() {
            return minTimes;
        }

        public void setMinTimes(int minTimes) {
            this.minTimes = minTimes;
        }
    }

    public static class PurchaseValue implements Criteria{
        private String minExpenses;
        private String maxExpenses;

        public PurchaseValue(String minExpenses, String maxExpenses) {
            this.minExpenses = minExpenses;
            this.maxExpenses = maxExpenses;
        }

        public String getMinExpenses() {
            return minExpenses;
        }

        public void setMinExpenses(String minExpenses) {
            this.minExpenses = minExpenses;
        }

        public String getMaxExpenses() {
            return maxExpenses;
        }

        public void setMaxExpenses(String maxExpenses) {
            this.maxExpenses = maxExpenses;
        }
    }

    public static class BadCustomers implements Criteria{
        int badCustomers;

        public BadCustomers(int badCustomers) {
            this.badCustomers = badCustomers;
        }

        public int getBadCustomers() {
            return badCustomers;
        }

        public void setBadCustomers(int badCustomers) {
            this.badCustomers = badCustomers;
        }
    }

    public static class Customer{
        private String lastName;
        private String firstName;

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    }
}