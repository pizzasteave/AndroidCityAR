package com.example.cityarr.DAO;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cityarr.Dialog.AddPropositionDialog;
import com.example.cityarr.MesProposition;
import com.example.cityarr.entity.Proposition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DaoProposition {

    public static String currentTitle ;
    public static Proposition finalPorp;

    private DatabaseReference databaseReference;


    StorageReference storageReference;

    public DaoProposition() {

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://cityar-78578-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = db.getReference(Proposition.class.getSimpleName());

        storageReference = FirebaseStorage.getInstance().getReference("Proposition");

    }

    public Task<Void> upload() {
        Context applicationContext = MesProposition.getContextOfApplication();

        StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(AddPropositionDialog.image));
        storageReference2.putFile(AddPropositionDialog.image)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(applicationContext.getApplicationContext(), "Upload Successfully", Toast.LENGTH_SHORT).show();
                        storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Proposition proposition = new Proposition(currentTitle,String.valueOf(uri));
                                DatabaseReference databaseReference1 = databaseReference.push();
                                databaseReference1.setValue(proposition).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(applicationContext.getApplicationContext(), "done" , Toast.LENGTH_SHORT).show();
                                        finalPorp = proposition;

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


    public String GetFileExtension(Uri uri) {
        Context applicationContext = MesProposition.getContextOfApplication();
        ContentResolver contentResolver = applicationContext.getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }



}
