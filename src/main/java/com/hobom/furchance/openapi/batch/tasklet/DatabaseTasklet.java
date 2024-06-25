package com.hobom.furchance.openapi.batch.tasklet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hobom.furchance.abandonedAnimal.AbandonedAnimal;
import com.hobom.furchance.abandonedAnimal.AbandonedAnimalBackUp;
import com.hobom.furchance.abandonedAnimal.AbandonedAnimalBackUpRepository;
import com.hobom.furchance.abandonedAnimal.AbandonedAnimalRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
public class DatabaseTasklet implements Tasklet {

    private final AbandonedAnimalRepository abandonedAnimalRepository;

    private final AbandonedAnimalBackUpRepository abandonedAnimalBackUpRepository;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        abandonedAnimalBackUpRepository.deleteAllInBatch();
        abandonedAnimalBackUpRepository.flush();
        System.out.println("백업 테이블 삭제");

        List<AbandonedAnimal> abandonedAnimals = abandonedAnimalRepository.findAll();
        for (AbandonedAnimal abandonedAnimal : abandonedAnimals) {
            AbandonedAnimalBackUp backUpAnimal = new AbandonedAnimalBackUp();

            backUpAnimal.setDesertionNo(abandonedAnimal.getDesertionNo());
            backUpAnimal.setFilename(abandonedAnimal.getFilename());
            backUpAnimal.setHappenDt(abandonedAnimal.getHappenDt());
            backUpAnimal.setHappenPlace(abandonedAnimal.getHappenPlace());
            backUpAnimal.setKindCd(abandonedAnimal.getKindCd());
            backUpAnimal.setColorCd(abandonedAnimal.getColorCd());
            backUpAnimal.setAge(abandonedAnimal.getAge());
            backUpAnimal.setWeight(abandonedAnimal.getWeight());
            backUpAnimal.setNoticeNo(abandonedAnimal.getNoticeNo());
            backUpAnimal.setNoticeSdt(abandonedAnimal.getNoticeSdt());
            backUpAnimal.setNoticeEdt(abandonedAnimal.getNoticeEdt());
            backUpAnimal.setPopfile(abandonedAnimal.getPopfile());
            backUpAnimal.setProcessState(abandonedAnimal.getProcessState());
            backUpAnimal.setSexCd(abandonedAnimal.getSexCd());
            backUpAnimal.setNeuterYn(abandonedAnimal.getNeuterYn());
            backUpAnimal.setSpecialMark(abandonedAnimal.getSpecialMark());
            backUpAnimal.setCareNm(abandonedAnimal.getCareNm());
            backUpAnimal.setCareTel(abandonedAnimal.getCareTel());
            backUpAnimal.setCareAddr(abandonedAnimal.getCareAddr());
            backUpAnimal.setOrgNm(abandonedAnimal.getOrgNm());
            backUpAnimal.setChargeNm(abandonedAnimal.getChargeNm());
            backUpAnimal.setOfficetel(abandonedAnimal.getOfficetel());

            abandonedAnimalBackUpRepository.save(backUpAnimal);
        }
        System.out.println("백업 완료");

        abandonedAnimalRepository.deleteAllInBatch();
        abandonedAnimalRepository.flush();
        System.out.println("기본 테이블 삭제");

        List<AbandonedAnimal> newAnimals = new ArrayList<>();

        for (JsonNode openApiAbandonedAnimal : OpenApiTasklet.tempStorage) {
            AbandonedAnimal abandonedAnimal = objectMapper.treeToValue(openApiAbandonedAnimal, AbandonedAnimal.class);

            if (abandonedAnimal != null) {
                newAnimals.add(abandonedAnimal);
            }
        }

        if (!newAnimals.isEmpty()) {
            abandonedAnimalRepository.saveAllAndFlush(newAnimals);
        }

        return RepeatStatus.FINISHED;
    }
}
