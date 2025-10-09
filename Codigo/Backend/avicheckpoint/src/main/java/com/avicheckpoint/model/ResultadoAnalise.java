package com.avicheckpoint.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa o resultado da análise de um formulário.
 * Contém as categorias de feedback baseadas no etapa-hackaton.md.
 */
public class ResultadoAnalise {
    
    /**
     * Panorama geral da propriedade (marcado em azul no frontend).
     */
    private List<String> panorama = new ArrayList<>();
    
    /**
     * Pontos fortes da propriedade (marcado em verde no frontend).
     */
    private List<String> pontosFortes = new ArrayList<>();
    
    /**
     * Pontos que podem melhorar (marcado em laranja no frontend).
     */
    private List<String> melhorias = new ArrayList<>();
    
    /**
     * Comentários e recomendações (marcado em roxo no frontend).
     */
    private List<String> comentarios = new ArrayList<>();
    
    /**
     * Profissão recomendada baseada nas respostas.
     * Ex: "Medicina veterinária", "Zootecnia", "Medicina"
     */
    private String profissaoRecomendada;
    
    /**
     * Lista de veterinários recomendados na região.
     */
    private List<VeterinarioRecomendado> veterinariosProximos = new ArrayList<>();
    
    // Constructors
    public ResultadoAnalise() {}
    
    // Getters and Setters
    public List<String> getPanorama() {
        return panorama;
    }
    
    public void setPanorama(List<String> panorama) {
        this.panorama = panorama;
    }
    
    public List<String> getPontosFortes() {
        return pontosFortes;
    }
    
    public void setPontosFortes(List<String> pontosFortes) {
        this.pontosFortes = pontosFortes;
    }
    
    public List<String> getMelhorias() {
        return melhorias;
    }
    
    public void setMelhorias(List<String> melhorias) {
        this.melhorias = melhorias;
    }
    
    public List<String> getComentarios() {
        return comentarios;
    }
    
    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }
    
    public String getProfissaoRecomendada() {
        return profissaoRecomendada;
    }
    
    public void setProfissaoRecomendada(String profissaoRecomendada) {
        this.profissaoRecomendada = profissaoRecomendada;
    }
    
    public List<VeterinarioRecomendado> getVeterinariosProximos() {
        return veterinariosProximos;
    }
    
    public void setVeterinariosProximos(List<VeterinarioRecomendado> veterinariosProximos) {
        this.veterinariosProximos = veterinariosProximos;
    }
    
    // Métodos utilitários para adicionar itens
    
    public void addPanorama(String item) {
        this.panorama.add(item);
    }
    
    public void addPontoForte(String item) {
        this.pontosFortes.add(item);
    }
    
    public void addMelhoria(String item) {
        this.melhorias.add(item);
    }
    
    public void addComentario(String item) {
        this.comentarios.add(item);
    }
    
    /**
     * Classe interna para representar um veterinário recomendado.
     */
    public static class VeterinarioRecomendado {
        private String nome;
        private String formacao;
        private String uf;
        private String cidade;
        private String bio;
        
        // Constructors
        public VeterinarioRecomendado() {}
        
        public VeterinarioRecomendado(String nome, String formacao, String uf, String cidade, String bio) {
            this.nome = nome;
            this.formacao = formacao;
            this.uf = uf;
            this.cidade = cidade;
            this.bio = bio;
        }
        
        // Getters and Setters
        public String getNome() {
            return nome;
        }
        
        public void setNome(String nome) {
            this.nome = nome;
        }
        
        public String getFormacao() {
            return formacao;
        }
        
        public void setFormacao(String formacao) {
            this.formacao = formacao;
        }
        
        public String getUf() {
            return uf;
        }
        
        public void setUf(String uf) {
            this.uf = uf;
        }
        
        public String getCidade() {
            return cidade;
        }
        
        public void setCidade(String cidade) {
            this.cidade = cidade;
        }
        
        public String getBio() {
            return bio;
        }
        
        public void setBio(String bio) {
            this.bio = bio;
        }
    }
}