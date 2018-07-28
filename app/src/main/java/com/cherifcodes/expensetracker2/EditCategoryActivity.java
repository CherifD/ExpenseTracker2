package com.cherifcodes.expensetracker2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cherifcodes.expensetracker2.database.Category;
import com.cherifcodes.expensetracker2.viewModels.CategoryEditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditCategoryActivity extends AppCompatActivity {

    @BindView(R.id.category_text)
    TextView mTvCategory;

    TextView mTvId;

    Bundle mExtras;

    private int currCategoryId;

    private CategoryEditorViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        ButterKnife.bind(this);

        //Get the current categoryId from the intent
        mExtras = getIntent().getExtras();
        if (mExtras != null) {
            currCategoryId = mExtras.getInt("categoryId");
        }

        mTvCategory = findViewById(R.id.category_text);

        initViewModel();

    }

    private void initViewModel() {

        //Instantiate ViewModel
        mViewModel = ViewModelProviders.of(this).get(CategoryEditorViewModel.class);

        //Observe the LiveData objects
        mViewModel.getCategoryById(currCategoryId).observe(this, new Observer<Category>() {
            @Override
            public void onChanged(@Nullable Category category) {
                if (category != null) {
                    //Display the current Category's data
                    mTvCategory.setText(category.getName());

                } else {
                    Toast.makeText(EditCategoryActivity.this, "Null category",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

       /* //Instantiate ViewModel
        mViewModel = ViewModelProviders.of(this)
                .get(CategoryEditorViewModel.class);

        mViewModel.getLiveCategory().observe(this, new Observer<Category>() {
            @Override
            public void onChanged(@Nullable Category category) {
                if (category != null) {
                    //Display the current Category's data
                    mTvCategory.setText(category.getName());
                    mTvId.setText(Integer.toString(category.getId()));

                } else {
                    Toast.makeText(EditCategoryActivity.this, "Null category",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mExtras = getIntent().getExtras();
        if (mExtras == null) {
            setTitle("New Note");
        } else {
            setTitle("Edit Note");
            int currId = mExtras.getInt("note_id_key");
            //Trigger data reload and display using the current Id
            mViewModel.getCategoryById(currId); //This causes an update to the ViewModel's LiveData
            // object and a call to the above observer's onChanged method
        }*/
    }

    public void saveCategory(View v) {
        String name = mTvCategory.getText().toString().trim();
        if (!TextUtils.isEmpty(name)) {
            if (mExtras != null) {
                //int currId = mExtras.getInt("note_id_key");
                mViewModel.updateCategory(currCategoryId, name);
            } else {
                mViewModel.insertCategory(name);
            }
        } else {
            return;
        }

        finish();
    }

    public void deleteCategory(View v) {
        String name = mTvCategory.getText().toString().trim();
        if (!TextUtils.isEmpty(name)) {
            if (mExtras != null) {
                mViewModel.deleteCategory(currCategoryId, name);
            } else {
                mViewModel.insertCategory(name);
            }
        } else {
            return;
        }

        finish();
    }

    public void updateCategory(View v) {
        String name = mTvCategory.getText().toString().trim();
        if (!TextUtils.isEmpty(name)) {
            if (mExtras != null) {
                mViewModel.updateCategory(currCategoryId, name);
            } else {
                mViewModel.insertCategory(name);
            }
        } else {
            return;
        }

        finish();
    }
}
