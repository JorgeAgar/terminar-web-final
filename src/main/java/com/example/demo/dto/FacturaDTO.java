package com.example.demo.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FacturaDTO {

	private Double impuesto;
	private ClienteDTO cliente;
	private List<DetallesCompraFacturaDTO> productos;
	private List<PagoDTO> medios_pago;
	private VendedorFacturaDTO vendedor;
	private CajeroDTO cajero;
}
