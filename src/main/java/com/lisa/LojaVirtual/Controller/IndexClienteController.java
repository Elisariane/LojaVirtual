package com.lisa.LojaVirtual.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lisa.LojaVirtual.Repository.ProdutoRepository;

@Controller
public class IndexClienteController {

	@Autowired
	private ProdutoRepository prodRepo;
	
	@GetMapping("/")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/index");
		mv.addObject("listaProduto",prodRepo.findAll());
		return mv;
	}
	
}
