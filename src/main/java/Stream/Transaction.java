package Stream;

public class Transaction {
    private Trader trader;
    private int year;
    private double value;

    public Transaction(Trader trader, int year, double value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return trader;
    }

    public int getYear() {
        return year;
    }

    public double getValue() {
        return value;
    }
}
