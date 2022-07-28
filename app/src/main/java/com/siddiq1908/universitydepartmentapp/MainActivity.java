package com.siddiq1908.universitydepartmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.siddiq1908.universitydepartmentapp.faculty.UpdateFaculty;
import com.siddiq1908.universitydepartmentapp.notice.DeleteNotice;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView uploadNotice, deleteNotice, uploadImage, uploadPdf, updateFaculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadNotice = findViewById(R.id.addNotice);
        deleteNotice = findViewById(R.id.deleteNotice);
        uploadImage = findViewById(R.id.addImage);
        uploadPdf = findViewById(R.id.uploadPdf);
        updateFaculty = findViewById(R.id.addFaculty);

        uploadNotice.setOnClickListener(this);
        deleteNotice.setOnClickListener(this);
        uploadImage.setOnClickListener(this);
        uploadPdf.setOnClickListener(this);
        updateFaculty.setOnClickListener(this);

        Button signOut = findViewById(R.id.signOut);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.addNotice:
                intent = new Intent(MainActivity.this, UploadNotice.class);
                startActivity(intent);
                break;
            case R.id.deleteNotice:
                intent = new Intent(MainActivity.this, DeleteNotice.class);
                startActivity(intent);
                break;
            case R.id.addImage:
                intent = new Intent(MainActivity.this, UploadImage.class);
                startActivity(intent);
                break;
            case R.id.uploadPdf:
                intent = new Intent(MainActivity.this, UploadPdf.class);
                startActivity(intent);
                break;
            case R.id.addFaculty:
                intent = new Intent(MainActivity.this, UpdateFaculty.class);
                startActivity(intent);
                break;
        }
    }
}