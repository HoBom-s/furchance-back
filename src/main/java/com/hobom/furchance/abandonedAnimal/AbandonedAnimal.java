package com.hobom.furchance.abandonedAnimal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "animal")
@Getter
@Setter
public class AbandonedAnimal {
    @Id
    @Column(name = "animal_id")
    private Long id;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "found_date")
    private Date foundDate;

    @Column(name = "found_place")
    private String foundPlace;

    @Column(name = "breed")
    private String breed;

    @Column(name = "color")
    private String color;

    @Column(name = "age")
    private int age;

    @Column(name = "weight")
    private String weight;

    @Column(name = "notice_no")
    private String noticeNo;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    /*
    @Column(name = "popfile")
    private String popfile;
    */

    @Column(name = "status")
    private String status;

    @Column(name = "sex")
    private String sex;

    @Column(name = "is_neuter")
    private String isNeuter;

    @Column(name = "note")
    private String note;

    @Column(name = "shelter")
    private String shelter;

    @Column(name = "shelter_tel")
    private String shelterTel;

    @Column(name = "shelter_addr")
    private String shelterAddr;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "office_manager")
    private String officeManager;

    @Column(name = "office_tel")
    private String officeTel;
}
