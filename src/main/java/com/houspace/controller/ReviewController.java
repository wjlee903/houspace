package com.houspace.controller;

/*
작성자 : 김미혜
내용 : 리뷰 업로드 Controller
 */

import com.houspace.dto.ReviewDTO;
import com.houspace.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{bnum}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("bnum") Long bnum){
        log.info("@@@@@@@@@@@@@@@List@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info("BNUM : "+bnum);

        List<ReviewDTO> reviewDTOList = reviewService.getListOfBoardEntity(bnum);

        log.info("reviewDTOList : "+ reviewDTOList);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{bnum}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO boardReviewDTO){
        log.info("@@@@@@@@@@@@@@@add review@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info("reviewDTO : "+boardReviewDTO);

        Long reviewNum = reviewService.register(boardReviewDTO);
        return new ResponseEntity<>(reviewNum, HttpStatus.OK);
    }
//
//    @PutMapping("/{bNum}/{reviewNum}")
//    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewNum, @RequestBody ReviewDTO boardReviewDTO){
//        log.info("@@@@@@@@@@@@@@@modify review@@@@@@@@@@@@@@@@@@@@@@@@@");
//        log.info("reviewDTO : "+boardReviewDTO);
//
//        reviewService.modify(boardReviewDTO);
//        return new ResponseEntity<>(reviewNum, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{bNum}/{reviewNum}")
//    public ResponseEntity<Long> removeReview(@PathVariable Long reviewNum){
//        log.info("@@@@@@@@@@@@@@@modify review@@@@@@@@@@@@@@@@@@@@@@@@@");
//        log.info("reviewNum: "+reviewNum);
//
//        reviewService.remove(reviewNum);
//        return new ResponseEntity<>(reviewNum,HttpStatus.OK);
//    }
}

