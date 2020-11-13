package com.rns.testes.java.service.impl;


import com.rns.testes.java.dao.ICentroEstocagemDao;
import com.rns.testes.java.dao.IProdutoDao;
import com.rns.testes.java.model.CentralEstoque;
import com.rns.testes.java.model.CentralEstoqueProdutos;
import com.rns.testes.java.model.Produto;
import com.rns.testes.java.service.AbstractGenericServicePersistence;
import com.rns.testes.java.service.ICentralEstoqueService;
import com.rns.testes.java.service.IProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CentralEstoqueService extends AbstractGenericServicePersistence<ICentroEstocagemDao, CentralEstoque, Long> implements ICentralEstoqueService {
    @Autowired
    ICentroEstocagemDao dao;

    @Autowired
    ICentralEstoqueService centralEstoqueService;

    @Autowired
    IProdutoService produtoService;

    @Override
    public CentralEstoqueProdutos saveEstoque(Long idCentral,String idProduto, int quantidade) {


        CentralEstoque centralEstoque = centralEstoqueService.findById(idCentral);
        Produto produto = produtoService.findById(idProduto);
        if(produto==null){
            throw new UnsupportedOperationException("Id do produto é invalido");
        }
        if(centralEstoque==null){
            throw new UnsupportedOperationException("Id da central de estoque é invalido");
        }

        CentralEstoqueProdutos centralEstoqueProdutos = null;

        if(quantidade > 0) {
            centralEstoqueProdutos = new CentralEstoqueProdutos(centralEstoque, produto, quantidade);

            centralEstoque.getCentralEstoqueProdutos().add(centralEstoqueProdutos);
            centralEstoqueService.update(centralEstoque);
            return centralEstoqueProdutos;

        }
        else throw new UnsupportedOperationException("Quandidade invalida");
    }

    @Override
    public List<CentralEstoqueProdutos> getAllEstoque(Long idCentral) {
            return findById(idCentral).getCentralEstoqueProdutos();
    }

    @Override
    public CentralEstoqueProdutos getEstoqueById(Long id, String idProduto) {
        Produto produto = produtoService.findById(idProduto);
        CentralEstoque centralEstoque =findById(id);
        if(produto==null){
            throw new UnsupportedOperationException("Id do produto é invalido");
        }
        if(centralEstoque == null){
            throw new UnsupportedOperationException("Id da central de estoque é invalido");
        }
        List<CentralEstoqueProdutos> centralEstoqueProdutosFinded = centralEstoque.getCentralEstoqueProdutos().stream().filter(centralEstoqueProdutos -> centralEstoqueProdutos.getProduto().equals(produto)).collect(Collectors.toList());

        if(centralEstoqueProdutosFinded.size()>0){

            return centralEstoqueProdutosFinded.get(0);
        }
        throw new NoSuchElementException("Estoque não encontrado");
    }

    @Override
    public CentralEstoqueProdutos updateEstoque(CentralEstoqueProdutos newCentralEstoqueProdutos) throws Exception {
        deleteEstoque(newCentralEstoqueProdutos.getCentralEstoque().getId(),newCentralEstoqueProdutos.getProduto().getId());
        saveEstoque(newCentralEstoqueProdutos.getCentralEstoque().getId(),newCentralEstoqueProdutos.getProduto().getId(),newCentralEstoqueProdutos.getQuandidadeProdutos());
        return newCentralEstoqueProdutos;
    }

    @Override
    public void deleteEstoque(Long id, String idProduto) {

        CentralEstoque centralEstoque = centralEstoqueService.findById(id);
        if(centralEstoque==null){
            throw new UnsupportedOperationException("Id da central de estoque é invalido");
        }
        CentralEstoqueProdutos findedCentralEstoqueProdutos = getEstoqueById(id,idProduto);

        List<CentralEstoqueProdutos> centralEstoqueProdutosList = centralEstoque.getCentralEstoqueProdutos().stream().filter(centralEstoqueProdutos-> !centralEstoqueProdutos.equals(findedCentralEstoqueProdutos)).collect(Collectors.toList());
        centralEstoque.setCentralEstoqueProdutos(centralEstoqueProdutosList);
        centralEstoqueService.update(centralEstoque);
    }



}
