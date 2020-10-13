package com.example.ejemploandroidphpmysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
   String LOGIN_URL="http://pruebasdeprogra.atwebpages.com/loginandroid.php";
   String LOGIN_SUCCESS="success";
EditText txtusuario, txtpass;
Button aceptar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtusuario=findViewById(R.id.txtpass);
        txtpass=findViewById(R.id.txtpass);
        aceptar=findViewById(R.id.button);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });



    }



    private void login(){

        //Getting values from edit texts
        final String usuario = txtusuario.getText().toString().trim();
        final String password = txtpass.getText().toString().trim();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(response.equalsIgnoreCase(LOGIN_SUCCESS)){
                            //Starting profile activity
                            Intent intent = new Intent(login.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(login.this, "Usuario no autorizado!", Toast.LENGTH_LONG).show();
                            Log.e("volley", response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("elusario", usuario);
                params.put("elpassword", password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}