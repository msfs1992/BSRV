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
import android.content.Intent;
import android.app.PendingIntent;
import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
/**
 * This class echoes a string called from JavaScript.
 */
public class BackgroundSV extends CordovaPlugin {
    //PerformSync ps;
    private boolean isBind = false;
    NotificationCompat.Builder notification;
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

    public void createNotification(){
        //Build the notification
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setTicker("This is the ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Here is the title");
        notification.setContentText("I am the body text of your notification");

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Builds notification and issues it
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1232, notification.build());

    }
    private void echo(String message, CallbackContext callbackContext) {
        callback = callbackContext;
        Activity context = cordova.getActivity();
        if (message != null && message.length() > 0) {
            Toast toast = Toast.makeText(context,
                "Bienvenido a YouGotIt",
                Toast.LENGTH_SHORT);
            toast.show();
            if(isBind) return;
            //Intent myServiceIntent = new Intent(context, PerformSync.class);
            try {
                ////context.bindService(intent, connection, BIND_AUTO_CREATE);
                isBind = true;
                //context.startService(myServiceIntent);
            } catch (Exception e) {
                //Log.d("Error", ""+e+"");
            }
            notification = new NotificationCompat.Builder(this);
            notification.setAutoCancel(true);
            createNotification();
            callback.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}