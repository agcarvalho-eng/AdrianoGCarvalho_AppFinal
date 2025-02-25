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
        // Recebe o id do usuário como parâmetro
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

        // Referenciando os componentes de interface gráfica
        botaoSalvar = view.findViewById(R.id.botaoSalvarProdutosInserirPC);
        linearLayoutProdutos = view.findViewById(R.id.linearLayoutProdutos);

        // Instaciando a lista de produtos selecionados
        produtosSelecionados = new ArrayList<>();

        // Instaciando manipular produtos
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

        // Instanciando um ArrayList vazio e atribuindo à listaProdutos
        listaProdutos = new ArrayList<>();

        // Adicionando os produtos vindos do BD na listaProdutos
        for (Produto produto : produtos) {
            listaProdutos.add(produto);

            // Instanciando um checkbox para cada produto
            CheckBox checkBox = new CheckBox(getContext());
            // Definindo o nome do produto para o checkbox
            checkBox.setText(produto.getNome());
            // Armazenando o produto no checbox
            checkBox.setTag(produto);
            // Adicionando o checkbox ao layout
            linearLayoutProdutos.addView(checkBox);

            // Configurando o evento de clique para atualizar o seleção
            checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                // Método chamado quando o estado do checkbox muda (true ou false)
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
            // Criando uma lista de usuarioProdutos
            List<UsuarioProduto> usuarioProdutos = new ArrayList<>();
            //
            for (Integer idProduto : produtosSelecionados) {
                //Instanciando usuarioProduto
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
