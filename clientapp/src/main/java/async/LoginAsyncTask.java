package async;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.groupryan.client.UIFacade;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.LoginResult;


/**
 * Created by clairescout on 2/10/18.
 */

public class LoginAsyncTask extends AsyncTask<User, Void, LoginResult> {

    private Activity loginActivity;

    public LoginAsyncTask(Activity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    public LoginResult doInBackground(User... params) {
        UIFacade uiFacade = UIFacade.getInstance();
        return uiFacade.login(params[0].getUsername(), params[0].getPassword());

    }

    public void onPostExecute(LoginResult loginResult) {
        if (!loginResult.isSucceeded()) {
            Toast.makeText(loginActivity, loginResult.getUserMessage(), Toast.LENGTH_SHORT).show(); //might not need this?
        } else {
            Poller.get().poll();
        }

    }
}
