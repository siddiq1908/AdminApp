package com.siddiq1908.userapp.ui.faculty;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.siddiq1908.userapp.R;

import java.util.ArrayList;
import java.util.List;

public class FacultyFragment extends Fragment {
    private RecyclerView caDepartment;
    private LinearLayout noDataFound;
    private List<TeacherData> list;
    private TeacherAdapter adapter;

    private DatabaseReference databaseReference, dbRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);

        caDepartment = view.findViewById(R.id.caDepartment);
        noDataFound = view.findViewById(R.id.noData);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        caDepartment();

        return view;
    }

    private void caDepartment() {
        dbRef = databaseReference.child("teacher");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                if (!snapshot.exists()) {
                    noDataFound.setVisibility(View.VISIBLE);
                    caDepartment.setVisibility(View.GONE);
                } else {
                    noDataFound.setVisibility(View.GONE);
                    caDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        list.add(data);
                    }
                    caDepartment.setHasFixedSize(true);
                    caDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list, getContext());
                    caDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}