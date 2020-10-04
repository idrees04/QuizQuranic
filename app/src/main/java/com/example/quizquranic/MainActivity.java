package com.example.quizquranic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView userName,token_,id,isSueccess,errorMessage;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            userName = findViewById(R.id.textViewUsername);
            token_ = findViewById(R.id.textViewtoken);
            id = findViewById(R.id.textViewId);
            isSueccess = findViewById(R.id.textViewIsSuccess);
            errorMessage = findViewById(R.id.textViewErrorMessage);
            btnLogout = findViewById(R.id.buttonLogout);
            User user = SharedPrefManager.getInstance(this).getUser();

            userName.setText(user.getPreferredName_());
            token_.setText(user.getToken_());
            id.setText(String.valueOf(user.getUserId_()));
      //      errorMessage.setText(isSuccess_.);
            errorMessage.setText(user.getErrorMessage_());


            btnLogout.setOnClickListener(this);
        }
        else{
            Intent  intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void onClick(View view){
        if(view.equals(btnLogout)){
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }
    }

}
