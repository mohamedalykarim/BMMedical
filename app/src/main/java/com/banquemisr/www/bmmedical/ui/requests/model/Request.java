package com.banquemisr.www.bmmedical.ui.requests.model;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;

public class Request extends BaseObservable {
    MutableLiveData<String> searchText;

    public Request() {
        this.searchText = new MutableLiveData<>();
        this.setSearchText("");
    }

    public MutableLiveData<String> getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText.setValue(searchText);
    }

}
