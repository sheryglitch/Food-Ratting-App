////List Items

package com.example.projectmcclass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

//Firebase Dependencies

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.util.Queue;

import android.os.Bundle;


public class ListItems extends AppCompatActivity {
    private ArrayList<String> rNames = new ArrayList<String>();
    private ArrayList<String> rUrls = new ArrayList<String>();
    private ArrayList<String> rRating = new ArrayList<String>();
    private ArrayList<String> rService = new ArrayList<String>();
    private ArrayList<String> rQuality = new ArrayList<String>();
    private ArrayList<String> rHygiene = new ArrayList<String>();
    private ArrayList<String> rTaste = new ArrayList<String>();
    private ArrayList<String> rExp = new ArrayList<String>();

    private ArrayList<String> dRating = new ArrayList<String>();
    private ArrayList<String> dService = new ArrayList<String>();
    private ArrayList<String> dQuality = new ArrayList<String>();
    private ArrayList<String> dHygiene = new ArrayList<String>();
    private ArrayList<String> dTaste = new ArrayList<String>();
    private ArrayList<String> dExp = new ArrayList<String>();
    private ArrayList<String> rKey = new ArrayList<String>();
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceInFun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Intent intent = getIntent();
        String fromDish = intent.getStringExtra("from");
        databaseReference = FirebaseDatabase.getInstance().getReference("Restaurent");
        /*databaseReferenceBurger = databaseReference.child("burger");*/
        if(fromDish.equals("burger") || fromDish.equals("burgers") || fromDish.contains("burger") || fromDish.contains("burgers")){
            fromDish = "burger";
            initBurgers(fromDish);
        }
        else if(fromDish.equals("pizza") || fromDish.equals("pizza") || fromDish.contains("pizza")){
            fromDish = "pizza";
            initPizza(fromDish);
        }
        else if(fromDish.equals("steak") || fromDish.equals("steaks") || fromDish.contains("steak") || fromDish.contains("steaks")){
            fromDish = "steak";
            initSteak(fromDish);
        }
        else if(fromDish.equals("brownie") || fromDish.equals("brownies") || fromDish.contains("brownie") || fromDish.contains("brownies")){
            fromDish = "brownie";
            initBrownie(fromDish);
        }
        else{
            Toast.makeText(getApplicationContext(),"NOT FOUND",Toast.LENGTH_SHORT).show();
            Intent intentPre = new Intent(ListItems.this,MainActivity.class);
            startActivity(intentPre);
        }
    }
    private void initBurgers(String dish){
        GetData(dish);
    }
    private void initPizza(String dish){
        GetData(dish);
    }

    private void initSteak(String dish){
        GetData(dish);
    }

    private void initBrownie(String dish){
        GetData(dish);
    }

    /*Database */

    private void GetData(final String dish){
        databaseReferenceInFun = databaseReference.child(dish);
        Query query = databaseReferenceInFun;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    uploadinfo uploadinfo = new uploadinfo();
                    rNames.add(snapshot.child("imageName").getValue().toString());
                    String tempUrl = snapshot.child("imageURL").getValue().toString();
                    String tempSplit = tempUrl.substring(0, tempUrl.indexOf("&"));
                    Log.e("URL AFTER SPLIT",tempSplit);
                    String finalUrl = tempSplit + "?alt=media";
                    finalUrl = finalUrl.replace("?name=", "/");
                    rUrls.add(finalUrl);
                    Log.e("Final URL",finalUrl);
                    dExp.add(snapshot.child("exp").getValue().toString());
                    dHygiene.add(snapshot.child("hygiene").getValue().toString());
                    dQuality.add(snapshot.child("quality").getValue().toString());
                    dService.add(snapshot.child("service").getValue().toString());
                    dTaste.add(snapshot.child("taste").getValue().toString());
                    rKey.add(snapshot.getKey());

                }
                Log.e("Average : ",calAvg(dService.get(0)));
                for(int i = 0;i < dService.size() ;i++) {
                    rService.add(calAvg(dService.get(i)));
                    rTaste.add(calAvg(dTaste.get(i)));
                    rHygiene.add(calAvg(dHygiene.get(i)));
                    rExp.add(calAvg(dExp.get(i)));
                    rQuality.add(calAvg(dQuality.get(i)));
                }
                sortRecyclerView(dish);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    /* Average Function*/

    public String calAvg(String list) {
        double avg = 0.0;
        int sz = list.length();
        boolean flag = false;
        String num = "";
        int index = 0;
        ArrayList<String> tempString = new ArrayList<String>();
        while (index < list.length()) {
            tempString.add(list.substring(index, Math.min(index + 3,list.length())));
            index += 3;
        }
        if(list.length()>3 && tempString.get(0).equals("0.0"))
        {
            return tempString.get(1);
        }
        else {
            for (int i = 0; i < tempString.size(); i++) {
                Log.e("One Number", tempString.get(i));
                avg = avg + Double.parseDouble(tempString.get(i));
            }

            Log.e("Average", String.valueOf(avg));

            int rs = list.length() / 3;
            avg = avg / rs;
            Double toBeTruncated = avg;
            Double truncatedDouble = BigDecimal.valueOf(toBeTruncated)
                    .setScale(1, RoundingMode.HALF_UP)
                    .doubleValue();

            return String.valueOf(truncatedDouble);
        }
    }

    /*Lists Sorting*/

    public void sortRecyclerView(String d)
    {
        int sz = rService.size();
        for(int c = 0; c < sz ; c++)
        {
            Float rrRating;
            Float temp = Float.parseFloat(rService.get(c))+Float.parseFloat(rQuality.get(c))+Float.parseFloat(rHygiene.get(c))+Float.parseFloat(rTaste.get(c))+Float.parseFloat(rExp.get(c));
            rrRating = temp/5;
            rRating.add(Float.toString(rrRating));
        }
        for(int i=0; i < sz; i++)
        {

            for(int j = i+1; j < sz; j++)
            {
                if(Float.parseFloat(rRating.get(i)) < Float.parseFloat(rRating.get(j)))
                {
                    Collections.swap(rRating, i, j);
                    Collections.swap(rService, i, j);
                    Collections.swap(rQuality, i, j);
                    Collections.swap(rHygiene, i, j);
                    Collections.swap(rTaste, i, j);
                    Collections.swap(rExp, i, j);
                    Collections.swap(rNames,i,j);
                    Collections.swap(rUrls,i,j);
                    Collections.swap(rKey,i,j);
                }
            }
        }
        initRecyclerView(d);
    }

    /*Initialize RecyclerView*/
    private void initRecyclerView(String dish){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(rRating,rNames,rUrls,rService,rQuality,rHygiene,rTaste,rExp,rKey,dish,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
