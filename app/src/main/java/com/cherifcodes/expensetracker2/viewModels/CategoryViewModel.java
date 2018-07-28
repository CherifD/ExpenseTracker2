package com.cherifcodes.expensetracker2.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.cherifcodes.expensetracker2.database.AppRepository;
import com.cherifcodes.expensetracker2.database.Category;
import com.cherifcodes.expensetracker2.database.Expense;

import java.util.Date;
import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    AppRepository mAppRepository;

    private LiveData<List<Category>> mCategories;
    private LiveData<List<Expense>> mExpenses;
    private LiveData<Double> totalExpenseAfter;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance(application.getApplicationContext());
        mCategories = mAppRepository.getAllCategories();
        mExpenses = mAppRepository.getAllExpenses();
    }

    public LiveData<List<Category>> getAllCategories() {
        return mCategories;
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return mExpenses;
    }

    public void deleteAll() {
        mAppRepository.deleteAll();
    }

    public LiveData<Double> getTotalExpenseAfter(Date date) {
        totalExpenseAfter = mAppRepository.getTotalExpenseAfter(date);
        return totalExpenseAfter;
    }
}
