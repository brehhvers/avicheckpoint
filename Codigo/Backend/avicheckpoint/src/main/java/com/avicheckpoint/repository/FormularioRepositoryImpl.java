package com.avicheckpoint.repository;

import com.avicheckpoint.model.FormularioResposta;
import com.avicheckpoint.model.StatusFormulario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação do repositório de formulários usando persistência em arquivos JSON.
 * Organiza os formulários por produtor em diretórios separados.
 */
@Repository
public class FormularioRepositoryImpl implements FormularioRepository {
    
    private final ObjectMapper objectMapper;
    private final String baseDir = "data/formularios";
    
    public FormularioRepositoryImpl() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }
    
    /**
     * Cria o diretório para um produtor se não existir.
     */
    private void criarDiretorioProdutor(Integer produtorId) {
        File dir = new File(baseDir + "/" + produtorId);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Obtém o caminho do arquivo para um formulário específico.
     */
    private String obterCaminhoArquivo(Integer produtorId, String formularioId) {
        return baseDir + "/" + produtorId + "/" + formularioId + ".json";
    }
    
    /**
     * Obtém o caminho do arquivo de índice para um produtor.
     */
    private String obterCaminhoIndice(Integer produtorId) {
        return baseDir + "/" + produtorId + "/index.json";
    }
    
    /**
     * Lê o índice de formulários de um produtor.
     */
    private List<String> lerIndice(Integer produtorId) {
        try {
            File indexFile = new File(obterCaminhoIndice(produtorId));
            if (!indexFile.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(indexFile, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Salva o índice de formulários de um produtor.
     */
    private void salvarIndice(Integer produtorId, List<String> formularios) {
        try {
            criarDiretorioProdutor(produtorId);
            objectMapper.writeValue(new File(obterCaminhoIndice(produtorId)), formularios);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar índice: " + e.getMessage(), e);
        }
    }
    
    @Override
    public FormularioResposta salvar(FormularioResposta formulario) {
        try {
            // Gerar ID se não existir
            if (formulario.getFormularioId() == null) {
                formulario.setFormularioId(gerarNovoId(formulario.getProdutorId()));
                formulario.setDataPreenchimento(LocalDateTime.now());
            }
            
            formulario.setDataAtualizacao(LocalDateTime.now());
            
            // Criar diretório e salvar arquivo
            criarDiretorioProdutor(formulario.getProdutorId());
            String caminhoArquivo = obterCaminhoArquivo(formulario.getProdutorId(), 
                                                       formulario.getFormularioId());
            objectMapper.writeValue(new File(caminhoArquivo), formulario);
            
            // Atualizar índice
            List<String> indice = lerIndice(formulario.getProdutorId());
            if (!indice.contains(formulario.getFormularioId())) {
                indice.add(formulario.getFormularioId());
                salvarIndice(formulario.getProdutorId(), indice);
            }
            
            return formulario;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar formulário: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Optional<FormularioResposta> buscarPorId(String formularioId) {
        try {
            // Buscar em todos os diretórios de produtores
            File baseFolder = new File(baseDir);
            if (!baseFolder.exists()) {
                return Optional.empty();
            }
            
            for (File produtorDir : baseFolder.listFiles(File::isDirectory)) {
                File formularioFile = new File(produtorDir, formularioId + ".json");
                if (formularioFile.exists()) {
                    FormularioResposta formulario = objectMapper.readValue(
                        formularioFile, FormularioResposta.class);
                    return Optional.of(formulario);
                }
            }
            
            return Optional.empty();
        } catch (IOException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public List<FormularioResposta> buscarPorProdutor(Integer produtorId) {
        List<FormularioResposta> formularios = new ArrayList<>();
        List<String> indice = lerIndice(produtorId);
        
        for (String formularioId : indice) {
            try {
                File arquivo = new File(obterCaminhoArquivo(produtorId, formularioId));
                if (arquivo.exists()) {
                    FormularioResposta formulario = objectMapper.readValue(
                        arquivo, FormularioResposta.class);
                    formularios.add(formulario);
                }
            } catch (IOException e) {
                // Continuar com próximo formulário em caso de erro
            }
        }
        
        // Ordenar por data de preenchimento (mais recente primeiro)
        formularios.sort((f1, f2) -> f2.getDataPreenchimento().compareTo(f1.getDataPreenchimento()));
        
        return formularios;
    }
    
    @Override
    public List<FormularioResposta> buscarPorStatus(String status) {
        List<FormularioResposta> resultado = new ArrayList<>();
        StatusFormulario statusEnum = StatusFormulario.fromString(status);
        
        File baseFolder = new File(baseDir);
        if (!baseFolder.exists()) {
            return resultado;
        }
        
        for (File produtorDir : baseFolder.listFiles(File::isDirectory)) {
            try {
                Integer produtorId = Integer.parseInt(produtorDir.getName());
                List<FormularioResposta> formulariosProdutor = buscarPorProdutor(produtorId);
                
                for (FormularioResposta formulario : formulariosProdutor) {
                    if (formulario.getStatus() == statusEnum) {
                        resultado.add(formulario);
                    }
                }
            } catch (NumberFormatException e) {
                // Ignorar diretórios que não são IDs de produtor
            }
        }
        
        return resultado;
    }
    
    @Override
    public FormularioResposta atualizar(FormularioResposta formulario) {
        return salvar(formulario);
    }
    
    @Override
    public boolean excluir(String formularioId) {
        Optional<FormularioResposta> formularioOpt = buscarPorId(formularioId);
        if (formularioOpt.isEmpty()) {
            return false;
        }
        
        FormularioResposta formulario = formularioOpt.get();
        
        try {
            // Remover arquivo
            File arquivo = new File(obterCaminhoArquivo(formulario.getProdutorId(), formularioId));
            boolean removido = arquivo.delete();
            
            if (removido) {
                // Atualizar índice
                List<String> indice = lerIndice(formulario.getProdutorId());
                indice.remove(formularioId);
                salvarIndice(formulario.getProdutorId(), indice);
            }
            
            return removido;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean existe(String formularioId) {
        return buscarPorId(formularioId).isPresent();
    }
    
    @Override
    public String gerarNovoId(Integer produtorId) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
        String baseId = "form_" + produtorId + "_" + now.format(formatter);
        
        // Verificar se ID já existe e adicionar sufixo se necessário
        String novoId = baseId;
        int contador = 1;
        
        while (existe(novoId)) {
            novoId = baseId + "_" + contador;
            contador++;
        }
        
        return novoId;
    }
}