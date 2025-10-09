package com.avicheckpoint.controller;

import com.avicheckpoint.dto.UsuarioResponseDTO;
import com.avicheckpoint.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller específico para funcionalidades de veterinários.
 * Inclui endpoint de recomendação baseado em localização e especialidade.
 */
@RestController
@RequestMapping("/api/veterinarios")
@CrossOrigin(origins = "*") // Para desenvolvimento
public class VeterinarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    /**
     * Busca veterinários por região e especialidade para recomendação.
     * GET /api/veterinarios/por-regiao?uf={uf}&especialidade={especialidade}
     * 
     * Mapeia à função filterProfessionalsSuggestion() do frontend.
     */
    @GetMapping("/por-regiao")
    public ResponseEntity<?> buscarPorRegiao(
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) String especialidade) {
        
        try {
            List<UsuarioResponseDTO> veterinarios = usuarioService
                    .buscarVeterinariosParaRecomendacao(uf, especialidade);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", veterinarios,
                "total", veterinarios.size()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                        "success", false,
                        "message", "Erro ao buscar veterinários: " + e.getMessage()
                    ));
        }
    }
    
    /**
     * Lista todos os veterinários cadastrados.
     * GET /api/veterinarios
     */
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            // Buscar todos os veterinários (sem filtro de região)
            List<UsuarioResponseDTO> veterinarios = usuarioService
                    .buscarVeterinariosParaRecomendacao(null, null);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", veterinarios,
                "total", veterinarios.size()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                        "success", false,
                        "message", "Erro ao listar veterinários: " + e.getMessage()
                    ));
        }
    }
}