package com.avicheckpoint.service;

import com.avicheckpoint.dto.ProdutorRequestDTO;
import com.avicheckpoint.dto.UsuarioResponseDTO;
import com.avicheckpoint.dto.VeterinarioRequestDTO;
import com.avicheckpoint.model.Endereco;
import com.avicheckpoint.model.Produtor;
import com.avicheckpoint.model.Usuario;
import com.avicheckpoint.model.Veterinario;
import com.avicheckpoint.repository.ProdutorRepositoryImpl;
import com.avicheckpoint.repository.VeterinarioRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciar operações de usuários.
 * Centraliza lógica de negócio e validações.
 */
@Service
public class UsuarioService {
    
    @Autowired
    private ProdutorRepositoryImpl produtorRepository;
    
    @Autowired
    private VeterinarioRepositoryImpl veterinarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Cadastra um novo produtor.
     */
    public UsuarioResponseDTO cadastrarProdutor(ProdutorRequestDTO dto) {
        // Validar se email já existe
        if (produtorRepository.existePorEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + dto.getEmail());
        }
        
        // Criar produtor
        Produtor produtor = new Produtor();
        produtor.setUsuarioNomeCompleto(dto.getNome());
        produtor.setUsuarioEmail(dto.getEmail());
        produtor.setUsuarioSenha(passwordEncoder.encode(dto.getSenha()));
        produtor.setDataCriacao(LocalDateTime.now());
        
        // Criar endereço
        Endereco endereco = new Endereco();
        endereco.setEstado(dto.getUf());
        endereco.setCidade(dto.getCidade());
        produtor.setEndereco(endereco);
        
        // Salvar
        Produtor salvo = produtorRepository.salvar(produtor);
        
        return mapearParaResponse(salvo);
    }
    
    /**
     * Cadastra um novo veterinário.
     */
    public UsuarioResponseDTO cadastrarVeterinario(VeterinarioRequestDTO dto) {
        // Validar se email já existe
        if (veterinarioRepository.existePorEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + dto.getEmail());
        }
        
        // Criar veterinário
        Veterinario veterinario = new Veterinario();
        veterinario.setUsuarioNomeCompleto(dto.getNome());
        veterinario.setUsuarioEmail(dto.getEmail());
        veterinario.setUsuarioSenha(passwordEncoder.encode(dto.getSenha()));
        veterinario.setFormacao(dto.getFormacao());
        veterinario.setBio(dto.getBio());
        veterinario.setVeterinarioCRM(dto.getCrm());
        veterinario.setDataCriacao(LocalDateTime.now());
        
        // Criar endereço
        Endereco endereco = new Endereco();
        endereco.setEstado(dto.getUf());
        endereco.setCidade(dto.getCidade());
        veterinario.setEndereco(endereco);
        
        // Salvar
        Veterinario salvo = veterinarioRepository.salvar(veterinario);
        
        return mapearParaResponse(salvo);
    }
    
    /**
     * Busca produtor por ID.
     */
    public Optional<UsuarioResponseDTO> buscarProdutorPorId(Integer id) {
        return produtorRepository.buscarPorId(id)
                .map(this::mapearParaResponse);
    }
    
    /**
     * Busca veterinário por ID.
     */
    public Optional<UsuarioResponseDTO> buscarVeterinarioPorId(Integer id) {
        return veterinarioRepository.buscarPorId(id)
                .map(this::mapearParaResponse);
    }
    
    /**
     * Lista veterinários para recomendação.
     */
    public List<UsuarioResponseDTO> buscarVeterinariosParaRecomendacao(String uf, String especialidade) {
        List<Veterinario> veterinarios = veterinarioRepository.buscarParaRecomendacao(uf, especialidade);
        return veterinarios.stream()
                .map(this::mapearParaResponse)
                .toList();
    }
    
    /**
     * Busca usuário por email (para login).
     */
    public Optional<Usuario> buscarPorEmail(String email) {
        // Buscar em produtores
        Optional<Produtor> produtor = produtorRepository.buscarPorEmail(email);
        if (produtor.isPresent()) {
            return Optional.of(produtor.get());
        }
        
        // Buscar em veterinários
        Optional<Veterinario> veterinario = veterinarioRepository.buscarPorEmail(email);
        return veterinario.map(v -> v);
    }
    
    /**
     * Atualiza dados do produtor.
     */
    public UsuarioResponseDTO atualizarProdutor(Integer id, ProdutorRequestDTO dto) {
        Produtor produtor = produtorRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Produtor não encontrado"));
        
        // Verificar se email mudou e se já existe
        if (!produtor.getUsuarioEmail().equals(dto.getEmail()) && 
            produtorRepository.existePorEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + dto.getEmail());
        }
        
        // Atualizar dados
        produtor.setUsuarioNomeCompleto(dto.getNome());
        produtor.setUsuarioEmail(dto.getEmail());
        if (dto.getSenha() != null && !dto.getSenha().trim().isEmpty()) {
            produtor.setUsuarioSenha(passwordEncoder.encode(dto.getSenha()));
        }
        
        // Atualizar endereço
        if (produtor.getEndereco() == null) {
            produtor.setEndereco(new Endereco());
        }
        produtor.getEndereco().setEstado(dto.getUf());
        produtor.getEndereco().setCidade(dto.getCidade());
        
        Produtor atualizado = produtorRepository.atualizar(produtor);
        return mapearParaResponse(atualizado);
    }
    
    /**
     * Exclui produtor.
     */
    public boolean excluirProdutor(Integer id) {
        return produtorRepository.excluir(id);
    }
    
    /**
     * Exclui veterinário.
     */
    public boolean excluirVeterinario(Integer id) {
        return veterinarioRepository.excluir(id);
    }
    
    /**
     * Mapeia Usuario para UsuarioResponseDTO.
     */
    private UsuarioResponseDTO mapearParaResponse(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setUsuarioId(usuario.getUsuarioId());
        dto.setUsuarioNomeCompleto(usuario.getUsuarioNomeCompleto());
        dto.setUsuarioEmail(usuario.getUsuarioEmail());
        dto.setTipoUsuario(usuario.getTipoUsuario());
        dto.setDataCriacao(usuario.getDataCriacao());
        
        // Mapear endereço
        if (usuario.getEndereco() != null) {
            dto.setEstado(usuario.getEndereco().getEstado());
            dto.setCidade(usuario.getEndereco().getCidade());
        }
        
        // Dados específicos de veterinário
        if (usuario instanceof Veterinario veterinario) {
            dto.setFormacao(veterinario.getFormacao());
            dto.setBio(veterinario.getBio());
            dto.setVeterinarioCRM(veterinario.getVeterinarioCRM());
        }
        
        return dto;
    }
}