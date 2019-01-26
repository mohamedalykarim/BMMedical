package com.banquemisr.www.bmmedical.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.net.Uri;
import android.util.Log;

import com.banquemisr.www.bmmedical.AppExecutor;
import com.banquemisr.www.bmmedical.data.database.ApprovalsDao;
import com.banquemisr.www.bmmedical.data.database.EntityDao;
import com.banquemisr.www.bmmedical.data.database.RequestDetailsDao;
import com.banquemisr.www.bmmedical.data.database.UserDao;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.ui.requests.model.Filter;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;
import com.banquemisr.www.bmmedical.ui.login.model.User;
import com.banquemisr.www.bmmedical.ui.show_approvals.model.Approval;
import com.banquemisr.www.bmmedical.utilities.FilterUtils;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppRepository {
    private static final Object LOCK = new Object();
    private static AppRepository sInstance;
    private final AppExecutor mExecutors;
    UserDao userDao;
    EntityDao entityDao;
    RequestDetailsDao requestDetailsDao;
    ApprovalsDao approvalsDao;
    LiveData<User> user;

    NetworkDataHelper networkDataHelper;

    private AppRepository(AppExecutor appExecutor, UserDao userDao, EntityDao entityDao,
                          RequestDetailsDao requestDetailsDao, ApprovalsDao approvalsDao, NetworkDataHelper networkDataHelper){
        mExecutors = appExecutor;
        this.userDao = userDao;
        this.entityDao = entityDao;
        this.requestDetailsDao = requestDetailsDao;
        this.networkDataHelper = networkDataHelper;
        this.approvalsDao = approvalsDao;

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

        this.networkDataHelper.approvalsRequests.observeForever(approvals -> {
            appExecutor.diskIO().execute(()->{
                approvalsDao.bulkInsert(approvals);
            });

        });

    }

    public synchronized static AppRepository getsInstance(
            AppExecutor appExecutor,
            UserDao userDao,
            EntityDao entityDao,
            RequestDetailsDao requestDetailsDao,
            ApprovalsDao approvalsDao,
            NetworkDataHelper networkDataHelper){
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppRepository(appExecutor,userDao, entityDao,
                        requestDetailsDao, approvalsDao, networkDataHelper);
            }
        }
        return sInstance;
    }





    /*****************************************************************************
                                      User
    /*****************************************************************************/




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

    public DataSource.Factory<Integer,MedicalEntity> getEntitiesByFilter(String filterType, Filter filter) {
        if(filterType.equals(FilterUtils.FILTER_TYPE_1)){
            if(filter.getSpecialization() != 0){
                return entityDao.getEntitiesByFilter1and2WithSpecialization(
                        "clinic",
                        FilterUtils.getSpecializationByPosition(filter.getSpecialization())
                );
            }else {
                return entityDao.getEntitiesByFilter1and2(
                        "clinic"
                );
            }
        }else if(filterType.equals(FilterUtils.FILTER_TYPE_2)){
            if(filter.getSpecialization() != 0){
                return entityDao.getEntitiesByFilter1and2WithSpecialization(
                        "hospital",
                        FilterUtils.getSpecializationByPosition(filter.getSpecialization())
                );
            }else {
                return entityDao.getEntitiesByFilter1and2(
                        "hospital"
                );
            }
        }else if(filterType.equals(FilterUtils.FILTER_TYPE_3)){
            if(filter.getSpecialization() != 0){
                return entityDao.getEntitiesByFilter3(
                        FilterUtils.getSpecializationByPosition(filter.getSpecialization())
                );
            }else {
                return entityDao.getEntities();
            }
        }
        return null;
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

    public LiveData<List<RequestDetails>> getRequestsWithin15Days(String specialization) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -15);
        long dateBefore15 = calendar.getTimeInMillis();
        return  requestDetailsDao.getRequestsDetailsWithin15Days(dateBefore15, specialization);
    }



    public void initializeRequestsDetails(String oracle) {
        startFetchRequestDetailsService(oracle);
    }

    private void startFetchRequestDetailsService(String oracle) {
        networkDataHelper.startFetchRequestDetailsService(oracle);
    }


    public void deleteAllRequests() {
        requestDetailsDao.deleteALL();
    }

    /*****************************************************************************
                                    Approvals
     /*****************************************************************************/

    public void startAddTheApprovalRequest(Approval approval, ArrayList<Uri> uris, String oracle) {
        networkDataHelper.startaddingTheApprovalRequest(approval,uris, oracle);
    }

    public void startFetchApprovals(String oracle){
        networkDataHelper.startFetchApprovalRequestsData(oracle);
    }

    public LiveData<List<Approval>> getApprovalsRequests(String oracle, String type){
        startFetchApprovals(oracle);
       return approvalsDao.getApprovalsByType(type);
    }

    public void deleteAllApprovalRequests(){
        mExecutors.diskIO().execute(()->{
            approvalsDao.deleteALL();
        });
    }


    public LiveData<Approval> getApprovalDetails(String id) {
        return approvalsDao.getApprovalDetails(id);
    }


    public MutableLiveData<List<String>> getImagesNames(String id) {
        return networkDataHelper.getImagesNames(id);
    }
}
