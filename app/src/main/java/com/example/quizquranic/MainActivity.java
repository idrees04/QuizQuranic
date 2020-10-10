package com.example.quizquranic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtArabicWord1_, txtA_quiz_, txtB_quiz_, txtC_quiz_, txtD_quiz_, txtUser_;
    TextView txtArabicWord2_, txtRootWord_, txtMeaning_, txtExplanation_;
    TextView txtInternetError_, txtLogout_;
    Button btnA_quiz_, btnB_quiz_, btnC_quiz_, btnD_quiz_, btnNext_;
    ProgressBar progressBar_;
    LinearLayout llDescription_;


    String meaning = "Meaning: ";
    String rootWord = "Root word: ";
    String explanation = "Description: ";
    String exp = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUser_ = findViewById(R.id.txtUser);
        txtArabicWord1_ = findViewById(R.id.txtArabicWord1);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {

            final User user = SharedPrefManager.getInstance(this).getUser();


            llDescription_ = findViewById(R.id.llDescription);
            progressBar_ = findViewById(R.id.progressBar);
            txtInternetError_ = findViewById(R.id.txtArabicWord2);
            txtA_quiz_ = findViewById(R.id.txtA_quiz);
            txtB_quiz_ = findViewById(R.id.txtB_quiz);
            txtC_quiz_ = findViewById(R.id.txtC_quiz);
            txtD_quiz_ = findViewById(R.id.txtD_quiz);

            btnA_quiz_ = findViewById(R.id.btnA_quiz);
            btnB_quiz_ = findViewById(R.id.btnB_quiz);
            btnC_quiz_ = findViewById(R.id.btnC_quiz);
            btnD_quiz_ = findViewById(R.id.btnD_quiz);

            txtLogout_ = findViewById(R.id.txtLogout);
            //   int a= Integer.parseInt(btnA_quiz_.toString());


            //      errorMessage = findViewById(R.id.textViewErrorMessage);

            txtArabicWord2_ = findViewById(R.id.txtArabicWord2);
            txtRootWord_ = findViewById(R.id.txtRootWord);
            txtMeaning_ = findViewById(R.id.txtMeaning);
            txtExplanation_ = findViewById(R.id.txtExplanation);
            btnNext_ = findViewById(R.id.btnNext);


            final String token_ = user.getToken_();

            txtUser_.setText(user.getPreferredName_());


            txtLogout_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                }
            });

//            txtUser_.setText(user.getPreferredName_());
//            txtA_quiz_.setText(user.getPreferredName_());
//            txtB_quiz_.setText("user.getToken_()");
//            //     txtB_quiz_.setText(user.getToken_());
//            txtC_quiz_.setText(String.valueOf(user.getUserId_()));
//            //      errorMessage.setText(isSuccess_.);
//            txtD_quiz_.setText(user.getErrorMessage_());
//            txtArabicWord1_.setOnClickListener(this);

            ////////////////////////

            //if everything is fine
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.QUIZ,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBar_.setVisibility(View.GONE);

                            String strArabicWord2 = "";
                            String strRootWord = "";
                            String strMeaning = "";
                            String strExplanation = "";

                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);
                                String QuestionId_ = obj.getString("QuestionId");
                                txtArabicWord1_.setText(obj.getString("Question"));
                                txtA_quiz_.setText(obj.getString("OptionOne"));
                                txtB_quiz_.setText(obj.getString("OptionTwo"));
                                txtC_quiz_.setText(obj.getString("OptionThree"));
                                txtD_quiz_.setText(obj.getString("OptionFour"));
                                final int ans = obj.getInt("Answer");

                                JSONArray myListsAll = obj.getJSONArray("QuestionWord");


                                btnA_quiz_.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        {
                                            int click = 1;
                                            if (ans == click) {
                                                llDescription_.setVisibility(View.VISIBLE);
                                                btnA_quiz_.setBackgroundDrawable(getResources().getDrawable(R.color.colorButtonGreen));
                                            } else {
                                                btnA_quiz_.setBackgroundDrawable(getResources().getDrawable(R.color.colorButtonRed));
                                            }

//                                            for (int j = 1; j < arr.length; ) {
//                                                    if (ans == j) {
//                                                        llDescription_.setVisibility(View.VISIBLE);
//                                                        btnA_quiz_.setBackgroundDrawable(getResources().getDrawable(R.color.colorButtonGreen));
//                                                        break;
//                                                    } else {
//                                                        btnA_quiz_.setBackgroundDrawable(getResources().getDrawable(R.color.colorButtonRed));
//                                                        j++;
//                                                    }
//                                                    if (ans == j)
//                                                        break;
//                                            }
                                        }
                                    }
                                });
                                btnB_quiz_.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        int click = 2;
                                        if (ans == click) {
                                            llDescription_.setVisibility(View.VISIBLE);
                                            btnB_quiz_.setBackgroundDrawable(getResources().getDrawable(R.color.colorButtonGreen));
                                        } else {
                                            btnB_quiz_.setBackgroundDrawable(getResources().getDrawable(R.color.colorButtonRed));
                                        }
                                    }
                                });
                                btnC_quiz_.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        int click = 3;
                                        if (ans == click) {
                                            llDescription_.setVisibility(View.VISIBLE);
                                            btnC_quiz_.setBackgroundDrawable(getResources().getDrawable(R.color.colorButtonGreen));
                                        } else {
                                            btnC_quiz_.setBackgroundDrawable(getResources().getDrawable(R.color.colorButtonRed));
                                        }
                                    }
                                });
                                btnD_quiz_.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        int click = 4;
                                        if (ans == click) {
                                            llDescription_.setVisibility(View.VISIBLE);
                                            btnD_quiz_.setBackgroundDrawable(getResources().getDrawable(R.color.colorButtonGreen));
                                        } else {
                                            btnD_quiz_.setBackgroundDrawable(getResources().getDrawable(R.color.colorButtonRed));
                                        }
                                    }
                                });

                                for (int i = 0; i < myListsAll.length(); i++) {
                                    JSONObject jsonobject = (JSONObject) myListsAll.get(i);

                                    strArabicWord2 = jsonobject.getString("Word");
                                    strRootWord = jsonobject.getString("RootWord");
                                    strMeaning = jsonobject.getString("Meaning");
                                    strExplanation = jsonobject.getString("Explanation");

                                    int id = jsonobject.getInt("UserId");
                                    boolean IsSuccess = jsonobject.getBoolean("IsSuccess");
                                    String error = jsonobject.getString("ErrorMessage");
                                }

                                btnNext_.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    }
                                });

                                txtArabicWord2_.setText(exp.concat(strArabicWord2));
                                txtRootWord_.setText(rootWord.concat(strRootWord));
                                txtMeaning_.setText(meaning.concat(strMeaning));
                                txtExplanation_.setText(explanation.concat(strExplanation));

                                int id1 = obj.getInt("UserId");
                                boolean IsSuccess1 = obj.getBoolean("IsSuccess");
                                String error1 = obj.getString("ErrorMessage");

//                                txtArabicWord2_.setText(obj.getString("Word"));
//                                txtRootWord_.setText(obj.getString("RootWord"));
//                                txtMeaning_.setText(obj.getString("Meaning"));
//                                txtExplanation_.setText(obj.getString("Explanation"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("TAG", error.toString());
                            if (error instanceof NoConnectionError) {
                                Toast.makeText(getApplicationContext(), "Unable to connect to the server! Please ensure your internet is working!", Toast.LENGTH_LONG).show();
                                txtInternetError_.setText("Unable to connect to the server! Please ensure your internet is working!");
                            } else {
                                NetworkResponse response = error.networkResponse;
                                int res = response.statusCode;
                                switch (res) {
                                    case 401:
                                        Toast.makeText(getApplicationContext(), "Unauthorize", Toast.LENGTH_LONG).show();
                                        break;
                                    case 404:
                                        Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_LONG).show();
                                        break;
                                    case 500:
                                        Toast.makeText(getApplicationContext(), "internal server error", Toast.LENGTH_LONG).show();
                                        break;
                                    case 200:
                                        Toast.makeText(getApplicationContext(), "successful", Toast.LENGTH_LONG).show();
                                        break;
                                    case 201:
                                        Toast.makeText(getApplicationContext(), "successfull creted", Toast.LENGTH_LONG).show();
                                        break;
                                    case 405:
                                        Toast.makeText(getApplicationContext(), "Not Allowed is an HTTP response status code", Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                                        //  Toast.makeText(getApplicationContext(), "Another Error", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token_);
                    return headers;
                }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


            ////////////////////////


        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
