package br.com.hamix.persistence.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;


@Entity
@Table(name = "album")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alb_id")
    private Integer id;

    @NotBlank(message = "Nome do album não pode ser nulo")
    @Column(name = "alb_nome",length = 100,nullable = false)
    private String nome;

    @Column(name = "alb_ano",length = 20)
    private String ano;

    @ManyToMany(mappedBy = "artista")
    @ToString.Exclude
    private ArrayList<ArtistaEntity> artists;


}
