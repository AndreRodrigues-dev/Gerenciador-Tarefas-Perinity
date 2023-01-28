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
import com.api.apirest.Model.Tarefa;
import com.api.apirest.Service.PessoaService;
import com.api.apirest.Service.TarefaService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping
public class TarefaController {

    
    final TarefaService tarefaService;
    final PessoaService pessoaService;

    public TarefaController(TarefaService tarefaService, PessoaService pessoaService) {
        this.tarefaService = tarefaService;
        this.pessoaService = pessoaService;
    }

    @PostMapping("/tarefa")
    public ResponseEntity<Object> salvarTarefa(@RequestBody Tarefa tarefa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.salvarTarefa(tarefa));
    }

    @GetMapping("/tarefa")
    public ResponseEntity<List<Tarefa>> buscarTarefas() {
        return ResponseEntity.status(HttpStatus.OK).body(tarefaService.buscarTarefas());
    }

    @GetMapping("/tarefa/{id}")
    public ResponseEntity<Object> buscarTarefaPorId(@PathVariable(value = "id") Integer id) {
        Optional<Tarefa> tarefaOptional = tarefaService.buscarTarefaPorId(id);
        if(!tarefaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(tarefaService.buscarTarefaPorId(id));
    }

    @DeleteMapping("/tarefa/{id}")
    public ResponseEntity<Object> deletarTarefa(@PathVariable(value = "id") Integer id) {
        Optional<Tarefa> tarefaOptional = tarefaService.buscarTarefaPorId(id);
        if(!tarefaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        }
        tarefaService.deletarTarefa(tarefaOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Tarefa foi deletada com sucesso");
    }

    @PutMapping("/tarefa/{id}")
    public ResponseEntity<Object> alterarTarefa(@PathVariable(value = "id") Integer id, @RequestBody Tarefa tarefa){
        Optional<Tarefa> tarefaOptional = tarefaService.buscarTarefaPorId(id);
        if(!tarefaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        }
        var novaTarefa = tarefaOptional.get();
        novaTarefa.setTitulo(tarefa.getTitulo());
        novaTarefa.setDescricao(tarefa.getDescricao());
        novaTarefa.setPrazo(tarefa.getPrazo());
        novaTarefa.setIdDepartamento(tarefa.getIdDepartamento());
        novaTarefa.setDuracao(tarefa.getDuracao());
        novaTarefa.setIdPessoa(tarefa.getIdPessoa());
        return ResponseEntity.status(HttpStatus.OK).body(tarefaService.salvarTarefa(novaTarefa));
    }

    @PutMapping("/finalizar/tarefa/{id}")
    public ResponseEntity<Object> finalizarTarefa(@PathVariable(value = "id") Integer id){
        Optional<Tarefa> tarefaOptional = tarefaService.buscarTarefaPorId(id);
        if(!tarefaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        }
        var novaTarefa = tarefaOptional.get();
        novaTarefa.setFinalizado(true);
        return ResponseEntity.status(HttpStatus.OK).body(tarefaService.salvarTarefa(novaTarefa));
    }

     /* @PutMapping("/finalizar/tarefa/{id}")
    public ResponseEntity<Object> finalizarTarefa(@PathVariable(value = "id") Integer id, @RequestBody Tarefa tarefa){
        Optional<Tarefa> tarefaOptional = tarefaService.buscarTarefaPorId(id);
        if(!tarefaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        }
        var novaTarefa = tarefaOptional.get();
        novaTarefa.setFinalizado(tarefa.getFinalizado());
        return ResponseEntity.status(HttpStatus.OK).body(tarefaService.salvarTarefa(novaTarefa));
    } */

    @PutMapping("/alocar/pessoa/{id}")
    public ResponseEntity<Object> alocarPessoaTarefa(@PathVariable(value = "id") Integer idPessoa){
        Optional<Pessoa> pessoaOptional = pessoaService.buscarPessoaPorId(idPessoa);
        var pessoa = pessoaOptional.get();
        List<Tarefa> listaTarefas = tarefaService.buscarTarefas();
        for (Tarefa tarefa : listaTarefas) {
            if(tarefa.getIdPessoa() == null && tarefa.getIdDepartamento() == pessoa.getIdDepartamento()) {
                tarefa.setIdPessoa(idPessoa);
                return ResponseEntity.status(HttpStatus.OK).body(tarefaService.salvarTarefa(tarefa));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado nenhuma tarefa disponível para este departamento");
    }
}


