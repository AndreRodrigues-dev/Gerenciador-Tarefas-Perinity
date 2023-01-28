package com.api.apirest.Service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.api.apirest.Model.Departamento;
import com.api.apirest.Repository.DepartamentoRepository;

@Service
public class DepartamentoService{
    
    final DepartamentoRepository departamentoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    @Transactional
    public Departamento salvarDepartamento(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    public List<Departamento> buscarDepartamentos(){
        return departamentoRepository.findAll();
    }

    public Optional<Departamento> buscarDepartamentoPorId(Integer id) {
        return departamentoRepository.findById(id);
    }

    @Transactional
    public void deletarDepartamento(Departamento departamento) {
        departamentoRepository.delete(departamento);
    }
}
