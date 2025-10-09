package com.avicheckpoint.controller;

import com.avicheckpoint.dto.ProdutorRequestDTO;
import com.avicheckpoint.dto.UsuarioResponseDTO;
import com.avicheckpoint.dto.VeterinarioRequestDTO;
import com.avicheckpoint.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * Controller REST para gerenciar usuários do sistema.
 * Mapeia exatamente aos endpoints esperados pelo frontend.
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // Para desenvolvimento
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    /**
     * Cadastra um novo produtor.
     * POST /api/usuarios/produtor
     * 
     * Mapeia à função saveProducer() do frontend.
     */
    @PostMapping("/produtor")
    public ResponseEntity<?> cadastrarProdutor(@Valid @RequestBody ProdutorRequestDTO dto) {
        try {
            UsuarioResponseDTO response = usuarioService.cadastrarProdutor(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                        "success", true,
                        "message", "Produtor cadastrado com sucesso",
                        "data", response
                    ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                        "success", false,
                        "message", e.getMessage()
                    ));
        }
    }
    
    /**
     * Cadastra um novo veterinário.
     * POST /api/usuarios/veterinario
     * 
     * Mapeia à função saveProfessional() do frontend.
     */
    @PostMapping("/veterinario")
    public ResponseEntity<?> cadastrarVeterinario(@Valid @RequestBody VeterinarioRequestDTO dto) {
        try {
            UsuarioResponseDTO response = usuarioService.cadastrarVeterinario(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                        "success", true,
                        "message", "Veterinário cadastrado com sucesso",
                        "data", response
                    ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                        "success", false,
                        "message", e.getMessage()
                    ));
        }
    }
    
    /**
     * Busca produtor por ID.
     * GET /api/usuarios/produtor/{id}
     */
    @GetMapping("/produtor/{id}")
    public ResponseEntity<?> buscarProdutor(@PathVariable Integer id) {
        Optional<UsuarioResponseDTO> produtor = usuarioService.buscarProdutorPorId(id);
        
        if (produtor.isPresent()) {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", produtor.get()
            ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Busca veterinário por ID.
     * GET /api/usuarios/veterinario/{id}
     */
    @GetMapping("/veterinario/{id}")
    public ResponseEntity<?> buscarVeterinario(@PathVariable Integer id) {
        Optional<UsuarioResponseDTO> veterinario = usuarioService.buscarVeterinarioPorId(id);
        
        if (veterinario.isPresent()) {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", veterinario.get()
            ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Atualiza dados do produtor.
     * PUT /api/usuarios/produtor/{id}
     */
    @PutMapping("/produtor/{id}")
    public ResponseEntity<?> atualizarProdutor(@PathVariable Integer id, 
                                              @Valid @RequestBody ProdutorRequestDTO dto) {
        try {
            UsuarioResponseDTO response = usuarioService.atualizarProdutor(id, dto);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Produtor atualizado com sucesso",
                "data", response
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                        "success", false,
                        "message", e.getMessage()
                    ));
        }
    }
    
    /**
     * Exclui produtor.
     * DELETE /api/usuarios/produtor/{id}
     */
    @DeleteMapping("/produtor/{id}")
    public ResponseEntity<?> excluirProdutor(@PathVariable Integer id) {
        boolean excluido = usuarioService.excluirProdutor(id);
        
        if (excluido) {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Produtor excluído com sucesso"
            ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Exclui veterinário.
     * DELETE /api/usuarios/veterinario/{id}
     */
    @DeleteMapping("/veterinario/{id}")
    public ResponseEntity<?> excluirVeterinario(@PathVariable Integer id) {
        boolean excluido = usuarioService.excluirVeterinario(id);
        
        if (excluido) {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Veterinário excluído com sucesso"
            ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}