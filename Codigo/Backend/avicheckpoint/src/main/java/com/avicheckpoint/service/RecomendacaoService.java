package com.avicheckpoint.service;

import com.avicheckpoint.dto.VeterinarioRecomendadoDTO;
import com.avicheckpoint.model.FormularioResposta;
import com.avicheckpoint.model.Produtor;
import com.avicheckpoint.model.ResultadoAnalise;
import com.avicheckpoint.model.Veterinario;
import com.avicheckpoint.repository.FormularioRepositoryImpl;
import com.avicheckpoint.repository.ProdutorRepositoryImpl;
import com.avicheckpoint.repository.VeterinarioRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Serviço para recomendação inteligente de veterinários.
 * Baseado em proximidade geográfica, especialização e necessidades do produtor.
 */
@Service
public class RecomendacaoService {
    
    @Autowired
    private VeterinarioRepositoryImpl veterinarioRepository;
    
    @Autowired
    private ProdutorRepositoryImpl produtorRepository;
    
    @Autowired
    private FormularioRepositoryImpl formularioRepository;
    
    // Raio máximo de busca em km
    private static final double RAIO_MAXIMO_KM = 100.0;
    
    /**
     * Recomenda veterinários para um produtor baseado na última análise.
     */
    public List<VeterinarioRecomendadoDTO> recomendarVeterinarios(Integer produtorId) {
        return recomendarVeterinarios(produtorId, RAIO_MAXIMO_KM, 5);
    }
    
    /**
     * Recomenda veterinários com parâmetros customizados.
     */
    public List<VeterinarioRecomendadoDTO> recomendarVeterinarios(Integer produtorId, 
                                                               Double raioKm, 
                                                               Integer limite) {
        // Buscar produtor
        Produtor produtor = produtorRepository.buscarPorId(produtorId)
                .orElseThrow(() -> new RuntimeException("Produtor não encontrado: " + produtorId));
        
        // Buscar última análise do produtor
        List<FormularioResposta> formularios = formularioRepository.buscarPorProdutor(produtorId);
        Optional<FormularioResposta> ultimaAnalise = formularios.stream()
                .filter(f -> f.getResultado() != null)
                .max(Comparator.comparing(FormularioResposta::getDataPreenchimento));
        
        // Buscar todos os veterinários
        List<Veterinario> todosVeterinarios = veterinarioRepository.listarTodos();
        
        // Filtrar por proximidade e calcular recomendações
        List<VeterinarioRecomendadoDTO> recomendacoes = todosVeterinarios.stream()
                .map(vet -> calcularRecomendacao(vet, produtor, ultimaAnalise.orElse(null)))
                .filter(rec -> rec.getDistanciaKm() <= raioKm)
                .sorted(Comparator
                        .comparing(VeterinarioRecomendadoDTO::getPontuacaoCompatibilidade, Comparator.reverseOrder())
                        .thenComparing(VeterinarioRecomendadoDTO::getDistanciaKm))
                .limit(limite)
                .collect(Collectors.toList());
        
        return recomendacoes;
    }
    
    /**
     * Recomenda veterinários baseado em problemas específicos detectados.
     */
    public List<VeterinarioRecomendadoDTO> recomendarPorProblemas(Integer produtorId, 
                                                               List<String> problemasDetectados) {
        Produtor produtor = produtorRepository.buscarPorId(produtorId)
                .orElseThrow(() -> new RuntimeException("Produtor não encontrado: " + produtorId));
        
        List<Veterinario> veterinarios = veterinarioRepository.listarTodos();
        
        return veterinarios.stream()
                .map(vet -> calcularRecomendacaoPorProblemas(vet, produtor, problemasDetectados))
                .filter(rec -> rec.getPontuacaoCompatibilidade() > 50) // Mínimo de compatibilidade
                .sorted(Comparator
                        .comparing(VeterinarioRecomendadoDTO::getPontuacaoCompatibilidade, Comparator.reverseOrder())
                        .thenComparing(VeterinarioRecomendadoDTO::getDistanciaKm))
                .limit(3)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca veterinários por especialidade em uma região.
     */
    public List<VeterinarioRecomendadoDTO> buscarPorEspecialidade(String especialidade, 
                                                               String cidade, 
                                                               String uf) {
        List<Veterinario> veterinarios = veterinarioRepository.listarTodos();
        
        return veterinarios.stream()
                .filter(vet -> {
                    // Filtrar por especialidade (simulado - na prática seria um campo no modelo)
                    String bio = vet.getBio() != null ? vet.getBio().toLowerCase() : "";
                    return bio.contains(especialidade.toLowerCase()) ||
                           bio.contains("avicultura") || 
                           bio.contains("aves");
                })
                .filter(vet -> {
                    // Filtrar por localização
                    return vet.getEndereco() != null && 
                           vet.getEndereco().getCidade().equalsIgnoreCase(cidade) &&
                           vet.getEndereco().getEstado().equalsIgnoreCase(uf);
                })
                .map(this::mapearVeterinarioParaDTO)
                .sorted(Comparator.comparing(VeterinarioRecomendadoDTO::getPontuacaoCompatibilidade, 
                                           Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
    
    /**
     * Calcula recomendação para um veterinário específico.
     */
    private VeterinarioRecomendadoDTO calcularRecomendacao(Veterinario veterinario, 
                                                         Produtor produtor, 
                                                         FormularioResposta ultimaAnalise) {
        VeterinarioRecomendadoDTO dto = mapearVeterinarioParaDTO(veterinario);
        
        // Calcular distância (simulada - na prática usaria coordenadas reais)
        double distancia = calcularDistanciaSimulada(veterinario, produtor);
        dto.setDistanciaKm(distancia);
        
        // Calcular pontuação de compatibilidade
        int pontuacao = calcularPontuacaoCompatibilidade(veterinario, ultimaAnalise);
        dto.setPontuacaoCompatibilidade(pontuacao);
        
        // Definir motivo da recomendação
        String motivo = gerarMotivoRecomendacao(veterinario, ultimaAnalise, distancia);
        dto.setMotivoRecomendacao(motivo);
        
        // Status de disponibilidade (simulado)
        dto.setDisponivel(true);
        
        return dto;
    }
    
    /**
     * Calcula recomendação baseada em problemas específicos.
     */
    private VeterinarioRecomendadoDTO calcularRecomendacaoPorProblemas(Veterinario veterinario, 
                                                                     Produtor produtor, 
                                                                     List<String> problemas) {
        VeterinarioRecomendadoDTO dto = mapearVeterinarioParaDTO(veterinario);
        
        double distancia = calcularDistanciaSimulada(veterinario, produtor);
        dto.setDistanciaKm(distancia);
        
        // Pontuação baseada na especialização para os problemas
        int pontuacao = calcularPontuacaoPorEspecializacao(veterinario, problemas);
        dto.setPontuacaoCompatibilidade(pontuacao);
        
        String motivo = "Especialização em: " + String.join(", ", problemas);
        dto.setMotivoRecomendacao(motivo);
        
        dto.setDisponivel(true);
        
        return dto;
    }
    
    /**
     * Mapeia Veterinario para VeterinarioRecomendadoDTO.
     */
    private VeterinarioRecomendadoDTO mapearVeterinarioParaDTO(Veterinario veterinario) {
        VeterinarioRecomendadoDTO dto = new VeterinarioRecomendadoDTO();
        
        // Dados básicos
        dto.setVeterinarioId(veterinario.getUsuarioId());
        dto.setNome(veterinario.getUsuarioNomeCompleto());
        dto.setEmail(veterinario.getUsuarioEmail());
        dto.setCrmv(veterinario.getVeterinarioCRM());
        dto.setBio(veterinario.getBio());
        
        // Endereco
        if (veterinario.getEndereco() != null) {
            dto.setEndereco("Número " + veterinario.getEndereco().getEnderecoNumero() + 
                           " - CEP: " + veterinario.getEndereco().getEnderecoCEP());
            dto.setCidade(veterinario.getEndereco().getCidade());
            dto.setUf(veterinario.getEndereco().getEstado());
            dto.setCep(veterinario.getEndereco().getEnderecoCEP());
        }
        
        // Especialidades (simuladas baseadas na bio)
        List<String> especialidades = extrairEspecialidades(veterinario.getBio());
        dto.setEspecialidades(especialidades);
        
        // Métricas simuladas
        dto.setAnosExperiencia(gerarExperienciaSimulada());
        dto.setAvaliacaoMedia(gerarAvaliacaoSimulada());
        dto.setTotalAvaliacoes(gerarTotalAvaliacoesSimulado());
        
        return dto;
    }
    
    /**
     * Calcula distância simulada entre veterinário e produtor.
     */
    private double calcularDistanciaSimulada(Veterinario veterinario, Produtor produtor) {
        // Simulação baseada em cidades (na prática seria cálculo de coordenadas)
        if (veterinario.getEndereco() != null && produtor.getEndereco() != null) {
            String cidadeVet = veterinario.getEndereco().getCidade();
            String cidadeProd = produtor.getEndereco().getCidade();
            
            if (cidadeVet.equals(cidadeProd)) {
                return Math.random() * 15; // Mesma cidade: 0-15km
            } else {
                return 20 + (Math.random() * 60); // Cidades diferentes: 20-80km
            }
        }
        return 50.0; // Distância padrão
    }
    
    /**
     * Calcula pontuação de compatibilidade baseada na análise.
     */
    private int calcularPontuacaoCompatibilidade(Veterinario veterinario, FormularioResposta analise) {
        int pontuacao = 60; // Base
        
        // Bonus por experiência com aves
        String bio = veterinario.getBio() != null ? veterinario.getBio().toLowerCase() : "";
        if (bio.contains("avicultura") || bio.contains("aves")) {
            pontuacao += 20;
        }
        
        // Bonus baseado na análise
        if (analise != null && analise.getResultado() != null) {
            ResultadoAnalise resultado = analise.getResultado();
            
            // Se tem alertas críticos, bonus para especialistas
            if (resultado.getComentarios() != null) {
                long alertasCriticos = resultado.getComentarios().stream()
                        .filter(c -> c.toLowerCase().contains("crítico"))
                        .count();
                if (alertasCriticos > 0) {
                    pontuacao += 10;
                }
            }
            
            // Bonus por pontuação baixa (mais urgente)
            if (resultado.getPontuacaoGeral() != null && resultado.getPontuacaoGeral() < 50) {
                pontuacao += 5;
            }
        }
        
        return Math.min(100, Math.max(0, pontuacao));
    }
    
    /**
     * Calcula pontuação baseada em especialização para problemas específicos.
     */
    private int calcularPontuacaoPorEspecializacao(Veterinario veterinario, List<String> problemas) {
        int pontuacao = 40; // Base menor para busca específica
        
        String bio = veterinario.getBio() != null ? veterinario.getBio().toLowerCase() : "";
        
        for (String problema : problemas) {
            if (bio.contains(problema.toLowerCase())) {
                pontuacao += 15;
            }
        }
        
        // Bonus geral por avicultura
        if (bio.contains("avicultura") || bio.contains("aves")) {
            pontuacao += 20;
        }
        
        return Math.min(100, pontuacao);
    }
    
    /**
     * Gera motivo da recomendação.
     */
    private String gerarMotivoRecomendacao(Veterinario veterinario, 
                                         FormularioResposta analise, 
                                         double distancia) {
        List<String> motivos = new ArrayList<>();
        
        if (distancia <= 20) {
            motivos.add("Proximidade geográfica");
        }
        
        String bio = veterinario.getBio() != null ? veterinario.getBio().toLowerCase() : "";
        if (bio.contains("avicultura")) {
            motivos.add("Especialista em avicultura");
        }
        
        if (analise != null && analise.getResultado() != null && 
            analise.getResultado().getPontuacaoGeral() != null &&
            analise.getResultado().getPontuacaoGeral() < 50) {
            motivos.add("Urgência da situação");
        }
        
        return motivos.isEmpty() ? "Profissional qualificado" : String.join(", ", motivos);
    }
    
    /**
     * Extrai especialidades da bio do veterinário.
     */
    private List<String> extrairEspecialidades(String bio) {
        List<String> especialidades = new ArrayList<>();
        
        if (bio != null) {
            String bioLower = bio.toLowerCase();
            
            if (bioLower.contains("avicultura")) especialidades.add("Avicultura");
            if (bioLower.contains("aves")) especialidades.add("Medicina de Aves");
            if (bioLower.contains("nutrição")) especialidades.add("Nutrição Animal");
            if (bioLower.contains("reprodução")) especialidades.add("Reprodução Animal");
            if (bioLower.contains("clínica")) especialidades.add("Clínica Geral");
        }
        
        if (especialidades.isEmpty()) {
            especialidades.add("Medicina Veterinária Geral");
        }
        
        return especialidades;
    }
    
    /**
     * Gera anos de experiência simulados.
     */
    private Integer gerarExperienciaSimulada() {
        return (int) (Math.random() * 20) + 2; // 2-22 anos
    }
    
    /**
     * Gera avaliação média simulada.
     */
    private Double gerarAvaliacaoSimulada() {
        return 3.5 + (Math.random() * 1.5); // 3.5-5.0
    }
    
    /**
     * Gera total de avaliações simulado.
     */
    private Integer gerarTotalAvaliacoesSimulado() {
        return (int) (Math.random() * 50) + 5; // 5-55 avaliações
    }
}