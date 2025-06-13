package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TiendaDTO;
import com.example.demo.entities.Tienda;
import com.example.demo.repositories.TiendaRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    public List<Tienda> getAllTiendas() {
        return tiendaRepository.findAll();
    }
    
    public Tienda getTiendaByUUID(String uuid) {
    	List<Tienda> all = getAllTiendas();
    	for(Tienda t : all) {
    		if(t.getUuid().equals(uuid))
    			return t;
    	}
    	return null;
    }

    public Tienda getTiendaById(int id) {
        return tiendaRepository.getReferenceById(id);
    }

    public void addTienda(TiendaDTO dto) {
        Tienda tienda = new Tienda();
        tienda.setNombre(dto.getNombre());
        tienda.setDireccion(dto.getDireccion());
        tienda.setUuid(dto.getUuid());
        tiendaRepository.save(tienda);
    }
}
