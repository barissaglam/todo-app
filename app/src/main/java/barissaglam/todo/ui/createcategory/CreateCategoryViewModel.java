package barissaglam.todo.ui.createcategory;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import barissaglam.todo.database.repository.CategoryRepository;
import barissaglam.todo.model.entities.CategoryEntity;
import barissaglam.todo.ui.base.BaseViewModel;

public class CreateCategoryViewModel extends BaseViewModel {

    // Observable variables for Model Class
    public ObservableField<String> categoryName = new ObservableField<>("");
    private ObservableField<String> selectedColor = new ObservableField<>("#d32f2f");

    // For contact with UI
    private MutableLiveData<String> warningMessage;
    private MutableLiveData<Boolean> createdSuccess;

    // Observable variables for other purposes
    public ObservableInt selectedColorIndex = new ObservableInt(0); // For Orientation Change

    private CategoryRepository categoryRepository;

    @Inject
    public CreateCategoryViewModel(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    // Send request to RoomDatabase to create new TaskList
    void createNewList() {
        if (categoryName.get() == null || categoryName.get().trim().isEmpty()) {
            warningMessage.setValue("Please enter the category name");
            return;
        }

        categoryRepository.createNewCategory(new CategoryEntity(categoryName.get(), getSelectedColor().get()));
        createdSuccess.setValue(true);
    }


    // ******************** Getter functions **/
    MutableLiveData<String> getWarningMessage() {
        if (warningMessage == null)
            warningMessage = new MutableLiveData<>();
        return warningMessage;
    }

    MutableLiveData<Boolean> getCreatedSuccess() {
        if (createdSuccess == null)
            createdSuccess = new MutableLiveData<>();
        return createdSuccess;
    }

    public ObservableField<String> getSelectedColor() {
        if (selectedColor == null)
            selectedColor = new ObservableField<>("");
        return selectedColor;
    }

}
