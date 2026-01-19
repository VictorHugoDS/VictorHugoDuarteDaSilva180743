package br.com.hamix.persistence.jpa;


import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;


@Entity
@Table(name = "artista")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class ArtistaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "art_id")
    private Integer id;

    @NotBlank(message = "Nome do artista não pode ser nulo")
    @Column(name = "art_nome",length = 100,nullable = false)
    private String nome;

    @Column(name = "art_nome",length = 100)
    private String origem;

    @ManyToMany
    @JoinTable(
            name = "artista_album",
            joinColumns = @JoinColumn(name = "art_id"),
            inverseJoinColumns = @JoinColumn(name = "alb_id")
    )

    private ArrayList<AlbumEntity> unidades;
}
