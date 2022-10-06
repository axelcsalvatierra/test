package com.isil.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CrearMascotaFragment extends DialogFragment {

    Button btnAdd;
    EditText etNombre, etColor, etEdad;
    private FirebaseFirestore mFirestore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crear_mascota, container, false);
        mFirestore = FirebaseFirestore.getInstance();

        etNombre = v.findViewById(R.id.etNombre);
        etColor = v.findViewById(R.id.etColor);
        etEdad = v.findViewById(R.id.etEdad);
        btnAdd = v.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etNombre.getText().toString().trim();
                String color = etColor.getText().toString().trim();
                String edad = etEdad.getText().toString().trim();

                if (nombre.isEmpty() && color.isEmpty() && edad.isEmpty()){
                    Toast.makeText(getContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
                }else{
                    postMascota(nombre, color, edad);
                }

            }
        });

        return v;
    }

    private void postMascota(String nombre, String color, String edad) {

        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombre);
        map.put("color", color);
        map.put("edad", edad);

        mFirestore.collection("mascota").add(map).addOnSuccessListener(documentReference -> {
            Toast.makeText(getContext(), "Creado exitosamente", Toast.LENGTH_SHORT).show();
            getDialog().dismiss();
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}