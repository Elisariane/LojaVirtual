package com.lisa.LojaVirtual.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lisa.LojaVirtual.Models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
