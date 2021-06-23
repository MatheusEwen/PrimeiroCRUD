package com.example.sqlcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlcrud.dao.PessoaDao;
import com.example.sqlcrud.modelo.Pessoa;

public class TelaCadastro extends AppCompatActivity {

    TextView txtVariavel;
    Button btnVariavel;
    EditText edtNome, edtIdade, edtEmail, edtEndereco;

    //PARA FAZER O CRUD
    Pessoa pessoa, altPessoa;
    PessoaDao pessoaDao;
    long retornoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        //Para poder receber os dados da tela inicial caso tenham clicado em algum nome da lista
        Intent telaAtual = getIntent();
        altPessoa = (Pessoa) telaAtual.getSerializableExtra("pessoa-enviada");
        pessoa = new Pessoa();
        pessoaDao = new PessoaDao(TelaCadastro.this);

        //Referencias
        edtNome = findViewById(R.id.edtNome);
        edtIdade = findViewById(R.id.edtIdade);
        edtEmail = findViewById(R.id.edtEmail);
        edtEndereco = findViewById(R.id.edtEndereco);
        btnVariavel = findViewById(R.id.btnVariavel);
        txtVariavel = findViewById(R.id.txtVariavel);

        if(altPessoa != null){
            btnVariavel.setText("ALTERAR REGISTRO");
            txtVariavel.setText("ALTERAÇÃO");

            //PARA ATUALIZAÇÃO
            edtEmail.setText(altPessoa.getEmail());
            edtNome.setText(altPessoa.getNome());
            edtIdade.setText(altPessoa.getIdade() + "");
            edtEndereco.setText(altPessoa.getEndereco());
            pessoa.setId(altPessoa.getId());
        } else {
            btnVariavel.setText("SALVAR REGISTRO");
            txtVariavel.setText("CADASTRO");

        }

        btnVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNome.getText().toString().isEmpty()){
                    edtNome.setError("Digite o nome");
                    edtNome.requestFocus();
                } else if (edtIdade.getText().toString().isEmpty()){
                    edtIdade.setError("Digite a idade");
                    edtIdade.requestFocus();
                } else if (edtEmail.getText().toString().isEmpty()){
                    edtEmail.setError("Digite o email");
                    edtEmail.requestFocus();
                } else if (edtEndereco.getText().toString().isEmpty()){
                    edtEndereco.setError("Digite o email");
                    edtEndereco.requestFocus();
                } else {
                    pessoa.setNome(edtNome.getText().toString());
                    pessoa.setEmail(edtEmail.getText().toString());
                    pessoa.setIdade(Integer.parseInt(edtIdade.getText().toString()));
                    pessoa.setEndereco(edtEndereco.getText().toString());

                    if(btnVariavel.getText().toString().equals("SALVAR REGISTRO")){
                        //gravação dos dados
                        retornoDB = pessoaDao.salvarPessoa(pessoa);
                        pessoaDao.close();
                        if(retornoDB == -1){
                            //Toast.makeText(TelaCadastro.this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
                            mensagem("Erro ao Cadastrar");
                        }else {
                            //Toast.makeText(TelaCadastro.this, "Cadastrado com Sucesso", Toast.LENGTH_SHORT).show();
                            mensagem("Cadastrado com Sucesso");
                        }
                    } else {
                        //Alteração
                        retornoDB = pessoaDao.alterarPessoa(pessoa);
                        pessoaDao.close();
                        if (retornoDB == -1) {
                            //Toast.makeText(TelaCadastro.this, "Erro ao alterar", Toast.LENGTH_SHORT).show();
                            mensagem("Erro ao Alterar");
                        } else {
                            //Toast.makeText(TelaCadastro.this, "Alterado com Sucesso", Toast.LENGTH_SHORT).show();
                            mensagem("Alterado com sucesso");
                        }
                    }
                    finish();
                }
            }
        });
    }
    private void mensagem(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}