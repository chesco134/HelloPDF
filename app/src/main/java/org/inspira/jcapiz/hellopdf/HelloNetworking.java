package org.inspira.jcapiz.hellopdf;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by jcapiz on 13/09/15.
 */
public class HelloNetworking extends AsyncTask<String,String,String> {

    Activity activity;

    public HelloNetworking(Activity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... args){
        String result = null;
        //result = HelloClient.doCORBA(args);
        return result;
    }

    @Override
    protected void onPostExecute(String result){
        try{
            Zukam mActivity = (Zukam)activity;
            mActivity.changeMainLabelMessage(result);
            mActivity.activateLauncher();
        }catch(ClassCastException e){
            Toast.makeText(activity,"Error: " + e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
