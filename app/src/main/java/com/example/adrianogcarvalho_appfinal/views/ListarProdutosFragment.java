package com.example.adrianogcarvalho_appfinal.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adrianogcarvalho_appfinal.R;
import com.example.adrianogcarvalho_appfinal.controllers.ManipularProdutos;
import com.example.adrianogcarvalho_appfinal.models.Produto;

import java.util.ArrayList;
import java.util.List;

public class ListarProdutosFragment extends Fragment {
    private RecyclerView recyclerView;
    private MeuAdapter meuAdapter;
    private ManipularProdutos dbProdutos;
    private List<Produto> listaProdutos;
    private Button botaoVoltarHome;

    public ListarProdutosFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflando o layout do fragmento
        return inflater.inflate(R.layout.fragment_listar_produtos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Inicializando o recyclerView e o botão
        recyclerView = view.findViewById(R.id.recyclerViewProdutos);
        botaoVoltarHome = view.findViewById(R.id.botaoVoltarHome);

        // Verificando se o recyclerView não está vazio
        if(recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            Toast.makeText(getContext(), "RecyclerView não encontrado!",
                    Toast.LENGTH_SHORT).show();
        }

        // Iniciando a manipulação dos produtos no BD
        dbProdutos = new ManipularProdutos(getContext());
        listaProdutos = new ArrayList<>();

        // Buscando todas os produtos no BD
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Obtendo a lista de produtos do BD
                listaProdutos = dbProdutos.listarTodosProdutos();

                // Log para verificar se os produtos foram carregados no BD
                Log.d("ListarProdutosFragment", "Lista de produtos carregada. Tamanho: " + listaProdutos.size());

                // Atualizando a UI no thread principal após a busca no BD
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Criando e configurando o Adapter
                        // Log de confirmação de configuração do Adapter
                        Log.d("ListarProdutosFragment", "Criando o Adapter com " + listaProdutos.size() + " produtos.");
                        meuAdapter = new MeuAdapter(listaProdutos);
                        recyclerView.setAdapter(meuAdapter);
                    }
                });
            }
        }).start();

        // Configurando o botão voltar para a Home
        botaoVoltarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retornando para o HomeFragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new HomeFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
}
