package com.HospitalManagementSystem.utility;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.HospitalManagementSystem.entity.Patient;
import com.HospitalManagementSystem.entity.master.ServiceMaster;
import com.HospitalManagementSystem.enums.YesNo;

@Component
public class DietUtility {

	public Specification<ServiceMaster> getServiceMasterSpecification(Patient patient, List<Long> dailyServiceMaster) {
		Specification<ServiceMaster> specification = (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (ObjectUtils.isNotEmpty(patient.getDietTypeOralSolid())) {
				// 1 Full Diet
				// 2 Soft Diet
				// 3 Semi Solid Diet
				if (patient.getDietTypeOralSolid().getDietTypeOralSolidId() == 1) {
					predicates.add(criteriaBuilder.equal(root.get("fullDiet"), YesNo.YES));
				} else if (patient.getDietTypeOralSolid().getDietTypeOralSolidId() == 2) {
					predicates.add(criteriaBuilder.equal(root.get("softDiet"), YesNo.YES));
				} else if (patient.getDietTypeOralSolid().getDietTypeOralSolidId() == 3) {
					predicates.add(criteriaBuilder.equal(root.get("semiSolidDiet"), YesNo.YES));
				}
			}

			if (patient.getExtraLiquid()) {
				predicates.add(criteriaBuilder.equal(root.get("extraLiquid"), YesNo.YES));
			}

			if (ObjectUtils.isNotEmpty(patient.getDietSubType())) {
				// 1 CLEAR LIQUIDS
				// 2 ALL LIQUIDS ORALLY
				// 3 BARIATRICS
				// 4 RT FEEDING
				// 5 PEG FEEDING
				// 6 NG FEEDING
				// 7 JJ FEEDING
				// 8 CLEAR LIQUIDS THROUGH TUBE FEEDING
				if (patient.getDietSubType().getDietSubTypeId() == 1) {
					predicates.add(criteriaBuilder.equal(root.get("clearLiquids"), YesNo.YES));
				} else if (patient.getDietSubType().getDietSubTypeId() == 2) {
					predicates.add(criteriaBuilder.equal(root.get("allLiquidsOrally"), YesNo.YES));
				} else if (patient.getDietSubType().getDietSubTypeId() == 3) {
					predicates.add(criteriaBuilder.equal(root.get("bariatrics"), YesNo.YES));
				} else if (patient.getDietSubType().getDietSubTypeId() == 4) {
					predicates.add(criteriaBuilder.equal(root.get("rtFeeding"), YesNo.YES));
				} else if (patient.getDietSubType().getDietSubTypeId() == 5) {
					predicates.add(criteriaBuilder.equal(root.get("pegFeeding"), YesNo.YES));
				} else if (patient.getDietSubType().getDietSubTypeId() == 6) {
					predicates.add(criteriaBuilder.equal(root.get("ngFeeding"), YesNo.YES));
				} else if (patient.getDietSubType().getDietSubTypeId() == 7) {
					predicates.add(criteriaBuilder.equal(root.get("jjFeeding"), YesNo.YES));
				} else if (patient.getDietSubType().getDietSubTypeId() == 8) {
					predicates.add(criteriaBuilder.equal(root.get("clearLiquidsThroughTubeFeeding"), YesNo.YES));
				}
			}
			List<Predicate> finalPredicates = new ArrayList<>();
			finalPredicates.add(criteriaBuilder.equal(root.get("isActive"), Boolean.TRUE));
//			if (CollectionUtils.isNotEmpty(dailyServiceMaster)) {
//				finalPredicates.add(root.get("serviceMasterId").in(dailyServiceMaster).not());
//			}
			finalPredicates.add(criteriaBuilder.or(predicates.toArray(new Predicate[0])));
			return criteriaBuilder.and(finalPredicates.toArray(new Predicate[0]));
		};
		return specification;
	}

}
