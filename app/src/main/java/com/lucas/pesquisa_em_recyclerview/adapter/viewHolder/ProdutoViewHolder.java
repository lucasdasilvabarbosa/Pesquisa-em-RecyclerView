package com.lucas.pesquisa_em_recyclerview.adapter.viewHolder;

import android.support.annotation.NonNull;
import android.view.View;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.lucas.pesquisa_em_recyclerview.adapter.ProdutoAdpater;
import com.lucas.pesquisa_em_recyclerview.databinding.ItemListBinding;
import com.lucas.pesquisa_em_recyclerview.model.Produto;


/**
 * Created by RCK 03 on 20/07/2017.
 */

public class ProdutoViewHolder extends SortedListAdapter.ViewHolder<Produto>{

    private final ItemListBinding mBinding;

    public ProdutoViewHolder(ItemListBinding binding, ProdutoAdpater.Listener listener) {
        super(binding.getRoot());
        binding.setListener(listener);

        this.mBinding = binding;
    }

    @Override
    protected void performBind(@NonNull Produto produto) {
        mBinding.setModel(produto);
    }
}
