package com.isil.test.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.isil.test.R;
import com.isil.test.model.Mascota;

public class MascotaAdapter extends FirestoreRecyclerAdapter<Mascota, MascotaAdapter.ViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MascotaAdapter(@NonNull FirestoreRecyclerOptions<Mascota> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Mascota Mascota) {
        viewHolder.nombre.setText(Mascota.getNombre());
        viewHolder.edad.setText(Mascota.getEdad());
        viewHolder.color.setText(Mascota.getColor());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_mascota_single, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, edad, color;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre);
            edad = itemView.findViewById(R.id.edad);
            color = itemView.findViewById(R.id.color);
        }
    }
}
