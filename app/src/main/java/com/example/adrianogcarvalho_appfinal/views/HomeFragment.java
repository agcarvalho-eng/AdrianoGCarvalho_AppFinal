package com.example.adrianogcarvalho_appfinal.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.adrianogcarvalho_appfinal.R;
import com.example.adrianogcarvalho_appfinal.controllers.ManipularUsuarioProduto;
import com.example.adrianogcarvalho_appfinal.models.SessaoUsuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment {
    private TextView textViewUsuario;
    private ProgressBar progressBar;
    private FloatingActionButton botaoFlutuante;
    private ManipularUsuarioProduto manipularUsuarioProduto;

    public HomeFragment() {

    }

    // Buscando as informações do usuário logado
    int idUsuario = SessaoUsuario.getIdUsuario();
    String nomeUsuario = SessaoUsuario.getNomeUsuario();

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflando o layout do fragmento
        return inflater.inflate(R.layout.fragmento_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Referenciando os componentes de interface gráfica
        textViewUsuario = view.findViewById(R.id.textViewUsuario);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        progressBar = view.findViewById(R.id.progressBar);

        // Habilitando o menu para este fragmento
        setHasOptionsMenu(true);

        // Configurando a toolbar para que exista apenas neste fragmento
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

        // Inicializando a classe ManipularUsuarioProduto
        manipularUsuarioProduto = new ManipularUsuarioProduto(getContext());

        // Calculando a pegada de carbono atual
        List<Integer> listaIdsProdutos = manipularUsuarioProduto.listarIdProdutosUsuario(idUsuario);
        double pegadaCarbono = manipularUsuarioProduto.calcularPegadaUsuario(listaIdsProdutos);

        // Exibindo as informações no TextView
        String mensagem = String.format("Olá %s, sua pegada de carbono atual é: %.2f.", nomeUsuario, pegadaCarbono);
        textViewUsuario.setText(mensagem);

        // Definindo o valor máximo da progress bar para mostrar a evolução da pegada

        progressBar.setMax(100000);

        // Atualizando a progress bar conforme a pegada de carbono
        int progresso = (int) pegadaCarbono;
        if(progresso > 100000) {
            progresso = 100000;
        }
        progressBar.setProgress(progresso);
        progressBar.setVisibility(View.VISIBLE);

        // Referenciando o botão flutuante
        botaoFlutuante = view.findViewById(R.id.botaoFlutuante);
        // Configurando o clique do botão flutuante
        botaoFlutuante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quando clicado direciona para DicaFragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new DicaFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    // Método do ciclo de vida do fragmento (quando iniciar o fragmento)
    @Override
    public void onStart() {
        super.onStart();
        // Mostrar a Toolbar quando o Fragmento Home for exibido
        ((MainActivity) getActivity()).toolbarVisibilidade(true);
    }

    // Método do ciclo de vida do fragmento (quando finalizar o fragmento)
    @Override
    public void onStop() {
        super.onStop();
        // Escondendo a Toolbar quando for outros fragmentos
        ((MainActivity) getActivity()).toolbarVisibilidade(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflando o menu para adicionar as opções na Toolbar
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Verificando qual item foi selecionado
        if (item.getItemId() == R.id.menu_listarProdutos) {
            Toast.makeText(getActivity(), "Listar Produtos", Toast.LENGTH_SHORT).show();
            // Direcionando para o fragmento de Listar Produtos
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new ListarProdutosFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.menu_inserirProduto) {
            Toast.makeText(getActivity(), "Inserindo Novo Produto", Toast.LENGTH_SHORT).show();
            // Direcionando para o fragmento Inserir Produto
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new InserirProdutoFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.menu_logout) {
            Toast.makeText(getActivity(), "Desconectando!", Toast.LENGTH_SHORT).show();
            // Direcionando para o fragmento de Tela Login
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new LoginFragment())
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.menu_inserirPegada) {
            // Ação ao clicar no novo item "Peso"
            Toast.makeText(getActivity(), "Você clicou em Inserir Pegada!", Toast.LENGTH_SHORT).show();
            // Direcionando para o fragmento de Tela Login
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new InserirPCProdutoFragment(idUsuario))
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
