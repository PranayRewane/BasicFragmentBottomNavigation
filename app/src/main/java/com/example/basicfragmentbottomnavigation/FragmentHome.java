package com.example.basicfragmentbottomnavigation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class FragmentHome extends Fragment {

    private RecyclerView mRecylerView;
    private ImageAdapter mImgAdapter;
    private DatabaseReference mdataRef;
    private DocumentReference mdocref;
    private ProgressBar mProgCircle;
    private List<Upload> mupload;
    private List<Upload> mupload2;
    private CollectionReference mDataRef;
    private final FirebaseFirestore db= FirebaseFirestore.getInstance();
    String uid;

//
//    public FragmentHome(String id) {
//        uid = id;
//        Toast.makeText(getActivity(),"is here"+uid,Toast.LENGTH_SHORT).show();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);

        mRecylerView=view.findViewById(R.id.recycle_view);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgCircle=view.findViewById(R.id.prg_circle);
        mupload=new ArrayList<>();
        mupload2=new ArrayList<>();


        mDataRef= FirebaseFirestore.getInstance().collection("NGOs");
        db.collection("NGOs").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        // List<String> cities = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {

                            Upload upload=doc.toObject(Upload.class);

                            mupload.add(upload);


                        }
                        mImgAdapter=new ImageAdapter(getActivity(),mupload,uid);
                        mRecylerView.setAdapter(mImgAdapter);
                        mProgCircle.setVisibility(View.INVISIBLE);
                        // Log.d(TAG, "Current cites in CA: " + cities);
                    }
                });


//        Map<String,Object> map=new HashMap<>();
//
//          DatabaseReference drf=FirebaseDatabase.getInstance().getReference();
//          drf.child("Users").child("Events");
//          drf.addValueEventListener(new ValueEventListener() {
//              @Override
//              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                  for(DataSnapshot dss:dataSnapshot.getChildren()){
//                      System.out.println("-------------------------");
//
//                      Upload up=dss.getValue(Upload.class);
//                      System.out.println("subject is "+up.getSubject());
//
//                      mupload2.add(up);
//                  }
//                  mImgAdapter=new ImageAdapter(getActivity(),mupload2,"for no reasons at all");
//                  mRecylerView.setAdapter(mImgAdapter);
//                  mProgCircle.setVisibility(View.INVISIBLE);
//              }
//
//              @Override
//              public void onCancelled(@NonNull DatabaseError databaseError) {
//                  Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_LONG);
//              mProgCircle.setVisibility(View.INVISIBLE);
////
//              }
//          });

//        drf.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                System.out.println(dataSnapshot.getValue());
//                for(DataSnapshot dss:dataSnapshot.getChildren()){
//                   // Upload upload=dss.getValue(Upload.class);
//
//                    Upload thisupload=dss.getValue(Upload.class);
//                    System.out.println("-----------------------------------------------------------");
//
//                    System.out.println("date is "+thisupload.getDate());
//                    System.out.println("name is :"+thisupload.getName());
//                    System.out.println("subject is "+thisupload.getSubject());
//                    mupload2.add(thisupload);
//                }
//
//                mImgAdapter=new ImageAdapter(getActivity(),mupload2,"for no reason at all");
//                mRecylerView.setAdapter(mImgAdapter);
//                mProgCircle.setVisibility(View.INVISIBLE);
//
//
//
//            }
//
//public void onCancelled(@NonNull DatabaseError databaseError) {
//        Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_LONG);
//        mProgCircle.setVisibility(View.INVISIBLE);
//        }
//        });



       return view;
    }

}
/*
super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mRecylerView=findViewById(R.id.recycle_view);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        mProgCircle=findViewById(R.id.prg_circle);
        mupload=new ArrayList<>();
        mDataRef= FirebaseDatabase.getInstance().getReference("uploads");





        mDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dss:dataSnapshot.getChildren()){
                    Upload upload=dss.getValue(Upload.class);

                    mupload.add(upload);
                }

                mImgAdapter=new ImageAdapter(ImageActivity.this,mupload);
                mRecylerView.setAdapter(mImgAdapter);
                mProgCircle.setVisibility(View.INVISIBLE);
                /*
                 for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }

                mAdapter = new ImageAdapter(ImagesActivity.this, mUploads);

                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);

            }
/*

public void onCancelled(@NonNull DatabaseError databaseError) {
        Toast.makeText(ImageActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG);
        mProgCircle.setVisibility(View.INVISIBLE);
        }
        });
 */
