package com.groupryan.client.ui;

import com.groupryan.shared.models.Game;

import java.awt.Button;
import java.util.ArrayList;
import java.util.List;

public interface IJoinGameView {
    Button mCreateGamebtn = null;
    List<Game> possibleGames = new ArrayList<>();
}
