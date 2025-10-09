package com.avicheckpoint.controller;

import com.avicheckpoint.dto.FormularioRequestDTO;
import com.avicheckpoint.dto.FormularioResponseDTO;
import com.avicheckpoint.service.FormularioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para operações de formulários.
 * Mapeado para funcionar com o frontend JavaScript.
 */
@RestController
@RequestMapping("/api/formularios")
@CrossOrigin(origins = "*")
public class FormularioController {
    
    @Autowired
    private FormularioService formularioService;
    
    /**
     * Salva um novo formulário (rascunho ou completo).
     * POST /api/formularios
     */
    @PostMapping
    public ResponseEntity<FormularioResponseDTO> salvarFormulario(
            @RequestBody FormularioRequestDTO dto) {
        try {
            FormularioResponseDTO resposta = formularioService.salvarFormulario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Atualiza um formulário existente.
     * PUT /api/formularios/{formularioId}
     */
    @PutMapping("/{formularioId}")
    public ResponseEntity<FormularioResponseDTO> atualizarFormulario(
            @PathVariable String formularioId,
            @RequestBody FormularioRequestDTO dto) {
        try {
            FormularioResponseDTO resposta = formularioService.atualizarFormulario(
                formularioId, dto);
            return ResponseEntity.ok(resposta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Busca um formulário específico.
     * GET /api/formularios/{formularioId}
     */
    @GetMapping("/{formularioId}")
    public ResponseEntity<FormularioResponseDTO> buscarFormulario(
            @PathVariable String formularioId) {
        Optional<FormularioResponseDTO> formulario = 
            formularioService.buscarFormularioPorId(formularioId);
        
        return formulario
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Lista todos os formulários de um produtor.
     * GET /api/formularios/produtor/{produtorId}
     */
    @GetMapping("/produtor/{produtorId}")
    public ResponseEntity<List<FormularioResponseDTO>> buscarFormulariosPorProdutor(
            @PathVariable Integer produtorId) {
        List<FormularioResponseDTO> formularios = 
            formularioService.buscarFormulariosPorProdutor(produtorId);
        return ResponseEntity.ok(formularios);
    }
    
    /**
     * Lista formulários por status.
     * GET /api/formularios/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<FormularioResponseDTO>> buscarFormulariosPorStatus(
            @PathVariable String status) {
        try {
            List<FormularioResponseDTO> formularios = 
                formularioService.buscarFormulariosPorStatus(status.toUpperCase());
            return ResponseEntity.ok(formularios);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Submete um formulário para análise.
     * POST /api/formularios/{formularioId}/submeter
     */
    @PostMapping("/{formularioId}/submeter")
    public ResponseEntity<FormularioResponseDTO> submeterFormulario(
            @PathVariable String formularioId) {
        try {
            FormularioResponseDTO resposta = 
                formularioService.submeterFormulario(formularioId);
            return ResponseEntity.ok(resposta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Exclui um formulário.
     * DELETE /api/formularios/{formularioId}
     */
    @DeleteMapping("/{formularioId}")
    public ResponseEntity<Void> excluirFormulario(@PathVariable String formularioId) {
        boolean excluido = formularioService.excluirFormulario(formularioId);
        
        if (excluido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Endpoint para teste de conectividade.
     * GET /api/formularios/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Formulários API funcionando!");
    }
}