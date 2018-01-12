package com.example.edwin.appmyevents.interfaz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.edwin.appmyevents.R;
import com.example.edwin.appmyevents.interfaz.Modelo.Login;
import com.example.edwin.appmyevents.interfaz.Modelo.Persona;
import com.example.edwin.appmyevents.interfaz.Utilidades.ClienteRest;
import com.example.edwin.appmyevents.interfaz.Utilidades.OnTaskCompleted;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, OnTaskCompleted {

    private ClienteRest clienteRest;
    private int WS_CONSULTA = 1;
    public static int codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button btnListaEventos = (Button) findViewById(R.id.btnListaEventos);
        btnListaEventos.setOnClickListener(this);

        Button btnLocales = (Button) findViewById(R.id.btnLocales);
        btnLocales.setOnClickListener(this);

        Button btnEliminaUsuario = (Button) findViewById(R.id.btnEliminarUsuario);
        btnEliminaUsuario.setOnClickListener(this);

        Button btnVerPerfil = (Button) findViewById(R.id.btnVerPerfil);
        btnVerPerfil.setOnClickListener(this);

        //eliminarUsuario();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            // Handle the camera action
        } else if (id == R.id.nav_login) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_categoria) {
            Intent intent = new Intent(MainActivity.this, CategoriaActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_elimina_usuario) {

            Intent intent = new Intent(MainActivity.this,ListadoLocalesActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_ayuda) {
            Intent intent = new Intent(MainActivity.this, AyudaActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_ajustes) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnListaEventos:
                Intent intent = new Intent(this,ListadoEventosActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLocales:
                Intent intent1 = new Intent(this,ListadoLocalesActivity.class);
                startActivity(intent1);
                break;
            case R.id.btnEliminarUsuario:
                eliminarUsuario();
                Intent intent2 = new Intent(this, Ingreso.class);
                startActivity(intent2);

                Context context = getApplicationContext();
                CharSequence text = "Usuario Eliminado";
                int duracion = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context,text,duracion);
                toast.show();

                break;

            case R.id.btnVerPerfil:
                Intent intent4 = new Intent(this,EditUsuario.class);
                startActivity(intent4);
                break;
            default:
                break;

        }
    }



    public void eliminarUsuario(){

        clienteRest = new ClienteRest(this);

        try {

            String url = "http://192.168.0.101:8080/MyEvents/rs/usuarios/eliminar-usuario?id_usuario="+ LoginActivity.cod_per;
            clienteRest.doGet(url, null,WS_CONSULTA,true);

        }catch (Exception e){
            showMensaje("Error Consulta");
            e.printStackTrace();
        }

    }

    public void verUsuario(){

        clienteRest = new ClienteRest(this);

        try {

            String url = "http://192.168.0.101:8080/MyEvents/rs/usuarios/listado-users"+ LoginActivity.cod_per;
            clienteRest.doGet(url, null,WS_CONSULTA,true);

        }catch (Exception e){
            showMensaje("Error Consulta");
            e.printStackTrace();
        }

    }

    @Override
    public void onTaskCompleted(int idSolicitud) {

        List<Login> personas;

        if (idSolicitud == WS_CONSULTA) {
            if (!clienteRest.isCancelled()) {
                //Recupera del WS el resultado que y se muestra en consola
                /* personas = clienteRest.getResultList(Login.class);
                for (Login per : personas) {
                    codigo = per.getId();
                    System.out.println("NOMBRES: " + codigo);
                }*/
            }
        }


    }

    /**
     * Permite mostrar un mensaje Toast en pantalla,
     * @param mensaje    Texto del mensaje a mostrar
     */
    private void showMensaje(String mensaje){
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.show();
    }
}
