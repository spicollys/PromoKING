package com.mpoo.promoking.infra.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mpoo.promoking.R;
import com.mpoo.promoking.usuario.negocios.UsuarioServices;
import com.mpoo.promoking.usuario.ui.LoginActivity;

public class MainActivityEstabelecimentoComercial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_estabelecimento_comercial);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        configurarBottomNavigation();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewPager, new FeedFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_sair:
                deslogarUsuario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario() {
        try{
            UsuarioServices services = new UsuarioServices();
            services.logout();
            startActivity(new Intent(this, LoginActivity.class));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void configurarBottomNavigation(){

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        habilitarNavegacao(bottomNavigationView);

    }

    private void habilitarNavegacao(BottomNavigationView view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (menuItem.getItemId()){
                    case R.id.ic_home:
                        fragmentTransaction.replace(R.id.viewPager, new FeedFragment()).commit();
                        return true;
                    case R.id.ic_add:
                        fragmentTransaction.replace(R.id.viewPager, new AddPublicacaoFragment()).commit();
                        return true;
                    case R.id.ic_perfil:
                        fragmentTransaction.replace(R.id.viewPager, new PerfilFragment()).commit();
                        return true;
                }
                return false;
            }
        });

    }
}
