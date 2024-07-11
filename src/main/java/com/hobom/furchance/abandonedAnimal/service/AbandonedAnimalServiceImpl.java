package com.hobom.furchance.abandonedAnimal.service;

import com.hobom.furchance.abandonedAnimal.dto.AbandonedAnimalResponseDto;
import com.hobom.furchance.abandonedAnimal.dto.AbandonedAnimalPaginationRequestParamDto;
import com.hobom.furchance.abandonedAnimal.entity.AbandonedAnimal;
import com.hobom.furchance.abandonedAnimal.repository.AbandonedAnimalRepository;
import com.hobom.furchance.abandonedAnimal.repository.spec.AbandonedAnimalSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AbandonedAnimalServiceImpl implements AbandonedAnimalService {

    private final AbandonedAnimalRepository abandonedAnimalRepository;

    private final AbandonedAnimalSpecification abandonedAnimalSpecification;

    @Override
    public AbandonedAnimalResponseDto getOneAbandonedAnimal(Long id) {

        AbandonedAnimal foundAbandonedAnimal = abandonedAnimalRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return AbandonedAnimalResponseDto.from(foundAbandonedAnimal);

    }

    @Override
    public Page<AbandonedAnimalResponseDto> getAbandonedAnimalPagination(AbandonedAnimalPaginationRequestParamDto abandonedAnimalPaginationRequestParamDto) {

        int pageNum = abandonedAnimalPaginationRequestParamDto.getPageNum() - 1;
        int perPage = abandonedAnimalPaginationRequestParamDto.getPerPage();
        String sortField = abandonedAnimalPaginationRequestParamDto.getSortField();
        Sort.Direction sortDirection = Sort.Direction.fromString(String.valueOf(abandonedAnimalPaginationRequestParamDto.getSort()));

        Pageable pageable = PageRequest.of(pageNum, perPage, sortDirection, sortField);

        return abandonedAnimalRepository.findAll(abandonedAnimalSpecification.withFilters(abandonedAnimalPaginationRequestParamDto),pageable).map(AbandonedAnimalResponseDto::from);
    }
}