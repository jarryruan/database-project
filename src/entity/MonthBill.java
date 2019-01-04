package entity;

import java.sql.Timestamp;

public class MonthBill implements Entity{
    private Float amount;
    private Timestamp time;
    private boolean paied;
    private String type;

    public MonthBill(Float amount, Timestamp time, boolean paied, String type) {
        this.amount = amount;
        this.time = time;
        this.paied = paied;
        this.type = type;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isPaied() {
        return paied;
    }

    public void setPaied(boolean paied) {
        this.paied = paied;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
