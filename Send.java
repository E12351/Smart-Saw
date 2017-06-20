package app.akexorcist.bluetoothspp;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class Send  extends Service  {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"service is created",Toast.LENGTH_LONG).show();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"service is started",Toast.LENGTH_LONG).show();

        while(true){

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    TerminalActivity.bt.send(Main.signal,true);
                }
            }, 5000);

        }

//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        try {
            //client.disconnect(0);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Something went wrong!" + e.getMessage(), Toast.LENGTH_LONG).show();
            //e.printStackTrace();
            System.out.println(e);
        }
        super.onDestroy();
        Toast.makeText(this,"service is destroyed",Toast.LENGTH_LONG).show();
    }

}
