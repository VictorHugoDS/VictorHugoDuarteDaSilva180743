package br.com.hamix.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alb_id", nullable = false)
    private AlbumEntity album;




}
