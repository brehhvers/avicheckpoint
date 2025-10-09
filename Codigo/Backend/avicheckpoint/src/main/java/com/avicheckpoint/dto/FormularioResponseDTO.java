package com.avicheckpoint.dto;

import com.avicheckpoint.model.ResultadoAnalise;
import com.avicheckpoint.model.StatusFormulario;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO para resposta de formulários.
 * Retorna dados do formulário com resultado da análise.
 */
public class FormularioResponseDTO {
    
    private String formularioId;
    private Integer produtorId;
    private LocalDateTime dataPreenchimento;
    private LocalDateTime dataAtualizacao;
    private StatusFormulario status;
    private Map<String, Object> respostas;
    private ResultadoAnalise resultado;
    private boolean completo;
    
    // Constructors
    public FormularioResponseDTO() {}
    
    public FormularioResponseDTO(String formularioId, Integer produtorId, 
                               StatusFormulario status, boolean completo) {
        this.formularioId = formularioId;
        this.produtorId = produtorId;
        this.status = status;
        this.completo = completo;
    }
    
    // Getters and Setters
    public String getFormularioId() {
        return formularioId;
    }
    
    public void setFormularioId(String formularioId) {
        this.formularioId = formularioId;
    }
    
    public Integer getProdutorId() {
        return produtorId;
    }
    
    public void setProdutorId(Integer produtorId) {
        this.produtorId = produtorId;
    }
    
    public LocalDateTime getDataPreenchimento() {
        return dataPreenchimento;
    }
    
    public void setDataPreenchimento(LocalDateTime dataPreenchimento) {
        this.dataPreenchimento = dataPreenchimento;
    }
    
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
    
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
    
    public StatusFormulario getStatus() {
        return status;
    }
    
    public void setStatus(StatusFormulario status) {
        this.status = status;
    }
    
    public Map<String, Object> getRespostas() {
        return respostas;
    }
    
    public void setRespostas(Map<String, Object> respostas) {
        this.respostas = respostas;
    }
    
    public ResultadoAnalise getResultado() {
        return resultado;
    }
    
    public void setResultado(ResultadoAnalise resultado) {
        this.resultado = resultado;
    }
    
    public boolean isCompleto() {
        return completo;
    }
    
    public void setCompleto(boolean completo) {
        this.completo = completo;
    }
}