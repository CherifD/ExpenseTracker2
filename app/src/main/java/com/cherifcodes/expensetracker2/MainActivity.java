package com.cherifcodes.expensetracker2;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cherifcodes.expensetracker2.database.Category;
import com.cherifcodes.expensetracker2.database.Expense;
import com.cherifcodes.expensetracker2.ui.CategoryAdapter;
import com.cherifcodes.expensetracker2.utils.ListFilter;
import com.cherifcodes.expensetracker2.viewModels.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_total_expense)
    TextView mTotalExpense;
    private CategoryViewModel mCategoryModel;
    private List<Category> categoryList = new ArrayList<>();
    private List<Expense> mExpenseList = new ArrayList<>();
    private CategoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        Button fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditCategoryActivity.class);
                startActivity(intent);
            }
        });

        initRecyclerView();
        initViewModel();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
            initViewModel();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);

    }

    public void updateTotalExpenseForThisMonth(View v) {

        mTotalExpense.setText("$" + String.format("%.2f", ListFilter.getTotalExpenseForThisMonth(
                mCategoryModel.getAllExpenses().getValue())));

    }

    private void initViewModel() {

        // Observer of full category list
        final Observer<List<Category>> categoryObserver =
                new Observer<List<Category>>() {
                    @Override
                    public void onChanged(@Nullable List<Category> categories) {
                        categoryList.clear();
                        categoryList.addAll(categories);

                        if (mAdapter == null) {
                            mAdapter = new CategoryAdapter(categoryList,
                                    MainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }

                    }
                };

        final Observer<List<Expense>> expenseObserver = new Observer<List<Expense>>() {
            @Override
            public void onChanged(@Nullable List<Expense> expenseList) {
                mExpenseList.clear();
                mExpenseList.addAll(expenseList);

                if (mAdapter != null) {
                    // Pass the Expense list to the observer
                    mAdapter.setExpenseList(expenseList);
                }
            }
        };

        // Instantiate the ViewModel
        mCategoryModel = ViewModelProviders.of(this)
                .get(CategoryViewModel.class);
        // Observe the full Category list
        mCategoryModel.getAllCategories().observe(this, categoryObserver);
        // Observe the full Expense list
        mCategoryModel.getAllExpenses().observe(this, expenseObserver);
        //mCategoryModel.getAllExpenses().observeForever(expenseObserver);
        mTotalExpense.setText("$" + String.format("%.2f", ListFilter.getTotalExpense()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete_all) {
            mCategoryModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
