package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductoFacturaDTO {
	String referencia;
	String nombre;
	int cantidad;
	double precio;
	double descuento;
	double subtotal;
}
