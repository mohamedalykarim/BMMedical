package com.banquemisr.www.bmmedical.data;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;

import com.banquemisr.www.bmmedical.AppExecutor;
import com.banquemisr.www.bmmedical.data.database.EntityDao;
import com.banquemisr.www.bmmedical.data.database.RequestDetailsDao;
import com.banquemisr.www.bmmedical.data.database.UserDao;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;
import com.banquemisr.www.bmmedical.ui.login.model.User;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

import java.util.List;

public class AppRepository {
    private static final Object LOCK = new Object();
    private static AppRepository sInstance;
    private final AppExecutor mExecutors;
    UserDao userDao;
    EntityDao entityDao;
    RequestDetailsDao requestDetailsDao;
    LiveData<User> user;

    NetworkDataHelper networkDataHelper;

    private AppRepository(AppExecutor appExecutor, UserDao userDao, EntityDao entityDao, RequestDetailsDao requestDetailsDao, NetworkDataHelper networkDataHelper){
        mExecutors = appExecutor;
        this.userDao = userDao;
        this.entityDao = entityDao;
        this.requestDetailsDao = requestDetailsDao;
        this.networkDataHelper = networkDataHelper;

        user = userDao.getUser();

        this.networkDataHelper.getUser().observeForever(newUser->{
            InjectorUtils.provideAppExecuter().diskIO().execute(()->{
                userDao.deleteALL();
                userDao.insert(newUser);
            });
        });

        /**
         / Insert the new Entities to the database;
         */
        this.networkDataHelper.entities.observeForever(newEntities->{
            InjectorUtils.provideAppExecuter().diskIO().execute(()->{
                entityDao.bulkInsert(newEntities);
            });

        });


        this.networkDataHelper.requests.observeForever(newRequests->{
            InjectorUtils.provideAppExecuter().diskIO().execute(()->{
                requestDetailsDao.bulkInsert(newRequests);
            });
        });


    }





    /*****************************************************************************
                                      User
    /*****************************************************************************/

    public synchronized static AppRepository getsInstance(AppExecutor appExecutor, UserDao userDao, EntityDao entityDao, RequestDetailsDao requestDetailsDao, NetworkDataHelper networkDataHelper){
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppRepository(appExecutor,userDao, entityDao, requestDetailsDao, networkDataHelper);
            }
        }
        return sInstance;
    }


    public LiveData<User> getUserByOracle(int oracle) {
        initializeUserData();
        return userDao.getUserByOracle(String.valueOf(oracle));
    }

    public LiveData<User> getUserByOracleNoIni(int oracle) {
        return userDao.getUserByOracle(String.valueOf(oracle));
    }



    public LiveData<User> getUser() {
        return userDao.getUser();
    }

    private synchronized void initializeUserData(){
        mExecutors.diskIO().execute(()->{
            startFetchUserService();
        });
    }

    public void startFetchUserService(){
        networkDataHelper.startFetchUserService();
    }


    public void deleteALLUsers() {
        mExecutors.diskIO().execute(()->{
            userDao.deleteALL();
        });
    }


    /*****************************************************************************
                                        Entity
     /*****************************************************************************/

    public DataSource.Factory<Integer, MedicalEntity> getRandomEntities() {
        initializeEntitiesData();
        return entityDao.getEntities();
    }

    public DataSource.Factory<Integer, MedicalEntity> getEntitiesBySearch(String searchText) {
        return entityDao.getEntitiesBySearch("%"+searchText+"%");
    }

    public LiveData<MedicalEntity> getEntityById(String id){
        return entityDao.getEntityByID(id);
    }



    public void initializeEntitiesData() {
        mExecutors.diskIO().execute(()->{
            startFetchEntitiesService();
        });
    }

    private void startFetchEntitiesService() {
        networkDataHelper.startFetchEntitiesService();
    }


    /*****************************************************************************
                                        Requests
     /*****************************************************************************/

    public void addTheMedicalRequest(RequestDetails requestDetails) {
        networkDataHelper.startAddTheMedicalRequest(requestDetails);
    }


    public LiveData<List<RequestDetails>> getRequests(String oracle) {
        initializeRequestsDetails(oracle);
        return requestDetailsDao.getRequestsDetails();
    }

    public LiveData<RequestDetails> getRequestById(String id) {
        return requestDetailsDao.getRequstDetailsById(id);
    }



    private void initializeRequestsDetails(String oracle) {
        mExecutors.diskIO().execute(()->{
            requestDetailsDao.deleteALL();
        });

        startFetchRequestDetailsService(oracle);
    }

    private void startFetchRequestDetailsService(String oracle) {
        networkDataHelper.startFetchRequestDetailsService(oracle);
    }

}
