package com.unimib.eden.adapter;

import android.app.Application;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimib.eden.R;
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.repository.FaseRepository;
import com.unimib.eden.repository.PiantaRepository;

import java.util.List;

/**
 * Adapter per la visualizzazione dei prodotti in una RecyclerView.
 * Questo adapter si occupa di gestire l'interfacciamento tra i dati dei prodotti e la RecyclerView che li visualizza.
 */
public class ProdottoAdapter extends RecyclerView.Adapter<ProdottoAdapter.ProdottoViewHolder> {

    private static final String TAG = "ProdottoAdapter";
    private View view;
    private PiantaRepository piantaRepository;
    private FaseRepository faseRepository;

    /**
     * Interfaccia per la gestione dei click sugli elementi della RecyclerView.
     */
    public interface OnItemClickListener {
        void onItemClick(Prodotto prodotto);
    }

    private List<Prodotto> mProdottiList;
    private ProdottoAdapter.OnItemClickListener onItemClickListener;
    private int layout;

    /**
     * Costruttore dell'adapter.
     *
     * @param prodottiList       Lista dei prodotti da visualizzare.
     * @param onItemClickListener Gestore dei click sugli elementi della RecyclerView.
     * @param layout             Layout da utilizzare per ogni elemento della RecyclerView.
     */
    public ProdottoAdapter(List<Prodotto> prodottiList, ProdottoAdapter.OnItemClickListener onItemClickListener, int layout, Application application) {
        this.mProdottiList = prodottiList;
        this.onItemClickListener = onItemClickListener;
        this.layout = layout;
        this.piantaRepository = new PiantaRepository(application);
        this.faseRepository = new FaseRepository(application);
    }

    @NonNull
    @Override
    public ProdottoAdapter.ProdottoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new ProdottoAdapter.ProdottoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdottoAdapter.ProdottoViewHolder prodottoViewHolder, int i) {
        prodottoViewHolder.bind(mProdottiList.get(i));
    }

    @Override
    public int getItemCount() {
        if (mProdottiList != null) {
            return mProdottiList.size();
        }
        return 0;
    }

    public void update(List<Prodotto> prodottiList) {
        if (this.mProdottiList != null) {
            this.mProdottiList.clear();
            this.mProdottiList.addAll(prodottiList);
            notifyDataSetChanged();
        }
    }

    /**
     * ViewHolder per ogni elemento della RecyclerView.
     */
    public class ProdottoViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewProdottoBancarella;
        private final TextView textViewPiantaProdotto;
        private final TextView textViewFaseProdotto;
        private final TextView textViewQuantitaProdotto;
        private final TextView textViewPrezzoProdotto;

        public ProdottoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewProdottoBancarella = itemView.findViewById(R.id.imageViewProdottoBancarella);
            this.textViewPiantaProdotto = itemView.findViewById(R.id.textViewPiantaProdotto);
            this.textViewFaseProdotto = itemView.findViewById(R.id.textViewFaseProdotto);
            this.textViewQuantitaProdotto = itemView.findViewById(R.id.textViewQuantitaProdotto);
            this.textViewPrezzoProdotto = itemView.findViewById(R.id.textViewPrezzoProdotto);
        }

        /**
         * Associa i dati del prodotto al ViewHolder.
         *
         * @param prodotto Il prodotto da visualizzare.
         */
        public void bind(Prodotto prodotto) {

            String nomePiantaProdotto = piantaRepository.getPiantaById(prodotto.getPianta()).getNome();

            int resID = itemView.getContext().getResources()
                    .getIdentifier(
                            nomePiantaProdotto.toLowerCase(),
                            "drawable",
                            itemView.getContext().getPackageName()
                    );

            if(resID != 0) { // Se l'immagine esiste nel drawable
                this.imageViewProdottoBancarella.setImageResource(resID);
            } else {
                int fallbackResID = itemView.getContext().getResources().getIdentifier("fase_card_illustration", "drawable", itemView.getContext().getPackageName());
                this.imageViewProdottoBancarella.setImageResource(fallbackResID);
            }

            this.textViewPiantaProdotto.setText(nomePiantaProdotto);
            this.textViewFaseProdotto.setText(faseRepository.getFaseById(prodotto.getFaseAttuale()).getNomeFase());
            this.textViewQuantitaProdotto.setText(String.valueOf(prodotto.getQuantita()));
            this.textViewPrezzoProdotto.setText(String.format("%.2f", prodotto.getPrezzo()) + " €");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(prodotto);
                }
            });
        }
    }
}
