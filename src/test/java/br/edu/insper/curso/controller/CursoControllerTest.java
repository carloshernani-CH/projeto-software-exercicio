package br.edu.insper.curso.controller;

import br.edu.insper.curso.model.Curso;
import br.edu.insper.curso.repository.CursoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CursoController.class)
class CursoControllerTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper om;
  @MockBean CursoRepository repo;

  @Test
  void listar_ok() throws Exception {
    when(repo.findAll()).thenReturn(Collections.emptyList());
    mvc.perform(get("/api/cursos"))
       .andExpect(status().isOk())
       .andExpect(content().json("[]"));
  }

  @Test
  void criar_ok() throws Exception {
    Curso salvo = new Curso();
    salvo.setId(1);
    salvo.setTitulo("T");
    when(repo.save(ArgumentMatchers.any())).thenReturn(salvo);

    Curso req = new Curso();
    req.setTitulo("T");
    req.setDescricao("D");
    req.setCargaHoraria(10);
    req.setInstrutor("I");

    mvc.perform(post("/api/cursos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(om.writeValueAsString(req)))
       .andExpect(status().isCreated())
       .andExpect(header().string("Location", "/api/cursos/1"))
       .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  void excluir_notFound() throws Exception {
    when(repo.existsById(1)).thenReturn(false);
    mvc.perform(delete("/api/cursos/1"))
       .andExpect(status().isNotFound());
  }
}
