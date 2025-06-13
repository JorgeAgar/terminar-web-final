package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedioPagoFacturaDTO {
	
	private String tipo_pago;
	private String tipo_tarjeta;
	private int cuotas;
	private double valor;
}
