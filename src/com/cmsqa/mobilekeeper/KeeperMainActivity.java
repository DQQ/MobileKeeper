package com.cmsqa.mobilekeeper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeeperMainActivity extends Activity {

    private ListView listView;
    private List<Map<String,String>> data;
    private final static String fileName = "D:\\coding\\MobileKeeper\\01.json";
    private ProgressDialog pg;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.main);
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,android.R.drawable.btn_default);

        init();
        pg.show();
        new DataThread().start();
    }


    private void init() {
        listView = (ListView)findViewById(R.id.listView);
        data = new ArrayList<Map<String, String>>();
        pg = new ProgressDialog(this);
        pg.setMessage("数据加载中…");
    }

    class DataThread extends Thread {
        public void run() {
            String jsonStr = getJson(fileName);
            setData(jsonStr);
            dataHandler.sendMessage(dataHandler.obtainMessage());
        }
    }

    Handler dataHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (pg != null) {
                pg.dismiss();
            }
            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                    data, R.layout.main, new String[] {
                    "board",
                    "model",
                    "release",
                    "product",
                    "mobileId",
                    "author"
            },
                    new int[] {
                    R.id.board,R.id.model,R.id.release,R.id.product,R.id.mobileId,R.id.author
            });
            listView.setAdapter(adapter);
        }
    };

    private String getJson(String fileName) {

        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    getAssets().open(fileName),"UTF-8"));
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

    private void setData(String str) {
        try {
            JSONArray array = new JSONArray(str);
            int len = array.length();
            Map<String, String> map;
            for (int i = 0; i < len; i++) {
                JSONObject object = array.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put("board", object.getString("board"));
                map.put("model", object.getString("model"));
                map.put("release", object.getString("release"));
                map.put("product", object.getString("product"));
                map.put("mobileId", object.getString("mobileId"));
                map.put("author", object.getString("author"));
                data.add(map);
                }
            } catch (JSONException e) {
            e.printStackTrace();
             }
        }

}
