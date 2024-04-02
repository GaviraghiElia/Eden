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

import java.util.List;

public class FaseAdapter extends RecyclerView.Adapter<FaseAdapter.FaseViewHolder> {
    private int itemLayout = R.layout.fase_details_item;;

    private int  operationCode;
    List<Fase> fasiList;

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

    public String getMese(int mese) {
        String nomeMese = "";
        switch (mese) {
            case 1:
                nomeMese = "Gennaio";
                break;
            case 2:
                nomeMese = "Febbraio";
                break;
            case 3:
                nomeMese = "Marzo";
                break;
            case 4:
                nomeMese = "Aprile";
                break;
            case 5:
                nomeMese = "Maggio";
                break;
            case 6:
                nomeMese = "Giugno";
                break;
            case 7:
                nomeMese = "Luglio";
                break;
            case 8:
                nomeMese = "Agosto";
                break;
            case 9:
                nomeMese = "Settembre";
                break;
            case 10:
                nomeMese = "Ottobre";
                break;
            case 11:
                nomeMese = "Novembre";
                break;
            case 12:
                nomeMese = "Dicembre";
                break;
        }
        return nomeMese;
    }

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

        public void bind(Fase fase, int pos) {
            this.getNomeFaseLabel.setText("Fase " + pos + ":");
            this.nomeFaseDetails.setText(fase.getNomeFase());
            this.inizioFaseDetails.setText(getMese(fase.getInizioFase()));
            this.durataFaseDetails.setText(String.valueOf(fase.getDurataFase()));
            this.descrizioneFaseDetails.setText(fase.getDescrizione());
        }
    }

}
