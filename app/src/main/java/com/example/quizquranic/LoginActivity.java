package com.example.quizquranic;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    public static final String KEY_EMail="Email";
    public static final String KEY_PASSWORD="Password";
    private EditText etName, etPassword;
    private ProgressBar progressBar;
    private Button btnLogin_;
    private TextView txtInternetError_;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        progressBar = findViewById(R.id.progressBar);
        etName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etUserPassword);
        btnLogin_=findViewById(R.id.btnLogin);
        txtInternetError_=findViewById(R.id.txtInternetError);

        //calling the method userLogin() for login the user
        btnLogin_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        //if user presses on textview not register calling RegisterActivity
        findViewById(R.id.tvRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void userLogin() {
        //first getting the values
        email=etName.getText().toString().trim();
        password=etPassword.getText().toString().trim();

        final String Email = etName.getText().toString();
        final String Password = etPassword.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(Email)) {
            etName.setError("Please enter your username");
            etName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String name = obj.getString("PreferredName");
                            String token = obj.getString("Token");
                            int id = obj.getInt("UserId");
                            boolean IsSuccess = obj.getBoolean("IsSuccess");
                            String error = obj.getString("ErrorMessage");

                            if(IsSuccess==false)
                            {
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //creating a new user object
                                User user = new User(name, token, id, IsSuccess, error);
                                user.setPreferredName_(name);
                                user.setToken_(token);
                                user.setUserId_(id);
                                user.setSuccess_(IsSuccess);
                                user.setErrorMessage_(error);

                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                //starting the profile activity
                                finish();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                //       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
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
                        if(error instanceof NoConnectionError)
                         {
                            Toast.makeText(getApplicationContext(), "Unable to connect to the server! Please ensure your internet is working!", Toast.LENGTH_LONG).show();
                            txtInternetError_.setText("Unable to connect to the server! Please ensure your internet is working!");
                            btnLogin_.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    userLogin();
                                }
                            });
                        }
                        else {
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
                                default:
                                    Toast.makeText(getApplicationContext(), "Another Error", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_PASSWORD,password );
                params.put(KEY_EMail,email);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}