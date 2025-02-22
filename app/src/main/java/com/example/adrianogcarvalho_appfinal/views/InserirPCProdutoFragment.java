package com.example.adrianogcarvalho_appfinal.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adrianogcarvalho_appfinal.R;
import com.example.adrianogcarvalho_appfinal.controllers.ManipularProduto;
import com.example.adrianogcarvalho_appfinal.models.Produto;
import com.example.adrianogcarvalho_appfinal.models.UsuarioProduto;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class InserirPCProdutoFragment extends Fragment {
    private MaterialButton botaoSalvar;
    private LinearLayout linearLayoutProdutos;
    private List<Produto> listaProdutos;
    private ArrayList<Integer> produtosSelecionados;
    private ManipularProduto manipularProduto;
    private int idUsuario;

    public InserirPCProdutoFragment(int idUsuario) {
        // Recebe o ID do usuário como parâmetro
        this.idUsuario = idUsuario;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflando o layout do fragmento
        return inflater.inflate(R.layout.fragment_inserir_pc_produto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Referenciando os componentes do layout
        botaoSalvar = view.findViewById(R.id.botaoSalvarProdutosInserirPC);

        // Inicializando a variável para o LinearLayout onde os checkboxes serão adicionados
        linearLayoutProdutos = view.findViewById(R.id.linearLayoutProdutos);

        // Inicializando a lista de produtos selecionados
        produtosSelecionados = new ArrayList<>();

        // Inicializando a manipulação de produtos no BD
        manipularProduto = new ManipularProduto(getContext());

        // Carregando a lista de produtos do BD
        carregarProdutos();

        // Botão para salvar os produtos selecionados e voltar para a Home
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarEscolhas();
                // Retornando para a Home
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new HomeFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    // Método para carregar os produtos a partir do BD
    private void carregarProdutos() {
        // Recuperando os produtos do BD
        List<Produto> produtos = manipularProduto.listarTodosProdutos();

        // Adicionando os produtos na lista
        listaProdutos = new ArrayList<>();
        for (Produto produto : produtos) {
            listaProdutos.add(produto);

            // Criando um checkbox para cada produto
            CheckBox checkBox = new CheckBox(getContext());
            // Nome do produto
            checkBox.setText(produto.getNome());
            // Armazenando o produto no checbox
            checkBox.setTag(produto);
            // Adicionando o checkbox ao layout
            linearLayoutProdutos.addView(checkBox);

            // Configurando o evento de clique para atualizar o seleção
            checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Produto produtoSelecionado = (Produto) buttonView.getTag();
                    if (isChecked) {
                        // Adicionando o produto selecionado ao ArrayList
                        produtosSelecionados.add(produtoSelecionado.getId());
                    } else {
                        // Removendo o produto do ArrayList
                        produtosSelecionados.remove(Integer.valueOf(produtoSelecionado.getId()));
                    }
                }
            });
        }
    }

    // Método chamado quando o botão de salvar é clicado
    private void salvarEscolhas() {
        if (produtosSelecionados.isEmpty()) {
            // Caso nenhum produto tenha sido selecionado
            Toast.makeText(getContext(), "Nenhum produto selecionado!", Toast.LENGTH_SHORT).show();
        } else {
            // Criando a lista de objetos UsuarioProduto para salvar no BD
            List<UsuarioProduto> usuarioProdutos = new ArrayList<>();
            for (Integer idProduto : produtosSelecionados) {
                UsuarioProduto usuarioProduto = new UsuarioProduto(idUsuario, idProduto);
                usuarioProdutos.add(usuarioProduto);
            }

            // Salvando as escolhas no BD
            manipularProduto.salvarEscolhaProdutos(usuarioProdutos);

            // Apresentando a mensagem de sucesso
            Toast.makeText(getContext(), "Produtos selecionados salvos!", Toast.LENGTH_SHORT).show();
        }
    }
}
