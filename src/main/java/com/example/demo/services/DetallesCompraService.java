package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DetallesCompraDTO;
import com.example.demo.entities.Compra;
import com.example.demo.entities.DetallesCompra;
import com.example.demo.entities.Producto;
import com.example.demo.repositories.DetallesCompraRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class DetallesCompraService {

    @Autowired
    private DetallesCompraRepository detallesCompraRepository;

    @Autowired
    private CompraService compraService;

    @Autowired
    private ProductoService productoService;

    public List<DetallesCompra> getAllDetallesCompra() {
        return detallesCompraRepository.findAll();
    }

    public DetallesCompra getDetallesCompraById(int id) {
        return detallesCompraRepository.getReferenceById(id);
    }
    
    public void addDetallesCompra(DetallesCompra d) {
    	detallesCompraRepository.save(d);
    }

    public void addDetallesCompra(DetallesCompraDTO dto) {
        DetallesCompra detalle = new DetallesCompra();

        Compra compra = compraService.getCompraById(dto.getCompraId());
        Producto producto = productoService.getProductoById(dto.getProductoId());

        detalle.setCompra(compra);
        detalle.setProducto(producto);
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecio(dto.getPrecio());
        detalle.setDescuento(dto.getDescuento());

        detallesCompraRepository.save(detalle);
    }
}
