package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDTO {

	private Double impuesto;
	private ClienteDTO cliente;
	private List<DetallesCompraFacturaDTO> productos;
	private List<MedioPagoFacturaDTO> medios_pago;
	private VendedorFacturaDTO vendedor;
	private CajeroFacturaDTO cajero;
}
