package com.lisa.LojaVirtual.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lisa.LojaVirtual.Models.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}
