package com.example.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Projeto;
import com.example.demo.repository.ProjetoRepository;


@RestController
@RequestMapping("/Projetos")
public class ProjetoController {
	@Autowired
	ProjetoRepository repoProjeto;
	
	@GetMapping()
	public ResponseEntity<List<Projeto>> getProjetos() {
		return ResponseEntity.status(HttpStatus.OK).body(repoProjeto.findAll());
	}
		
	@PostMapping()
	public ResponseEntity<Projeto> insereProjeto(@RequestBody Projeto projeto) {
		Projeto prj = repoProjeto.save(projeto);
		return ResponseEntity.status(HttpStatus.CREATED).body(prj);
	}
	
	@PutMapping("/{idprojeto}")
	public ResponseEntity<Projeto> alteraProjeto(@PathVariable("idprojeto") Long idprojeto,
	        @RequestBody Projeto projeto) {
	    Optional<Projeto> opProjeto = repoProjeto.findById(idprojeto);
	    try {
	        Projeto prj = opProjeto.orElseThrow();  
	        prj.setNome(projeto.getNome());
	        prj.setEquipe(projeto.getEquipe());
	        prj.setTarefa(projeto.getTarefa());
	        prj.setDetalhes(projeto.getDetalhes());
	        repoProjeto.save(prj);
	        return ResponseEntity.status(HttpStatus.OK).body(prj);
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<Projeto> getProjeto(@PathVariable("id") long id) {
	    Optional<Projeto> opProjeto = repoProjeto.findById(id);
	    try {
	        Projeto prj = opProjeto.orElseThrow();  
	        return ResponseEntity.status(HttpStatus.OK).body(prj);
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Projeto> excluiProjeto(@PathVariable("id") long id) {
		Optional<Projeto> opProjeto = repoProjeto.findById(id);
		try {
			Projeto prj = opProjeto.get();	
			repoProjeto.delete(prj);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		catch(Exception e) {
		   return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
}