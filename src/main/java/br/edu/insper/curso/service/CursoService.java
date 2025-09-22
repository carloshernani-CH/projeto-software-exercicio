package br.edu.insper.curso.service;

import br.edu.insper.curso.model.Curso;
import br.edu.insper.curso.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

  private final CursoRepository repo;

  public CursoService(CursoRepository repo) {
    this.repo = repo;
  }

  public Curso criar(Curso c) {
    return repo.save(c);
  }

  public List<Curso> listar() {
    return repo.findAll();
  }

  public boolean excluir(Integer id) {
    if (!repo.existsById(id)) return false;
    repo.deleteById(id);
    return true;
  }
}
