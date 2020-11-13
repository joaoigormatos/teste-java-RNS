package com.rns.testes.java.service.impl;

import com.rns.testes.java.dao.IFilialDao;
import com.rns.testes.java.dao.IFilialEstoqueDao;
import com.rns.testes.java.model.*;
import com.rns.testes.java.service.AbstractGenericServicePersistence;
import com.rns.testes.java.service.ICentralEstoqueService;
import com.rns.testes.java.service.IFilialService;
import com.rns.testes.java.service.IProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilialService extends AbstractGenericServicePersistence<IFilialDao, Filial, Long> implements IFilialService {
    @Autowired
    ICentralEstoqueService centralEstoqueService;

    @Autowired
    IProdutoService produtoService;

    @Override
    public FilialEstoqueProduto saveEstoque(Long idCentral, Long idFilial, String idProduto, int quantidade) throws Exception {
        CentralEstoque centralEstoque = centralEstoqueService.findById(idCentral);
        if(centralEstoque==null){
            throw new UnsupportedOperationException("Id da central é invalido!!!");
        }
        Filial filial = this.findById(idFilial);
        if(filial==null){
            throw new UnsupportedOperationException("Id da filial é invalido!!!");
        }
        Produto produto = produtoService.findById(idProduto);
        if(produto==null){
            throw new UnsupportedOperationException("Id do produto é invalido!!!");
        }

        CentralEstoqueProdutos centralEstoqueProdutos = centralEstoqueService.getEstoqueById(idCentral, idProduto);

        if(centralEstoqueProdutos.getQuandidadeProdutos() < quantidade)
            throw new UnsupportedOperationException("Quandidade informada maior que quantidade em estoque");
        //Atualiza estoque local
        FilialEstoqueProduto filialEstoqueProduto = new FilialEstoqueProduto(filial,produto,quantidade);

        filial.addFilialEstoqueProduto(filialEstoqueProduto);

        update(filial);
        //Atualiza estoque central
        centralEstoqueProdutos.diminuirQuantidade(quantidade);

        centralEstoqueService.updateEstoque(centralEstoqueProdutos);

        return filialEstoqueProduto;
    }

    @Override
    public List<FilialEstoqueProduto> getAllEstoque(Long idCentral) throws Exception {
        return findById(idCentral).getFilialEstoqueProduto();
    }

    @Override
    public FilialEstoqueProduto getEstoqueById(Long id, String idProduto) throws Exception {
        Produto produto = produtoService.findById(idProduto);
        Filial filial = findById(id);
        if(produto==null){
            throw new UnsupportedOperationException("Id do produto é invalido");
        }
        if(filial == null){
            throw new UnsupportedOperationException("Id da filial  é invalido");
        }
        List<FilialEstoqueProduto> filialEstoqueServiceList = filial.getFilialEstoqueProduto().stream().filter(filialEstoqueProduto -> filialEstoqueProduto.getProduto().equals(produto)).collect(Collectors.toList());

        if(filialEstoqueServiceList.size()>0){

            return filialEstoqueServiceList.get(0);
        }
        throw new NoSuchElementException("Estoque não encontrado");
    }

    @Override
    public void deleteEstoque(Long id, String idProduto) throws Exception {
        Filial filial = findById(id);
        if(filial==null){
            throw new UnsupportedOperationException("Id da filial  é invalido");
        }
        FilialEstoqueProduto findedCentralEstoqueProdutos = getEstoqueById(id,idProduto);

        List<FilialEstoqueProduto> filialEstoqueProdutoList = filial.getFilialEstoqueProduto().stream().filter(filialEstoqueProduto-> !filialEstoqueProduto.equals(findedCentralEstoqueProdutos)).collect(Collectors.toList());
        filial.setFilialEstoqueProduto(filialEstoqueProdutoList);
        update(filial);
    }

    @Override
    public List<FilialEstoqueProduto> exchangeFilial(Long idFilial, Long idFilialProvider, String idProduto, int quandidadeProdutoToExchange) throws Exception {
        if(idFilial.equals(idFilialProvider))
            throw new UnsupportedOperationException("A filial provedora não pode ser a filial que vai receber");
        Filial filial = findById(idFilial);
        Filial filialProvider = findById(idFilialProvider);
        Produto produto = produtoService.findById(idProduto);
        if(filial == null)
            throw new UnsupportedOperationException("Id da filial é invalido");
        if(filialProvider == null)
            throw new UnsupportedOperationException("Id da filial provedora é invalido");
        if(produto == null)
            throw new UnsupportedOperationException("Id do produto é invalido");
        FilialEstoqueProduto filialEstoqueProdutoProvider = getEstoqueById(idFilialProvider,idProduto);
        FilialEstoqueProduto filialEstoqueProduto = getEstoqueById(idFilial,idProduto);
        if(filialEstoqueProdutoProvider.getQuandidadeProduto() < quandidadeProdutoToExchange)
            throw new UnsupportedOperationException("Quandidade pedida é maior que a quandidade do produto disponivel na filial de codigo "+filialProvider.getId());
        filialEstoqueProduto.aumentarQuantidade(quandidadeProdutoToExchange);
        filialEstoqueProdutoProvider.diminuirQuantidade(quandidadeProdutoToExchange);

        updateEstoque(filialEstoqueProduto);
        updateEstoque(filialEstoqueProdutoProvider);

        List<FilialEstoqueProduto> resposeFilias = new ArrayList<>();
        resposeFilias.add(filialEstoqueProduto);
        resposeFilias.add(filialEstoqueProdutoProvider);

        return resposeFilias;
    }

    @Override
    public void updateEstoque(FilialEstoqueProduto newFilialEstoqueProduto) throws Exception {
        deleteEstoque(newFilialEstoqueProduto.getFilial().getId(),newFilialEstoqueProduto.getProduto().getId());
        Filial filial = newFilialEstoqueProduto.getFilial();
        filial.getFilialEstoqueProduto().add(newFilialEstoqueProduto);
        update(filial);
    }
}
