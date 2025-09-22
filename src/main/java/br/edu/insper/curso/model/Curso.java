package br.edu.insper.curso.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Curso {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String titulo;
  private String descricao;
  private Integer cargaHoraria; // em horas
  private String instrutor;

  private LocalDate dataCadastro;

  @PrePersist
  public void prePersist() {
    if (dataCadastro == null) {
      dataCadastro = LocalDate.now();
    }
  }

  // getters/setters
  public Integer getId() { return id; }
  public void setId(Integer id) { this.id = id; }

  public String getTitulo() { return titulo; }
  public void setTitulo(String titulo) { this.titulo = titulo; }

  public String getDescricao() { return descricao; }
  public void setDescricao(String descricao) { this.descricao = descricao; }

  public Integer getCargaHoraria() { return cargaHoraria; }
  public void setCargaHoraria(Integer cargaHoraria) { this.cargaHoraria = cargaHoraria; }

  public String getInstrutor() { return instrutor; }
  public void setInstrutor(String instrutor) { this.instrutor = instrutor; }

  public LocalDate getDataCadastro() { return dataCadastro; }
  public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
}
