package br.com.cotiinformatica.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.domain.entities.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {

	Optional<Endereco> findByIdAndClienteId(UUID enderecoId, UUID clienteId);
}
