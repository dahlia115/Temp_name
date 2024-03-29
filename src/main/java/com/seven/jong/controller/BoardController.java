package com.seven.jong.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.seven.jong.DTO.BoardDTO;

import com.seven.jong.service.BoardService;



@Controller
@RequestMapping("board")
public class BoardController {
	@Autowired 
	BoardService bs;
	
	//boardAllList.jsp연결
	@GetMapping("/boardAllList")
	public String boardAllList(Model model, @RequestParam(value="num", required = false, defaultValue = "1") int num) {
		System.out.println("boardAllList연결");
		bs.boardAllList(model,num);
		return "board/boardAllList";
	}
	//writeForm.jsp 연결
	@GetMapping("/writeForm")
	public String writeForm() {
		return "board/writeForm";
	}
	//게시물 저장
	@PostMapping("/writeSave")
	public String writeSave(BoardDTO dto, HttpServletRequest request, MultipartHttpServletRequest mtfRequest, HttpServletResponse response) throws IOException {
		bs.writeSave(dto, request, mtfRequest);
		
	   
		return "redirect:/board/boardAllList";
	}
	//선택 게시물 보기 , 리플 가져오기
	@GetMapping("contentView")
	public String contentView (@RequestParam int writeNo, Model model) {
		bs.contentView(writeNo, model);
		System.out.println("contentView연결");
		return "board/contentView";
	}
	//이미지 불러오기
	@GetMapping("download")
	public void downLoad(@RequestParam String fileName,
						HttpServletResponse response) throws Exception{
		response.addHeader("Content-disposition",
				"attachment;fileName"+fileName);
		File file = new File("C:\\upload\\"+fileName);
		FileInputStream in = new FileInputStream(file);
		
		System.out.println(file);
		
		FileCopyUtils.copy(in, response.getOutputStream());
		in.close();
	}
	
	//modifyForm 연결
	@GetMapping("modifyForm")
	public String modifyForm(@RequestParam int writeNo, Model model) {
		bs.contentView(writeNo, model);
		System.out.println("modifyForm 연결");
		return "board/modifyForm";
	}
	//게시물 수정
	@PostMapping("modify")
	public String modify(BoardDTO dto, HttpServletRequest request, MultipartHttpServletRequest mul) {
		bs.modify(dto, request, mul);
		return "redirect:/board/boardAllList";
	}
	//게시물 삭제
	@GetMapping("delete")
	public String delete(@RequestParam int writeNo) {
		bs.delete(writeNo);
		return "redirect:/board/boardAllList";
	}
	//게시물 검색
	@PostMapping("/boardSearch")
	public String userSearch(@RequestParam(value="num" , required=false, defaultValue="1") int num, @RequestParam("choice")String choice, @RequestParam("boardSearch")String search, Model model) {
		System.out.println("boardSearch연결");

		bs.boardSearch(num, choice ,search,model);
		return "board/boardSearch";
	}
	//댓글 추가
	@PostMapping("addReply")
	public String addReply(@RequestParam String content,@RequestParam int writeNo, @RequestParam String writer){//세션 추가해야함
		bs.addReply(content,writeNo,writer);	
		return "redirect:/board/contentView?writeNo="+writeNo;
	}
	//댓글 삭제
	@GetMapping("replydelete")
	public String replyDelete(@RequestParam int writeNo, @RequestParam int replyNum) {
		bs.replyDelete(replyNum);
		return "redirect:/board/contentView?writeNo="+writeNo;
	}
	//댓글 수정창 이동
	@GetMapping("modifyReplyForm")
	public String modifyReplyForm(@RequestParam int reply_num, @RequestParam int writeNo ,Model model) {
		bs.selectReply(model, reply_num, writeNo);
		return "board/modifyReplyForm";
	}
	//댓글 수정
	@PostMapping("modifyReply")
	public String modifyReply(@RequestParam int writeNo,@RequestParam String content,@RequestParam int reply_num) {
		bs.modifyReply(content,reply_num);
		System.out.println(content);
		return "redirect:/board/contentView?writeNo="+writeNo;
	}

}
















