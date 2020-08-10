package com.example.basicfragmentbottomnavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.basicfragmentbottomnavigation.LoginActivity.fbauth;


public class ImageAdapter2 extends RecyclerView.Adapter<ImageAdapter2.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    String participants;
    private String NGOname;
    private String SUBJECT;

    public ImageAdapter2(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;

       // System.out.println(uid);
//        test = fauth;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_view, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        final Upload uploadCurrent = mUploads.get(position);
        holder.expbtn.setVisibility(View.GONE);
        DatabaseReference mdoc=FirebaseDatabase.getInstance().getReference().child("student_for_event").child(uploadCurrent.getName()+"->"+uploadCurrent.getSubject()+"->"+uploadCurrent.getDate());
        mdoc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dss:dataSnapshot.getChildren()){

                    //Toast.makeText(EventRequest.this,"name:"+map.get("Name").toString(),Toast.LENGTH_SHORT).show();
                    participants+= dss.getValue(String.class);
                    participants+=" , ";
                    System.out.println("this participant is"+participants);


                }

            }

            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


      //  DatabaseReference dbr=FirebaseDatabase.getInstance().getReference().child("student_for_event").child(up)
        holder.tvsubject.setText("Subject "+uploadCurrent.getSubject());
       // holder.tvsubject.setText(uploadCurrent.getSub());
        holder.textViewName.setText(uploadCurrent.getName());
        holder.date.setText("date "+uploadCurrent.getDate());

      //  holder.tvaddr.setText("Location "+uploadCurrent.getLoc());

        // holder.tvagegroup.setText(uploadCurrent.getDesc());

       // NGOname=uploadCurrent.getName();
       // SUBJECT=uploadCurrent.getSubject();

      //  Picasso.get().load(mImageUri).into(mImageView);
    //    Picasso.get().load(uploadCurrent.getImageUrl()).into(holder.imageView);
       // Glide.with(mContext).load(uploadCurrent.getImageUrl()).into(holder.imageView);
//        holder.expbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(holder.cv.getVisibility()== View.GONE)
//                {
//                    holder.cv.setVisibility(View.VISIBLE);
//                    holder.expbtn.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
//
//                }
//               else{
//                   holder.cv.setVisibility(View.GONE);
//                    holder.expbtn.setImageResource(R.drawable.ic_expand_more_black_24dp);
//
//                }
//
//            }
//        });
//
//        holder.btnapply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Map<String, Object> user = new HashMap<>();
//                FirebaseDatabase realt=FirebaseDatabase.getInstance();
//
//                Map<String, Object> user2 = new HashMap<>();
//                Map<String, Object> user3 = new HashMap<>();
//                user.put("applie", fbauth.getCurrentUser().getEmail());
//                user2.put("event", NGOname+"-"+SUBJECT);
//                //user.put("NGO", NGOname);
//
//                user3.put("date",uploadCurrent.getDate());
//                user3.put("imageUrl",uploadCurrent.getImageUrl());
//                user3.put("loc",uploadCurrent.getLoc());
//                user3.put("name",uploadCurrent.getName());
//                String collname=uploadCurrent.getName();
//                user3.put("subject",uploadCurrent.getSubject());
//
//
//                   String uid=fbauth.getCurrentUser().getUid();
//                DatabaseReference rdr=realt.getReference("applicant");
//                rdr.child(uid).child(collname).setValue(user3);
//
//                FirebaseFirestore db=FirebaseFirestore.getInstance();
//                FirebaseFirestore db2=FirebaseFirestore.getInstance();
//               db.collection("applicant").document(NGOname).collection("applicants UID->").add(user);
//               db2.collection("users").document(uid).collection("applied").add(user2);
//               // holder.textViewName.setText(uid);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName,tvaddr,tvagegroup;
        public TextView tvsubject;

        public ImageView imageView;
        public ImageView expbtn;
        public CardView cv;
        public Button btnapply;
        public TextView date;
        public TextView age;


        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.cv_tv_name);
            imageView = itemView.findViewById(R.id.image_img);
            expbtn=itemView.findViewById(R.id.iv_expbtn);
            cv=itemView.findViewById(R.id.cv_expand);
            tvaddr=itemView.findViewById(R.id.cv_tv_addr);
            tvsubject=itemView.findViewById(R.id.cv_tv_subject);
            tvagegroup=itemView.findViewById(R.id.cv_tv_agegroup);
            btnapply=itemView.findViewById(R.id.btn_apply);
           date=itemView.findViewById(R.id.cv_tv_date);
           age=itemView.findViewById(R.id.cv_tv_agegroup);
        }
    }
}
