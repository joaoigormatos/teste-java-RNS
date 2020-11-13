package com.rns.testes.java.model;

import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CENTRAL_ESTOQUE_PRODUTOS")
@SequenceGenerator(name = "CENTRAL_ESTOQUE_PRODUTOS_SEQ", sequenceName = "CENTRAL_ESTOQUE_Produtos_SEQ", allocationSize = 1)

@Data

public class CentralEstoqueProdutos  extends GenericEntity<Long> {
    @Id
    @GeneratedValue(generator = "CENTRAL_ESTOQUE_PRODUTOS_SEQ", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="central_estoque_id")
    private CentralEstoque centralEstoque;

    @ManyToOne
    @JoinColumn(name="produto_id")
    private Produto produto;

    @Column
    private int quandidadeProdutos;

    public CentralEstoqueProdutos(CentralEstoque centralEstoque, Produto produto, int quandidadeProdutos){
        this.centralEstoque = centralEstoque;
        this.produto = produto;
        this.quandidadeProdutos = quandidadeProdutos;
    }
    public CentralEstoqueProdutos(){

    }

    public void diminuirQuantidade(int quantidade){
        this.quandidadeProdutos -=quantidade;
    }

    public void aumentarQuantidade(int quantidade){
        this.quandidadeProdutos +=quantidade;
    }


}
