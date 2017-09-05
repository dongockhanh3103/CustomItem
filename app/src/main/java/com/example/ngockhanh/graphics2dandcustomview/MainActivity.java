package com.example.ngockhanh.graphics2dandcustomview;

import android.graphics.Shader;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ksa";
    DynamicGridView gridView;
    Adapter adapter;
    ArrayList<Logo> listLogo;
    int arrImage[] = {R.drawable.facebook,
            R.drawable.icon2, R.drawable.image, R.drawable.google_plus,
            R.drawable.instagram,

            R.drawable.apple, R.drawable.react
    };
    String arrString[] = {"Facebook", "Game Play",
            "Safari", "Game", "Google+",
            "Instagram", "Messenger", "Zingmp3",
            "IOS", "React+", "Xamarin",
            "LOL", "TVC", "Photo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (DynamicGridView) findViewById(R.id.dynamic_grid);
        adapter = new Adapter(this,
                new ArrayList<String>(Arrays.asList(arrString)),
                3);
        gridView.setAdapter(adapter);


        gridView.setOnDragListener(new DynamicGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {
                Log.d(TAG, "drag started at position " + position);
            }

            @Override
            public void onDragPositionsChanged(int oldPosition, int newPosition) {
                Log.d(TAG, String.format("drag item position changed from %d to %d", oldPosition, newPosition));
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridView.startEditMode(position);
                return true;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, parent.getAdapter().getItem(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (gridView.isEditMode()) {
            gridView.stopEditMode();
        } else {
            super.onBackPressed();
        }
    }


}
