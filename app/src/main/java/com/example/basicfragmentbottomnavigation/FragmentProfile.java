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
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.basicfragmentbottomnavigation.LoginActivity.*;

public class FragmentProfile extends Fragment {
    private RecyclerView mRecylerView;
    private ImageAdapterNGO mImgAdapterNGO;
    private DatabaseReference mdataRef;
    private DocumentReference mdocref;
    private ProgressBar mProgCircle;
    private List<Upload> mupload;
    private CollectionReference mDataRef;
    private final FirebaseFirestore db= FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment_profile, container, false);
        // Inflate the layout for this fragment
        mRecylerView=view.findViewById(R.id.recycle_view);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgCircle=view.findViewById(R.id.prg_circle);
        mupload=new ArrayList<>();

//        uid = getArguments().getString("UID");
//        Toast.makeText(getActivity(),"is here"+uid,Toast.LENGTH_SHORT).show();


        mDataRef= FirebaseFirestore.getInstance().collection("NGOs");

                // Log.d(TAG, "Current cites in CA: " + cities);





        mdataRef= FirebaseDatabase.getInstance().getReference("applicant").child(fbauth.getCurrentUser().getUid().toString());

        mdataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dss:dataSnapshot.getChildren()){
                    Upload upload=dss.getValue(Upload.class);

                    mupload.add(upload);
                }

                mImgAdapterNGO=new ImageAdapterNGO(getActivity(),mupload,"correct ur con");
                mRecylerView.setAdapter(mImgAdapterNGO);
                mProgCircle.setVisibility(View.INVISIBLE);



            }

public void onCancelled(@NonNull DatabaseError databaseError) {
        Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_LONG);
        mProgCircle.setVisibility(View.INVISIBLE);
        }
        });




        return view;




    }


}
