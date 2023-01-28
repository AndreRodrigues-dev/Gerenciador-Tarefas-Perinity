package com.api.apirest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.apirest.Model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {
    
}
