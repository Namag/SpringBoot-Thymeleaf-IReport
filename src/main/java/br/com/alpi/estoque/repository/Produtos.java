package br.com.alpi.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alpi.estoque.modelo.Produto;

public interface Produtos extends JpaRepository<Produto, Long>{

}
