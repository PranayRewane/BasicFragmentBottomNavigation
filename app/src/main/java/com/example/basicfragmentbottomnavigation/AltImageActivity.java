package com.example.basicfragmentbottomnavigation;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static com.example.basicfragmentbottomnavigation.LoginActivity.*;


public class AltImageActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private DocumentReference mDocRef;
    private StorageTask mUploadTask;
    private Uri mImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alt_image);


        mButtonUpload = findViewById(R.id.btn_upload);
      //  mTextViewShowUploads = findViewById(R.id.tv_viewimg);
       // mEditTextFileName = findViewById(R.id.et_imgname);
        mImageView = findViewById(R.id.iv_img);
        mProgressBar = findViewById(R.id.prgb);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        Button btnchoose=findViewById(R.id.btn_chosefile);
        btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });



        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(AltImageActivity.this, "uploading", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

//        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openAllImages();
//            }
//        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
            Toast.makeText(AltImageActivity.this,mImageUri.toString(),Toast.LENGTH_LONG).show();

        }
    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mtm = MimeTypeMap.getSingleton();
        return mtm.getExtensionFromMimeType(cr.getType(uri));

    }

    private void uploadFile() {
        UploadTask uploadTask;
        final String[] downloadURL = new String[1];
        if(mImageUri!=null){
            final StorageReference fileRef=mStorageRef.child(System.currentTimeMillis()+"."+getExtension(mImageUri));
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            System.currentTimeMillis();

            uploadTask = fileRef.putFile(mImageUri);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        progressDialog.dismiss();
                        downloadURL[0] = downloadUri.toString();
                        Toast.makeText(getApplicationContext(), "its her but "+downloadURL[0],Toast.LENGTH_SHORT).show();
                        DatabaseReference mDatabaseRe = FirebaseDatabase.getInstance().getReference("Users").child("NGO").child(fbauth.getCurrentUser().getUid()).child("imageUri");
                                mDatabaseRe.setValue(downloadURL[0]);;
                        Map<String,Object> mmap=new HashMap<String, Object>();
                        mmap.put("imageUri",downloadURL[0]);
                        db.collection("NGO").document(fbauth.getCurrentUser().getUid()).set(mmap);

                      //  mDatabaseRef.child("Users").child("Student").child(fbauth.getCurrentUser().getUid()).child("imageUri").setValue(downloadURL[0]);;

                      //  mDatabaseRef.child("imageUri");
                      //  Map<String,Object> hhmap=new HashMap<String, Object>();
                       // mDatabaseRef.setValue(downloadURL);
                        ImageView ivprofile=findViewById(R.id.iv_profilen);
                   //     Picasso.get().load(downloadURL[0]).into(ivprofile);
                        startActivity(new Intent(AltImageActivity.this,Main2Activity.class));
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });




            mUploadTask=fileRef.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    },600);







                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AltImageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int) progress);
                }
            });




        }
        else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openAllImages(){
        startActivity(new Intent(this,ImageActivity.class));

    }
}
