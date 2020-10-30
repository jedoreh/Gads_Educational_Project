package com.example.jotham_gadsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubjectActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText txtTitle;
    EditText txtClass;
    EditText txtDescription;
    Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        //FirebaseUtil.openFbReference("Subject", this);
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        txtTitle = (EditText) findViewById(R.id.txt_subject);
        txtClass = (EditText) findViewById(R.id.txt_class);
        txtDescription = (EditText) findViewById(R.id.txt_description);
        Intent intent = getIntent();
        Subject subject = (Subject) intent.getSerializableExtra("Subject");
        if(subject == null) {
            subject = new Subject();
        }
        this.subject = subject;
        txtTitle.setText(subject.getTitle());
        txtDescription.setText(subject.getDescription());
        txtClass.setText(subject.getClassname());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_menu:
                saveSubject();
                Toast.makeText(this, "Subject Saved", Toast.LENGTH_LONG).show();
                clean();
                backToList();
                return true;
            case R.id.delete_menu:
                deleteSubject();
                Toast.makeText(this, "Subject Deleted", Toast.LENGTH_LONG).show();
                backToList();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);


        if(FirebaseUtil.isAdmin) {
            menu.findItem(R.id.delete_menu).setVisible(true);
            menu.findItem(R.id.save_menu).setVisible(true);
            enableEditTexts(true);
        } else {
            menu.findItem(R.id.delete_menu).setVisible(true);
            menu.findItem(R.id.save_menu).setVisible(true);
            enableEditTexts(false);

        }

        return true;
    }

    private void saveSubject() {
        subject.setTitle(txtTitle.getText().toString());
        subject.setClassname(txtClass.getText().toString());
        subject.setDescription(txtDescription.getText().toString());

        if(subject.getId()==null){
            mDatabaseReference.push().setValue(subject);
        }
        else {
            mDatabaseReference.child(subject.getId()).setValue(subject);
        }

    }

    private void deleteSubject() {
        if(subject==null){
            Toast.makeText(this,"Please save the subject first",Toast.LENGTH_SHORT).show();
            return;
        }
        mDatabaseReference.child(subject.getId()).removeValue();
    }

    private void backToList() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void clean() {
        txtDescription.setText("");
        txtClass.setText("");
        txtTitle.setText("");

        txtTitle.requestFocus();
    }

    private void enableEditTexts (boolean isEnabled) {
        txtTitle.setEnabled(isEnabled);
        txtClass.setEnabled(isEnabled);
        txtDescription.setEnabled(isEnabled);
    }
}