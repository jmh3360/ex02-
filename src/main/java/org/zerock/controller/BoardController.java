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
import org.zerock.domain.Criteria;
import org.zerock.domain.PageMaker;
import org.zerock.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private BoardService service;
	
	@RequestMapping(value = "/register",method = RequestMethod.GET)
	public void registerGET(BoardVO board, Model model)throws Exception{
		
		logger.info("register get ............");
	}
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public String registPOST(BoardVO board,RedirectAttributes rttr)throws Exception{
		
		logger.info("regist post ............");
		logger.info(board.toString());
		service.regist(board);
		
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/board/listPage";
	}

	/*
	 * @RequestMapping(value = "/listAll",method = RequestMethod.GET) public void
	 * listAll(Model model)throws Exception{
	 * logger.info("show all list ............"); System.out.println(model);
	 * model.addAttribute("list", service.listAll());
	 * 
	 * }
	 */
	@RequestMapping(value = "/readPage",method = RequestMethod.GET)
	public void read(@RequestParam("bno") int bno,@ModelAttribute("cri") Criteria cri, Model model)throws Exception{
		logger.info("show read ............");
		System.out.println("게시물 bno : "+bno+"/ cri 정보 : "+cri);
		model.addAttribute(service.read(bno));
	}
	
	@RequestMapping(value = "/removePage",method = RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno,@ModelAttribute("cri") Criteria cri,RedirectAttributes rttr)throws Exception{
		logger.info("remove ............ ="+bno);
		service.remove(bno);
		rttr.addFlashAttribute("msg", "SUCCESS");
		rttr.addFlashAttribute("page", cri.getPage());
		rttr.addFlashAttribute("perPageNum", cri.getPerPageNum());
		return "redirect:/board/listPage";
	}
	@RequestMapping(value = "/modifyPage",method = RequestMethod.GET)
	public void modifyGET(int bno,@ModelAttribute("cri") Criteria cri, Model model)throws Exception{
		model.addAttribute(service.read(bno));
		
	}
	
	@RequestMapping(value = "/modifyPage", method =RequestMethod.POST)
	public String ModifyPOST(BoardVO board,@ModelAttribute("cri") Criteria cri, RedirectAttributes rttr)throws Exception {
		
		logger.info("mod post ...........");
		System.out.println(board);
		service.modify(board);
		rttr.addFlashAttribute("page", cri.getPage());
		rttr.addFlashAttribute("perPageNum", cri.getPerPageNum());
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/board/listPage";
	}
	@RequestMapping(value = "/listCri", method = RequestMethod.GET)
	public void listAll(Criteria cri, Model model)throws Exception{
		logger.info("show list Page with Criteria...................");
		model.addAttribute("list", service.listCriteria(cri));
	}
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public void listPage(Criteria cri, Model model)throws Exception{
		logger.info(cri.toString());
		
		model.addAttribute("list", service.listCriteria(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listCountCriteria(cri));
		/* pageMaker.setTotalCount(38); */
		
		model.addAttribute("pageMaker", pageMaker);
	}
	
	
	
}
