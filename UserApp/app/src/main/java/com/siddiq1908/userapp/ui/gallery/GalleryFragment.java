package com.siddiq1908.userapp.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.siddiq1908.userapp.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    RecyclerView convoRecycler, indeRecycler, otherRecycler;
    GalleryAdapter adapter;

    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        convoRecycler = view.findViewById(R.id.convoRecycler);
        indeRecycler = view.findViewById(R.id.indeRecycler);
        otherRecycler = view.findViewById(R.id.otherRecycler);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("gallery");

        getConovoImage();
        getIndeImage();
        getOthersImage();

        return view;
    }

    private void getOthersImage() {
        databaseReference.child("Other Events").addValueEventListener(new ValueEventListener() {
            List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String data = (String) snapshot1.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(), imageList);
                otherRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                otherRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getIndeImage() {
        databaseReference.child("Fiesta").addValueEventListener(new ValueEventListener() {
            List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String data = (String) snapshot1.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(), imageList);
                indeRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                indeRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getConovoImage() {
        databaseReference.child("Convocation").addValueEventListener(new ValueEventListener() {
            List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String data = (String) snapshot1.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(), imageList);
                convoRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                convoRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}