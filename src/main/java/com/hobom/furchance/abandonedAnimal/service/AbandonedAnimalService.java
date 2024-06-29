package com.hobom.furchance.abandonedAnimal.service;


import com.hobom.furchance.abandonedAnimal.dto.AbandonedAnimalResponseDto;
import com.hobom.furchance.abandonedAnimal.dto.PaginationRequestParamDto;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface AbandonedAnimalService {

    public AbandonedAnimalResponseDto getOneAbandonedAnimal(Long id);

    public AbandonedAnimalResponseDto[] getAbandonedAnimalPagination(@ModelAttribute PaginationRequestParamDto paginationRequestParamDto);

}
