package com.algaworks.algafood.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Getter
@Entity
public class Pedido {

    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

   // @Column(nullable = false)
    private BigDecimal subtotal;

    //@Column(nullable = false)
    private BigDecimal taxaFrete;

    //@Column(nullable = false)
    private BigDecimal valorTotal;

    @Embedded
    private Endereco enderecoEntrega;

    private StatusPedido status;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCriacao;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataConfirmacao;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataCancelamento;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataEntrega;

    @ManyToOne
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;




}
