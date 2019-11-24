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

import com.lisa.LojaVirtual.Models.Funcionario;
import com.lisa.LojaVirtual.Models.Papel;
import com.lisa.LojaVirtual.Models.Permissao;
import com.lisa.LojaVirtual.Repository.FuncionarioRepository;
import com.lisa.LojaVirtual.Repository.PapelRepository;
import com.lisa.LojaVirtual.Repository.PermissaoRepository;

@Controller
public class PermissaoController {

	
	
	@Autowired
	private PermissaoRepository repository;
	
	@Autowired
	private FuncionarioRepository repositoryF;
	
	@Autowired
	private PapelRepository repositoryP;
	
	@GetMapping("/administrativo/permissoes")
	public ModelAndView buscarTodos() {
		ModelAndView mv = new ModelAndView("/administrativo/lista/permissao");
		mv.addObject("permissoes", repository.findAll());
		
		return mv;
	}

	
	
	@GetMapping("/administrativo/addPermissao")
	public ModelAndView add(Permissao permissao) {
		ModelAndView mv = new ModelAndView("/administrativo/cadastro/permissao");
		mv.addObject("permissao", permissao);
		List<Funcionario> listaFuncionarios = repositoryF.findAll();
		mv.addObject("listaFuncionarios", repositoryF.findAll());
		
		List<Papel> listaPapeis = repositoryP.findAll();
		mv.addObject("listaPapeis", repositoryP.findAll());
		
		return mv;
	}
	
	
	@GetMapping("/administrativo/editarPermissao/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Optional<Permissao> permissao = repository.findById(id);
		Permissao p = permissao.get();
		
		return add(p);
		
	}

	
	@GetMapping("/administrativo/removerPermissao/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Permissao> permissao = repository.findById(id);
		Permissao p = permissao.get();
		
		repository.delete(p);
		
		
		return buscarTodos();
	}
	
	@PostMapping("/administrativo/salvarPermissao")
	public ModelAndView save(@Valid Permissao permissao, BindingResult result) {
		if(result.hasErrors()) {
			return add(permissao);
		}
		
		repository.saveAndFlush(permissao);
		
		return buscarTodos();
	}
	
	
	
	
	
	
	
	
}
