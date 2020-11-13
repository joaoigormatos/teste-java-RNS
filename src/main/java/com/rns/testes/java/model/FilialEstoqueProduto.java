package com.rns.testes.java.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "FILIAL_ESTOQUE")
@SequenceGenerator(name = "FILIAL_SEQ", sequenceName = "FILIAL_SEQ", allocationSize = 1)

@Data
public class FilialEstoqueProduto  extends GenericEntity<Long>{
    @Id
    @GeneratedValue(generator = "FILIAL_SEQ", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="filial_id")
    private Filial filial;

    @ManyToOne
    @JoinColumn(name="produto_id")
    private Produto produto;

    @Column
    private int quandidadeProduto;

    public FilialEstoqueProduto(){

    }

    public FilialEstoqueProduto(int quandidadeProduto){
        this.quandidadeProduto = quandidadeProduto;
    }

    public FilialEstoqueProduto(Filial filial, Produto produto,int quandidadeProduto){
        this.filial = filial;
        this.quandidadeProduto = quandidadeProduto;
        this.produto = produto;
    }

    public void diminuirQuantidade(int quantidade){
        this.quandidadeProduto -=quantidade;
    }

    public void aumentarQuantidade(int quantidade){
        this.quandidadeProduto +=quantidade;
    }
}
