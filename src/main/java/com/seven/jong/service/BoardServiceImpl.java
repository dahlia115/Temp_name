package com.seven.jong.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.seven.jong.DTO.BoardDTO;
import com.seven.jong.DTO.BoardReplyDTO;
import com.seven.jong.repository.IBoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	IBoardMapper mapper;
	
	@Override
	public void writeSave(BoardDTO dto, HttpServletRequest request, MultipartHttpServletRequest mul) {
		dto.setTitle(request.getParameter("title"));
		dto.setWriter(request.getParameter("writer"));
		dto.setContent(request.getParameter("content"));
		
		mapper.writeSave(dto);
	}

	@Override
	public void boardAllList(Model model,int num) {
		int allCount = mapper.BoardCount();
		int pageLetter = 10;
		int repeat = allCount / pageLetter;
		
		if(allCount % pageLetter != 0) {
			repeat += 1;
		}
		
		int end = num * pageLetter;
		int start = end + 1 - pageLetter;
		model.addAttribute("repeat", repeat);		
		model.addAttribute("boardList",mapper.boardAllList(start,end));
		
	}

	@Override
	public void contentView(int writeNo, Model model) {
		
		model.addAttribute("contentData",mapper.contentView(writeNo));
		model.addAttribute("replyList",mapper.getReplyList(writeNo));
		upHit(writeNo);
	}
	private void upHit(int writeNo) {
		mapper.upHit(writeNo);
	}

	@Override
	public void modify(BoardDTO dto, HttpServletRequest request) {
		
		dto.setWriteNo(Integer.parseInt(request.getParameter("writeNo")));
		
		dto.setTitle(request.getParameter("title"));
		dto.setContent(request.getParameter("content"));
		
		mapper.modify(dto);
	}

	@Override
	public void delete(int writeNo) {
		mapper.delete(writeNo);
		
	}

	@Override
	public void boardSearch(int num, String choice, String search, Model model) {
		
		String c = null;
		if(choice.equals("1")) {
			c = "title";
		}else {
			c = "writer";
		}
		
		System.out.println(num);
		System.out.println(c);
		System.out.println(search);
				
		int allCount = mapper.selectBoardCount(search,c);
		int pageLetter = 10;
		int repeat = allCount / pageLetter;
		
		if(allCount % pageLetter != 0) {
			repeat += 1;
		}
		int end = num * pageLetter;
		int start = end + 1 - pageLetter;
	
		model.addAttribute("repeat", repeat);	
		model.addAttribute("searchList", mapper.boardSearchList(start,end,c,search));
		
	}

	@Override
	public List<BoardReplyDTO> getReplyList(int write_group) {
		
		return mapper.getReplyList(write_group);
	}

	@Override
	public void addReply(String content, int writeNo,String writer) {
		mapper.addReply(content, writeNo, writer);
		
	}

	@Override
	public void replyDelete(int replyNum) {
		mapper.replyDelete(replyNum);
		
	}


	





	

	

}
