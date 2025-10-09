package com.avicheckpoint.service;

import com.avicheckpoint.model.FormularioResposta;
import com.avicheckpoint.model.ResultadoAnalise;
import com.avicheckpoint.model.StatusFormulario;
import com.avicheckpoint.repository.FormularioRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Engine de análise responsável por processar formulários submetidos
 * e gerar diagnósticos baseados nas regras do hackathon.
 */
@Service
public class AnaliseService {
    
    @Autowired
    private FormularioRepositoryImpl formularioRepository;
    
    /**
     * Processa um formulário submetido e gera análise completa.
     */
    public FormularioResposta analisarFormulario(String formularioId) {
        FormularioResposta formulario = formularioRepository.buscarPorId(formularioId)
                .orElseThrow(() -> new RuntimeException("Formulário não encontrado: " + formularioId));
        
        if (formulario.getStatus() != StatusFormulario.SUBMETIDO) {
            throw new RuntimeException("Apenas formulários submetidos podem ser analisados");
        }
        
        // Gerar análise
        ResultadoAnalise resultado = processarAnalise(formulario.getRespostas());
        formulario.setResultado(resultado);
        formulario.setStatus(StatusFormulario.ANALISADO);
        
        // Salvar formulário com resultado
        return formularioRepository.atualizar(formulario);
    }
    
    /**
     * Processa as respostas e gera o resultado da análise.
     */
    private ResultadoAnalise processarAnalise(Map<String, Object> respostas) {
        ResultadoAnalise resultado = new ResultadoAnalise();
        
        // Processar cada seção
        List<String> pontosFortes = new ArrayList<>();
        List<String> pontosAMelhorar = new ArrayList<>();
        List<String> alertas = new ArrayList<>();
        Set<String> profissionaisRecomendados = new HashSet<>();
        
        // Analisar seção Saúde
        analisarSaude(respostas, pontosFortes, pontosAMelhorar, alertas, profissionaisRecomendados);
        
        // Analisar seção Nutrição
        analisarNutricao(respostas, pontosFortes, pontosAMelhorar, alertas, profissionaisRecomendados);
        
        // Analisar seção Avaliação
        analisarAvaliacao(respostas, pontosFortes, pontosAMelhorar, alertas, profissionaisRecomendados);
        
        // Analisar seção Doenças
        analisarDoencas(respostas, pontosFortes, pontosAMelhorar, alertas, profissionaisRecomendados);
        
        // Calcular pontuação geral
        int pontuacaoGeral = calcularPontuacaoGeral(pontosFortes.size(), pontosAMelhorar.size(), alertas.size());
        
        // Montar resultado
        resultado.setPontuacaoGeral(pontuacaoGeral);
        resultado.setPontosFortes(pontosFortes);
        resultado.setPontosAMelhorar(pontosAMelhorar);
        resultado.setAlertas(alertas);
        resultado.setProfissionaisRecomendados(new ArrayList<>(profissionaisRecomendados));
        resultado.setPanoramaGeral(gerarPanoramaGeral(pontuacaoGeral));
        resultado.setComentarios(gerarComentarios(profissionaisRecomendados));
        
        return resultado;
    }
    
    /**
     * Análise da seção Saúde das Aves.
     */
    @SuppressWarnings("unchecked")
    private void analisarSaude(Map<String, Object> respostas, List<String> pontosFortes, 
                              List<String> pontosAMelhorar, List<String> alertas,
                              Set<String> profissionaisRecomendados) {
        
        Map<String, Object> saude = (Map<String, Object>) respostas.get("saude");
        if (saude == null) return;
        
        // 1. Sinais de doença
        String sinaisDoenca = (String) saude.get("sinaisDoenca");
        if ("nao".equals(sinaisDoenca)) {
            pontosFortes.add("Suas aves não apresentam sinais de doença, continue monitorando o plantel.");
        } else if ("sim".equals(sinaisDoenca)) {
            alertas.add("Suas aves estão apresentando sinais de doenças. Faça monitoramento constante, anotações diárias de produção e alterações de comportamento.");
            profissionaisRecomendados.add("Médico Veterinário");
        }
        
        // 2. Sinais clínicos
        List<String> sinaisClinicos = (List<String>) saude.get("sinaisClinicos");
        if (sinaisClinicos != null) {
            if (sinaisClinicos.contains("semAlteracoes")) {
                pontosFortes.add("Suas aves não apresentam sinais clínicos de doenças.");
            } else {
                if (sinaisClinicos.contains("espirros")) {
                    alertas.add("Cuidado! Suas aves apresentam espirros. Sinais respiratórios têm alta transmissibilidade e podem resultar em queda produtiva.");
                }
                if (sinaisClinicos.contains("diarreia")) {
                    alertas.add("Cuidado! Suas aves apresentam diarreia, sinal inespecífico que pode indicar Salmonella ou Coccidiose.");
                }
                if (sinaisClinicos.contains("apatia")) {
                    alertas.add("Cuidado! Suas aves apresentam apatia, resultando em queda do desempenho produtivo.");
                }
                if (sinaisClinicos.contains("quedaProducao")) {
                    alertas.add("Queda na produção detectada. Situação multifatorial que requer avaliação completa do plantel.");
                }
                if (sinaisClinicos.size() > 1 || !sinaisClinicos.contains("semAlteracoes")) {
                    profissionaisRecomendados.add("Médico Veterinário");
                }
            }
        }
        
        // 3. Vacinação
        String vacinacao = (String) saude.get("vacinacao");
        if ("sim".equals(vacinacao)) {
            pontosFortes.add("Parabéns! A vacinação das aves está em dia (Newcastle, Marek, Bronquite Infecciosa, Gumboro e Anticoccidianos).");
        } else if ("nao".equals(vacinacao)) {
            alertas.add("Cuidado! A vacinação não está em dia, comprometendo a saúde das aves e dos consumidores.");
            profissionaisRecomendados.add("Médico Veterinário");
        }
        
        // 4. Quarentena
        String quarentena = (String) saude.get("quarentena");
        if ("sim".equals(quarentena)) {
            pontosFortes.add("Excelente! A quarentena de novas aves é feita corretamente, prevenindo disseminação de doenças.");
        } else if ("nao".equals(quarentena)) {
            pontosAMelhorar.add("É fundamental realizar quarentena das novas aves antes de introduzi-las no plantel.");
        }
        
        // 5. Acesso de aves silvestres
        String avesSilvestres = (String) saude.get("avesSilvestres");
        if ("nao".equals(avesSilvestres)) {
            pontosFortes.add("Muito bem! Não há acesso de aves silvestres, prevenindo transmissão de gripe aviária.");
        } else if ("sim".equals(avesSilvestres)) {
            alertas.add("Cuidado! Aves silvestres podem transmitir gripe aviária. Remova árvores frutíferas próximas e tele o local.");
            profissionaisRecomendados.add("Médico Veterinário");
        }
    }
    
    /**
     * Análise da seção Nutrição.
     */
    @SuppressWarnings("unchecked")
    private void analisarNutricao(Map<String, Object> respostas, List<String> pontosFortes,
                                 List<String> pontosAMelhorar, List<String> alertas,
                                 Set<String> profissionaisRecomendados) {
        
        Map<String, Object> nutricao = (Map<String, Object>) respostas.get("nutricao");
        if (nutricao == null) return;
        
        // 1. Tipo de alimentação
        String tipoAlimentacao = (String) nutricao.get("tipoAlimentacao");
        if ("racaoIndustrial".equals(tipoAlimentacao)) {
            pontosFortes.add("Boa opção! A ração industrial fornece todos os componentes exigidos pelas aves.");
        } else if ("comidaCaseira".equals(tipoAlimentacao) || "sobrasAlimentos".equals(tipoAlimentacao)) {
            pontosAMelhorar.add("A alimentação caseira/sobras pode deixar nutrientes de lado, gerando prejuízos para as aves.");
            profissionaisRecomendados.add("Zootecnista");
            profissionaisRecomendados.add("Médico Veterinário");
        }
        
        // 2. Armazenamento
        String armazenamento = (String) nutricao.get("armazenamento");
        if ("sim".equals(armazenamento)) {
            pontosFortes.add("Muito bem! O alimento é armazenado corretamente em local fechado, seco e protegido.");
        } else if ("nao".equals(armazenamento)) {
            alertas.add("Atenção! Armazenamento inadequado compromete a qualidade do alimento e saúde das aves.");
        }
        
        // 3. Presença de fungos/bolor
        String fungosBolor = (String) nutricao.get("fungosBolor");
        if ("nao".equals(fungosBolor)) {
            pontosFortes.add("Muito bem! A qualidade da alimentação está preservada sem fungos, bolor ou insetos.");
        } else if ("sim".equals(fungosBolor)) {
            alertas.add("Cuidado! A qualidade da alimentação está comprometida com fungos, bolor ou insetos!");
        }
    }
    
    /**
     * Calcular pontuação geral baseada nos resultados.
     */
    private int calcularPontuacaoGeral(int pontosFortes, int pontosAMelhorar, int alertas) {
        int pontuacao = pontosFortes * 10; // Cada ponto forte vale 10
        pontuacao -= pontosAMelhorar * 3; // Cada ponto a melhorar desconta 3
        pontuacao -= alertas * 8; // Cada alerta desconta 8
        
        return Math.max(0, Math.min(100, pontuacao)); // Entre 0 e 100
    }
    
    /**
     * Gerar panorama geral baseado na pontuação.
     */
    private String gerarPanoramaGeral(int pontuacao) {
        if (pontuacao >= 80) {
            return "Excelente! Sua propriedade apresenta ótimas condições de manejo e sanidade.";
        } else if (pontuacao >= 60) {
            return "Bom! Sua propriedade está em condições adequadas, mas há pontos que podem ser melhorados.";
        } else if (pontuacao >= 40) {
            return "Atenção! Sua propriedade necessita de melhorias importantes para garantir a sanidade das aves.";
        } else {
            return "Crítico! Sua propriedade apresenta sérios problemas que precisam ser corrigidos urgentemente.";
        }
    }
    
    /**
     * Gerar comentários baseados nos profissionais recomendados.
     */
    private List<String> gerarComentarios(Set<String> profissionaisRecomendados) {
        List<String> comentarios = new ArrayList<>();
        
        if (profissionaisRecomendados.contains("Médico Veterinário")) {
            comentarios.add("Sugerimos passar por avaliação de um médico veterinário");
        }
        if (profissionaisRecomendados.contains("Zootecnista")) {
            comentarios.add("Sugerimos passar por avaliação de um zootecnista");
        }
        if (profissionaisRecomendados.contains("Médico")) {
            comentarios.add("Sugerimos passar por avaliação de um médico caso funcionários apresentem sinais clínicos");
        }
        
        return comentarios;
    }
    
    /**
     * Análise da seção Avaliação do Ovo.
     */
    @SuppressWarnings("unchecked")
    private void analisarAvaliacao(Map<String, Object> respostas, List<String> pontosFortes,
                                  List<String> pontosAMelhorar, List<String> alertas,
                                  Set<String> profissionaisRecomendados) {
        
        Map<String, Object> avaliacao = (Map<String, Object>) respostas.get("avaliacao");
        if (avaliacao == null) return;
        
        // 1. Registros zootécnicos
        String registros = (String) avaliacao.get("registrosZootecnicos");
        if ("sim".equals(registros)) {
            pontosFortes.add("Excelente! Manter registros zootécnicos ajuda no acompanhamento da produtividade e identificação de problemas.");
        } else if ("nao".equals(registros)) {
            pontosAMelhorar.add("Atenção! Registrar dados de produção, consumo de ração e mortalidade é fundamental para gestão da propriedade.");
        }
        
        // 2. Fotoperíodo
        String fotoperíodo = (String) avaliacao.get("fotoperíodo");
        if ("sim".equals(fotoperíodo)) {
            pontosFortes.add("Muito bem! O manejo correto da iluminação (16h de luz) é essencial para estimular a postura.");
        } else if ("nao".equals(fotoperíodo)) {
            alertas.add("Atenção! Manejo inadequado da iluminação compromete a produção. Galinhas necessitam de pelo menos 16 horas de luz diária.");
            profissionaisRecomendados.add("Médico Veterinário");
        }
        
        // 3. Qualidade da casca
        String qualidadeCasca = (String) avaliacao.get("qualidadeCasca");
        if ("semAlteracao".equals(qualidadeCasca)) {
            pontosFortes.add("Muito bem! A qualidade da casca está boa, preservando o conteúdo interno do ovo.");
        } else if ("rachaduras".equals(qualidadeCasca)) {
            alertas.add("Cuidado! Rachaduras e fissuras são portas de entrada para microrganismos. Realize coleta mais frequente e verifique a alimentação.");
            profissionaisRecomendados.add("Médico Veterinário");
        } else if ("rugosa".equals(qualidadeCasca)) {
            alertas.add("Cuidado! Casca rugosa pode indicar deficiências nutricionais, estresse térmico ou doenças como Newcastle/bronquite.");
            profissionaisRecomendados.add("Médico Veterinário");
        }
        
        // 4. Limpeza dos ovos
        String limpezaOvos = (String) avaliacao.get("limpezaOvos");
        if ("semAlteracao".equals(limpezaOvos)) {
            pontosFortes.add("Muito bem! Ovos limpos mostram que a postura não está sendo feita na cama e a coleta está correta.");
        } else if ("sujidades".equals(limpezaOvos)) {
            alertas.add("Cuidado! Ovos sujos podem contaminar com microrganismos. Verifique frequência da coleta e limpeza dos ninhos.");
            profissionaisRecomendados.add("Médico Veterinário");
        }
    }
    
    /**
     * Análise da seção Doenças (Influenza e Newcastle).
     */
    @SuppressWarnings("unchecked") 
    private void analisarDoencas(Map<String, Object> respostas, List<String> pontosFortes,
                                List<String> pontosAMelhorar, List<String> alertas,
                                Set<String> profissionaisRecomendados) {
        
        Map<String, Object> doencas = (Map<String, Object>) respostas.get("doencas");
        if (doencas == null) return;
        
        // 1. Mortes repentinas
        String mortesRepentinas = (String) doencas.get("mortesRepentinas");
        if ("sim".equals(mortesRepentinas)) {
            alertas.add("CRÍTICO! Mortes repentinas podem indicar Influenza Aviária ou Newcastle. Isolamento imediato necessário!");
            profissionaisRecomendados.add("Médico Veterinário");
            profissionaisRecomendados.add("Médico");
        } else if ("nao".equals(mortesRepentinas)) {
            pontosFortes.add("Não foram observadas mortes repentinas, indicando ausência de doenças graves até o momento.");
        }
        
        // 2. Queda repentina na produção
        String quedaProducao = (String) doencas.get("quedaProducaoRepentina");
        if ("sim".equals(quedaProducao)) {
            alertas.add("Atenção! Queda repentina na produção pode indicar doença, estresse ou problemas alimentares.");
        } else if ("nao".equals(quedaProducao)) {
            pontosFortes.add("Produção de ovos normal, suas aves parecem saudáveis.");
        }
        
        // 3. Sintomas neurológicos
        String sintomasNeurologicos = (String) doencas.get("sintomasNeurologicos");
        if ("sim".equals(sintomasNeurologicos)) {
            alertas.add("CRÍTICO! Tremores, torcicolo ou andar cambaleante indicam problemas neurológicos graves (Influenza/Newcastle)!");
            profissionaisRecomendados.add("Médico Veterinário");
            profissionaisRecomendados.add("Médico");
        } else if ("nao".equals(sintomasNeurologicos)) {
            pontosFortes.add("Ausência de sinais neurológicos é ótimo indicativo da sanidade das aves.");
        }
        
        // 4. Sintomas respiratórios
        String sintomasRespiratorios = (String) doencas.get("sintomasRespiratorios");
        if ("sim".equals(sintomasRespiratorios)) {
            alertas.add("Atenção! Tosse, espirros ou chiado indicam doenças respiratórias. Monitore se há aumento desses sinais.");
        } else if ("nao".equals(sintomasRespiratorios)) {
            pontosFortes.add("Aves sem sinais respiratórios indicam sanidade e bem-estar dos animais.");
        }
        
        // 5. Diarreia
        String diarreiaVerde = (String) doencas.get("diarreiaVerde");
        if ("sim".equals(diarreiaVerde)) {
            alertas.add("CRÍTICO! Diarreia verde/aquosa pode indicar infecção viral (Influenza Aviária ou Newcastle)!");
            profissionaisRecomendados.add("Médico Veterinário");
            profissionaisRecomendados.add("Médico");
        } else if ("nao".equals(diarreiaVerde)) {
            pontosFortes.add("Fezes normais indicam que as aves não apresentam alterações intestinais.");
        }
        
        // 6. Contato com aves silvestres
        String contatoAvesSilvestres = (String) doencas.get("contatoAvesSilvestres");
        if ("sim".equals(contatoAvesSilvestres)) {
            alertas.add("CRÍTICO! Contato com aves silvestres traz risco de Influenza Aviária e Newcastle. Impedir contato imediatamente!");
            profissionaisRecomendados.add("Médico Veterinário");
            profissionaisRecomendados.add("Médico");
        } else if ("nao".equals(contatoAvesSilvestres)) {
            pontosFortes.add("Importante continuar restringindo contato com aves silvestres para prevenir contaminação.");
        }
    }
}