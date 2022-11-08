package com.example.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class speechToText extends AppCompatActivity {
    protected static final int RESULT_SPEECH = 1;
    private ImageButton btnSpeak;
    private TextView tvText;
    Button copyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);
        tvText = findViewById(R.id.tvTest);
        btnSpeak = findViewById(R.id.btnSpeak);
        copyText=findViewById(R.id.copyBtn);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    tvText.setText("");
                }catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(),"Your device doesnt support Speech to Text ",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        });

        copyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = tvText.getText().toString();
                copyToClipBoard(str);
            }
        });
    }

    private void copyToClipBoard(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Copied text", text);
        clipboardManager.setPrimaryClip(clipData);

//        let user know data save on clipBoard Successfully.
        Toast.makeText(this, "Copied to clipBoard", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SPEECH:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tvText.setText((text.get(0)));
                }
                break;
        }
    }
}