package com.cherifcodes.expensetracker2.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface ExpenseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExpense(Expense expense);

    @Update
    void updateExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

    /*@Query("SELECT * FROM Expense WHERE catId = 1 ORDER BY amount ASC")
    LiveData<List<Expense>> getAllExpenses();*/

    @Query("SELECT * FROM Expense ORDER BY amount ASC")
    LiveData<List<Expense>> getAllExpenses();

    @Query("SELECT * FROM Expense WHERE date > :date ORDER BY date ASC ")
    LiveData<List<Expense>> getExpensesAfter(Date date);

    /*@Query("SELECT * FROM Expense WHERE catId = :catId ORDER BY amount ASC")
    LiveData<List<Expense>> getAllExpenses(int catId);
*/
    /*@Query("SELECT * FROM Expense WHERE catId =:categoryId ORDER BY amount ASC")
    LiveData<List<Expense>> getExpensesByCategoryId(int categoryId);*/

    /*@Query("SELECT * FROM Expense WHERE catId = :categoryId")
    LiveData<List<Expense>> getExpensesForCategory(int categoryId);*/

    @Query("SELECT SUM(amount) FROM Expense WHERE date > :date")
    LiveData<Double> getTotalExpenseAfter(Date date);

    @Query("SELECT * FROM expense WHERE id =:currExpenseId")
    LiveData<Expense> getExpenseById(int currExpenseId);
}
