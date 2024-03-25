package com.nuclea.reembolsoapi.model;

public enum StatusReembolso {
    SOLICITACAO_EM_ANALISE("SOLICITACAO_EM_ANALISE"),
    APROVADO("APROVADO"),
    NEGADO("NEGADO"),
    CANCELAMENTO_EM_ANALISE("CANCELAMENTO_EM_ANALISE"),
    CANCELADO("CANCELADO");


    private String value;

    StatusReembolso(String value) {
        this.value = value;
    }


}
