package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallesCompraFacturaDTO {

	String referencia;
	Integer cantidad;
	Double descuento;
}
