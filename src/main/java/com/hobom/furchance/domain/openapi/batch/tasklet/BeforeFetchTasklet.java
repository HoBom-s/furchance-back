package com.hobom.furchance.domain.openapi.batch.tasklet;

import com.hobom.furchance.domain.abandonedAnimal.entity.AbandonedAnimal;
import com.hobom.furchance.domain.abandonedAnimal.entity.AbandonedAnimalBackUp;
import com.hobom.furchance.domain.abandonedAnimal.repository.AbandonedAnimalBackUpRepository;
import com.hobom.furchance.domain.abandonedAnimal.repository.AbandonedAnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
public class BeforeFetchTasklet implements Tasklet {

    private final AbandonedAnimalRepository abandonedAnimalRepository;

    private final AbandonedAnimalBackUpRepository abandonedAnimalBackUpRepository;

    @Override
    @Transactional
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

        abandonedAnimalBackUpRepository.deleteAllInBatch();

        List<AbandonedAnimal> ExistingAbandonedAnimals = abandonedAnimalRepository.findAll();

        ArrayList<AbandonedAnimalBackUp> abandonedAnimalBackUps = new ArrayList<>();

        for (AbandonedAnimal existingAbandonedAnimal : ExistingAbandonedAnimals) {
            AbandonedAnimalBackUp backUpAnimal = new AbandonedAnimalBackUp();

            backUpAnimal.setDesertionNo(existingAbandonedAnimal.getDesertionNo());
            backUpAnimal.setFilename(existingAbandonedAnimal.getFilename());
            backUpAnimal.setHappenDt(existingAbandonedAnimal.getHappenDt());
            backUpAnimal.setHappenPlace(existingAbandonedAnimal.getHappenPlace());
            backUpAnimal.setKindCd(existingAbandonedAnimal.getKindCd());
            backUpAnimal.setColorCd(existingAbandonedAnimal.getColorCd());
            backUpAnimal.setAge(existingAbandonedAnimal.getAge());
            backUpAnimal.setWeight(existingAbandonedAnimal.getWeight());
            backUpAnimal.setNoticeNo(existingAbandonedAnimal.getNoticeNo());
            backUpAnimal.setNoticeSdt(existingAbandonedAnimal.getNoticeSdt());
            backUpAnimal.setNoticeEdt(existingAbandonedAnimal.getNoticeEdt());
            backUpAnimal.setPopfile(existingAbandonedAnimal.getPopfile());
            backUpAnimal.setProcessState(existingAbandonedAnimal.getProcessState());
            backUpAnimal.setSexCd(existingAbandonedAnimal.getSexCd());
            backUpAnimal.setNeuterYn(existingAbandonedAnimal.getNeuterYn());
            backUpAnimal.setSpecialMark(existingAbandonedAnimal.getSpecialMark());
            backUpAnimal.setCareNm(existingAbandonedAnimal.getCareNm());
            backUpAnimal.setCareTel(existingAbandonedAnimal.getCareTel());
            backUpAnimal.setCareAddr(existingAbandonedAnimal.getCareAddr());
            backUpAnimal.setOrgNm(existingAbandonedAnimal.getOrgNm());
            backUpAnimal.setChargeNm(existingAbandonedAnimal.getChargeNm());
            backUpAnimal.setOfficetel(existingAbandonedAnimal.getOfficetel());

            abandonedAnimalBackUps.add(backUpAnimal);
        }

        abandonedAnimalBackUpRepository.saveAll(abandonedAnimalBackUps);

        abandonedAnimalRepository.deleteAllInBatch();

        return RepeatStatus.FINISHED;
    }
}
