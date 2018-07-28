package com.cherifcodes.expensetracker2.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cherifcodes.expensetracker2.database.AppRepository;
import com.cherifcodes.expensetracker2.database.Expense;

import java.util.Date;

public class ExpenseDetailsViewModel extends AndroidViewModel {

    AppRepository mAppRepository;

    MutableLiveData<Expense> mLiveExpenses = new MutableLiveData<>();

    public ExpenseDetailsViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<Expense> getLiveExpense() {
        return mLiveExpenses;
    }

    public void insertExpense(int catId, double amount, String storeName) {
        Expense expense = mLiveExpenses.getValue(); //Is this necessary??

        if (TextUtils.isEmpty(storeName.trim()) || amount <= 0.0 || catId < 0)
            return;

        if (expense == null) {
            expense = new Expense(catId, amount, storeName, new Date());
        } else {
            expense.setAmount(amount);
            expense.setCatId(catId);
            expense.setStoreName(storeName);
        }

        mAppRepository.insertExpense(expense);
    }

    public void getExpenseById(int currExpenseId) {
        mLiveExpenses.postValue(mAppRepository.getExpenseById(currExpenseId).getValue());
    }

    public void updateExpense(int expenseId, int categoryId, double amount, String storeName) {
        if (TextUtils.isEmpty(storeName.trim()) || amount <= 0.0 || categoryId < 0
                || expenseId < 0)
            return;

        Expense expense = new Expense(expenseId, categoryId, amount, storeName, new Date());
        mAppRepository.updateExpense(expense);

    }

    public void deleteExpense(int expenseId, int categoryId, double amount, String storeName) {
        if (TextUtils.isEmpty(storeName.trim()) || amount <= 0.0 || categoryId < 0
                || expenseId < 0)
            return;

        Expense expense = new Expense(expenseId, categoryId, amount, storeName, new Date());
        mAppRepository.deleteExpense(expense);
    }
}
