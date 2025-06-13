package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.entities.Cliente;
import com.example.demo.entities.TipoDocumento;
import com.example.demo.repositories.ClienteRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }
    
    public Cliente getClienteByDoc(String doc) {
    	List<Cliente> todos = getAllClientes();
    	for(Cliente c : todos) {
    		if(c.getDocumento().equals(doc))
    			return c;
    	}
    	return null;
    }

    public Cliente getClienteById(int id) {
        return clienteRepository.getReferenceById(id);
    }

    public Cliente addCliente(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setDocumento(dto.getDocumento());
        TipoDocumento tipo = tipoDocumentoService.getTipoDocumentoByName(dto.getTipoDocumento());
        cliente.setTipoDocumento(tipo);
        return clienteRepository.save(cliente);
    }
}
