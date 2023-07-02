package com.example.deberjasonvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import WebService.Asynchtask;
import WebService.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void BtIngresar (View view){
        EditText Nombre = findViewById(R.id.edtEmail);
        EditText Contraseña = findViewById(R.id.edtContraseña);
        String nombre;
        String  contraseña;
        nombre=Nombre.getText().toString();
        contraseña=Contraseña.getText().toString();
        //codigo para concetar a internet
        Bundle bundle = this.getIntent().getExtras();
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("correo",nombre);
        datos.put("clave",contraseña);
        WebService ws= new WebService(" https://api.uealecpeterson.net/public/login"
                ,datos, MainActivity.this, MainActivity.this);
        ws.execute("POST");
    }
    public void btLimpiar(View view){
        EditText usuario=findViewById(R.id.edtEmail);
        EditText contraseña=findViewById(R.id.edtContraseña);
        usuario.setText("");
        contraseña.setText("");
    }
    @Override
    public void processFinish(String result) throws JSONException {


        TextView respuesta =findViewById(R.id.txtMostrar);
        JSONObject jsonrespuesta = new JSONObject(result);


        if (jsonrespuesta.has("error"))
        {
            respuesta.setText(jsonrespuesta.getString("error"));
        }
        else
        {

            Bundle b = new Bundle();
            b.putString("TOKEN", jsonrespuesta.getString("access_token"));
            Intent intent = new Intent(MainActivity.this, ListaBancos.class);
            intent.putExtras(b);
            startActivity(intent);

        }

    }
}