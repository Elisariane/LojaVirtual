package com.lisa.LojaVirtual.Controller;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lisa.LojaVirtual.Models.Papel;
import com.lisa.LojaVirtual.Repository.PapelRepository;

@Controller
public class PapelController {


	@Autowired
	private PapelRepository repository;

	@GetMapping("/administrativo/papel/listar")
	public ModelAndView buscarTodos() {
		ModelAndView mv = new ModelAndView("/administrativo/lista/papel");
		mv.addObject("papeis", repository.findAll());

		return mv;
	}

	@GetMapping("/administrativo/papel/add")
	public ModelAndView add(Papel papel) {
		ModelAndView mv = new ModelAndView("/administrativo/cadastro/papel");
		mv.addObject("papel", papel);

		return mv;
	}

	@GetMapping("/administrativo/papel/editar/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Optional<Papel> papel = repository.findById(id);
		Papel e = papel.get();

		return add(e);

	}

	@GetMapping("/administrativo/papel/remover/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Papel> papel = repository.findById(id);
		Papel e = papel.get();
		repository.delete(e);

		return buscarTodos();
	}

	@PostMapping("/administrativo/papel/salvar")
	public ModelAndView save(@Valid Papel papel, BindingResult result) {
		if (result.hasErrors()) {
			return add(papel);
		}

	
		repository.saveAndFlush(papel);

		return buscarTodos();
	}

}
