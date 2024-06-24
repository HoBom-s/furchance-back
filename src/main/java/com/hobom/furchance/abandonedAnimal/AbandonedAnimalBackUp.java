package com.hobom.furchance.abandonedAnimal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ABANDONED_ANIMAL_BAK")
@Getter
@Setter
public class AbandonedAnimalBackUp {

    @Id
    @Column(name = "abandoned_animal_id")
    private Long id;

    @Column(name = "desertion_no")
    private String desertionNo;

    @Column(name = "profile_img")
    private String filename;

    @Column(name = "happen_date")
    private String happenDt;

    @Column(name = "happen_place")
    private String happenPlace;

    @Column(name = "kind")
    private String kindCd;

    @Column(name = "color")
    private String colorCd;

    @Column(name = "age")
    private String age;

    @Column(name = "weight")
    private String weight;

    @Column(name = "notice_no")
    private String noticeNo;

    @Column(name = "notice_start_date")
    private String noticeSdt;

    @Column(name = "notice_edit_date")
    private String noticeEdt;

    @Column(name = "big_profile_img")
    private String popfile;

    @Column(name = "status")
    private String processState;

    @Column(name = "sex")
    private String sexCd;

    @Column(name = "neuter_yn")
    private String neuterYn;

    @Column(name = "special_mark")
    private String specialMark;

    @Column(name = "shelter_name")
    private String careNm;

    @Column(name = "shelter_tel")
    private String careTel;

    @Column(name = "shelter_address")
    private String careAddr;

    @Column(name = "org_name")
    private String orgNm;

    @Column(name = "charge_name")
    private String chargeNm;

    @Column(name = "office_tel")
    private String officetel;

}
