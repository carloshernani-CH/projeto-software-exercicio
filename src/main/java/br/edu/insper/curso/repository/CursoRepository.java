package br.edu.insper.curso.repository;

import br.edu.insper.curso.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Integer> {}
