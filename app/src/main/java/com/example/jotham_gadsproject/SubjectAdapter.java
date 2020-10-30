package com.example.jotham_gadsproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    ArrayList<Subject> subjects;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    private static Activity caller;
    public SubjectAdapter(ListActivity callerActivity) {

        FirebaseUtil.openFbReference("Subject", callerActivity);
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;

        subjects = FirebaseUtil.mSubjects;
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Subject subject = dataSnapshot.getValue(Subject.class);
                Log.d("Subject: ", subject.getTitle());
                subject.setId(dataSnapshot.getKey());
                subjects.add(subject);
                notifyItemInserted(subjects.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);

    }
    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.rvrow,parent,false);
        return new SubjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {

        Subject subject = subjects.get(position);
        holder.bind(subject);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        TextView tvTitle;
        TextView tvDescription;
        TextView tvClass;
        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvClass = (TextView) itemView.findViewById(R.id.tvClass);
            itemView.setOnClickListener(this);
        }

        public void bind(Subject subject) {
            tvTitle.setText(subject.getTitle());
            tvDescription.setText(subject.getDescription());
            tvClass.setText(subject.getClassname());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Subject selectedSubject = subjects.get(position);
            Intent intent = new Intent(view.getContext(), SubjectActivity.class);
            intent.putExtra("Subject", selectedSubject);
            view.getContext().startActivity(intent);
        }
    }
}
