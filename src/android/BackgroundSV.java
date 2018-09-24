package you.got.it.plugin;

import android.app.Activity;
import android.util.Log;
import android.os.IBinder;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import android.content.Intent;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * This class echoes a string called from JavaScript.
 */
public class BackgroundSV extends CordovaPlugin {
    PerformSync ps;
    private boolean isBind = false;
    private static CallbackContext callback;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("echo")) {
            String message = args.getString(0);
            this.echo(message, callbackContext);
            return true;
        }
        return false;
    }

    public static void enviar(String m){
        callback.success(m);
    }
    private void echo(String message, CallbackContext callbackContext) {
        callback = callbackContext;
        Activity context = cordova.getActivity();
        if (message != null && message.length() > 0) {
            Toast toast = Toast.makeText(context,
                "Bienvenido a YouGotIt",
                Toast.LENGTH_SHORT);
            toast.show();
            //if(isBind) return;
            Intent myServiceIntent = new Intent(context, PerformSync.class);
        //try {
            ////context.bindService(intent, connection, BIND_AUTO_CREATE);
            //isBind = true;
            //context.startService(myServiceIntent);
        //} catch (Exception e) {
            //Log.d("Error", ""+e+"");
        //}
            callback.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}