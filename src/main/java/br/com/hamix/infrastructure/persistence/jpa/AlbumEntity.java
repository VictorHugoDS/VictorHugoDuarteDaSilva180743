package br.com.hamix.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "album")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
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

    @ManyToMany(mappedBy = "albuns")
    @ToString.Exclude
    private List<ArtistaEntity> artistas;

}
