package com.example.adrianogcarvalho_appfinal.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.adrianogcarvalho_appfinal.R;

public class DicaFragment extends Fragment {

    private TextView textViewDicas;
    private Button botaoVoltarHome;


    public DicaFragment() {
        // Construtor vazio requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflando o layout para este fragmento
        return inflater.inflate(R.layout.fragment_dica, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Referência para a dica
        textViewDicas = view.findViewById(R.id.textViewDicas);
        botaoVoltarHome = view.findViewById(R.id.botaoVoltarHome);

        // Definindo a dica que está no strings.xml
        textViewDicas.setText(getString(R.string.dicas));

        // Configuração do botão de retorno
        botaoVoltarHome.setOnClickListener(v -> {
            // Trocar para o fragmento HomeFragment
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }
}

