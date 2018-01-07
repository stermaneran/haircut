package com.example.user.eran;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Sefi on 07/01/2018.
 */

public class Analytics {
    private FirebaseAnalytics mFirebaseAnalytics;

    protected static void logEventSignUp(Context context){
        Bundle signup = new Bundle();
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.SIGN_UP,signup);
    }

}
