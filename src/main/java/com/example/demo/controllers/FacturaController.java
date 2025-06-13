package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ConsultaFacturaDTO;
import com.example.demo.dto.FacturaDTO;
import com.example.demo.dto.FacturaResponseDTO;
import com.example.demo.services.FacturaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/")
public class FacturaController {
	
	@Autowired
	FacturaService facturaService;

	@PostMapping("/crear/{idTienda}")
	public ResponseEntity<String> procesarFactura(@PathVariable String idTienda, @RequestBody FacturaDTO factura) {
		System.out.println("En controller " + factura);
		return facturaService.generarFactura(idTienda, factura);
	}
	
	@GetMapping("/consultar/{idTienda}")
	public FacturaResponseDTO consultarFactura(@PathVariable String idTienda, @RequestBody ConsultaFacturaDTO datosConsulta) {
		return facturaService.consultarFactura(datosConsulta.getToken(), idTienda, datosConsulta.getCliente(), datosConsulta.getFactura());
	}
}
