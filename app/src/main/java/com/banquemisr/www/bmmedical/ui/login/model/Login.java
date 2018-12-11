package com.banquemisr.www.bmmedical.ui.login.model;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.banquemisr.www.bmmedical.BR;


public class Login extends BaseObservable {
    String email,password,oracle;
    boolean valid, loginEvent;


    private MutableLiveData<Boolean> logged, loginPressedEvent;

    public Login() {
        logged = new MutableLiveData<>();
        loginPressedEvent = new MutableLiveData<>();
    }


    @Bindable
    public String getEmail() {
        return email;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    @Bindable
    public String getOracle() {
        return oracle;
    }

    @Bindable
    public boolean isValid() {
        return valid;
    }

    @Bindable
    public boolean isLoginEvent() {
        return loginEvent;
    }


    public MutableLiveData<Boolean> isLogged() {
        return logged;
    }

    public MutableLiveData<Boolean> getLoginPressedEvent() {
        return loginPressedEvent;
    }


    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);

    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void setOracle(String oracle) {
        this.oracle = oracle;
        notifyPropertyChanged(BR.oracle);
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        notifyPropertyChanged(BR.valid);
    }

    public void setLoginEvent(boolean loginEvent) {
        this.loginEvent = loginEvent;
        notifyPropertyChanged(BR.loginEvent);
    }

    public void setLogged(Boolean isLogged) {
        this.logged.setValue(isLogged);
    }

    public void postLogged(Boolean isLogged) {
        this.logged.postValue(isLogged);
    }

    public void setLoginPressedEvent(Boolean loginPressedEvent) {
        this.loginPressedEvent.setValue(loginPressedEvent);
    }
}
