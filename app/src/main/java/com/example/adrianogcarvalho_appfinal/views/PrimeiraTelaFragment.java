package com.example.adrianogcarvalho_appfinal.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adrianogcarvalho_appfinal.R;

public class PrimeiraTelaFragment extends Fragment {
    private TextView descricaoApp;
    private Button botaoLogin, botaoExplicacao;

    public PrimeiraTelaFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflando o layout do fragmento
        return inflater.inflate(R.layout.fragmento_primeira_tela, container, false);
    }
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

            // Referenciando os componentes de interface gráfica
            descricaoApp = view.findViewById(R.id.descricaoApp);
            botaoLogin = view.findViewById(R.id.botaoLogin);
            botaoExplicacao = view.findViewById(R.id.botaoExplicacao);

            // Atribuindo o texto ao componente para exibir
            descricaoApp.setText(getString(R.string.descricao_app));

            // Configurando o botão para ir para o login
            botaoLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navegando para o fragmento login
                    getFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, new LoginFragment())
                            // Permitindo voltar ao fragmento
                            .addToBackStack(null)
                            .commit();

                }
            });
            // Configurando botão para alertDialog (Explicação)
            botaoExplicacao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarDialog();
                }
            });

        }

    // Método para carregar o AlertDialog
    private void mostrarDialog() {
        MyDialog mdf = new MyDialog();
        mdf.show(requireActivity().getSupportFragmentManager(), "dialogo");
    }
}
