package com.isil.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {

    Button btnRegistrar;
    EditText etNombreRegistro, etCorreoRegistro, etContrasenaRegistro;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        etNombreRegistro = findViewById(R.id.etNombreRegistro);
        etCorreoRegistro = findViewById(R.id.etCorreoRegistro);
        etContrasenaRegistro = findViewById(R.id.etContrasenaRegistro);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreUsuario = etNombreRegistro.getText().toString().trim();
                String correoUsuario = etCorreoRegistro.getText().toString().trim();
                String contrasenaUsuario = etContrasenaRegistro.getText().toString().trim();

                if(nombreUsuario.isEmpty() && correoUsuario.isEmpty() && contrasenaUsuario.isEmpty()){
                    Toast.makeText(RegistrarActivity.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                }else{
                    registrarUsuario(nombreUsuario, correoUsuario, contrasenaUsuario);
                }
            }
        });
    }

    private void registrarUsuario(String nombreUsuario, String correoUsuario, String contrasenaUsuario) {
        mAuth.createUserWithEmailAndPassword(correoUsuario, contrasenaUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();

                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("etNombreRegistro", nombreUsuario);
                map.put("etCorreoRegistro", correoUsuario);
                map.put("etContrasenaRegistro", contrasenaUsuario);

                mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        startActivity(new Intent(RegistrarActivity.this, MainActivity.class));
                        Toast.makeText(RegistrarActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistrarActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrarActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });

    }
}