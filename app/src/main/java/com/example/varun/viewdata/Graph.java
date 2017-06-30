package com.example.varun.viewdata;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Graph extends AppCompatActivity {

    private static String url = "https://api.myjson.com/bins/1cfbgb";
    private GraphView graph;

    private static final String PGT = "PGT";
    private static final String PROG = "Progress %";

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<>();

    ListView lv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        lv1= (ListView) findViewById(R.id.graphlist);

        graph = (GraphView) findViewById(R.id.graph);
        new ProgressTask(Graph.this).execute();
    }

    private class ProgressTask  extends AsyncTask<String, Void, Boolean> {
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

            ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();

            for (int i=0; i<jsonlist.size() ;i++){
                Log.d("tatti",jsonlist.get(i).get(PROG).replace("%",""));
                dataPointArrayList.add(new DataPoint(i,Integer.parseInt(jsonlist.get(i).get(PROG).replace("%",""))));
            }
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                    dataPointArrayList.get(0),
                    dataPointArrayList.get(1),
                    dataPointArrayList.get(2),
                    dataPointArrayList.get(3),
                    dataPointArrayList.get(4),
                    dataPointArrayList.get(5),
                    dataPointArrayList.get(6)            });
            graph.addSeries(series);

            series.setSpacing(50);

// draw values on top
            series.setDrawValuesOnTop(true);

        }

        @Override
        protected Boolean doInBackground(String... params) {
            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.getJSONFromUrl(url);

            for (int i = 0; i < 7; i++) {
                try {
                    JSONObject c = json.getJSONObject(i);
                    String pgt = c.getString("PGT");
                    String prog = c.getString("Progress %");
                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(PGT, pgt);
                    map.put(PROG, prog);
                    jsonlist.add(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }
}
