package com.houspace.controller;

import com.houspace.dto.*;
import com.houspace.repository.MemberRepository;
import com.houspace.security.dto.AuthMemberDTO;
import com.houspace.service.BoardService;
import com.houspace.service.BookInfoService;
import com.houspace.service.CategoryService;
import com.houspace.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.PermitAll;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/houspace")
public class HouspaceController {

    private final CategoryService categoryService;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final BoardService boardService;

    private final BookInfoService bookInfoService;

    @GetMapping("/loginForm")
    public void loginForm() {
    }

    @GetMapping("/joinForm")
    public void joinForm() {
    }

    @PostMapping("/join")
    public String join(MemberDTO memberDTO, Model model) {

        /* 회원 가입 **********************************/
        String rawPw = memberDTO.getPw();
        String encPw = passwordEncoder.encode(rawPw);
        memberDTO.setPw(encPw);
        /* 변경 ************************************/
        memberService.joinMember(memberDTO);
//        String msg = memberService.joinMember(memberDTO);
//        if (msg.equals("회원")) {
//            return "redirect:/houspace/joinForm";
//        }
//        model.addAttribute("msg", msg);
        /* 변경 ************************************/
        /* 회원 가입 **********************************/
        return "redirect:/houspace/loginForm";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/houspace/main";
    }

    @GetMapping("/main")
    public void main(PageRequestDTO pageRequestDTO, Model model) {

        /* 메인 화면 게시글 불러오기 ************************************/
        model.addAttribute("result", boardService.getList(pageRequestDTO));
        /* 메인 화면 게시글 불러오기 ************************************/
    }

    /* 상세보기 권한 수정 ****************************************************************************/
    @GetMapping("/view")
    public void view(Long bNum, @AuthenticationPrincipal AuthMemberDTO authMemberDTO, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {

        /* 추가 0707 내가 예약한 방에서만 리뷰 버튼이 보이도록 **********************/
        String bookEmail = bookInfoService.getBookEmail(bNum);
        model.addAttribute("bookEmail", bookEmail);
        /* 추가 0707 내가 예약한 방에서만 리뷰 버튼이 보이도록 **********************/


        /* 로그인 없이 상세보기 페이지 들어가기 위하여 수정 ***********************************/
        if (authMemberDTO != null) {
            /* 글 번호로 필요 데이터 불러옴, 게시글, 이미지, 회원, 카테고리... ************/
            BoardDTO boardDTO = boardService.getBoard(bNum);
            model.addAttribute("dto", boardDTO);

            /* 추가 0704 예약시 버튼 비활성화 ***************************/
            boolean bookInfoDTO = bookInfoService.getBook(bNum);
            log.info("******************************************** bookinfo : " + bookInfoDTO);
            model.addAttribute("bookDTO", bookInfoDTO);
            /* 추가 0704 예약시 버튼 비활성화 ***************************/

            // 현재 로그인한 user
            model.addAttribute("gmail", authMemberDTO.getEmail());
            model.addAttribute("nickname", authMemberDTO.getName());
        } else if (authMemberDTO == null) {
            BoardDTO boardDTO = boardService.getBoard(bNum);
            model.addAttribute("dto", boardDTO);

            /* 추가 0704 예약시 버튼 비활성화 ***************************/
            boolean bookInfoDTO = bookInfoService.getBook(bNum);
            log.info("******************************************** bookinfo : " + bookInfoDTO);
            model.addAttribute("bookDTO", bookInfoDTO);
            /* 추가 0704 예약시 버튼 비활성화 ***************************/

            // 현재 로그인한 user
            model.addAttribute("gmail", "nogmail");
            model.addAttribute("nickname", "nonickname");
            /* 글 번호로 필요 데이터 불러옴, 게시글, 이미지, 회원, 카테고리... ************/
        }
        /* 로그인 없이 상세보기 페이지 들어가기 위하여 수정 ***********************************/
    }
    /* 상세보기 권한 수정 ****************************************************************************/

    /*
   작성자 : 이원종
   내용 : 예약하기 @GetMapping => @PostMapping 변경
           권한 USER 추가
   BookInfoEntity Long bookNum 자동 증가로 변경
   DB bookinfo 테이블 한번 지워야 함
    */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/booking")
    public String booking(BookInfoDTO bookInfoDTO, Model model) {
        /* 예약 메소드 ************************************************/
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ booking : " + bookInfoDTO);
        Long bookNum = bookInfoService.bookingBoard(bookInfoDTO);
        model.addAttribute("bookNumber", bookNum);
        return "redirect:/houspace/main";
        /* 예약 메소드 ************************************************/
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/write")
    public void write(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, Model model) {

        /* 수정 by wdd *********************************************/
        List<CategoryDTO> categoryDTOList = categoryService.getCgList();
        model.addAttribute("gmail", authMemberDTO.getEmail());
        model.addAttribute("nickname", authMemberDTO.getName());
        model.addAttribute("cgList", categoryDTOList);
        /* 수정 by wdd */
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/write")
    public String write(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {

        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ boardDTO : " + boardDTO);
        Long bNum = boardService.write(boardDTO);
        redirectAttributes.addFlashAttribute("msg", bNum);
        return "redirect:/houspace/main";
    }

    /* 마이페이지 추가로 인한 파라미터 및 내용 변경 ********************************************/
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mypage")
    public void mypage(PageRequestDTO pageRequestDTO, @AuthenticationPrincipal AuthMemberDTO authMemberDTO, Model model) {
        model.addAttribute("nickname", authMemberDTO.getName());
        model.addAttribute("gmail", authMemberDTO.getEmail());

        /*마이페이지 내 예약 글 가져오기 ***********************************************************/
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ mybooklist : " + memberService.getMyBookList(authMemberDTO.getEmail(), pageRequestDTO));
        model.addAttribute("result", memberService.getMyBookList(authMemberDTO.getEmail(), pageRequestDTO));
        /*마이페이지 내 예약 글 가져오기 ***********************************************************/

        // 마이페이지 > 내 게시글 목록 (오채영 0701)
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ mybooklist : " + memberService.getMyBoardList(authMemberDTO.getEmail(), pageRequestDTO));
        model.addAttribute("myResult", memberService.getMyBoardList(authMemberDTO.getEmail(), pageRequestDTO));


    }
    /* 마이페이지 추가로 인한 파라미터 및 내용 변경 ********************************************/


    @GetMapping("/modify")
    public void modify() {
    }

    @GetMapping("/delete")
    public String delete() {

        return "redirect:/houspace/main";
    }

    /* 2022.07.01. 추가 - 이원종 : 예약 취소 *************************************/
    @GetMapping("/bookdelete")
    public String bookdelete(Long bookNum) {
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ delete bookNum : " + bookNum);
        bookInfoService.bookDelete(bookNum);
        return "redirect:/houspace/mypage";
    }
    /* 2022.07.01. 추가 - 이원종 : 예약 취소 *************************************/

    /* 220705 추가 카테고리별 검색 ************************************************/
    @GetMapping("/search")
    public void search(Long cgNum, PageRequestDTO pageRequestDTO, Model model) {
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ search pagerequestDTO : " + pageRequestDTO);
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ search pagerequestDTO.cgNum : " + pageRequestDTO.getCgNum());


        /* 메인 화면 게시글 불러오기 ************************************/
        model.addAttribute("result", boardService.getCategoryList(pageRequestDTO, pageRequestDTO.getCgNum()));
        model.addAttribute("cgNum", pageRequestDTO.getCgNum());
        /* 메인 화면 게시글 불러오기 ************************************/

    }
    /* 220705 추가 카테고리별 검색 ************************************************/


}
