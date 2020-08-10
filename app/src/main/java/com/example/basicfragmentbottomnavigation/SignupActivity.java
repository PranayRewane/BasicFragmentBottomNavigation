package com.example.basicfragmentbottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

        private Button mRegister;
        private EditText mEmail, mPassword, mName, mAddresss;
        private RadioGroup mRadioGroup;
        TextView tv;

        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener authStateListener;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);
           tv=findViewById(R.id.tv_gotologin);
           tv.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                   startActivity(intent);
               }
           });
            mAuth = FirebaseAuth.getInstance();
            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
//                    Toast.makeText(Registeration.this,"uid"+user.getUid().toString(),Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(Registeration.this,MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                    return;
                    }
                }
            };

            mRegister = (Button) findViewById(R.id.register);
            mEmail = (EditText) findViewById(R.id.email);
            mPassword = (EditText) findViewById(R.id.password);
            mName = (EditText) findViewById(R.id.name);
            mAddresss = (EditText) findViewById(R.id.address);

            mRadioGroup = (RadioGroup) findViewById(R.id.RadioGroup);


            mRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selectId = mRadioGroup.getCheckedRadioButtonId();
                    final RadioButton radioButton = (RadioButton) findViewById(selectId);

                    if (radioButton.getText() == null) {
                        return;
                    }

                    final String email = mEmail.getText().toString();
                    final String password = mPassword.getText().toString();
                    final String name = mName.getText().toString();
                    final String address = mAddresss.getText().toString();


                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, "already a user", Toast.LENGTH_SHORT).show();
                            } else {
                                String userId = mAuth.getCurrentUser().getUid();
                                DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(radioButton.getText().toString()).child(userId);
                                currentUserDb.child("name").setValue(name);
                                currentUserDb.child("imageUri").setValue("not set yet");

                                currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child("All").child(userId).child("Usertype");
                                currentUserDb.setValue(radioButton.getText().toString());

                                currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(radioButton.getText().toString()).child(userId);
                                currentUserDb.child("loc").setValue(address);

                                if (radioButton.getText().toString().compareTo("Student")==0) {
                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }else{
                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }
                            }
                        }
                    });
                }
            });
        }

        @Override
        protected void onStart() {
            super.onStart();
            mAuth.addAuthStateListener(authStateListener);
        }

        @Override
        protected void onStop() {
            super.onStop();
            mAuth.addAuthStateListener(authStateListener);
        }
    }
