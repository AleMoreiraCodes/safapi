package com.example.safapi.model.entity;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends Pessoa{

    private String email;
    private String senha;
    private String telefone;
    private Boolean administrador;
}
