// src/main/java/br/edu/insper/curso/service/CursoService.java
package br.edu.insper.curso.service;

import br.edu.insper.curso.model.Curso;
import br.edu.insper.curso.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;   // <-- IMPORT
import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

  private final CursoRepository repo;

  public CursoService(CursoRepository repo) {
    this.repo = repo;
  }

  public Curso criar(Curso c) {
    if (c.getDataCadastro() == null) {
      c.setDataCadastro(LocalDate.now());
    }
    return repo.save(c);
  }

  public List<Curso> listar() {
    return repo.findAll();
  }

  public Optional<Curso> buscarPorId(Integer id) {
    return repo.findById(id);
  }

  public boolean excluir(Integer id) {
    if (!repo.existsById(id)) return false;
    repo.deleteById(id);
    return true;
  }
}
