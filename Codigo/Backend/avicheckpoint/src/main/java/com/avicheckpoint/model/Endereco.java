package com.avicheckpoint.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Representa o endereço de um usuário no sistema.
 * Relacionamento de composição (1:1) com a classe Usuario.
 */
public class Endereco {
    
    @NotNull
    private Integer enderecoId;
    
    @NotNull(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter exatamente 8 dígitos")
    private String enderecoCEP;
    
    @NotNull(message = "Número é obrigatório")
    private Integer enderecoNumero;
    
    @Size(max = 100, message = "Complemento deve ter no máximo 100 caracteres")
    private String enderecoComplemento;
    
    @NotNull(message = "Estado é obrigatório")
    @Size(min = 2, max = 2, message = "Estado deve ter exatamente 2 caracteres")
    private String estado;
    
    @NotNull(message = "Cidade é obrigatória")
    @Size(min = 2, max = 100, message = "Cidade deve ter entre 2 e 100 caracteres")
    private String cidade;
    
    // Constructors
    public Endereco() {}
    
    public Endereco(Integer enderecoId, String enderecoCEP, Integer enderecoNumero, 
                    String enderecoComplemento, String estado, String cidade) {
        this.enderecoId = enderecoId;
        this.enderecoCEP = enderecoCEP;
        this.enderecoNumero = enderecoNumero;
        this.enderecoComplemento = enderecoComplemento;
        this.estado = estado;
        this.cidade = cidade;
    }
    
    // Getters and Setters
    public Integer getEnderecoId() {
        return enderecoId;
    }
    
    public void setEnderecoId(Integer enderecoId) {
        this.enderecoId = enderecoId;
    }
    
    public String getEnderecoCEP() {
        return enderecoCEP;
    }
    
    public void setEnderecoCEP(String enderecoCEP) {
        this.enderecoCEP = enderecoCEP;
    }
    
    public Integer getEnderecoNumero() {
        return enderecoNumero;
    }
    
    public void setEnderecoNumero(Integer enderecoNumero) {
        this.enderecoNumero = enderecoNumero;
    }
    
    public String getEnderecoComplemento() {
        return enderecoComplemento;
    }
    
    public void setEnderecoComplemento(String enderecoComplemento) {
        this.enderecoComplemento = enderecoComplemento;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    @Override
    public String toString() {
        return "Endereco{" +
                "enderecoId=" + enderecoId +
                ", enderecoCEP='" + enderecoCEP + '\'' +
                ", enderecoNumero=" + enderecoNumero +
                ", enderecoComplemento='" + enderecoComplemento + '\'' +
                ", estado='" + estado + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }
}