package com.hobom.furchance.domain.abandonedAnimal.service;


import com.hobom.furchance.domain.abandonedAnimal.dto.AbandonedAnimalResponseDto;
import com.hobom.furchance.domain.abandonedAnimal.dto.AbandonedAnimalPaginationRequestParamDto;
import org.springframework.data.domain.Page;

public interface AbandonedAnimalService {

    AbandonedAnimalResponseDto getOneAbandonedAnimalById(Long id);

    Page<AbandonedAnimalResponseDto> getAbandonedAnimalPagination(AbandonedAnimalPaginationRequestParamDto abandonedAnimalPaginationRequestParamDto);

}
