package com.rns.testes.java.dto;

import lombok.Data;

@Data
public class FilialEstoqueProdutoDto {

    private Long idCentral;
    private Long idFilial;
    private String idProduto;
    private int  quantidadeProduto;

}
