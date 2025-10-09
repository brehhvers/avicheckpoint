package com.avicheckpoint.repository;

import com.avicheckpoint.model.Produtor;
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
 * Implementação do repositório para Produtor usando persistência em arquivos JSON.
 */
@Repository
public class ProdutorRepositoryImpl implements UsuarioRepository<Produtor> {
    
    private final ObjectMapper objectMapper;
    private final String filePath;
    
    public ProdutorRepositoryImpl() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.filePath = "data/usuarios/produtores.json";
        
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
    
    private List<Produtor> lerTodos() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<List<Produtor>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    private void salvarTodos(List<Produtor> produtores) {
        try {
            createDirectoryIfNotExists();
            objectMapper.writeValue(new File(filePath), produtores);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar produtores: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Produtor salvar(Produtor produtor) {
        List<Produtor> produtores = lerTodos();
        
        if (produtor.getUsuarioId() == null) {
            produtor.setUsuarioId(gerarProximoId());
            produtor.setDataCriacao(LocalDateTime.now());
        }
        
        produtor.setDataAtualizacao(LocalDateTime.now());
        
        // Remover se já existir (para atualização)
        produtores.removeIf(p -> p.getUsuarioId().equals(produtor.getUsuarioId()));
        produtores.add(produtor);
        
        salvarTodos(produtores);
        return produtor;
    }
    
    @Override
    public Optional<Produtor> buscarPorId(Integer id) {
        return lerTodos().stream()
                .filter(p -> p.getUsuarioId().equals(id))
                .findFirst();
    }
    
    @Override
    public Optional<Produtor> buscarPorEmail(String email) {
        return lerTodos().stream()
                .filter(p -> p.getUsuarioEmail().equalsIgnoreCase(email))
                .findFirst();
    }
    
    @Override
    public List<Produtor> listarTodos() {
        return lerTodos();
    }
    
    @Override
    public List<Produtor> buscarPorEstado(String estado) {
        return lerTodos().stream()
                .filter(p -> p.getEndereco() != null && 
                           estado.equalsIgnoreCase(p.getEndereco().getEstado()))
                .toList();
    }
    
    @Override
    public Produtor atualizar(Produtor produtor) {
        return salvar(produtor);
    }
    
    @Override
    public boolean excluir(Integer id) {
        List<Produtor> produtores = lerTodos();
        boolean removido = produtores.removeIf(p -> p.getUsuarioId().equals(id));
        
        if (removido) {
            salvarTodos(produtores);
        }
        
        return removido;
    }
    
    @Override
    public boolean existePorEmail(String email) {
        return buscarPorEmail(email).isPresent();
    }
    
    @Override
    public Integer gerarProximoId() {
        List<Produtor> produtores = lerTodos();
        return produtores.stream()
                .mapToInt(p -> p.getUsuarioId())
                .max()
                .orElse(0) + 1;
    }
}