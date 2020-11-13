package com.rns.testes.java.controller;

import com.rns.testes.java.dto.CentralEstoqueDto;
import com.rns.testes.java.dto.CentralEstoqueProdutosDto;
import com.rns.testes.java.dto.mapper.CentralEstoqueMapper;
import com.rns.testes.java.exceptions.StandardError;
import com.rns.testes.java.model.CentralEstoque;
import com.rns.testes.java.model.CentralEstoqueProdutos;
import com.rns.testes.java.service.ICentralEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping
public class CentralEstoqueController {

    private static final String BASE_URL = "central/";

    @Autowired
    ICentralEstoqueService service;



    @GetMapping(value = BASE_URL + "find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<CentralEstoque>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = BASE_URL + "find-by-id", produces = MediaType.APPLICATION_JSON_VALUE, params = {"id"})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<CentralEstoque> findById(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping(value = BASE_URL + "update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<CentralEstoque> update(@RequestBody CentralEstoqueDto dto) {
        return ResponseEntity.ok(service.update(CentralEstoqueMapper.INSTANCE.dtoToEntity(dto)));
    }

    @PostMapping(value = BASE_URL + "insert", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<CentralEstoque> insert(@RequestBody CentralEstoqueDto dto) {
        return ResponseEntity.ok(service.save(CentralEstoqueMapper.INSTANCE.dtoToEntity(dto)));
    }

    @DeleteMapping(value = BASE_URL + "delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestParam(name = "id") Long id) {
        service.delete(id);
    }

    @PostMapping(value = BASE_URL + "estoque/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Object> insertEstoque(@RequestBody CentralEstoqueProdutosDto dto) {
        try{
            return ResponseEntity.ok(service.saveEstoque(dto.getIdCentral(),dto.getIdProduto(),dto.getQuantidadeProduto()));
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
            service.deleteEstoque(id,idProduto);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(new StandardError(HttpStatus.BAD_REQUEST.value(),"operação não permitida",e.getMessage(),BASE_URL+"estoque/delete"));
        }
    }





//    @GetMapping(value = BASE_URL +"estoque", params = {"id"})
//    @ResponseStatus(value = HttpStatus.OK)
//    public ResponseEntity<FilialEstoqueProduto> getEstoque(@RequestParam(name = "id") Long id){
//        FilialEstoqueProduto filialEstoqueProduto = service.getEstoque(id).get();
//        if (filialEstoqueProduto !=null){
//            return ResponseEntity.ok(filialEstoqueProduto);
//        }
//       return  ResponseEntity.notFound().build();
//    }


}
