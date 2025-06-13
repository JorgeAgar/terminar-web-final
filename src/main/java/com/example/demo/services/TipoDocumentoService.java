package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TipoDocumentoDTO;
import com.example.demo.entities.TipoDocumento;
import com.example.demo.repositories.TipoDocumentoRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class TipoDocumentoService {

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    public List<TipoDocumento> getAllTipoDocumentos() {
        return tipoDocumentoRepository.findAll();
    }
    
    public TipoDocumento getTipoDocumentoByName(String name) {
    	List<TipoDocumento> all = getAllTipoDocumentos();
    	for(TipoDocumento td : all) {
    		if(td.getNombre().equalsIgnoreCase(name))
    			return td;
    	}
    	return null;
    }

    public TipoDocumento getTipoDocumentoById(int id) {
        return tipoDocumentoRepository.getReferenceById(id);
    }

    public void addTipoDocumento(TipoDocumentoDTO dto) {
        TipoDocumento tipo = new TipoDocumento();
        tipo.setNombre(dto.getNombre());
        tipoDocumentoRepository.save(tipo);
    }
}
