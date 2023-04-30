package com.HospitalManagementSystem.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.HospitalManagementSystem.dto.AdHocOrderDto;
import com.HospitalManagementSystem.dto.AdHocSearchDto;
import com.HospitalManagementSystem.dto.PatientDataTablesOutputDto;
import com.HospitalManagementSystem.dto.PatientSearchDto;
import com.HospitalManagementSystem.dto.PatientServiceReportDto;
import com.HospitalManagementSystem.entity.AdHocOrder;
import com.HospitalManagementSystem.entity.AdHocOrderItems;
import com.HospitalManagementSystem.entity.Patient;
import com.HospitalManagementSystem.repository.AdHocOrderRepository;
import com.HospitalManagementSystem.service.AdHocOrderService;
import com.HospitalManagementSystem.service.ExportService;
import com.HospitalManagementSystem.service.PatientDetailsService;
import com.HospitalManagementSystem.service.ReportService;
import com.HospitalManagementSystem.utility.CommonUtility;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

@Service
public class ExportServiceImpl implements ExportService {

	@Autowired
	private AdHocOrderRepository adHocOrderRepository;
	
	@Autowired
	private PatientDetailsService patientDetailsService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private AdHocOrderService adHocOrderService;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CommonUtility commonUtility;
	
	@Override
	public ResponseEntity<ByteArrayResource> getByteArrayResource(String reportName, String type, List<JasperPrint> jasperPrints, Map<String, Object> parameters) throws JRException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if (type.equals("PDF")) {
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
			exporter.exportReport();
			ByteArrayResource resource = new ByteArrayResource(out.toByteArray());
			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + reportName + ".pdf").contentType(MediaType.APPLICATION_PDF).body(resource);
		}

		if (type.equals("EXCEL")) {
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
			SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
			xlsReportConfiguration.setOnePagePerSheet(false);
			xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);

			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
			exporter.setConfiguration(xlsReportConfiguration);
			exporter.exportReport();
			ByteArrayResource resource = new ByteArrayResource(out.toByteArray());
			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + reportName + ".xls").contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
		}
		return null;
	}

	@Override
	public ResponseEntity<ByteArrayResource> getPdfPatientData(PatientSearchDto patientSearchDto, Integer patientStatus) {
		return exportPatientData("PDF", patientSearchDto, patientStatus);
	}

	@Override
	public ResponseEntity<ByteArrayResource> getExcelPatientData(PatientSearchDto patientSearchDto, Integer patientStatus) {
		return exportPatientData("EXCEL", patientSearchDto, patientStatus);
	}
	
	private ResponseEntity<ByteArrayResource> exportPatientData(String type, PatientSearchDto patientSearchDto, Integer patientStatus) {
		DataTablesInput input = patientSearchDto;
		input.addColumn("patientName", true, true, null);
		input.addColumn("umrNumber", true, true, null);
		input.addColumn("ipNumber", true, true, null);
		input.addColumn("doctor", true, true, null);
//		input.addColumn("bed.bedCode", true, true, null);
//		input.addColumn("bed.wardName", true, true, null);
//		input.addColumn("bed.floor.floorName", true, true, null);
//		input.addColumn("modifiedOn", false, true, null);
//		input.addOrder(orderColumn, direction);
		input.setLength(Integer.MAX_VALUE);

		PatientDataTablesOutputDto dataTablesOutput = patientDetailsService.getPatientData(patientSearchDto, patientStatus, Boolean.TRUE);
		List<Patient> data = dataTablesOutput.getData().getData();
		if (CollectionUtils.isNotEmpty(data)) {
			try {
				String reportName = patientStatus == 0 ? "New Patient Details" : patientStatus == 1 ? "Active Patient Details" : "Discharged Patient Details";
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("reportName", reportName);
				parameters.put("patientStatus", patientStatus);
				parameters.put("localDateTimeFormatter", CommonUtility.localDateTimeFormatter);
				parameters.put("printedOn", LocalDateTime.now().format(CommonUtility.localDateTimeFormatter));
				parameters.put("tableData", new JRBeanCollectionDataSource(data));
				
				InputStream jasperInput = ExportServiceImpl.class.getResourceAsStream("/" + "jasper/"+ (patientStatus == 0 ? "NewPatientDetails" : patientStatus == 1 ? "PatientDetails" : "PatientDetails") + ".jrxml");
				JasperDesign jasperDesign = JRXmlLoader.load(jasperInput);
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
				jasperPrints.add(jasperPrint);
				
				return getByteArrayResource(reportName, type, jasperPrints, parameters);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public ResponseEntity<ByteArrayResource> getPdfAdhocOrderData(AdHocSearchDto adHocSearchDto) {
		return exportAdhocOrderData("PDF", adHocSearchDto);
	}

	@Override
	public ResponseEntity<ByteArrayResource> getExcelAdhocOrderData(AdHocSearchDto adHocSearchDto) {
		return exportAdhocOrderData("EXCEL", adHocSearchDto);
	}
	
	@Override
	public ResponseEntity<ByteArrayResource> getExcelMISAdhocOrderData(AdHocSearchDto adHocSearchDto) {
		adHocSearchDto.setLength(Integer.MAX_VALUE);

		DataTablesOutput<AdHocOrder> dataTablesOutput = adHocOrderService.getAdhocOrderListing(adHocSearchDto);
		List<AdHocOrder> data = dataTablesOutput.getData();
		if (CollectionUtils.isNotEmpty(data)) {
			try {
				List<AdHocOrder> misData = new ArrayList<AdHocOrder>();
				
				for (AdHocOrder adHocOrder : data) {
					if (CollectionUtils.isNotEmpty(adHocOrder.getAdHocOrderItems())) {
						for (AdHocOrderItems adHocOrderItems : adHocOrder.getAdHocOrderItems()) {
							AdHocOrder adHocOrderCopy = modelMapper.map(adHocOrder, AdHocOrder.class);
							adHocOrderCopy.setAdHocOrderItem(adHocOrderItems);
							misData.add(adHocOrderCopy);
						}
					} else {
						misData.add(adHocOrder);
					}
				}
				
				String reportName = "AdHoc Oders";
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("reportName", reportName);
				parameters.put("localDateTimeFormatter", CommonUtility.localDateTimeFormatter);
				parameters.put("localDateFormatter", CommonUtility.localDateFormatter);
				parameters.put("localTime24hFormatter", CommonUtility.localTime24hFormatter);
				parameters.put("printedOn", LocalDateTime.now().format(CommonUtility.localDateTimeFormatter));
				parameters.put("tableData", new JRBeanCollectionDataSource(misData));
				
				InputStream jasperInput = ExportServiceImpl.class.getResourceAsStream("/" + "jasper/MISAdHocOders.jrxml");
				JasperDesign jasperDesign = JRXmlLoader.load(jasperInput);
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
				jasperPrints.add(jasperPrint);
				
				return getByteArrayResource(reportName, "EXCEL", jasperPrints, parameters);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private ResponseEntity<ByteArrayResource> exportAdhocOrderData(String type, AdHocSearchDto adHocSearchDto) {
		adHocSearchDto.setLength(Integer.MAX_VALUE);

		DataTablesOutput<AdHocOrder> dataTablesOutput = adHocOrderService.getAdhocOrderListing(adHocSearchDto);
		List<AdHocOrder> data = dataTablesOutput.getData();
		if (CollectionUtils.isNotEmpty(data)) {
			try {
				String reportName = "AdHoc Oders";
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("reportName", reportName);
				parameters.put("localDateTimeFormatter", CommonUtility.localDateTimeFormatter);
				parameters.put("printedOn", LocalDateTime.now().format(CommonUtility.localDateTimeFormatter));
				parameters.put("tableData", new JRBeanCollectionDataSource(data));
				
				InputStream jasperInput = ExportServiceImpl.class.getResourceAsStream("/" + "jasper/AdHocOders.jrxml");
				JasperDesign jasperDesign = JRXmlLoader.load(jasperInput);
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
				jasperPrints.add(jasperPrint);
				
				return getByteArrayResource(reportName, type, jasperPrints, parameters);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public ResponseEntity<ByteArrayResource> patientServiceReportExport(Model model, String type, Integer patientServiceReport, String dateSelection, String diagonosisIds, String dietTypeOralSolidIds, String dietSubTypeIds) {
		reportService.patientServiceReport(model, patientServiceReport, dateSelection, diagonosisIds, dietTypeOralSolidIds, dietSubTypeIds);
		List<PatientServiceReportDto> patientServiceReportList = ObjectUtils.isNotEmpty(model.getAttribute("patientServiceReportList")) ? (List<PatientServiceReportDto>) model.getAttribute("patientServiceReportList") : new ArrayList<>();
		if (CollectionUtils.isNotEmpty(patientServiceReportList)) {
			try {
				String reportName = patientServiceReport == 1 ? "Patient Service Report" : "Patient Service Report";
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("reportName", reportName);
				parameters.put("localDateTimeFormatter", CommonUtility.localDateTimeFormatter);
				parameters.put("printedOn", LocalDateTime.now().format(CommonUtility.localDateTimeFormatter));
				parameters.put("tableData", new JRBeanCollectionDataSource(patientServiceReportList));
				
				InputStream jasperInput = ExportServiceImpl.class.getResourceAsStream("/" + "jasper/"+ (patientServiceReport == 1 ? "PatientServiceReport_DietType" : "PatientServiceReport_Diagnosis") + ".jrxml");
				JasperDesign jasperDesign = JRXmlLoader.load(jasperInput);
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
				jasperPrints.add(jasperPrint);
				
				return getByteArrayResource(reportName, type, jasperPrints, parameters);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
