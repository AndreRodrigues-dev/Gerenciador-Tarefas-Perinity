package com.api.apirest.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.apirest.Model.Departamento;
import com.api.apirest.Service.DepartamentoService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping
public class DepartamentoController {
    
    final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }
    
    @PostMapping("/departamento")
    public ResponseEntity<Object> salvarDepartamento(@RequestBody Departamento departamento) {
        return ResponseEntity.status(HttpStatus.OK).body(departamentoService.salvarDepartamento(departamento));
    }

    @GetMapping("/departamento")
    public ResponseEntity<List<Departamento>> buscarDepartamentos() {
        return ResponseEntity.status(HttpStatus.OK).body(departamentoService.buscarDepartamentos());
    }

    @GetMapping("/departamento/{id}")
    public ResponseEntity<Object> buscarDepartamentoPorId(@PathVariable(value = "id") Integer id) {
        Optional<Departamento> departamentoOptional = departamentoService.buscarDepartamentoPorId(id);
        if(!departamentoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Departamento não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(departamentoService.buscarDepartamentoPorId(id));
    }

    @DeleteMapping("/departamento/{id}")
    public ResponseEntity<Object> deletarDepartamento(@PathVariable(value = "id") Integer id) {
        Optional<Departamento> departamentoOptional = departamentoService.buscarDepartamentoPorId(id);
        if(!departamentoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Departamento não encontrado");
        }
        departamentoService.deletarDepartamento(departamentoOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Departamento deletado com sucesso");
    }

    @PutMapping("/departamento/{id}")
    public ResponseEntity<Object> alterarDepartamento(@PathVariable(value = "id") Integer id, @RequestBody Departamento departamento) {
        Optional<Departamento> departamentoOptional = departamentoService.buscarDepartamentoPorId(id);
        if(!departamentoOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Departamento não encontrado");
        }
        var novoDepartamento = departamentoOptional.get();
        novoDepartamento.setTitulo(departamento.getTitulo());
        return ResponseEntity.status(HttpStatus.OK).body(departamentoService.salvarDepartamento(novoDepartamento));
    }
}
