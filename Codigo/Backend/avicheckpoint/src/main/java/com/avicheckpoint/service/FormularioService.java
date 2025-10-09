package com.avicheckpoint.service;

import com.avicheckpoint.dto.FormularioRequestDTO;
import com.avicheckpoint.dto.FormularioResponseDTO;
import com.avicheckpoint.model.FormularioResposta;
import com.avicheckpoint.model.StatusFormulario;
import com.avicheckpoint.repository.FormularioRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciar operações de formulários.
 * Centraliza lógica de negócio para formulários e suas análises.
 */
@Service
public class FormularioService {
    
    @Autowired
    private FormularioRepositoryImpl formularioRepository;
    
    /**
     * Salva um formulário (rascunho ou completo).
     */
    public FormularioResponseDTO salvarFormulario(FormularioRequestDTO dto) {
        // Criar ou buscar formulário existente
        FormularioResposta formulario = new FormularioResposta();
        formulario.setProdutorId(dto.getProdutorId());
        formulario.setRespostas(dto.getRespostas());
        
        // Definir status baseado na flag submeter
        if (dto.isSubmeter() && isFormularioCompleto(dto.getRespostas())) {
            formulario.setStatus(StatusFormulario.SUBMETIDO);
        } else {
            formulario.setStatus(StatusFormulario.RASCUNHO);
        }
        
        // Salvar
        FormularioResposta salvo = formularioRepository.salvar(formulario);
        
        return mapearParaResponse(salvo);
    }
    
    /**
     * Atualiza um formulário existente.
     */
    public FormularioResponseDTO atualizarFormulario(String formularioId, 
                                                   FormularioRequestDTO dto) {
        FormularioResposta formulario = formularioRepository.buscarPorId(formularioId)
                .orElseThrow(() -> new RuntimeException("Formulário não encontrado: " + formularioId));
        
        // Atualizar dados
        formulario.setRespostas(dto.getRespostas());
        
        // Atualizar status se necessário
        if (dto.isSubmeter() && isFormularioCompleto(dto.getRespostas())) {
            formulario.setStatus(StatusFormulario.SUBMETIDO);
        }
        
        FormularioResposta atualizado = formularioRepository.atualizar(formulario);
        
        return mapearParaResponse(atualizado);
    }
    
    /**
     * Busca um formulário por ID.
     */
    public Optional<FormularioResponseDTO> buscarFormularioPorId(String formularioId) {
        return formularioRepository.buscarPorId(formularioId)
                .map(this::mapearParaResponse);
    }
    
    /**
     * Lista todos os formulários de um produtor.
     */
    public List<FormularioResponseDTO> buscarFormulariosPorProdutor(Integer produtorId) {
        List<FormularioResposta> formularios = formularioRepository.buscarPorProdutor(produtorId);
        return formularios.stream()
                .map(this::mapearParaResponse)
                .toList();
    }
    
    /**
     * Busca formulários por status.
     */
    public List<FormularioResponseDTO> buscarFormulariosPorStatus(String status) {
        List<FormularioResposta> formularios = formularioRepository.buscarPorStatus(status);
        return formularios.stream()
                .map(this::mapearParaResponse)
                .toList();
    }
    
    /**
     * Submete um formulário para análise.
     */
    public FormularioResponseDTO submeterFormulario(String formularioId) {
        FormularioResposta formulario = formularioRepository.buscarPorId(formularioId)
                .orElseThrow(() -> new RuntimeException("Formulário não encontrado: " + formularioId));
        
        if (!isFormularioCompleto(formulario.getRespostas())) {
            throw new RuntimeException("Formulário incompleto. Não é possível submeter.");
        }
        
        formulario.setStatus(StatusFormulario.SUBMETIDO);
        FormularioResposta atualizado = formularioRepository.atualizar(formulario);
        
        return mapearParaResponse(atualizado);
    }
    
    /**
     * Exclui um formulário.
     */
    public boolean excluirFormulario(String formularioId) {
        return formularioRepository.excluir(formularioId);
    }
    
    /**
     * Verifica se um formulário está completo.
     * Um formulário é considerado completo quando tem todas as seções obrigatórias:
     * saude, nutricao, avaliacao, doencas
     */
    private boolean isFormularioCompleto(java.util.Map<String, Object> respostas) {
        if (respostas == null) {
            return false;
        }
        
        // Verificar se todas as seções obrigatórias estão presentes
        return respostas.containsKey("saude") && 
               respostas.containsKey("nutricao") && 
               respostas.containsKey("avaliacao") && 
               respostas.containsKey("doencas");
    }
    
    /**
     * Verifica se uma seção tem respostas válidas.
     */
    @SuppressWarnings("unchecked")
    private boolean isSecaoValida(Object secaoObj) {
        if (!(secaoObj instanceof java.util.Map)) {
            return false;
        }
        
        java.util.Map<String, Object> secao = (java.util.Map<String, Object>) secaoObj;
        
        // Verificar se a seção tem pelo menos uma resposta não vazia
        return secao.values().stream()
                .anyMatch(valor -> valor != null && !valor.toString().trim().isEmpty());
    }
    
    /**
     * Mapeia FormularioResposta para FormularioResponseDTO.
     */
    private FormularioResponseDTO mapearParaResponse(FormularioResposta formulario) {
        FormularioResponseDTO dto = new FormularioResponseDTO();
        dto.setFormularioId(formulario.getFormularioId());
        dto.setProdutorId(formulario.getProdutorId());
        dto.setDataPreenchimento(formulario.getDataPreenchimento());
        dto.setDataAtualizacao(formulario.getDataAtualizacao());
        dto.setStatus(formulario.getStatus());
        dto.setRespostas(formulario.getRespostas());
        dto.setResultado(formulario.getResultado());
        dto.setCompleto(isFormularioCompleto(formulario.getRespostas()));
        
        return dto;
    }
}