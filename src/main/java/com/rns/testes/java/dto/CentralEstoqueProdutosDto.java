package com.rns.testes.java.dto;

import com.rns.testes.java.model.GenericEntity;
import lombok.Data;

@Data
public class CentralEstoqueProdutosDto extends GenericEntity<Long> {


    private Long idCentral;
    private String idProduto;
    private int  quantidadeProduto;


    @Override
    public Long getId() {
        return null;
    }
}
