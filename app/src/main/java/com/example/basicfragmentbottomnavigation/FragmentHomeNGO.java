//package com.example.basicfragmentbottomnavigation;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static android.content.ContentValues.TAG;
//
//
//public class FragmentHomeNGO extends Fragment  {
//
//    private Button mAddEvent;
//
//    private RecyclerView mRecylerView;
//    private ImageAdapter mImgAdapter;
//    private DatabaseReference mdataRef;
//    private DocumentReference mdocref;
//    private ProgressBar mProgCircle;
//    private List<Upload> mupload;
//    private List<Upload> mupload2;
//    private CollectionReference mDataRef;
//    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
//    String uid;
//    private String address, ngoName;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_fragment_home_ngo, container, false);
//
//
//
//        return view;
//    }
//
//
//
//}
//
package com.example.basicfragmentbottomnavigation;

import static com.example.basicfragmentbottomnavigation.LoginActivity.*;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.TestLooperManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;


public class FragmentHomeNGO extends Fragment {

    private TextView tvname2, tvaddr, tvemail, tvage;
    private String name,address,age;
    private ImageView profimage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_home_ngo, container, false);
//       tvname=container.findViewById(R.id.tv_profname);
//       tvaddr=container.findViewById(R.id.tv_profaddr);
//       tvage=container.findViewById(R.id.tv_profname);

//        tvname2 = v.findViewById(R.id.tv_profnamen);
//        tvaddr = v.findViewById(R.id.tv_profaddrn);
//        tvage = v.findViewById(R.id.tv_profagen);
//        tvemail=v.findViewById(R.id.tv_profemailn);
//        profimage=v.findViewById(R.id.iv_profilen);
         tvname2=v.findViewById(R.id.tv_profnamen);
         tvaddr=v.findViewById(R.id.tv_profaddrn);
         tvage=v.findViewById(R.id.tv_profagen);
         tvemail=v.findViewById(R.id.tv_profemailn);
         profimage=v.findViewById(R.id.iv_profilen);


         Picasso.get().load(R.drawable.upload_img_here).into(profimage);
        profimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AltImageActivity.class));
            }
        });


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference myref = FirebaseDatabase.getInstance().getReference().child("Users").child("NGO").child(user.getUid());
        Toast.makeText(getActivity(),"IN PROFILE:"+user.getUid(),Toast.LENGTH_LONG).show();
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String,Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    name = map.get("name").toString();
                     String img=map.get("imageUri").toString();
                    // age = map.get("Age").toString();
                    address = map.get("loc").toString();
                    Toast.makeText(getActivity(),"Name:"+name,Toast.LENGTH_LONG).show();
                  tvname2.setText("user name->");
                     tvname2.setText("user name->"+name);
                    tvaddr.setText("address ->"+address);
                    tvage.setVisibility(View.GONE);
                    //  tvage.setText("age->"+age);
                    tvemail.setText("E-mail ->"+user.getEmail().toString());
                    Picasso.get().load(img).into(profimage);





                } else {
                    Toast.makeText(getActivity(), "going in else ", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return v;
    }



}
