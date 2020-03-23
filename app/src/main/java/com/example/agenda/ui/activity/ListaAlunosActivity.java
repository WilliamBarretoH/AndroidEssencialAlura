package com.example.agenda.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.model.dao.AlunoDAO;
import com.example.agenda.model.entity.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final String titulo_appbar = "Lista de alunos";
    private final AlunoDAO alunoDao = new AlunoDAO();
    private ArrayAdapter<Aluno> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(titulo_appbar);

        configuraBotaoSalvarAluno();
        configuraListaDeAlunos();

        alunoDao.salva(new Aluno("william", "122312", "william@email.com"));
        alunoDao.salva(new Aluno("giovana", "122312", "gi@email.com"));

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        adapter.addAll(alunoDao.getAlunos());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_alunos_menu_remover) {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Aluno alunoEscolhido = adapter.getItem(menuInfo.position);

            deletarCaixaDeDialogo(alunoEscolhido.getId(), alunoEscolhido);
        }
        return super.onContextItemSelected(item);
    }

    private void configuraBotaoSalvarAluno() {
        FloatingActionButton botaoSalvarNovoAluno = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        botaoSalvarNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormNovoAluno();
            }
        });
    }

    private void abreFormNovoAluno() {
        startActivity(new Intent(ListaAlunosActivity.this,
                FormAlunoActivity.class));
    }

    private void configuraListaDeAlunos() {
        ListView listaDeALunos = findViewById(R.id.activity_lista_de_alunos_list_view);
        final List<Aluno> alunos = alunoDao.getAlunos();

        configuraAdapterList(listaDeALunos, alunos);
        configuraListenerDeCliquePorItem(listaDeALunos, alunos);
        registerForContextMenu(listaDeALunos);
    }

    private void configuraListenerDeCliquePorItem(ListView listaDeALunos, final List<Aluno> alunos) {
        listaDeALunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno alunoEscolhido = alunos.get(position);
                Intent vaiParaFormNovoAluno = new Intent(ListaAlunosActivity.this,
                        FormAlunoActivity.class);
                vaiParaFormNovoAluno.putExtra("aluno", alunoEscolhido);
                startActivity(vaiParaFormNovoAluno);
            }
        });
    }

    private void configuraAdapterList(ListView listaDeALunos, List<Aluno> alunos) {
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                alunos);
        listaDeALunos.setAdapter(adapter);
    }

    private void deletarCaixaDeDialogo(final int position, final Aluno aluno){
        new AlertDialog.Builder(this)
                .setTitle("Deletando aluno")
                .setMessage("Tem certeza que deseja deletar o aluno")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alunoDao.remove(aluno);
                        adapter.remove(aluno);
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

}

