package br.edu.insper.curso.controller;

import br.edu.insper.curso.model.Curso;
import br.edu.insper.curso.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

  private final CursoService service;

  public CursoController(CursoService service) {
    this.service = service;
  }

  // POST /api/cursos  -> cadastrar
  @PostMapping
  public ResponseEntity<Curso> criar(@RequestBody Curso c) {
    Curso salvo = service.criar(c);
    return ResponseEntity
        .created(URI.create("/api/cursos/" + salvo.getId()))
        .body(salvo);
  }

  // GET /api/cursos   -> listar todos
  @GetMapping
  public List<Curso> listar() {
    return service.listar();
  }

  // DELETE /api/cursos/{id} -> excluir
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Integer id) {
    if (service.excluir(id)) return ResponseEntity.noContent().build();
    return ResponseEntity.notFound().build();
  }
}
