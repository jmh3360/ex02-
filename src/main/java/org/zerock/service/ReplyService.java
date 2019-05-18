package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
import org.zerock.domain.SearchCriteria;

public interface ReplyService {
	
	public List<ReplyVO> listReply(Integer bno)throws Exception;
	
	public void addReply(ReplyVO vo)throws Exception;
	
	public void modifyReply(ReplyVO vo)throws Exception;
	
	public void removeReply(Integer rno)throws Exception;
	
	public List<ReplyVO>  listReplyPage(Integer  bno, Criteria cri)throws Exception;
	
	public int count (Integer bno)throws Exception;

}
