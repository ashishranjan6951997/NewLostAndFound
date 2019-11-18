package com.example.lostandfound.View.Chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lostandfound.Controller.ChatController.ChatController;
import com.example.lostandfound.R;
import com.example.lostandfound.View.Adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.lostandfound.NameClass.CHAT_ID;

public class ChatActivity extends AppCompatActivity {

    ChatController controller;

    RecyclerView recyclerView;
    EditText editText;
    Button sendBtn;
    String chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle b = getIntent().getExtras();
        chatId = (String) b.get(CHAT_ID);
        controller = new ChatController(this,chatId);

        recyclerView = findViewById(R.id.recycler);
        editText = findViewById(R.id.chatText);
        sendBtn = findViewById(R.id.send);
        final String str = editText.getText().toString();
        //controller.send(chatId,str);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strText = editText.getText().toString();
                controller.send(chatId,strText);

            }
        });

//        List list = new ArrayList();
//        ChatAdapter chatAdapter = new ChatAdapter(this,list);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        chatAdapter.notifyDataSetChanged();
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(chatAdapter);

    }

    public void bindRecyclerView(List list)
    {
        ChatAdapter chatAdapter = new ChatAdapter(this,list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        chatAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chatAdapter);
    }

}