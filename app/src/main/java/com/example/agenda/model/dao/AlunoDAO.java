package com.example.agenda.model.dao;

import com.example.agenda.model.entity.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private final static List<Aluno> alunos = new ArrayList<>();
    private static int contId = 1;

    public void salva(Aluno aluno) {
        aluno.setId(contId);
        alunos.add(aluno);
        contId++;
    }

    public void edita(Aluno aluno){
        Aluno alunoEncontrado = buscaAlunoPeloId(aluno);
        if(alunoEncontrado != null){
            int posicaoDoALuno = alunos.indexOf(alunoEncontrado);
            alunos.set(posicaoDoALuno, aluno);
        }
    }

    private Aluno buscaAlunoPeloId(Aluno aluno) {
        for (Aluno a: alunos) {
            if(a.getId() == aluno.getId()){
                return a;
            }
        }
        return null;
    }

    public List<Aluno> getAlunos() {
        return new ArrayList<>(alunos);
    }


    public void remove(Aluno aluno) {
        Aluno alunoDevolvido = buscaAlunoPeloId(aluno);
        if(alunoDevolvido != null){
            alunos.remove(alunoDevolvido);
        }
    }
}
