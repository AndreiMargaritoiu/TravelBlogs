package com.example.travelblogs;

import androidx.lifecycle.ViewModel;

public class SeeTripsViewModel extends ViewModel {
    private boolean mIsSigningIn;

    public SeeTripsViewModel() {
        mIsSigningIn = false;
    }

    public boolean getIsSigningIn() {
        return mIsSigningIn;
    }

    public void setIsSigningIn(boolean mIsSigningIn) {
        this.mIsSigningIn = mIsSigningIn;
    }

}
