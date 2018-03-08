package presenters;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import async.DiscardDestCardAsyncTask;

import static org.junit.Assert.*;

/**
 * Created by ryanm on 3/7/2018.
 */
public class GamePlayPresenterTest {
    @Test
    public void discardDestinationCard() throws Exception {
        List<Integer>list =new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        DiscardDestCardAsyncTask task = new DiscardDestCardAsyncTask(null);
        task.execute(list);

    }

}