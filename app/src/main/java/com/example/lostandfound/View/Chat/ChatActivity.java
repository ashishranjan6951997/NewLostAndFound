package com.example.lostandfound.View.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lostandfound.Controller.ChatController.ChatController;
import com.example.lostandfound.R;
import com.example.lostandfound.View.Adapter.ChatAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Calendar;
import java.util.Map;

import static android.content.Intent.ACTION_PICK;
import static com.example.lostandfound.NameClass.CHAT_ID;
import static com.example.lostandfound.NameClass.CHAT_TIME;
import static com.example.lostandfound.NameClass.DATE;
import static com.example.lostandfound.NameClass.DETAILS;
import static com.example.lostandfound.NameClass.EDIT;
import static com.example.lostandfound.NameClass.HOUR;
import static com.example.lostandfound.NameClass.IMAGE_URI;
import static com.example.lostandfound.NameClass.MINUTE;
import static com.example.lostandfound.NameClass.MONTH;
import static com.example.lostandfound.NameClass.NAME;
import static com.example.lostandfound.NameClass.TEXT;
import static com.example.lostandfound.NameClass.USERS;
import static com.example.lostandfound.NameClass.YEAR;

public class ChatActivity extends AppCompatActivity {

    ChatController controller;

    Toolbar toolbar;
    ImageView profileImageView;
    TextView nameTextView;
    RecyclerView recyclerView;
    EditText editText;
    Button sendBtn;
    Button attachmentBtn;
    String chatId;
    DatabaseReference reference;
    Uri uri;
    int hour,min,date,month,year;
    static int imageViewRequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle b = getIntent().getExtras();
        chatId = (String) b.get(CHAT_ID);
        //chatTime = (String) b.get(CHAT_TIME);
        controller = new ChatController(this,chatId);

        Map map = new HashMap();
        map.put(TEXT,"");
        controller.send(chatId,map,false);

        profileImageView = findViewById(R.id.profile_imageView);
        nameTextView = findViewById(R.id.profileName);
        attachmentBtn = findViewById(R.id.attachmentButton);

        reference = FirebaseDatabase.getInstance().getReference()
                .child(USERS)
                .child(chatId)
                .child(DETAILS)
                .child(EDIT);

        final String[] profileUri = new String[1];
        final String[] name = new String[1];

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(IMAGE_URI).exists())
                {
                    profileUri[0] = dataSnapshot.child(IMAGE_URI).getValue().toString();
                }
                name[0] = dataSnapshot.child(NAME).getValue().toString();

                if(profileUri[0]!=null) {
                    Glide.with(ChatActivity.this).load(Uri.parse(profileUri[0])).into(profileImageView);
                }
                nameTextView.setText(name[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = findViewById(R.id.recycler);
        editText = findViewById(R.id.chatText);
        sendBtn = findViewById(R.id.send);
        final String str = editText.getText().toString();
        //controller.send(chatId,str);

        toolbar = findViewById(R.id.toolbar);

        toolbar.setBackgroundColor(Color.parseColor("#43A047"));
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String chatTime = String.valueOf(System.currentTimeMillis());
                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);
                date = calendar.get(Calendar.DATE);
                month = calendar.get(Calendar.MONTH)+1;
                year = calendar.get(Calendar.YEAR);

                Map map = new HashMap();
                map.put(HOUR,hour);
                map.put(MINUTE,min);
                map.put(DATE,date);
                map.put(MONTH,month);
                map.put(YEAR,year);

                String strText = editText.getText().toString();

                map.put(TEXT,strText);
                controller.send(chatId,map,false);
                editText.setText("");

            }
        });


        attachmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ACTION_PICK);
                intent.setType("image/");
                startActivityForResult(intent, imageViewRequestCode);
            }
        });
    }

    public void bindRecyclerView(List list)
    {
        ChatAdapter chatAdapter = new ChatAdapter(this,list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        chatAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chatAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == imageViewRequestCode && resultCode == RESULT_OK && data !=null && data.getData() !=null)
        {
            uri = data.getData();
            String uriString = uri.toString();
            Map map = new HashMap();

            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            min = calendar.get(Calendar.MINUTE);
            date = calendar.get(Calendar.DATE);
            month = calendar.get(Calendar.MONTH) + 1;
            year = calendar.get(Calendar.YEAR);

            map.put(HOUR,hour);
            map.put(MINUTE,min);
            map.put(DATE,date);
            map.put(MONTH,month);
            map.put(YEAR,year);
            map.put(TEXT,uriString);
            controller.send(chatId,map,true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        controller.destroy();
    }
}