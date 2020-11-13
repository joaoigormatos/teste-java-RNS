package com.rns.testes.java.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CENTRAL_ESTOQUE")
@SequenceGenerator(name = "CENTRAL_ESTOQUE_SEQ", sequenceName = "CENTRAL_ESTOQUE_SEQ", allocationSize = 1)

@Data
public class CentralEstoque  extends GenericEntity<Long>  {
    @Id
    @GeneratedValue(generator = "CENTRAL_ESTOQUE_SEQ", strategy = GenerationType.SEQUENCE)
    private Long id;

    @CNPJ
    @Column
    private String cnpj;

    @Column
    private String endereco;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CentralEstoqueProdutos> centralEstoqueProdutos;


}
