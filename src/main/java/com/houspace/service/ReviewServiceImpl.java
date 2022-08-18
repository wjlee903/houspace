package com.houspace.service;

import com.houspace.dto.ReviewDTO;
import com.houspace.entity.BoardEntity;
import com.houspace.entity.ReviewEntity;
import com.houspace.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Repository
public class ReviewServiceImpl implements ReviewService{

    //생성자주입
    private final ReviewRepository reviewRepository;

    //리뷰 가지고오기
    @Override
    public List<ReviewDTO> getListOfBoardEntity(Long bNum) {
        BoardEntity boardEntity = BoardEntity.builder().bNum(bNum).build();
        List<ReviewEntity> result = reviewRepository.findByBoard(boardEntity);
        return result.stream().map(boardReview -> entityToDto(boardReview)).collect(Collectors.toList());
    }

    //리뷰 등록
    @Override
    public Long register(ReviewDTO boardReviewDTO) {
        ReviewEntity boardReview = dtoToEntity(boardReviewDTO);
        reviewRepository.save(boardReview);
        return boardReview.getReviewNum();
    }

    //리뷰 수정
    @Override
    public void modify(ReviewDTO boardReviewDTO) {
        Optional<ReviewEntity> result = reviewRepository.findById(boardReviewDTO.getReviewNum());
        if(result.isPresent()){
            ReviewEntity boardReview = result.get();
            boardReview.changeGrade(boardReview.getGrade());
            boardReview.changeText(boardReview.getText());

            reviewRepository.save(boardReview);
        }
    }

    //리뷰 수정
    @Override
    public void remove(Long reviewNum) {
        reviewRepository.deleteById(reviewNum);
    }
}
