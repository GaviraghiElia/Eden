package com.unimib.eden.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimib.eden.R;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.PiantaRepository;
import com.unimib.eden.utils.Converters;
import com.unimib.eden.utils.Transformer;

import java.util.ArrayList;
import java.util.List;

public class ColturaAdapter extends RecyclerView.Adapter<ColturaAdapter.ColturaViewHolder> {

    private static final String TAG = "GamesAdapter";
    private View view;

    public interface OnItemClickListener {
        void onItemClick(Coltura coltura);
    }

    private List<Coltura> mColtureList;
    private OnItemClickListener onItemClickListener;
    private PiantaRepository piantaRepository;
    private int layout;

    public ColturaAdapter(List<Coltura> coltureList, OnItemClickListener onItemClickListener, int layout, Application application) {
        this.mColtureList = coltureList;
        this.onItemClickListener = onItemClickListener;
        this.layout = layout;
        piantaRepository = new PiantaRepository(application);
    }

    @NonNull
    @Override
    public ColturaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new ColturaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColturaViewHolder colturaViewHolder, int i) {
        colturaViewHolder.bind(mColtureList.get(i));
    }

    @Override
    public int getItemCount() {
        if(mColtureList != null) {
            return mColtureList.size();
        }
        return 0;
    }

    public class ColturaViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewColturaPianta;
        private final TextView textViewGiorniInnaffiamento;
        private final TextView textViewDataInserimento;
        private final TextView textViewNote;
        private final ImageView imageViewNote;
        public ColturaViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewColturaPianta = itemView.findViewById(R.id.textViewPianta);
            this.textViewGiorniInnaffiamento = itemView.findViewById(R.id.textViewDaysNumber);
            this.textViewDataInserimento = itemView.findViewById(R.id.textViewDate);
            this.textViewNote = itemView.findViewById(R.id.textViewNote);
            this.imageViewNote = itemView.findViewById(R.id.imageViewNote);
        }

        public void bind(Coltura coltura) {
            //Picasso.with(imageViewGamesImageUrl.getContext()).load(coltura.getImage_url()).resize(1500, 900).into(imageViewGamesImageUrl);
            //TODO: sistemare quando scaricherà le piante nel db
            //this.textViewColturaPianta.setText(coltura.getIdPianta());
            this.textViewColturaPianta.setText("Pomodoro");
            //TODO: sistemare quando scaricherà le piante nel db
            //this.textViewGiorniInnaffiamento.setText(Transformer.formatProssimoInnaffiamento(coltura, piantaRepository.getPiantaById(coltura.getIdPianta())));
            this.textViewGiorniInnaffiamento.setText(Transformer.formatProssimoInnaffiamento(itemView.getContext(), coltura, new Pianta("", "", "","", 2, 5, 2, new ArrayList<>(), 1.0, "", "", 0, 0, 1)));
            this.textViewDataInserimento.setText(Converters.dateToString(coltura.getDataInserimento()));
            if(coltura.getNote().isEmpty()) {
                this.imageViewNote.setVisibility(View.GONE);
                this.textViewNote.setVisibility(View.GONE);
            }
            else {
                this.textViewNote.setText(coltura.getNote());
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(coltura);
                }
            });
        }
    }
}
