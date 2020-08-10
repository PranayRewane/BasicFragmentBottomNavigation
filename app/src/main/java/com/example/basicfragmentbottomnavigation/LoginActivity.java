package com.example.basicfragmentbottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText etname, etpwd;
    private Button btnlogin, backdoor;
    private TextView tvsignup;
    static public FirebaseAuth fbauth;
    private SharedPreferences sp;
    private FirebaseAuth mAuth;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etname=findViewById(R.id.et_login_name);
        etpwd=findViewById(R.id.et_login_passwd);
        btnlogin = (Button) findViewById(R.id.btn_login);
//        backdoor = (Button) findViewById(R.id.btn_backdoor);
        tvsignup = (TextView) findViewById(R.id.tv_signup);
        fbauth = FirebaseAuth.getInstance();
//        sp=getSharedPreferences("login",MODE_PRIVATE);
//        if(sp.getBoolean("logged",false)) {
//            finish();
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//         startActivity(intent);
//        }


        mAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etname.getText().toString();
                String password = etpwd.getText().toString();
//                fbauth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                            String uid = fbauth.getCurrentUser().getUid().toString();
//                            Toast.makeText(LoginActivity.this, "logged in successfully", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("UID",uid));
//
//
//                    }
//                });

                fbauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "account not found", Toast.LENGTH_SHORT).show();
                        } else {
                             // sp.edit().putBoolean("logged",true).apply();
                            final FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference studentdb = FirebaseDatabase.getInstance().getReference().child("Users").child("Student").child(user1.getUid());

                            studentdb.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists() ) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        return;
                                    } else {
                                        Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                                        startActivity(intent);
                                        finish();
                                        return;
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
            }
        });
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));

            }
        });
//        backdoor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            }
//        });


    }
    public FirebaseAuth getuser(){
        return this.fbauth;
    }
}
