package br.com.hamix.infrastructure.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "regional")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reg_id")
    private Integer id;

    @NotBlank(message = "Nome da regional não pode ser nulo")
    @Column(name = "reg_nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "reg_ativo", nullable = false)
    private Boolean ativo;
}
