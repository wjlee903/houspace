package com.houspace.controller;

import com.houspace.dto.MediumPageRequestDTO;
import com.houspace.dto.ReportDTO;
import com.houspace.entity.ReportEntity;
import com.houspace.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
	프로그램명 : 고객불만 및 개선사항 접수 게시판
	작성자 : 박유림
	최초작성일 : 2022-6-24
	마지막수정일 : 2022-6-29

	사용법 : 고객센터 게시판 버튼 클릭
	        -> 게시글 등록 (1.비밀글여부/2.건의사항구분/3.제목/4.내용/5.작성자)
	        -> 비밀글로 작성하면 타인 열람 불가 페이지로 이동됨
	        -> 비밀글 체크 해제하면 열람 가능
	        -> 수정은 구분/제목/내용만
	 => 수정/삭제는 작성자, 관리자만 가능하게 하는 것 구현 필요
	 => 비밀글 열람은 작성자, 관리자만 가능하게 하는 것 구현 필요
*/

@Controller
@RequestMapping("/houspace")
@Log4j2
@RequiredArgsConstructor //자동 주입을 위한 Annotation
public class ReportController {

    private final ReportService reportService; //final로 선언

    @GetMapping("/report")
    public String index() {
        return "redirect:/houspace/report/list";
    }

    @GetMapping("/report/list")
    public void list(MediumPageRequestDTO requestDTO, Model model){
        log.info("list............." + requestDTO);
        model.addAttribute("result", reportService.getList(requestDTO));
    }

    @GetMapping("/report/secret")
    public String secret() {
        return "/houspace/report/secret";
    }

    @GetMapping("/report/register")
    public void register(){
        log.info("register get...");
    }

    @PostMapping("/report/register")
    public String registerPost(ReportDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto..." + dto);
        //새로 추가된 엔티티의 번호
        Long gno = reportService.register(dto);
        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/houspace/report/list";
    }

    @GetMapping({"/report/read", "/report/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") MediumPageRequestDTO requestDTO, Model model){
        log.info("gno: " + gno);
        ReportDTO dto = reportService.read(gno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/report/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){
        log.info("gno: " + gno);
        reportService.remove(gno);
        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/houspace/report/list";
    }

    @PostMapping("/report/modify")
    public String modify(ReportDTO dto,
                         @ModelAttribute("requestDTO") MediumPageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){
        log.info("post modify.........................................");
        log.info("dto: " + dto);
        reportService.modify(dto);
        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());
        redirectAttributes.addAttribute("gno",dto.getGno());
        return "redirect:/houspace/report/read";
    }
}
