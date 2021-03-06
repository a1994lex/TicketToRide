package presenters;

import android.view.View;
import android.widget.Toast;

import com.example.clientapp.IBankView;
import com.example.clientapp.IGameView;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;

import async.EndTurnAsyncTask;
import states.BankState;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import async.DrawDestinationCardsAsyncTask;
import async.DrawTrainCardAsyncTask;
import states.bank.InactiveState;
import states.game.ActiveState;
import states.game.DrawCardState;

/**
 * Created by ryanm on 3/3/2018.
 */

public class BankPresenter implements Observer, IBankPresenter {

    private IBankView bankView;
    private View fragView;
    private ClientGame game;
    private static BankPresenter instance = new BankPresenter(RootClientModel.getInstance().getCurrentGame());

    private int curDeckIndex;

    private BankState state;

    private BankPresenter(ClientGame clientGame){
        this.game = clientGame;
        this.state = new InactiveState();
        game.addObserver(this);
    }

    public static BankPresenter getInstance(){
        return instance;
    }

    @Override
    public void updateBank() {

    }

    public void drawTrainCard(int position) {
        DrawTrainCardAsyncTask task = new DrawTrainCardAsyncTask();
        task.execute(position);
    }

    public void drawDestinationCards(){
        DrawDestinationCardsAsyncTask task = new DrawDestinationCardsAsyncTask();
        task.execute();
    }

    public int getCurDeckIndex(){
        return curDeckIndex;
    }

    public void callCancelFail(){
        bankView.toastPleaseFinishDraw();
    }
    // STATE FUNCTIONS /////////////////
    @Override
    public void clickTCard(int deckIndex){
        this.curDeckIndex = deckIndex;
        String color = "";
        if (deckIndex>=0){
            color = getBank().get(deckIndex).getColor();
        }
        if (color.equals(utils.LOCOMOTIVE)){
            state.chooseWild(this);
        }
        else{
            state.chooseCard(this);
        }
    }
    @Override
    public void clickDCard(){
        state.chooseDest(this);
    }

    @Override
    public void exit(){
        state.cancel(this);
    }

    public void setState(BankState state){
        this.state = state;
    }

    public GamePlayPresenter getGamePlayPresenter(){
        return GamePlayPresenter.getInstance();
    }
    ////END OF STATE FUNCTIONS////////////
    public void endTurn() {
        EndTurnAsyncTask task = new EndTurnAsyncTask();
        task.execute();
    }


    public ArrayList<TrainCard> getBank(){
        return RootClientModel.getCurrentGame().getBankCards();
    }
    public int getTDeckSize(){
        return RootClientModel.getCurrentGame().getTDeckSize();
    }

    public int getDDeckSize(){
        return RootClientModel.getCurrentGame().getDDeckSize();
    }

    @Override
    public void setView(IBankView bankView, View view)
    {
        this.bankView = bankView;
        this.fragView = view;
    }
    @Override
    public IBankView getBankView(){
        return this.bankView;
    }

    public boolean checkEndGame() {
        return RootClientModel.getCurrentGame().checkEndGame();
    }

    public void setShowRoutes(boolean showRoutes) {
        game.setShowRoutes(showRoutes);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == game){
            if (arg.equals(utils.BANK)){
                bankView.init(fragView);
            }
            else if (arg.equals(utils.DRAW_THREE_CARDS)){

                GamePlayPresenter.getInstance().callDrawDestCards();

            } else if(o.equals(utils.GAME_OVER)){
                IBankView bankView = (IBankView) this.bankView;
                setState(new InactiveState());
                bankView.endGame();
            }
            else if (arg.equals(utils.NEW_TURN)){
                if (game.isMyTurn()){
                    this.getGamePlayPresenter().setState(new DrawCardState());
                    this.setState(new states.bank.ActiveState());
                }
            }
            else if(arg.equals(utils.CARD_DRAWN)){
                String m="You drew a ";
                String n=" card!";
                int i=RootClientModel.getCurrentGame().getMyPlayer().getTrainCards().size();
                String color=RootClientModel.getCurrentGame().getMyPlayer().getTrainCards().get(i-1).getColor();
                String message=m+ color+n;
                bankView.showCardToast(message);
            }
        }
    }
}
