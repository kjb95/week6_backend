package com.springbatch.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String afCode;
    private String afCodeName;
    private String costSource;
    private String adType;
    private String campaign;
    private String subCampaign;
    private String device;
    private String channel;
    private String mediaName;
    private String productName;
    private String brand;
    private String exhibitionNumber;
    private String department;
    private String keyword;
    private String period;
    private int impressions;
}
