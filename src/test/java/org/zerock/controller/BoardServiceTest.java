package org.zerock.controller;


import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.persistence.BoardDAO;
import org.zerock.service.BoardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BoardServiceTest {
	
	@Inject
	private BoardService service;
	
	private static Logger logger = LoggerFactory.getLogger(BoardServiceTest.class);
	
	@Test
	public void testCreate() throws Exception{
		BoardVO vo = new BoardVO();
		vo.setTitle("새로운 글을 넣습니다. ");
		vo.setContent("새로운 글을 넣습니다. ");
		vo.setWriter("user00");
		service.regist(vo);
	}
	
	@Test
	public void testRead()throws Exception{
		logger.info(service.read(2).toString());
	}
	@Test
	public void testUpdate()throws Exception{
		BoardVO vo = new BoardVO();
		vo.setBno(2);
		vo.setTitle("수정된 글입니다.");
		vo.setContent("수정 테스트");
		service.modify(vo);
	}
	@Test
	public void testDelete()throws Exception{
		/* dao.delete(1); */
	}
	
	
	
}
