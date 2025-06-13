package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CompraDTO;
import com.example.demo.entities.Cajero;
import com.example.demo.entities.Cliente;
import com.example.demo.entities.Compra;
import com.example.demo.entities.Tienda;
import com.example.demo.entities.Vendedor;
import com.example.demo.repositories.CompraRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private TiendaService tiendaService;

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private CajeroService cajeroService;

    public List<Compra> getAllCompras() {
        return compraRepository.findAll();
    }

    public Compra getCompraById(int id) {
        return compraRepository.getReferenceById(id);
    }
    
    public void addCompra(Compra c) {
    	compraRepository.save(c);
    }

    public void addCompra(CompraDTO dto) {
        Compra compra = new Compra();

        Cliente cliente = clienteService.getClienteById(dto.getClienteId());
        Tienda tienda = tiendaService.getTiendaById(dto.getTiendaId());
        Vendedor vendedor = vendedorService.getVendedorById(dto.getVendedorId());
        Cajero cajero = cajeroService.getCajeroById(dto.getCajeroId());

        compra.setCliente(cliente);
        compra.setTienda(tienda);
        compra.setVendedor(vendedor);
        compra.setCajero(cajero);
        compra.setTotal(dto.getTotal());
        compra.setImpuestos(dto.getImpuestos());
        compra.setFecha(dto.getFecha());
        compra.setObservaciones(dto.getObservaciones());

        compraRepository.save(compra);
    }
}
