package com.groupryan.server.models;

import com.groupryan.server.DatabaseHolder;
import com.groupryan.shared.JavaSerializer;
import com.groupryan.shared.models.Card;
import com.groupryan.shared.models.Deck;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clairescout on 4/10/18.
 */

public class ServerGameTest {

    private ServerGame serverGame;
    @Before
    public void setup(){
        ArrayList<Card> destinationCards = new ArrayList<>();
        ArrayList<Card> trainCards = new ArrayList<>();

        DestCard d = new DestCard(11, "WINNIPEG", "LITTLE ROCK", 1);

        destinationCards.add(d);
        d = new DestCard(7, "CALGARY", "SALT LAKE CITY", 2);

        destinationCards.add(d);
        d = new DestCard(10, "TORONTO", "MIAMI", 3);

        destinationCards.add(d);
        d = new DestCard(11, "DALLAS", "NEW YORK", 4);

        destinationCards.add(d);
        d = new DestCard(12, "BOSTON", "MIAMI", 5);

        destinationCards.add(d);
        d = new DestCard(21, "LOS ANGELES", "NEW YORK", 6);

        destinationCards.add(d);
        d = new DestCard(8, "HELENA", "LOS ANGELES", 7);

        destinationCards.add(d);
        d = new DestCard(13, "MONTREAL", "NEW ORLEANS", 8);

        destinationCards.add(d);
        d = new DestCard(10, "DULUTH", "EL PASO", 9);

        destinationCards.add(d);
        d = new DestCard(9, "SAULT STE. MARIE", "OKLAHOMA CITY", 10);

        destinationCards.add(d);
        d = new DestCard(17, "SAN FRANCISCO", "ATLANTA", 11);

        destinationCards.add(d);
        d = new DestCard(22, "SEATTLE", "NEW YORK", 12);

        destinationCards.add(d);
        d = new DestCard(12, "WINNIPEG", "HOUSTON", 13);

        destinationCards.add(d);
        d = new DestCard(20, "LOS ANGELES", "MIAMI", 14);

        destinationCards.add(d);
        d = new DestCard(9, "CHICAGO", "SANTA FE", 15);

        destinationCards.add(d);
        d = new DestCard(8, "DULUTH", "HOUSTON", 16);

        destinationCards.add(d);
        d = new DestCard(6, "NEW YORK", "ATLANTA", 17);

        destinationCards.add(d);
        d = new DestCard(11, "PORTLAND", "PHOENIX", 18);

        destinationCards.add(d);
        d = new DestCard(20, "VANCOUVER", "MONTREAL", 19);

        destinationCards.add(d);
        d = new DestCard(7, "CHICAGO", "NEW ORLEANS", 20);

        destinationCards.add(d);
        d = new DestCard(5, "KANSAS CITY", "HOUSTON", 21);

        destinationCards.add(d);
        d = new DestCard(13, "CALGARY", "PHOENIX", 22);

        destinationCards.add(d);
        d = new DestCard(16, "LOS ANGELES", "CHICAGO", 23);

        destinationCards.add(d);
        d = new DestCard(13, "VANCOUVER", "SANTA FE", 24);

        destinationCards.add(d);
        d = new DestCard(9, "SEATTLE", "LOS ANGELES", 25);

        destinationCards.add(d);
        d = new DestCard(9, "MONTREAL", "ATLANTA", 26);

        destinationCards.add(d);
        d = new DestCard(4, "DENVER", "EL PASO", 27);

        destinationCards.add(d);
        d = new DestCard(8, "SAULT STE. MARIE", "NASHVILLE", 28);

        destinationCards.add(d);
        d = new DestCard(11, "DENVER", "PITTSBURGH", 29);

        destinationCards.add(d);
        d = new DestCard(17, "PORTLAND", "NASHVILLE", 30);

        destinationCards.add(d);

        int id = 1;
        TrainCard t;
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.PINK, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.WHITE, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.BLUE, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.YELLOW, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.ORANGE, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.BLACK, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.RED, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.GREEN, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 14; i++) {
            t = new TrainCard(utils.LOCOMOTIVE, id);
            trainCards.add(t);
            id++;
        }
        serverGame = new ServerGame("gameid", new Deck(trainCards), new Deck(destinationCards));

        String hi = "first fake history";
        String hello = "second fake history";
        String indeed = "thirdFakeHistory";
        serverGame.addHistory(hi);
        serverGame.addHistory(hello);
        serverGame.addHistory(indeed);

        List<TrainCard> tCards = new ArrayList<>();
        tCards.add((TrainCard) serverGame.drawTrainCard());
        tCards.add((TrainCard) serverGame.drawTrainCard());
        tCards.add((TrainCard) serverGame.drawTrainCard());
        tCards.add((TrainCard) serverGame.drawTrainCard());
        Player p = new Player(utils.RED, serverGame.drawDestinationCards(), tCards, "claire", 0, false);
        p.addRoute(new Route(2, "PITTSBURGH", "NEW YORK", 2, utils.GREEN, 82));
        p.addPoints(10);
        List<TrainCard> tCards2 = new ArrayList<>();
        tCards2.add((TrainCard) serverGame.drawTrainCard());
        tCards2.add((TrainCard) serverGame.drawTrainCard());
        tCards2.add((TrainCard) serverGame.drawTrainCard());
        Player p2 = new Player(utils.BLUE, serverGame.drawDestinationCards(), tCards2, "grace", 1, false);
        p2.addRoute(new Route(2, "PITTSBURGH", "WASHINGTON", 2, "", 83));
        p2.addPoints(25);
        serverGame.addPlayer(p);
        serverGame.addPlayer(p2);
        serverGame.getStat("claire");
        serverGame.getStat("grace");
    }

    @After
    public void teardown(){

    }

    @Test
    public void testSerAndDeserializeServerGame(){
        /*byte[] stream = JavaSerializer.getInstance().serializeObject(serverCreateGameCommand);
        ServerCommand afterCommand = (ServerCommand) JavaSerializer.getInstance().toObject(stream);
        Assert.assertEquals(serverCreateGameCommand.getClass(), afterCommand.getClass());*/
        byte[] stream = JavaSerializer.getInstance().serializeObject(serverGame);
        ServerGame afterGame = (ServerGame) JavaSerializer.getInstance().toObject(stream);
        Assert.assertEquals(serverGame.getTDeckSize(), afterGame.getTDeckSize());

    }

    @Test
    public void testSerializeAndPutIntoDatabase(){
        byte[] stream = JavaSerializer.getInstance().serializeObject(serverGame);
        DatabaseHolder.getInstance().getDatabase().startTransaction();
        DatabaseHolder.getInstance().getDatabase().getGameDao().updateGameSnapshot(serverGame.getServerGameID(), stream);
        byte[] afterstream = DatabaseHolder.getInstance().getDatabase().getGameDao().getSnapshotByGameId(serverGame.getServerGameID());
        DatabaseHolder.getInstance().getDatabase().endTransaction();
        int a = 2;

    }
}
