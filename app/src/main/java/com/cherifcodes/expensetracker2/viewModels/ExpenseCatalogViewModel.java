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
    //private List<Expense> categoryExpenses = new ArrayList<>();

    private int mCurrentCatalogId;

    public ExpenseCatalogViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        mAppRepository = AppRepository.getInstance(application.getApplicationContext());
        mExpenseList = mAppRepository.getAllExpenses();
        //mAppRepository.setCurrCategoryId(catalogId);

        /*Toast.makeText(application.getApplicationContext(), "catI in viewModel = " +
            catalogId, Toast.LENGTH_LONG);*/
        //mExpenseList = mAppRepository.getAllExpenses(catalogId);
        //mExpenseList = mAppRepository.getAllExpenses();

        //mCurrentCatalogId = catalogId;

        //mExpenseList = mAppRepository.getExpensesByCategoryId(mCurrentCatalogId);
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return mExpenseList;
    }

    //Will the app crash if this static method is called in CategoryAdapter, before
    //Starting the ExpenseCatalogActivity?
    /*public static double getExpenseTotalForCatalogId(int catalogId) {
        double totalExpense = 0.0;
        List<Expense> list = mExpenseList.getValue();

        for( Expense ex: list) {
            if (ex.getCatId() == catalogId) {
                totalExpense += ex.getAmount();
            }
        }
        return totalExpense;
    }*/

    /*public void init(int currCatalogId) {
        mAppRepository = AppRepository.getInstance(mApplication.getApplicationContext());
        mCurrentCatalogId = currCatalogId;
        //mAppRepository.init(currCatalogId);
        //mExpenseList = mAppRepository.getAllExpenses(currCatalogId);
        mExpenseList = mAppRepository.getAllExpenses();
    }
*/



    /*public LiveData<List<Expense>> getExpensesByCategoryId(int currentCatId) {
        mExpenseList = mAppRepository.getExpensesByCategoryId(currentCatId);
        return mExpenseList;
    }*/

    /*public LiveData<List<Expense>> getLiveExpenseList() {
        //mExpenseList = mAppRepository.getExpensesByCategoryId(mCurrentCatalogId);
        return mExpenseList;
    }*/

   /* public void setCurrentCatalogId(int currentCatalogId) {
        mCurrentCatalogId = currentCatalogId;
    }*/
}
