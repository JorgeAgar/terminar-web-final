package com.example.demo.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CajeroDTO;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.DetallesCompraFacturaDTO;
import com.example.demo.dto.FacturaDTO;
import com.example.demo.dto.FacturaResponseDTO;
import com.example.demo.dto.MedioPagoFacturaDTO;
import com.example.demo.dto.PagoDTO;
import com.example.demo.dto.ProductoFacturaDTO;
import com.example.demo.entities.Cajero;
import com.example.demo.entities.Cliente;
import com.example.demo.entities.Compra;
import com.example.demo.entities.DetallesCompra;
import com.example.demo.entities.Pago;
import com.example.demo.entities.Pago.TarjetaTipo;
import com.example.demo.entities.Producto;
import com.example.demo.entities.Tienda;
import com.example.demo.entities.TipoPago;
import com.example.demo.entities.Vendedor;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class FacturaService {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private TiendaService tiendaService;
	
	@Autowired
	private CajeroService cajeroService;
	
	@Autowired
	private VendedorService vendedorService;
	
	@Autowired
	private TipoPagoService tipoPagoService;
	
	@Autowired
	private CompraService compraService;

	public ResponseEntity<String> generarFactura(String tiendaId, FacturaDTO factura) {
		Compra compra = new Compra();
		
		Tienda tienda = tiendaService.getTiendaByUUID(tiendaId);
		Double total = 0d;
		Cliente cliente = clienteService.getClienteByDoc(factura.getCliente().getDocumento());
		if(cliente == null) {
			cliente = clienteService.addCliente(factura.getCliente());
		}
		List<DetallesCompra> detalles = new LinkedList<DetallesCompra>();
		if(factura.getProductos().isEmpty()) { //no hay productos
			String body = "{"
					+ "	'status': 'error',"
					+ "	'message': 'No hay productos asignados para esta compra',"
					+ "	'data': 'null'"
					+ "}";
			return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		}
		for(DetallesCompraFacturaDTO detallesP : factura.getProductos()) {
			Producto p = productoService.getProductoByReference(detallesP.getReferencia());
			if(p == null) { // no existe producto con esa referencia
				String body = "{"
						+ "	'status': 'error',"
						+ "	'message': 'La referencia del producto " + detallesP.getReferencia() + " no existe, por favor revisar los datos',"
						+ "	'data': 'null'"
						+ "}";
				return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
			}
			if(detallesP.getCantidad() > p.getCantidad()) { //está comprando más de lo que hay
				String body = "{"
						+ "	'status': 'error',"
						+ "	'message': 'La cantidad a comprar supera el máximo del producto en tienda',"
						+ "	'data': 'null'"
						+ "}";
				return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
			}
			DetallesCompra d = new DetallesCompra();
			d.setCantidad(detallesP.getCantidad());
			d.setDescuento(d.getDescuento());
			d.setPrecio(p.getPrecio()*detallesP.getCantidad());
			d.setProducto(p);
			d.setCompra(compra);
			detalles.add(d);
			total += d.getPrecio();
			
			
		}
		List<Pago> pagos = new LinkedList<Pago>();
		if(factura.getMedios_pago().isEmpty()) { //no hay medios de pago
			String body = "{"
					+ "	'status': 'error',"
					+ "	'message': 'No hay medios de pago asignados para esta compra',"
					+ "	'data': 'null'"
					+ "}";
			return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		}
		for(MedioPagoFacturaDTO pagoDto : factura.getMedios_pago()) {
			Pago pago = new Pago();
			pago.setCompra(compra);
			pago.setCuotas(pagoDto.getCuotas());
			pago.setValor(pagoDto.getValor());
			TarjetaTipo tipo = null;
			if(pagoDto.getTipo_tarjeta().equalsIgnoreCase("Mastercard"))
				tipo = TarjetaTipo.MASTERCARD;
			else if(pagoDto.getTipo_tarjeta().equalsIgnoreCase("Visa"))
				tipo = TarjetaTipo.VISA;
			pago.setTarjetaTipo(tipo);
			TipoPago tipoPago = tipoPagoService.getTipoPagoByName(pagoDto.getTipo_pago());
			if(tipoPago == null) { //el tipo de pago no existe
				String body = "{"
						+ "	'status': 'error',"
						+ "	'message': 'Tipo de pago no permitido en la tienda',"
						+ "	'data': 'null'"
						+ "}";
				return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
			}
			pago.setTipoPago(tipoPago);
			pagos.add(pago);
		}
		Vendedor vendedor = vendedorService.getVendedorByDoc(factura.getVendedor().getDocumento());
		if(vendedor == null) { //no existe vendedor con ese documento
			String body = "{"
					+ "	'status': 'error',"
					+ "	'message': 'El vendedor no existe en la tienda',"
					+ "	'data': 'null'"
					+ "}";
			return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		}
		Cajero cajero = cajeroService.getCajeroByToken(factura.getCajero().getToken());
		if(cajero == null) { //no existe cajero con ese token
			String body = "{"
					+ "	'status': 'error',"
					+ "	'message': 'El Token no corresponde a ningún cajero en la tienda',"
					+ "	'data': 'null'"
					+ "}";
			return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		}
		if(cajero.getTienda().getId() != tienda.getId()) { //el cajero es de otra tienda
			String body = "{"
					+ "	'status': 'error',"
					+ "	'message': 'El cajero no está asignado a esta tienda',"
					+ "	'data': 'null'"
					+ "}";
			return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
		}
		
		compra.setCajero(cajero);
		compra.setTienda(tienda);
		compra.setVendedor(vendedor);
		compra.setCliente(cliente);
		compra.setDetallesCompra(detalles);
		compra.setImpuestos(factura.getImpuesto());
		compra.setPagos(pagos);
		compra.setFecha(LocalDateTime.now());
		compra.setTotal(total);
		
		compraService.addCompra(compra);
		
		String body = "{"
				+ "	'status': 'success',"
				+ "	'message': 'La factura se ha creado correctamente con el número: " + compra.getId() + "',"
				+ "	'data': {"
				+ "		'numero': '" + compra.getId() + "',"
				+ "		'total': '" + compra.getTotal() + "',"
				+ "		'fecha': '" + compra.getFecha().format(DateTimeFormatter.ISO_DATE) + "'"
				+ "	}"
				+ "}";
		return new ResponseEntity<>(body, HttpStatus.OK);
		
	}
	
	public FacturaResponseDTO consultarFactura(String token, String tiendaUUID, String clienteDoc, int facturaId) {
		Tienda tienda = tiendaService.getTiendaByUUID(tiendaUUID);
		Cajero cajero = cajeroService.getCajeroByToken(token);
		Cliente cliente = clienteService.getClienteByDoc(clienteDoc);
		if(cajero.getTienda().getId() != tienda.getId()) { //cajero sin permiso
			throw new RuntimeException("Cajero sin permiso");
		}
		Compra compra = compraService.getCompraById(facturaId);
		FacturaResponseDTO factura = new FacturaResponseDTO();
		factura.setCajero(new CajeroDTO(cajero));
		factura.setCliente(new ClienteDTO(cliente));
		factura.setImpuestos(compra.getImpuestos());
		factura.setTotal(compra.getTotal());
		List<ProductoFacturaDTO> productos = new LinkedList<ProductoFacturaDTO>();
		for(DetallesCompra dc : compra.getDetallesCompra()) {
			ProductoFacturaDTO producto = new ProductoFacturaDTO();
			producto.setCantidad(dc.getCantidad());
			producto.setDescuento(dc.getDescuento());
			producto.setNombre(dc.getProducto().getNombre());
			producto.setPrecio(dc.getProducto().getPrecio());
			producto.setReferencia(dc.getProducto().getReferencia());
			producto.setSubtotal((producto.getPrecio()*producto.getCantidad())-producto.getDescuento());
			productos.add(producto);
		}
		factura.setProductos(productos);
		
		return factura;
	}
}
