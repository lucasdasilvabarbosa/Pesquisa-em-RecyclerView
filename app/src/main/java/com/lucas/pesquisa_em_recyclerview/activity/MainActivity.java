package com.lucas.pesquisa_em_recyclerview.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.lucas.pesquisa_em_recyclerview.R;
import com.lucas.pesquisa_em_recyclerview.adapter.ProdutoAdpater;
import com.lucas.pesquisa_em_recyclerview.databinding.ActivityMainBinding;
import com.lucas.pesquisa_em_recyclerview.model.Produto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SortedListAdapter.Callback {

    private static final Comparator<Produto> COMPARATOR = new SortedListAdapter.ComparatorBuilder<Produto>().
            setOrderForModel(Produto.class, new Comparator<Produto>() {
                @Override
                public int compare(Produto a, Produto b) {
                    return Integer.signum(a.getId() - b.getId());
                }
            }).build();
    private ProdutoAdpater produtoAdpater;
    private ActivityMainBinding activityMainBinding;
    private Animator animator;

    private List<Produto> produtoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(activityMainBinding.toolBar);

        produtoAdpater = new ProdutoAdpater(this, COMPARATOR, new ProdutoAdpater.Listener() {
            @Override
            public void onExampleModelClicked(Produto produto) {
                final String mensagem = "Você selecionou o produto "+produto.getId()+" descricao: "+produto.getNome()+" ...";
                Snackbar.make(activityMainBinding.getRoot(), mensagem, Snackbar.LENGTH_SHORT).show();
            }
        });


        produtoAdpater.addCallback(this);

        activityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityMainBinding.recyclerView.setAdapter(produtoAdpater);

        produtoList = preencherLista();
        produtoAdpater.edit()
                .replaceAll(produtoList)
                .commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_pesquisa);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);


        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {


        return true;
    }


    @Override
    public boolean onQueryTextChange(String query) {
        final List<Produto> buscaLista = buscaProduto(produtoList, query);
        produtoAdpater.edit().replaceAll(buscaLista).commit();
        return true;
    }

    @Override
    public void onEditStarted() {
        if (activityMainBinding.editProgressBar.getVisibility() != View.VISIBLE) {
            activityMainBinding.editProgressBar.setVisibility(View.VISIBLE);
            activityMainBinding.editProgressBar.setAlpha(0.0f);
        }

        if (animator != null) {
            animator.cancel();
        }

        animator = ObjectAnimator.ofFloat(activityMainBinding.editProgressBar, View.ALPHA, 1.0f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();

        activityMainBinding.recyclerView.animate().alpha(0.5f);
    }


    @Override
    public void onEditFinished() {
        activityMainBinding.recyclerView.scrollToPosition(0);
        activityMainBinding.recyclerView.animate().alpha(1.0f);

        if (animator != null) {
            animator.cancel();
        }

        animator = ObjectAnimator.ofFloat(activityMainBinding.editProgressBar, View.ALPHA, 0.0f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {

            private boolean mCanceled = false;

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mCanceled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!mCanceled) {
                    activityMainBinding.editProgressBar.setVisibility(View.GONE);
                }
            }
        });
        animator.start();
    }

    public List<Produto> preencherLista() {
        List<Produto> preenchendo = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            Produto produto = new Produto(i, "Produto " + i);

            preenchendo.add(produto);
        }

        return preenchendo;
    }

    public List<Produto> buscaProduto(List<Produto> produtos, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Produto> listaBusca = new ArrayList<>();
        for (Produto produto : produtos) {
            final String nome = produto.getNome().toLowerCase();
            final String id = String.valueOf(produto.getId());
            if (nome.contains(lowerCaseQuery) || id.contains(lowerCaseQuery)) {
                listaBusca.add(produto);
            }
        }

        return listaBusca;
    }
}
