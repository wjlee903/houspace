package com.houspace.controller;

import com.houspace.dto.PageRequestDTO;
import com.houspace.dto.SamllPageRequestDTO;
import com.houspace.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
작성자 : 이용훈
내용 : html 파일에 연결
작성일 : 22-07-01
 */

@Controller
@RequestMapping("/houspace")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    @GetMapping("/faq")
    public void list(SamllPageRequestDTO samllPageRequestDTO, Model model) {

        model.addAttribute("result", faqService.getListAll(samllPageRequestDTO));

    }
}
