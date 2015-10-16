package com.cmsqa.mobilekeeper;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeeperMainActivity extends Activity {

    private ListView listView;
    private List<Map<String,String>> data;
    private final static String fileName = "01.json";
    private FragmentManager fragmentManager;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.main);
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, android.R.drawable.btn_default);



        init();
        new DataThread().start();
    }

    private void init() {
//        listView = (ListView) findViewById(R.id.listView);
        data = new ArrayList<Map<String, String>>();
    }

    class DataThread extends Thread {
        public void run() {
            String jsonStr = getJson(fileName);
        }
    }

    private String getJson(String fileName) {

        StringBuilder stringBuilder = new StringBuilder();

        try {
            InputStream inputStream = getResources().getAssets().open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
            BufferedReader bf = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bf.readLine()) != null) {
                StringBuilder append = stringBuilder.append(line);
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
