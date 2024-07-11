package com.hobom.furchance.abandonedAnimal.service;


import com.hobom.furchance.abandonedAnimal.dto.AbandonedAnimalResponseDto;
import com.hobom.furchance.abandonedAnimal.dto.AbandonedAnimalPaginationRequestParamDto;
import org.springframework.data.domain.Page;

public interface AbandonedAnimalService {

    AbandonedAnimalResponseDto getOneAbandonedAnimalById(Long id);

    Page<AbandonedAnimalResponseDto> getAbandonedAnimalPagination(AbandonedAnimalPaginationRequestParamDto abandonedAnimalPaginationRequestParamDto);

}
