package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ConsultaFacturaDTO {
	private String token;
	private String cliente;
	private int factura;

}
