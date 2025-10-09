package com.avicheckpoint.repository;

import com.avicheckpoint.model.FormularioResposta;
import java.util.List;
import java.util.Optional;

/**
 * Interface para repositório de formulários de resposta.
 */
public interface FormularioRepository {
    
    /**
     * Salva um formulário de resposta.
     */
    FormularioResposta salvar(FormularioResposta formulario);
    
    /**
     * Busca um formulário por ID.
     */
    Optional<FormularioResposta> buscarPorId(String formularioId);
    
    /**
     * Lista todos os formulários de um produtor.
     */
    List<FormularioResposta> buscarPorProdutor(Integer produtorId);
    
    /**
     * Lista formulários por status.
     */
    List<FormularioResposta> buscarPorStatus(String status);
    
    /**
     * Atualiza um formulário existente.
     */
    FormularioResposta atualizar(FormularioResposta formulario);
    
    /**
     * Exclui um formulário.
     */
    boolean excluir(String formularioId);
    
    /**
     * Verifica se um formulário existe.
     */
    boolean existe(String formularioId);
    
    /**
     * Gera um novo ID único para formulário.
     */
    String gerarNovoId(Integer produtorId);
}