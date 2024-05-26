package com.unimib.eden.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.R;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.repository.PiantaRepository;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.Converters;
import com.unimib.eden.utils.Transformer;

import java.util.Date;
import java.util.List;

public class IrrigazioniAdapter extends RecyclerView.Adapter<IrrigazioniAdapter.IrrigazioniViewHolder> {

    private static final String TAG = "ColturaAdapter";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private View view;

    /**
     * Interfaccia per la gestione dei click sugli elementi della RecyclerView.
     */
    public interface OnItemClickListener {
        void onItemClick(Coltura coltura);
    }

    private List<Coltura> mColtureList;
    private IrrigazioniAdapter.OnItemClickListener onItemClickListener;
    private PiantaRepository piantaRepository;
    private int layout;

    /**
     * Costruttore dell'adapter.
     *
     * @param coltureList       Lista delle colture da visualizzare.
     * @param onItemClickListener Gestore dei click sugli elementi della RecyclerView.
     * @param layout             Layout da utilizzare per ogni elemento della RecyclerView.
     * @param application        Oggetto Application per l'accesso al repository delle piante.
     */
    public IrrigazioniAdapter(List<Coltura> coltureList, IrrigazioniAdapter.OnItemClickListener onItemClickListener, int layout, Application application) {
        this.mColtureList = coltureList;
        this.onItemClickListener = onItemClickListener;
        this.layout = layout;
        piantaRepository = new PiantaRepository(application);
    }

    @NonNull
    @Override
    public IrrigazioniAdapter.IrrigazioniViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new IrrigazioniAdapter.IrrigazioniViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IrrigazioniAdapter.IrrigazioniViewHolder colturaViewHolder, int i) {
        colturaViewHolder.bind(mColtureList.get(i));
        /*
        colturaViewHolder.itemView.findViewById(R.id.irrigazioniCheckbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection(Constants.FIRESTORE_COLLECTION_COLTURE)
                        .document(mColtureList.get(colturaViewHolder.getAdapterPosition()).getId())
                        .update("ultimo_innaffiamento", new Date());
                update(mColtureList.get(colturaViewHolder.getAdapterPosition()));
            }
        });

         */
    }

    @Override
    public int getItemCount() {
        if (mColtureList != null) {
            return mColtureList.size();
        }
        return 0;
    }

    public void update(Coltura coltura){
        mColtureList.remove(coltura);
        coltura.setUltimoInnaffiamento(new Date());
        mColtureList.add(coltura);
        notifyDataSetChanged();
    }


    public void update(List<Coltura> colturaList) {
        if (this.mColtureList != null) {
            this.mColtureList.clear();
            this.mColtureList.addAll(colturaList);
            notifyDataSetChanged();
        }
    }

    /**
     * ViewHolder per ogni elemento della RecyclerView.
     */
    public class IrrigazioniViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewColturaPianta;
        private final TextView textViewGiorniInnaffiamento;
        private final TextView textViewDataUltimaIrrigazione;

        public IrrigazioniViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textViewColturaPianta = itemView.findViewById(R.id.textViewPianta);
            this.textViewGiorniInnaffiamento = itemView.findViewById(R.id.textViewDaysNumber);
            this.textViewDataUltimaIrrigazione = itemView.findViewById(R.id.textViewDate);
        }

        /**
         * Associa i dati della coltura al ViewHolder.
         *
         * @param coltura La coltura da visualizzare.
         */
        public void bind(Coltura coltura) {
            //this.textViewColturaPianta.setText(piantaRepository.getPiantaById(coltura.getIdPianta()).getNome());
            //this.textViewGiorniInnaffiamento.setText(Transformer.formatProssimoInnaffiamento(itemView.getContext(), coltura, piantaRepository.getPiantaById(coltura.getIdPianta())));
            this.textViewColturaPianta.setText(coltura.getNomePianta());

            this.textViewGiorniInnaffiamento.setText(Transformer.formatProssimoInnaffiamento(itemView.getContext(), coltura));
            if(Transformer.daysToProssimoInnaffiamento(coltura) >= 0){
                this.textViewGiorniInnaffiamento.setCompoundDrawablesWithIntrinsicBounds(R.drawable.garden_watering_can_24_ok, 0, 0, 0);
            }else{
                this.textViewGiorniInnaffiamento.setCompoundDrawablesWithIntrinsicBounds(R.drawable.garden_watering_can_24_delay, 0, 0, 0);
            }



            this.textViewDataUltimaIrrigazione.setText(Converters.dateToString(coltura.getDataInserimento()));


        }
    }
}
