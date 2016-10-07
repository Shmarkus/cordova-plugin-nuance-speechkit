package ee.helmes;

import android.net.Uri;
import android.util.Log;

import com.nuance.speechkit.Audio;
import com.nuance.speechkit.Language;
import com.nuance.speechkit.Session;
import com.nuance.speechkit.Transaction;
import com.nuance.speechkit.TransactionException;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

public class SpeechKit extends CordovaPlugin {
    private static final String TAG = "SpeechKit";
    private Session session;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        String uri = this.cordova.getActivity().getApplicationContext().getResources().getString("sk_url");
        String app_key = this.cordova.getActivity().getApplicationContext().getResources().getString("sk_app_key");

        this.session = Session.Factory.session(this.cordova.getActivity().getApplicationContext(), Uri.parse(uri), app_key);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("startTTS".equals(action)) {
            this.startTTS(args);
            callbackContext.success();
            return true;
        }
        return false;  // Returning false results in a "MethodNotFound" error.
    }

    private void startTTS(JSONArray args) {
        try {
            Transaction.Options options = new Transaction.Options();
            final String textToSpeak = args.getString(0);
            options.setLanguage(new Language(args.getString(1)));
            Transaction transaction = session.speakString(textToSpeak, options, new Transaction.Listener() {
                public void onAudio(Transaction transaction, Audio audio) {
                    //when text is received, it starts playback automatically
                }
                public void onSuccess(Transaction transaction, String s) {
                    Log.d(TAG, "Successful transaction: " + s);
                }
                public void onError(Transaction transaction, String s, TransactionException e) {
                    Log.e(TAG, "Error while speaking: " + s + ", Reason: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Unable to speak, Reason " + e.getMessage());
        }
    }
}
