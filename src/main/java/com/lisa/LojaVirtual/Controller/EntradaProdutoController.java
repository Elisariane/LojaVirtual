package com.lisa.LojaVirtual.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lisa.LojaVirtual.Models.EntradaItens;
import com.lisa.LojaVirtual.Models.EntradaProduto;
import com.lisa.LojaVirtual.Models.Produto;
import com.lisa.LojaVirtual.Repository.EntradaItensRepository;
import com.lisa.LojaVirtual.Repository.EntradaProdutoRepository;
import com.lisa.LojaVirtual.Repository.FuncionarioRepository;
import com.lisa.LojaVirtual.Repository.ProdutoRepository;

@Controller
public class EntradaProdutoController {

	private List<EntradaItens> listaEntrada = new ArrayList<EntradaItens>();

	@Autowired
	private EntradaProdutoRepository entradaProdutoRepositorio;

	@Autowired
	private EntradaItensRepository entradaItensRepositorio;

	@Autowired
	private FuncionarioRepository funcionarioRepositorio;

	@Autowired
	private ProdutoRepository produtoRepositorio;

	@GetMapping("/administrativo/addEntradaProduto")
	public ModelAndView add(EntradaProduto entrada, EntradaItens entradaItens) {
		ModelAndView mv = new ModelAndView("administrativo/cadastro/entradaProduto");
		mv.addObject("entrada", entrada);
		mv.addObject("listaEntradaItens", this.listaEntrada);
		mv.addObject("entradaItens", entradaItens);
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		return mv;
	}

//	@GetMapping("/administrativo/estados/listar")
//	public ModelAndView listar() {
//		ModelAndView mv=new ModelAndView("administrativo/estados/lista");
//		mv.addObject("listaEstados", estadoRepositorio.findAll());
//		return mv;
//	}

//	@GetMapping("/administrativo/estados/editar/{id}")
//	public ModelAndView editar(@PathVariable("id") Long id) {
//		Optional<Estado> estado = estadoRepositorio.findById(id);
//		return cadastrar(estado.get());
//	}

//	@GetMapping("/administrativo/estados/remover/{id}")
//	public ModelAndView remover(@PathVariable("id") Long id) {
//		Optional<Estado> estado = estadoRepositorio.findById(id);
//		estadoRepositorio.delete(estado.get());
//		return listar();
//	}

	@PostMapping("/administrativo/salvarEntradaProduto")
	public ModelAndView salvar(String acao, EntradaProduto entrada, EntradaItens entradaItens) {

		if (acao.equals("itens")) {
			this.listaEntrada.add(entradaItens);
		} else if (acao.equals("salvar")) {
			entradaProdutoRepositorio.saveAndFlush(entrada);
			for (EntradaItens it : listaEntrada) {
				it.setEntrada(entrada);
				entradaItensRepositorio.saveAndFlush(it);
				Optional<Produto> prod = produtoRepositorio.findById(it.getProduto().getId());
				Produto produto = prod.get();
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + it.getQuantidade());
				produto.setValorVenda(it.getValorVenda());
				produtoRepositorio.saveAndFlush(produto);
				this.listaEntrada = new ArrayList<>();
			}
			return add(new EntradaProduto(), new EntradaItens());
		}

		return add(entrada, new EntradaItens());
	}

}
