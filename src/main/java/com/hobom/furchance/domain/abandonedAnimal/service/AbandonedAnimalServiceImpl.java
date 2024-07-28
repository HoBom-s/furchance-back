package com.hobom.furchance.domain.abandonedAnimal.service;

import com.hobom.furchance.domain.abandonedAnimal.dto.AbandonedAnimalResponseDto;
import com.hobom.furchance.domain.abandonedAnimal.dto.AbandonedAnimalPaginationRequestParamDto;
import com.hobom.furchance.domain.abandonedAnimal.entity.AbandonedAnimal;
import com.hobom.furchance.domain.abandonedAnimal.repository.AbandonedAnimalRepository;
import com.hobom.furchance.domain.abandonedAnimal.repository.spec.AbandonedAnimalSpecification;
import com.hobom.furchance.exception.CustomException;
import com.hobom.furchance.exception.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AbandonedAnimalServiceImpl implements AbandonedAnimalService {

    private final AbandonedAnimalRepository abandonedAnimalRepository;

    private final AbandonedAnimalSpecification abandonedAnimalSpecification;

    @Override
    public AbandonedAnimalResponseDto getOneAbandonedAnimalById(Long id) {

        AbandonedAnimal foundAbandonedAnimal = abandonedAnimalRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND + id));

        return AbandonedAnimalResponseDto.from(foundAbandonedAnimal);
    }

    @Override
    public Page<AbandonedAnimalResponseDto> getAbandonedAnimalPagination(AbandonedAnimalPaginationRequestParamDto abandonedAnimalPaginationRequestParamDto) {

        int pageNum = abandonedAnimalPaginationRequestParamDto.getPageNum() - 1;
        int perPage = abandonedAnimalPaginationRequestParamDto.getPerPage();
        Sort.Direction sortDirection = Sort.Direction.fromString(String.valueOf(abandonedAnimalPaginationRequestParamDto.getSort()));
        String sortField = abandonedAnimalPaginationRequestParamDto.getSortField();

        Pageable pageable = PageRequest.of(pageNum, perPage, sortDirection, sortField);

        return abandonedAnimalRepository.findAll(abandonedAnimalSpecification.withFilters(abandonedAnimalPaginationRequestParamDto), pageable).map(AbandonedAnimalResponseDto::from);
    }
}