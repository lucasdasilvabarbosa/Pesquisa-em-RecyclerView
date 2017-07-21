package com.lucas.pesquisa_em_recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.lucas.pesquisa_em_recyclerview.adapter.viewHolder.ProdutoViewHolder;
import com.lucas.pesquisa_em_recyclerview.databinding.ItemListBinding;
import com.lucas.pesquisa_em_recyclerview.model.Produto;

import java.util.Comparator;

/**
 * Created by RCK 03 on 20/07/2017.
 */

public class ProdutoAdpater extends SortedListAdapter<Produto>{


    public interface Listener {

        void onExampleModelClicked(Produto model);
    }
    private final Listener mListener;

    public ProdutoAdpater(Context context, Comparator<Produto> comparator, Listener listener){
        super(context, Produto.class, comparator);
        mListener = listener;

    }

    @NonNull
    @Override
    protected ViewHolder<? extends Produto> onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int viewType) {
        final ItemListBinding binding = ItemListBinding.inflate(layoutInflater, viewGroup, false);
        return new ProdutoViewHolder(binding, mListener);
    }


}
