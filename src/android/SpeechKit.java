package ee.helmes;

import android.net.Uri;
import android.util.Log;

import com.nuance.speechkit.Audio;
import com.nuance.speechkit.Language;
import com.nuance.speechkit.Session;
import com.nuance.speechkit.Transaction;
import com.nuance.speechkit.TransactionException;
import com.nuance.speechkit.Voice;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SpeechKit extends CordovaPlugin {
    private static final String TAG = "SpeechKit";
    private Session session;
    private String uri;
    private String api_key;
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("startTTS".equals(action)) {
            this.startTTS(args);
            callbackContext.success();
            return true;
        } else if ("configure".equals(action)) {
            this.configure(args);
            callbackContext.success();
            return true;
        }
        return false;  // Returning false results in a "MethodNotFound" error.
    }

    private void configure(JSONArray args) {
        try {
            this.uri = args.getString(0);
            this.api_key = args.getString(1);

            this.session = Session.Factory.session(this.cordova.getActivity().getApplicationContext(), Uri.parse(this.uri), this.api_key);
        } catch (Exception e) {
            Log.e(TAG, "Configuring error! Reason: " + e.getMessage());
        }
    }

    private void startTTS(JSONArray args) {
        Transaction.Options options = new Transaction.Options();
        options.setLanguage(new Language("ENG_USA"));
        options.setVoice(new Voice("samantha")); //optional
        try {
            final String textToSpeak = args.getString(0);
            Transaction transaction = session.speakString(textToSpeak, options, new Transaction.Listener() {
                public void onAudio(Transaction transaction, Audio audio) {
                    Log.d(TAG, "Speaking: " + textToSpeak);
                    session.getAudioPlayer().playAudio(audio);
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
