package com.example.agendacontatostp1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;

public class MainActivity extends AppCompatActivity {

    final String contatos = "contatos.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_salvar = (Button) findViewById(R.id.btn_salvar);

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText stringNome = (EditText) findViewById(R.id.nome);
                String vNome = stringNome.getText().toString();

                EditText stringTelefone = (EditText) findViewById(R.id.telefone);
                String vTelefone = stringTelefone.getText().toString();

                EditText stringEmail = (EditText) findViewById(R.id.email);
                String vEmail = stringEmail.getText().toString();

                EditText stringCidade = (EditText) findViewById(R.id.cidade);
                String vCidade = stringCidade.getText().toString();

                if (vNome == null || vNome.equals("") ||
                vTelefone == null || vTelefone.equals("") ||
                vEmail == null || vEmail.equals("") ||
                vCidade == null || vCidade.equals("")) {

                    Mensagem("ATENÇÃO!","Preencha TODOS OS CAMPOS!");
                } else{
                    gravarArquivoTexto();
                    //stringNome.setText(null); stringTelefone.setText(null); stringEmail.setText(null); stringCidade.setText(null);
                    Mensagem("SALVO","Dados salvos com sucesso!");
                }
            }
        });

        Button btn_limpar = (Button) findViewById(R.id.btn_limpar);

        btn_limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File f = getFileStreamPath(contatos);

                Log.i("MSG", "Lendo arquivo " + f.getAbsolutePath());

                if (f.exists()){
                    deleteFile(contatos);
                    Mensagem("DELETADO","Arquivo Deletado com sucesso!");
                }else{
                    Mensagem("ARQUIVO INEXISTENTE","Nenhum registro foi encontrado");
                }
            }

        });

        Button btn_contatos = (Button) findViewById(R.id.btn_contatos);

        btn_contatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lerArquivoTexto();
            }

        });

    }

    public void Mensagem(String titulo, String mensagem){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
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

    public void gravarArquivoTexto() {

        EditText editNome = (EditText) findViewById(R.id.nome);
        String nome = editNome.getText().toString();

        EditText editTelefone = (EditText) findViewById(R.id.telefone);
        String telefone = editTelefone.getText().toString();

        EditText editEmail = (EditText) findViewById(R.id.email);
        String email = editEmail.getText().toString();

        EditText editCidade = (EditText) findViewById(R.id.cidade);
        String cidade = editCidade.getText().toString();

        try {

            FileOutputStream out = openFileOutput(contatos, MODE_APPEND);
            out.write(nome.getBytes());
            out.write(telefone.getBytes());
            out.write(email.getBytes());
            out.write(cidade.getBytes());
            out.close();

        } catch (Exception e) {

            Log.e("ERRO", e.getMessage());

        }

    }

    public void lerArquivoTexto() {

        try {

            EditText editNome = (EditText) findViewById(R.id.nome);
            String nome = editNome.getText().toString();

            EditText editTelefone = (EditText) findViewById(R.id.telefone);
            String telefone = editTelefone.getText().toString();

            EditText editEmail = (EditText) findViewById(R.id.email);
            String email = editEmail.getText().toString();

            EditText editCidade = (EditText) findViewById(R.id.cidade);
            String cidade = editCidade.getText().toString();

            File f = getFileStreamPath(contatos);

            Log.i("MSG", "Lendo arquivo " + f.getAbsolutePath());

            if (f.exists()) {

                FileInputStream in = openFileInput(contatos);
                int tamanho = in.available();
                ////byte bytes[] = new byte[tamanho];
                //in.read(bytes);

                String nomeBundle = new String(nome);
                String telefoneBundle = new String(telefone);
                String emailBundle = new String(email);
                String cidadeBundle = new String(cidade);

                //Toast.makeText(this, telefoneBundle, Toast.LENGTH_SHORT).show();

                Bundle parametros = new Bundle();

                parametros.putString("nome", nome);
                parametros.putString("telefone", telefone);
                parametros.putString("email", email);
                parametros.putString("cidade", cidade);
                parametros.putString("contatos", contatos);
                parametros.putInt("tamanho", tamanho);

                Intent enviarDados = new Intent(this, Resultado.class);
                enviarDados.putExtras(parametros);
                startActivity(enviarDados);

                //in.close();
            }else {
                Mensagem("ARQUIVO INEXISTENTE","Nenhum registro foi encontrado");
            }

        } catch (Exception e) {

            Log.e("ERRO", e.getMessage());

        }

    }
}
