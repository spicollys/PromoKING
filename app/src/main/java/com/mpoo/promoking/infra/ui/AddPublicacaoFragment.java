package com.mpoo.promoking.infra.ui;


import android.app.DatePickerDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.mpoo.promoking.R;
import com.mpoo.promoking.cliente.persistencia.ClienteDAO;
import com.mpoo.promoking.infra.Sessao;
import com.mpoo.promoking.infra.persistencia.BancoDadosHelper;
import com.mpoo.promoking.produto.negocio.ProdutoServices;
import com.mpoo.promoking.publicacao.dominio.Publicacao;
import com.mpoo.promoking.publicacao.dominio.UnidadeVenda;
import com.mpoo.promoking.publicacao.negocio.PublicacaoServices;
import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.dominio.Usuario;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPublicacaoFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

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


    public AddPublicacaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_produto, container, false);

        context = getActivity();
        campoProduto = view.findViewById(R.id.autoCompleteProdutoCriarPub);
        campoProduto = view.findViewById(R.id.autoCompleteProdutoCriarPub);
        campoMarca = view.findViewById(R.id.autoCompleteMarcaCriarPub);
        campoUnidadeVenda = view.findViewById(R.id.autoCompleteUnVendaCriarPub);
        campoValidadeProduto = view.findViewById(R.id.editTextDateValidadeProdutoCriarPub);
        campoPreco = view.findViewById(R.id.editTextPrecoProdutoCriarPub);
        botaoAdicionar = view.findViewById(R.id.botaoAdicionarPubEstComercial);
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
            System.out.println(listaStringProdutos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        listaStringUnidadeVenda = UnidadeVenda.listaUnidadeVendaValues();

        campoProduto.addTextChangedListener(campoProdutoTextWatcher);


        ArrayAdapter<String> adapterCampoProduto = new ArrayAdapter<String>(context, android.R.layout.select_dialog_item, listaStringProdutos);
        campoProduto.setThreshold(1);
        campoProduto.setAdapter(adapterCampoProduto);

        ArrayAdapter<String> adapterUnidadeVenda = new ArrayAdapter<String>(context, android.R.layout.select_dialog_item, listaStringUnidadeVenda);
        campoUnidadeVenda.setThreshold(1);
        campoUnidadeVenda.setAdapter(adapterUnidadeVenda);

        return view;
    }
    public Context getContext() {
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
            String produto = campoProduto.getText().toString().trim();
            System.out.println(produto);
            System.out.println(isProductValid(produto));
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
        if (!isUnidadeVendaValida(unidadeVenda)) {
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

    private boolean isUnidadeVendaValida(String unidadeVenda) {
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
                context,
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
                Usuario usuario = Sessao.instance.getUsuario();
                String username = usuario.getUsername();
                TipoUsuario tipoUsuario = usuario.getIdTipoUsuario();
                long id = new ClienteDAO().getID(username);
                Publicacao publicacao = new Publicacao();

                publicacao.setProduto(produtoServices.getProduto(produto.toUpperCase().replaceAll(" ", "_")));
                publicacao.setMarca(marca.toUpperCase().replaceAll(" ", "_"));
                publicacao.setUnidadeVenda(UnidadeVenda.valueOf(unidadeVenda));
                publicacao.setValidadeProduto(validadeProduto);
                publicacao.setPreco(Double.valueOf(preco));
                publicacaoServices.salvarPublicacao(publicacao,tipoUsuario, id); //<<<<<< NESSE METODO ENTRA O USER DA SESSÃO


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
