package com.hobom.furchance.domain.abandonedAnimal.controller;

import com.hobom.furchance.domain.abandonedAnimal.dto.AbandonedAnimalResponseDto;
import com.hobom.furchance.domain.abandonedAnimal.dto.AbandonedAnimalPaginationRequestParamDto;
import com.hobom.furchance.domain.abandonedAnimal.service.AbandonedAnimalService;
import com.hobom.furchance.dto.ApiResponse;
import com.hobom.furchance.url.Url;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Url.AbandonedAnimal.BASE_URL)
@RequiredArgsConstructor
public class AbandonedAnimalController {

    private final AbandonedAnimalService abandonedAnimalService;

    @GetMapping(Url.ID_PARAM)
    public ResponseEntity<ApiResponse<AbandonedAnimalResponseDto>> getOneAbandonedAnimal(@PathVariable("id") final Long id) {

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: get one abandoned animal by id", abandonedAnimalService.getOneAbandonedAnimalById(id)));
    }

    @GetMapping(Url.PAGINATION)
    public ResponseEntity<ApiResponse<Page<AbandonedAnimalResponseDto>>> getAbandonedAnimalPagination(
            @ModelAttribute @Valid AbandonedAnimalPaginationRequestParamDto abandonedAnimalPaginationRequestParamDto
    ) {

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: abandoned animals pagination", abandonedAnimalService.getAbandonedAnimalPagination(abandonedAnimalPaginationRequestParamDto)));
    }
}
