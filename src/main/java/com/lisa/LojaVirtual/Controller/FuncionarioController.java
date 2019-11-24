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
import com.lisa.LojaVirtual.Models.Funcionario;
import com.lisa.LojaVirtual.Repository.CidadeRepository;
import com.lisa.LojaVirtual.Repository.FuncionarioRepository;

@Controller
public class FuncionarioController {

	
	
	
	@Autowired
	private CidadeRepository repositoryCidade;
	
	@Autowired
	private FuncionarioRepository repository;
	
	@GetMapping("/administrativo/funcionarios")
	public ModelAndView buscarTodos() {
		ModelAndView mv = new ModelAndView("/administrativo/lista/funcionario");
		mv.addObject("funcionarios", repository.findAll());
		
		return mv;
	}

	
	
	@GetMapping("/administrativo/addFuncionario")
	public ModelAndView add(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("/administrativo/cadastro/funcionario");
		mv.addObject("funcionario", funcionario);

		List<Cidade> listaCidade = repositoryCidade.findAll();
		mv.addObject("cidades",listaCidade);
		
		return mv;
	}
	
	
	@GetMapping("/administrativo/editarFuncionario/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Optional<Funcionario> funcionario = repository.findById(id);
		Funcionario f = funcionario.get();
		
		return add(f);
		
	}

	
	@GetMapping("/administrativo/removerFuncionario/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Funcionario> funcionario = repository.findById(id);
		Funcionario f = funcionario.get();
		repository.delete(f);
		
		
		return buscarTodos();
	}
	
	@PostMapping("/administrativo/salvarFuncionario")
	public ModelAndView save(@Valid Funcionario funcionario, BindingResult result) {
		if(result.hasErrors()) {
			return add(funcionario);
		}
		
		repository.saveAndFlush(funcionario);
		
		return buscarTodos();
	}
	
	
	
	
	
	
	
	
	
	
}
