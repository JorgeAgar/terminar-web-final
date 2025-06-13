package com.example.demo.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FacturaResponseDTO {
	double total;
	double impuestos;
	ClienteDTO cliente;
	List<ProductoFacturaDTO> productos;
	CajeroDTO cajero;
}
