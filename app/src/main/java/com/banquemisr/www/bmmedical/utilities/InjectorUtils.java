package com.banquemisr.www.bmmedical.utilities;

import android.content.Context;

import com.banquemisr.www.bmmedical.AppExecutor;
import com.banquemisr.www.bmmedical.data.AppRepository;
import com.banquemisr.www.bmmedical.data.NetworkDataHelper;
import com.banquemisr.www.bmmedical.data.database.BMMedicalDatabase;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModelFactory;

public class InjectorUtils {

    public static AppRepository provideRepository(Context context){
        AppExecutor appExecutor = AppExecutor.getInstance();
        BMMedicalDatabase bmMedicalDatabase = BMMedicalDatabase.getsInstance(context);
        NetworkDataHelper networkDataHelper = NetworkDataHelper.getInstance(context);
        return AppRepository.getsInstance(appExecutor,bmMedicalDatabase.userDao(),networkDataHelper);
    }

    public static AppExecutor provideAppExecuter(){
        return AppExecutor.getInstance();
    }

    public static NetworkDataHelper provideNetworkDataHelper(Context context) {
        return NetworkDataHelper.getInstance(context.getApplicationContext());
    }

    public static LoginViewModelFactory provideLoginViewModelFactory(Context context){
        AppRepository appRepository = InjectorUtils.provideRepository(context);
        return new LoginViewModelFactory(appRepository);
    }

}
