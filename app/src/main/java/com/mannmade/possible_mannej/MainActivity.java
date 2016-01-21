package com.mannmade.possible_mannej;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {
    String json;
    JSONParser jParser;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.ebay_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new DownloadJSONTask().execute();
    }

    //AsyncTask to download list
    private class DownloadJSONTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jParser = new JSONParser();
        }

        protected Boolean doInBackground(Void... Void) {
            try{
                //download all images into the list that holds
                //if (peopleList == null || peopleList.isEmpty()){
                    //Borrowed raw URL from github link
                    //initialize
                    json =  ConnectionManager.getInstance().getConnectionToURL("http://de-coding-test.s3.amazonaws.com/books.json");
                    //Log.v("Downloading from URL", "JSON Object = " + json);
                    //Log.v("Count of items", String.valueOf(jParser.getJSONforString(json).size()));
                //}
                return false;
            }catch(Exception e){
                e.printStackTrace();
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean isError) {
            super.onPostExecute(isError);
            System.out.println(json);
            if (!isError) {
                // specify an adapter (see also next example)
                mAdapter = new MyAdapter(jParser.getJSONforString(json));
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList<LinkedHashMap<String, String>> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            protected TextView titleText;
            protected TextView authorText;
            protected TextView ImageText;

            public ViewHolder(View v) {
                super(v);
                titleText =  (TextView) v.findViewById(R.id.item_title);
                authorText = (TextView)  v.findViewById(R.id.item_author);
                ImageText = (TextView)  v.findViewById(R.id.item_image);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<LinkedHashMap<String, String>> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ebay_list_item, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.titleText.setText(mDataset.get(position).get("title"));

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
