package greek.dev.challenge.charities.views;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import greek.dev.challenge.charities.model.Charity;

public class MainViewModel extends ViewModel {
    public MutableLiveData<List<Charity>> charitiesList;

    public LiveData<List<Charity>> getCharitiesList() {
        if (charitiesList == null){
            charitiesList = new MutableLiveData<List<Charity>>();
        }
        return charitiesList;
    }
    public void addCharity(Charity charity){
         List<Charity> charityListTemp;
         charityListTemp = charitiesList.getValue();
            if (charityListTemp == null) {
                charityListTemp = new ArrayList<Charity>();
            }
        charityListTemp.add(charity);

        charitiesList.setValue(charityListTemp);
    }
}
