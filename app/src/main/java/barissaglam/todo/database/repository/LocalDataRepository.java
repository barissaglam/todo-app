package barissaglam.todo.database.repository;

import javax.inject.Inject;

import barissaglam.todo.database.dao.LocalDataDao;
import barissaglam.todo.model.entities.PriorityEntity;

public class LocalDataRepository {

    LocalDataDao localDataDao;

    @Inject
    public LocalDataRepository(LocalDataDao localDataDao){
        this.localDataDao = localDataDao;
    }

    public void insertPriorityData(PriorityEntity priorityEntity){
        localDataDao.insertPriority(priorityEntity);
    }

}
