package presenters;

import android.widget.Toast;

import com.example.clientapp.GameActivity;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Chat;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by alex on 3/22/18.
 */

public class phase2routetestpres {
    private RootClientModel root = RootClientModel.getInstance();
    private GameActivity gameActivity;

    public void changeTrainCards() {
        Toast.makeText(this.gameActivity, "Removing train card from current player", Toast.LENGTH_SHORT);
        List<TrainCard> trainCards = root.getCurrentGame().getMyPlayer().getTrainCards();
        trainCards.remove(trainCards.size() - 1);
        Stat stat = root.getCurrentGame().getMyPlayer().makeStat();
        stat.setTrainCards(stat.getTrainCards() - 1);
        root.getCurrentGame().updateStat(stat);
        root.getCurrentGame().getMyPlayer().setTrainCards(trainCards);
    }

    public void changeDestCards() {
        Toast.makeText(this.gameActivity, "Removing destination card from current player", Toast.LENGTH_SHORT);
        List<DestCard> destCards = root.getCurrentGame().getMyPlayer().getDestCards();
        destCards.remove(destCards.size() - 1);
        Stat stat = root.getCurrentGame().getMyPlayer().makeStat();
        stat.setDestinationCards(stat.getDestinationCards() - 1);
        root.getCurrentGame().updateStat(stat);
        root.getCurrentGame().getMyPlayer().setDestCards(destCards);
    }
    public void changeDestinationCards(String username) {
        Toast.makeText(this.gameActivity, "Yellow player now has 2 destination cards.", Toast.LENGTH_SHORT);
        Stat stat = new Stat(username, 3,2, 43, 3, 2);
        root.getCurrentGame().updateStat(stat);
        root.getCurrentGame().updateHistory("Yellow discarded 1 destination card.");
    }

    public void addChatMessage(String username, String message) {
        String playerColor = root.getCurrentGame().getPlayersColors().get(username);
        Toast.makeText(this.gameActivity, "Adding message from jimbob to chat.", Toast.LENGTH_SHORT);
        Chat myChat = new Chat(message, playerColor);
        root.getCurrentGame().updateChat(myChat);
    }
    public void claimRouteTest(String keyValue) {
        if (keyValue.equals(utils.CURRENT_PLAYER)) {
            String playerColor = root.getCurrentGame().getMyPlayer().getColor();
            String myPlayerName = root.getCurrentGame().getMyPlayer().getUsername();
            Toast.makeText(this.gameActivity, "Current player is claiming route.", Toast.LENGTH_SHORT);
            root.getCurrentGame().getMyPlayer().removeTrains(2);
            List<TrainCard> trainCards = root.getCurrentGame().getMyPlayer().getTrainCards();
            trainCards.remove(trainCards.size() - 1);
            trainCards.remove(trainCards.size() - 1);
            Stat stat = new Stat(myPlayerName, 3,2, 43, 3, 3);
            root.getCurrentGame().updateStat(stat);
            root.addClaimedRoute(myPlayerName, new Route(2, "OKLAHOMA CITY",
                    "LITTLE ROCK", 2, playerColor, 50));
            root.getCurrentGame().updateHistory("Red player claimed Oklahoma City to Little Rock.");
        }
        else {
            String playerColor = root.getCurrentGame().getPlayersColors().get(keyValue);
            switch (playerColor) {
                case utils.GREEN: {
                    HashMap<String, Stat> stats = root.getCurrentGame().getStats();
                    Stat s = stats.get(keyValue);
                    int trainCards = s.getTrainCards();
                    if (trainCards == 4) {
                        Toast.makeText(this.gameActivity, "Updating Green player cards.", Toast.LENGTH_SHORT);
                        Stat stat = new Stat(keyValue, 1, 0, 45, 6, 3);
                        root.getCurrentGame().updateStat(stat);
                        root.getCurrentGame().updateHistory("Green player now has 6 cards.");
                        break;
                    } else {
                        root.addClaimedRoute(keyValue, new Route(6, "HELENA",
                                "DULUTH", 15, utils.GREEN, 26));
                        root.addClaimedRoute(keyValue, new Route(6, "DULUTH",
                                "TORONTO", 15, utils.GREEN, 42));
                        root.addClaimedRoute(keyValue, new Route(5, "ATLANTA",
                                "MIAMI", 10, utils.GREEN, 84));
                        root.addClaimedRoute(keyValue, new Route(6, "SEATTLE",
                                "HELENA", 15, utils.GREEN, 9));
                        root.addClaimedRoute(keyValue, new Route(6, "LA",
                                "EL PASO", 15, utils.GREEN, 15));
                        root.addClaimedRoute(keyValue, new Route(6, "EL PASO",
                                "HOUSTON", 15, utils.GREEN, 38));
                        root.addClaimedRoute(keyValue, new Route(6, "NEW ORLEANS",
                                "MIAMI", 15, utils.GREEN, 83));
                        root.addClaimedRoute(keyValue, new Route(6, "CALGARY",
                                "WINNIPEG", 15, utils.BLUE, 16));
                        root.addClaimedRoute(keyValue, new Route(6, "PORTLAND",
                                "SALT LAKE CITY", 15, utils.GREEN, 10));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 87));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 40));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 65));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 22));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 8));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 45));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 59));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 27));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 3));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 4));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 11));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 12));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 33));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 36));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 1));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 91));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 2));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 92));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 5));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 6));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 19));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 20));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 31));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 32));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 44));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 93));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 46));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 94));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 49));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 95));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 51));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 96));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 52));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 97));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 47));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 48));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 60));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 61));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 62));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 63));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 73));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 74));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 81));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 82));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 85));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 98));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 90));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 99));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 75));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 76));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 71));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 72));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 69));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 100));








                        Toast.makeText(this.gameActivity, "Green player is claiming route.", Toast.LENGTH_SHORT);
                        Stat stat = new Stat(keyValue,1, 15, 39, 0, 3);
                        root.getCurrentGame().updateStat(stat);
                        root.getCurrentGame().updateHistory("Green player claimed Helena to Duluth.");
                        break;
                    }
                }
                case utils.YELLOW: {
                    Toast.makeText(this.gameActivity, "Yellow player is claiming route.", Toast.LENGTH_SHORT);
                    Stat stat = new Stat(keyValue, 2, 2, 43, 3, 3);
                    root.getCurrentGame().updateStat(stat);
                    root.addClaimedRoute(keyValue, new Route(2, "ATLANTA",
                            "CHARLESTON", 2, utils.YELLOW, 86));
                    root.getCurrentGame().updateHistory("Yellow player claimed Atlanta to Charleston.");
                    break;
                }
            }
        }
    }

}


