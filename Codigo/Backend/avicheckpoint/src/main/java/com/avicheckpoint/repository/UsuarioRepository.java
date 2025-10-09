package com.avicheckpoint.repository;

import com.avicheckpoint.model.Usuario;
import java.util.List;
import java.util.Optional;

/**
 * Interface base para repositórios de usuários.
 * Define operações CRUD padrão.
 */
public interface UsuarioRepository<T extends Usuario> {
    
    /**
     * Salva um usuário no repositório.
     */
    T salvar(T usuario);
    
    /**
     * Busca um usuário por ID.
     */
    Optional<T> buscarPorId(Integer id);
    
    /**
     * Busca um usuário por email.
     */
    Optional<T> buscarPorEmail(String email);
    
    /**
     * Lista todos os usuários.
     */
    List<T> listarTodos();
    
    /**
     * Busca usuários por estado (UF).
     */
    List<T> buscarPorEstado(String estado);
    
    /**
     * Atualiza um usuário existente.
     */
    T atualizar(T usuario);
    
    /**
     * Exclui um usuário por ID.
     */
    boolean excluir(Integer id);
    
    /**
     * Verifica se um email já existe.
     */
    boolean existePorEmail(String email);
    
    /**
     * Gera o próximo ID disponível.
     */
    Integer gerarProximoId();
}