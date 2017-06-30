package com.example.varun.viewdata;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static String url = "https://api.myjson.com/bins/lv65n";

    private static final String COUNT = "Count";
    private static final String BRIDGE = "Bridge Cases";
    private ListView lv;

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    Button defects;
    Button scen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView) findViewById(R.id.list);
        new ProgressTask(MainActivity.this).execute();

        scen = (Button) findViewById(R.id.scen);
        scen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Scenario.class);
                startActivity(i);
            }
        });

        defects = (Button) findViewById(R.id.defects);
        defects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, defects.class);
                startActivity(i);
            }
        });

        defects = (Button) findViewById(R.id.graph);
        defects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Graph.class);
                startActivity(i);
            }
        });
    }

    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;
        private Context context;

        public ProgressTask(Context context) {
            this.context = context;
            dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (dialog.isShowing()){
                dialog.dismiss();
            }

            ListAdapter adapter = new SimpleAdapter(context, jsonlist,
                    R.layout.bc_list_item, new String[] {COUNT, BRIDGE},
                    new int[] {R.id.count, R.id.bridge});

            lv.setAdapter(adapter);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.getJSONFromUrl(url);

            if(json!=null){
                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject c = json.getJSONObject(i);
                        String count = c.getString("Count");
                        String bridge = c.getString("Bridge Cases");

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put(COUNT, count);
                        map.put(BRIDGE, bridge);
                        jsonlist.add(map);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            return false;
        }
    }
}
