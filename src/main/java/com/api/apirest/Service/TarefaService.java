package com.api.apirest.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.api.apirest.Model.Tarefa;
import com.api.apirest.Repository.TarefaRepository;

@Service
public class TarefaService{

    final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    @Transactional
    public Tarefa salvarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> buscarTarefas() {
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> buscarTarefaPorId(Integer id) {
        return tarefaRepository.findById(id);
    }

    @Transactional
    public void deletarTarefa(Tarefa tarefa) {
        tarefaRepository.delete(tarefa);
    }
}
