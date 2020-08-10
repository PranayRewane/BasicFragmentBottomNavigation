package com.example.basicfragmentbottomnavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.basicfragmentbottomnavigation.LoginActivity.fbauth;


public class FragmentInfoNGO extends Fragment {

    private TextView tvname, tvaddr, tvemail, tvage;
    private String name,address,age;
    private List<Upload> mupload;
    private ImageAdapter2 mImgAdapter;
    private RecyclerView mrview;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_info_ngo, container, false);
//       tvname=container.findViewById(R.id.tv_profname);
//       tvaddr=container.findViewById(R.id.tv_profaddr);
//       tvage=container.findViewById(R.id.tv_profname);
        mrview=v.findViewById(R.id.recycle_view);
        progressBar=v.findViewById(R.id.prg_circle);
       // mrview.setHasFixedSize(true);
        mrview.setLayoutManager(new LinearLayoutManager(getActivity()));

        tvname = v.findViewById(R.id.tv_profname);
        tvaddr = v.findViewById(R.id.tv_profaddr);
        tvage = v.findViewById(R.id.tv_profage);
        mupload=new ArrayList<>();


       final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference myref = FirebaseDatabase.getInstance().getReference().child("Users").child("NGO").child(user.getUid()).child("EventReq");
//        String onestepdeeper = myref.push().getKey();
//        myref.child(onestepdeeper);
      //  Toast.makeText(getActivity(),"IN PROFILE:"+user.getUid(),Toast.LENGTH_LONG).show();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dss:dataSnapshot.getChildren()){
                    Upload upload=dss.getValue(Upload.class);

                  //  Toast.makeText(getActivity(),"date->"+upload.getDate()+" and subject "+upload.getSubject(),Toast.LENGTH_LONG).show();
                    mupload.add(upload);
                }

                mImgAdapter=new ImageAdapter2(getActivity(),mupload);
                mrview.setAdapter(mImgAdapter);
            //    progressBar.setVisibility(View.INVISIBLE);

    

            }

            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"oncallncelled"+databaseError.getMessage(),Toast.LENGTH_LONG);
               progressBar.setVisibility(View.INVISIBLE);
            }
        });

        return v;
    }


}
