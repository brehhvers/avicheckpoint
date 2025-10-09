package com.avicheckpoint.controller;

import com.avicheckpoint.dto.VeterinarioRecomendadoDTO;
import com.avicheckpoint.service.RecomendacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller REST para recomendação de veterinários.
 * Fornece endpoints para buscar profissionais baseados em localização e necessidades.
 */
@RestController
@RequestMapping("/api/recomendacoes")
@CrossOrigin(origins = "*")
public class RecomendacaoController {
    
    @Autowired
    private RecomendacaoService recomendacaoService;
    
    /**
     * Recomenda veterinários para um produtor baseado na última análise.
     * GET /api/recomendacoes/produtor/{produtorId}
     */
    @GetMapping("/produtor/{produtorId}")
    public ResponseEntity<List<VeterinarioRecomendadoDTO>> recomendarVeterinarios(
            @PathVariable Integer produtorId) {
        try {
            List<VeterinarioRecomendadoDTO> recomendacoes = 
                recomendacaoService.recomendarVeterinarios(produtorId);
            return ResponseEntity.ok(recomendacoes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Recomenda veterinários com parâmetros customizados.
     * GET /api/recomendacoes/produtor/{produtorId}/customizado
     * 
     * Parâmetros:
     * - raioKm: raio de busca em quilômetros (padrão: 100)
     * - limite: número máximo de recomendações (padrão: 5)
     */
    @GetMapping("/produtor/{produtorId}/customizado")
    public ResponseEntity<List<VeterinarioRecomendadoDTO>> recomendarVeterinariosCustomizado(
            @PathVariable Integer produtorId,
            @RequestParam(defaultValue = "100.0") Double raioKm,
            @RequestParam(defaultValue = "5") Integer limite) {
        try {
            List<VeterinarioRecomendadoDTO> recomendacoes = 
                recomendacaoService.recomendarVeterinarios(produtorId, raioKm, limite);
            return ResponseEntity.ok(recomendacoes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Recomenda veterinários baseado em problemas específicos.
     * POST /api/recomendacoes/produtor/{produtorId}/problemas
     * 
     * Body: Lista de problemas detectados
     * Exemplo: ["respiratorio", "nutricao", "mortalidade"]
     */
    @PostMapping("/produtor/{produtorId}/problemas")
    public ResponseEntity<List<VeterinarioRecomendadoDTO>> recomendarPorProblemas(
            @PathVariable Integer produtorId,
            @RequestBody List<String> problemasDetectados) {
        try {
            List<VeterinarioRecomendadoDTO> recomendacoes = 
                recomendacaoService.recomendarPorProblemas(produtorId, problemasDetectados);
            return ResponseEntity.ok(recomendacoes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Busca veterinários por especialidade em uma região.
     * GET /api/recomendacoes/especialidade/{especialidade}
     * 
     * Parâmetros:
     * - cidade: cidade para busca
     * - uf: estado para busca
     */
    @GetMapping("/especialidade/{especialidade}")
    public ResponseEntity<List<VeterinarioRecomendadoDTO>> buscarPorEspecialidade(
            @PathVariable String especialidade,
            @RequestParam String cidade,
            @RequestParam String uf) {
        try {
            List<VeterinarioRecomendadoDTO> veterinarios = 
                recomendacaoService.buscarPorEspecialidade(especialidade, cidade, uf);
            return ResponseEntity.ok(veterinarios);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Busca veterinários emergenciais (próximos e disponíveis).
     * GET /api/recomendacoes/emergencia/{produtorId}
     */
    @GetMapping("/emergencia/{produtorId}")
    public ResponseEntity<List<VeterinarioRecomendadoDTO>> buscarEmergencia(
            @PathVariable Integer produtorId) {
        try {
            // Busca veterinários num raio menor para emergência
            List<VeterinarioRecomendadoDTO> emergencia = 
                recomendacaoService.recomendarVeterinarios(produtorId, 50.0, 3);
            
            // Filtrar apenas disponíveis
            List<VeterinarioRecomendadoDTO> disponiveis = emergencia.stream()
                    .filter(vet -> vet.getDisponivel())
                    .toList();
                    
            return ResponseEntity.ok(disponiveis);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Estatísticas de veterinários por região.
     * GET /api/recomendacoes/estatisticas/{uf}
     */
    @GetMapping("/estatisticas/{uf}")
    public ResponseEntity<Map<String, Object>> obterEstatisticas(@PathVariable String uf) {
        // Para implementação futura - estatísticas regionais
        Map<String, Object> estatisticas = Map.of(
            "uf", uf,
            "totalVeterinarios", 0,
            "especializadosAvicultura", 0,
            "avaliacaoMediaRegiao", 0.0,
            "message", "Funcionalidade em desenvolvimento"
        );
        return ResponseEntity.ok(estatisticas);
    }
    
    /**
     * Avalia um veterinário (futuro sistema de feedback).
     * POST /api/recomendacoes/avaliar/{veterinarioId}
     */
    @PostMapping("/avaliar/{veterinarioId}")
    public ResponseEntity<Map<String, Object>> avaliarVeterinario(
            @PathVariable Integer veterinarioId,
            @RequestBody Map<String, Object> avaliacao) {
        
        // Para implementação futura - sistema de avaliações
        Map<String, Object> resposta = Map.of(
            "veterinarioId", veterinarioId,
            "message", "Sistema de avaliação em desenvolvimento",
            "avaliacaoRecebida", avaliacao
        );
        return ResponseEntity.ok(resposta);
    }
    
    /**
     * Endpoint para teste de conectividade do sistema de recomendações.
     * GET /api/recomendacoes/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> status = Map.of(
            "status", "OK",
            "service", "RecomendacaoService",
            "message", "Sistema de recomendações funcionando!",
            "funcionalidades", List.of(
                "Recomendação por proximidade",
                "Recomendação por especialidade", 
                "Recomendação por problemas específicos",
                "Busca emergencial",
                "Cálculo de compatibilidade"
            )
        );
        return ResponseEntity.ok(status);
    }
}