package com.api.apirest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.apirest.Model.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer>{
    
}
