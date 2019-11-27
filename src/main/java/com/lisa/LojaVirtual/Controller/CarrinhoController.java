package com.lisa.LojaVirtual.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CarrinhoController {

	@GetMapping("/carrinho")
	public ModelAndView carrinho() {
		ModelAndView mv = new ModelAndView("/cliente/cart");
		return mv;
	}
	
	@GetMapping("/adicionarCarrinho/{id}")
	public ModelAndView addCarrinho(@PathVariable Long id) {
		System.out.println(id);
		ModelAndView mv = new ModelAndView("/cliente/cart");
		return mv;
	}
	
	
}
