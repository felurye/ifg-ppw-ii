package br.edu.ifg.sistemacomercial.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class Pedido_has_produto_PK implements Serializable {

    private Integer produto;
    private Integer pedido;

}
