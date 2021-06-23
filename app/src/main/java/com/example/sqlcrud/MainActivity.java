package com.example.sqlcrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sqlcrud.dao.PessoaDao;
import com.example.sqlcrud.modelo.Pessoa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listaPessoas;
    Button btnNovoCadastro;

    //PARA FAZER O CRUD
    Pessoa pessoa;
    PessoaDao pessoaDao;
    ArrayList<Pessoa> arrayListPessoa;
    ArrayAdapter <Pessoa> arrayAdapterPessoa;
    long retornoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referenciando
        listaPessoas = findViewById(R.id.lstPessoas);
        btnNovoCadastro = findViewById(R.id.btnNovo);
        //MENU DE CONTEXTO PARA EXCLUIR
        registerForContextMenu(listaPessoas);

        btnNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastro = new Intent(MainActivity.this, TelaCadastro.class);
                startActivity(cadastro);
            }
        });

        //CLIQUE SIMPLES na lista para editar
        listaPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Pessoa pessoaEnviada = (Pessoa) arrayAdapterPessoa.getItem(position);
                Intent editar = new Intent(MainActivity.this, TelaCadastro.class);
                editar.putExtra("pessoa-enviada", pessoaEnviada);
                startActivity(editar);
            }
        });

        //CLIQUE LONGO NA LISTA PARA EXCLUIR
        listaPessoas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                pessoa = arrayAdapterPessoa.getItem(position);
                return false;
            }
        });

    }
    public void preencherLista(){
        //Conexão
        pessoaDao = new PessoaDao(MainActivity.this);

        arrayListPessoa = pessoaDao.selectAllPessoa();
        pessoaDao.close();

        if(listaPessoas != null){
            arrayAdapterPessoa = new ArrayAdapter<Pessoa>(MainActivity.this,
                    android.R.layout.simple_list_item_1, arrayListPessoa);
            listaPessoas.setAdapter(arrayAdapterPessoa);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        preencherLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem mDelete = menu.add("Apagar Registro");

        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                pessoaDao = new PessoaDao(MainActivity.this);
                AlertDialog.Builder msgExluir = new AlertDialog.Builder(MainActivity.this);
                msgExluir.setTitle("Excluir");
                msgExluir.setMessage("Confirma a exclusão de registro?");
                //Botão sim
                msgExluir.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        retornoDB = pessoaDao.excluirPessoa(pessoa);
                        pessoaDao.close();
                        if(retornoDB == -1){
                            mensagemToast("Erro ao excluir!");
                        }else{
                            mensagemToast("Excluido com Sucesso!");
                            preencherLista();
                        }
                    }
                });
                //Botão não
                msgExluir.setNegativeButton("Não", null);

                //para não cancelar a mensagem clicando fora dela(mensagem modal)
                msgExluir.setCancelable(false);

                //icone da mensagem
                msgExluir.setIcon(android.R.drawable.ic_delete);
                msgExluir.show();
                return false;

            }
        });
    }

    private void mensagemToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();


    }
}