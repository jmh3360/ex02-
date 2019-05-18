package org.zerock.controller;


import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;
import org.zerock.domain.BoardVO;
import org.zerock.persistence.BoardDAO;
import org.zerock.service.BoardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BoardControllerTest {
	
	@Inject
	private BoardController ct;
	
	private static Logger logger = LoggerFactory.getLogger(BoardControllerTest.class);
	
	@Test
	public void testCreate() throws Exception{
		BoardVO vo = new BoardVO();
		
		vo.setTitle("새로운 글을 넣습니다. ");
		vo.setContent("새로운 글을 넣습니다. ");
		vo.setWriter("user00");
		ct.registPOST(vo, null);
	}
	

	
	
	
}
