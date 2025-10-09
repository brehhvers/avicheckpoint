package com.avicheckpoint.controller;

import com.avicheckpoint.dto.DashboardProdutorDTO;
import com.avicheckpoint.dto.HistoricoFormularioDTO;
import com.avicheckpoint.service.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Controller REST para operações de histórico e dashboard.
 * Fornece endpoints para análise evolutiva das propriedades.
 */
@RestController
@RequestMapping("/api/historico")
@CrossOrigin(origins = "*")
public class HistoricoController {
    
    @Autowired
    private HistoricoService historicoService;
    
    /**
     * Gera dashboard completo para um produtor.
     * GET /api/historico/dashboard/{produtorId}
     */
    @GetMapping("/dashboard/{produtorId}")
    public ResponseEntity<DashboardProdutorDTO> gerarDashboard(
            @PathVariable Integer produtorId) {
        try {
            DashboardProdutorDTO dashboard = historicoService.gerarDashboard(produtorId);
            return ResponseEntity.ok(dashboard);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Retorna histórico completo de formulários de um produtor.
     * GET /api/historico/produtor/{produtorId}
     */
    @GetMapping("/produtor/{produtorId}")
    public ResponseEntity<List<HistoricoFormularioDTO>> buscarHistoricoCompleto(
            @PathVariable Integer produtorId) {
        try {
            List<HistoricoFormularioDTO> historico = 
                historicoService.buscarHistoricoCompleto(produtorId);
            return ResponseEntity.ok(historico);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Compara dois formulários para análise evolutiva.
     * GET /api/historico/comparar/{formularioId1}/{formularioId2}
     */
    @GetMapping("/comparar/{formularioId1}/{formularioId2}")
    public ResponseEntity<Map<String, Object>> compararFormularios(
            @PathVariable String formularioId1,
            @PathVariable String formularioId2) {
        try {
            Map<String, Object> comparacao = 
                historicoService.compararFormularios(formularioId1, formularioId2);
            return ResponseEntity.ok(comparacao);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Gera relatório de tendências por período.
     * GET /api/historico/tendencias/{produtorId}
     * 
     * Parâmetros opcionais:
     * - dataInicio: início do período (formato: yyyy-MM-dd'T'HH:mm:ss)
     * - dataFim: fim do período (formato: yyyy-MM-dd'T'HH:mm:ss)
     */
    @GetMapping("/tendencias/{produtorId}")
    public ResponseEntity<Map<String, Object>> gerarRelatorioTendencias(
            @PathVariable Integer produtorId,
            @RequestParam(required = false) 
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dataInicio,
            @RequestParam(required = false) 
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dataFim) {
        
        try {
            // Se não informadas, usar período dos últimos 6 meses
            if (dataInicio == null) {
                dataInicio = LocalDateTime.now().minusMonths(6);
            }
            if (dataFim == null) {
                dataFim = LocalDateTime.now();
            }
            
            Map<String, Object> relatorio = 
                historicoService.gerarRelatorioTendencias(produtorId, dataInicio, dataFim);
            return ResponseEntity.ok(relatorio);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Retorna estatísticas resumidas de todos os produtores.
     * GET /api/historico/estatisticas-gerais
     */
    @GetMapping("/estatisticas-gerais")
    public ResponseEntity<Map<String, Object>> obterEstatisticasGerais() {
        // Para implementação futura - estatísticas do sistema
        Map<String, Object> estatisticas = Map.of(
            "totalProdutores", 0,
            "formulariosProcessados", 0,
            "pontuacaoMediaSistema", 0.0,
            "message", "Funcionalidade em desenvolvimento"
        );
        return ResponseEntity.ok(estatisticas);
    }
    
    /**
     * Endpoint para exportar dados históricos (futuro).
     * GET /api/historico/exportar/{produtorId}
     */
    @GetMapping("/exportar/{produtorId}")
    public ResponseEntity<Map<String, Object>> exportarDados(
            @PathVariable Integer produtorId,
            @RequestParam(defaultValue = "json") String formato) {
        
        // Para implementação futura - exportação em PDF/Excel
        Map<String, Object> resposta = Map.of(
            "message", "Funcionalidade de exportação em desenvolvimento",
            "formatosSuportados", List.of("json", "pdf", "excel"),
            "formatoSolicitado", formato
        );
        return ResponseEntity.ok(resposta);
    }
    
    /**
     * Endpoint para teste de conectividade do histórico.
     * GET /api/historico/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> status = Map.of(
            "status", "OK",
            "service", "HistoricoService",
            "message", "Serviço de histórico funcionando!",
            "timestamp", LocalDateTime.now()
        );
        return ResponseEntity.ok(status);
    }
}