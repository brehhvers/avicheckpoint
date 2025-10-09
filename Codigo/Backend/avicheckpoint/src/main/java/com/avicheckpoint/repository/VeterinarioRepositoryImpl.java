package com.avicheckpoint.repository;

import com.avicheckpoint.model.Veterinario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação do repositório para Veterinario usando persistência em arquivos JSON.
 */
@Repository
public class VeterinarioRepositoryImpl implements UsuarioRepository<Veterinario> {
    
    private final ObjectMapper objectMapper;
    private final String filePath;
    
    public VeterinarioRepositoryImpl() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.filePath = "data/usuarios/veterinarios.json";
        
        // Criar diretório se não existir
        createDirectoryIfNotExists();
    }
    
    private void createDirectoryIfNotExists() {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
    }
    
    private List<Veterinario> lerTodos() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<List<Veterinario>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    private void salvarTodos(List<Veterinario> veterinarios) {
        try {
            createDirectoryIfNotExists();
            objectMapper.writeValue(new File(filePath), veterinarios);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar veterinarios: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Veterinario salvar(Veterinario veterinario) {
        List<Veterinario> veterinarios = lerTodos();
        
        if (veterinario.getUsuarioId() == null) {
            veterinario.setUsuarioId(gerarProximoId());
            veterinario.setDataCriacao(LocalDateTime.now());
        }
        
        veterinario.setDataAtualizacao(LocalDateTime.now());
        
        // Remover se já existir (para atualização)
        veterinarios.removeIf(v -> v.getUsuarioId().equals(veterinario.getUsuarioId()));
        veterinarios.add(veterinario);
        
        salvarTodos(veterinarios);
        return veterinario;
    }
    
    @Override
    public Optional<Veterinario> buscarPorId(Integer id) {
        return lerTodos().stream()
                .filter(v -> v.getUsuarioId().equals(id))
                .findFirst();
    }
    
    @Override
    public Optional<Veterinario> buscarPorEmail(String email) {
        return lerTodos().stream()
                .filter(v -> v.getUsuarioEmail().equalsIgnoreCase(email))
                .findFirst();
    }
    
    @Override
    public List<Veterinario> listarTodos() {
        return lerTodos();
    }
    
    @Override
    public List<Veterinario> buscarPorEstado(String estado) {
        return lerTodos().stream()
                .filter(v -> v.getEndereco() != null && 
                           estado.equalsIgnoreCase(v.getEndereco().getEstado()))
                .toList();
    }
    
    /**
     * Busca veterinários por formação/especialidade.
     */
    public List<Veterinario> buscarPorFormacao(String formacao) {
        return lerTodos().stream()
                .filter(v -> v.getFormacao() != null && 
                           v.getFormacao().toLowerCase().contains(formacao.toLowerCase()))
                .toList();
    }
    
    /**
     * Busca veterinários por região e especialidade (para recomendações).
     */
    public List<Veterinario> buscarParaRecomendacao(String estado, String especialidade) {
        return lerTodos().stream()
                .filter(v -> {
                    boolean mesmoEstado = v.getEndereco() != null && 
                                        estado.equalsIgnoreCase(v.getEndereco().getEstado());
                    
                    boolean temEspecialidade = especialidade == null || 
                                             (v.getFormacao() != null && 
                                              v.getFormacao().toLowerCase().contains(especialidade.toLowerCase()));
                    
                    return mesmoEstado && temEspecialidade;
                })
                .limit(5) // Limitar a 5 recomendações
                .toList();
    }
    
    @Override
    public Veterinario atualizar(Veterinario veterinario) {
        return salvar(veterinario);
    }
    
    @Override
    public boolean excluir(Integer id) {
        List<Veterinario> veterinarios = lerTodos();
        boolean removido = veterinarios.removeIf(v -> v.getUsuarioId().equals(id));
        
        if (removido) {
            salvarTodos(veterinarios);
        }
        
        return removido;
    }
    
    @Override
    public boolean existePorEmail(String email) {
        return buscarPorEmail(email).isPresent();
    }
    
    @Override
    public Integer gerarProximoId() {
        List<Veterinario> veterinarios = lerTodos();
        return veterinarios.stream()
                .mapToInt(v -> v.getUsuarioId())
                .max()
                .orElse(0) + 1;
    }
}