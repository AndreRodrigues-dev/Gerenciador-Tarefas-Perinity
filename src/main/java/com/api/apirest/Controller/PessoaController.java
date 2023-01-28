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
import com.api.apirest.Model.Pessoa;
import com.api.apirest.Service.PessoaService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping
public class PessoaController {
    final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping("/pessoa")
    public ResponseEntity<Object> salvarPessoa(@RequestBody Pessoa pessoa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.salvarPessoa(pessoa));
    }

    @GetMapping("/pessoa")
    public ResponseEntity<List<Pessoa>> buscarPessoas() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.buscarPessoas());
    }
    
    @GetMapping("/pessoa/{id}")
    public ResponseEntity<Object> buscarPessoaPorId(@PathVariable(value = "id") Integer id) {
        Optional<Pessoa> pessoaOptional = pessoaService.buscarPessoaPorId(id);
        if(!pessoaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.buscarPessoaPorId(id));
    }

    @DeleteMapping("/pessoa/{id}")
    public ResponseEntity<Object> deletarPessoa(@PathVariable(value = "id") Integer id) {
        Optional<Pessoa> pessoaOptional = pessoaService.buscarPessoaPorId(id);
        if(!pessoaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
        }
        pessoaService.deletarPessoa(pessoaOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Pessoa deletada com sucesso");
    }

    @PutMapping("/pessoa/{id}")
    public ResponseEntity<Object> alterarPessoa(@PathVariable(value = "id") Integer id, @RequestBody Pessoa pessoa) {
        Optional<Pessoa> pessoaOptional = pessoaService.buscarPessoaPorId(id);
        if(!pessoaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
        }
        var novaPessoa = pessoaOptional.get();
        novaPessoa.setNome(pessoa.getNome());
        novaPessoa.setIdDepartamento(pessoa.getIdDepartamento());
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.salvarPessoa(novaPessoa));
    }
}
