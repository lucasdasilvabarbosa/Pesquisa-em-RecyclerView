package com.lucas.pesquisa_em_recyclerview.model;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

/**
 * Created by RCK 03 on 20/07/2017.
 */

public class Produto implements SortedListAdapter.ViewModel {
    private final int id;
    private final String nome;

    public Produto(int id, String nome1) {
        this.id = id;
        nome = nome1;
    }

    public int getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }



    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", Nome='" + nome + '\'' +
                '}';
    }

    @Override
    public <T> boolean isSameModelAs(@NonNull T item) {
       if(item instanceof Produto){
           final Produto produto = (Produto) item;
           return produto.getId() == id;
       }

        return false;
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T item) {
        if(item instanceof Produto){
            final Produto outro = (Produto) item;
            if(id != outro.getId()){
                return false;
            }
            return this.nome != null ? nome.equals(outro.getNome()) : outro.nome == null;
        }
        return false;
    }
}
