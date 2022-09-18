<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>Diet Instruction</title>
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
					<h1>Diet Instruction</h1>
				</div>
				<!-- Main content -->
				<div class="content">
					<div class="card">
						<div class="card-body">
							<%@include file="IncludePatientDetails.jsp"%>
							<Br>
							<form:form method="POST" action="${contextPath}/diet/diet-instruction" modelAttribute="dietInstructionDto" onsubmit="return Validation();" id="dietInstructionForm">
								<form:hidden path="dietInstructionId" id="dietInstructionId"/>
								<form:hidden path="patient.patientId" id="patientId"/>
								<div class="row">
									<div class="col-lg-12">
										<fieldset class="form-group">
											<label for="dietSubType">Diet Instruction</label><span class="text-danger">*</span>
											<form:textarea cssClass="form-control" id="instruction" path="instruction" rows="2"></form:textarea>
										</fieldset>
									</div>
								</div>
								<div class="row">
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="medicalComorbidities">Service Type</label><span class="text-danger">*</span>
											<select class="form-control selectpicker" id="serviceMasters" name="serviceMasterIds" multiple data-live-search="true" data-size="10" onchange="serviceMastersChange();" title="Please select" data-actions-box="true">
												<c:forEach items="${serviceMasterList}" var="serviceMasters">
													<option value="${serviceMasters.serviceMasterId}">${serviceMasters.service}</option>
												</c:forEach>
											</select>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="applicableFor">Applicable for</label><span class="text-danger">*</span>
											<form:select cssClass="form-control selectpicker" id="applicableFor" path="applicableFor" onchange="applicableForChange();">
												<form:option value="1">Selected Date</form:option>
												<form:option value="2" id="dailyOption">Daily</form:option>
											</form:select>
										</fieldset>
									</div>
									<div class="col-lg-4" id="dateSelection_div">
										<fieldset class="form-group">
											<label for="dateSelection">Date Selection</label><span class="text-danger">*</span>
											<input class="form-control daterange-single" id="dateSelection" name="dateSelection"></input>
										</fieldset>
									</div>
								</div>
								<c:if test="${patient.patientStatus ne 2}">
									<c:if test="${isDietitian || isAdmin}">
										<button type="submit" class="btn btn-success waves-effect waves-light">Submit</button>
									</c:if>
									<a href="${contextPath}/diet/diet-instruction?patientId=${dietInstructionDto.patient.patientId}">
										<button type="button" class="btn btn-inverse waves-effect waves-light">Clear</button>
									</a>
								</c:if>
								<a href="${contextPath}/diet/patients">
									<button type="button" class="btn btn-primary waves-effect waves-light">Go To Patients</button>
								</a>								
							</form:form>
						</div>
					</div>
		               <div class="card">
		                  <div class="card-body">
		                     <div class="table-responsive">
		                        <table id="diet-instruction-table" class="table table-bordered table-striped">
		                           <thead>
		                              <tr>
		                                 <th>Diet Instruction</th>
		                                 <th>Service Type</th>
		                                 <th>Diet Type- Oral Solid</th>
		                                 <th>Diet Type  - Oral Liquid/TF</th>
		                                 <th>Extra Liquid</th>
		                                 <th>Applicable for</th>
		                                 <th>Date Selection</th>
		                                 <th>Action</th>
		                              </tr>
		                           </thead>
		                        </table>
		                     </div>
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
		<!-- DataTable --> 
		<script src="${contextPath}/resources/dist/plugins/datatables/jquery.dataTables.min.js"></script> 
		<script src="${contextPath}/resources/dist/plugins/datatables/dataTables.bootstrap.min.js"></script> 
				
		<script src="${contextPath}/resources/dist/plugins/jquery-validation-1.19.3/dist/jquery.validate.js"></script>
		<script src="${contextPath}/resources/dist/plugins/moment/min/moment.min.js"></script>
		<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>

        <!-- SweetAlert2 -->
        <script src="${contextPath}/resources/dist/plugins/sweetalert2/sweetalert2.min.js"></script>			
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
		</script>  
		<script type="text/javascript">
		var dateFormat = "DD/MM/YYYY";
		var invalidDateRange = [];
		var serviceInvalidDateRangeMap = new Map();
		
		jQuery.validator.addMethod("validateDateRange", function(value, element) {
			var startDate = $('#dateSelection').data('daterangepicker').startDate.format(dateFormat);
			var endDate = $('#dateSelection').data('daterangepicker').endDate.format(dateFormat);
		    for (i = 0; i < invalidDateRange.length; i++) {
		        var tempDate = invalidDateRange[i];
		        if (startDate < tempDate && endDate > tempDate) {
		            return false;
		        }
		    }
		    return true;
		});
		
		function applicableForChange() {
		    if ($("#applicableFor").val() == 2) {
		        $("#dateSelection_div").hide();
		        $("#dateSelection").attr("disabled", true);
		    } else {
		        $("#dateSelection_div").show();
		        $("#dateSelection").attr("disabled", false);
		    }
		}

		function serviceMastersChange() {
			invalidDateRange = [];
			var serviceMasters = $("#serviceMasters").val();
			for (var i=0; i<serviceMasters.length; i++) {
				var serviceInvalidDateRange = serviceInvalidDateRangeMap.get(parseInt(serviceMasters[i]));
				if (typeof serviceInvalidDateRange !== 'undefined' && serviceInvalidDateRange.length > 0) {
					for (var j=0; j<serviceInvalidDateRange.length; j++) {
						if (!invalidDateRange.includes(serviceInvalidDateRange[j])) {
							invalidDateRange.push(serviceInvalidDateRange[j]);
						}
					}
				}
			}
// 			if (invalidDateRange.length != 0) { 
// 				$('#applicableFor').selectpicker('val', "1"); 
// 				$("#dailyOption").attr("disabled", true);
// 			} else {
// 				$("#dailyOption").attr("disabled", false);
// 			}
		}		
		
		function Validation() {}

		$(document).ready(function() {
		    $("#Patients").addClass("active");
		    $('.selectpicker').selectpicker(); 
		    <c:if test="${not empty dietInstructionDto.serviceMasterIds}">
		        $('#serviceMasters').selectpicker('val', [${dietInstructionDto.serviceMasterIds}]); 
		    </c:if>       	
		    <c:if test="${not empty serviceInvalidDateRangeMap}">
				<c:forEach items="${serviceInvalidDateRangeMap}" var="entry">
					serviceInvalidDateRangeMap.set(${entry.key}, ${entry.value});
				</c:forEach>
	    	</c:if> 		    
		    $('#dateSelection').daterangepicker({
		        alwaysShowCalendars: true,
		        locale: {
		            format: dateFormat
		        },
	        "isInvalidDate" : function(date) {
	        	  for(var i = 0; i < invalidDateRange.length; i++){
	        	    if (date.format(dateFormat) == invalidDateRange[i]){
	        	      return true;
	        	    }
	        	  }}
		    });
		    <c:if test="${not empty dietInstructionDto.applicableFrom}">
		    	$('#dateSelection').data('daterangepicker').setStartDate("${dietInstructionDto.applicableFrom.format(localDateFormatter)}");
	   		</c:if> 		    
		    <c:if test="${not empty dietInstructionDto.applicableTo}">
	    		$('#dateSelection').data('daterangepicker').setEndDate("${dietInstructionDto.applicableTo.format(localDateFormatter)}");
   			</c:if> 	
		    applicableForChange();

		    $("#dietInstructionForm").validate({
		        // in 'rules' user have to specify all the constraints for respective fields
		        rules: {
		        	instruction: {
		                required: true,
		                minlength: 2,
		                maxlength: 150,
		                alphanumericWithSpeCharValidator: true
		            },
		            serviceMasterIds: {
		                required: true
		            },
		            applicableFor: {
		                required: true
		            },
		            dateSelection: {
		                required: {
		                    depends: function(element) {
		                        return ($("#applicableFor").val() == "1");
		                    }
		                },
		            	validateDateRange: true
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
		        	instruction: {
		                required: "Please enter Diet Instruction",
		                minlength: "At least 2 characters required",
		                maxlength: "Max 150 characters allowed",
		                alphanumericWithSpeCharValidator: "Only Alphanumeric characters and " + allowsChars + " are allowed"
		            },
		            serviceMasterIds: {
		                required: "Please Select Service Type"
		            },
		            applicableFor: {
		                required: "Please Select Applicable for"
		            },
		            dateSelection: {
		            	required: "Please Select Date Selection",
		            	validateDateRange: "Invalid Date Range Selected"
		            }
		        }
		    });
		    
        	var table = $('#diet-instruction-table').DataTable({
        		"ajax": {
        			'url': contextPath + "/diet/diet-instruction-data?patientId=${dietInstructionDto.patient.patientId}",
        			'method': "POST",
        			"dataSrc": ""
        		},
        		"columns": [{
        			"data": "instruction"
        		}, {
        			"data": "serviceMastersString"
        		}, {
        			"data": "dietTypeOralSolid.value",
        			"defaultContent": "-"
        		}, {
        			"data": "dietSubType.value",
        			"defaultContent": "-"
        		}, {
        			"data": "extraLiquid",
		            "render": function(data, type, row) {
		                if (type === "sort" || type === 'type') {
		                    return (data);
		                } else {
		                    return data ? "Yes" : "No";
		                }
		            }
        		}, {
        			"data": "applicableForString"
        		}, {
        			"data": "dateSelection",
        			"defaultContent": "NA"
        		}, {
        			"orderable": false,
        			"searchable": false,
        			"data": "",
        			"defaultContent": ${patient.patientStatus ne 2 && (isDietitian || isAdmin)} ? '<i class="fa fa-edit fa-lg" title="Edit"></i>&nbsp;&nbsp;<i class="fa fa-trash-o fa-lg" title="Delete"></i>' : ''

        		}],
		        "createdRow": function(row, data, dataIndex) {
		            if (data['dietInstructionId'] == "${dietInstructionDto.dietInstructionId}") {
		                $(row).addClass('showUpdated');
		            }
		        },        		
        		"order": [
        			[1, 'asc']
        		],
        		'paging': true,
        		'ordering': true,
        		'info': true,
        		'autoWidth': false,
        		"pageLength": 25,
        		"fnDrawCallback": function() {
//         			$(".status_switch").bootstrapToggle();
        		},
        	});
        	
        	$('#diet-instruction-table').on('click', 'tbody .fa-edit', function() {
        		var data_row = table.row($(this).closest('tr')).data();
        		window.location = contextPath + "/diet/diet-instruction?patientId=${dietInstructionDto.patient.patientId}&dietInstructionId=" + data_row["dietInstructionId"];
        	})
    
        	$('#diet-instruction-table').on('click', 'tbody .fa-trash-o', function(e, data) {
        		var data_row = table.row($(this).closest('tr')).data();
        		var instruction = data_row["instruction"];
        		var dietInstructionId = data_row["dietInstructionId"];
        		var title_Text = "Are you sure, you want to Delete Instruction '" + instruction + "'?";
        		var confirmButtonText_Text = "Yes, Delete it!";
        		Swal.fire({
        			title: title_Text,
        			icon: 'warning',
        			showCancelButton: true,
        			confirmButtonColor: '#3085d6',
        			cancelButtonColor: '#d33',
        			confirmButtonText: confirmButtonText_Text
        		}).then((result) => {
        			if (result.isConfirmed) {
        			    var form = document.createElement("form");
        			    var dietInstructionIdElement= document.createElement("input"); 

        			    form.method = "POST";
        			    form.action = "/diet/delete-diet-instruction";   

        			    dietInstructionIdElement.value= dietInstructionId;
        			    dietInstructionIdElement.name = "dietInstructionId";
        			    form.appendChild(dietInstructionIdElement);  

        			    document.body.appendChild(form);
        			    form.submit();
        			}
        		});
        	})        	
		});
		</script>    	
	</body>
</html>