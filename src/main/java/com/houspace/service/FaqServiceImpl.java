package com.houspace.service;

import com.houspace.dto.FaqDTO;
import com.houspace.dto.PageRequestDTO;
import com.houspace.dto.PageResultDTO;
import com.houspace.dto.SamllPageRequestDTO;
import com.houspace.entity.FaqEntity;
import com.houspace.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/*
작성자 : 이용훈
내용 : 리스트 가져오기
작성일 : 22-07-01
 */

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {


    private final FaqRepository faqRepository;

    @Override
    public PageResultDTO<FaqDTO, FaqEntity> getListAll(SamllPageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("fno").descending());

        Page<FaqEntity> result = faqRepository.findAll(pageable);

        Function<FaqEntity, FaqDTO> fn = (faqEntity -> entityToDto(faqEntity));

        return new PageResultDTO<>(result, fn);
    }
}
