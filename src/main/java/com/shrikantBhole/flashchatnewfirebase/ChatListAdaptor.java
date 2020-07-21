package com.shrikantBhole.flashchatnewfirebase;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;



public class ChatListAdaptor extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;
    private ArrayList<DataSnapshot> mSnapshotList;

    // A dataSnapshot instance contains data from Firebase data location. ANy time you receive
    // Data, you receive the data as data snapshot
    // Why are we using ArrayList? In Java, arraylist can grow or shrink in size in contrast to array.

    public ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mSnapshotList.add(dataSnapshot);
            //After each addition to mSnapshotList we have to notify ListView
            // that it needs to refresh itself
            notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public ChatListAdaptor(MainChatActivity activity, DatabaseReference ref, String name) {

        mActivity = activity;
        mDisplayName = name;
        mDatabaseReference = ref.child("messages");
        // No Idea why we added over here
        mDatabaseReference.addChildEventListener(mListener);
        mSnapshotList = new ArrayList<>();
    }




    static class ViewHolder{

        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {

        return mSnapshotList.size();
    }

    @Override
    public InstantMessage getItem(int position) {

        DataSnapshot snapshot = mSnapshotList.get(position);
        // Converts JSON from snapshot into instant message object.
        return snapshot.getValue(InstantMessage.class);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView  == null){
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_msg_row,parent,false);
            final ViewHolder holder = new ViewHolder();
            holder.authorName = convertView.findViewById(R.id.author);
            holder.body = convertView.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
        }


        final ViewHolder holder = new ViewHolder();
        holder.authorName = convertView.findViewById(R.id.author);
        holder.body = convertView.findViewById(R.id.message);
        holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();


        final InstantMessage message = getItem(position);




        String author = message.getAuthor();
        holder.authorName.setText(author);

        String msg = message.getMessage();
        holder.body.setText(msg);


        return convertView;
    }

    public void cleanup(){

      mDatabaseReference.removeEventListener(mListener);
    };
}
