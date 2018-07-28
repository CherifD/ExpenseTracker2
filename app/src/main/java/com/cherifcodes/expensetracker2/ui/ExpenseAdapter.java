package com.cherifcodes.expensetracker2.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cherifcodes.expensetracker2.ExpenseDetailsActivity;
import com.cherifcodes.expensetracker2.R;
import com.cherifcodes.expensetracker2.database.Expense;

import java.util.List;


public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    List<Expense> mExpenseList;
    Context mContext;

    public ExpenseAdapter(List<Expense> expenseList, Context context) {
        mExpenseList = expenseList;
        mContext = context;
    }


    @NonNull
    @Override
    public ExpenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.expense_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ViewHolder holder, int position) {
        final Expense expense = mExpenseList.get(position);

        if (expense == null) {
            Toast.makeText(mContext, "null expense!!", Toast.LENGTH_SHORT);
        }

        holder.mTvStoreName.setText(expense.getStoreName());
        holder.mTvAmount.setText(String.valueOf(expense.getAmount()));

        holder.mFabEditExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                Intent intent = new Intent(mContext, ExpenseDetailsActivity.class);
                extras.putInt("categoryId", expense.getCatId());
                extras.putInt("expenseId", expense.getId());
                extras.putString("expenseStore", expense.getStoreName());
                extras.putDouble("expenseAmount", expense.getAmount());
                intent.putExtras(extras);
                //mContext.startActivity(new Intent(mContext, ExpenseDetailsActivity.class));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mExpenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvAmount;
        FloatingActionButton mFabEditExpense;
        private TextView mTvStoreName;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvStoreName = itemView.findViewById(R.id.tv_store_name);
            mTvAmount = itemView.findViewById(R.id.tv_amount);
            mFabEditExpense = itemView.findViewById(R.id.fab_edit_expense);
        }
    }
}
