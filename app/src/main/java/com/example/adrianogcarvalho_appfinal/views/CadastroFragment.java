package com.example.adrianogcarvalho_appfinal.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adrianogcarvalho_appfinal.R;
import com.example.adrianogcarvalho_appfinal.controllers.ManipularUsuarios;
import com.example.adrianogcarvalho_appfinal.models.Usuario;
import com.google.android.material.button.MaterialButton;

public class CadastroFragment extends Fragment {
    private EditText editCadastroNome, editCadastroEmail, editCadastroSenha;
    private MaterialButton botaoSalvarCadastro;

    public CadastroFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflando o layout do fragmento
        return inflater.inflate(R.layout.fragmento_cadastro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Referenciando os componentes do layout
        editCadastroNome = view.findViewById(R.id.editCadastroNome);
        editCadastroEmail = view.findViewById(R.id.editCadastroEmail);
        editCadastroSenha = view.findViewById(R.id.editCadastroSenha);
        botaoSalvarCadastro = view.findViewById(R.id.botaoSalvarCadastro);

        // Configurando o botão cadastrar
        botaoSalvarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validando os campos
                String nome = editCadastroNome.getText().toString();
                String email = editCadastroEmail.getText().toString();
                String senha = editCadastroSenha.getText().toString();

                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(getContext(), "Precisa preencher todos os campos!",
                            Toast.LENGTH_SHORT).show();
                } else  {

                    // Verificar se o usário já possui cadastro
                    // MONTAR A CONSULTA AQUI
                    // /////////////////////////////////////////////////////

                    // Criando um novo usuario e salvando no BD
                    Usuario usuario = new Usuario(nome, email, senha);
                    ManipularUsuarios manipularUsuarios = new ManipularUsuarios(getContext());
                    manipularUsuarios.inserirUsuario(usuario);

                    // Exibindo a mensagem de sucesso
                    Toast.makeText(getContext(), "Cadastro realizado com sucesso!",
                            Toast.LENGTH_SHORT).show();

                    // Limpando os campos
                    editCadastroNome.setText("");
                    editCadastroEmail.setText("");
                    editCadastroSenha.setText("");

                    // Voltando para o fragmento de login
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, new LoginFragment())
                            // Permitindo voltar ao fragmento anterior
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

    }

}
