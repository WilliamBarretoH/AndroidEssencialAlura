package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.model.dao.AlunoDAO;
import com.example.agenda.model.entity.Aluno;

public class FormAlunoActivity extends AppCompatActivity {

    private static final String titulo_appbar_novo_aluno = "Novo aluno";
    private static final String titulo_appbar_edita_aluno = "Edita aluno";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    final AlunoDAO alunoDAO = new AlunoDAO();
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form__aluno_);

        inicializacaoDosCampos();
        carregaAlunoSeVazioOuNao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_form_aluno_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_alunos_menu_salvar) {
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregaAlunoSeVazioOuNao() {
        Intent dados = getIntent();
        if (dados.hasExtra("aluno")) {
            setTitle(titulo_appbar_edita_aluno);
            aluno = (Aluno) dados.getSerializableExtra("aluno");
            preencheCampos();
        } else {
            setTitle(titulo_appbar_novo_aluno);
            aluno = new Aluno();
        }
    }

    private void preencheCampos() {
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoEmail.setText(aluno.getEmail());
    }

    private void finalizaFormulario() {
        preencheAluno();
        if (aluno.temIdValido()) {
            alunoDAO.edita(aluno);
        } else {
            alunoDAO.salva(aluno);
        }
        finish();
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_form_aluno_nome);
        campoTelefone = findViewById(R.id.activity_form_aluno_telefone);
        campoEmail = findViewById(R.id.activity_form_aluno_email);
    }

    private void preencheAluno() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        aluno.setNome(nome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
    }
}
