package com.example.adrianogcarvalho_appfinal.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adrianogcarvalho_appfinal.R;
import com.example.adrianogcarvalho_appfinal.controllers.ManipularProduto;
import com.example.adrianogcarvalho_appfinal.models.Produto;

public class InserirProdutoFragment extends Fragment {
    private EditText editTextNomeProduto;
    private EditText editTextPCProduto;
    private Button botaoInserirProduto, botaoVoltarHome;
    private ManipularProduto dbProdutos;
    private double pcProduto;

    public InserirProdutoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflando o layout do fragmento
        return inflater.inflate(R.layout.fragment_inserir_produto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Instanciando ManipularProduto
        dbProdutos = new ManipularProduto(getContext());

        // Referenciando os componentes de interface gráfica
        editTextNomeProduto = view.findViewById(R.id.editTextInserirNovoProduto);
        editTextPCProduto = view.findViewById(R.id.editTextPCNovoProduto);
        botaoInserirProduto = view.findViewById(R.id.buttonInserirProduto);
        botaoVoltarHome = view.findViewById(R.id.buttonVoltarHome);

        // Configurando o botão de inserção
        botaoInserirProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtendo os valores dos campos de entrada
                String nomeProduto = editTextNomeProduto.getText().toString().trim();
                String pcProdutoString = editTextPCProduto.getText().toString().trim();

                // Validação dos campos
                if (nomeProduto.isEmpty() || pcProdutoString.isEmpty()) {
                    Toast.makeText(getActivity(), "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    pcProduto = Double.parseDouble(pcProdutoString);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Pegada inválida, insira um número válido!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificando se o produto já existe no BD
                if (dbProdutos.existeProduto(nomeProduto)) {
                    Toast.makeText(getActivity(), "Produto já cadastrado!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Instanciando o Produto e inserindo no BD
                    Produto produto = new Produto(nomeProduto, pcProduto);
                    dbProdutos.inserirProduto(produto);

                    // Exibindo uma mensagem de sucesso
                    Toast.makeText(getActivity(), "Produto inserido com sucesso!", Toast.LENGTH_SHORT).show();
                }

                // Limpando os campos
                editTextNomeProduto.setText("");
                editTextPCProduto.setText("");
            }
        });

        // Configurando o botão de voltar para o HomeFragment
        botaoVoltarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retornando para o HomeFragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new HomeFragment())
                        .commit();
            }
        });
    }
}
