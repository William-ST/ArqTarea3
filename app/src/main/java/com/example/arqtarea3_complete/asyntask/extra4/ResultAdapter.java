package com.example.arqtarea3_complete.asyntask.extra4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arqtarea3_complete.R;

import java.util.List;

/**
 * Created by William_ST on 20/02/19.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private LayoutInflater inflador;
    protected List<String> resultList;
    private Context contexto;

    public List<String> getResultList() {
        return resultList;
    }

    public void addResultItem(String item) {
        resultList.add(item);
        notifyItemInserted(resultList.size()-1);
    }

    public ResultAdapter(Context contexto, List<String> resultList) {
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resultList = resultList;
        this.contexto = contexto;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.item_result, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int posicion) {
        holder.tvItem.setText(resultList.get(posicion));
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

}
