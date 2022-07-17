package com.HospitalManagementSystem.entity.master;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.HospitalManagementSystem.enums.YesNo;

import lombok.Data;

@Entity
@Table(name = "service_master")
@Data
public class ServiceMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serviceMasterId;
	
	private String service;
	
	private LocalTime fromTime;
	
	private LocalTime toTime;
	
    @Column(columnDefinition = "ENUM('YES', 'NO')")
    @Enumerated(EnumType.STRING)
	private YesNo fullDiet;
	
    @Column(columnDefinition = "ENUM('YES', 'NO')")
    @Enumerated(EnumType.STRING)
	private YesNo softDiet;
	
    @Column(columnDefinition = "ENUM('YES', 'NO')")
    @Enumerated(EnumType.STRING)    
	private YesNo semiSolidDiet;
	
    @Column(columnDefinition = "ENUM('YES', 'NO')")
    @Enumerated(EnumType.STRING)    
	private YesNo extraLiquid;
	
    @Column(columnDefinition = "ENUM('YES', 'NO')")
    @Enumerated(EnumType.STRING)    
	private YesNo clearLiquids;
	
    @Column(columnDefinition = "ENUM('YES', 'NO')")
    @Enumerated(EnumType.STRING)    
	private YesNo allLiquidsOrally;
	
    @Column(columnDefinition = "ENUM('YES', 'NO')")
    @Enumerated(EnumType.STRING)    
	private YesNo bariatrics;
	
    @Column(columnDefinition = "ENUM('YES', 'NO')")
    @Enumerated(EnumType.STRING)
	private YesNo rtFeeding;
	
    @Column(columnDefinition = "ENUM('YES', 'NO')")
    @Enumerated(EnumType.STRING)
	private YesNo pegFeeding;
	
    @Column(columnDefinition = "ENUM('YES', 'NO')")
    @Enumerated(EnumType.STRING)
	private YesNo ngFeeding;
	
    @Column(columnDefinition = "ENUM('YES', 'NO')")
    @Enumerated(EnumType.STRING)
	private YesNo jjFeeding;
	
    @Column(columnDefinition = "ENUM('YES', 'NO')")
    @Enumerated(EnumType.STRING)
	private YesNo clearLiquidsThroughTubeFeeding;
    
    private Boolean isActive = Boolean.FALSE;
	
}
