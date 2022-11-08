package com.example.notes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import java.io.IOException;

public class ResultActivity extends AppCompatActivity {
    EditText edittext1;
    Button copyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        edittext1 = findViewById(R.id.edittext1);
        copyText=findViewById(R.id.copyBtn);

        String uriString = getIntent().getStringExtra("uri");
        Uri uri = Uri.parse(uriString);
        _extractTextFromUri(getApplicationContext(), uri);

        copyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = edittext1.getText().toString();
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

    public void  _extractTextFromUri(Context context, Uri _uri) {
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        try {
            InputImage image = InputImage.fromFilePath(context, _uri);
            Task<Text> result =
                    recognizer.process(image)
                            .addOnSuccessListener(new OnSuccessListener<Text>() {
                                @Override
                                public void onSuccess(Text visionText) {
                                    // Task completed successfully
                                    edittext1.setText(visionText.getText());
                                }
                            })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Task failed with an exception
                                        }
                                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
