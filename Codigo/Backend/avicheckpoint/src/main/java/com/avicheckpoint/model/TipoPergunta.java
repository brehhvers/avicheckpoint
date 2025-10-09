package com.avicheckpoint.model;

/**
 * Enumeração dos tipos de pergunta suportados pelo sistema.
 * Mapeia diretamente aos tipos do frontend.
 */
public enum TipoPergunta {
    
    /**
     * Pergunta de sim/não.
     * Usado para perguntas como "Você observou aves com sinais de doença?"
     */
    YESNO("yesno"),
    
    /**
     * Pergunta de seleção única (dropdown).
     * Usado para perguntas com opções pré-definidas.
     */
    SELECT("select"),
    
    /**
     * Campo de texto livre.
     * Usado para campos como nome, email.
     */
    TEXT("text"),
    
    /**
     * Campo de número.
     * Usado para campos numéricos.
     */
    NUMBER("number"),
    
    /**
     * Campo de texto longo (textarea).
     * Usado para campos como bio, comentários.
     */
    TEXTAREA("textarea"),
    
    /**
     * Seletor de coloração da gema (1-16).
     * Tipo especial do sistema.
     */
    YOLK("yolk");
    
    private final String valor;
    
    TipoPergunta(String valor) {
        this.valor = valor;
    }
    
    public String getValor() {
        return valor;
    }
    
    public static TipoPergunta fromString(String valor) {
        for (TipoPergunta tipo : values()) {
            if (tipo.valor.equals(valor)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de pergunta inválido: " + valor);
    }
}