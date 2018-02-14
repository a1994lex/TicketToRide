package async;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.clientapp.RegisterActivity;
import com.groupryan.client.UIFacade;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.LoginResult;

/**
 * Created by clairescout on 2/10/18.
 */

public class RegisterAsyncTask extends AsyncTask<User, Void, LoginResult>{

    private Activity registerActivity;

    public RegisterAsyncTask(Activity registerActivity){this.registerActivity = registerActivity;}

    @Override
    protected LoginResult doInBackground(User... params){
        UIFacade uiFacade = UIFacade.getInstance();
        return uiFacade.register(params[0].getUsername(), params[0].getPassword());
    }

    public void onPostExecute(LoginResult loginResult){
        //return string
        if(!loginResult.isSucceeded()){
            Toast.makeText(registerActivity, loginResult.getUserMessage(), Toast.LENGTH_SHORT).show(); //might not need this?
        } else {
            Poller poller = new Poller();
            poller.poll();
        }

    }

}
