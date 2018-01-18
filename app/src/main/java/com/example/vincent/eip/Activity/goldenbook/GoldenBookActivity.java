package com.example.vincent.eip.Activity.goldenbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.vincent.eip.GlobalClass;
import com.example.vincent.eip.Interfaces.GoldenBookCallback;
import com.example.vincent.eip.Network.UserClientInfo;
import com.example.vincent.eip.Network.goldenbook.GoldenBook;
import com.example.vincent.eip.Network.goldenbook.GoldenBookRetrofit;
import com.example.vincent.eip.Network.goldenbook.GoldenRetrofit;
import com.example.vincent.eip.Network.goldenbook.UserMessages;
import com.example.vincent.eip.R;

import java.util.ArrayList;
import java.util.List;


public class GoldenBookActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private GoldenRetrofit request = new GoldenRetrofit();
    private RecyclerView recyclerView;
    private RVAdapterGolden rvAdapterListRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goldenbook);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Livre d'Or");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initRecycler();
        getComments();

    }

    private void initRecycler() {
        GoldenBook test = new GoldenBook();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        rvAdapterListRequest = new RVAdapterGolden(test,GoldenBookActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(GoldenBookActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rvAdapterListRequest);
        recyclerView.setNestedScrollingEnabled(false);
    }


    private void sendMessage() {

        GlobalClass global = (GlobalClass) GoldenBookActivity.this.getApplication();
        UserClientInfo clientInfo = global.userInfos;

        UserMessages message = new UserMessages();
        message.setComment("T'es mauvais");
        message.setFirstName("Jack");
        message.setLastName("OSS117");
        message.setNumRoom("30");
        GoldenBook gold = new GoldenBook();
        gold.setUser(clientInfo);
        List<UserMessages> toto = new ArrayList<>();
        toto.add(message);
        gold.setList(toto);
        request.sendMessage(GoldenBookActivity.this, gold, new GoldenBookCallback() {
            @Override
            public void getCommentsBook(GoldenBook goldenBook) {
                getComments();
            }
        }, message);
    }

    private void updateView(GoldenBook goldenBook) {
        rvAdapterListRequest.updateList(goldenBook);
    }

    private void getComments(){
        GlobalClass global = (GlobalClass) GoldenBookActivity.this.getApplication();
        UserClientInfo clientInfo = global.userInfos;
        GoldenBookRetrofit golden = new GoldenBookRetrofit();
        golden.login(GoldenBookActivity.this, clientInfo, new GoldenBookCallback() {
            @Override
            public void getCommentsBook(GoldenBook goldenBook) {
                updateView(goldenBook);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            Log.d("exit", "edit home");
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}