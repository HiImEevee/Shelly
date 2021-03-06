package com.shelly.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shelly.R;

public class WelcomeActivity extends AppCompatActivity {

    //Views
    Button mLogInBtn;
    Button mSignUpBtn;
    TextView mWelcomeTitleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Views Binding
        mLogInBtn = findViewById(R.id.LogInBtn);
        mSignUpBtn = findViewById(R.id.SignUpBtn);
        mWelcomeTitleTV = findViewById(R.id.WelcomeTextView);

        //Implementing Functionalities
        mWelcomeTitleTV.setText(Html.fromHtml(getResources().getString(R.string.welcoming_text)));
        mLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeActivity.this, LogInActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}
