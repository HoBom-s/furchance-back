package com.hobom.furchance.abandonedAnimal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
