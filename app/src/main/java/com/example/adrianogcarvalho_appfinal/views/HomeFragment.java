package com.example.adrianogcarvalho_appfinal.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adrianogcarvalho_appfinal.R;
import com.example.adrianogcarvalho_appfinal.controllers.ManipularPegadas;
import com.example.adrianogcarvalho_appfinal.controllers.ManipularProdutos;
import com.example.adrianogcarvalho_appfinal.controllers.ManipularUsuarios;

public class HomeFragment extends Fragment {
    private TextView textViewUsuario;
    //private ManipularProdutos dbProdutos;
    //private ManipularUsuarios dbUsuarios;
    //private ManipularPegadas dbPegadas;

    public HomeFragment() {

    }

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


    }
}
