package com.mpoo.promoking.publicacao.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.mpoo.promoking.infra.ui.PromoKINGApp;
import com.mpoo.promoking.infra.ui.TaskResult;

import com.mpoo.promoking.R;
import com.mpoo.promoking.infra.ui.TaskResultType;
import com.mpoo.promoking.produto.negocio.ProdutoServices;
import com.mpoo.promoking.publicacao.dominio.Publicacao;
import com.mpoo.promoking.publicacao.dominio.UnidadeVenda;
import com.mpoo.promoking.publicacao.negocio.PublicacaoServices;
import com.mpoo.promoking.usuario.dominio.Usuario;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CriarPublicacaoEstComercialActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private AppCompatAutoCompleteTextView campoProduto;
    private AppCompatAutoCompleteTextView campoMarca;
    private EditText campoValidadeProduto;
    private AppCompatAutoCompleteTextView campoUnidadeVenda;
    private EditText campoPreco;
    private AdicionarPublicacaoTask adicionarPublicacaoTask = null;
    private Button botaoAdicionar;
    private final PublicacaoServices publicacaoServices = new PublicacaoServices();
    private final ProdutoServices produtoServices = new ProdutoServices();
    private List<String> listaStringMarcas;
    private List<String> listaStringProdutos;
    private List<String> listaStringUnidadeVenda;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_publicacao_est_comercial);

        context = this;
        campoProduto = (AppCompatAutoCompleteTextView) findViewById(R.id.autoCompleteProdutoCriarPub);
        campoMarca = (AppCompatAutoCompleteTextView) findViewById(R.id.autoCompleteMarcaCriarPub);
        campoUnidadeVenda = (AppCompatAutoCompleteTextView) findViewById(R.id.autoCompleteUnVendaCriarPub);
        campoValidadeProduto = findViewById(R.id.editTextDateValidadeProdutoCriarPub);
        campoPreco = findViewById(R.id.editTextPrecoProdutoCriarPub);
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
        try {
            listaStringProdutos = produtoServices.retornarListaStringProdutosDisponiveis();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listaStringUnidadeVenda = UnidadeVenda.listaUnidadeVendaValues();

        campoProduto.addTextChangedListener(campoProdutoTextWatcher);


        ArrayAdapter<String> adapterCampoProduto = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, listaStringProdutos);
        campoProduto.setThreshold(1);
        campoProduto.setAdapter(adapterCampoProduto);

        ArrayAdapter<String> adapterUnidadeVenda = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, listaStringUnidadeVenda);
        campoUnidadeVenda.setThreshold(1);
        campoUnidadeVenda.setAdapter(adapterUnidadeVenda); }

        public static Context getContext() {
            return context;
        }

        private TextWatcher campoProdutoTextWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String produto = campoProduto.getText().toString().trim();
                if (isProductValid(produto) && !produto.isEmpty()) {
                    try {
                        listaStringMarcas = produtoServices.listaMarcas(produtoServices.getProduto(produto.toUpperCase().replaceAll(" ", "_")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayAdapter<String> adapterCampoMarca = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, listaStringMarcas);
                campoMarca.setThreshold(1);
                campoMarca.setAdapter(adapterCampoMarca);
            }
        };

    private void adicionar() {
        if (adicionarPublicacaoTask != null) {
            return;
        }
        if (validaCampos()) {
            adicionarPublicacaoTask = new AdicionarPublicacaoTask();
            adicionarPublicacaoTask.execute((Void) null);
        }
    }

    private boolean validaCampos() {
        boolean result = true;

        campoProduto.setError(null);
        campoMarca.setError(null);
        campoValidadeProduto.setError(null);
        campoUnidadeVenda.setError(null);
        campoPreco.setError(null);

        String produto = campoProduto.getText().toString();
        String marca = campoMarca.getText().toString();
        String validadeProduto = campoValidadeProduto.getText().toString();
        String unidadeVenda = campoUnidadeVenda.getText().toString();
        String preco = campoPreco.getText().toString();

        View focusView = null;

        if (TextUtils.isEmpty(produto)) {
            campoProduto.setError("Campo obrigatório.");
            focusView = campoProduto;
            result = false;
        }
        if (!isProductValid(produto)) {
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
        if (!isUnidadeVendaValid(unidadeVenda)) {
            campoUnidadeVenda.setError("Unidade de venda inválida.");
            focusView = campoUnidadeVenda;
            result = false;
        }
        if (TextUtils.isEmpty(preco)) {
            campoPreco.setError("Campo obrigatório.");
            focusView = campoPreco;
            result = false;
        }
        if (!result) {
            focusView.requestFocus();
        }
        return result;
    }
    private boolean isUnidadeVendaValid(String unidadeVenda) {
        Boolean result = false;
        if(listaStringUnidadeVenda.contains(unidadeVenda.toUpperCase())){
            result = true;
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
        if(isProductValid(produto)){
            try {
                listaMarcas = produtoServices.listaMarcas(produtoServices.getProduto(produto.toUpperCase().replaceAll(" ", "_")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!listaMarcas.contains(marca)) {
                result = false;
            }
        }else{
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
        private final String preco;

        AdicionarPublicacaoTask(){
            produto = campoProduto.getText().toString();
            marca = campoMarca.getText().toString();
            unidadeVenda = campoUnidadeVenda.toString();
            String validadeProdutoString = campoValidadeProduto.getText().toString();
            int dia = Integer.parseInt(validadeProdutoString.substring(0, 2));
            int mes = Integer.parseInt(validadeProdutoString.substring(3, 5)) - 1;
            int ano = Integer.parseInt(validadeProdutoString.substring(6));
            validadeProduto = new GregorianCalendar(ano, mes, dia);
            preco = campoPreco.getText().toString();
            System.out.println(preco);
        }
        @Override
        protected TaskResult doInBackground(Void... voids) {
            TaskResult result = resultTaskAdicionar();
            return result;
        }
        private TaskResult resultTaskAdicionar(){
            TaskResult result = TaskResult.SUCCESS;
            try {
                Usuario usuario = new Usuario(); //<<<<<<<<<<<<<<<<<<<<<
                Publicacao publicacao = new Publicacao();
                publicacao.setProduto(produtoServices.getProduto(produto.toUpperCase().replaceAll(" ", "_")));
                publicacao.setMarca(marca.toUpperCase().replaceAll(" ", "_"));
                publicacao.setUnidadeVenda(UnidadeVenda.valueOf(unidadeVenda));
                publicacao.setValidadeProduto(validadeProduto);
                publicacao.setPreco(Double.valueOf(preco));
                publicacaoServices.salvarPublicacao(publicacao, usuario); //<<<<<< NESSE METODO ENTRA O USER DA SESSÃO


            } catch (Exception e) {
                result = new TaskResult(TaskResultType.FAIL, e.getMessage());
            }
            return result;
        }
        @Override
        protected void onCancelled() {
            resetTask();
        }

        private void resetTask() {
            adicionarPublicacaoTask = null;
        }
    }
}

