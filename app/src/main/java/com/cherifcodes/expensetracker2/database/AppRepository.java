package com.cherifcodes.expensetracker2.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {

    private static AppRepository ourInstance;

    public LiveData<List<Category>> mCategories;
    public LiveData<List<Expense>> mExpenses;

    private CategoryDAO mCategoryDAO;
    private ExpenseDAO mExpenseDAO;

    private Executor executor = Executors.newSingleThreadExecutor();

    private AppRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        mCategoryDAO = db.categoryDAO();
        mExpenseDAO = db.expenseDAO();

        mCategories = mCategoryDAO.getAllCategories();
    }

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    public LiveData<List<Category>> getAllCategories() {
        return mCategories;
    }

    public LiveData<List<Expense>> getAllExpenses() {
        mExpenses = mExpenseDAO.getAllExpenses();
        return mExpenses;
    }

    public void insertCategory(final Category category) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mCategoryDAO.insertCategory(category);
            }
        });
    }

    public void insertExpense(final Expense expense) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mExpenseDAO.insertExpense(expense);
            }
        });
    }

    public LiveData<Category> getCategoryById(final int categoryId) {
        return mCategoryDAO.getCategoryById(categoryId);
    }

    public void updateCategory(final Category category) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mCategoryDAO.updateCategory(category);
            }
        });
    }

    public void updateExpense(final Expense expense) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mExpenseDAO.updateExpense(expense);
            }
        });
    }

    public LiveData<Expense> getExpenseById(int currExpenseId) {
        return mExpenseDAO.getExpenseById(currExpenseId);
    }

    public void deleteCategory(final Category category) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mCategoryDAO.deleteCategory(category);
            }
        });
    }

    public void deleteExpense(final Expense expense) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mExpenseDAO.deleteExpense(expense);
            }
        });
    }

    public void deleteAll() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mCategoryDAO.deleteAll();
            }
        });
    }

}
