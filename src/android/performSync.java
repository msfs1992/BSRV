package you.got.it.plugin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.os.Handler;
import android.widget.Toast;
import org.apache.cordova.CallbackContext;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.LogRecord;


/**
 * Created by Phoenix on 22/07/2018.
 */

public class PerformSync extends Service {
    public static Runnable r = null;
    public int c = 0;
    public Handler dataHandler = null;
    private final IBinder mBinder = new Binder();
    private class MyHandlerThread extends HandlerThread {

        Handler handler;

        public MyHandlerThread(String name, int p) {

            super(name, p);
        }

        @Override
        protected void onLooperPrepared() {
            Log.d("MyService", "Looper Prepared");
            Looper l = getLooper();
            dataHandler = new Handler(l) {
                public void handleMessage(Message msg) {
                    Log.d("MyService", "Handler function");
                    String nombre = msg.getData().getString("nombre");
                    String mensaje = msg.getData().getString("mensaje");
                    showNotification();
                   /* String myData = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), ""+myData+"",
                            Toast.LENGTH_LONG).show();*/

                }
            };
            r = new Runnable() {
                public void run() {
                    try {
                        // send to our Handler
                        Message msg = new Message();
                        Bundle b = new Bundle();
                        b.putString("nombre", "Marce");
                        b.putString("mensaje", "Hello World");
                        msg.setData(b);

                        c++;

                        dataHandler.sendMessage(msg);

                        dataHandler.postDelayed(this, 10000);
                        if(c == 4){
                            dataHandler.removeCallbacks(r);
                        }
                    } catch (Exception e) {
                        // wait 30 seconds
                        try {
                            Thread.sleep(30000);
                        } catch (InterruptedException ie) {
                            // do nothing
                        }
                        // try again

                    }


                }
            };
            dataHandler.postDelayed(r, 10000);
        }
    }

    public void showNotification(){
        BackgroundSV.enviar("Hola desde background thread");
        JSONObject j = new JSONObject();
        Notification notification = makeNotification(j);
        getNotificationManager().notify(c, notification);
    }
    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
    private Notification makeNotification() {
        return makeNotification();
    }

    private Notification makeNotification(JSONObject settings) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName("you.got.it", "you.got.it.MainActivity"));
        PendingIntent pi = PendingIntent.getActivity(this, c, intent, 0);
        Resources r = getResources();
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String title = "Title";
        String text = "asdadaasdasdadadasasdasdasd";
        int icon = org.apache.cordova.R.drawable.icon;
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
        //builder.setPriority(Notification.PRIORITY_MAX);


        return builder.build();
    }
    public PerformSync(){
        Log.d("MyService", "perform");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("MyService", "service bindeded");
        return mBinder;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w("MyService", "onStartCommand callback called");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MyHandlerThread thread = new MyHandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy callback called");
    }

}
