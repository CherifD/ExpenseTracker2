package com.cherifcodes.expensetracker2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cherifcodes.expensetracker2.database.Expense;
import com.cherifcodes.expensetracker2.ui.ExpenseAdapter;
import com.cherifcodes.expensetracker2.utils.ListFilter;
import com.cherifcodes.expensetracker2.viewModels.ExpenseCatalogViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExpenseCatalogActivity extends AppCompatActivity {
    private ExpenseCatalogViewModel mViewModel;
    private List<Expense> mExpenseList = new ArrayList<>();
    private ExpenseAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private int currCatalogId;
    private Bundle mExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_catalog);

        mRecyclerView = findViewById(R.id.rv_expense_list);

        this.setTitle("Expense Catalog");
        initViewModel();
        initRecyclerView();

        mExtras = this.getIntent().getExtras();
        currCatalogId = mExtras.getInt("catalogId");

        FloatingActionButton fab = findViewById(R.id.fab_add_expense);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseCatalogActivity.this,
                        ExpenseDetailsActivity.class);
                intent.putExtra("categoryId", currCatalogId);

                startActivity(intent);
            }
        });

    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);

    }

    private void initViewModel() {

        final Observer<List<Expense>> expenseObserver =
                new Observer<List<Expense>>() {
                    @Override
                    public void onChanged(@Nullable List<Expense> expenseList) {
                        mExpenseList.clear();
                        mExpenseList.addAll(ListFilter.getExpensesByCategoryId(expenseList,
                                currCatalogId));
                        /*for(Expense ex: expenseList) {
                            if (ex.getCatId() == currCatalogId) {
                                mExpenseList.add(ex);
                            }
                        }*/

                        if (mAdapter == null) {
                           /* mAdapter = new ExpenseAdapter(expenseList,
                                    ExpenseCatalogActivity.this);*/
                            mAdapter = new ExpenseAdapter(mExpenseList,
                                    ExpenseCatalogActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                };
        /*mViewModel = ViewModelProviders.of(this,
                new ViewModelFactoryWithParameter(this.getApplication(),
                        currCatalogId)).get(ExpenseCatalogViewModel.class);
        mViewModel.init(currCatalogId);*/
        mViewModel = new ExpenseCatalogViewModel(getApplication());
        mViewModel = ViewModelProviders.of(this)
                .get(ExpenseCatalogViewModel.class); //Instantiate viewModel
        //mViewModel.init(currCatalogId);
        //mViewModel.setCurrentCatalogId(currCatalogId); //Set it's parameter
        mViewModel.getAllExpenses().observe(this, expenseObserver); //Start observation (works!)
        //mViewModel.getFilteredExpenseList();
    }
}
