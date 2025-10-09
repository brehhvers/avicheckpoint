package com.avicheckpoint.service;

import com.avicheckpoint.dto.DashboardProdutorDTO;
import com.avicheckpoint.dto.HistoricoFormularioDTO;
import com.avicheckpoint.model.FormularioResposta;
import com.avicheckpoint.model.Produtor;
import com.avicheckpoint.model.ResultadoAnalise;
import com.avicheckpoint.model.StatusFormulario;
import com.avicheckpoint.repository.FormularioRepositoryImpl;
import com.avicheckpoint.repository.ProdutorRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar histórico e dashboards de produtores.
 */
@Service
public class HistoricoService {
    
    @Autowired
    private FormularioRepositoryImpl formularioRepository;
    
    @Autowired
    private ProdutorRepositoryImpl produtorRepository;
    
    /**
     * Gera dashboard completo para um produtor.
     */
    public DashboardProdutorDTO gerarDashboard(Integer produtorId) {
        // Buscar dados do produtor
        Optional<Produtor> produtorOpt = produtorRepository.buscarPorId(produtorId);
        if (produtorOpt.isEmpty()) {
            throw new RuntimeException("Produtor não encontrado: " + produtorId);
        }
        Produtor produtor = produtorOpt.get();
        
        // Buscar todos os formulários do produtor
        List<FormularioResposta> formularios = formularioRepository.buscarPorProdutor(produtorId);
        
        // Gerar dashboard
        DashboardProdutorDTO dashboard = new DashboardProdutorDTO();
        dashboard.setProdutorId(produtorId);
        dashboard.setNomeProdutor(produtor.getUsuarioNomeCompleto());
        
        // Estatísticas básicas
        dashboard.setTotalFormularios(formularios.size());
        dashboard.setFormulariosAnalisados((int) formularios.stream()
                .filter(f -> f.getStatus() == StatusFormulario.ANALISADO)
                .count());
        dashboard.setRascunhos((int) formularios.stream()
                .filter(f -> f.getStatus() == StatusFormulario.RASCUNHO)
                .count());
        
        // Formulários analisados (com resultado)
        List<FormularioResposta> formulariosAnalisados = formularios.stream()
                .filter(f -> f.getStatus() == StatusFormulario.ANALISADO && f.getResultado() != null)
                .sorted((a, b) -> b.getDataPreenchimento().compareTo(a.getDataPreenchimento()))
                .collect(Collectors.toList());
        
        if (!formulariosAnalisados.isEmpty()) {
            // Métricas evolutivas
            calcularMetricasEvolutivas(dashboard, formulariosAnalisados);
            
            // Estatísticas gerais
            calcularEstatisticasGerais(dashboard, formulariosAnalisados);
            
            // Histórico recente (últimos 5)
            List<HistoricoFormularioDTO> historicoRecente = formulariosAnalisados.stream()
                    .limit(5)
                    .map(this::mapearParaHistorico)
                    .collect(Collectors.toList());
            dashboard.setHistoricoRecente(historicoRecente);
            
            // Alertas ativos (do último formulário)
            extrairAlertasAtivos(dashboard, formulariosAnalisados.get(0));
        }
        
        return dashboard;
    }
    
    /**
     * Retorna histórico completo de um produtor.
     */
    public List<HistoricoFormularioDTO> buscarHistoricoCompleto(Integer produtorId) {
        List<FormularioResposta> formularios = formularioRepository.buscarPorProdutor(produtorId);
        
        return formularios.stream()
                .filter(f -> f.getStatus() == StatusFormulario.ANALISADO)
                .sorted((a, b) -> b.getDataPreenchimento().compareTo(a.getDataPreenchimento()))
                .map(this::mapearParaHistorico)
                .collect(Collectors.toList());
    }
    
    /**
     * Compara dois formulários para análise evolutiva.
     */
    public Map<String, Object> compararFormularios(String formularioId1, String formularioId2) {
        FormularioResposta form1 = formularioRepository.buscarPorId(formularioId1)
                .orElseThrow(() -> new RuntimeException("Formulário 1 não encontrado"));
        FormularioResposta form2 = formularioRepository.buscarPorId(formularioId2)
                .orElseThrow(() -> new RuntimeException("Formulário 2 não encontrado"));
        
        Map<String, Object> comparacao = new HashMap<>();
        
        // Dados básicos
        comparacao.put("formulario1", mapearParaHistorico(form1));
        comparacao.put("formulario2", mapearParaHistorico(form2));
        
        // Comparação de pontuação
        if (form1.getResultado() != null && form2.getResultado() != null) {
            Integer pont1 = form1.getResultado().getPontuacaoGeral();
            Integer pont2 = form2.getResultado().getPontuacaoGeral();
            
            if (pont1 != null && pont2 != null) {
                comparacao.put("diferencaPontuacao", pont2 - pont1);
                comparacao.put("percentualMelhoria", calcularPercentualMelhoria(pont1, pont2));
                comparacao.put("melhorou", pont2 > pont1);
            }
        }
        
        return comparacao;
    }
    
    /**
     * Gera relatório de tendências por período.
     */
    public Map<String, Object> gerarRelatorioTendencias(Integer produtorId, 
                                                       LocalDateTime dataInicio, 
                                                       LocalDateTime dataFim) {
        List<FormularioResposta> formularios = formularioRepository.buscarPorProdutor(produtorId)
                .stream()
                .filter(f -> f.getStatus() == StatusFormulario.ANALISADO)
                .filter(f -> f.getDataPreenchimento().isAfter(dataInicio) || 
                            f.getDataPreenchimento().isEqual(dataInicio))
                .filter(f -> f.getDataPreenchimento().isBefore(dataFim) || 
                            f.getDataPreenchimento().isEqual(dataFim))
                .sorted((a, b) -> a.getDataPreenchimento().compareTo(b.getDataPreenchimento()))
                .collect(Collectors.toList());
        
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("periodo", Map.of(
            "inicio", dataInicio.format(DateTimeFormatter.ISO_LOCAL_DATE),
            "fim", dataFim.format(DateTimeFormatter.ISO_LOCAL_DATE)
        ));
        relatorio.put("totalFormularios", formularios.size());
        
        if (!formularios.isEmpty()) {
            // Evolução da pontuação
            List<Map<String, Object>> evolucaoPontuacao = formularios.stream()
                    .filter(f -> f.getResultado() != null && f.getResultado().getPontuacaoGeral() != null)
                    .map(f -> {
                        Map<String, Object> ponto = new HashMap<>();
                        ponto.put("data", f.getDataPreenchimento().format(DateTimeFormatter.ISO_LOCAL_DATE));
                        ponto.put("pontuacao", f.getResultado().getPontuacaoGeral());
                        return ponto;
                    })
                    .collect(Collectors.toList());
            relatorio.put("evolucaoPontuacao", evolucaoPontuacao);
            
            // Estatísticas do período
            OptionalDouble pontuacaoMedia = formularios.stream()
                    .filter(f -> f.getResultado() != null && f.getResultado().getPontuacaoGeral() != null)
                    .mapToInt(f -> f.getResultado().getPontuacaoGeral())
                    .average();
            
            relatorio.put("pontuacaoMedia", pontuacaoMedia.orElse(0.0));
            
            // Primeira e última pontuação para calcular tendência
            if (evolucaoPontuacao.size() >= 2) {
                Integer primeira = (Integer) evolucaoPontuacao.get(0).get("pontuacao");
                Integer ultima = (Integer) evolucaoPontuacao.get(evolucaoPontuacao.size() - 1).get("pontuacao");
                relatorio.put("tendencia", calcularTendencia(primeira, ultima));
            }
        }
        
        return relatorio;
    }
    
    /**
     * Calcula métricas evolutivas para o dashboard.
     */
    private void calcularMetricasEvolutivas(DashboardProdutorDTO dashboard, 
                                          List<FormularioResposta> formulariosAnalisados) {
        // Pontuação média
        OptionalDouble pontuacaoMedia = formulariosAnalisados.stream()
                .filter(f -> f.getResultado() != null && f.getResultado().getPontuacaoGeral() != null)
                .mapToInt(f -> f.getResultado().getPontuacaoGeral())
                .average();
        dashboard.setPontuacaoMedia(pontuacaoMedia.orElse(0.0));
        
        // Últimas pontuações para tendência
        List<Integer> ultimasPontuacoes = formulariosAnalisados.stream()
                .filter(f -> f.getResultado() != null && f.getResultado().getPontuacaoGeral() != null)
                .map(f -> f.getResultado().getPontuacaoGeral())
                .limit(2)
                .collect(Collectors.toList());
        
        if (!ultimasPontuacoes.isEmpty()) {
            dashboard.setUltimaPontuacao(ultimasPontuacoes.get(0));
            
            if (ultimasPontuacoes.size() >= 2) {
                dashboard.setPenultimaPontuacao(ultimasPontuacoes.get(1));
                dashboard.setTendenciaPontuacao(
                    calcularTendencia(ultimasPontuacoes.get(1), ultimasPontuacoes.get(0))
                );
            } else {
                dashboard.setTendenciaPontuacao("primeiro");
            }
        }
    }
    
    /**
     * Calcula estatísticas gerais do dashboard.
     */
    private void calcularEstatisticasGerais(DashboardProdutorDTO dashboard, 
                                          List<FormularioResposta> formulariosAnalisados) {
        // Última análise para determinar categoria atual
        FormularioResposta ultimoFormulario = formulariosAnalisados.get(0);
        if (ultimoFormulario.getResultado() != null) {
            Integer pontuacao = ultimoFormulario.getResultado().getPontuacaoGeral();
            dashboard.setCategoriaAtual(determinarCategoria(pontuacao));
            
            // Contar totais da última análise
            ResultadoAnalise resultado = ultimoFormulario.getResultado();
            dashboard.setTotalPontosFortes(resultado.getPontosFortes() != null ? 
                    resultado.getPontosFortes().size() : 0);
            dashboard.setTotalPontosAMelhorar(resultado.getMelhorias() != null ? 
                    resultado.getMelhorias().size() : 0);
            dashboard.setTotalAlertas(resultado.getComentarios() != null ? 
                    resultado.getComentarios().size() : 0);
        }
    }
    
    /**
     * Extrai alertas ativos do último formulário.
     */
    private void extrairAlertasAtivos(DashboardProdutorDTO dashboard, FormularioResposta ultimoFormulario) {
        if (ultimoFormulario.getResultado() != null && 
            ultimoFormulario.getResultado().getComentarios() != null) {
            
            List<String> alertas = ultimoFormulario.getResultado().getComentarios();
            dashboard.setAlertasAtivos(alertas);
            
            // Verificar se tem alertas críticos
            boolean temCriticos = alertas.stream()
                    .anyMatch(alerta -> alerta.toLowerCase().contains("crítico") || 
                                      alerta.toLowerCase().contains("urgente") ||
                                      alerta.toLowerCase().contains("mortes"));
            dashboard.setTemAlertasCriticos(temCriticos);
        }
    }
    
    /**
     * Mapeia FormularioResposta para HistoricoFormularioDTO.
     */
    private HistoricoFormularioDTO mapearParaHistorico(FormularioResposta formulario) {
        HistoricoFormularioDTO dto = new HistoricoFormularioDTO();
        dto.setFormularioId(formulario.getFormularioId());
        dto.setDataPreenchimento(formulario.getDataPreenchimento());
        dto.setStatus(formulario.getStatus());
        dto.setCompleto(isFormularioCompleto(formulario.getRespostas()));
        
        if (formulario.getResultado() != null) {
            ResultadoAnalise resultado = formulario.getResultado();
            dto.setPontuacaoGeral(resultado.getPontuacaoGeral());
            dto.setPanoramaGeral(resultado.getPanorama() != null && !resultado.getPanorama().isEmpty() ? 
                    resultado.getPanorama().get(0) : "");
            dto.setTotalPontosFortes(resultado.getPontosFortes() != null ? 
                    resultado.getPontosFortes().size() : 0);
            dto.setTotalPontosAMelhorar(resultado.getMelhorias() != null ? 
                    resultado.getMelhorias().size() : 0);
            dto.setTotalAlertas(resultado.getComentarios() != null ? 
                    resultado.getComentarios().size() : 0);
        }
        
        return dto;
    }
    
    /**
     * Verifica se formulário está completo.
     */
    private boolean isFormularioCompleto(Map<String, Object> respostas) {
        if (respostas == null) return false;
        return respostas.containsKey("saude") && 
               respostas.containsKey("nutricao") && 
               respostas.containsKey("avaliacao") && 
               respostas.containsKey("doencas");
    }
    
    /**
     * Calcula tendência entre duas pontuações.
     */
    private String calcularTendencia(Integer pontuacao1, Integer pontuacao2) {
        if (pontuacao1 == null || pontuacao2 == null) return "desconhecida";
        
        int diferenca = pontuacao2 - pontuacao1;
        if (diferenca > 5) return "crescente";
        if (diferenca < -5) return "decrescente";
        return "estavel";
    }
    
    /**
     * Calcula percentual de melhoria.
     */
    private Double calcularPercentualMelhoria(Integer pontuacao1, Integer pontuacao2) {
        if (pontuacao1 == null || pontuacao2 == null || pontuacao1 == 0) return 0.0;
        return ((double) (pontuacao2 - pontuacao1) / pontuacao1) * 100;
    }
    
    /**
     * Determina categoria baseada na pontuação.
     */
    private String determinarCategoria(Integer pontuacao) {
        if (pontuacao == null) return "Não avaliado";
        if (pontuacao >= 80) return "Excelente";
        if (pontuacao >= 60) return "Bom";
        if (pontuacao >= 40) return "Atenção";
        return "Crítico";
    }
}