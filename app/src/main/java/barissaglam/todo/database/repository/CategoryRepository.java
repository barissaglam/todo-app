package barissaglam.todo.database.repository;

import java.util.List;

import javax.inject.Inject;

import barissaglam.todo.database.dao.CategoryDao;
import barissaglam.todo.model.entities.CategoryEntity;

public class CategoryRepository {

    private CategoryDao categoryDao;

    @Inject
    public CategoryRepository(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void createNewCategory(CategoryEntity categoryEntity) {
        categoryDao.createCategory(categoryEntity);
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryDao.getAllCategories();
    }
}
