package com.example.adrianogcarvalho_appfinal.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.adrianogcarvalho_appfinal.R;
import com.example.adrianogcarvalho_appfinal.models.SessaoUsuario;

public class HomeFragment extends Fragment {
    private TextView textViewUsuario;
    //private ManipularProdutos dbProdutos;
    //private ManipularUsuarios dbUsuarios;
    //private ManipularPegadas dbPegadas;

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

        // Habilitando o menu para este fragmento
        setHasOptionsMenu(true);

        // Configurando a toolbar para que exista apenas neste fragmento
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

        // Referenciando o textView para exibir nome e aproveitamento escolar
        textViewUsuario = view.findViewById(R.id.textViewUsuario);

        // Exibindo as informações no TextView
        // COLOCANDO UM PC GENÉRICO
        double pc = 0.0;
        String mensagem = "Olá " + nomeUsuario + ", sua pegada de carbono atual é: "
                + pc;
        textViewUsuario.setText(mensagem);
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
