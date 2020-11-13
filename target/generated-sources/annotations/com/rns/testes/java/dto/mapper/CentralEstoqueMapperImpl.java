package com.rns.testes.java.dto.mapper;

import com.rns.testes.java.dto.CentralEstoqueDto;
import com.rns.testes.java.model.CentralEstoque;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-11-11T23:56:04-0300",
    comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 1.8.0_261 (Oracle Corporation)"
)
public class CentralEstoqueMapperImpl implements CentralEstoqueMapper {

    @Override
    public CentralEstoqueDto entityToDto(CentralEstoque entity) {
        if ( entity == null ) {
            return null;
        }

        CentralEstoqueDto centralEstoqueDto = new CentralEstoqueDto();

        centralEstoqueDto.setVersao( entity.getVersao() );
        centralEstoqueDto.setDataUltAlteracao( entity.getDataUltAlteracao() );
        centralEstoqueDto.setId( entity.getId() );
        centralEstoqueDto.setCnpj( entity.getCnpj() );
        centralEstoqueDto.setEndereco( entity.getEndereco() );

        return centralEstoqueDto;
    }

    @Override
    public CentralEstoque dtoToEntity(CentralEstoqueDto dto) {
        if ( dto == null ) {
            return null;
        }

        CentralEstoque centralEstoque = new CentralEstoque();

        centralEstoque.setVersao( dto.getVersao() );
        centralEstoque.setDataUltAlteracao( dto.getDataUltAlteracao() );
        centralEstoque.setId( dto.getId() );
        centralEstoque.setCnpj( dto.getCnpj() );
        centralEstoque.setEndereco( dto.getEndereco() );

        return centralEstoque;
    }
}
