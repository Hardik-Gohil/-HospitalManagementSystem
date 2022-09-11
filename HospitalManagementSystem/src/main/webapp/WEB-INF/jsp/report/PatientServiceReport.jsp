<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>Patient Service Report</title>
		<%@include file="../includes/HeadScript.jsp"%>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/css/bootstrap-select.css" />
		<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
		<!-- DataTables -->
		<link rel="stylesheet" href="${contextPath}/resources/dist/plugins/datatables/css/dataTables.bootstrap.min.css">
        <!-- SweetAlert2 -->
        <link rel="stylesheet" href="${contextPath}/resources/dist/plugins/sweetalert2-theme-bootstrap-4/bootstrap-4.min.css">				
		<style type="text/css">
			.table-sm>tbody>tr>td, .table>tfoot>tr>td {
			    border-top: 1px solid #f4f4f4;
			    border-bottom: 1px solid #f4f4f4;
			}
			.showUpdated {
				color: #0c5460 !important;
				background-color: #bee5eb !important;
			}				
		</style>		
	</head>
	<body class="skin-blue sidebar-mini">
		<div class="wrapper boxed-wrapper">
			<%@include file="../includes/Header.jsp"%>
			<%@include file="../includes/Sidebar.jsp"%>
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper">
				<!-- Content Header (Page header) -->
				<div class="content-header sty-one">
					<h1>Patient Service Report</h1>
				</div>
				<!-- Main content -->
				<div class="content">
					<div class="card">
						<div class="card-body">
							<form method="POST" action="${contextPath}/reports/patient-service-report" onsubmit="return Validation();" id="patientServiceReportForm">
								<input type="hidden" name="type" id="type"></input>
								<div class="row">
									<div class="col-lg-12">
										<fieldset class="form-group">
<!-- 											<label for="serviceType">Service Type:</label><span class="text-danger">*</span> -->
											<div class="radio">
											<label><input type="radio" name="patientServiceReport" id="patientServiceReport1" value="1" checked="checked"> Diet Type Wise </label>
											<label><input type="radio" name="patientServiceReport" id="patientServiceReport2" value="2"> Diagnosis Wise </label>
											</div>
										</fieldset>	
									</div>
								</div>
								<div class="row">
									<div class="col-lg-3">
										<fieldset class="form-group">
											<label for="dateSelection">Date Selection</label><span class="text-danger">*</span>
											<input class="form-control daterange-single" id="dateSelection" name="dateSelection"></input>
										</fieldset>
									</div>	
									<div class="col-lg-3">
										<fieldset class="form-group">
											<label for="diagonosis">Diagnosis:</label><span class="text-danger">*</span>
											<select class="form-control selectpicker" id="diagonosis" name="diagonosisIds" multiple data-live-search="true" data-size="10" data-actions-box="true">
												<c:forEach items="${diagonosisList}" var="diagonosis">
													<option value="${diagonosis.diagonosisId}">${diagonosis.value}</option>
												</c:forEach>
											</select>
										</fieldset>	
									</div>	
									<div class="col-lg-3">
										<fieldset class="form-group">
											<label for="dietTypeOralSolid">Diet Type:</label><span class="text-danger">*</span>
											<select class="form-control selectpicker" id="dietTypeOralSolid" name="dietTypeOralSolidIds" multiple data-live-search="true" data-size="10" data-actions-box="true">
												<c:forEach items="${dietTypeOralSolidList}" var="dietTypeOralSolid">
													<option value="${dietTypeOralSolid.dietTypeOralSolidId}">${dietTypeOralSolid.value}</option>
												</c:forEach>
												<option value="0">Extra Liquid</option>
											</select>
										</fieldset>	
									</div>	
									<div class="col-lg-3">
										<fieldset class="form-group">
											<label for="dietSubType">Diet Sub Type:</label><span class="text-danger">*</span>
											<select class="form-control selectpicker" id="dietSubType" name="dietSubTypeIds" multiple data-live-search="true" data-size="10" data-actions-box="true">
											<c:forEach items="${dietSubTypeList}" var="dietSubType">
													<option value="${dietSubType.dietSubTypeId}" class="dietSubType_options">${dietSubType.value}</option>
												</c:forEach>
											</select>
										</fieldset>	
									</div>																																		
								</div>								
								<button type="submit" class="btn btn-success waves-effect waves-light" onclick="changeAction('')">Generate Report</button>
								<button type="submit" class="btn btn-outline-primary" onclick="changeAction('1')"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;&nbsp;&nbsp;PDF</button>
							    <button type="submit" class="btn btn-outline-primary" onclick="changeAction('2')"><i class="fa fa-file-excel-o"></i>&nbsp;&nbsp;&nbsp;&nbsp;Excel</button>
							</form>
						</div>
					</div>
					 <c:if test="${not empty patientServiceReportList}">
		               <div class="card">	
		                  <div class="card-body">
		                     <div class="table-responsive">
		                        <table id="" class="table table-bordered table-striped">
		                           <thead>
		                              <tr>
		                                 <th rowspan="2">Sr No</th>
		                                 <c:if test="${patientServiceReport eq '1'}"><th rowspan="2">Diet Type</th></c:if>
		                                 <c:if test="${patientServiceReport eq '1'}"><th rowspan="2">Diet Sub Type</th></c:if>
		                                 <c:if test="${patientServiceReport eq '2'}"><th rowspan="2">Diagnosis</th></c:if>
		                                 <th colspan="7">Total Discharged patients</th>
		                              </tr>
		                              <tr>
		                                 <th>Normal</th>
		                                 <th>DD</th>
		                                 <th>Renal</th>
		                                 <th>SRD</th>
		                                 <th>SFD</th>
		                                 <th>FFD</th>
		                                 <th>Total</th>     
		                              </tr>		                              
		                           </thead>
		                           <tbody>
		                           <c:if test="${patientServiceReport eq '1'}">
									<c:forEach items="${patientServiceReportList}" var="patientServiceReport" varStatus="loop">
										<tr>
											 <td>${loop.index + 1}</td>
											 <td>${patientServiceReport.dietType}</td>
											 <td>${patientServiceReport.dietSubType}</td>
											 <td>${patientServiceReport.normal}</td>
											 <td>${patientServiceReport.dd}</td>
											 <td>${patientServiceReport.renal}</td>
											 <td>${patientServiceReport.srd}</td>
											 <td>${patientServiceReport.sfd}</td>
											 <td>${patientServiceReport.ffd}</td>
											 <td>${patientServiceReport.total}</td>
										 </tr>
									</c:forEach>		                           
		                           </c:if>
		  						   <c:if test="${patientServiceReport eq '2'}">
									<c:forEach items="${patientServiceReportList}" var="patientServiceReport" varStatus="loop">
										<tr>
											 <td>${loop.index + 1}</td>
											 <td>${patientServiceReport.diagnosis}</td>
											 <td>${patientServiceReport.normal}</td>
											 <td>${patientServiceReport.dd}</td>
											 <td>${patientServiceReport.renal}</td>
											 <td>${patientServiceReport.srd}</td>
											 <td>${patientServiceReport.sfd}</td>
											 <td>${patientServiceReport.ffd}</td>
											 <td>${patientServiceReport.total}</td>
										 </tr>
									</c:forEach>		  						   
		                           </c:if>
		                           </tbody>
		                        </table>
		                     </div>
		                  </div>
		               </div>						 
					 </c:if>
				</div>
				<!-- /.content --> 
			</div>
			<!-- /.content-wrapper -->
			<%@include file="../includes/Footer.jsp"%>
		</div>
		<!-- ./wrapper --> 
		<%@include file="../includes/FooterScript.jsp"%>
		<!-- DataTable --> 
		<script src="${contextPath}/resources/dist/plugins/datatables/jquery.dataTables.min.js"></script> 
		<script src="${contextPath}/resources/dist/plugins/datatables/dataTables.bootstrap.min.js"></script> 
				
		<script src="${contextPath}/resources/dist/plugins/jquery-validation-1.19.3/dist/jquery.validate.js"></script>
		<script src="${contextPath}/resources/dist/plugins/moment/min/moment.min.js"></script>
		<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>

        <!-- SweetAlert2 -->
        <script src="${contextPath}/resources/dist/plugins/sweetalert2/sweetalert2.min.js"></script>			 
		<script type="text/javascript">
		var dateFormat = "DD/MM/YYYY";
		
		function changeAction(value) {
	    	if (value == '') {
	    		$("#patientServiceReportForm").attr("action", "${contextPath}/reports/patient-service-report");
	    		$("#patientServiceReportForm").attr("target", "");
	    	} else if (value == '1') {
	    		$("#patientServiceReportForm").attr("action", "${contextPath}/reports/patient-service-report-export");
	    		$("#patientServiceReportForm").attr("target", "_blank");
	    		$("#type").val("PDF");
	    	} else if (value == '2') {
	    		$("#patientServiceReportForm").attr("action", "${contextPath}/reports/patient-service-report-export");
	    		$("#patientServiceReportForm").attr("target", "_blank");
	    		$("#type").val("EXCEL");
	    	} 
		}

		function Validation() {
		}
		
		$(document).ready(function() {
		    $("#Patients").addClass("active");
		    $('.selectpicker').selectpicker(); 
		    
		    $('#dateSelection').daterangepicker({
		        alwaysShowCalendars: true,
		        locale: {
		            format: dateFormat
		        }
		    });

		    $("#patientServiceReportForm").validate({
		        // in 'rules' user have to specify all the constraints for respective fields
		        rules: {
		        	diagonosisIds: {
		                required: {
		                    depends: function(element) {
		                        return ($('input[name="patientServiceReport"]:checked').val() == 2);
		                    }
		                }
		            },
		            dietTypeOralSolidIds: {
		                required: {
		                    depends: function(element) {
		                        return ($('input[name="patientServiceReport"]:checked').val() == 1);
		                    }
		                }
		            },
		            dietSubTypeIds: {
		                required: {
		                    depends: function(element) {
		                        return ($('input[name="patientServiceReport"]:checked').val() == 1);
		                    }
		                }
		            }
		        },
		        errorPlacement: function(error, element) {
		            if (element.is("select")) {
		                error.insertAfter(element.parent("div"));
		            } else {
		                error.insertAfter(element);
		            }
		        },
		        // in 'messages' user have to specify message as per rules
		        messages: {
		        	diagonosisIds: {
		                required: "Please select"
		            },
		            dietTypeOralSolidIds: {
		            	required: "Please select"
		            },
		            dietSubTypeIds: {
		            	required: "Please select"	                
		            }
		        }
		    });    	
		});
		</script>    	
	</body>
</html>