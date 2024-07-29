package com.hobom.furchance.domain.abandonedAnimal.dto;

import com.hobom.furchance.domain.abandonedAnimal.entity.AbandonedAnimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbandonedAnimalResponseDto {

    private Long id;

    private String desertionNo;

    private String filename;

    private String happenDt;

    private String happenPlace;

    private String kindCd;

    private String colorCd;

    private String age;

    private String weight;

    private String noticeNo;

    private String noticeSdt;

    private String noticeEdt;

    private String popfile;

    private String processState;

    private String sexCd;

    private String neuterYn;

    private String specialMark;

    private String careNm;

    private String careTel;

    private String careAddr;

    private String orgNm;

    private String chargeNm;

    private String officetel;

    public static AbandonedAnimalResponseDto from(AbandonedAnimal abandonedAnimal) {
        return new AbandonedAnimalResponseDto(
                abandonedAnimal.getId(),
                abandonedAnimal.getDesertionNo(),
                abandonedAnimal.getFilename(),
                abandonedAnimal.getHappenDt(),
                abandonedAnimal.getHappenPlace(),
                abandonedAnimal.getKindCd(),
                abandonedAnimal.getColorCd(),
                abandonedAnimal.getAge(),
                abandonedAnimal.getWeight(),
                abandonedAnimal.getNoticeNo(),
                abandonedAnimal.getNoticeSdt(),
                abandonedAnimal.getNoticeEdt(),
                abandonedAnimal.getPopfile(),
                abandonedAnimal.getProcessState(),
                abandonedAnimal.getSexCd(),
                abandonedAnimal.getNeuterYn(),
                abandonedAnimal.getSpecialMark(),
                abandonedAnimal.getCareNm(),
                abandonedAnimal.getCareTel(),
                abandonedAnimal.getCareAddr(),
                abandonedAnimal.getOrgNm(),
                abandonedAnimal.getChargeNm(),
                abandonedAnimal.getOfficetel()
        );
    }

}
