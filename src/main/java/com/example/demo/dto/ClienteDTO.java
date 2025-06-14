package com.example.demo.dto;

import com.example.demo.entities.Cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private String nombre;
    private String documento;
    private String tipo_documento;

    public ClienteDTO(Cliente entity) {
        this.nombre = entity.getNombre();
        this.documento = entity.getDocumento();
        this.tipo_documento = entity.getTipoDocumento() != null ? entity.getTipoDocumento().getNombre() : null;
    }
}

