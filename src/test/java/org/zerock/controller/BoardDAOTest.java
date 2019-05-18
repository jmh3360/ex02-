package org.zerock.controller;


import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.SearchCriteria;
import org.zerock.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BoardDAOTest {
	
	@Inject
	private BoardDAO dao;
	
	private static Logger logger = LoggerFactory.getLogger(BoardDAOTest.class);
	
	/*
	 * @Test public void testCreate() throws Exception{ BoardVO vo = new BoardVO();
	 * vo.setTitle("새로운 글을 넣습니다. "); vo.setContent("새로운 글을 넣습니다. ");
	 * vo.setWriter("user00"); dao.create(vo); }
	 * 
	 * @Test public void testRead()throws Exception{
	 * logger.info(dao.read(2).toString()); }
	 * 
	 * @Test public void testUpdate()throws Exception{ BoardVO vo = new BoardVO();
	 * vo.setBno(2); vo.setTitle("수정된 글입니다."); vo.setContent("수정 테스트");
	 * dao.update(vo); }
	 * 
	 * @Test public void testDelete()throws Exception{ dao.delete(1); }
	 */
//	@Test
//	public void testListPage()throws Exception {
//		int page = 3;
//		List<BoardVO> list = dao.listPage(page);
//		
//		for (BoardVO boardVO : list) {
//			logger.info(boardVO.getBno() + ":" + boardVO.getTitle());
//		}
//	}
	/*
	 * @Test public void testListCriteria()throws Exception { Criteria cri = new
	 * Criteria(); cri.setPage(2); cri.setPerPageNum(20);
	 * System.out.println("테스트 코드"+cri); List<BoardVO> list = dao.listCriteria(cri);
	 * for (BoardVO boardVO : list) {
	 * 
	 * logger.info(list.size()+"///"+ boardVO.getBno() + ":" + boardVO.getTitle());
	 * } }
	 */
	@Test
	public void testURI()throws Exception{
		
		UriComponents uriComponents =
				UriComponentsBuilder.newInstance()
				.path("/board/read")
				.queryParam("bno", 12)
				.queryParam("perPageNum", 20)
				.build();
		
		logger.info("/board/read?bno=12&perPageNum=20");
		logger.info(uriComponents.toString());
		
	}
	@Test
	public void testDynamic1()throws Exception{
		SearchCriteria cri = new SearchCriteria();
		cri.setPage(1);
		cri.setKeyword("글");
		cri.setSearchType("t");
		
		logger.info("=============================================");
		
		List<BoardVO> list = dao.listSearch(cri);
		
		for (BoardVO boardVO : list) {
			logger.info(boardVO.getBno() + ":" + boardVO.getTitle());
		}
		
		logger.info("=============================================");
		logger.info("COUNT: "+dao.listSearchCount(cri));
	}
	
	
	
}
