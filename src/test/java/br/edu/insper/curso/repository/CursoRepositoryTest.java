package br.edu.insper.curso.repository;

import br.edu.insper.curso.model.Curso;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CursoRepositoryTest {

  @Autowired CursoRepository repo;

  @Test
  void save_setsIdAndDate() {
    Curso c = new Curso();
    c.setTitulo("A");
    c.setDescricao("B");
    c.setCargaHoraria(8);
    c.setInstrutor("X");
    Curso salvo = repo.save(c);
    assertThat(salvo.getId()).isNotNull();
    assertThat(salvo.getDataCadastro()).isNotNull();
  }
}
