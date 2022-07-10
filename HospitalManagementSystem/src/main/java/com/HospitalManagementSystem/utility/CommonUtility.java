package com.HospitalManagementSystem.utility;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.HospitalManagementSystem.auth.service.PrincipalUser;
import com.HospitalManagementSystem.dto.BedDto;
import com.HospitalManagementSystem.dto.PatientDto;
import com.HospitalManagementSystem.entity.User;
import com.HospitalManagementSystem.entity.master.Bed;
import com.HospitalManagementSystem.repository.BedRepository;
import com.HospitalManagementSystem.repository.UserRepository;

@Component
public class CommonUtility {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BedRepository bedRepository;
	
	public static String localDateTimeFormat= "MM/dd/yyyy hh:mm:ss a";
	public static DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern(localDateTimeFormat);
	
	public User getCurrentUser() {
		if (ObjectUtils.isNotEmpty(httpSession.getAttribute("currentUser"))) {
			return (User) httpSession.getAttribute("currentUser");
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
			Optional<User> user = userRepository.findById(principalUser.getUserId());
			if (user.isPresent()) {
				httpSession.setAttribute("currentUser", user.get());
				return user.get();
			}
		}
		httpSession.invalidate();
		return null;
	}

	public void removeCurrentUser() {
		httpSession.removeAttribute("currentUser");
	}
	
	public PatientDto getPatientDto(Map<String, String> params) {
		try {
			BedDto bedDto = null;
			if (ObjectUtils.isNotEmpty(params.get("BedCode"))) {
				Bed bed = bedRepository.findByBedCode(java.net.URLDecoder.decode(params.get("BedCode"), StandardCharsets.UTF_8.name()));
				bedDto = new BedDto();
				bedDto.setBedId(bed.getBedId());
			}
			PatientDto patientDto = new PatientDto();
			patientDto.setUmrNumber(ObjectUtils.isNotEmpty(params.get("UMRNumber")) ? java.net.URLDecoder.decode(params.get("UMRNumber"), StandardCharsets.UTF_8.name()) : null);
			patientDto.setIpNumber(ObjectUtils.isNotEmpty(params.get("IPNumber")) ? java.net.URLDecoder.decode(params.get("IPNumber"), StandardCharsets.UTF_8.name()) : null);
			patientDto.setRoom(ObjectUtils.isNotEmpty(params.get("room")) ? java.net.URLDecoder.decode(params.get("room"), StandardCharsets.UTF_8.name()) : null);
			patientDto.setBed(bedDto);
			patientDto.setBedStatus(ObjectUtils.isNotEmpty(params.get("BedStatus")) ? java.net.URLDecoder.decode(params.get("BedStatus"), StandardCharsets.UTF_8.name()) : null);
			patientDto.setPatientName(ObjectUtils.isNotEmpty(params.get("PatientName")) ? java.net.URLDecoder.decode(params.get("PatientName"), StandardCharsets.UTF_8.name()) : null);
			patientDto.setAge(ObjectUtils.isNotEmpty(params.get("Age")) ? java.net.URLDecoder.decode(params.get("Age"), StandardCharsets.UTF_8.name()) : null);
			patientDto.setGender(ObjectUtils.isNotEmpty(params.get("Gender")) ? java.net.URLDecoder.decode(params.get("Gender"), StandardCharsets.UTF_8.name()) : null);
			patientDto.setAllergicto(ObjectUtils.isNotEmpty(params.get("Allergicto")) ? java.net.URLDecoder.decode(params.get("Allergicto"), StandardCharsets.UTF_8.name()) : null);
			patientDto.setDoctor(ObjectUtils.isNotEmpty(params.get("Doctor")) ? java.net.URLDecoder.decode(params.get("Doctor"), StandardCharsets.UTF_8.name()) : null);
			patientDto.setAdmissionType(ObjectUtils.isNotEmpty(params.get("AdmissionType")) ? java.net.URLDecoder.decode(params.get("AdmissionType"), StandardCharsets.UTF_8.name()) : null);
			patientDto.setProcedureStr(ObjectUtils.isNotEmpty(params.get("Procedure")) ? java.net.URLDecoder.decode(params.get("Procedure"), StandardCharsets.UTF_8.name()) : null);
			patientDto.setPaymentType(ObjectUtils.isNotEmpty(params.get("Paymenttype")) ? java.net.URLDecoder.decode(params.get("Paymenttype"), StandardCharsets.UTF_8.name()) : null);
			patientDto.setAdmittedDate(ObjectUtils.isNotEmpty(params.get("AdmittedDate")) ? getLocalDateTime(java.net.URLDecoder.decode(params.get("AdmittedDate"), StandardCharsets.UTF_8.name())) : null);
			patientDto.setDischargedTime(ObjectUtils.isNotEmpty(params.get("dischargedTime")) ? getLocalDateTime(java.net.URLDecoder.decode(params.get("dischargedTime"), StandardCharsets.UTF_8.name())) : null);
			patientDto.setBillStatus(ObjectUtils.isNotEmpty(params.get("BillStatus")) ? java.net.URLDecoder.decode(params.get("BillStatus"), StandardCharsets.UTF_8.name()) : null);
			return patientDto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public LocalDateTime getLocalDateTime(String value) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(localDateTimeFormat);
		return convertToLocalDateTime(formatter.parse(value));
	}
	
	public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
		return LocalDateTime.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
	}
}
