package com.cherifcodes.expensetracker2.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.cherifcodes.expensetracker2.database.AppRepository;
import com.cherifcodes.expensetracker2.database.Expense;

import java.util.List;

public class ExpenseCatalogViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;
    private LiveData<List<Expense>> mExpenseList;
    private MutableLiveData<List<Expense>> mFilteredExpenseList = new MutableLiveData<>();
    private Application mApplication;

    public ExpenseCatalogViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        mAppRepository = AppRepository.getInstance(application.getApplicationContext());
        mExpenseList = mAppRepository.getAllExpenses();
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return mExpenseList;
    }
}
