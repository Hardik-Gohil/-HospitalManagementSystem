<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>Patient Details</title>
		<%@include file="../includes/HeadScript.jsp"%>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
		<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
	</head>
	<body class="skin-blue sidebar-mini">
		<div class="wrapper boxed-wrapper">
			<%@include file="../includes/Header.jsp"%>
			<%@include file="../includes/Sidebar.jsp"%>
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper">
				<!-- Content Header (Page header) -->
				<div class="content-header sty-one">
					<c:if test="${empty patientDto.patientId}">
						<h1>Add Patient Details</h1>
					</c:if>
					<c:if test="${patientDto.patientId ge 0}">
						<h1>Edit Patient Details</h1>
					</c:if>
				</div>
				<!-- Main content -->
				<div class="content">
					<div class="card">
						<div class="card-body">
							<form:form method="POST" action="${contextPath}/diet/patient-details" modelAttribute="patientDto" onsubmit="return Validation();" id="planDetailsForm">
								<input type="hidden" id="admittedDateStr" value="${not empty patientDto.admittedDate ? patientDto.admittedDate.format(localDateTimeFormatter) : ''}"></input>
								<form:hidden path="patientId" id="patientId"/>
								<form:hidden path="immediateService" id="immediateService"/>
								<div class="row">
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="patientName">Patient Name</label><span class="text-danger">*</span>
											<form:input cssClass="form-control" id="patientName" path="patientName" readonly="${patientDto.patientId ge 0}"></form:input>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="umrNumber">UMR No</label><span class="text-danger">*</span>
											<form:input cssClass="form-control" id="umrNumber" path="umrNumber" readonly="${patientDto.patientId ge 0}"></form:input>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="ipNumber">IP number</label><span class="text-danger">*</span>
											<form:input cssClass="form-control" id="ipNumber" path="ipNumber" readonly="${patientDto.patientId ge 0}"></form:input>
										</fieldset>
									</div>
								</div>
								<div class="row">
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="admittedDate">Admission Date</label><span class="text-danger">*</span>
											<form:input cssClass="form-control daterange-single" id="admittedDate" path="admittedDate" disabled="${patientDto.patientId ge 0}"></form:input>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="doctor">Doctor Name</label><span class="text-danger">*</span>
											<form:input cssClass="form-control" id="doctor" path="doctor" readonly="${patientDto.patientId ge 0}"></form:input>
										</fieldset>
									</div>
								</div>
								<div class="row">
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="bedCode">Bed Cd</label><span class="text-danger">*</span>
											<form:select cssClass="form-control selectpicker" id="bed" path="bed.bedId" disabled="${patientDto.patientId ge 0}" data-live-search="true" data-size="10" onchange="bedChange();">
												<c:forEach items="${bedList}" var="bed">
													<form:option value="${bed.bedId}" data-wardName="${bed.wardName}" data-floorName="${bed.floor.floorName}">${bed.bedCode}</form:option>
												</c:forEach>
											</form:select>
										</fieldset>
									</div>
									<div class="col-lg-2">
										<fieldset class="form-group">
											<label for="floorName">Floor</label>
											<input class="form-control" readonly="readonly" id="floorName"></input>
										</fieldset>
									</div>
									<div class="col-lg-2">
										<fieldset class="form-group">
											<label for="wardName">Ward Name</label>
											<input class="form-control" readonly="readonly" id="wardName"></input>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="nbm">NBM</label>
											<form:select cssClass="form-control selectpicker" id="nbm" path="nbm" onchange="nbmChange();">
												<form:option value="false">No</form:option>
												<form:option value="true">Yes</form:option>
											</form:select>
										</fieldset>
									</div>
								</div>
								<div class="row">
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="dietTypeOralSolid">Diet Type- Oral Solid</label>
											<form:select cssClass="form-control selectpicker" id="dietTypeOralSolid" path="dietTypeOralSolid.dietTypeOralSolidId" data-size="10">
												<form:option value="" disabled="disabled" selected="selected">Please select</form:option>
												<c:forEach items="${dietTypeOralSolidList}" var="dietTypeOralSolid">
													<form:option value="${dietTypeOralSolid.dietTypeOralSolidId}">${dietTypeOralSolid.value}</form:option>
												</c:forEach>
											</form:select>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="extraLiquid">Extra Liquid</label>
											<form:select cssClass="form-control selectpicker" id="extraLiquid" path="extraLiquid" onchange="extraLiquidChange();">
												<form:option value="false">No</form:option>
												<form:option value="true">Yes</form:option>
											</form:select>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="dietTypeOralLiquidTF">Diet Type- Oral Liquid/TF</label>
											<form:select cssClass="form-control selectpicker" id="dietTypeOralLiquidTF" path="dietTypeOralLiquidTF.dietTypeOralLiquidTFId" onchange="dietTypeOralLiquidTFChange();" data-size="10">
												<form:option value="" disabled="disabled" selected="selected">Please select</form:option>
												<c:forEach items="${dietTypeOralLiquidTFList}" var="dietTypeOralLiquidTF">
													<form:option value="${dietTypeOralLiquidTF.dietTypeOralLiquidTFId}">${dietTypeOralLiquidTF.value}</form:option>
												</c:forEach>
											</form:select>
										</fieldset>
									</div>
								</div>
								<div class="row" id="dietTypeOralLiquidTF_row">
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="dietSubType">Diet Sub Type</label><span class="text-danger">*</span>
											<form:select cssClass="form-control selectpicker" id="dietSubType" path="dietSubType.dietSubTypeId" data-size="10">
												<form:option value="" disabled="disabled" selected="selected">Please select</form:option>
												<c:forEach items="${dietSubTypeList}" var="dietSubType">
													<form:option value="${dietSubType.dietSubTypeId}" class="dietSubType_options" data-dietTypeOralLiquidTF="${dietSubType.dietTypeOralLiquidTF.dietTypeOralLiquidTFId}">${dietSubType.value}</form:option>
												</c:forEach>
											</form:select>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="quantity">Quantity</label><span class="text-danger">*</span>
											<form:select cssClass="form-control selectpicker" id="quantity" path="quantity.quantityId" data-live-search="true" data-size="10">
												<c:forEach items="${quantityList}" var="quantity">
													<form:option value="${quantity.quantityId}">${quantity.valueStr}</form:option>
												</c:forEach>
											</form:select>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="frequency">Frequency</label><span class="text-danger">*</span>
											<form:select cssClass="form-control selectpicker" id="frequency" path="frequency.frequencyId" data-size="10">
												<c:forEach items="${frequencyList}" var="frequency">
													<form:option value="${frequency.frequencyId}">${frequency.valueStr}</form:option>
												</c:forEach>
											</form:select>
										</fieldset>
									</div>
								</div>
								<div class="row">
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="medicalComorbidities">Medical Co-morbidities</label><span class="text-danger">*</span>
											<select class="form-control selectpicker" id="medicalComorbidities" name="medicalComorbiditiesIds" multiple data-live-search="true" data-size="10" title="Please select">
												<c:forEach items="${medicalComorbiditiesList}" var="medicalComorbidities">
													<option value="${medicalComorbidities.medicalComorbiditiesId}">${medicalComorbidities.value}</option>
												</c:forEach>
											</select>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="diagonosis">Diagonosis</label><span class="text-danger">*</span>
											<select class="form-control selectpicker" id="diagonosis" name="diagonosisIds" multiple data-live-search="true" data-size="10" title="Please select">
												<c:forEach items="${diagonosisList}" var="diagonosis">
													<option value="${diagonosis.diagonosisId}">${diagonosis.value}</option>
												</c:forEach>
											</select>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="specialNotesByNursing">Special Notes By Nursing</label><span class="text-danger">*</span>
											<select class="form-control selectpicker" id="specialNotesByNursing" name="specialNotesByNursingIds" multiple data-live-search="true" data-size="10" onchange="specialNotesByNursingChange();" title="Please select">
												<c:forEach items="${specialNotesByNursingList}" var="specialNotesByNursing">
													<option value="${specialNotesByNursing.specialNotesByNursingId}">${specialNotesByNursing.value}</option>
												</c:forEach>
												<option value="0">Others</option>
											</select>
										</fieldset>
									</div>
								</div>
								<div class="row" id="othersSpecialNotesByNursing_row">
									<div class="col-lg-12">
										<fieldset class="form-group">
											<label for="othersSpecialNotesByNursing">Others Special Notes By Nursing</label><span class="text-danger">*</span>
											<form:input cssClass="form-control" id="othersSpecialNotesByNursing" path="othersSpecialNotesByNursing"></form:input>
										</fieldset>
									</div>
								</div>
								<div class="row">
									<div class="col-lg-4">
<!-- 										<div class="checkbox"> -->
<%-- 											<form:checkbox id="startServiceImmediately" path="startServiceImmediately"></form:checkbox> --%>
<!-- 											<label for="startServiceImmediately">Start Service Immediately</label><span class="text-danger">*</span> -->
<!-- 										</div> -->
										<div class="checkbox">
											<form:checkbox id="isVip" path="isVip"></form:checkbox>
											<label for="isVip">Is VIP</label><span class="text-danger">*</span>
										</div>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="nursingName">Nursing Name</label><span class="text-danger">*</span>
											<form:input cssClass="form-control" id="nursingName" path="nursingName"></form:input>
										</fieldset>
									</div>
									<div class="col-lg-4" style="display: none;">
										<fieldset class="form-group">
											<label for="employeeNo">Employee No</label><span class="text-danger">*</span>
											<form:input cssClass="form-control" id="employeeNo" path="employeeNo"></form:input>
										</fieldset>
									</div>
								</div>
								<c:if test="${patientDto.patientStatus ne 2}">
									<button type="submit" class="btn btn-success waves-effect waves-light" onclick="changeImmediateService('FALSE')">Submit</button>
									<a href="${contextPath}/diet/patients">
										<button type="button" class="btn btn-inverse waves-effect waves-light">Cancel</button>
									</a>									
									<button type="submit" class="btn btn-primary waves-effect waves-light" onclick="changeImmediateService('TRUE')">Start Service Immediately</button>
								</c:if>
								<c:if test="${patientDto.patientStatus eq 2}">
									<a href="${contextPath}/diet/patients">
										<button type="button" class="btn btn-primary waves-effect waves-light">Go To Patients</button>
									</a>								
								</c:if>
							</form:form>
						</div>
					</div>
				</div>
				<!-- /.content --> 
			</div>
			<!-- /.content-wrapper -->
			<%@include file="../includes/Footer.jsp"%>
		</div>
		<!-- ./wrapper --> 
		<%@include file="../includes/FooterScript.jsp"%>
		<script src="${contextPath}/resources/dist/plugins/jquery-validation-1.19.3/dist/jquery.validate.js"></script>
		<script src="${contextPath}/resources/dist/plugins/moment/min/moment.min.js"></script>
		<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
		<script type="text/javascript">
		jQuery.validator.addMethod("alphanumericWithSpeCharValidator", function(value, element) {
		    $(element).val((this.elementValue(element).replace(/\s+/g, ' ')));
		    this.value = $(element).val();
		    if (alphanumericWithSpeChar.test(value)) {
		        return true;
		    } else {
		        return false;
		    };
		});

		jQuery.validator.addMethod("numberRegex", function(value, element) {
		    $(element).val((this.elementValue(element).replace(/\s+/g, ' ')));
		    this.value = $(element).val();
		    if (/^\d{2,50}$/.test(value)) {
		        return true;
		    } else {
		        return false;
		    };
		});

		jQuery.validator.addMethod("ipNumberRegex", function(value, element) {
		    $(element).val((this.elementValue(element).replace(/\s+/g, ' ')));
		    this.value = $(element).val();
		    if (/(IP-)\d{7,50}$/.test(value)) {
		        return true;
		    } else {
		        return false;
		    };
		});
		</script>  
		<script type="text/javascript">
		var dateFormat = "MM/DD/YYYY h:mm:ss a";
		function changeImmediateService(value) {
		    $("#immediateService").val(value);
		}
		
		function nbmChange() {
		    if ($("#nbm").val() == "true") {
		        $("#dietTypeOralSolid").attr("disabled", true);
		        $("#extraLiquid").attr("disabled", true);
		        $("#dietTypeOralLiquidTF").attr("disabled", true);
		        $("#dietSubType").attr("disabled", true);
		        $("#quantity").attr("disabled", true);
		        $("#frequency").attr("disabled", true);
		    } else {
		        $("#dietTypeOralSolid").attr("disabled", false);
		        $("#extraLiquid").attr("disabled", false);
		        $("#dietTypeOralLiquidTF").attr("disabled", false);
		        $("#dietSubType").attr("disabled", false);
		        $("#quantity").attr("disabled", false);
		        $("#frequency").attr("disabled", false);
		    }
		    $('.selectpicker').selectpicker('refresh');
		}

		function extraLiquidChange() {
		    if ($("#extraLiquid").val() == "true") {
		        $("#dietTypeOralLiquidTF").attr("disabled", true);
		        $("#dietSubType").attr("disabled", true);
		        $("#quantity").attr("disabled", true);
		        $("#frequency").attr("disabled", true);
		    } else {
		        $("#dietTypeOralLiquidTF").attr("disabled", false);
		        $("#dietSubType").attr("disabled", false);
		        $("#quantity").attr("disabled", false);
		        $("#frequency").attr("disabled", false);
		    }
		    $('.selectpicker').selectpicker('refresh');
		}
		
		function dietTypeOralLiquidTFChange() {
		    if ($("#dietTypeOralLiquidTF").val() == "") {
		    	$("#extraLiquid").attr("disabled", false);
		        $("#dietTypeOralLiquidTF_row").hide();
		        $("#dietSubType").attr("disabled", true);
		        $("#quantity").attr("disabled", true);
		        $("#frequency").attr("disabled", true);
		    } else {
		    	$("#extraLiquid").attr("disabled", true);
		        $("#dietTypeOralLiquidTF_row").show();
		        $("#dietSubType").attr("disabled", false);
		        $("#quantity").attr("disabled", false);
		        $("#frequency").attr("disabled", false);
		        $("#dietSubType").val("");
		        $(".dietSubType_options").each(function() {
		            if ($(this).attr("data-dietTypeOralLiquidTF") == $("#dietTypeOralLiquidTF").val()) {
		                $(this).show();
		            } else {
		                $(this).hide();
		            }
		        });
		    }
		    $('.selectpicker').selectpicker('refresh');
		}

		function specialNotesByNursingChange() {
		    if (!$("#specialNotesByNursing").val().includes('0')) {
		        $("#othersSpecialNotesByNursing_row").hide();
		        $("#othersSpecialNotesByNursing").attr("disabled", true);
		    } else {
		        $("#othersSpecialNotesByNursing_row").show();
		        $("#othersSpecialNotesByNursing").attr("disabled", false);
		    }
		}

		function bedChange() {
		    $('#floorName').val($('#bed').find(":selected").attr("data-floorName"));
		    $('#wardName').val($('#bed').find(":selected").attr("data-wardName"));
		}

		function Validation() {
			 $("#extraLiquid").attr("disabled", false);
		}

		$(document).ready(function() {
		    $("#Patients").addClass("active");
		    $('.selectpicker').selectpicker(); 
		    <c:if test="${not empty patientDto.medicalComorbiditiesIds}">
		        $('#medicalComorbidities').selectpicker('val', [${patientDto.medicalComorbiditiesIds}]); 
		    </c:if> 
		    <c:if test ="${not empty patientDto.diagonosisIds}">
		        $('#diagonosis').selectpicker('val', [${patientDto.diagonosisIds}]); 
		    </c:if> 
		    <c:if test="${not empty patientDto.specialNotesByNursingIds}">
		        $('#specialNotesByNursing').selectpicker('val', [${patientDto.specialNotesByNursingIds}]); 
		    </c:if> 
		    <c:if test="${not empty patientDto.othersSpecialNotesByNursing}">
		        var specialNotesByNursingValue = $('#specialNotesByNursing').val();
		   		specialNotesByNursingValue.push(0);
		   		$('#specialNotesByNursing').selectpicker('val', specialNotesByNursingValue); 
			</c:if>       	
		    $('#admittedDate').daterangepicker({
		        alwaysShowCalendars: true,
		        singleDatePicker: true,
		        timePicker: true,
		        locale: {
		            format: 'MM/DD/YYYY h:mm:ss a'
		        }
		    });
		    if ($('#admittedDateStr').val() != "") {
		        $('#admittedDate').data('daterangepicker').setStartDate($('#admittedDateStr').val());
		    } else {
		    	 $('#admittedDate').data('daterangepicker').setStartDate(moment().format(dateFormat));
		    }
		    bedChange();
		    dietTypeOralLiquidTFChange();
		    nbmChange();
		    specialNotesByNursingChange();
		    <c:if test="${not empty patientDto.dietSubType.dietSubTypeId}">
		   		$("#dietSubType").val("${patientDto.dietSubType.dietSubTypeId}"); 
		   	 	$('.selectpicker').selectpicker('refresh');
	   		</c:if> 		    

		    $("#planDetailsForm").validate({
		        // in 'rules' user have to specify all the constraints for respective fields
		        rules: {
		            patientName: {
		                required: true,
		                minlength: 2,
		                maxlength: 150,
		                alphanumericWithSpeCharValidator: true
		            },
		            umrNumber: {
		                required: true,
		                minlength: 2,
		                maxlength: 50,
		                numberRegex: true
		            },
		            ipNumber: {
		                required: true,
		                minlength: 10,
		                maxlength: 50,
		                ipNumberRegex: true,
		                remote: {
		                    url: contextPath + "/diet/check-unique-ipNumber",
		                    type: "POST",
		                    async: false,
		                    data: {
		                        ipNumber: function() {
		                            return $("#ipNumber").val();
		                        },
		                        patientId: function() {
		                            return $("#patientId").val();
		                        }
		                    }
		                }
		            },
		            doctor: {
		                required: true,
		                minlength: 2,
		                maxlength: 150,
		                alphanumericWithSpeCharValidator: true
		            },
		            "dietTypeOralSolid.dietTypeOralSolidId": {
		                required: {
		                    depends: function(element) {
		                        return ($("#nbm").val() == "false" && $("#dietTypeOralSolid").val() == "" && $("#dietTypeOralLiquidTF").val() == "");
		                    }
		                }
		            },
		            "dietTypeOralLiquidTF.dietTypeOralLiquidTFId": {
		                required: {
		                    depends: function(element) {
		                        return ($("#nbm").val() == "false" && $("#dietTypeOralSolid").val() == "" && $("#dietTypeOralLiquidTF").val() == "");
		                    }
		                }
		            },
		            "dietSubType.dietSubTypeId": {
		                required: {
		                    depends: function(element) {
		                        return ($("#nbm").val() == "false" && $("#dietTypeOralLiquidTF").val() != "");
		                    }
		                }
		            },		            
		            "quantity.quantityId": {
		                required: {
		                    depends: function(element) {
		                        return ($("#nbm").val() == "false" && $("#dietTypeOralLiquidTF").val() != "");
		                    }
		                }
		            },
		            "frequency.frequencyId": {
		                required: {
		                    depends: function(element) {
		                        return ($("#nbm").val() == "false" && $("#dietTypeOralLiquidTF").val() != "");
		                    }
		                }
		            },
		            medicalComorbiditiesIds: {
		                required: true
		            },
		            diagonosisIds: {
		                required: true
		            },
		            specialNotesByNursingIds: {
		                required: true
		            },
		            othersSpecialNotesByNursing: {
		                required: {
		                    depends: function(element) {
		                        return ($("#specialNotesByNursing").val().includes('0'));
		                    }
		                },
		                minlength: 2,
		                maxlength: 150,
		                alphanumericWithSpeCharValidator: true
		            },
		            nursingName: {
		                required: true,
		                minlength: 2,
		                maxlength: 150,
		                alphanumericWithSpeCharValidator: true
		            },
		            employeeNo: {
		                required: false,
		                minlength: 2,
		                maxlength: 150,
		                alphanumericWithSpeCharValidator: true
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
		            patientName: {
		                required: "Please enter Patient Name",
		                minlength: "At least 2 characters required",
		                maxlength: "Max 150 characters allowed",
		                alphanumericWithSpeCharValidator: "Only Alphanumeric characters and " + allowsChars + " are allowed"
		            },
		            umrNumber: {
		                required: "Please enter UMR No",
		                minlength: "At least 2 characters required",
		                maxlength: "Max 50 characters allowed",
		                numberRegex: "Please enter valid UMR No"
		            },
		            ipNumber: {
		                required: "Please enter IP number",
		                minlength: "At least 10 characters required",
		                maxlength: "Max 50 characters allowed",
		                ipNumberRegex: "Must start with IP- followed by numbers only",
		                remote: "IP number already in use"
		            },
		            doctor: {
		                required: "Please enter Doctor Name",
		                minlength: "At least 2 characters required",
		                maxlength: "Max 150 characters allowed",
		                alphanumericWithSpeCharValidator: "Only Alphanumeric characters and " + allowsChars + " are allowed"
		            },
		            "dietTypeOralSolid.dietTypeOralSolidId": {
		                required: "Please Select Diet Type- Oral Solid or Diet Type- Oral Liquid/TF"
		            },
		            "dietTypeOralLiquidTF.dietTypeOralLiquidTFId": {
		                required: "Please Select Diet Type- Oral Solid or Diet Type- Oral Liquid/TF"
		            },
		            "dietSubType.dietSubTypeId": {
		                required: "Please Select Diet Sub Type"
		            },		            
		            "quantity.quantityId": {
		                required: "Please Select Quantity"
		            },
		            "frequency.frequencyId": {
		                required: "Please Select Frequency"
		            },
		            medicalComorbiditiesIds: {
		                required: "Please Select Medical Co-morbidities"
		            },
		            diagonosisIds: {
		                required: "Please Select Diagonosis"
		            },
		            specialNotesByNursingIds: {
		                required: "Please Select Special Notes By Nursing"
		            },
		            othersSpecialNotesByNursing: {
		                required: "Please enter Others Special Notes By Nursing",
		                minlength: "At least 2 characters required",
		                maxlength: "Max 150 characters allowed",
		                alphanumericWithSpeCharValidator: "Only Alphanumeric characters and " + allowsChars + " are allowed"
		            },
		            nursingName: {
		                required: "Please enter Nursing Name",
		                minlength: "At least 2 characters required",
		                maxlength: "Max 150 characters allowed",
		                alphanumericWithSpeCharValidator: "Only Alphanumeric characters and " + allowsChars + " are allowed"
		            },
		            employeeNo: {
		                required: "Please enter Employee No",
		                minlength: "At least 2 characters required",
		                maxlength: "Max 150 characters allowed",
		                alphanumericWithSpeCharValidator: "Only Alphanumeric characters and " + allowsChars + " are allowed"
		            }
		        }
		    });
		});
		</script>    	
	</body>
</html>