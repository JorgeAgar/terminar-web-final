package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TipoPagoDTO;
import com.example.demo.entities.TipoPago;
import com.example.demo.repositories.TipoPagoRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class TipoPagoService {

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    public List<TipoPago> getAllTipoPagos() {
        return tipoPagoRepository.findAll();
    }
    
    public TipoPago getTipoPagoByName(String name) {
    	List<TipoPago> all = getAllTipoPagos();
    	for(TipoPago tp : all) {
    		if(tp.getNombre().equalsIgnoreCase(name))
    			return tp;
    	}
    	return null;
    }

    public TipoPago getTipoPagoById(int id) {
        return tipoPagoRepository.getReferenceById(id);
    }

    public void addTipoPago(TipoPagoDTO dto) {
        TipoPago tipo = new TipoPago();
        tipo.setNombre(dto.getNombre());
        tipoPagoRepository.save(tipo);
    }
}
