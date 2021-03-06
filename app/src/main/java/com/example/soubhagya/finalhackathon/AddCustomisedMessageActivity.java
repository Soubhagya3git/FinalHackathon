package com.example.soubhagya.finalhackathon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by soubhagya on 2/4/17.
 */

public class AddCustomisedMessageActivity extends AppCompatActivity {

    private static final String EXTRA_PHONE_NUM = "com.example.soubhagya.finalhackathon.phoneNum";

    public static Intent newIntent(Context context, String phoneNum){
        Intent i = new Intent(context, AddCustomisedMessageActivity.class);
        i.putExtra(EXTRA_PHONE_NUM, phoneNum);
        return i;
    }

    private DataStash dataStash = DataStash.getDataStash();

    private String userPhoneNumber;
    private EditText editMessage;
    private Button submitButton;
    private String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customised_message);

        userPhoneNumber = getSharedPreferences(dataStash.sharedPreferences, MODE_PRIVATE)
                .getString("PHONE_NUMBER", "");

        editMessage = (EditText)findViewById(R.id.editText);
        editMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                message = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        submitButton = (Button)findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataStash.fireBase
                        .child("USERS")
                        .child(userPhoneNumber)
                        .child("message")
                        .setValue(message)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Intent i = AddPinActivity.newIntent(AddCustomisedMessageActivity.this, userPhoneNumber);
                                startActivity(i);
                                finish();

                            }
                        });
            }
        });
    }
}
