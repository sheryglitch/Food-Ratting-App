package com.example.projectmcclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText searchText,searchBar;
TextView appName,appTips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchText = (EditText) findViewById(R.id.searchEditText);
        appName = (TextView) findViewById(R.id.restName);
        appTips = (TextView) findViewById(R.id.textView3);
        String text = "<font color=#ff0000>F</font><font color=#ffde39>O</font><font color=#70202b>O</font><font color=#d9891f>D</font><font color=#256038> H</font><font color=#70202b>A</font><font color=#dbb928>W</font><font color=#ff0000>K</font>";
        appName.setText(Html.fromHtml(text));
        searchText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    onSearch(v);
                    return true;
                }
                return false;
            }
        });
            /* Thread Code */
        final String[] dynText={"Always Choose The Best","Learn From Others Experiences","Lets Not Waste Time In Research","Help Others To Decide!"};
        Thread t=new Thread(){
            int count = 0;
            @Override
            public void run(){

                while(!isInterrupted()){

                    try {
                        Thread.sleep(3000);

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                               if(count == 0){
                                    appTips.setTextColor(Color.rgb(37,96,56));
                                }
                                else if(count == 1){
                                    appTips.setTextColor(Color.rgb(219,185,40));
                                }
                                else if(count == 2){
                                    appTips.setTextColor(Color.rgb(112,32,43));
                                }
                               else if(count == 3){
                                   appTips.setTextColor(Color.rgb(255,0,0));
                               }
                                appTips.setText(String.valueOf(dynText[count]));
                                if(count<3) {
                                    count++;
                                }
                                else if(count == 3)
                                {
                                    count=0;
                                }

                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        t.start();
        /* Thread End*/
    }

    public void onBurger(View v)
    {
        Intent intent = new Intent(MainActivity.this,ListItems.class);
        intent.putExtra("from","burger");
        startActivity(intent);
    }
    public void onPizza(View v)
    {
        Intent intent = new Intent(MainActivity.this,ListItems.class);
        intent.putExtra("from","pizza");
        startActivity(intent);
    }
    public void onSteak(View v)
    {
        Intent intent = new Intent(MainActivity.this,ListItems.class);
        intent.putExtra("from","steak");
        startActivity(intent);
    }
    public void onBrownie(View v)
    {
        Intent intent = new Intent(MainActivity.this,ListItems.class);
        intent.putExtra("from","brownie");
        startActivity(intent);
    }

    public void onSearch(View v)
    {

        String searchInput = searchText.getText().toString().toLowerCase().trim();
        if(searchInput == null || searchInput.isEmpty()) {
            Toast.makeText(v.getContext(),"Enter Something",Toast.LENGTH_SHORT).show();

        }
        else{
        Intent intent = new Intent(MainActivity.this,ListItems.class);
        intent.putExtra("from",searchInput);
        startActivity(intent);
        }
    }
    public void onAdmin(View v){
        Intent intent = new Intent(MainActivity.this,adminLogin.class);
        startActivity(intent);
    }

}
