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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import me.aflak.bluetooth.Bluetooth;

public class Scroll extends AppCompatActivity   {
    private Button Green;
    private SeekBar SeekBar1;
    private TextView Brightness;
    private SeekBar SeekBar2;
    private TextView HSV;
    private SeekBar SeekBar3;
    private TextView Speed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.scroll);
        Green = (Button)findViewById(R.id.button);
        Green.setEnabled(true);
        SeekBar1 = (SeekBar) findViewById(R.id.seekBar5);
        SeekBar1.setEnabled(true);
        SeekBar1.setMax(255);

        SeekBar2 = (SeekBar) findViewById(R.id.seekBar6);
        SeekBar2.setEnabled(true);
        SeekBar2.setMax(255);

        SeekBar3 = (SeekBar) findViewById(R.id.seekBar7);
        SeekBar3.setEnabled(true);
        SeekBar3.setMax(60);

        Brightness = (TextView) findViewById(R.id.TextProg);
        HSV = (TextView) findViewById(R.id.HSV);
        Speed = (TextView) findViewById(R.id.SpeedVal);


        Green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "e";
                Chat.b.send("e");
                //Display("Excercise selected: Triceps Extension");
                Brightness.setText(String.valueOf(msg));

            }
        });
        SeekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                if (fromUser) {
                    String msg = "z";
                    msg += String.valueOf(progress);
                    Chat.b.send(msg);
                    seekBar.setProgress(progress);
                    Brightness.setText(String.valueOf(progress));
                    progress = 0;
                    msg = "";
                }

            }
        });
        SeekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                if (fromUser) {
                    String msg = "x";
                    msg += String.valueOf(progress);
                    Chat.b.send(msg);
                    seekBar.setProgress(progress);
                    HSV.setText(String.valueOf(progress));
                    progress =0;
                    msg = "";
                }

            }
        });

        SeekBar3.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                if (fromUser) {
                    String msg = "y";
                    msg += String.valueOf(progress);
                    Chat.b.send(msg);
                    seekBar.setProgress(progress);
                    Speed.setText(String.valueOf(progress));
                    progress =0;
                    msg = "";
                }

            }
        });


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
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
                onBackPressed();
                return true;

            case R.id.Scroll:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
