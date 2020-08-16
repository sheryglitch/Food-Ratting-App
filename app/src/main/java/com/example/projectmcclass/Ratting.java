package com.example.projectmcclass;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.widget.EditText;
        import android.widget.RatingBar;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;

        import org.w3c.dom.Text;

        import de.hdodenhof.circleimageview.CircleImageView;
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

public class Ratting extends AppCompatActivity {
    private String rName;
    private String rService;
    private String rQuality;
    private String rHygiene;
    private String rTaste;
    private String rExp;
    private String rDish;
    private String rImage;
    private String rKey;
    private TextView restName;
    private TextView dishName;
    private CircleImageView restImage;
    private RatingBar serviceBar,qualityBar,hygieneBar,tasteBar,expBar;
    private Context rContext;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratting);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        rService = extras.getString("service");
        rQuality = extras.getString("quality");
        rHygiene = extras.getString("hygiene");
        rTaste = extras.getString("taste");
        rExp = extras.getString("exp");
        rDish = extras.getString("dish");
        rImage = extras.getString("image");
        rName = extras.getString("name");
        rKey = extras.getString("key");
        databaseReference = FirebaseDatabase.getInstance().getReference("Restaurent").child(rDish).child(rKey);
        restName = (TextView) findViewById(R.id.textView2);
        dishName = (TextView) findViewById(R.id.textView4);
        serviceBar = (RatingBar) findViewById(R.id.ratingBar2);
        qualityBar = (RatingBar) findViewById(R.id.ratingBar4);
        hygieneBar = (RatingBar) findViewById(R.id.ratingBar5);
        tasteBar = (RatingBar) findViewById(R.id.ratingBar3);
        expBar = (RatingBar) findViewById(R.id.ratingBar6);
        restImage = (CircleImageView) findViewById(R.id.restImage);
        Glide.with(Ratting.this)
                .load(rImage)
                .into(restImage);
        restName.setText(rName.toUpperCase());
        rDish = rDish.toUpperCase();
        dishName.setText("RATING FOR " + rDish);

        serviceBar.setRating(Float.parseFloat(rService));
        qualityBar.setRating(Float.parseFloat(rQuality));
        hygieneBar.setRating(Float.parseFloat(rHygiene));
        tasteBar.setRating(Float.parseFloat(rTaste));
        expBar.setRating(Float.parseFloat(rExp));



    }
    public void onRating(View v)
    {
        Float serv = serviceBar.getRating();
        Float qual = qualityBar.getRating();
        Float hygi = hygieneBar.getRating();
        Float taste = tasteBar.getRating();
        Float exp = expBar.getRating();
        String servs = serv.toString();
        String quals = qual.toString();
        String hygis = hygi.toString();
        String tastes = taste.toString();
        String exps = exp.toString();

        databaseReference.child("service").setValue(rService + servs);
        databaseReference.child("quality").setValue(rQuality + quals);
        databaseReference.child("hygiene").setValue(rHygiene + hygis);
        databaseReference.child("taste").setValue(rTaste + tastes);
        databaseReference.child("exp").setValue(rExp + exps);
        Toast.makeText(getApplicationContext(),"Rating Added Sucessfully",Toast.LENGTH_SHORT).show();
    }
}
