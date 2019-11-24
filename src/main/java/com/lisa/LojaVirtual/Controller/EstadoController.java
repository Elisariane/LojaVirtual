package com.lisa.LojaVirtual.Controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lisa.LojaVirtual.Models.Estado;
import com.lisa.LojaVirtual.Repository.EstadoRepository;

@Controller
public class EstadoController {
	
	@Autowired
	private EstadoRepository repository;
	
	@GetMapping("/administrativo/estados")
	public ModelAndView buscarTodos() {
		ModelAndView mv = new ModelAndView("/administrativo/lista/estado");
		mv.addObject("estados", repository.findAll());
		
		return mv;
	}
	
//	@GetMapping("/administrativo/estado/busca")
//	public ModelAndView buscarNome(String nome) {
//		
//		ModelAndView mv = new ModelAndView("/administrativo/lista/estado");
//		mv.addObject("estados", repository.buscarPorNome(nome));
//		
//		return mv;
//	}
//	
	
	
	@GetMapping("/administrativo/addEstado")
	public ModelAndView add(Estado estado) {
		ModelAndView mv = new ModelAndView("/administrativo/cadastro/estado");
		mv.addObject("estado", estado);
		
		return mv;
	}
	
	
	@GetMapping("/administrativo/editarEstado/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Optional<Estado> estado = repository.findById(id);
		Estado e = estado.get();
		
		return add(e);
		
	}

	
	@GetMapping("/administrativo/removerEstado/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Estado> estado = repository.findById(id);
		Estado e = estado.get();
		repository.delete(e);
		
		
		return buscarTodos();
	}
	
	@PostMapping("/administrativo/salvarEstado")
	public ModelAndView save(@Valid Estado estado, BindingResult result) {
		if(result.hasErrors()) {
			return add(estado);
		}
		
		repository.saveAndFlush(estado);
		
		return buscarTodos();
	}
	
}
