package com.mpoo.promoking.usuario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.mpoo.promoking.R;
import com.mpoo.promoking.infra.ui.MainActivityCliente;
import com.mpoo.promoking.infra.ui.MainActivityEstabelecimentoComercial;
import com.mpoo.promoking.infra.ui.TaskResult;
import com.mpoo.promoking.infra.ui.TaskResultType;
import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.negocios.UsuarioServices;

import android.content.Intent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private UserLoginTask userLoginTask = null;
    private EditText campoUsername, campoSenha;
    private final UsuarioServices usuarioServices = new UsuarioServices();
    private RadioButton lastRadioButton;
    private RadioGroup rgLoginTipoUsuario;
    private RadioButton rbLoginCliente;
    private RadioButton rbLoginEstabelecimentoComercial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoUsername = findViewById(R.id.editLoginUsuario);
        campoSenha = findViewById(R.id.editLoginSenha);
        Button botaoLogin = findViewById(R.id.buttonLoginEntrar);
        TextView textAbrirCadastro = findViewById(R.id.textCadastrar);
        rgLoginTipoUsuario = findViewById(R.id.radioGroupLoginTipoUsuario);
        rbLoginCliente = findViewById(R.id.radioButtonLoginConsumidor);
        rbLoginEstabelecimentoComercial = findViewById(R.id.radioButtonLoginEstabelecimentoComercial);
        rgLoginTipoUsuario.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                lastRadioButton.setError(null);
            }
        });
        int ultimo = rgLoginTipoUsuario.getChildCount() - 1;
        lastRadioButton = ((RadioButton) rgLoginTipoUsuario.getChildAt(ultimo));
        campoSenha.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    tentativaLogin();
                    return true;
                }
                return false;
            }
        });
        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tentativaLogin();
            }
        });
        textAbrirCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivityCadastro(view);
            }
        });
    }

    private void tentativaLogin() {
        if(userLoginTask != null) {
            return;
        }
        if (validarCampos()) {
            userLoginTask = new UserLoginTask();
            userLoginTask.execute((Void) null);
        }
    }
    private void abrirActivityCadastro(View view) {
        startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
    }
    private boolean validarCampos() {
        boolean result = true;

        //reset errors
        campoUsername.setError(null);
        campoSenha.setError(null);
        lastRadioButton.setError(null);

        String username = campoUsername.getText().toString();
        String senha = campoSenha.getText().toString();

        View focusView = null;

        if (TextUtils.isEmpty(senha) || !isPasswordValid(senha)) {
            campoSenha.setError(getString(R.string.error_invalid_password));
            focusView = campoSenha;
            result = false;
        }
        if (TextUtils.isEmpty((username)) || !isUsernameValid(username)) {
            campoUsername.setError(getString((R.string.error_invalid_username)));
            focusView = campoSenha;
            result = false;
        }
        // Cheking for a correct radioButton answer
        if (rgLoginTipoUsuario.getCheckedRadioButtonId() == -1) {

            lastRadioButton.setError(getString(R.string.error_field_required));
            focusView = lastRadioButton;
            result = false;
        }
        if (!result) {
            focusView.requestFocus();
        }
        return result;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 7;
    }
    private boolean isUsernameValid(String username) {
        return username.length() > 7;
    }

    //AsyncTasks
    public class UserLoginTask extends AsyncTask<Void, Void, TaskResult> {

        private final String username;
        private final String senha;
        private final TipoUsuario idTipoUsuario;

        UserLoginTask() {
            username = campoUsername.getText().toString();
            senha = campoSenha.getText().toString();
            if (rbLoginCliente.isChecked()) { idTipoUsuario = TipoUsuario.CLIENTE;}
            else { idTipoUsuario = TipoUsuario.ESTABELECIMENTO_COMERCIAL;}

        }
        @Override
        protected TaskResult doInBackground(Void... voids) {
            TaskResult result = loginUser();
            return result;
        }


        private TaskResult loginUser() {
            TaskResult result = TaskResult.SUCCESS;
            try {
                usuarioServices.login(username, senha, idTipoUsuario);
            } catch (Exception error){
                error.printStackTrace();
                result = new TaskResult(TaskResultType.FAIL, error.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(final TaskResult result) {
            if(result == null) {
                resetTask();
                return;
            }
            if (result.getType() == TaskResultType.FAIL) {
                campoUsername.requestFocus();
                Toast.makeText(LoginActivity.this, "Usuário ou senha inválidos ou tipo de usuário incorreto.", Toast.LENGTH_LONG).show();
            }
            if (result.getType() == TaskResultType.SUCCESS) {
                loginConcluido(idTipoUsuario);
            }
            resetTask();
        }
        private void loginConcluido(TipoUsuario idTipoUsuario){
            Toast.makeText(LoginActivity.this, "Login realizado com sucesso.", Toast.LENGTH_LONG).show();

            if (idTipoUsuario.equals(TipoUsuario.CLIENTE)){
                startActivity(new Intent(LoginActivity.this, MainActivityCliente.class));
            } else if (idTipoUsuario.equals(TipoUsuario.ESTABELECIMENTO_COMERCIAL)){
                startActivity(new Intent(LoginActivity.this, MainActivityEstabelecimentoComercial.class));
            }

        }

        @Override
        protected void onCancelled() {
            resetTask();
        }

        private void resetTask() {
            userLoginTask = null;
        }
    }

}