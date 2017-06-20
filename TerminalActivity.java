package app.akexorcist.bluetoothspp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;
import app.akexorcist.bluetotohspp.library.BluetoothSPP.BluetoothConnectionListener;
import app.akexorcist.bluetotohspp.library.BluetoothSPP.OnDataReceivedListener;

public class TerminalActivity extends Activity {
    public static BluetoothSPP bt;

    TextView textStatus;
    EditText etMessage;

    int evalue = 0;
    public String Feet ="";
    public String inch="";
    public String dec = "";

    Menu menu;
//    testing git
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal);
        Log.i("Check", "onCreate");

//        disable keyboard popup
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM, WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//        ############################################################################################
        TextView A = (TextView)findViewById(R.id.textView5);
        A.setPaintFlags(A.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        A.setText("CB LIST");

        textStatus = (TextView)findViewById(R.id.textView7);

        bt = new BluetoothSPP(this);

        if(!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
//                textRead.append(message + "\n");
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothConnectionListener() {
            public void onDeviceDisconnected() {
                textStatus.setText("Status : Not connect");
                menu.clear();
                getMenuInflater().inflate(R.menu.menu_connection, menu);
            }

            public void onDeviceConnectionFailed() {
                textStatus.setText("Status : Connection failed");
            }

            public void onDeviceConnected(String name, String address) {
                textStatus.setText("Status : Connected to " + name);
                menu.clear();
                getMenuInflater().inflate(R.menu.menu_disconnection, menu);
            }
        });

//        ############ Handle comunications ########################################################
        final TextView histry = (TextView)findViewById(R.id.textView4);

        final EditText etext1 = (EditText)findViewById(R.id.editText3);
        final EditText etext2 = (EditText)findViewById(R.id.editText2);
        final EditText etext3 = (EditText)findViewById(R.id.editText);
        etext1.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View arg0, MotionEvent arg1){
                evalue = 1;
//                etext1.setText("");
                Log.d("Eval", String.valueOf(evalue));
                return false;
            }
        });
        etext2.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View arg0, MotionEvent arg1){
                evalue = 2;
//                etext2.setText("");
                Log.d("Eval", String.valueOf(evalue));
                return false;
            }
        });
        etext3.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View arg0, MotionEvent arg1){
                evalue = 3;
//                etext3.setText("");
                Log.d("Eval", String.valueOf(evalue));
                return false;
            }
        });

        final Button cut = (Button)findViewById(R.id.cut);
        cut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Feet = String.valueOf(etext1.getText());
                inch = String.valueOf(etext2.getText());
                dec = String.valueOf(etext3.getText());

                Log.d("Send",Feet);
                Log.d("Send",inch);
                Log.d("Send",dec);

                String app = String.valueOf(histry.getText());
                histry.setText("");

                histry.append(Feet);
                histry.append("'");
                histry.append(inch);
                histry.append("\"");
                histry.append(dec);
                histry.append("\n");
                histry.append(app);

//                bt.send("cut", true);
                bt.send(Feet, true);
                bt.send(inch, true);
                bt.send(dec, true);
            }
        });
//        cut.setOnTouchListener(new View.OnTouchListener() {
////            private Handler mHandler;
//            @Override public boolean onTouch(View v, MotionEvent event) {
//                switch(event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        bt.send("cut", true);
//                        Log.d("Cut","Cut send");
////                        cut.setBackground(R.drawable.numberb);
////                        Feet = etext1.getText().toString();
////                        Feet = etext1.getText().toString();
////                        Feet = etext1.getText().toString();
//                        break;
//                    case MotionEvent.ACTION_UP:
////                        bt.send("0", true);
//                        Log.d("tag","stop");
//                        break;
//                }
//                return false;
//            }
//        });
        final Button clear = (Button)findViewById(R.id.clear);
        clear.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                etext1.setText("");
                etext2.setText("");
                etext3.setText("");
            }
        });
//      ############################################################################################
        Button num1 = (Button)findViewById(R.id.button27);
        num1.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button","1");
                if(evalue == 1 ){
                    etext1.append("1");
//                    Log.d("Debug : ",temp);
                }
                if(evalue == 2 ){
                    etext2.append("1");
                }
                if(evalue == 3 ){
                    etext3.append("1");
                }
            }
        });
        Button num2 = (Button)findViewById(R.id.button26);
        num2.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button","2");
                if(evalue == 1 ){
                    etext1.append("2");
                }
                if(evalue == 2 ){
                    etext2.append("2");
                }
                if(evalue == 3 ){
                    etext3.append("2");
                }
            }
        });
        Button num3 = (Button)findViewById(R.id.button25);
        num3.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(evalue == 1 ){
                    etext1.append("3");
                }
                if(evalue == 2 ){
                    etext2.append("3");
                }
                if(evalue == 3 ){
                    etext3.append("3");
                }
                Log.d("Button","3");
            }
        });
        Button num4 = (Button)findViewById(R.id.button24);
        num4.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(evalue == 1 ){
                    etext1.append("4");
                }
                if(evalue == 2 ){
                    etext2.append("4");
                }
                if(evalue == 3 ){
                    etext3.append("4");
                }
            }
        });
        Button num5 = (Button)findViewById(R.id.button23);
        num5.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(evalue == 1 ){
                    etext1.append("5");
                }
                if(evalue == 2 ){
                    etext2.append("5");
                }
                if(evalue == 3 ){
                    etext3.append("5");
                }
            }
        });
        Button num6 = (Button)findViewById(R.id.button22);
        num6.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(evalue == 1 ){
                    etext1.append("6");
                }
                if(evalue == 2 ){
                    etext2.append("6");
                }
                if(evalue == 3 ){
                    etext3.append("6");
                }
            }
        });
        Button num7 = (Button)findViewById(R.id.button21);
        num7.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(evalue == 1 ){
                    etext1.append("7");
                }
                if(evalue == 2 ){
                    etext2.append("7");
                }
                if(evalue == 3 ){
                    etext3.append("7");
                }
            }
        });
        Button num8 = (Button)findViewById(R.id.button20);
        num8.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(evalue == 1 ){
                    etext1.append("8");
                }
                if(evalue == 2 ){
                    etext2.append("8");
                }
                if(evalue == 3 ){
                    etext3.append("8");
                }
            }
        });
        Button num9 = (Button)findViewById(R.id.button19);
        num9.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(evalue == 1 ){
                    etext1.append("9");
                }
                if(evalue == 2 ){
                    etext2.append("9");
                }
                if(evalue == 3 ){
                    etext3.append("9");
                }
            }
        });
        Button num0 = (Button)findViewById(R.id.button17);
        num0.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(evalue == 1 ){
                    etext1.append("0");
                }
                if(evalue == 2 ){
                    etext2.append("0");
                }
                if(evalue == 3 ){
                    etext3.append("0");
                }
            }
        });
        Button num10 = (Button)findViewById(R.id.button16);
        num10.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(evalue == 1 ){
                    etext1.append("/");
                }
                if(evalue == 2 ){
                    etext2.append("/");
                }
                if(evalue == 3 ){
                    etext3.append("/");
                }
            }
        });
    }
//############################################# background fucntions ###############################
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_connection, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if(id == R.id.menu_android_connect) {
//            bt.setDeviceTarget(BluetoothState.DEVICE_ANDROID);
//			/*
//			if(bt.getServiceState() == BluetoothState.STATE_CONNECTED)
//    			bt.disconnect();*/
//            Intent intent = new Intent(getApplicationContext(), DeviceList.class);
//            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
//        } else
//
        if(id == R.id.menu_device_connect) {
            bt.setDeviceTarget(BluetoothState.DEVICE_OTHER);
			/*
			if(bt.getServiceState() == BluetoothState.STATE_CONNECTED)
    			bt.disconnect();*/
            Intent intent = new Intent(getApplicationContext(), DeviceList.class);
            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
        } else if(id == R.id.menu_disconnect) {
            if(bt.getServiceState() == BluetoothState.STATE_CONNECTED)
                bt.disconnect();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDestroy() {
        super.onDestroy();
        bt.stopService();
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if(!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
                setup();
            }
        }
    }

    public void setup() {
//        Button btnSend = (Button)findViewById(R.id.btnSend);
//        btnSend.setOnClickListener(new OnClickListener(){
//            public void onClick(View v){
//                if(etMessage.getText().length() != 0) {
//                    bt.send(etMessage.getText().toString(), true);
//                    etMessage.setText("");
//                }
//            }
//        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
                setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
