package com.lisa.LojaVirtual.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lisa.LojaVirtual.Models.Produto;
import com.lisa.LojaVirtual.Repository.ProdutoRepository;

@Controller
public class ProdutoController {


	@Autowired
	private ProdutoRepository repository;

	@GetMapping("/administrativo/produto/listar")
	public ModelAndView buscarTodos() {
		ModelAndView mv = new ModelAndView("/administrativo/lista/produto");
		mv.addObject("produtos", repository.findAll());

		return mv;
	}

	@GetMapping("/administrativo/produto/add")
	public ModelAndView add(Produto produto) {
		ModelAndView mv = new ModelAndView("/administrativo/cadastro/produto");
		mv.addObject("produto", produto);

		return mv;
	}

	
	@GetMapping("/administrativo/produto/editar/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Optional<Produto> produto = repository.findById(id);
		Produto p = produto.get();

		return add(p);

	}

	@GetMapping("/administrativo/produto/remover/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Produto> produto = repository.findById(id);
		Produto p = produto.get();
		repository.delete(p);

		return buscarTodos();
	}

	@PostMapping("/administrativo/produto/salvar")
	public ModelAndView save(@Valid Produto produto, BindingResult result,
			@RequestParam("file") MultipartFile arquivo) {
		if (result.hasErrors()) {
			return add(produto);
		}

		repository.saveAndFlush(produto);

		

		return add(new Produto());
	}
}
