package com.example.safapi.model.entity;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Date dataLancamento;
    private String sinopse;
    private Double duracao;
    private Double imdb;
    @ManyToOne
    private Estudio estudio;
}
