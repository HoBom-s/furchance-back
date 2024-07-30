package com.hobom.furchance.domain.abandonedAnimal.service;

import com.hobom.furchance.domain.abandonedAnimal.dto.AbandonedAnimalPaginationRequestParamDto;
import com.hobom.furchance.domain.abandonedAnimal.dto.AbandonedAnimalResponseDto;
import com.hobom.furchance.domain.abandonedAnimal.entity.AbandonedAnimal;
import com.hobom.furchance.domain.abandonedAnimal.repository.AbandonedAnimalRepository;
import com.hobom.furchance.domain.abandonedAnimal.repository.spec.AbandonedAnimalSpecification;
import com.hobom.furchance.dto.Sorting;
import com.hobom.furchance.exception.CustomException;
import com.hobom.furchance.exception.constant.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbandonedAnimalServiceImplTest {

    @Mock
    private AbandonedAnimalRepository abandonedAnimalRepository;

    @Mock
    private AbandonedAnimalSpecification abandonedAnimalSpecification;

    @InjectMocks
    private AbandonedAnimalServiceImpl abandonedAnimalService;

    @Test
    @DisplayName("해당 ID 값의 유기동물 정보를 가져온다.")
    void 유기동물_정보_가져오기() {

        Long abandonedAnimalId = 1L;
        AbandonedAnimal abandonedAnimal = new AbandonedAnimal();
        abandonedAnimal.setId(abandonedAnimalId);

        when(abandonedAnimalRepository.findById(any(Long.class))).thenReturn(Optional.of(abandonedAnimal));

        AbandonedAnimalResponseDto abandonedAnimalResponseDto = abandonedAnimalService.getOneAbandonedAnimalById(abandonedAnimalId);

        assertThat(abandonedAnimalResponseDto.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("해당 ID 값의 유기동물이 존재하지 않을 경우 에러가 발생한다.")
    void 해당_ID_값의_유기동물이_없는_경우() {

        Long abandonedAnimalId = 1L;
        AbandonedAnimal abandonedAnimal = new AbandonedAnimal();
        abandonedAnimal.setId(abandonedAnimalId);

        when(abandonedAnimalRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        CustomException customException = assertThrows(CustomException.class, () -> {
            abandonedAnimalService.getOneAbandonedAnimalById(abandonedAnimalId);
        });

        assertThat(customException.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);;
        assertThat(customException.getMessage()).isEqualTo(ErrorMessage.NOT_FOUND + abandonedAnimalId);
    }

    @Test
    @DisplayName("유기동물 페이지네이션을 가져온다.")
    void 유기동물_페이지네이션() {

        Long abandonedAnimalId = 1L;

        AbandonedAnimal abandonedAnimal = new AbandonedAnimal();
        abandonedAnimal.setId(abandonedAnimalId);

        AbandonedAnimalPaginationRequestParamDto abandonedAnimalPaginationRequestParamDto = new AbandonedAnimalPaginationRequestParamDto();
        abandonedAnimalPaginationRequestParamDto.setPageNum(1);
        abandonedAnimalPaginationRequestParamDto.setPerPage(10);
        abandonedAnimalPaginationRequestParamDto.setSort(Sorting.ASC);

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, abandonedAnimalPaginationRequestParamDto.getSortField());

        Page<AbandonedAnimal> abandonedAnimals = new PageImpl<>(Collections.singletonList(abandonedAnimal));

        when(abandonedAnimalRepository.findAll(abandonedAnimalSpecification.withFilters(abandonedAnimalPaginationRequestParamDto), pageable)).thenReturn(abandonedAnimals);

        Page<AbandonedAnimalResponseDto> abandonedAnimalPagination = abandonedAnimalService.getAbandonedAnimalPagination(abandonedAnimalPaginationRequestParamDto);

        assertThat(abandonedAnimalPagination.getTotalElements()).isEqualTo(1);
    }
}