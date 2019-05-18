package org.zerock.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.PageMaker;
import org.zerock.domain.SearchCriteria;
import org.zerock.service.BoardService;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {
	
	private static Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	@Inject
	private BoardService service;
	@Inject
	private PageMaker pageMaker;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") SearchCriteria cri, Model model)throws Exception{
		
		logger.info(cri.toString());
		model.addAttribute("list", service.listSearchCriteria(cri));
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listSearchCount(cri));
		
		model.addAttribute("pageMaker", pageMaker);
	}
	
	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public void read (@RequestParam("bno") int bno, @ModelAttribute("cri") SearchCriteria cri
			, Model model)throws Exception {
		model.addAttribute(service.read(bno));
	}
	@RequestMapping(value = "/removePage", method = RequestMethod.POST)
	public String remove (@RequestParam("bno") int bno, @ModelAttribute("cri") SearchCriteria cri
			, RedirectAttributes rttr)throws Exception {
		service.remove(bno);
		pageMaker.setCri(cri);
		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/sboard/list"+pageMaker.makeSearch(cri.getPage())+"";
	}
	@RequestMapping(value = "/modifyPage", method = RequestMethod.GET)
	public void modifyPagingGET(int bno,
			@ModelAttribute("cri") SearchCriteria cri,Model model)throws Exception{
		model.addAttribute(service.read(bno));
	}
	
	@RequestMapping(value = "/modifyPage", method = RequestMethod.POST)
	public String modifyPagingPOST(BoardVO board,
			@ModelAttribute("cri") SearchCriteria cri,RedirectAttributes rttr)throws Exception{
		logger.info(cri.toString());
		service.modify(board);
		
		return "redirect:/sboard/list"+pageMaker.makeSearch(cri.getPage())+"";
	}
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registGET()throws Exception{
		logger.info("get");
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(BoardVO board,RedirectAttributes attr)throws Exception{
		service.regist(board);
		attr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/sboard/list";
	}
}
