package com.mpoo.promoking.usuario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mpoo.promoking.estabelecimentoComercial.negocios.EstabelecimentoComercialServices;
import com.mpoo.promoking.infra.ui.TaskResult;
import com.mpoo.promoking.infra.ui.TaskResultType;
import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.dominio.Usuario;
import com.mpoo.promoking.util.EmailValidator;
import com.mpoo.promoking.cliente.negocios.ClienteServices;

import com.mpoo.promoking.R;

public class CadastroActivity extends AppCompatActivity {

    private final ClienteServices clienteServices = new ClienteServices();
    private final EstabelecimentoComercialServices estabelecimentoComercialServices = new EstabelecimentoComercialServices();
    private RadioGroup rgroupTipoUsuario;
    private RadioButton lastRadioButton;
    private EditText campoUsernameView, campoSenhaView, campoEmailView,campoConfirmeSenhaView;
    private RadioButton rbCliente;
    private RadioButton rbEstabelecimentoComercial;
    private UserRegisterTask userRegisterTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button botaoCadastrar = findViewById(R.id.buttonCadastroCadastrar);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
        //fields
        campoUsernameView = findViewById(R.id.editCadastroUsuario);
        campoSenhaView = findViewById(R.id.editCadastroSenha);
        campoEmailView = findViewById(R.id.editCadastroEmail);
        campoConfirmeSenhaView = findViewById(R.id.editConfirmeSenha);
        rbCliente = findViewById(R.id.radioButtonConsumidor);
        rbEstabelecimentoComercial = findViewById(R.id.radioButtonEstabelecimentoComercial);
        rgroupTipoUsuario = findViewById(R.id.rgTipoUsuario);
        rgroupTipoUsuario.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                lastRadioButton.setError(null);
            }
        });
        int ultimo = rgroupTipoUsuario.getChildCount() - 1;
        lastRadioButton = ((RadioButton) rgroupTipoUsuario.getChildAt(ultimo));
    }


    //CADASTROACTIVITY METHODS AND TASKS
    private void cadastrar() {
        if (userRegisterTask != null) {
            return;
        }
        if(validarCampos()) {
            userRegisterTask = new UserRegisterTask();
            userRegisterTask.execute((Void) null);
        }
    }

    private boolean validarCampos() {
        boolean result = true;

        // Reset errors.
        campoUsernameView.setError(null);
        campoEmailView.setError(null);
        campoSenhaView.setError(null);
        campoConfirmeSenhaView.setError(null);
        lastRadioButton.setError(null);

        // Store values at the time of the login attempt.
        String username = campoUsernameView.getText().toString();
        String email = campoEmailView.getText().toString();
        String senha = campoSenhaView.getText().toString();
        String confirmeSenha = campoConfirmeSenhaView.getText().toString();

        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(senha)) {
            campoSenhaView.setError(getString(R.string.error_invalid_password));
            focusView = campoSenhaView;
            result = false;}
        if (TextUtils.isEmpty(confirmeSenha)){
            campoConfirmeSenhaView.setError(getString(R.string.error_invalid_password));
            focusView = campoConfirmeSenhaView;
            result = false;}
        if (!isPasswordEqualConfirmation(senha, confirmeSenha)) {
            campoConfirmeSenhaView.setError(getString(R.string.error_incompatible_password));
            focusView = campoConfirmeSenhaView;
            result = false;
        if (!isPasswordValid(senha)) {
                campoConfirmeSenhaView.setError(getString(R.string.error_invalid_password));
                focusView = campoSenhaView;
                result = false;
            }
        }

        EmailValidator emailValidator = new EmailValidator();

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            campoEmailView.setError(getString(R.string.error_field_required));
            focusView = campoEmailView;
            result = false;
        } else if (!emailValidator.isValidEmail(email)) {
            campoEmailView.setError(getString(R.string.error_invalid_email));
            focusView = campoEmailView;
            result = false;
        }
        // Cheking for a correct radioButton answer
        if (rgroupTipoUsuario.getCheckedRadioButtonId() == -1) {

            lastRadioButton.setError(getString(R.string.error_field_required));
            focusView = lastRadioButton;
            result = false;
        }
        // Cheking for a correct id
        if (TextUtils.isEmpty(username)) {
            campoUsernameView.setError(getString(R.string.error_field_required));
            focusView = campoUsernameView;
            result = false;
        } else if (!isUsernameValid(username)){
            campoUsernameView.setError(getString(R.string.error_invalid_username));
            focusView = campoUsernameView;
            result = false;
        }
        if (!result) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        return result;
    }

    private boolean isPasswordValid(String senha) {
        return senha.length() > 7; }

    private boolean isPasswordEqualConfirmation(String senha, String confirmacaoSenha) {
        return senha.equals(confirmacaoSenha); }

    private boolean isUsernameValid(String username) {
        return username.length() > 7; }

    public class UserRegisterTask extends AsyncTask<Void, Void, TaskResult> {
        private final String username;
        private final String email;
        private final String senha;
        private final String confirmeSenha;
        private final TipoUsuario idTipoUsuario;

        UserRegisterTask(){
            username = campoUsernameView.getText().toString();
            email = campoEmailView.getText().toString();
            senha = campoSenhaView.getText().toString();
            confirmeSenha = campoConfirmeSenhaView.getText().toString();
            if (rbCliente.isChecked()) { idTipoUsuario = TipoUsuario.CLIENTE;}
            else { idTipoUsuario = TipoUsuario.ESTABELECIMENTO_COMERCIAL;}

        }

        @Override
        protected TaskResult doInBackground(Void... voids) {
            TaskResult result = registerUser();
            return result;
        }

        private TaskResult registerUser() {
            TaskResult result = TaskResult.SUCCESS;
            try {
                Usuario usuario = new Usuario();
                usuario.setUsername(username);
                usuario.setEmail(email);
                usuario.setSenha(senha);

                if (idTipoUsuario.equals(TipoUsuario.CLIENTE)){
                    clienteServices.cadastrar(usuario);
                }else if (idTipoUsuario.equals(TipoUsuario.ESTABELECIMENTO_COMERCIAL)){
                    estabelecimentoComercialServices.cadastrar(usuario);
                }

            } catch (Exception error) {
                error.printStackTrace();
                result = new TaskResult(TaskResultType.FAIL, error.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(final TaskResult result) {
            if (result == null) {
                resetTask();
                return;
            }
            if (result.getType() == TaskResultType.FAIL) {
                campoUsernameView.requestFocus();
                Toast.makeText(CadastroActivity.this, "Usuário já cadastrado.", Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(CadastroActivity.this, result.getMsg(), Toast.LENGTH_LONG).show();

            /*final Handler handler = new Handler()
            {
                @Override
                public void handleMessage(Message mesg)
                {
                    throw new RuntimeException(); }
            };

            try{ Looper.loop(); }
            catch(RuntimeException e){}
            */
            if (result.getType() == TaskResultType.SUCCESS) {
                cadastroConcluido();
            }
            resetTask();
        }
        private void resetTask() {
            userRegisterTask = null;
        }
        private void cadastroConcluido() {
            Toast.makeText(CadastroActivity.this, "Cadastro concluído com sucesso.", Toast.LENGTH_LONG).show();
            Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }

    }
}

