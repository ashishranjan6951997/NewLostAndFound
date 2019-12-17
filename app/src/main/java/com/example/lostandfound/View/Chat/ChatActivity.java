package com.example.lostandfound.View.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

import static com.example.lostandfound.NameClass.CHAT_ID;
import static com.example.lostandfound.NameClass.DETAILS;
import static com.example.lostandfound.NameClass.EDIT;
import static com.example.lostandfound.NameClass.IMAGE_URI;
import static com.example.lostandfound.NameClass.NAME;
import static com.example.lostandfound.NameClass.USERS;

public class ChatActivity extends AppCompatActivity {

    ChatController controller;
    Toolbar toolbar;
    ImageView profileImageView;
    TextView nameTextView;
    RecyclerView recyclerView;
    EditText editText;
    Button sendBtn;
    String chatId;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle b = getIntent().getExtras();
        chatId = (String) b.get(CHAT_ID);
        controller = new ChatController(this,chatId);

        controller.send(chatId,"");

        profileImageView = findViewById(R.id.profile_imageView);
        nameTextView = findViewById(R.id.profileName);

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference()
                .child(USERS)
                .child(currentUser)
                .child(DETAILS)
                .child(EDIT);

        final String[] profileUri = new String[1];
        final String[] name = new String[1];

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profileUri[0] = dataSnapshot.child(IMAGE_URI).getValue().toString();
                name[0] = dataSnapshot.child(NAME).getValue().toString();

                Glide.with(ChatActivity.this).load(Uri.parse(profileUri[0])).into(profileImageView);
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
            public void onClick(View v) {
                String strText = editText.getText().toString();
                controller.send(chatId,strText);
                editText.setText("");

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