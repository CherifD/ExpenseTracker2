package com.cherifcodes.expensetracker2.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Category.class,
                parentColumns = "id",
                childColumns = "catId",
                onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "catId")}
)
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int id;

    //@ForeignKey(entity = Category.class, parentColumns = {"id"}, childColumns = "id",
    //onDelete = CASCADE)
    private int catId;

    private double amount;

    private String storeName;

    private Date date;

    public Expense(int id, int catId, double amount, String storeName, Date date) {
        this.id = id;
        this.catId = catId;
        this.amount = amount;
        this.storeName = storeName;
        this.date = date;
    }

    @Ignore
    public Expense(int catId, double amount, String storeName, Date date) {
        this.catId = catId;
        this.amount = amount;
        this.storeName = storeName;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", catId=" + catId +
                ", amount=" + amount +
                ", storeName='" + storeName + '\'' +
                ", date=" + date +
                '}';
    }
}
