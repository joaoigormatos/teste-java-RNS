package com.rns.testes.java.controller;

import com.rns.testes.java.dto.CentralEstoqueProdutosDto;
import com.rns.testes.java.dto.FilialDto;
import com.rns.testes.java.dto.FilialEstoqueProdutoDto;
import com.rns.testes.java.dto.FilialEstoqueProdutoExchangeDto;
import com.rns.testes.java.dto.mapper.FilialMapper;
import com.rns.testes.java.enums.EnumTipoFilial;
import com.rns.testes.java.exceptions.StandardError;
import com.rns.testes.java.model.Filial;
import com.rns.testes.java.model.FilialEstoqueProduto;
import com.rns.testes.java.service.IFilialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping
public class FilialController {

    private static final String BASE_URL = "filial/";

    @Autowired
    IFilialService service;

    @GetMapping(value = BASE_URL + "find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<Filial>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = BASE_URL + "find-by-id", produces = MediaType.APPLICATION_JSON_VALUE, params = {"id"})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Filial> findById(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping(value = BASE_URL + "update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Filial> update(@RequestBody FilialDto dto) {
        return ResponseEntity.ok(service.update(FilialMapper.INSTANCE.dtoToEntity(dto)));
    }

    @PostMapping(value = BASE_URL + "insert", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Filial> insert(@RequestBody FilialDto dto) {
        return ResponseEntity.ok(service.save(FilialMapper.INSTANCE.dtoToEntity(dto)));
    }

    @DeleteMapping(value = BASE_URL + "delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestParam(name = "id") Long id) {
        service.delete(id);
    }



    @GetMapping(value = BASE_URL + "tipo/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<EnumTipoFilial>> findAllEnumTipoFilial() {
        return ResponseEntity.ok(EnumTipoFilial.getAll());
    }

    @PostMapping(value = BASE_URL + "estoque/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Object> insertEstoque(@RequestBody FilialEstoqueProdutoDto dto) {
        try{
            return ResponseEntity.ok(service.saveEstoque(dto.getIdCentral(),dto.getIdFilial(),dto.getIdProduto(),dto.getQuantidadeProduto()));
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(new StandardError(HttpStatus.BAD_REQUEST.value(),"operação não permitida",e.getMessage(),BASE_URL+"estoque/insert"));
        }
    }

    @PostMapping(value = BASE_URL + "estoque/exchange", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Object> estoqueExchange(@RequestBody FilialEstoqueProdutoExchangeDto dto) {
        try{
            return ResponseEntity.ok(service.exchangeFilial(dto.getIdFilial(),dto.getIdFilialProvider(),dto.getIdProduto(),dto.getQuantidadeProduto()));
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(new StandardError(HttpStatus.BAD_REQUEST.value(),"operação não permitida",e.getMessage(),BASE_URL+"estoque/insert"));
        }
    }



    @GetMapping(value = BASE_URL + "estoque/find-all", produces = MediaType.APPLICATION_JSON_VALUE, params = {"id"})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Object> getAllEstoque(@RequestParam Long id) {
        try{
            return ResponseEntity.ok(service.getAllEstoque(id));
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(new StandardError(HttpStatus.BAD_REQUEST.value(),"operação não permitida",e.getMessage(),BASE_URL+"estoque/find-all"));
        }
    }

    @GetMapping(value = BASE_URL + "estoque/find-by-id", produces = MediaType.APPLICATION_JSON_VALUE, params = {"id","idProduto"})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Object> getEstoqueById(@RequestParam Long id, @RequestParam String idProduto) {
        try{
            return ResponseEntity.ok(service.getEstoqueById(id,idProduto));
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(new StandardError(HttpStatus.BAD_REQUEST.value(),"operação não permitida",e.getMessage(),BASE_URL+"estoque/find-by-id"));

        }
    }

    @DeleteMapping(value = BASE_URL + "estoque/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Object> deleteEstoque(@RequestParam(name = "id") Long id, @RequestParam(name = "idProduto") String idProduto) {

        try{
            System.out.println(idProduto);
            service.deleteEstoque(id,idProduto);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(new StandardError(HttpStatus.BAD_REQUEST.value(),"operação não permitida",e.getMessage(),BASE_URL+"estoque/delete"));
        }
    }





}
