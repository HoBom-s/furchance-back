package com.hobom.furchance.abandonedAnimal.service;


import com.hobom.furchance.abandonedAnimal.dto.AbandonedAnimalResponseDto;
import com.hobom.furchance.abandonedAnimal.dto.PaginationRequestParamDto;
import org.springframework.data.domain.Page;

public interface AbandonedAnimalService {

    AbandonedAnimalResponseDto getOneAbandonedAnimal(Long id);

    Page<AbandonedAnimalResponseDto> getAbandonedAnimalPagination(PaginationRequestParamDto paginationRequestParamDto);

}
