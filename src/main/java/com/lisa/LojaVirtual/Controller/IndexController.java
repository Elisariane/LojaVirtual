package com.lisa.LojaVirtual.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/administrativo")
	public String index() {
		return"administrativo/index";
	}
}
