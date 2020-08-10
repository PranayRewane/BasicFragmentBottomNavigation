package com.example.basicfragmentbottomnavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventRequestFragment extends Fragment {
    private EditText mName, mSubject, mTopic, mStandard, mDate;
    private Button mSubmit;
    private String address, ngoName;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_event_request, container, false);

        mName = v.findViewById(R.id.name);
        mSubject = v.findViewById(R.id.subject);
        mTopic = v.findViewById(R.id.topic);
        mStandard = v.findViewById(R.id.standard);
        mDate = v.findViewById(R.id.date);

        mSubmit = v.findViewById(R.id.submit);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference NGOdb = FirebaseDatabase.getInstance().getReference().child("Users").child("NGO").child(user.getUid());
        NGOdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    //Toast.makeText(EventRequest.this,"name:"+map.get("Name").toString(),Toast.LENGTH_SHORT).show();
                    address = map.get("loc").toString();
                    ngoName = map.get("name").toString();
                } else {
                    Toast.makeText(getActivity(), "name:Not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name = mName.getText().toString();
                final String subject = mSubject.getText().toString();
                final String topic = mTopic.getText().toString();
                final String date = mDate.getText().toString();
                final String standard = mStandard.getText().toString();

                Toast.makeText(getActivity(), "EVENTNAME:" + user.getUid(), Toast.LENGTH_SHORT).show();
                DatabaseReference eventdb = FirebaseDatabase.getInstance().getReference().child("Users").child("Events").child(name);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> doc = new HashMap<String, Object>();
                doc.put("name", ngoName);
                doc.put("address", address);
                doc.put("subject", subject);
                doc.put("topic", topic);
                doc.put("date", date);
                doc.put("Standard", standard);
                eventdb.setValue(doc);
                db.collection("NGOs").add(doc);
                Map<String, Object> doc2 = new HashMap<String, Object>();
                doc2.put("subject",subject);
                doc2.put("date",date);
                DatabaseReference ngo = FirebaseDatabase.getInstance().getReference().child("Users").child("NGO").child(user.getUid()).child("EventReq");
                ngo.child(subject+"->"+date).setValue(doc2);

            }

        });
        return v;

    }
}