package com.rns.testes.java.service;

import com.rns.testes.java.model.CentralEstoque;
import com.rns.testes.java.model.CentralEstoqueProdutos;
import com.rns.testes.java.model.Filial;

import java.util.List;

public interface ICentralEstoqueService extends IGenericService<CentralEstoque,Long> {
    CentralEstoqueProdutos saveEstoque(Long idCentral,String idProduto, int quantidade) throws Exception;
    List<CentralEstoqueProdutos> getAllEstoque(Long idCentral) throws Exception;
    CentralEstoqueProdutos getEstoqueById(Long id, String idProduto) throws Exception;
    CentralEstoqueProdutos updateEstoque(CentralEstoqueProdutos newCentralEstoqueProdutos) throws Exception;
    void deleteEstoque(Long id, String idProduto)throws Exception;
}
