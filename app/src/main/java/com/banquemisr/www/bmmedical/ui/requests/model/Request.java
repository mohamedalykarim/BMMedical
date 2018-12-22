package com.banquemisr.www.bmmedical.ui.requests.model;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;

public class Request extends BaseObservable {
    public static final int FILTER_TYPE_FORM = 1;
    public static final int FILTER_TYPE_SPECIALIST = 2;
    public static final int FILTER_TYPE_BRANCH = 3;

    MutableLiveData<String> searchText;
    MutableLiveData<Integer> filterType;

    public Request() {
        this.searchText = new MutableLiveData<>();
        this.setSearchText("");
        this.filterType = new MutableLiveData<>();
        this.filterType.setValue(0);
    }

    public MutableLiveData<String> getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText.setValue(searchText);
    }

}
