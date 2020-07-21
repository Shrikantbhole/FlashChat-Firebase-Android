package com.shrikantBhole.flashchatnewfirebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainChatActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private String mDisplayName = "Shrikant";
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference mDatabaseReference;
    private ChatListAdaptor mAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        // TODO:  get the Firebase reference

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        // Link the Views in the layout to the Java code
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);

        // TODO: Send the message when the "enter" button is pressed

        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String textMessage = mInputText.getText().toString();
                Log.d("flatchat","Your message is: " +  textMessage);
                sendMessage();
                return true;
            }
        });


        // TODO: Add an OnClickListener to the sendButton to send a message

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("flatchat","Success: onCLickListener() working");
                sendMessage();
            }
        });

    }

    // TODO: Retrieve the display name from the Shared Preferences



    // TODO: Grab the text the user typed in and push the message to Firebase

    private void sendMessage() {

        Log.d("Flashchat","I sent something");



        String input = mInputText.getText().toString();
        if(!input.equals("")) {
            InstantMessage chat = new InstantMessage(input, mDisplayName);
            mDatabaseReference.child("messages").push().setValue(chat);
            mInputText.setText("");
        }


    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.

    @Override

    public void onStart(){

        super.onStart();
        mAdaptor = new ChatListAdaptor(this,mDatabaseReference,mDisplayName);
        //ListView now knows which adaptor to talk to
        mChatListView.setAdapter(mAdaptor);
    }



    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Firebase event listener on the adapter.

        mAdaptor.cleanup();



    }

}
