package greek.dev.challenge.charities.views;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;

import greek.dev.challenge.charities.model.Charity;

public class MainViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Charity>> charitiesList;
    //an exei eidi katebasei ta dedomena gia na min xreiazetai na ksanakatevoun
    public boolean fetched = false;
    public LiveData<ArrayList<Charity>> getCharitiesList() {
        if (charitiesList == null){
            charitiesList = new MutableLiveData<ArrayList<Charity>>();
        }
        return charitiesList;
    }
    public void addCharity(Charity charity){
         ArrayList<Charity> charityListTemp;
         charityListTemp = charitiesList.getValue();
            if (charityListTemp == null) {
                charityListTemp = new ArrayList<Charity>();
            }
        charityListTemp.add(charity);

        charitiesList.setValue(charityListTemp);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.v("viewmodel","cleard");
    }
}
