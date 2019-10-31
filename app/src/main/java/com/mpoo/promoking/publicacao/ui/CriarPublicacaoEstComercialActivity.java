package com.mpoo.promoking.publicacao.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.mpoo.promoking.infra.ui.TaskResult;

import com.mpoo.promoking.R;
import com.mpoo.promoking.infra.ui.TaskResultType;
import com.mpoo.promoking.produto.negocio.ProdutoServices;
import com.mpoo.promoking.publicacao.dominio.Publicacao;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CriarPublicacaoEstComercialActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private AppCompatAutoCompleteTextView campoProduto;
    private AppCompatAutoCompleteTextView campoMarca;
    private EditText campoValidadeProduto;
    private AppCompatAutoCompleteTextView campoUnidadeVenda;
    private Button botaoAdicionar;
    private AdicionarPublicacaoTask adicionarPublicacaoTask = null;
    private final ProdutoServices produtoServices = new ProdutoServices();
    private List<String> listaStringMarcas;
    private List<String> listaStringProdutos;
    private List<String> listaStringUnidadeVenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_publicacao_est_comercial);

        campoProduto = (AppCompatAutoCompleteTextView) findViewById(R.id.autoCompleteProdutoCriarPub);
        campoMarca = (AppCompatAutoCompleteTextView) findViewById(R.id.autoCompleteMarcaCriarPub);
        campoUnidadeVenda = (AppCompatAutoCompleteTextView) findViewById(R.id.autoCompleteUnVendaCriarPub);
        campoValidadeProduto = findViewById(R.id.editTextDateValidadeProdutoCriarPub);
        botaoAdicionar = findViewById(R.id.botaoAdicionarPubEstComercial);
        campoValidadeProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionar();
            }
        });


        ArrayAdapter<String> adapterCampoProduto = new ArrayAdapter<String>( this, android.R.layout.select_dialog_item);
        campoProduto.setThreshold(1);
        campoProduto.setAdapter(adapterCampoProduto);

        ArrayAdapter<String> adapterCampoMarca = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        campoMarca.setThreshold(1);
        campoProduto.setAdapter(adapterCampoMarca);

        ArrayAdapter<String> adapterUnidadeVenda = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        campoUnidadeVenda.setThreshold(1);
        campoUnidadeVenda.setAdapter(adapterUnidadeVenda);
    }

    private void adicionar() {
        if (adicionarPublicacaoTask != null) {
            return;
        }
        if (validaCampos()) {
            adicionarPublicacaoTask = new AdicionarPublicacaoTask();
        }
    }

    private boolean validaCampos() {
        boolean result = true;

        campoProduto.setError(null);
        campoMarca.setError(null);
        campoValidadeProduto.setError(null);
        campoUnidadeVenda.setError(null);

        String produto = campoProduto.getText().toString();
        String marca = campoMarca.getText().toString();
        String validadeProduto = campoValidadeProduto.getText().toString();
        String unidadeVenda = campoUnidadeVenda.getText().toString();

        View focusView = null;

        if (TextUtils.isEmpty(produto)) {
            campoProduto.setError("Campo obrigatório.");
            focusView = campoProduto;
            result = false;
        }
        if (!isProductValid(produto)){
            campoProduto.setError("Produto inválido.");
            focusView = campoProduto;
            result = false;
        }
        if (TextUtils.isEmpty(marca)) {
            campoMarca.setError("Campo obrigatório.");
            focusView = campoMarca;
            result = false;
        }
        if (!isTrademarkValid(marca, produto)) {
            campoMarca.setError("Marca inválida para este produto.");
            focusView = campoMarca;
            result = false;
        }
        if (TextUtils.isEmpty(validadeProduto)) {
            campoValidadeProduto.setError("Campo obrigatório.");
            focusView = campoValidadeProduto;
            result = false;
        }
        if (TextUtils.isEmpty(unidadeVenda)) {
            campoUnidadeVenda.setError("Campo obrigatório.");
            focusView = campoUnidadeVenda;
            result = false;
        }
        if (!result) {
            focusView.requestFocus();
        }
        return result;
    }

    private boolean isProductValid(String produto) {
        boolean result = true;
        produto = StringUtils.capitalize(produto);
        List listaStringProdutosDisponiveis = null;
        try {
            listaStringProdutosDisponiveis = produtoServices.retornarListaStringProdutosDisponiveis();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!listaStringProdutosDisponiveis.contains(produto)) {
            result = false;
        }
        return result;
    }

    private boolean isTrademarkValid(String marca, String produto) {
        boolean result = true;
        marca = StringUtils.capitalize(marca);
        List listaMarcas = null;
        try {
            listaMarcas = produtoServices.listaMarcas(produtoServices.getProduto(produto.toUpperCase().replaceAll(" ", "_")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!listaMarcas.contains(marca)) {
            result = false;
        }
        return result;
    }

    private void showDatePicker() {
        Calendar calendario = Calendar.getInstance();
        DatePickerDialog pickerDialog = new DatePickerDialog(
                this,
                this,
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
        );
        pickerDialog.getDatePicker().setMinDate(new Date().getTime());
        pickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        month += 1;
        campoValidadeProduto.setText((day < 10 ? "0" : "") + day + "/" + (month < 10 ? "0" : "") + month + "/" + year);
        campoValidadeProduto.setError(null);
    }

    public class AdicionarPublicacaoTask extends AsyncTask<Void, Void, TaskResult> {
        private final String produto;
        private final String marca;
        private final String unidadeVenda;
        private final GregorianCalendar validadeProduto;

        AdicionarPublicacaoTask(){
            produto = campoProduto.getText().toString();
            marca = campoMarca.getText().toString();
            unidadeVenda = campoUnidadeVenda.toString();
            String validadeProdutoString = campoValidadeProduto.getText().toString();
            int dia = Integer.parseInt(validadeProdutoString.substring(0, 2));
            int mes = Integer.parseInt(validadeProdutoString.substring(3, 5)) - 1;
            int ano = Integer.parseInt(validadeProdutoString.substring(6));
            validadeProduto = new GregorianCalendar(ano, mes, dia);
        }
        @Override
        protected TaskResult doInBackground(Void... voids) {
            TaskResult result = resultTaskAdicionar();
            return result;
        }
        private TaskResult resultTaskAdicionar(){
            TaskResult result = TaskResult.SUCCESS;
            try {
                Publicacao publicacao = new Publicacao();
                ////////////////////////ADICIONAR SETS//////////////////////////////
            } catch (Exception e) {
                result = new TaskResult(TaskResultType.FAIL, e.getMessage());
            }
            return result;
        }
    }
}
