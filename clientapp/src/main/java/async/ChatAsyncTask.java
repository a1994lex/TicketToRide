package async;

import android.os.AsyncTask;

/**
 * Created by alex on 2/26/18.
 */

public class ChatAsyncTask extends AsyncTask<String, Void, Void> {
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(String... strings) {
        return null;
    }
}
