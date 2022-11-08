package com.example.notes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class imageToText extends AppCompatActivity {
    ImageView imageview1;
    String uriString = "";

    TextView EditTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_to_text);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        imageview1 = findViewById(R.id.imageView);
        EditTxtView=findViewById(R.id.edittext1);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(imageToText.this, ResultActivity.class);
                intent.putExtra("uri", uriString);
                startActivity(intent);
            }
        });

    }
    

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    imageview1.setImageURI(uri);
                    uriString = uri.toString();
                }
            });

}