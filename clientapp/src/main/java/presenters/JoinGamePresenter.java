package com.groupryan.client.ui;

import com.groupryan.client.ClientFacade;
import com.groupryan.client.UIFacade;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Color;

import java.util.Observable;
import java.util.Observer;

public class JoinGamePresenter implements Observer {
    RootClientModel root;
    IJoinGameView gameView;
    private static JoinGamePresenter instance = new JoinGamePresenter(RootClientModel.getSingle_instance());
    private UIFacade uifacade = UIFacade.getInstance();

    private JoinGamePresenter(RootClientModel root){
        this.root = root;
        root.addObserver(this);
    }
    public static void setView(IJoinGameView view){
        instance._setView(view);
    }
    public static void joinGame(){
        instance._joinGame();
    }
    private void _createGame(String title, int numPlayers, Color color){
        uifacade.createGame(color, title, numPlayers);
    }

    private void _joinGame(){

    }

    private static void _setView(IJoinGameView view){
        if (instance.gameView == null){
            instance.gameView = view;
        }
    }

    public static void createGame(String title, int numPlayers, Color color){
        instance._createGame(title, numPlayers, color);
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
