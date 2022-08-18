package com.houspace.service;

import com.houspace.dto.MediumPageRequestDTO;
import com.houspace.dto.PageResultDTO;
import com.houspace.dto.ReportDTO;
import com.houspace.entity.QReportEntity;
import com.houspace.entity.ReportEntity;
import com.houspace.repository.ReportRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

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

@Service
@Log4j2
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;

    @Override
    public Long register(ReportDTO dto) {

        log.info("DTO------------------------");
        log.info(dto);

        ReportEntity entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<ReportDTO, ReportEntity> getList(MediumPageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO); //검색 조건 처리

        Page<ReportEntity> result = repository.findAll(booleanBuilder, pageable); //Querydsl 사용

        Function<ReportEntity, ReportDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn );
    }


    @Override
    public ReportDTO read(Long gno) {

        Optional<ReportEntity> result = repository.findById(gno);

        return result.isPresent()? entityToDto(result.get()): null;
    }

    @Override
    public void remove(Long gno) {

        repository.deleteById(gno);

    }

    @Override
    public void modify(ReportDTO dto) {

        //업데이트 하는 항목은 '제목', '내용', '카테고리'

        Optional<ReportEntity> result = repository.findById(dto.getGno());

        if(result.isPresent()){

            ReportEntity entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            entity.changeCategory(dto.getCategory());

            repository.save(entity);

        }
    }


    private BooleanBuilder getSearch(MediumPageRequestDTO requestDTO){

        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QReportEntity qReportEntity = QReportEntity.reportEntity;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qReportEntity.gno.gt(0L); // gno > 0 조건만 생성

        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0){ //검색 조건이 없는 경우
            return booleanBuilder;
        }


        //검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){
            conditionBuilder.or(qReportEntity.title.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qReportEntity.content.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qReportEntity.writer.contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }
}
