package org.capiz.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.inspira.jcapiz.hellopdf.R;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import static android.os.AsyncTask.*;

/**
 * Created by jcapiz on 15/09/15.
 */
public class CustomBluetoothActivity extends ActionBarActivity {

    private static final int START_SERVER_ACTION = 1;
    private static final int START_CLIENT_ACTION = 2;
    private TextView serverMode;
    private TextView clientMode;
    private TextView logZone;
    private BluetoothManager manager;
    private int backButtonCount = 1;
    private boolean serverActionInProgress = false;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void makeServerAction(BluetoothSocket cliente){
        new ServerActions(this,cliente).executeOnExecutor(THREAD_POOL_EXECUTOR);
    }

    private void launchAction(int typeAction){
        backButtonCount = 1;
        serverActionInProgress = true;
        enableModes(false);
        switch (typeAction){
            case START_SERVER_ACTION:
                if(manager.isBluetoothEnabled())
                    manager.startServerMode();
                else{
                    manager.enableBluetooth();
                }
                break;
            case START_CLIENT_ACTION:
                Intent i = new Intent(this,DevicePickerActivity.class);
                startActivityForResult(i,START_CLIENT_ACTION);
                break;
            default:
        }
    }

    private void enableModes(boolean flag){
        serverMode.setEnabled(flag);
        clientMode.setEnabled(flag);
    }

    public void logMessage(String message){
        logZone.append("\n" + message);
    }

    public void putMessage(String message1, String message2){
        logZone.append("\n" + message1 + " said: " + message2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_activity);
        serverMode = (TextView)findViewById(R.id.start_label);
        clientMode = (TextView)findViewById(R.id.client_mode);
        logZone = (TextView)findViewById(R.id.log_field);
        manager = new BluetoothManager(this);
        if( manager.getBluetoothAdapter() == null ) {
            Toast.makeText(this,"Bluetooth no disponible X.X",Toast.LENGTH_SHORT).show();
        }else{
            serverMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    launchAction(START_SERVER_ACTION);
                }
            });
            clientMode.setOnClickListener( new View.OnClickListener() {
                public void onClick(View view){
                    launchAction(START_CLIENT_ACTION);
                }
        });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("log_zone", logZone.getText().toString());
        outState.putInt("backButtonCount", backButtonCount);
        outState.putBoolean("serverActionInProgress", serverActionInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        logZone.setText(savedInstanceState.getString("log_zone"));
        backButtonCount = savedInstanceState.getInt("backButtonCount");
        serverActionInProgress = savedInstanceState.getBoolean("serverActionInProgress");
        enableModes(!serverActionInProgress);
    }

    @Override
    public void onBackPressed(){
        if(backButtonCount < 2){
            serverActionInProgress = false;
            backButtonCount++;
            manager.stopClientActions();
            manager.stopClientActions();
            enableModes(true);
            Toast.makeText(this,"Presione una vez más para salir",Toast.LENGTH_SHORT).show();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(manager != null) {
            manager.stopServerActions();
            manager.stopClientActions();
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case START_CLIENT_ACTION:
                    manager.startClientMode(manager
                                    .getBluetoothAdapter()
                                    .getRemoteDevice(data.
                                            getStringExtra("device_addr")),"Juan","Capiz");
                    break;
                case BluetoothManager.REQUEST_ENABLE_BT:
                    manager.startServerMode();
                    manager.enableDiscoverability();
                    break;
                default:
            }
        }else{
            if(requestCode == BluetoothManager.REQUEST_ENABLE_BT){
                enableModes(true);
            }
        }
    }
}
