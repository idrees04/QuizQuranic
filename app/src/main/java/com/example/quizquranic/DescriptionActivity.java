package com.example.quizquranic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DescriptionActivity extends AppCompatActivity {

    TextView txtWord_, txtRootWord_, txtMeaning_, txtExplanation_;
    Button btnBack_;
    ProgressBar pr_Description_;

    String Word = " ";
    String rootWord = "Root word: ";
    String meaning = "Meaning: ";
    String explanation = "Description: ";

    String strWord = "";
    String strRootWord = "";
    String strMeaning = "";
    String strExplanation = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        pr_Description_ = findViewById(R.id.pr_Description);
        //   progressBar_.setVisibility(View.GONE);
        //     pr_Description_.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        Bundle bundle1 = intent.getExtras();

        txtWord_ = findViewById(R.id.txtWord);
        txtRootWord_ = findViewById(R.id.txtRootWord);
        txtMeaning_ = findViewById(R.id.txtMeaning);
        txtExplanation_ = findViewById(R.id.txtExplanation);
        btnBack_ = findViewById(R.id.btnBack);


        strWord = bundle1.getString("Word");
        if (strWord == null) {
            txtWord_.setVisibility(View.GONE);
        } else {
            txtWord_.setText(Word.concat(strWord));
        }
        strRootWord = bundle1.getString("RootWord");
        if (strRootWord == null) {
            txtRootWord_.setVisibility(View.GONE);
        } else {
            txtRootWord_.setText(rootWord.concat(strRootWord));
        }
        strMeaning = bundle1.getString("Meaning");
        if (strMeaning == null) {
            txtMeaning_.setVisibility(View.GONE);
        } else {
            txtMeaning_.setText(meaning.concat(strMeaning));
        }
        strExplanation = bundle1.getString("Explanation");
        if (strExplanation == null) {
            txtExplanation_.setVisibility(View.GONE);
        } else {
            txtExplanation_.setText(explanation.concat(strExplanation));
        }

        btnBack_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
