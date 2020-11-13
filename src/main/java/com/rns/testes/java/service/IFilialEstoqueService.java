package com.rns.testes.java.service;

import com.rns.testes.java.model.Filial;
import com.rns.testes.java.model.FilialEstoqueProduto;

public interface IFilialEstoqueService extends IGenericService<FilialEstoqueProduto,Long> {
    FilialEstoqueProduto addProdutoEstoque(Long idCentral, Long idFilial, String idProduto, int quantidade);
}
