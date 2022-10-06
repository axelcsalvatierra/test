package com.isil.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.isil.test.adapter.MascotaAdapter;
import com.isil.test.model.Mascota;

public class MainActivity extends AppCompatActivity {

    Button btn_Agregar;
    Button btnAgregarFragment;

    RecyclerView mRecycler;
    MascotaAdapter mAdapter;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.RvSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("mascota");

        FirestoreRecyclerOptions<Mascota> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Mascota>().setQuery(query, Mascota.class).build();

        mAdapter = new MascotaAdapter(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        btn_Agregar = findViewById(R.id.btn_Agregar);
        btnAgregarFragment = findViewById(R.id.btnAgregarFragment);

        btn_Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CrearMascotaActivity.class));
            }
        });

        btnAgregarFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearMascotaFragment fm = new CrearMascotaFragment();
                fm.show(getSupportFragmentManager(), "Navegar a fragment");

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}