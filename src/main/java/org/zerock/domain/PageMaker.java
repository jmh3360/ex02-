package org.zerock.domain;

import org.springframework.stereotype.Component;

import java.net.URLEncoder;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Component
@Data
public class PageMaker {
	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	
	private int displayPageNum = 10;
	
	private Criteria cri;
	
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}

	private void calcData() {
//		엔드페이지 계산 현재 페이지가 13페이지라는 가정 하에 진행
//		Math.ceil(13/10) = 2// 2*displayPageNum(10)
//		end page 20
		endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum);
		
		startPage = (endPage - displayPageNum) +1;
		
		int lastPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum()));
		
		if (endPage > lastPage) {
			endPage = lastPage;
		}
		
		prev = startPage == 1 ? false : true;
		next = endPage *cri.getPerPageNum() >= totalCount ? false : true;
	}
//	상세보기로 진입 전에 내가 있었던 곳의 페이지 번호와 perPageNum을 만들어준다.
//	jstl 태그를 통하여 화면에서 호출하는 방식으로 사용한다.
	public String makeQuery(int page) {
		UriComponents uriComponents =
				UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getPerPageNum())
				.build();
		return uriComponents.toUriString();
	}
	
	public String makeSearch(int page) {
		
		UriComponents uriComponents =
				UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getPerPageNum())
				.queryParam("searchType", ((SearchCriteria) cri).getSearchType())
						.queryParam("keyword", encoding(((SearchCriteria) cri).getKeyword()))
				.build();
		return uriComponents.toString();
	}
//	조회 정보가 이상하게 나왔을 때 encoding을 해줘야 함 아래 함수를 이용하면 가능한 부분
//	하지만 내경우에는 딱히 문제가 되지 않았고 오히려 작성했을 때 문제가 되서 없애버림 
//	이유 : 나중에 공부하자

	  private String encoding(String keyword) { System.out.println(keyword); if
	  (keyword ==null || keyword.trim().length() ==0) { return ""; }
	  
	  try { return URLEncoder.encode(keyword, "UTF-8"); } catch (Exception e) {
	  return ""; } }
	 
}
