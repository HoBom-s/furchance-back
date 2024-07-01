package com.hobom.furchance.abandonedAnimal.service;

import com.hobom.furchance.abandonedAnimal.dto.AbandonedAnimalResponseDto;
import com.hobom.furchance.abandonedAnimal.dto.PaginationRequestParamDto;
import com.hobom.furchance.abandonedAnimal.entity.AbandonedAnimal;
import com.hobom.furchance.abandonedAnimal.repository.AbandonedAnimalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AbandonedAnimalServiceImpl implements AbandonedAnimalService {

    private final AbandonedAnimalRepository abandonedAnimalRepository;

    @Override
    public AbandonedAnimalResponseDto getOneAbandonedAnimal(Long id) {

        AbandonedAnimal foundAbandonedAnimal = abandonedAnimalRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return AbandonedAnimalResponseDto.from(foundAbandonedAnimal);

    }

    @Override
    public Page<AbandonedAnimalResponseDto> getAbandonedAnimalPagination(PaginationRequestParamDto paginationRequestParamDto) {

        Pageable pageable = PageRequest.of(paginationRequestParamDto.getPageNum(), paginationRequestParamDto.getPerPage());

        return abandonedAnimalRepository.findAll(pageable).map(AbandonedAnimalResponseDto::from);
    }
}