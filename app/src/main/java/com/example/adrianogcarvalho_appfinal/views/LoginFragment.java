package com.example.adrianogcarvalho_appfinal.views;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

public class LoginFragment extends Fragment {
    private EditText editTextEmail, editTextSenha;
    private MaterialButton botaoLogin, botaoCadastrar;
    private ManipularUsuarios manipularUsuarios;

    public LoginFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflando o layout do fragmento
        return inflater.inflate(R.layout.fragmento_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Referenciando os componentes do layout
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextSenha = view.findViewById(R.id.editTextSenha);
        botaoLogin = view.findViewById(R.id.botaoLogin);
        botaoCadastrar = view.findViewById(R.id.botaoCadastrar);

        // Estanciando um objeto da classe ManipularUsuario
        manipularUsuarios = new ManipularUsuarios(getContext());

        // Configurando o botão login
        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarLogin();
            }
        });

        // Configurando o botão cadastrar
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new CadastroFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    private void validarLogin() {
        String email = editTextEmail.getText().toString().trim();
        String senha = editTextSenha.getText().toString().trim();

        // Verificando se email e senha estão vazios
        if(email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(getActivity(), "Precisa preencher todos os campos!",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Criando nova instância da classe Usuário
            Usuario usuario = new Usuario(email, senha);

            // Validando login na Thread
            manipularUsuarios.validarLogin(usuario, new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(@NonNull Message mensagem) {
                    // Obtendo o resultado retornado pela validação
                    Usuario usuarioResultado = (Usuario) mensagem.obj;

                    // Verificando se foi encontrado o usuário
                    if(usuarioResultado != null) {
                        Toast.makeText(getActivity(), "Login bem-sucedido!",
                            Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, new HomeFragment())
                                .commit();
                    } else  {
                        Toast.makeText(getActivity(), "Usuário não encontrado!",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
