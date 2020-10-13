package com.example.ejemploandroidphpmysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText nombre, telefono;
    Button guardar;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre=findViewById(R.id.txtNombre);
        telefono=findViewById(R.id.txtTelefono);
        guardar=findViewById(R.id.guardar);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserta();

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                inputMethodManager.hideSoftInputFromWindow(telefono.getWindowToken(), 0);
            }

            private void inserta() {


                String URL="http://pruebasdeprogra.atwebpages.com/guarda.php";

                final String elnombre=nombre.getText().toString();
                final String eltelefono=telefono.getText().toString();


                if(elnombre.isEmpty()){
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                    v.vibrate(100);

                    return;
                }else if(eltelefono.isEmpty()){
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                    return;

                }else{
                    // Creating string request with post method.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String ServerResponse) {
                                    try{
                                        JSONObject jsonObject = new JSONObject(ServerResponse);
                                   //podemos recoger variables de php y mostrarlas en java
                                        String Name = jsonObject.getString("nombre");

                                        String eltelefono=jsonObject.getString("tel");


                                        Toast.makeText(MainActivity.this, "guardé el nombre de"+eltelefono, Toast.LENGTH_SHORT).show();





                                        if(jsonObject.getString("message").equals("Data Successfully")){

                                            Toast.makeText(MainActivity.this, "Registro Insertado!", Toast.LENGTH_SHORT).show();
                                        }else{

                                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

                                        }
                                    }catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();

                                    // Hiding the progress dialog after all task complete.

                                    // Showing error message if something goes wrong.
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            // Adding All values to Params.
                            params.put("nombre", elnombre);
                            params.put("tel", eltelefono);
                            return params;
                        }

                    };

                    // Creating RequestQueue.
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                    // Adding the StringRequest object into requestQueue.
                    requestQueue.add(stringRequest);

                }



            }
        });


    }


    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void closeKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }


}