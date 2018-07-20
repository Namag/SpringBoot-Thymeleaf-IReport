package br.com.alpi.estoque.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import br.com.alpi.estoque.modelo.Produto;
import br.com.alpi.estoque.repository.Produtos;


@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	@Autowired
	private ApplicationContext appContext;
	@Autowired
	private Produtos produtos;
	
	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
	produtos.delete(id);
	
	attributes.addFlashAttribute("mensagem", "produto removido com sucesso");
	 return "redirect:/produtos";
	}
	@GetMapping
	public ModelAndView listar() {
		
		ModelAndView modelAndView = new ModelAndView("user/lista-produtos");
		
		modelAndView.addObject("produtos", produtos.findAll());
		
		modelAndView.addObject(new Produto());
		return modelAndView;
	}
	@GetMapping("/novo")
	public ModelAndView novo(Produto produto) {
		ModelAndView modelAndView = new ModelAndView("user/atualiza-produtos");
		
		modelAndView.addObject(produto);
		//modelAndView.addObject("sabores", SaborLicor.values());
		
		return modelAndView;
	}

	
	@PostMapping
	public String salvar(Produto produto) {
	this.produtos.save(produto);
	return "redirect:/produtos";
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Produto produto, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(produto);
		}
		produtos.save(produto);
		attributes.addFlashAttribute("mensagem","Produtos atualizado com sucesso!");
		
		return new ModelAndView("redirect:/produtos");
	}
	
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		return novo(produtos.findOne(id));
	}
	
	
@RequestMapping(path="/pdf", method = RequestMethod.GET)
public ModelAndView report() {
		JasperReportsPdfView view = new JasperReportsPdfView();
		//public ModelAndView novo(Produto produto) {
		//	ModelAndView modelAndView = new ModelAndView("cadastro-produtos");
	   view.setUrl("classpath:report1.jrxml");
		view.setApplicationContext(appContext);
		
		Map<String, Object> params  = new HashMap<>();
		params.put("datasource", produtos.findAll());
		return new ModelAndView(view , params);
	 
	}
}

