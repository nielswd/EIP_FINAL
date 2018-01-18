package com.example.vincent.eip.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vincent.eip.GlobalClass;
import com.example.vincent.eip.Network.CleaningServicesListAdapter;
import com.example.vincent.eip.Interfaces.OnSectorClick;
import com.example.vincent.eip.Network.ServiceAdapter;
import com.example.vincent.eip.R;

/**
 * Created by iNfecteD on 22/02/2017.
 */

public class ServiceActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Intent intent = getIntent();
        String value = intent.getStringExtra("sector"); //if it's a string you stored.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(value);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setLogo(R.mipmap.food);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        final GlobalClass global=(GlobalClass) getApplication();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(ServiceActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (value.equals("cuisine")) {
            mAdapter = new ServiceAdapter(global.listServices, new OnSectorClick() {
                @Override
                public void OnSectorClick(String idSector) {

                }
            });
        } else if (value.equals("entretien")){
            mAdapter = new CleaningServicesListAdapter(global.listServices, new OnSectorClick() {
                @Override
                public void OnSectorClick(String idSector) {

                }
            });
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
