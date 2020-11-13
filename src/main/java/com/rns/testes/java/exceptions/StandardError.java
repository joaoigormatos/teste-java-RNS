package com.rns.testes.java.exceptions;

import lombok.Data;

import java.io.Serializable;

@Data
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1l;

    private Integer status;
    private String error;
    private String mensage;
    private String path;

    public StandardError(Integer status, String error, String mensage, String path) {
        super();
        this.status = status;
        this.error = error;
        this.mensage = mensage;
        this.path = path;
    }


}
