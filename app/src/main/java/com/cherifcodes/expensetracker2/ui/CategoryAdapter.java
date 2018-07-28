package com.cherifcodes.expensetracker2.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cherifcodes.expensetracker2.EditCategoryActivity;
import com.cherifcodes.expensetracker2.ExpenseCatalogActivity;
import com.cherifcodes.expensetracker2.R;
import com.cherifcodes.expensetracker2.database.Category;
import com.cherifcodes.expensetracker2.database.Expense;
import com.cherifcodes.expensetracker2.utils.ListFilter;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> mCategoryList;
    private List<Expense> mExpenseList = new ArrayList<>();
    private Context mContext;

    public CategoryAdapter(List<Category> categories, Context context) {
        mCategoryList = categories;
        mContext = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate (initialize) the list item
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View list_item = inflater.inflate(R.layout.category_row, parent, false);

        //Pass the list item to the ViewHolder inner class
        return new ViewHolder(list_item);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        //Get the current entity.
        final Category category = mCategoryList.get(position);

        if (mExpenseList == null) {
            Toast.makeText(mContext, "Expense list is null in CategoryAdapter \nCatId = "
                            + category.getId(),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //Populate the views in the list item view (i.e. in the view holder)
        double totalExpenseForCategory = ListFilter.getCategoryTotal(mExpenseList, category.getId());
        holder.mTvTotalAmount.setText(String.format("%.2f", totalExpenseForCategory));

        holder.mTextView.setText(category.getName());

        //Listen to click events on the views in the list item view
        holder.mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditCategoryActivity.class);
                intent.putExtra("categoryId", category.getId());
                mContext.startActivity(intent);
            }
        });

        holder.mAddExpFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ExpenseCatalogActivity.class);
                intent.putExtra("catalogId", category.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.mExpenseList = expenseList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.cat_name)
        TextView mTextView;
        TextView mTvTotalAmount;
        //@BindView(R.id.fab)
        FloatingActionButton mFab, mAddExpFab;

        public ViewHolder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.cat_name);
            mTvTotalAmount = itemView.findViewById(R.id.tv_cat_total);
            mFab = itemView.findViewById(R.id.fab);
            mAddExpFab = itemView.findViewById(R.id.fab2);
        }
    }
}
