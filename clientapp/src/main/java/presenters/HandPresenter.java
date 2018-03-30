package presenters;

import android.view.View;

import com.example.clientapp.IHandView;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class HandPresenter implements Observer, IHandPresenter {


    private IHandView handView;
    private View fragView;
    private ClientGame game;
    private static HandPresenter instance = new HandPresenter(RootClientModel.getInstance().getCurrentGame());

    private HandPresenter(ClientGame clientGame) {
        this.game = clientGame;
        game.addObserver(this);
    }

    public static HandPresenter getInstance() {
        return instance;
    }

    @Override
    public void setView(IHandView handView, View view) {
        this.handView = handView;
        this.fragView = view;
    }

    @Override
    public boolean claimingRoute(){
        if (GamePlayPresenter.getInstance().isClaimingRoute()){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public void update(Observable o, Object arg) {
        if (o == game) {
            if (arg.equals(utils.HAND)) {
//                handView.init(fragView);
            } else if(o.equals(utils.GAME_OVER)){
                handView.endGame();
            }
        }
    }

    @Override
    public String decrementTextViewCount(String count) {
        int value = Integer.parseInt(count);
        value--;
        String returnVal = Integer.toString(value);
        return returnVal;
    }

    @Override
    public void setShowRoutes(boolean showRoutes) {
        RootClientModel.getCurrentGame().setShowRoutes(showRoutes);
    }

    @Override
    public Player getMyPlayer() {
        return RootClientModel.getCurrentGame().getMyPlayer();
    }

    @Override
    public List<DestCard> getMyDestCards() {
        return RootClientModel.getCurrentGame().getMyPlayer().getDestCards();
    }

    @Override
    public List<Integer> findTrainCardByColor(List<TrainCard> trainCards, String color, int numberOfCards) {
        List<Integer> cardIds = new ArrayList<>();
        for (TrainCard trainCard : trainCards) {
            if (color.equals(trainCard.getColor()) && numberOfCards > 0) {
                cardIds.add(trainCard.getID());
                numberOfCards--;
            }
        }
        return cardIds;
    }

    @Override
    public List<Integer> getDiscardingTrainCards(Map<String, Integer> pickedCards) {
        List<TrainCard> trainCards = RootClientModel.getCurrentGame().getMyPlayer().getTrainCards();
        List<Integer> pickedTrainCards = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : pickedCards.entrySet()) {
            if (entry.getKey().equals(utils.RED) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.RED, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.ORANGE) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.ORANGE, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.YELLOW) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.YELLOW, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.GREEN) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.GREEN, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.BLUE) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.BLUE, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.PINK) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.PINK, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.BLACK) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.BLACK, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.WHITE) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.WHITE, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.LOCOMOTIVE) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.LOCOMOTIVE, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
        }
        return pickedTrainCards;
    }
}
