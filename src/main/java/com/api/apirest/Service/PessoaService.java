package com.api.apirest.Service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.api.apirest.Model.Pessoa;
import com.api.apirest.Repository.PessoaRepository;

@Service
public class PessoaService{

    final PessoaRepository pessoaRepository;


    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public Pessoa salvarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> buscarPessoas() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> buscarPessoaPorId(Integer id) {
        return pessoaRepository.findById(id);
    }

    @Transactional
    public void deletarPessoa(Pessoa pessoa) {
        pessoaRepository.delete(pessoa);
    }
}
