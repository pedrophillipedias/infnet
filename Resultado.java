package com.example.agendacontatostp1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class Resultado extends AppCompatActivity {

    final String contatos = "contatos.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado);

        Intent enviarDados = getIntent();
        if(enviarDados != null) {
            Bundle parametros = enviarDados.getExtras();
            if(parametros != null) {

                String nome = parametros.getString("nome");
                String telefone = parametros.getString("telefone");
                String email = parametros.getString("email");
                String cidade = parametros.getString("cidade");
                String contatos = parametros.getString("contatos");
                int tamanho = parametros.getInt("tamanho");

                ListView lista = (ListView) findViewById(R.id.lista);

                final ArrayList<String> contatosList = preencherDados();

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, contatosList);
                lista.setAdapter(arrayAdapter);

                lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent enviarDados = getIntent();
                        Bundle parametros = enviarDados.getExtras();

                        String nome = parametros.getString("nome");
                        String telefone = parametros.getString("telefone");
                        String email = parametros.getString("email");
                        String cidade = parametros.getString("cidade");
                        String contatos = parametros.getString("contatos");
                        int tamanho = parametros.getInt("tamanho");

                        Toast.makeText(getApplicationContext(), "Teste: "+contatosList.get(position).toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            }

        }

    private ArrayList<String> preencherDados( ){
        ArrayList<String> dados = new ArrayList<String>();

        File f = getFileStreamPath(contatos);

        File list[] = f.listFiles();

        for( int i=0; i< list.length; i++)
        {
            dados.add( list[i].getName());
        }


        return dados;
    }

    public void Mensagem(String titulo, String mensagem){
        AlertDialog alertDialog = new AlertDialog.Builder(Resultado.this).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensagem);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}