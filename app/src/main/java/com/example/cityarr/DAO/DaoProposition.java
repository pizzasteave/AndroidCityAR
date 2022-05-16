package com.example.cityarr.DAO;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cityarr.Dialog.AddPropositionDialog;
import com.example.cityarr.LesProposition;
import com.example.cityarr.entity.Proposition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DaoProposition {

    public static String currentTitle ;
    public static Proposition finalPorp;

    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;

    private DatabaseReference propositionRef;
    private FirebaseAuth mAuth;

    StorageReference storageReference;

    public DaoProposition() {

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://cityar-78578-default-rtdb.europe-west1.firebasedatabase.app");

        databaseReference = db.getReference(Proposition.class.getSimpleName());
        databaseReference2 = db.getReference("UserPP");
        mAuth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference("Proposition");

    }

    public Task<Void> upload() {
        Context applicationContext = LesProposition.getContextOfApplication();

        StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(AddPropositionDialog.image));
        storageReference2.putFile(AddPropositionDialog.image)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(applicationContext.getApplicationContext(), "Upload Successfully", Toast.LENGTH_SHORT).show();
                        storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                DatabaseReference databaseReference1 = databaseReference ;
                                String mGroupId = databaseReference1.push().getKey();

                                Proposition proposition = new Proposition(mGroupId,currentTitle,String.valueOf(uri));


                                databaseReference1.child(mGroupId).setValue(proposition).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(applicationContext.getApplicationContext(), "done" , Toast.LENGTH_SHORT).show();
                                        finalPorp = proposition;


                                        FirebaseUser user = mAuth.getCurrentUser();
                                        DatabaseReference databaseReference3 = databaseReference2;
                                        databaseReference3.child(user.getUid()).push().setValue(mGroupId);

                                        AddPropositionDialog.instance.dismiss();
                                    }
                                });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        return null;
    }



    public Task<Void> removeFromFireBase(String id) {

        propositionRef = FirebaseDatabase.getInstance().getReference("Proposition");
        propositionRef.child(id).removeValue();

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String currentUserId = fAuth.getCurrentUser().getUid();
        propositionRef = FirebaseDatabase.getInstance().getReference("UserPP").child(currentUserId);

        propositionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    if((postSnapshot.getValue()).equals(id)){
                       String key = postSnapshot.getKey();

                       propositionRef.child(key).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return null;

    }


    public String GetFileExtension(Uri uri) {
        Context applicationContext = LesProposition.getContextOfApplication();
        ContentResolver contentResolver = applicationContext.getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }



}
