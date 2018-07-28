package com.cherifcodes.expensetracker2.utils;

import com.cherifcodes.expensetracker2.database.Expense;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListFilter {

    private static double mTotalExpense;

    public static List<Expense> getExpensesByCategoryId(List<Expense> expenseList, int categoryId) {
        List<Expense> list = new ArrayList<>();
        for (Expense ex : expenseList) {
            if (ex.getCatId() == categoryId) {
                list.add(ex);
            }
        }
        return list;
    }

    public static double getCategoryTotal(List<Expense> expenseList, int categoryId) {
        double categoryTotal = 0.0;
        mTotalExpense = 0.0;
        for (Expense ex : expenseList) {
            double currAmount = ex.getAmount();
            mTotalExpense += currAmount;
            if (ex.getCatId() == categoryId) {
                categoryTotal += currAmount;
            }
        }
        return categoryTotal;
    }

    public static double getTotalExpense() {
        return mTotalExpense;
    }

    public static double getTotalExpenseForThisMonth(List<Expense> expenseList) {
        Date firstDateOfMonth = getFirstDateOfCurrentMonth();
        double totalExpenseForThisMonth = 0.0;
        for (Expense expense : expenseList) {
            if (expense.getDate().compareTo(firstDateOfMonth) > 0) {
                totalExpenseForThisMonth += expense.getAmount();
            }
        }
        return totalExpenseForThisMonth;
    }

    private static Date getFirstDateOfCurrentMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }
}
