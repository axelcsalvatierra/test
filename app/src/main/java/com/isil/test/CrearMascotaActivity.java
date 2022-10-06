package com.isil.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CrearMascotaActivity extends AppCompatActivity {

    Button btnAdd;
    EditText etNombre, etColor, etEdad;
    private FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_mascota);

        this.setTitle("Crear Mascota");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra("id_mascota");
        mFirestore = FirebaseFirestore.getInstance();

        etNombre = findViewById(R.id.etNombre);
        etColor = findViewById(R.id.etColor);
        etEdad = findViewById(R.id.etEdad);
        btnAdd = findViewById(R.id.btnAdd);

        if(id == null || id == ""){
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombre = etNombre.getText().toString().trim();
                    String color = etColor.getText().toString().trim();
                    String edad = etEdad.getText().toString().trim();

                    if (nombre.isEmpty() && color.isEmpty() && edad.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        postMascota(nombre, color, edad);
                    }

                }
            });
        }else{
            btnAdd.setText("Update");
            getMascota(id);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombre = etNombre.getText().toString().trim();
                    String color = etColor.getText().toString().trim();
                    String edad = etEdad.getText().toString().trim();

                    if (nombre.isEmpty() && color.isEmpty() && edad.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        updateMascota(nombre, color, edad, id);
                    }
                }
            });
        }

        

    }

    private void updateMascota(String nombre, String color, String edad, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombre);
        map.put("color", color);
        map.put("edad", edad);

        mFirestore.collection("mascota").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Actualizado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void postMascota(String nombre, String color, String edad) {

        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombre);
        map.put("color", color);
        map.put("edad", edad);

        mFirestore.collection("mascota").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Creado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMascota(String id){
        mFirestore.collection("mascota").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombreMascota = documentSnapshot.getString("nombre");
                String edadMascota = documentSnapshot.getString("edad");
                String colorMascota = documentSnapshot.getString("color");

                etNombre.setText(nombreMascota);
                etColor.setText(colorMascota);
                etEdad.setText(edadMascota);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}