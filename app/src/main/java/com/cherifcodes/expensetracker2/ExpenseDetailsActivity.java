package com.cherifcodes.expensetracker2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cherifcodes.expensetracker2.database.Expense;
import com.cherifcodes.expensetracker2.viewModels.ExpenseDetailsViewModel;

public class ExpenseDetailsActivity extends AppCompatActivity {
    EditText mEtStoreName;
    EditText mEtAmount;

    private Bundle mExtras;

    private ExpenseDetailsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_details);

        this.setTitle("Expense Details");

        mEtStoreName = findViewById(R.id.et_edit_store_name);
        mEtAmount = findViewById(R.id.et_edit_amount);

        mExtras = this.getIntent().getExtras();

        initViewModel();

    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(ExpenseDetailsViewModel.class);

        mViewModel.getLiveExpense().observe(this, new Observer<Expense>() {


            @Override
            public void onChanged(@Nullable Expense expense) {
                if (expense != null) {
                    //Display the current Entity's data
                    mEtAmount.setText(String.valueOf(expense.getAmount()));
                    mEtStoreName.setText(expense.getStoreName());

                }
            }
        });

        mExtras = getIntent().getExtras();
        if (mExtras == null) {
            setTitle("New Expense");
        } else {
            setTitle("Edit Expense");
            int currExpenseId = mExtras.getInt("expenseId");
            mEtAmount.setText(String.valueOf(mExtras.getDouble("expenseAmount")));
            mEtStoreName.setText(mExtras.getString("expenseStore"));
            //Trigger data reload and display using the current Id

            mViewModel.getExpenseById(currExpenseId); //This causes an update to the ViewModel's
            // LiveData object and then a call to the above observer's onChanged method
        }
    }

    public void saveExpense(View vi) {
        int categoryId = mExtras.getInt("categoryId");

        String amountString = mEtAmount.getText().toString().trim();
        String storeName = mEtStoreName.getText().toString().trim();
        if (TextUtils.isEmpty(amountString) || TextUtils.isEmpty(storeName))
            return;

        double amount = Double.parseDouble(amountString);

        mViewModel.insertExpense(categoryId, amount, storeName);

        finish();
    }

    public void updateExpense(View v) {
        int categoryId = mExtras.getInt("categoryId");

        Toast.makeText(this, "categoryId is: " + categoryId, Toast.LENGTH_SHORT).show();
        int expenseId = mExtras.getInt("expenseId");
        String amountString = mEtAmount.getText().toString().trim();
        String storeName = mEtStoreName.getText().toString().trim();
        if (TextUtils.isEmpty(amountString) || TextUtils.isEmpty(storeName))
            return;

        double amount = Double.parseDouble(amountString);

        mViewModel.updateExpense(expenseId, categoryId, amount, storeName);

        finish();
    }


    public void deleteExpense(View v) {
        //Get the current Category and Expense ids from the previous activity
        int categoryId = mExtras.getInt("categoryId");
        int expenseId = mExtras.getInt("expenseId");

        String amountString = mEtAmount.getText().toString().trim();
        String storeName = mEtStoreName.getText().toString().trim();

        // validate expense data
        if (TextUtils.isEmpty(amountString) || TextUtils.isEmpty(storeName)) {
            Toast.makeText(this, "Invalid expense data.", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountString);

        mViewModel.deleteExpense(expenseId, categoryId, amount, storeName);

        finish();
    }

}
