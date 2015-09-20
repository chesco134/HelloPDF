package org.inspira.jcapiz.hellopdf;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

public class Zukam extends ActionBarActivity {

    private TextView label;
    private boolean isLauncherActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_activity);
        label = (TextView)findViewById(R.id.log_field);
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(isLauncherActive) {
                        String[] args = {"-ORBInitialPort","1050"
                        ,"-ORBInitialHost","192.168.1.77"};
                        new HelloNetworking(Zukam.this)
                                .execute(args);
                        isLauncherActive = true;
                    }
            }
        });
    }

    public void changeMainLabelMessage(String theMessage){
        label.setText(theMessage);
    }

    public void activateLauncher(){
        isLauncherActive = false;
    }
}
