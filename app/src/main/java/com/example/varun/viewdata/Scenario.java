package com.example.varun.viewdata;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class Scenario extends ListActivity {

    private Context context;
    private static String url = "https://api.myjson.com/bins/d4ba3";

    private static final String PASS = "Passed TC";
    private static final String OD = "Open Defects";
    private static final String WORK = "Workaround Taken, if any";
    private static final String PERC = "% Completion";
    private static final String PGT = "PGT";
    private static final String SCEN = "Scenario Name";
    private static final String TOTAL = "Total TC";
    private static final String CEAS = "Ceased (Y/N)";
    private static final String CLDEF = "Closed Defects";
    private static final String JUST = "Justification for Workaround";
    private static final String COMM = "Comments";
    private static final String PROP = "Proposition";
    private static final String STATUS = "Status";
    private static final String SN = "Sl. No.";
    private static final String LORDID = "Live Order ID";
    private static final String RAG = "RAG";

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    ListView lv3;

    Button defects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);
        new ProgressTask(Scenario.this).execute();

        defects = (Button) findViewById(R.id.defects);
        defects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Scenario.this, defects.class);
                startActivity(i);
            }
        });
    }

    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;
        private ListActivity activity;
        private Context context;

        public ProgressTask(ListActivity activity) {
            this.activity = activity;
            context = activity;
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
                    R.layout.scenario_list_item, new String[] {PASS, OD, WORK, PERC, PGT, SCEN,
                    TOTAL, CEAS, CLDEF, JUST, COMM, PROP, STATUS, SN, LORDID, RAG},
                    new int[] {R.id.pass, R.id.od, R.id.workaround, R.id.perc, R.id.pgt, R.id.scen,
                            R.id.total, R.id.ceas, R.id.cldef, R.id.just, R.id.comm, R.id.prop,
                            R.id.stat, R.id.sn, R.id.lordid, R.id.rag});

            setListAdapter(adapter);
            lv3 = getListView();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.getJSONFromUrl(url);

            for (int i = 0; i < json.length(); i++) {
                try {
                    JSONObject c = json.getJSONObject(i);
                    String pass = c.getString("Passed TC");
                    String od = c.getString("Open Defects");
                    String work = c.getString("Workaround Taken, if any");
                    String perc = c.getString("% Completion");
                    String pgt = c.getString("PGT");
                    String scen = c.getString("Scenario Name");
                    String total = c.getString("Total TC");
                    String ceas = c.getString("Ceased (Y/N)");
                    String cldef = c.getString("Closed Defects");
                    String just = c.getString("Justification for Workaround");
                    String comm = c.getString("Comments");
                    String prop = c.getString("Proposition");
                    String stat = c.getString("Status");
                    String sn = c.getString("Sl. No.");
                    String lordid = c.getString("Live Order ID");
                    String rag = c.getString("RAG");

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(PASS, pass);
                    map.put(OD, od);
                    map.put(WORK, work);
                    map.put(PERC, perc);
                    map.put(PGT, pgt);
                    map.put(SCEN, scen);
                    map.put(TOTAL, total);
                    map.put(CEAS, ceas);
                    map.put(CLDEF, cldef);
                    map.put(JUST, just);
                    map.put(COMM, comm);
                    map.put(PROP, prop);
                    map.put(STATUS, stat);
                    map.put(SN, sn);
                    map.put(LORDID, lordid);
                    map.put(RAG, rag);

                    jsonlist.add(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }
}


