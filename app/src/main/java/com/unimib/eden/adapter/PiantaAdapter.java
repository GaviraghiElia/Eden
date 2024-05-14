package com.unimib.eden.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimib.eden.R;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.ui.inserimentoProdotto.InserimentoProdottoActivity;
import com.unimib.eden.ui.piantaDetails.PiantaDetailsActivity;
import com.unimib.eden.ui.searchPianta.SearchPiantaActivity;
import com.unimib.eden.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe PiantaAdapter che visualizza i dati delle piante in una RecyclerView.
 *
 * @author Alice Hoa Galli
 */
public class PiantaAdapter extends RecyclerView.Adapter<PiantaAdapter.PiantaViewHolder> {

    private final static String TAG = "PiantaAdapter";
    private List<Pianta> piantaList;
    private int itemLayout = R.layout.search_pianta_item;;

    /**
     * Intero per gestire la destinazione corretta in seguito al onClick di una pianta
     */
    private int  operationCode;

    /**
     * Costruttore dell'adapter PiantaAdapter.
     *
     * @param piantaList    Lista di piante da visualizzare.
     */
    public PiantaAdapter(List<Pianta> piantaList) {
        this.piantaList = piantaList;
    }

    public PiantaAdapter(List<Pianta> piantaList, int itemLayout) {
        this.piantaList = piantaList;
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public PiantaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.search_pianta_item, parent, false);

        return new PiantaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PiantaViewHolder piantaViewHolder, @SuppressLint("RecyclerView") int position) {

        // Create an instance of the ChildItem
        // class for the given position

        Pianta pianta = piantaList.get(position);
        piantaViewHolder.bind(pianta);

        piantaViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                switch (operationCode) {
                    case Constants.SEARCH_PIANTA_OPERATION_CODE:
                        intent = new Intent(v.getContext(), PiantaDetailsActivity.class);
                        intent.putExtra("pianta", piantaList.get(position));
                        intent.putExtra("operationCode", Constants.SEARCH_PIANTA_OPERATION_CODE);
                        v.getContext().startActivity(intent);
                        break;
                    case Constants.CREATE_COLTURA_OPERATION_CODE:
                        intent = new Intent(v.getContext(), PiantaDetailsActivity.class);
                        intent.putExtra("pianta", piantaList.get(position));
                        intent.putExtra("operationCode", Constants.CREATE_COLTURA_OPERATION_CODE);
                        v.getContext().startActivity(intent);
                        break;
                    case Constants.CREATE_PRODOTTO_OPERATION_CODE:
                        intent = new Intent(v.getContext(), InserimentoProdottoActivity.class);
                        intent.putExtra("pianta", piantaList.get(position));
                        intent.putExtra("operationCode", Constants.CREATE_PRODOTTO_OPERATION_CODE);
                        ((SearchPiantaActivity) v.getContext()).setResult(Activity.RESULT_OK, intent);
                        ((SearchPiantaActivity) v.getContext()).finish();
                        break;
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        if (piantaList != null) {
            return piantaList.size();
        }
        return 0;
    }

    public void update(List<Pianta> piantaList, int operationCode) {
        if (this.piantaList != null) {
            this.piantaList.clear();
            this.piantaList.addAll(piantaList);
            this.operationCode = operationCode;
            notifyDataSetChanged();
        }
    }

    /**
     * Classe PiantaViewHolder per il ViewHolder di ogni elemento della RecyclerView.
     */
    class PiantaViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewPiantaName;
        private ImageView imageViewPianta;
        public PiantaViewHolder(View itemView) {
            super(itemView);
            this.textViewPiantaName = itemView.findViewById(R.id.piantaName);
            this.imageViewPianta = itemView.findViewById(R.id.imageViewPianta);


        }

        /**
         * Metodo bind che associa i dati della pianta al ViewHolder.
         * @param pianta    La pianta da visualizzare.
         */
        public void bind(Pianta pianta) {
            this.textViewPiantaName.setText(pianta.getNome());
        }
    }
}
