package com.banquemisr.www.bmmedical.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.banquemisr.www.bmmedical.AppExecutor;
import com.banquemisr.www.bmmedical.data.database.UserDao;
import com.banquemisr.www.bmmedical.ui.login.model.User;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

public class AppRepository {
    private static final Object LOCK = new Object();
    private static AppRepository sInstance;
    private final AppExecutor mExecutors;
    UserDao userDao;
    LiveData<User> user;

    NetworkDataHelper networkDataHelper;

    Boolean userInitialized = false;

    private AppRepository(AppExecutor appExecutor, UserDao dao, NetworkDataHelper networkDataHelper){
        mExecutors = appExecutor;
        this.userDao = dao;
        this.networkDataHelper = networkDataHelper;

        user = userDao.getUser();

        this.networkDataHelper.getUser().observeForever(newUser->{
            InjectorUtils.provideAppExecuter().diskIO().execute(()->{
                userDao.deleteALL();
                userDao.insert(newUser);
            });
        });
    }

    public synchronized static AppRepository getsInstance(AppExecutor appExecutor, UserDao userDao, NetworkDataHelper networkDataHelper){
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppRepository(appExecutor,userDao, networkDataHelper);
            }
        }
        return sInstance;
    }


    public LiveData<User> getUserByOracle(int oracle) {
        initializeUserData(oracle);
        return userDao.getUserByOracle(String.valueOf(oracle));
    }

    public LiveData<User> getUser() {
        return userDao.getUser();
    }

    private synchronized void initializeUserData(int oracle){
        InjectorUtils.provideAppExecuter().diskIO().execute(()->{
            startFetchUserService(oracle);
        });
    }

    public void startFetchUserService(int oracle){
        networkDataHelper.startFetchUserService(oracle);
    }

    public void deleteALLUsers() {
        mExecutors.diskIO().execute(()->{
            userDao.deleteALL();
        });
    }

    public void getRandomEntities() {
    }
}
