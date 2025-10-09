package com.avicheckpoint.dto;

import java.util.List;

/**
 * DTO para dashboard do produtor com métricas evolutivas.
 */
public class DashboardProdutorDTO {
    
    private Integer produtorId;
    private String nomeProdutor;
    private int totalFormularios;
    private int formulariosAnalisados;
    private int rascunhos;
    
    // Métricas evolutivas
    private Double pontuacaoMedia;
    private String tendenciaPontuacao; // "crescente", "decrescente", "estavel"
    private Integer ultimaPontuacao;
    private Integer penultimaPontuacao;
    
    // Estatísticas gerais
    private int totalPontosFortes;
    private int totalPontosAMelhorar;
    private int totalAlertas;
    private String categoriaAtual; // "Excelente", "Bom", "Atenção", "Crítico"
    
    // Histórico resumido (últimos 5 formulários)
    private List<HistoricoFormularioDTO> historicoRecente;
    
    // Alertas ativos
    private List<String> alertasAtivos;
    private boolean temAlertasCriticos;
    
    // Constructors
    public DashboardProdutorDTO() {}
    
    // Getters and Setters
    public Integer getProdutorId() {
        return produtorId;
    }
    
    public void setProdutorId(Integer produtorId) {
        this.produtorId = produtorId;
    }
    
    public String getNomeProdutor() {
        return nomeProdutor;
    }
    
    public void setNomeProdutor(String nomeProdutor) {
        this.nomeProdutor = nomeProdutor;
    }
    
    public int getTotalFormularios() {
        return totalFormularios;
    }
    
    public void setTotalFormularios(int totalFormularios) {
        this.totalFormularios = totalFormularios;
    }
    
    public int getFormulariosAnalisados() {
        return formulariosAnalisados;
    }
    
    public void setFormulariosAnalisados(int formulariosAnalisados) {
        this.formulariosAnalisados = formulariosAnalisados;
    }
    
    public int getRascunhos() {
        return rascunhos;
    }
    
    public void setRascunhos(int rascunhos) {
        this.rascunhos = rascunhos;
    }
    
    public Double getPontuacaoMedia() {
        return pontuacaoMedia;
    }
    
    public void setPontuacaoMedia(Double pontuacaoMedia) {
        this.pontuacaoMedia = pontuacaoMedia;
    }
    
    public String getTendenciaPontuacao() {
        return tendenciaPontuacao;
    }
    
    public void setTendenciaPontuacao(String tendenciaPontuacao) {
        this.tendenciaPontuacao = tendenciaPontuacao;
    }
    
    public Integer getUltimaPontuacao() {
        return ultimaPontuacao;
    }
    
    public void setUltimaPontuacao(Integer ultimaPontuacao) {
        this.ultimaPontuacao = ultimaPontuacao;
    }
    
    public Integer getPenultimaPontuacao() {
        return penultimaPontuacao;
    }
    
    public void setPenultimaPontuacao(Integer penultimaPontuacao) {
        this.penultimaPontuacao = penultimaPontuacao;
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
    
    public String getCategoriaAtual() {
        return categoriaAtual;
    }
    
    public void setCategoriaAtual(String categoriaAtual) {
        this.categoriaAtual = categoriaAtual;
    }
    
    public List<HistoricoFormularioDTO> getHistoricoRecente() {
        return historicoRecente;
    }
    
    public void setHistoricoRecente(List<HistoricoFormularioDTO> historicoRecente) {
        this.historicoRecente = historicoRecente;
    }
    
    public List<String> getAlertasAtivos() {
        return alertasAtivos;
    }
    
    public void setAlertasAtivos(List<String> alertasAtivos) {
        this.alertasAtivos = alertasAtivos;
    }
    
    public boolean isTemAlertasCriticos() {
        return temAlertasCriticos;
    }
    
    public void setTemAlertasCriticos(boolean temAlertasCriticos) {
        this.temAlertasCriticos = temAlertasCriticos;
    }
}