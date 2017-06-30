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

public class defects extends ListActivity {

    private Context context;
    private static String url = "https://api.myjson.com/bins/9s2kr";

    private static final String WORK = "Workaround Taken, if any";
    private static final String PROP = "Proposition";
    private static final String SN = "Sl. No";
    private static final String PGT = "PGT";
    private static final String DESC = "Description";
    private static final String COMM = "Comments";
    private static final String COMP = "Component";
    private static final String RCA = "RCA";
    private static final String DID = "Defect Id";
    private static final String JUST = "Justification for Workaround";
    private static final String STATUS = "Status";

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    ListView lv2;

    Button graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defects);
        new ProgressTask(defects.this).execute();

        graph = (Button) findViewById(R.id.graph);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(defects.this, Graph.class);
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
                    R.layout.defects_list_item, new String[] {WORK, PROP, SN, PGT,
                    DESC, COMM, COMP, RCA, DID, JUST, STATUS},
                    new int[] {R.id.workaround, R.id.prop, R.id.sn, R.id.pgt,
                    R.id.desc, R.id.comm, R.id.comp, R.id.rca, R.id.did, R.id.just, R.id.stat});

            setListAdapter(adapter);
            lv2 = getListView();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.getJSONFromUrl(url);

            for (int i = 0; i < json.length(); i++) {
                try {
                    JSONObject c = json.getJSONObject(i);
                    String work = c.getString("Workaround Taken, if any");
                    String prop = c.getString("Proposition");
                    String sn = c.getString("Sl. No");
                    String pgt = c.getString("PGT");
                    String desc = c.getString("Description");
                    String comm = c.getString("Comments");
                    String comp = c.getString("Component");
                    String rca = c.getString("RCA");
                    String did = c.getString("Defect Id");
                    String just = c.getString("Justification for Workaround");
                    String stat = c.getString("Status");

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(WORK, work);
                    map.put(PROP, prop);
                    map.put(SN, sn);
                    map.put(PGT, pgt);
                    map.put(DESC, desc);
                    map.put(COMM, comm);
                    map.put(COMP, comp);
                    map.put(RCA, rca);
                    map.put(DID, did);
                    map.put(JUST, just);
                    map.put(STATUS, stat);
                    jsonlist.add(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }
}

