package com.rns.testes.java.service.impl;

import com.rns.testes.java.dao.IFilialDao;
import com.rns.testes.java.dao.IFilialEstoqueDao;
import com.rns.testes.java.model.*;
import com.rns.testes.java.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilialEstoqueService extends AbstractGenericServicePersistence<IFilialEstoqueDao, FilialEstoqueProduto, Long> implements IFilialEstoqueService {
    @Autowired
    IFilialService filialService;

    @Autowired
    ICentralEstoqueService centralEstoqueService;

    @Autowired
    IProdutoService produtoService;

    @Autowired
    IFilialEstoqueDao repositoryFilialEstoque;


    @Override
    public FilialEstoqueProduto addProdutoEstoque(Long idCentral, Long idFilial, String idProduto, int quantidade) {
        //Verificar se o idCentral é valido
        //Verificar se idFilial é valido
        //Verificar se o IdProduto é valido
        //Vericiar se tem a quantidade para retirada
        Filial filial = filialService.findById(idFilial);

        CentralEstoque centralEstoque = centralEstoqueService.findById(idCentral);


        Produto produto = produtoService.findById(idProduto);

        CentralEstoqueProdutos current = null;
        for (CentralEstoqueProdutos estoqueProdutos:
             centralEstoque.getCentralEstoqueProdutos()) {
            if(estoqueProdutos.getProduto().getId().equals(produto.getId())){
                current = estoqueProdutos;
            }
        }

        if(current.getQuandidadeProdutos() > quantidade){
            current.setQuandidadeProdutos(current.getQuandidadeProdutos() - quantidade);
        }
        FilialEstoqueProduto filialEstoqueProduto = new FilialEstoqueProduto(filial,produto,quantidade);

        filial.getFilialEstoqueProduto().add(filialEstoqueProduto);

        filialService.update(filial);

        centralEstoque.getCentralEstoqueProdutos().remove(current);
        centralEstoque.getCentralEstoqueProdutos().add(current);

        centralEstoqueService.update(centralEstoque);


        return filialEstoqueProduto;

    }
}
