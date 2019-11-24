package com.lisa.LojaVirtual.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lisa.LojaVirtual.Models.Cidade;
import com.lisa.LojaVirtual.Models.Estado;
import com.lisa.LojaVirtual.Repository.CidadeRepository;
import com.lisa.LojaVirtual.Repository.EstadoRepository;

@Controller
public class CidadeController {

	
	
	
	@Autowired
	private CidadeRepository repository;
	
	@Autowired
	private EstadoRepository repositoryEstado;
	
	@GetMapping("/administrativo/cidades")
	public ModelAndView buscarTodos() {
		ModelAndView mv = new ModelAndView("/administrativo/lista/cidade");
		mv.addObject("cidades", repository.findAll());
		
		return mv;
	}

	
	
	@GetMapping("/administrativo/addCidade")
	public ModelAndView add(Cidade cidade) {
		ModelAndView mv = new ModelAndView("/administrativo/cadastro/cidade");
		mv.addObject("cidade", cidade);

		List<Estado> listaEstado = repositoryEstado.findAll();
		mv.addObject("estados",listaEstado);
		
		return mv;
	}
	
	
	@GetMapping("/administrativo/editarCidade/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Optional<Cidade> cidade = repository.findById(id);
		Cidade c = cidade.get();
		
		return add(c);
		
	}

	
	@GetMapping("/administrativo/removerCidade/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Cidade> cidade = repository.findById(id);
		Cidade c = cidade.get();
		repository.delete(c);
		
		
		return buscarTodos();
	}
	
	@PostMapping("/administrativo/salvarCidade")
	public ModelAndView save(@Valid Cidade cidade, BindingResult result) {
		if(result.hasErrors()) {
			return add(cidade);
		}
		
		repository.saveAndFlush(cidade);
		
		return buscarTodos();
	}
	
	
	
	
	
	
	
	
	
	
}
