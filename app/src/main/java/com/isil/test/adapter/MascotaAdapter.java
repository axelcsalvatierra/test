package com.isil.test.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isil.test.CrearMascotaActivity;
import com.isil.test.R;
import com.isil.test.model.Mascota;

public class MascotaAdapter extends FirestoreRecyclerAdapter<Mascota, MascotaAdapter.ViewHolder> {

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MascotaAdapter(@NonNull FirestoreRecyclerOptions<Mascota> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Mascota Mascota) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        viewHolder.nombre.setText(Mascota.getNombre());
        viewHolder.edad.setText(Mascota.getEdad());
        viewHolder.color.setText(Mascota.getColor());

        viewHolder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, CrearMascotaActivity.class);
                i.putExtra("id_mascota", id);
                activity.startActivity(i);
            }
        });

        viewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMascota(id);
            }
        });
    }

    private void deleteMascota(String id) {
        mFirestore.collection("mascota").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_mascota_single, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, edad, color;
        ImageView btnEliminar, btnEditar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre);
            edad = itemView.findViewById(R.id.edad);
            color = itemView.findViewById(R.id.color);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnEditar = itemView.findViewById(R.id.btnEditar);

        }
    }
}
