package com.avicheckpoint.dto;

import com.avicheckpoint.model.StatusFormulario;

import java.time.LocalDateTime;

/**
 * DTO para resumo de formulários no histórico.
 * Usado em listagens e dashboards.
 */
public class HistoricoFormularioDTO {
    
    private String formularioId;
    private LocalDateTime dataPreenchimento;
    private StatusFormulario status;
    private Integer pontuacaoGeral;
    private String panoramaGeral;
    private int totalPontosFortes;
    private int totalPontosAMelhorar;
    private int totalAlertas;
    private boolean completo;
    
    // Constructors
    public HistoricoFormularioDTO() {}
    
    public HistoricoFormularioDTO(String formularioId, LocalDateTime dataPreenchimento, 
                                 StatusFormulario status, Integer pontuacaoGeral) {
        this.formularioId = formularioId;
        this.dataPreenchimento = dataPreenchimento;
        this.status = status;
        this.pontuacaoGeral = pontuacaoGeral;
    }
    
    // Getters and Setters
    public String getFormularioId() {
        return formularioId;
    }
    
    public void setFormularioId(String formularioId) {
        this.formularioId = formularioId;
    }
    
    public LocalDateTime getDataPreenchimento() {
        return dataPreenchimento;
    }
    
    public void setDataPreenchimento(LocalDateTime dataPreenchimento) {
        this.dataPreenchimento = dataPreenchimento;
    }
    
    public StatusFormulario getStatus() {
        return status;
    }
    
    public void setStatus(StatusFormulario status) {
        this.status = status;
    }
    
    public Integer getPontuacaoGeral() {
        return pontuacaoGeral;
    }
    
    public void setPontuacaoGeral(Integer pontuacaoGeral) {
        this.pontuacaoGeral = pontuacaoGeral;
    }
    
    public String getPanoramaGeral() {
        return panoramaGeral;
    }
    
    public void setPanoramaGeral(String panoramaGeral) {
        this.panoramaGeral = panoramaGeral;
    }
    
    public int getTotalPontosFortes() {
        return totalPontosFortes;
    }
    
    public void setTotalPontosFortes(int totalPontosFortes) {
        this.totalPontosFortes = totalPontosFortes;
    }
    
    public int getTotalPontosAMelhorar() {
        return totalPontosAMelhorar;
    }
    
    public void setTotalPontosAMelhorar(int totalPontosAMelhorar) {
        this.totalPontosAMelhorar = totalPontosAMelhorar;
    }
    
    public int getTotalAlertas() {
        return totalAlertas;
    }
    
    public void setTotalAlertas(int totalAlertas) {
        this.totalAlertas = totalAlertas;
    }
    
    public boolean isCompleto() {
        return completo;
    }
    
    public void setCompleto(boolean completo) {
        this.completo = completo;
    }
}