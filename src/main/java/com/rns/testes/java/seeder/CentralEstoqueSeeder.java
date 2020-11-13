package com.rns.testes.java.seeder;

import com.rns.testes.java.dao.ICentroEstocagemDao;
import com.rns.testes.java.enums.EnumTipoFilial;
import com.rns.testes.java.model.CentralEstoque;
import com.rns.testes.java.model.Filial;
import com.rns.testes.java.service.IFilialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CentralEstoqueSeeder {

    @Autowired
    ICentroEstocagemDao service;

    @EventListener
    public void seedFilial(ContextRefreshedEvent event) {
        try {
            log.info("Criando central....");
            criandoCentral();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    private void criandoCentral() {
        CentralEstoque centralEstoque = new CentralEstoque();
        centralEstoque.setCnpj("83.230.227/0001-50");
        centralEstoque.setEndereco("Rua Tester Silva Galpao 1878, São Paulo - SP");

        service.save(centralEstoque);

    }
}
