package com.avicheckpoint.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

/**
 * DTO para receber dados de formulário do frontend.
 * Mapeia exatamente a estrutura valuesState do JavaScript.
 */
public class FormularioRequestDTO {
    
    @NotNull(message = "ID do produtor é obrigatório")
    private Integer produtorId;
    
    /**
     * Dados do formulário organizados por seção.
     * Mapeia exatamente o valuesState do frontend:
     * 
     * {
     *   "cadastro": {"p_nome": "João", "p_email": "joao@email.com"},
     *   "saude": {"s1": "Sim", "s2": "Espirros"},
     *   "nutricao": {"n1": "Ração industrial"},
     *   "avaliacao": {"a1": "Sim", "a2": "Não"}, 
     *   "doencas": {"d1": "Não", "d2": "Sim"}
     * }
     */
    @NotNull(message = "Dados do formulário são obrigatórios")
    private Map<String, Object> respostas;
    
    /**
     * Indica se o formulário deve ser salvo como rascunho ou submetido.
     * true = submeter (completo), false = salvar rascunho
     */
    private boolean submeter = false;
    
    // Constructors
    public FormularioRequestDTO() {}
    
    public FormularioRequestDTO(Integer produtorId, Map<String, Object> respostas) {
        this.produtorId = produtorId;
        this.respostas = respostas;
    }
    
    // Getters and Setters
    public Integer getProdutorId() {
        return produtorId;
    }
    
    public void setProdutorId(Integer produtorId) {
        this.produtorId = produtorId;
    }
    
    public Map<String, Object> getRespostas() {
        return respostas;
    }
    
    public void setRespostas(Map<String, Object> respostas) {
        this.respostas = respostas;
    }
    
    public boolean isSubmeter() {
        return submeter;
    }
    
    public void setSubmeter(boolean submeter) {
        this.submeter = submeter;
    }
    
    @Override
    public String toString() {
        return "FormularioRequestDTO{" +
                "produtorId=" + produtorId +
                ", submeter=" + submeter +
                ", secoesCount=" + (respostas != null ? respostas.size() : 0) +
                '}';
    }
}