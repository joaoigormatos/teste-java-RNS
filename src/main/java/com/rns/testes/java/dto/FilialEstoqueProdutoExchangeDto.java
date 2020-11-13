package com.rns.testes.java.dto;

import lombok.Data;

@Data
public class FilialEstoqueProdutoExchangeDto {

    private Long idFilial;
    private Long idFilialProvider;
    private String idProduto;
    private int  quantidadeProduto;

}
