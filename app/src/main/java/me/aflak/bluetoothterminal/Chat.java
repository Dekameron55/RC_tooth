package me.aflak.bluetoothterminal;
import android.os.Vibrator;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.Serializable;
import java.net.DatagramSocket;

import me.aflak.bluetooth.Bluetooth;

public class Chat extends AppCompatActivity implements Bluetooth.CommunicationCallback {
    private String name;
    public static Bluetooth b;
    private EditText message;
    private Button send;
    private TextView text;
    private TextView editText;
    private ScrollView scrollView;
    private Button RB;
    private Button Red;
    private Button Green;
    private Button Blue;

    private boolean registered=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (TextView)findViewById(R.id.editText);
        text = (TextView)findViewById(R.id.text);
        message = (EditText)findViewById(R.id.message);
        send = (Button)findViewById(R.id.send);
        RB = (Button)findViewById(R.id.RB);
        Red = (Button)findViewById(R.id.Red);
        Green = (Button)findViewById(R.id.Green);
        Blue = (Button)findViewById(R.id.Blue);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        text.setMovementMethod(new ScrollingMovementMethod());
        send.setEnabled(false);
        RB.setEnabled(false);
        Red.setEnabled(false);
        Green.setEnabled(false);
        Blue.setEnabled(false);

        b = new Bluetooth(this);
        b.enableBluetooth();
        b.setCommunicationCallback(this);

        int pos = getIntent().getExtras().getInt("pos");
        name = b.getPairedDevices().get(pos).getName();

        Display("Connecting...");
        b.connectToDevice(b.getPairedDevices().get(pos));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = message.getText().toString();
                message.setText("");
                b.send(msg);
                Display("You: "+msg);
            }
        });

        RB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "a";
                b.send(msg);
                //Display("Excercise selected: Bicep Curl");
            }
        });
        Red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "b";
                b.send(msg);
                //Display("Excercise selected: Triceps Extension");
            }
        });
        Green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "c";
                b.send(msg);
                //Display("Excercise selected: Triceps Extension");
            }
        });
        Blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "d";
                b.send(msg);
                //Display("Excercise selected: Triceps Extension");
            }
        });
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
        registered=true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(registered) {
            unregisterReceiver(mReceiver);
            registered=false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.close:
                b.removeCommunicationCallback();
                b.disconnect();
                Intent intent = new Intent(this, Select.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.Scroll:

                Intent newIntent = new Intent(getApplicationContext(), Scroll.class);
                startActivity(newIntent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void asd(final String s) {

        b.send(s);
    }
    public void Display(final String s){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.append(s + "\n");
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
    public void Display1(final String ss){

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                editText.append(ss + "\n");
                editText.setText(ss);
            }
        });
    }

    @Override
    public void onConnect(BluetoothDevice device) {
        Display("Connected to "+device.getName()+" - "+device.getAddress());
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                send.setEnabled(true);
                RB.setEnabled(true);
                Red.setEnabled(true);
                Green.setEnabled(true);
                Blue.setEnabled(true);
            }
        });
    }

    @Override
    public void onDisconnect(BluetoothDevice device, String message) {
        Display("Disconnected!");
        Display("Connecting again...");
        b.connectToDevice(device);
    }

    @Override
    public void onMessage(String message1) {
        if(message1.equals("TEST ARDUINO")||message1.equals("Fail set.")||message1.equals("Succesful set.")){
            Vibrator q = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            q.vibrate(500);
        }
        Display1(name+": "+message1);
    }

    @Override
    public void onError(String message) {
        Display("Error: "+message);
    }

    @Override
    public void onConnectError(final BluetoothDevice device, String message) {
        Display("Error: "+message);
        Display("Trying again in 3 sec.");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b.connectToDevice(device);
                    }
                }, 2000);
            }
        });
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                Intent intent1 = new Intent(Chat.this, Select.class);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        if(registered) {
                            unregisterReceiver(mReceiver);
                            registered=false;
                        }
                        startActivity(intent1);
                        finish();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        if(registered) {
                            unregisterReceiver(mReceiver);
                            registered=false;
                        }
                        startActivity(intent1);
                        finish();
                        break;
                }
            }
        }
    };
}
