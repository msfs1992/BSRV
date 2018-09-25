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
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
/**
 * This class echoes a string called from JavaScript.
 */
public class BackgroundSV extends CordovaPlugin {
    //PerformSync ps;
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
    public void showNotification(){
        JSONObject j = new JSONObject();
        Notification notification = makeNotification(j);
        getNotificationManager().notify(0, notification);
    }
    private NotificationManager getNotificationManager() {
        Activity context = cordova.getActivity();
        return (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }
    private Notification makeNotification() {
        return makeNotification();
    }
    private Notification makeNotification(JSONObject settings) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName("you.got.it", "you.got.it.BackgroundSV"));
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Resources r = getResources();
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String title = "Marc";
        String text = "Holaa";
        int icon = android.R.drawable.ic_delete;
        Context context = getApplicationContext();
        Notification.Builder builder = new Notification.Builder(context);
        builder.setTicker("Ticker");
        builder.setSmallIcon(icon);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        builder.setSound(uri);
        builder.setContentIntent(pi);
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_MAX);

        return builder.build();
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
            //notification = new NotificationCompat.Builder(this);
            //notification.setAutoCancel(true);
            //createNotification();
            //showNotification();
            callback.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}