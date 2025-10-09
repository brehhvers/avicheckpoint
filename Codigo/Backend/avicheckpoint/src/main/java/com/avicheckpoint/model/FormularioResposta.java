package com.avicheckpoint.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Representa um formulário completo de respostas de um produtor.
 * Mapeia diretamente à estrutura valuesState do frontend.
 */
public class FormularioResposta {
    
    @NotNull
    private String formularioId;
    
    @NotNull
    private Integer produtorId;
    
    @NotNull
    private LocalDateTime dataPreenchimento;
    
    private LocalDateTime dataAtualizacao;
    
    @NotNull
    private StatusFormulario status;
    
    /**
     * Estrutura flexível que mapeia exatamente o valuesState do frontend.
     * 
     * Exemplo:
     * {
     *   "cadastro": {"p_nome": "João", "p_email": "joao@email.com"},
     *   "saude": {"s1": "Sim", "s2": "Espirros"},
     *   "nutricao": {"n1": "Ração industrial"},
     *   "avaliacao": {"a1": "Sim", "a2": "Não"},
     *   "doencas": {"d1": "Não", "d2": "Sim"}
     * }
     */
    @Size(max = 10000, message = "Dados do formulário muito extensos")
    private Map<String, Object> respostas = new HashMap<>();
    
    // Campos calculados após análise
    private ResultadoAnalise resultado;
    
    // Constructors
    public FormularioResposta() {}
    
    public FormularioResposta(String formularioId, Integer produtorId) {
        this.formularioId = formularioId;
        this.produtorId = produtorId;
        this.dataPreenchimento = LocalDateTime.now();
        this.status = StatusFormulario.RASCUNHO;
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
    
    // Métodos utilitários
    
    /**
     * Adiciona ou atualiza respostas de uma seção.
     */
    public void adicionarSecao(String secao, Map<String, Object> dadosSecao) {
        this.respostas.put(secao, dadosSecao);
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    /**
     * Obtém respostas de uma seção específica.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> obterSecao(String secao) {
        Object secaoObj = this.respostas.get(secao);
        if (secaoObj instanceof Map) {
            return (Map<String, Object>) secaoObj;
        }
        return new HashMap<>();
    }
    
    /**
     * Obtém resposta de uma pergunta específica.
     */
    public Object obterResposta(String secao, String pergunta) {
        Map<String, Object> dadosSecao = obterSecao(secao);
        return dadosSecao.get(pergunta);
    }
    
    /**
     * Verifica se o formulário está completo (todas as seções obrigatórias preenchidas).
     */
    public boolean isCompleto() {
        return respostas.containsKey("saude") && 
               respostas.containsKey("nutricao") && 
               respostas.containsKey("avaliacao") && 
               respostas.containsKey("doencas");
    }
    
    /**
     * Marca o formulário como submetido se estiver completo.
     */
    public void submeter() {
        if (isCompleto()) {
            this.status = StatusFormulario.SUBMETIDO;
            this.dataAtualizacao = LocalDateTime.now();
        } else {
            throw new RuntimeException("Formulário incompleto. Não é possível submeter.");
        }
    }
    
    @Override
    public String toString() {
        return "FormularioResposta{" +
                "formularioId='" + formularioId + '\'' +
                ", produtorId=" + produtorId +
                ", status=" + status +
                ", dataPreenchimento=" + dataPreenchimento +
                '}';
    }
}