package com.example.clientapp;

import android.view.View;

/**
 * Created by ryanm on 3/3/2018.
 */

public interface IBankView {
    void init(View view);
    void showCardDrawn();
    void finish();
    void endGame();
    void toastPleaseFinishDraw();
    void showCardToast(String message);
}
