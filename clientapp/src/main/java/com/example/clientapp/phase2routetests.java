package com.example.clientapp;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.groupryan.shared.utils;

import java.util.Observer;

import presenters.GamePlayPresenter;
import presenters.phase2routetestpres;

/**
 * Created by alex on 3/22/18.
 */

public class phase2routetests extends FragmentActivity {
    private phase2routetestpres gamePlayPresenter = new phase2routetestpres();
    private int mapUpdatePhase= 0;
    public void modifyRoot() {
        switch (mapUpdatePhase) {
            case 0:
                Toast.makeText(this, "Testing...adding train cards to Green player--just because",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.claimRouteTest("jimbob");
                mapUpdatePhase++;
                break;
            case 1:
                Toast.makeText(this, "Testing...other player is claiming a route",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.claimRouteTest("jimbob");
                mapUpdatePhase++;
                break;
            case 2:
                Toast.makeText(this, "Testing...current player is claiming a route",
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Also changing number of trains and train cards",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.claimRouteTest(utils.CURRENT_PLAYER);
                mapUpdatePhase++;
                break;
            case 3:
                Toast.makeText(this, "Testing...other player is claiming a route",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.claimRouteTest("joanna");
                mapUpdatePhase++;
                break;
            case 4:
                Toast.makeText(this, "Testing...joanna now has 2 destination cards",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.changeDestinationCards("joanna");
                mapUpdatePhase++;
                break;
            case 5:
                Toast.makeText(this, "Testing...adding chat message from jimbob",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.addChatMessage("jimbob", "hewwo! (jimbob)");
                mapUpdatePhase++;
                break;
            case 6:
                Toast.makeText(this, "Testing...removing train card from current player",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.changeTrainCards();
                mapUpdatePhase++;
                break;
            case 7:
                Toast.makeText(this, "Testing...removing destination card from current player",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.changeDestCards();
                mapUpdatePhase++;
                break;
        }
    }
}
