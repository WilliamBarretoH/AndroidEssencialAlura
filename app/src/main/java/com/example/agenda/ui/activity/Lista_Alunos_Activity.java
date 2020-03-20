package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.model.dao.AlunoDAO;
import com.example.agenda.model.entity.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Lista_Alunos_Activity extends AppCompatActivity {

    public static final String titulo_appbar = "Lista de alunos";
    private final AlunoDAO alunoDao = new AlunoDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(titulo_appbar);
        alunoDao.salvar(new Aluno("william","122312","william@email.com"));
        alunoDao.salvar(new Aluno("giovana","122312","gi@email.com"));

        FloatingActionButton botaoSalvarNovoAluno = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        botaoSalvarNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormNovoAluno();
            }
        });
    }

    private void abreFormNovoAluno() {
        startActivity(new Intent(Lista_Alunos_Activity.this,
                Form_Aluno_Activity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        configuraListaDeAlunos();
    }

    private void configuraListaDeAlunos() {

        ListView listaDeALunos = findViewById(R.id.activity_lista_de_alunos_list_view);
        final List<Aluno> alunos = alunoDao.getAlunos();

        listaDeALunos.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                alunos));

        listaDeALunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno alunoEscolhido = alunos.get(position);
                Intent vaiParaFormNovoAluno = new Intent(Lista_Alunos_Activity.this, Form_Aluno_Activity.class);
                vaiParaFormNovoAluno.putExtra("aluno", alunoEscolhido);
                startActivity(vaiParaFormNovoAluno);
            }
        });

    }
}

