package com.example.projectmcclass;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import android.os.Bundle;

public class admin extends AppCompatActivity {
    Button btnbrowse, btnupload;
    EditText restName ;
    ImageView restImage;
    RadioGroup rG;
    RadioButton rBurger,rBrownie,rSteak,rPizza;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        storageReference = FirebaseStorage.getInstance().getReference("Restaurent");
        databaseReference = FirebaseDatabase.getInstance().getReference("Restaurent");
        restName = (EditText) findViewById(R.id.restName);
        restImage = (ImageView) findViewById(R.id.imageView2);
        rG = (RadioGroup) findViewById(R.id.radioGroup);
        rBurger = (RadioButton) findViewById(R.id.radioButton6);
        rBrownie = (RadioButton) findViewById(R.id.radioButton7);
        rSteak = (RadioButton) findViewById(R.id.radioButton9);
        rPizza = (RadioButton) findViewById(R.id.radioButton8);
        progressDialog = new ProgressDialog(admin.this);


    }
    public void onClickBrowse(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

    }
    public void onClickUpload(View view) {


        UploadImage();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                restImage.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }
    public void UploadImage() {

        if (FilePathUri != null) {

            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String TempImageName = restName.getText().toString();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully " , Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            uploadinfo imageUploadInfo = new uploadinfo(TempImageName, taskSnapshot.getUploadSessionUri().toString());
                            String ImageUploadId = databaseReference.push().getKey();
                            if(rBurger.isChecked()) {
                                databaseReference.child("burger").child(ImageUploadId).setValue(imageUploadInfo);
                            }
                            else if(rBurger.isChecked()){
                                databaseReference.child("burger").child(ImageUploadId).setValue(imageUploadInfo);

                            }
                            else if(rBrownie.isChecked()){
                                databaseReference.child("brownie").child(ImageUploadId).setValue(imageUploadInfo);

                            }
                            else if(rSteak.isChecked()){

                                databaseReference.child("steak").child(ImageUploadId).setValue(imageUploadInfo);
                            }
                            else if(rPizza.isChecked()){

                                databaseReference.child("pizza").child(ImageUploadId).setValue(imageUploadInfo);
                            }
                            else{
                                databaseReference.child("undefined").child(ImageUploadId).setValue(imageUploadInfo);
                            }
                        }
                    });
        }
        else {

            Toast.makeText(admin.this, "Please Select Image or Add Image Name" , Toast.LENGTH_LONG).show();

        }
    }


}
