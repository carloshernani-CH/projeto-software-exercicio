package br.edu.insper.curso.service;

import br.edu.insper.curso.model.Curso;
import br.edu.insper.curso.repository.CursoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

  @Mock
  CursoRepository repo;

  @InjectMocks
  CursoService service;

  @Test
  void listar_retornaCursos() {
    when(repo.findAll()).thenReturn(List.of(new Curso()));
    var result = service.listar();
    assertThat(result).hasSize(1);
    verify(repo).findAll();
  }

  @Test
  void criar_defineDataCadastroESalva() {
    Curso req = new Curso();
    req.setTitulo("Java");

    // simula retorno do save com id preenchido
    Curso salvo = new Curso();
    salvo.setId(1);
    salvo.setTitulo("Java");
    salvo.setDataCadastro(LocalDate.now());
    when(repo.save(any(Curso.class))).thenReturn(salvo);

    var out = service.criar(req);

    // garante que passou pelo repo e retornou o salvo
    verify(repo).save(any(Curso.class));
    assertThat(out.getId()).isEqualTo(1);

    // opcional: captura o argumento para checar dataCadastro setada no service (se o service seta)
    ArgumentCaptor<Curso> captor = ArgumentCaptor.forClass(Curso.class);
    verify(repo).save(captor.capture());
    assertThat(captor.getValue().getDataCadastro()).isNotNull();
  }

  @Test
  void buscarPorId_encontra() {
    var c = new Curso(); c.setId(123);
    when(repo.findById(123)).thenReturn(Optional.of(c));

    var opt = service.buscarPorId(123);

    assertThat(opt).isPresent();
    verify(repo).findById(123);
  }

  @Test
  void buscarPorId_vazioQuandoNaoExiste() {
    when(repo.findById(999)).thenReturn(Optional.empty());
    var opt = service.buscarPorId(999);
    assertThat(opt).isEmpty();
    verify(repo).findById(999);
  }

  @Test
  void excluir_ok() {
    when(repo.existsById(1)).thenReturn(true);
    doNothing().when(repo).deleteById(1);

    boolean removed = service.excluir(1);

    assertThat(removed).isTrue();
    verify(repo).deleteById(1);
  }

  @Test
  void excluir_quandoNaoExiste_retornaFalseOuLanca() {
    when(repo.existsById(2)).thenReturn(false);

    // caso seu service retorne boolean:
    boolean removed = service.excluir(2);
    assertThat(removed).isFalse();

    // Se em vez disso ele lança exceção, troque pelo assert abaixo:
    // assertThatThrownBy(() -> service.excluir(2)).isInstanceOf(AlgumaExcecao.class);
  }
}
