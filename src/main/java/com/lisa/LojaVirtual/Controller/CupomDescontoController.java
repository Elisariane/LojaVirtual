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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lisa.LojaVirtual.Models.CupomDesconto;
import com.lisa.LojaVirtual.Models.Funcionario;
import com.lisa.LojaVirtual.Models.Papel;
import com.lisa.LojaVirtual.Models.Permissao;
import com.lisa.LojaVirtual.Repository.CupomDescontoRepositoy;

@Controller
public class CupomDescontoController {
	@Autowired
	private CupomDescontoRepositoy repository;
	@GetMapping("/administrativo/cupons")
	public ModelAndView buscarTodos() {
		ModelAndView mv = new ModelAndView("/administrativo/lista/cupomDesconto");
		mv.addObject("descontos", repository.findAll());
		
		return mv;
	}

	
	
	@GetMapping("/administrativo/addCupomDesconto")
	public ModelAndView add(CupomDesconto desconto) {
		ModelAndView mv = new ModelAndView("/administrativo/cadastro/cupomDesconto");
		mv.addObject("desconto", desconto);
		
		return mv;
	}
	
	
	@GetMapping("/administrativo/editarCupomDesconto/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Optional<CupomDesconto> desconto = repository.findById(id);
		CupomDesconto d = desconto.get();
		
		return add(d);
		
	}

	
	@GetMapping("/administrativo/removerCupomDesconto/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<CupomDesconto> desconto = repository.findById(id);
		CupomDesconto d = desconto.get();
		
		repository.delete(d);
		
		
		return buscarTodos();
	}
	
	@PostMapping("/administrativo/salvarCupomDesconto")
	public ModelAndView save(@Valid CupomDesconto desconto, BindingResult result) {
		if(result.hasErrors()) {
			return add(desconto);
		}
		
		repository.saveAndFlush(desconto);
		
		return buscarTodos();
	}
	
	
}
