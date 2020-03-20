package com.example.agenda.model.dao;

import com.example.agenda.model.entity.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private final static List<Aluno> alunos = new ArrayList<>();
    private static int contId = 1;

    public void salvar(Aluno aluno) {
        aluno.setId(contId);
        alunos.add(aluno);
        contId++;
    }

    public void edita(Aluno aluno){
        Aluno alunoencontrado = null;
        for (Aluno a: alunos) {
            if(a.getId() == aluno.getId()){
                alunoencontrado = a;
            }
        }
        if(alunoencontrado != null){
            int posicaoDoALuno = alunos.indexOf(alunoencontrado);
            alunos.set(posicaoDoALuno, aluno);
        }
    }

    public List<Aluno> getAlunos() {
        return new ArrayList<>(alunos);
    }
}
