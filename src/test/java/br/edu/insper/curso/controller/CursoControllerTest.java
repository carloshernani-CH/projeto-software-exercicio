package br.edu.insper.curso.controller;

import br.edu.insper.curso.model.Curso;
import br.edu.insper.curso.service.CursoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CursoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class CursoControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean
    private CursoService cursoService;

    @Test
    void listar_ok() throws Exception {
        Curso c = new Curso();
        c.setId(1);
        c.setTitulo("Spring");
        c.setDescricao("API");
        c.setInstrutor("João");
        c.setCargaHoraria(8);
        c.setDataCadastro(LocalDate.now());

        when(cursoService.listar()).thenReturn(List.of(c));

        mockMvc.perform(get("/api/cursos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titulo").value("Spring"));
    }

    @Test
    void criar_ok() throws Exception {
        Curso req = new Curso();
        req.setTitulo("Java");
        req.setDescricao("Do zero");
        req.setInstrutor("Ana");
        req.setCargaHoraria(10);

        Curso saved = new Curso();
        saved.setId(1);
        saved.setTitulo(req.getTitulo());
        saved.setDescricao(req.getDescricao());
        saved.setInstrutor(req.getInstrutor());
        saved.setCargaHoraria(req.getCargaHoraria());
        saved.setDataCadastro(LocalDate.now());

        when(cursoService.criar(any(Curso.class))).thenReturn(saved);

        mockMvc.perform(post("/api/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Java"));
    }

    @Test
    void excluir_notFound() throws Exception {
        // seu controller deve retornar 404 quando o service NÃO excluir
        when(cursoService.excluir(999)).thenReturn(false);

        mockMvc.perform(delete("/api/cursos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void excluir_ok() throws Exception {
        // ajuste o status abaixo caso seu controller retorne 200 em vez de 204
        when(cursoService.excluir(1)).thenReturn(true);

        mockMvc.perform(delete("/api/cursos/1"))
                .andExpect(status().isNoContent()); // troque para isOk() se seu controller devolver 200
    }
}
