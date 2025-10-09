package com.avicheckpoint.model;

/**
 * Status de um formulário de respostas.
 */
public enum StatusFormulario {
    
    /**
     * Formulário salvo parcialmente, ainda em preenchimento.
     */
    RASCUNHO("rascunho"),
    
    /**
     * Formulário submetido e completo, aguardando análise.
     */
    SUBMETIDO("submetido"),
    
    /**
     * Formulário analisado, resultado disponível.
     */
    ANALISADO("analisado");
    
    private final String valor;
    
    StatusFormulario(String valor) {
        this.valor = valor;
    }
    
    public String getValor() {
        return valor;
    }
    
    public static StatusFormulario fromString(String valor) {
        for (StatusFormulario status : values()) {
            if (status.valor.equals(valor)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + valor);
    }
}