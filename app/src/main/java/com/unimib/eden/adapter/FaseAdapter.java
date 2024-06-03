package com.unimib.eden.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimib.eden.R;
import com.unimib.eden.model.Fase;
import com.unimib.eden.utils.ConvertIntMonthToString;

import java.util.List;

/**
 * Classe FaseAdapter che visualizza i dati delle fasi in una RecyclerView.
 *
 * @author Alice Hoa Galli
 */
public class FaseAdapter extends RecyclerView.Adapter<FaseAdapter.FaseViewHolder> {
    private int itemLayout = R.layout.fase_details_item;;

    private int  operationCode;
    List<Fase> fasiList;

    /**
     * Costruttore dell'adapter FaseAdapter.
     *
     * @param fasiList  Lista delle fasi da visualizzare.
     */
    public FaseAdapter(List<Fase> fasiList) {
        this.fasiList = fasiList;
    }

    public FaseAdapter(List<Fase> fasiList, int itemLayout) {
        this.fasiList = fasiList;
        this.itemLayout = itemLayout;
    }


    @NonNull
    @Override
    public FaseAdapter.FaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.fase_details_item, parent, false);

        return new FaseAdapter.FaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaseAdapter.FaseViewHolder faseViewHolder, @SuppressLint("RecyclerView") int position) {

        // Create an instance of the ChildItem
        // class for the given position

        Fase fase = fasiList.get(position);
        faseViewHolder.bind(fase, position);


    }

    @Override
    public int getItemCount() {
        if (fasiList != null) {
            return fasiList.size();
        }
        return 0;
    }

    public void update(List<Fase> fasiList) {
        if (this.fasiList != null) {
            this.fasiList.clear();
            this.fasiList.addAll(fasiList);
            //this.operationCode = operationCode;
            notifyDataSetChanged();
        }
    }

    /**
     * Classe FaseViewHolder per il ViewHolder di ogni elemento della RecyclerView.
     */
    class FaseViewHolder extends RecyclerView.ViewHolder {

        private final TextView getNomeFaseLabel;
        private final TextView nomeFaseDetails;
        private final TextView inizioFaseDetails;
        private final TextView durataFaseDetails;
        private final TextView descrizioneFaseDetails;
        public FaseViewHolder(View view) {
            super(view);
            this.getNomeFaseLabel = view.findViewById(R.id.nomeFaseLabel);
            this.nomeFaseDetails = view.findViewById(R.id.nomeFaseDetails);
            this.inizioFaseDetails = view.findViewById(R.id.inizioFaseDetails);
            this.durataFaseDetails = view.findViewById(R.id.durataFaseDetails);
            this.descrizioneFaseDetails = view.findViewById(R.id.descrizioneFaseDetails);


        }

        /**
         * Metodo bind che associa i dati della fase al ViewHolder.
         * @param fase  La fase da visualizzare.
         * @param pos   La posizione della fase da visualizzare all'interno della lista delle fasi.
         */
        public void bind(Fase fase, int pos) {
            this.getNomeFaseLabel.setText("Fase " + pos + ":");
            this.nomeFaseDetails.setText(fase.getNomeFase());
            this.inizioFaseDetails.setText(ConvertIntMonthToString.getMese(fase.getInizioFase()));
            this.durataFaseDetails.setText(String.valueOf(fase.getDurataFase()));
            this.descrizioneFaseDetails.setText(fase.getDescrizione());
        }
    }

}
