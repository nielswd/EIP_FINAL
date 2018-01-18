package com.example.vincent.eip.Activity.goldenbook;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vincent.eip.Activity.messages.ListMessagesActivity;
import com.example.vincent.eip.GlobalClass;
import com.example.vincent.eip.Interfaces.GoldenBookCallback;
import com.example.vincent.eip.Interfaces.SendMessageCallback;
import com.example.vincent.eip.Network.UserClientInfo;
import com.example.vincent.eip.Network.goldenbook.GoldenBook;
import com.example.vincent.eip.Network.goldenbook.GoldenBookRetrofit;
import com.example.vincent.eip.Network.goldenbook.GoldenRetrofit;
import com.example.vincent.eip.Network.goldenbook.UserMessages;
import com.example.vincent.eip.Network.messages.ListMessages;
import com.example.vincent.eip.Network.messages.MessageRetrofit;
import com.example.vincent.eip.Network.messages.Messages;
import com.example.vincent.eip.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        initSendMessage();

    }

    private void initSendMessage() {
        FloatingActionButton newMessage = (FloatingActionButton) findViewById(R.id.new_message);
        newMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // custom dialog
                final Dialog dialog = new Dialog(GoldenBookActivity.this);
                dialog.setContentView(R.layout.custom_dialog_messages);
                //dialog.setTitle("Title...");

                // set the custom dialog components - text, image and button
                final EditText userCom = (EditText) dialog.findViewById(R.id.edit);
                final EditText userObj  = (EditText) dialog.findViewById(R.id.idObjet);
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (userObj.getText().toString().length() > 0 && userCom.getText().toString().length() > 0) {
                            String chambre = userObj.getText().toString();
                            String msg = userCom.getText().toString();
                            sendMessage(chambre, msg);
                        } else {
                            Toast.makeText(GoldenBookActivity.this, "Merci de renseigner un numéro de chambre et un message", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCANCEL);
                // if button is clicked, close the custom dialog
                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
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


    private void sendMessage(String room, String msg) {

        GlobalClass global = (GlobalClass) GoldenBookActivity.this.getApplication();
        UserClientInfo clientInfo = global.userInfos;

        UserMessages message = new UserMessages();
        message.setComment(msg);
        message.setFirstName("");
        message.setLastName("");
        message.setNumRoom(room);
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