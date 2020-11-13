package com.rns.testes.java.service;

import com.rns.testes.java.model.CentralEstoqueProdutos;
import com.rns.testes.java.model.Filial;
import com.rns.testes.java.model.FilialEstoqueProduto;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Exchanger;

public interface IFilialService extends IGenericService<Filial,Long> {

    FilialEstoqueProduto saveEstoque(Long idCentral, Long idFilial ,String idProduto, int quantidade) throws Exception;
    List<FilialEstoqueProduto> getAllEstoque(Long idCentral) throws Exception;
    FilialEstoqueProduto getEstoqueById(Long id, String idProduto) throws Exception;
    void deleteEstoque(Long id, String idProduto)throws Exception;
    List<FilialEstoqueProduto> exchangeFilial(Long idFilial,Long idFilialProvider, String idProduto, int quandidadeProdutoToExchange) throws Exception;
    void updateEstoque(FilialEstoqueProduto newFilialEstoqueProduto) throws Exception;

}
