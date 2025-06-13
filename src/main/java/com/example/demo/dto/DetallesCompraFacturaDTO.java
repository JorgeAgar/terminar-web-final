package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetallesCompraFacturaDTO {

	String referencia;
	Integer cantidad;
	Double descuento;
}
