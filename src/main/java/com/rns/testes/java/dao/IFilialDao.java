package com.rns.testes.java.dao;

import com.rns.testes.java.model.Filial;
import com.rns.testes.java.model.FilialEstoqueProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface representa a camada de persistência da entidade Filial. Deve ser injetada <b>exclusivamente</b> em uma
 * camada service.
 */
public interface IFilialDao extends JpaRepository<Filial, Long> {
}
