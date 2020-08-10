package com.example.basicfragmentbottomnavigation;

import static com.example.basicfragmentbottomnavigation.LoginActivity.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.TestLooperManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class FragmentInfo extends Fragment {

    private TextView tvname, tvaddr, tvemail, tvage;
    private String name,address,age;
    private ImageView profimage;
    Button logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_info, container, false);
//       tvname=container.findViewById(R.id.tv_profname);
//       tvaddr=container.findViewById(R.id.tv_profaddr);
//       tvage=container.findViewById(R.id.tv_profname);
          logout=v.findViewById(R.id.logoutstu);
        tvname = v.findViewById(R.id.tv_profname);
        tvaddr = v.findViewById(R.id.tv_profaddr);
        tvage = v.findViewById(R.id.tv_profage);
        tvemail=v.findViewById(R.id.tv_profemail);
        profimage=v.findViewById(R.id.iv_profilestu);

        Picasso.get().load(R.drawable.upload_img_here).into(profimage);
        profimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  startActivity(new Intent(getActivity(),ImageActivity.class));
            }
        });
          logout.setVisibility(View.GONE);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                  SharedPreferences sp2 = null;
//                 sp2.edit().putBoolean("logged",false).apply();
//            }
//        });
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference myref = FirebaseDatabase.getInstance().getReference().child("Users").child("Student").child(user.getUid());
        Toast.makeText(getActivity(),"IN PROFILE:"+user.getUid(),Toast.LENGTH_LONG).show();
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String,Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    name = map.get("name").toString();
                    String str=map.get("imageUri").toString();
                   // age = map.get("Age").toString();
                    address = map.get("loc").toString();
                    Toast.makeText(getActivity(),"Name:"+name,Toast.LENGTH_LONG).show();

                    Picasso.get().load(str).into(profimage);
                    tvname.setText("user name->"+name);
                    tvaddr.setText("address ->"+address);
                    tvage.setVisibility(View.GONE);
                  //  tvage.setText("age->"+age);
                   tvemail.setText("E-mail ->"+user.getEmail().toString());





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
