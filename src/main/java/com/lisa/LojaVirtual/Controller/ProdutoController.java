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


import com.lisa.LojaVirtual.Models.Produto;
import com.lisa.LojaVirtual.Repository.ProdutoRepository;

@Controller
public class ProdutoController {
	
	

	@Autowired
	private ProdutoRepository repository;
	
	@GetMapping("/administrativo/produtos")
	public ModelAndView buscarTodos() {
		ModelAndView mv = new ModelAndView("/administrativo/lista/produto");
		mv.addObject("produtos", repository.findAll());
		
		return mv;
	}

	
	
	@GetMapping("/administrativo/addProduto")
	public ModelAndView add(Produto produto) {
		ModelAndView mv = new ModelAndView("/administrativo/cadastro/produto");
		mv.addObject("produto", produto);

		return mv;
	}
	
	
	@GetMapping("/administrativo/editarProduto/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Optional<Produto> produto = repository.findById(id);
		Produto p = produto.get();
		
		return add(p);
		
	}

	
	@GetMapping("/administrativo/removerProduto/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Produto> produto = repository.findById(id);
		Produto p = produto.get();
		repository.delete(p);
		
		
		return buscarTodos();
	}
	
	@PostMapping("/administrativo/salvarProduto")
	public ModelAndView save(@Valid Produto produto, BindingResult result) {
		if(result.hasErrors()) {
			return add(produto);
		}
		
		repository.saveAndFlush(produto);
		
		return buscarTodos();
	}
	
	
	
	
	

}
