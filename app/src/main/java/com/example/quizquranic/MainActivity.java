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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtArabicWord1_, txtA_quiz_, txtB_quiz_, txtC_quiz_, txtD_quiz_, txtUser_;
    TextView txtInternetError_, txtLogout_;
    Button btnA_quiz_, btnB_quiz_, btnC_quiz_, btnD_quiz_, btnNext_;
    ProgressBar progressBar_;
    LinearLayout llDescription_;

    String strArabicWord2 = "";
    String strRootWord = "";
    String strMeaning = "";
    String strExplanation = "";

    int questionId;
    boolean isSuccess;
    boolean answer;
    String error;


    int clickCounter = 0;

    public int getClickCounter() {
        return clickCounter;
    }

    public void setClickCounter(int clickCounter) {
        this.clickCounter = clickCounter;
    }

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
            txtInternetError_ = findViewById(R.id.txtWord);
            txtA_quiz_ = findViewById(R.id.txtA_quiz);
            txtB_quiz_ = findViewById(R.id.txtB_quiz);
            txtC_quiz_ = findViewById(R.id.txtC_quiz);
            txtD_quiz_ = findViewById(R.id.txtD_quiz);

            btnA_quiz_ = findViewById(R.id.btnA_quiz);
            btnB_quiz_ = findViewById(R.id.btnB_quiz);
            btnC_quiz_ = findViewById(R.id.btnC_quiz);
            btnD_quiz_ = findViewById(R.id.btnD_quiz);

            btnNext_ = findViewById(R.id.btnNext);
            txtLogout_ = findViewById(R.id.txtLogout);


            final String token_ = user.getToken_();
            final int userId = user.getUserId_();

            txtUser_.setText(user.getPreferredName_());

            txtLogout_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                }
            });

            ////////////////////////

            //if everything is fine
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.QUIZ,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //              progressBar_.setVisibility(View.VISIBLE);

                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);
                                questionId = obj.getInt("QuestionId");
                                txtArabicWord1_.setText(obj.getString("Question"));
                                txtA_quiz_.setText(obj.getString("OptionOne"));
                                txtB_quiz_.setText(obj.getString("OptionTwo"));
                                txtC_quiz_.setText(obj.getString("OptionThree"));
                                txtD_quiz_.setText(obj.getString("OptionFour"));
                                final int ans = obj.getInt("Answer");

                                JSONArray myListsAll = obj.getJSONArray("QuestionWord");
                                for (int i = 0; i < myListsAll.length(); i++) {
                                    JSONObject jsonobject = (JSONObject) myListsAll.get(i);

                                    strArabicWord2 = jsonobject.getString("Word");
                                    strRootWord = jsonobject.getString("RootWord");
                                    strMeaning = jsonobject.getString("Meaning");
                                    strExplanation = jsonobject.getString("Explanation");

                                    int userId1 = jsonobject.getInt("UserId");
                                    isSuccess = jsonobject.getBoolean("IsSuccess");
                                    error = jsonobject.getString("ErrorMessage");
                                }
                                final Bundle bundle = new Bundle();
                                bundle.putString("Word", strArabicWord2);
                                bundle.putString("RootWord", strRootWord);
                                bundle.putString("Meaning", strMeaning);
                                bundle.putString("Explanation", strExplanation);

                                btnA_quiz_.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        {
                                            clickCounter++;
                                            setClickCounter(clickCounter);
                                            int click = 1;

                                            if (ans == click) {
                                                btnA_quiz_.setText("");
                                                btnA_quiz_.setBackgroundResource(R.drawable.tick_sign);
                                                answer = clickCounter == 1;
                                                Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
                                                intent.putExtras(bundle);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            } else if (ans != click && clickCounter == 1) {
                                                btnA_quiz_.setText("");
                                                btnA_quiz_.setBackgroundResource(R.drawable.red_cross_sign);
                                                answer = false;
                                            }
                                        }
                                    }
                                });
                                btnB_quiz_.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        clickCounter++;
                                        setClickCounter(clickCounter);
                                        int click = 2;
                                        if (ans == click) {
                                            btnB_quiz_.setText("");
                                            btnB_quiz_.setBackgroundResource(R.drawable.tick_sign);
                                            answer = clickCounter == 1;
                                            Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
                                            intent.putExtras(bundle);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);

                                        } else if (ans != click && clickCounter == 1) {
                                            btnB_quiz_.setText("");
                                            btnB_quiz_.setBackgroundResource(R.drawable.red_cross_sign);
                                            answer = false;
                                        }
                                    }
                                });
                                btnC_quiz_.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        clickCounter++;
                                        setClickCounter(clickCounter);

                                        int click = 3;

                                        if (ans == click) {
                                            btnC_quiz_.setText("");
                                            btnC_quiz_.setBackgroundResource(R.drawable.tick_sign);
                                            answer = clickCounter == 1;
                                            Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
                                            intent.putExtras(bundle);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        } else if (ans != click && clickCounter == 1) {
                                            btnC_quiz_.setText("");
                                            btnC_quiz_.setBackgroundResource(R.drawable.red_cross_sign);
                                            answer = false;
                                        }
                                    }
                                });
                                btnD_quiz_.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        clickCounter++;
                                        setClickCounter(clickCounter);
                                        int click = 4;
                                        if (ans == click) {
                                            btnD_quiz_.setText("");
                                            btnD_quiz_.setBackgroundResource(R.drawable.tick_sign);
                                            answer = clickCounter == 1;
                                            Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
                                            intent.putExtras(bundle);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        } else if (ans != click && clickCounter == 1) {
                                            btnD_quiz_.setText("");
                                            btnD_quiz_.setBackgroundResource(R.drawable.red_cross_sign);
                                            answer = false;

                                        }
                                    }
                                });

                                int userId2 = obj.getInt("UserId");
                                isSuccess = obj.getBoolean("IsSuccess");
                                error = obj.getString("ErrorMessage");

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
                                //       txtInternetError_.setText("Unable to connect to the server! Please ensure your internet is working!");
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
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            ////////////////////////


            btnNext_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickCounter >= 1) {
                        //if everything is fine
                        JSONObject object = new JSONObject();
                        try {
                            object.put("QuestionId", questionId);
                            object.put("Answered", answer);
                            object.put("UserId", userId);
                            object.put("IsSuccess", isSuccess);
                            object.put("ErrorMessage", error);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLs.ANSWER,
                                object, new Response.Listener<JSONObject>() {


                            @Override
                            public void onResponse(JSONObject response) {
                                //                     progressBar.setVisibility(View.GONE);

                                try {
                                    //converting response to json object
                                    //        JSONObject obj = new JSONObject(response);
                                    boolean IsSuccess1 = response.getBoolean("IsSuccess");
                                    String error1 = response.getString("ErrorMessage");

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                    if (IsSuccess1 == false) {
                                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                                    }

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
                                            //     txtInternetError_.setText("Unable to connect to the server! Please ensure your internet is working!");
                                        } else {
                                            NetworkResponse response = error.networkResponse;
                                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                                            int res = response.statusCode;
                                            switch (res) {
                                                case 401:
                                                    Toast.makeText(getApplicationContext(), "statusCode 401 (unauthorized)", Toast.LENGTH_LONG).show();
                                                    //                          startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                    break;
                                                case 404:
                                                    Toast.makeText(getApplicationContext(), "statusCode 404 (not found)", Toast.LENGTH_LONG).show();
                                                    break;
                                                case 500:
                                                    Toast.makeText(getApplicationContext(), "statusCode 500 (internal server error)", Toast.LENGTH_LONG).show();
                                                    break;
                                                case 200:
                                                    Toast.makeText(getApplicationContext(), "statusCode 200 (successful)", Toast.LENGTH_LONG).show();
                                                    break;
                                                case 201:
                                                    Toast.makeText(getApplicationContext(), "statusCode 201 (successful created)", Toast.LENGTH_LONG).show();
                                                    break;
                                                case 405:
                                                    Toast.makeText(getApplicationContext(), "statusCode 405 (Not Allowed is an HTTP response status code)", Toast.LENGTH_LONG).show();
                                                    break;
                                                default:
                                                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                                                    //  Toast.makeText(getApplicationContext(), "Another Error", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                }) {
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headerss = new HashMap<>();
                                headerss.put("Authorization", "Bearer " + token_);
                                return headerss;
                            }

                        };

                        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
                    } else {
                        Toast.makeText(getApplicationContext(), "please choose at least one option", Toast.LENGTH_LONG).show();
                    }
                }


            });
        } else {
            finish();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}