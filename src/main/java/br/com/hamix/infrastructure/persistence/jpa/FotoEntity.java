package br.com.hamix.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "foto")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "foto_id")
    private Integer id;

    private String nome;
    @Column(name = "nome_identificacao", nullable = false, length = 255)
    private String nomeIdentificacao;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alb_id", nullable = false)
    private AlbumEntity album;




}
