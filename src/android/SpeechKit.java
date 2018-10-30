package ee.codehouse;

import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.nuance.speechkit.Audio;
import com.nuance.speechkit.Language;
import com.nuance.speechkit.Recognition;
import com.nuance.speechkit.RecognizedPhrase;
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

import java.util.List;

public class SpeechKit extends CordovaPlugin {
    private static final String TAG = "SpeechKit";
    private Session session;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        String packageName = this.cordova.getActivity().getApplication().getPackageName();
        Resources resources = this.cordova.getActivity().getApplication().getResources();
        int skUrlResource = resources.getIdentifier("sk_url", "string", packageName);
        int appKeyResource = resources.getIdentifier("sk_app_key", "string", packageName);
        String uri = this.cordova.getActivity().getApplicationContext().getResources().getString(skUrlResource);
        String app_key = this.cordova.getActivity().getApplicationContext().getResources().getString(appKeyResource);

        this.session = Session.Factory.session(this.cordova.getActivity().getApplicationContext(), Uri.parse(uri), app_key);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("startTTS".equals(action)) {
            this.startTTS(args, callbackContext);
            callbackContext.success();
            return true;
        } else if ("startASR".equals(action)) {
            this.startASR(args, callbackContext);
            return true;
        } else if ("stop".equals(action)) {
            this.stop(callbackContext);
            return true;
        }
        return false;  // Returning false results in a "MethodNotFound" error.
    }

    private void startTTS(JSONArray args, final CallbackContext callbackContext) {
        try {
            Transaction.Options options = new Transaction.Options();
            final String textToSpeak = args.getString(0);
            options.setLanguage(new Language(args.getString(1)));
            options.setVoice(new Voice(args.getString(2)));
            Log.e(TAG, "Java: will speak: " + textToSpeak + " using voice: " + args.getString(2) + " and language: " + args.getString(1));
            Transaction transaction = session.speakString(textToSpeak, options, new Transaction.Listener() {
                public void onAudio(Transaction transaction, Audio audio) {
                    callbackContext.success("Playing audio");
                }
                public void onSuccess(Transaction transaction, String s) {
                    Log.d(TAG, "Successful transaction: " + s);
                }
                public void onError(Transaction transaction, String s, TransactionException e) {
                    Log.e(TAG, "Error while speaking: " + s + ", Reason: " + e.getMessage());
                    callbackContext.error(e.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Unable to speak, Reason " + e.getMessage());
        }
    }

    private void startASR(final CallbackContext callbackContext) {
        try {
            Transaction.Options options = new Transaction.Options();
            options.setLanguage(new Language(args.getString(0)));
            Transaction transaction = session.recognize(options, new Transaction.Listener() {
                public void onStartedRecording(Transaction transaction) {
                    //play audio!
                    Log.d(TAG, "Started recognizing");
                }
                public void onFinishedRecording(Transaction transaction) {
                    //play audio!
                    Log.d(TAG, "Finished recognizing");
                }
                public void onRecognition(Transaction transaction, Recognition recognition) {
                    String topRecognitionText = recognition.getText();
                    Log.d(TAG, "Most likely you said: " + topRecognitionText);
                    //Or iterate through the NBest list
                    List<RecognizedPhrase> nBest = recognition.getDetails();
                    for (RecognizedPhrase phrase : nBest) {
                        String text = phrase.getText();
                        Log.d(TAG, "Did you mean: " + text);
                    }
                    callbackContext.success(topRecognitionText);
                }
                public void onSuccess(Transaction transaction, String s) {
                    Log.d(TAG, "Successful transaction: " + s);
                }
                public void onError(Transaction transaction, String s, TransactionException e) {
                    Log.e(TAG, "Error! Reason: " + e.getMessage());
                    callbackContext.error(e.getMessage());
                }
            });
        } catch (JSONException e) {
            Log.e(TAG, "Unable to start ASR, Reason " + e.getMessage());
        }
    }

    private void stop(JSONArray args, final CallbackContext callbackContext) {
        try {
            session.getAudioPlayer().stop();
            Log.d(TAG, "Audio stopped");
            callbackContext.success("Audio stopped");
        } catch (Exception e) {
            Log.e(TAG, "Unable to stop, Reason " + e.getMessage());
        }
    }
}
