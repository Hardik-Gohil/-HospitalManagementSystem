package com.HospitalManagementSystem.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.HospitalManagementSystem.entity.Patient;
import com.HospitalManagementSystem.entity.master.AdHocItems;
import com.HospitalManagementSystem.entity.master.ServiceItems;
import com.HospitalManagementSystem.entity.master.ServiceMaster;
import com.HospitalManagementSystem.enums.YesNo;
import com.HospitalManagementSystem.repository.DietSubTypeRepository;
import com.HospitalManagementSystem.repository.FrequencyRepository;
import com.HospitalManagementSystem.repository.ServiceItemsRepository;

@Component
public class DietUtility {

	@Autowired
	private ServiceItemsRepository serviceItemsRepository;
	@Autowired
	private DietSubTypeRepository dietSubTypeRepository;
	@Autowired
	private FrequencyRepository frequencyRepository;
	
	public Specification<ServiceMaster> getServiceMasterSpecification(Patient patient, List<Long> dailyServiceMaster) {
		Specification<ServiceMaster> specification = (root, query, criteriaBuilder) -> {
			List<LocalTime> fromTimeList = new ArrayList<>();
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
				
				if (ObjectUtils.isEmpty(patient.getDietSubType().getFromTime())) {
					patient.setDietSubType(dietSubTypeRepository.findById(patient.getDietSubType().getDietSubTypeId()).get());
				}
				
				if (ObjectUtils.isEmpty(patient.getFrequency().getValue())) {
					patient.setFrequency(frequencyRepository.findById(patient.getFrequency().getFrequencyId()).get());
				}
				LocalDateTime fromTime = patient.getDietSubType().getFromTime().atDate(LocalDate.now());
				LocalDateTime toTime = LocalDate.now().plusDays(1).atStartOfDay().plusMinutes(1);
				while (fromTime.isBefore(toTime)) {
					fromTimeList.add(fromTime.toLocalTime());
					fromTime = fromTime.plusHours(patient.getFrequency().getValue());
				}
			}
			List<Predicate> finalPredicates = new ArrayList<>();
			finalPredicates.add(criteriaBuilder.equal(root.get("isActive"), Boolean.TRUE));
//			if (CollectionUtils.isNotEmpty(dailyServiceMaster)) {
//				finalPredicates.add(root.get("serviceMasterId").in(dailyServiceMaster).not());
//			}
			if (CollectionUtils.isNotEmpty(fromTimeList)) {
				finalPredicates.add(root.get("fromTime").in(fromTimeList));
			}		
			finalPredicates.add(criteriaBuilder.or(predicates.toArray(new Predicate[0])));
			return criteriaBuilder.and(finalPredicates.toArray(new Predicate[0]));
		};
		return specification;
	}

	public Map<String, List<ServiceItems>> getServiceItemsMap() {
		Map<String, List<ServiceItems>> serviceItemsMap = new HashMap<String, List<ServiceItems>>();
		List<ServiceItems> ServiceItemsList = serviceItemsRepository.findAllByIsActive(true);
		if (CollectionUtils.isNotEmpty(ServiceItemsList)) {
			for (ServiceItems serviceItems : ServiceItemsList) {
				if (serviceItems.getS_2pmExtraLiq() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_2pmExtraLiq", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_6pmExtraLiq() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_6pmExtraLiq", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_6am() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_6am", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_7am() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_7am", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_8am() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_8am", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_9am() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_9am", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_10am() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_10am", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_11am() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_11am", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_12pm() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_12pm", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_1pm() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_1pm", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_2pm() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_2pm", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_3pm() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_3pm", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_4pm() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_4pm", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_5pm() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_5pm", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_6pm() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_6pm", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_7pm() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_7pm", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_8pm() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_8pm", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_9pm() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_9pm", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_10pm() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_10pm", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_11pm() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_11pm", getSubKeys(serviceItems));
				}
				if (serviceItems.getS_12am() == YesNo.YES) {
					addIntoMap(serviceItems, serviceItemsMap, "s_12am", getSubKeys(serviceItems));
				}
			}
		}
		return serviceItemsMap;
	}

	private void addIntoMap(ServiceItems serviceItems, Map<String, List<ServiceItems>> serviceItemsMap, String prefix, List<String> subKeys) {
		if (CollectionUtils.isNotEmpty(subKeys)) {
			for (String subKey : subKeys) {
				String key = prefix + "_" + subKey;
				List<ServiceItems> serviceItemsList = null;
				if (ObjectUtils.isNotEmpty(serviceItemsMap.get(key))) {
					serviceItemsList = serviceItemsMap.get(key);
				} else {
					serviceItemsList = new ArrayList<>();
				}
				serviceItemsList.add(serviceItems);
				serviceItemsMap.put(key, serviceItemsList);
			}
		}
	}

	private List<String> getSubKeys(ServiceItems serviceItems) {
		List<String> subKeys = new ArrayList<>();
		if (serviceItems.getExtraLiquid() == YesNo.YES) {
			subKeys.add("extraLiquid");
		}
		if (serviceItems.getClearLiquids() == YesNo.YES) {
			subKeys.add("clearLiquids");
		}
		if (serviceItems.getAllLiquidsOrally() == YesNo.YES) {
			subKeys.add("allLiquidsOrally");
		}
		if (serviceItems.getBariatrics() == YesNo.YES) {
			subKeys.add("bariatrics");
		}
		if (serviceItems.getRtFeeding() == YesNo.YES) {
			subKeys.add("rtFeeding");
		}
		if (serviceItems.getPegFeeding() == YesNo.YES) {
			subKeys.add("pegFeeding");
		}
		if (serviceItems.getNgFeeding() == YesNo.YES) {
			subKeys.add("ngFeeding");
		}
		if (serviceItems.getJjFeeding() == YesNo.YES) {
			subKeys.add("jjFeeding");
		}
		if (serviceItems.getClearLiquidsThroughTubeFeeding() == YesNo.YES) {
			subKeys.add("clearLiquidsThroughTubeFeeding");
		}
		return subKeys;
	}

	public String getKeySuffix(Patient patient) {
		// 1 CLEAR LIQUIDS
		// 2 ALL LIQUIDS ORALLY
		// 3 BARIATRICS
		// 4 RT FEEDING
		// 5 PEG FEEDING
		// 6 NG FEEDING
		// 7 JJ FEEDING
		// 8 CLEAR LIQUIDS THROUGH TUBE FEEDING
		String keySuffix = "";
		if (patient.getExtraLiquid()) {
			keySuffix = "extraLiquid";
		} else if (ObjectUtils.isNotEmpty(patient.getDietSubType()) && patient.getDietSubType().getDietSubTypeId() == 1) {
			keySuffix = "clearLiquids";
		} else if (ObjectUtils.isNotEmpty(patient.getDietSubType()) && patient.getDietSubType().getDietSubTypeId() == 2) {
			keySuffix = "allLiquidsOrally";
		} else if (ObjectUtils.isNotEmpty(patient.getDietSubType()) && patient.getDietSubType().getDietSubTypeId() == 3) {
			keySuffix = "bariatrics";
		} else if (ObjectUtils.isNotEmpty(patient.getDietSubType()) && patient.getDietSubType().getDietSubTypeId() == 4) {
			keySuffix = "rtFeeding";
		} else if (ObjectUtils.isNotEmpty(patient.getDietSubType()) && patient.getDietSubType().getDietSubTypeId() == 5) {
			keySuffix = "pegFeeding";
		} else if (ObjectUtils.isNotEmpty(patient.getDietSubType()) && patient.getDietSubType().getDietSubTypeId() == 6) {
			keySuffix = "ngFeeding";
		} else if (ObjectUtils.isNotEmpty(patient.getDietSubType()) && patient.getDietSubType().getDietSubTypeId() == 7) {
			keySuffix = "jjFeeding";
		} else if (ObjectUtils.isNotEmpty(patient.getDietSubType()) && patient.getDietSubType().getDietSubTypeId() == 8) {
			keySuffix = "clearLiquidsThroughTubeFeeding";
		}
		return keySuffix;
	}

	public Specification<AdHocItems> getAdHocItemsSpecification(Patient patient) {
		Specification<AdHocItems> specification = (root, query, criteriaBuilder) -> {
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

			if (CollectionUtils.isNotEmpty(patient.getMedicalComorbidities())) {
				// 1 Normal
				// 2 Diabetic Diet (DD)
				// 3 Renal
				// 4 Hypertensive (Salt Restricted-SRD)
				// 5 Salt Free
				// 6 FAT Free
				if (patient.getMedicalComorbidities().stream().filter(x -> x.getMedicalComorbiditiesId() == 1).findAny().isPresent()) {
					predicates.add(criteriaBuilder.equal(root.get("normal"), YesNo.YES));
				} 
				if (patient.getMedicalComorbidities().stream().filter(x -> x.getMedicalComorbiditiesId() == 2).findAny().isPresent()) {
					predicates.add(criteriaBuilder.equal(root.get("diabeticDiet"), YesNo.YES));
				} 
				if (patient.getMedicalComorbidities().stream().filter(x -> x.getMedicalComorbiditiesId() == 3).findAny().isPresent()) {
					predicates.add(criteriaBuilder.equal(root.get("renal"), YesNo.YES));
				} 
				if (patient.getMedicalComorbidities().stream().filter(x -> x.getMedicalComorbiditiesId() == 4).findAny().isPresent()) {
					predicates.add(criteriaBuilder.equal(root.get("hypertensive"), YesNo.YES));
				} 
				if (patient.getMedicalComorbidities().stream().filter(x -> x.getMedicalComorbiditiesId() == 5).findAny().isPresent()) {
					predicates.add(criteriaBuilder.equal(root.get("saltFree"), YesNo.YES));
				} 
				if (patient.getMedicalComorbidities().stream().filter(x -> x.getMedicalComorbiditiesId() == 6).findAny().isPresent()) {
					predicates.add(criteriaBuilder.equal(root.get("fatFree"), YesNo.YES));
				} 
			}
			predicates.add(criteriaBuilder.equal(root.get("isActive"), Boolean.TRUE));
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
		return specification;
	}

}
