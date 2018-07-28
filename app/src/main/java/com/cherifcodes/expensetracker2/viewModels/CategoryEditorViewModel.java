package com.cherifcodes.expensetracker2.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cherifcodes.expensetracker2.database.AppRepository;
import com.cherifcodes.expensetracker2.database.Category;

public class CategoryEditorViewModel extends AndroidViewModel {
    private MutableLiveData<Category> mLiveCategory = new MutableLiveData<>();
    private AppRepository mAppRepository;

    public CategoryEditorViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance(getApplication());
    }

    public LiveData<Category> getCategoryById(final int categoryId) {
        return mAppRepository.getCategoryById(categoryId);
    }

    public void insertCategory(String categoryName) {
        Category category = mLiveCategory.getValue();

        if (category == null) {
            if (TextUtils.isEmpty(categoryName.trim())) {
                return;
            }
            category = new Category(categoryName.trim());
        } else {
            category.setName(categoryName.trim());
        }
        mAppRepository.insertCategory(category);
    }

    public void updateCategory(int id, String name) {
        Category category = new Category(id, name);
        mAppRepository.updateCategory(category);
    }

    public void deleteCategory(int currCategoryId, String name) {
        Category category = new Category(currCategoryId, name);
        mAppRepository.deleteCategory(category);
    }
}
