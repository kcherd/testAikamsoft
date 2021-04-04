import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OutputStat {
    private String type;
    private int totalDays;
    private List<Customers> customers;
    private float totalExpenses;
    private float avgExpenses;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public List<Customers> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customers> customers) {
        this.customers = customers;
    }

    public float getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(float totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public float getAvgExpenses() {
        return avgExpenses;
    }

    public void setAvgExpenses(float avgExpenses) {
        this.avgExpenses = avgExpenses;
    }

    public static class Customers implements Comparable<Customers>{
        private String name;
        private List<Purchases> purchases;
        private float totalExpenses;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Purchases> getPurchases() {
            return purchases;
        }

        public void setPurchases(List<Purchases> purchases) {
            this.purchases = purchases;
        }

        public float getTotalExpenses() {
            return totalExpenses;
        }

        public void setTotalExpenses(float totalExpenses) {
            this.totalExpenses = totalExpenses;
        }

        @Override
        public int compareTo(@NotNull OutputStat.Customers o) {
            return (int) (this.totalExpenses - o.totalExpenses);
        }

        public static class Purchases{
            private String name;
            private float expenses;

            public void setName(String name) {
                this.name = name;
            }

            public void setExpenses(float expenses) {
                this.expenses = expenses;
            }

            public String getName() {
                return name;
            }

            public float getExpenses() {
                return expenses;
            }
        }
    }
}
