package com.avicheckpoint.dto;

import java.util.List;

/**
 * DTO para recomendação de veterinários baseada na análise.
 */
public class VeterinarioRecomendadoDTO {
    
    private Integer veterinarioId;
    private String nome;
    private String email;
    private String telefone;
    private String crmv; // Registro profissional
    
    // Localização
    private String endereco;
    private String cidade;
    private String uf;
    private String cep;
    private Double latitude;
    private Double longitude;
    
    // Especialização
    private List<String> especialidades;
    private String bio;
    private Integer anosExperiencia;
    
    // Métricas de recomendação
    private Double distanciaKm;
    private Integer pontuacaoCompatibilidade; // 0-100
    private String motivoRecomendacao;
    private Boolean disponivel;
    
    // Avaliações
    private Double avaliacaoMedia;
    private Integer totalAvaliacoes;
    
    // Constructors
    public VeterinarioRecomendadoDTO() {}
    
    // Getters and Setters
    public Integer getVeterinarioId() {
        return veterinarioId;
    }
    
    public void setVeterinarioId(Integer veterinarioId) {
        this.veterinarioId = veterinarioId;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getCrmv() {
        return crmv;
    }
    
    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public String getUf() {
        return uf;
    }
    
    public void setUf(String uf) {
        this.uf = uf;
    }
    
    public String getCep() {
        return cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public List<String> getEspecialidades() {
        return especialidades;
    }
    
    public void setEspecialidades(List<String> especialidades) {
        this.especialidades = especialidades;
    }
    
    public String getBio() {
        return bio;
    }
    
    public void setBio(String bio) {
        this.bio = bio;
    }
    
    public Integer getAnosExperiencia() {
        return anosExperiencia;
    }
    
    public void setAnosExperiencia(Integer anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }
    
    public Double getDistanciaKm() {
        return distanciaKm;
    }
    
    public void setDistanciaKm(Double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }
    
    public Integer getPontuacaoCompatibilidade() {
        return pontuacaoCompatibilidade;
    }
    
    public void setPontuacaoCompatibilidade(Integer pontuacaoCompatibilidade) {
        this.pontuacaoCompatibilidade = pontuacaoCompatibilidade;
    }
    
    public String getMotivoRecomendacao() {
        return motivoRecomendacao;
    }
    
    public void setMotivoRecomendacao(String motivoRecomendacao) {
        this.motivoRecomendacao = motivoRecomendacao;
    }
    
    public Boolean getDisponivel() {
        return disponivel;
    }
    
    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }
    
    public Double getAvaliacaoMedia() {
        return avaliacaoMedia;
    }
    
    public void setAvaliacaoMedia(Double avaliacaoMedia) {
        this.avaliacaoMedia = avaliacaoMedia;
    }
    
    public Integer getTotalAvaliacoes() {
        return totalAvaliacoes;
    }
    
    public void setTotalAvaliacoes(Integer totalAvaliacoes) {
        this.totalAvaliacoes = totalAvaliacoes;
    }
}