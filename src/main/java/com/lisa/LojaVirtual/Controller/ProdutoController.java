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

	private static String caminhoImagens = "C:\\Users\\lisa\\Pictures\\ImagensLoja";

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

	@GetMapping("/administrativo/mostrarImagem/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoImagens + imagem);
		if (imagem != null || imagem.trim().length() > 0) {
			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
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
	public ModelAndView save(@Valid Produto produto, BindingResult result,
			@RequestParam("file") MultipartFile arquivo) {
		if (result.hasErrors()) {
			return add(produto);
		}

		repository.saveAndFlush(produto);

		try {
			if (!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				Path caminho = Paths
						.get(caminhoImagens + String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
				Files.write(caminho, bytes);

				produto.setNomeImagem(String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
				repository.saveAndFlush(produto);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return add(new Produto());
	}
}
