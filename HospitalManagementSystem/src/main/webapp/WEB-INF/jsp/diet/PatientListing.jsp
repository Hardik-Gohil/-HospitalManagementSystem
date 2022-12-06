<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>Patients</title>
		<%@include file="../includes/HeadScript.jsp"%>
		<!-- DataTables -->
		<link rel="stylesheet" href="${contextPath}/resources/dist/plugins/datatables/css/dataTables.bootstrap.min.css">
		<!-- bootstrap-switch -->
		<link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
		<!-- SweetAlert2 -->
		<link rel="stylesheet" href="${contextPath}/resources/dist/plugins/sweetalert2-theme-bootstrap-4/bootstrap-4.min.css">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
		<!-- Tabs -->
		<link href="${contextPath}/resources/dist/css/tabs.css" rel="stylesheet">
		<style type="text/css">
			.showUpdated {
				color: #0c5460 !important;
				background-color: #bee5eb !important;
			}	
			.specialNotesbyNurising {
				background-color: #f5c6cb !important;
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
					<h1>Patients</h1>
					<a href="${contextPath}/diet/patient-details">
					<button type="button" class="btn btn-outline-primary" style="padding: 7px 5px;position: absolute;top: 11px;right: 10px;"><i class="fa fa-plus"></i>&nbsp;ADD&nbsp;</button>
					</a>
				</div>
				<!-- Main content -->
				<div class="content">
					<div class="card">
						<c:if test="${not empty errorMsg}">					
							<div class="alert alert-danger alert-dismissible fade show" role="alert">
								${errorMsg}
								<button type="button" class="close" data-dismiss="alert" aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
							</div>
						</c:if>					
						<div class="card-body">
							<!-- Nav tabs -->
							<ul class="nav nav-tabs customtab" role="tablist">
								<li class="nav-item"> 
									<a class="nav-link active" data-toggle="tab" href="#New-Patients" role="tab">
									<span class="hidden-sm-up"><i class="fa fa-user-plus"></i></span> 
									<span class="hidden-xs-down">New</span>&nbsp;&nbsp;&nbsp;<span class="badge bg-blue" id="New-Patients-Count">0</span>
									</a>
								</li>
								<li class="nav-item"> 
									<a class="nav-link" data-toggle="tab" href="#Active-Patients" role="tab">
									<span class="hidden-sm-up"><i class="fa fa-user-circle"></i></span>
									<span class="hidden-xs-down">Active</span>&nbsp;&nbsp;&nbsp;<span class="badge bg-blue" id="Active-Patients-Count">0</span>
									</a>
								</li>
								<li class="nav-item"> 
									<a class="nav-link" data-toggle="tab" href="#Discharged-Patients" role="tab">
									<span class="hidden-sm-up"><i class="fa fa-user-times"></i></span> 
									<span class="hidden-xs-down">Discharged</span>&nbsp;&nbsp;&nbsp;<span class="badge bg-blue" id="Discharged-Patients-Count">0</span>
									</a>
								</li>
								<c:if test="${isDietitian || isKitchen || isAdmin}">
									<li class="nav-item ml-auto">
										<button id="Export-PDF" class="btn btn-outline-primary btn-sm" type="button"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;&nbsp;&nbsp;PDF</button>
										<button id="Export-Excel" class="btn btn-outline-primary btn-sm" type="button"><i class="fa fa-file-excel-o"></i>&nbsp;&nbsp;&nbsp;&nbsp;Excel</button>
									</li>
								</c:if>
							</ul>
							<!-- Tab panes -->
							<div class="tab-content">
								<div class="tab-pane active" id="New-Patients" role="tabpanel">
									<div class="pad-20">
										<div class="table-responsive">
											<table id="new-patients-table" class="table table-striped">
												<thead>
													<tr>
														<th>Patient Name</th>
														<th>Umr No.</th>
														<th>IP number</th>
														<th>Admission Date</th>
														<th>Ward/Floor/Bed Cd</th>
														<th>Doctor Name</th>
														<th>Action</th>
													</tr>
												</thead>
											</table>
										</div>
									</div>
								</div>
								<div class="tab-pane  p-20" id="Active-Patients" role="tabpanel">
									<div class="pad-20">
										<form action="" id="searchFormActive">
											<div class="row">
												<div class="col-lg-6">
													<fieldset class="form-group">
														<input type="text" id="searchTextActive" class="form-control" placeholder="Search Patient name,Umr No., IP Number and Doctor Name">
													</fieldset>
												</div>
												<div class="col-lg-2">
													<fieldset class="form-group">
														<select class="form-control selectpicker" id="medicalComorbiditiesIdsActive" name="medicalComorbiditiesIdsActive" multiple data-live-search="true" data-size="10" title="Co-morbidities">
															<c:forEach items="${searchMedicalComorbiditiesList}" var="medicalComorbidities">
																<option value="${medicalComorbidities.medicalComorbiditiesId}">${medicalComorbidities.value}</option>
															</c:forEach>
														</select>
													</fieldset>
												</div>
												<div class="col-lg-2">
													<fieldset class="form-group">
														<select class="form-control selectpicker" id="floorIdsActive" name="floorIdsActive" multiple data-live-search="true" data-size="10" title="Floor">
															<c:forEach items="${searchFloorList}" var="floor">
																<option value="${floor.floorId}">${floor.floorName}</option>
															</c:forEach>
														</select>
													</fieldset>
												</div>
												<div class="col-lg-2">
													<fieldset class="form-group">
														<select class="form-control selectpicker" id="bedIdsActive" name="bedIdsActive" multiple data-live-search="true" data-size="10" title="Bed">
															<c:forEach items="${searchBedList}" var="bed">
																<option value="${bed.bedId}">${bed.bedCode}</option>
															</c:forEach>
														</select>
													</fieldset>
												</div>
											</div>
											<div class="row">
												<div class="col-lg-2">
													<fieldset class="form-group">
														<select class="form-control selectpicker" id="dietTypeOralSolidIdsActive" name="dietTypeOralSolidIdsActive" multiple data-live-search="true" data-size="10" title="Diet Type- Oral Solid">
															<c:forEach items="${searchDietTypeOralSolidList}" var="dietTypeOralSolid">
																<option value="${dietTypeOralSolid.dietTypeOralSolidId}">${dietTypeOralSolid.value}</option>
															</c:forEach>
														</select>
													</fieldset>
												</div>
												<div class="col-lg-2">
													<fieldset class="form-group">
														<select class="form-control selectpicker" id="dietTypeOralLiquidTFIdsActive" name="dietTypeOralLiquidTFIdsActive" multiple data-live-search="true" data-size="10" title="Diet Type- Oral Liquid/TF">
															<c:forEach items="${searchDietTypeOralLiquidTFList}" var="dietTypeOralLiquidTF">
																<option value="${dietTypeOralLiquidTF.dietTypeOralLiquidTFId}">${dietTypeOralLiquidTF.value}</option>
															</c:forEach>
														</select>
													</fieldset>
												</div>
												<div class="col-lg-2">
													<fieldset class="form-group">
														<select class="form-control selectpicker" id="dietSubTypeIdsActive" name="dietSubTypeIdsActive" multiple data-live-search="true" data-size="10" title="Diet Sub Type">
															<c:forEach items="${searchDietSubTypeList}" var="dietSubType">
																<option value="${dietSubType.dietSubTypeId}" class="dietSubType_options">${dietSubType.value}</option>
															</c:forEach>
														</select>
													</fieldset>
												</div>
												<div class="col-lg-1">
													<fieldset class="form-group">
														<div class="checkbox">
															<input id="isVipActive" type="checkbox" name="isVipActive" class="">
															<label for="isVipActive">Is VIP</label>
														</div>
													</fieldset>
												</div>
												<div class="col-lg-1">
													<fieldset class="form-group">
														<div class="checkbox">
															<input id="extraLiquidActive" type="checkbox" name="extraLiquidActive" class="">
															<label for="extraLiquidActive">Extra Liquid</label>
														</div>
													</fieldset>
												</div>
												<div class="col-lg-1">
													<fieldset class="form-group">
														<div class="checkbox">
															<input id="nbmActive" type="checkbox" name="nbmActive" class="">
															<label for="nbmActive">NBM</label>
														</div>
													</fieldset>
												</div>												
												<div class="col-lg-1">
												</div>							
												<div class="col-lg-2">
													<button type="button" class="btn btn-success waves-effect waves-light btn-block refreshTableActive">Search</button>
												</div>							
											</div>
										</form>											
										<div class="table-responsive">
											<table id="active-patients-table" class="table table-striped">
												<thead>
													<tr>
														<th>Patient Name</th>
														<th>Umr No.</th>
														<th>IP number</th>
														<th>Admission Date</th>
														<th>Ward/Floor/Bed Cd</th>
														<th>Doctor Name</th>
														<th>Diet Type/Qty/Frequency</th>
														<th>Co-morbities</th>
														<th class="specialNotesbyNurising">Special Notes by Nurising</th>
														<th>Action</th>
													</tr>
												</thead>
											</table>
										</div>
									</div>
								</div>
								<div class="tab-pane p-20" id="Discharged-Patients" role="tabpanel">
									<div class="pad-20">
										<form action="" id="searchFormDischarged">
											<div class="row">
												<div class="col-lg-6">
													<fieldset class="form-group">
														<input type="text" id="searchTextDischarged" class="form-control" placeholder="Search Patient name,Umr No., IP Number and Doctor Name">
													</fieldset>
												</div>
												<div class="col-lg-2">
													<fieldset class="form-group">
														<select class="form-control selectpicker" id="medicalComorbiditiesIdsDischarged" name="medicalComorbiditiesIdsDischarged" multiple data-live-search="true" data-size="10" title="Co-morbidities">
															<c:forEach items="${searchMedicalComorbiditiesList}" var="medicalComorbidities">
																<option value="${medicalComorbidities.medicalComorbiditiesId}">${medicalComorbidities.value}</option>
															</c:forEach>
														</select>
													</fieldset>
												</div>
												<div class="col-lg-2">
													<fieldset class="form-group">
														<select class="form-control selectpicker" id="floorIdsDischarged" name="floorIdsDischarged" multiple data-live-search="true" data-size="10" title="Floor">
															<c:forEach items="${searchFloorList}" var="floor">
																<option value="${floor.floorId}">${floor.floorName}</option>
															</c:forEach>
														</select>
													</fieldset>
												</div>
												<div class="col-lg-2">
													<fieldset class="form-group">
														<select class="form-control selectpicker" id="bedIdsDischarged" name="bedIdsDischarged" multiple data-live-search="true" data-size="10" title="Bed">
															<c:forEach items="${searchBedList}" var="bed">
																<option value="${bed.bedId}">${bed.bedCode}</option>
															</c:forEach>
														</select>
													</fieldset>
												</div>
											</div>
											<div class="row">
												<div class="col-lg-2">
													<fieldset class="form-group">
														<select class="form-control selectpicker" id="dietTypeOralSolidIdsDischarged" name="dietTypeOralSolidIdsDischarged" multiple data-live-search="true" data-size="10" title="Diet Type- Oral Solid">
															<c:forEach items="${searchDietTypeOralSolidList}" var="dietTypeOralSolid">
																<option value="${dietTypeOralSolid.dietTypeOralSolidId}">${dietTypeOralSolid.value}</option>
															</c:forEach>
														</select>
													</fieldset>
												</div>
												<div class="col-lg-2">
													<fieldset class="form-group">
														<select class="form-control selectpicker" id="dietTypeOralLiquidTFIdsDischarged" name="dietTypeOralLiquidTFIdsDischarged" multiple data-live-search="true" data-size="10" title="Diet Type- Oral Liquid/TF">
															<c:forEach items="${searchDietTypeOralLiquidTFList}" var="dietTypeOralLiquidTF">
																<option value="${dietTypeOralLiquidTF.dietTypeOralLiquidTFId}">${dietTypeOralLiquidTF.value}</option>
															</c:forEach>
														</select>
													</fieldset>
												</div>
												<div class="col-lg-2">
													<fieldset class="form-group">
														<select class="form-control selectpicker" id="dietSubTypeIdsDischarged" name="dietSubTypeIdsDischarged" multiple data-live-search="true" data-size="10" title="Diet Sub Type">
															<c:forEach items="${searchDietSubTypeList}" var="dietSubType">
																<option value="${dietSubType.dietSubTypeId}" class="dietSubType_options">${dietSubType.value}</option>
															</c:forEach>
														</select>
													</fieldset>
												</div>
												<div class="col-lg-1">
													<fieldset class="form-group">
														<div class="checkbox">
															<input id="isVipDischarged" type="checkbox" name="isVipDischarged" class="">
															<label for="isVipDischarged">Is VIP</label>
														</div>
													</fieldset>
												</div>
												<div class="col-lg-1">
													<fieldset class="form-group">
														<div class="checkbox">
															<input id="extraLiquidDischarged" type="checkbox" name="extraLiquidDischarged" class="">
															<label for="extraLiquidDischarged">Extra Liquid</label>
														</div>
													</fieldset>
												</div>
												<div class="col-lg-1">
													<fieldset class="form-group">
														<div class="checkbox">
															<input id="nbmDischarged" type="checkbox" name="nbmDischarged" class="">
															<label for="nbmDischarged">NBM</label>
														</div>
													</fieldset>
												</div>												
												<div class="col-lg-1">
												</div>							
												<div class="col-lg-2">
													<button type="button" class="btn btn-success waves-effect waves-light btn-block refreshTableDischarged">Search</button>
												</div>							
											</div>
										</form>									
										<div class="table-responsive">
											<table id="discharged-patients-table" class="table table-striped">
												<thead>
													<tr>
														<th>Patient Name</th>
														<th>Umr No.</th>
														<th>IP number</th>
														<th>Admission Date</th>
														<th>Discharge Date</th>
														<th>Ward/Floor/Bed Cd</th>
														<th>Doctor Name</th>
														<th>Diet Type/Qty/Frequency</th>
														<th>Co-morbities</th>
														<th>Special Notes by Nurising</th>
														<th>Action</th>
													</tr>
												</thead>
											</table>
										</div>
									</div>
								</div>
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
		<script type="text/javascript">
			$("#Patients").addClass("active");
		</script>  
		<!-- DataTable --> 
		<script src="${contextPath}/resources/dist/plugins/datatables/jquery.dataTables.min.js"></script> 
		<script src="${contextPath}/resources/dist/plugins/datatables/dataTables.bootstrap.min.js"></script> 
		<!-- bootstrap-switch --> 
		<script src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>
		<!-- SweetAlert2 -->
		<script src="${contextPath}/resources/dist/plugins/sweetalert2/sweetalert2.min.js"></script>	
		<script>
		$(document).ready(function() {
	    	$('.selectpicker').selectpicker(); 
		    var newPatientsTable = $('#new-patients-table').DataTable({
		        "ajax": {
		            'contentType': 'application/json',
		            'url': contextPath + "/diet/patient-data?patientStatus=0",
		            'method': "POST",
		            'data': function(d) {
	           	         return JSON.stringify($.extend( {}, d, { 
					          "searchText": "",
			           	      "medicalComorbiditiesIds": [],
		           	      	  "floorIds": [],
		           	     	  "bedIds": [],
		           	      	  "dietTypeOralSolidIds": [],
		           	      	  "dietTypeOralLiquidTFIds": [],
		           	       	  "dietSubTypeIds": [],
		           	      	  "isVip": false,
			           	      "extraLiquid": false,
			           	      "nbm": false
					         }))
		            },
		            'dataSrc': function(json) {
		                $("#New-Patients-Count").text(json.count);
		                json.draw = json.data.draw;
		                json.recordsTotal = json.count;
		                json.recordsFiltered = json.data.recordsFiltered;
		                return json.data.data;
		            }
		        },
		        "columns": [{
		            "data": "patientName"
		        }, {
		            "data": "umrNumber"
		        }, {
		            "data": "ipNumber"
		        }, {
		            "data": "admittedDate",
		            "defaultContent": "-"

		        }, {
		            "data": "bedString",
		            "defaultContent": "-",
		            "orderable": false,
		            "searchable": false
		        }, {
		            "data": "doctor",
		            "defaultContent": "-"
		        }, {
		            "orderable": false,
		            "searchable": false,
		            "data": "",
// 		            "defaultContent": '<i class="fa fa-edit fa-lg" title="Edit"></i>'
	            	"render": function(data, type, row) {
        				var action = '<i class="fa fa-edit fa-lg" title="Edit"></i>';
        				if (isDietitian || isAdmin) {
            				action += '&nbsp;&nbsp;<i class="fa fa-trash-o fa-lg" title="Delete"></i>';
        				}
        				return action;
        			}
		        }],
		        'paging': true,
		        'ordering': true,
		        'info': true,
		        'autoWidth': false,
		        "serverSide": true,
		        "pageLength": 25,
		        "fnDrawCallback": function(data) {
		        }
		    });

		    var activePatientsTable = $('#active-patients-table').DataTable({
		        "ajax": {
		            'contentType': 'application/json',
		            'url': contextPath + "/diet/patient-data?patientStatus=1",
		            'method': "POST",
		            'data': function(d) {
	           	         return JSON.stringify($.extend( {}, d, { 
					          "searchText": getValue("searchTextActive"),
			           	      "medicalComorbiditiesIds": getValue("medicalComorbiditiesIdsActive"),
		           	      	  "floorIds": getValue("floorIdsActive"),
		           	     	  "bedIds": getValue("bedIdsActive"),
		           	      	  "dietTypeOralSolidIds": getValue("dietTypeOralSolidIdsActive"),
		           	      	  "dietTypeOralLiquidTFIds": getValue("dietTypeOralLiquidTFIdsActive"),
		           	       	  "dietSubTypeIds": getValue("dietSubTypeIdsActive"),
		           	      	  "isVip": getCheckboxValue("isVipActive"),
			           	      "extraLiquid": getCheckboxValue("extraLiquidActive"),
			           	      "nbm": getCheckboxValue("nbmActive")
					         }))
		            },
		            'dataSrc': function(json) {
		                $("#Active-Patients-Count").text(json.count);
		                json.draw = json.data.draw;
		                json.recordsTotal = json.count;
		                json.recordsFiltered = json.data.recordsFiltered;
		                return json.data.data;
		            }
		        },
		        "columns": [{
		            "data": "patientName",
		            "render": function(data, type, row) {
		                if (type === "sort" || type === "filter" || type === 'type') {
		                    return (data);
		                } else {
		                    var text = '';
		                    if (row["isVip"]) {
		                        text = '<img height="23px" width="23px" src="${contextPath}/resources/dist/img/icons8-star-48.png" alt="">';
		                    }
		                    text += data;
		                    return text;
		                }
		            }
		        }, {
		            "data": "umrNumber"
		        }, {
		            "data": "ipNumber"
		        }, {
		            "data": "admittedDate",
		            "defaultContent": "-"
		        }, {
		            "data": "bedString",
		            "defaultContent": "-",
		            "orderable": false,
		            "searchable": false
		        }, {
		            "data": "doctor",
		            "defaultContent": "-"
		        }, {
		            "data": "dietTypeSolidLiquidQuantityFrequencyString",
		            "defaultContent": "-",
		            "orderable": false,
		            "searchable": false
		        }, {
		            "data": "medicalComorbiditiesString",
		            "defaultContent": "-",
		            "orderable": false,
		            "searchable": false
		        }, {
		            "data": "specialNotesByNursingString",
		            "className": "specialNotesbyNurising",
		            "defaultContent": "-",
		            "orderable": false,
		            "searchable": false,
		            "render": function(data, type, row) {
		                if (type === "sort" || type === "filter" || type === 'type') {
		                    return (data);
		                } else {
		                    var aTag = '<a href="#" style="color: #666f73;" title = "' + data + '">' + data + '</a>';
		                    if (data) {
		                    	if (data.length > 50) {
		                    		aTag = '<a href="#" style="color: #666f73;" title = "' + data + '">' + data.substring(0, 50) + '...</a>';
		                    	}
		                    } else {
		                    	aTag = "-";
		                    }
		                    return aTag;
		                }
		            }
		        }, {
		            "orderable": false,
		            "searchable": false,
		            "data": "",
// 		            "defaultContent": '<i class="fa fa-edit fa-lg" title="Edit"></i>&nbsp;&nbsp;<i class="fa fa-list-alt fa-lg" title="Diet Instruction"></i>&nbsp;&nbsp;<i class="fa fa-cart-plus fa-lg" title="AdHoc Order"></i>&nbsp;&nbsp;<i class="fa fa-tags fa-lg" title="Sticker"></i>'
        			"render": function(data, type, row) {
        				var action = '<i class="fa fa-edit fa-lg" title="Edit"></i>&nbsp;&nbsp;<i class="fa fa-list-alt fa-lg" title="Diet Instruction"></i>&nbsp;&nbsp;<i class="fa fa-cart-plus fa-lg" title="AdHoc Order"></i>';
        				if (isDietitian || isKitchen || isAdmin) {
            				action += '&nbsp;&nbsp;<i class="fa fa-tags fa-lg" title="Sticker"></i>';
        				}
        				if (isDietitian || isAdmin) {
            				action += '&nbsp;&nbsp;<i class="fa fa-trash-o fa-lg" title="Delete"></i>';
        				}
        				return action;
        			}
		        }],
		        "createdRow": function(row, data, dataIndex) {
		            if (data['showUpdated']) {
		                $(row).addClass('showUpdated');
		            }
		        },
		        'paging': true,
		        'ordering': true,
		        'info': true,
		        'autoWidth': false,
		        "serverSide": true,
		        "pageLength": 25,
		        "fnDrawCallback": function() {
		        	
		        }
		    });

// 		    var filters = '<div class="pull-left">';
// 		    filters += '<input id="nbm" type="checkbox" class="refreshTable"><label for="nbm">&nbsp;NBM</label>';
// 		    filters += '<input id="extraLiquid" type="checkbox" class="refreshTable"><label for="extraLiquid">&nbsp;Extra Liquid</label>';
// // 		    filters += '<input id="startServiceImmediately" type="checkbox" class="refreshTable"><label for="startServiceImmediately">&nbsp;Start Service Immediately</label>'
// 		    filters += '<input id="isVip" type="checkbox" class="refreshTable"><label for="isVip">&nbsp;Is VIP</label>';
// 		    filters += '</div>';
// // 		    $('#active-patients-table_filter').html(filters + $('#active-patients-table_filter').html());
// 			$(filters).insertBefore($("#active-patients-table_filter").find("label"));
			$('#active-patients-table_filter').html("");
// 		    $(".refreshTable").bind("click", function() {
// 		        activePatientsTable.ajax.url(contextPath + "/diet/patient-data?patientStatus=1&nbm=" + getCheckboxValue("nbm") + "&extraLiquid=" + getCheckboxValue("extraLiquid") + "&startServiceImmediately=" + getCheckboxValue("startServiceImmediately") + "&isVip=" + getCheckboxValue("isVip"));
// 		        activePatientsTable.ajax.reload();
// 		    });

		    function getCheckboxValue(id) {
		        return $('#' + id).is(":checked");
		    }

		    function getValue(id) {
		    	var value = $('#' + id).val();
		        return (value != "null" && value != "undefined"  && value != undefined) ? value : "";
		    }

		    var dischargedPatientsTable = $('#discharged-patients-table').DataTable({
		        "ajax": {
		            'contentType': 'application/json',
		            'url': contextPath + "/diet/patient-data?patientStatus=2",
		            'method': "POST",
		            'data': function(d) {
	           	         return JSON.stringify($.extend( {}, d, { 
					          "searchText": getValue("searchTextDischarged"),
			           	      "medicalComorbiditiesIds": getValue("medicalComorbiditiesIdsDischarged"),
		           	      	  "floorIds": getValue("floorIdsDischarged"),
		           	     	  "bedIds": getValue("bedIdsDischarged"),
		           	      	  "dietTypeOralSolidIds": getValue("dietTypeOralSolidIdsDischarged"),
		           	      	  "dietTypeOralLiquidTFIds": getValue("dietTypeOralLiquidTFIdsDischarged"),
		           	       	  "dietSubTypeIds": getValue("dietSubTypeIdsDischarged"),
		           	      	  "isVip": getCheckboxValue("isVipDischarged"),
			           	      "extraLiquid": getCheckboxValue("extraLiquidDischarged"),
			           	      "nbm": getCheckboxValue("nbmDischarged")
					         }))
		            },
		            'dataSrc': function(json) {
		                $("#Discharged-Patients-Count").text(json.count);
		                json.draw = json.data.draw;
		                json.recordsTotal = json.count;
		                json.recordsFiltered = json.data.recordsFiltered;
		                return json.data.data;
		            }
		        },
		        "columns": [{
		            "data": "patientName",
		            "render": function(data, type, row) {
		                if (type === "sort" || type === "filter" || type === 'type') {
		                    return (data);
		                } else {
		                    var text = '';
		                    if (row["isVip"]) {
		                        text = '<img height="23px" width="23px" src="${contextPath}/resources/dist/img/icons8-star-48.png" alt="">';
		                    }
		                    text += data;
		                    return text;
		                }
		            }		            
		        }, {
		            "data": "umrNumber"
		        }, {
		            "data": "ipNumber"
		        }, {
		            "data": "admittedDate",
		            "defaultContent": "-"
		        }, {
		            "data": "dischargedTime",
		            "defaultContent": "-"
		        }, {
		            "data": "bedString",
		            "defaultContent": "-",
		            "orderable": false,
		            "searchable": false
		        }, {
		            "data": "doctor",
		            "defaultContent": "-"
		        }, {
		            "data": "dietTypeSolidLiquidQuantityFrequencyString",
		            "defaultContent": "-",
		            "orderable": false,
		            "searchable": false
		        }, {
		            "data": "medicalComorbiditiesString",
		            "defaultContent": "-",
		            "orderable": false,
		            "searchable": false
		        }, {
		            "data": "specialNotesByNursingString",
		            "defaultContent": "-",
		            "orderable": false,
		            "searchable": false,
		            "render": function(data, type, row) {
		                if (type === "sort" || type === "filter" || type === 'type') {
		                    return (data);
		                } else {
		                    var aTag = '<a href="#" style="color: #666f73;" title = "' + data + '">' + data + '</a>';
		                    if (data) {
		                    	if (data.length > 50) {
		                    		aTag = '<a href="#" style="color: #666f73;" title = "' + data + '">' + data.substring(0, 50) + '...</a>';
		                    	}
		                    } else {
		                    	aTag = "-";
		                    }
		                    return aTag;
		                }
		            }
		        }, {
		            "orderable": false,
		            "searchable": false,
		            "data": "",
		            "defaultContent": '<i class="fa fa-edit fa-lg" title="Edit"></i>&nbsp;&nbsp;<i class="fa fa-list-alt fa-lg" title="Diet Instruction"></i>&nbsp;&nbsp;<i class="fa fa-cart-plus fa-lg" title="AdHoc Order"></i>'

		        }],
		        'paging': true,
		        'ordering': true,
		        'info': true,
		        'autoWidth': false,
		        "serverSide": true,
		        "pageLength": 25,
		        "fnDrawCallback": function() {
		        }
		    });

			$('#discharged-patients-table_filter').html("");
			
		    $('#new-patients-table').on('click', 'tbody .fa-edit', function() {
		        var data_row = newPatientsTable.row($(this).closest('tr')).data();
		        window.location = contextPath + "/diet/patient-details?patientId=" + data_row["patientId"];
		    })

		    $('#active-patients-table').on('click', 'tbody .fa-edit', function() {
		        var data_row = activePatientsTable.row($(this).closest('tr')).data();
		        window.location = contextPath + "/diet/patient-details?patientId=" + data_row["patientId"];
		    })

		    $('#active-patients-table').on('click', 'tbody .fa-list-alt', function() {
		        var data_row = activePatientsTable.row($(this).closest('tr')).data();
		        window.location = contextPath + "/diet/diet-instruction?patientId=" + data_row["patientId"];
		    })
		    
		    $('#active-patients-table').on('click', 'tbody .fa-cart-plus', function() {
		        var data_row = activePatientsTable.row($(this).closest('tr')).data();
		        window.location = contextPath + "/diet/adhoc-order?patientId=" + data_row["patientId"];
		    })
		    
		    $('#active-patients-table').on('click', 'tbody .fa-tags', function() {
		        var data_row = activePatientsTable.row($(this).closest('tr')).data();
		        window.location = contextPath + "/diet/stickers?patientId=" + data_row["patientId"];
		    })
		    
		    $('#discharged-patients-table').on('click', 'tbody .fa-edit', function() {
		        var data_row = dischargedPatientsTable.row($(this).closest('tr')).data();
		        window.location = contextPath + "/diet/patient-details?patientId=" + data_row["patientId"];
		    })

		    $('#discharged-patients-table').on('click', 'tbody .fa-list-alt', function() {
		        var data_row = dischargedPatientsTable.row($(this).closest('tr')).data();
		        window.location = contextPath + "/diet/diet-instruction?patientId=" + data_row["patientId"];
		    })
		    
		    $('#discharged-patients-table').on('click', 'tbody .fa-cart-plus', function() {
		        var data_row = dischargedPatientsTable.row($(this).closest('tr')).data();
		        window.location = contextPath + "/diet/adhoc-order?patientId=" + data_row["patientId"];
		    })		    
		    
		    $("#Export-PDF").bind("click", function() {
		        var activeTab = $(".customtab").find(".active").attr("href");
		        var patientStatus = 0;
		        var searchText = "";
		        var checkboxSearch = "&nbm=" + getCheckboxValue("nbm") + "&extraLiquid=" + getCheckboxValue("extraLiquid") + "&startServiceImmediately=" + getCheckboxValue("startServiceImmediately") + "&isVip=" + getCheckboxValue("isVip");
		        var orderColumn = "";
		        var direction = "";
		        if (activeTab == "#New-Patients") {
		            patientStatus = 0;
		            searchText = $("#new-patients-table_filter").find("input[type=search]").val();
		            var order = newPatientsTable.order();
		            orderColumn = newPatientsTable.settings().init().columns[order[0][0]]['data'];
		            direction = order[0][1] == "asc" ? true : false;
		        } else if (activeTab == "#Active-Patients") {
		            patientStatus = 1;
		            searchText = $("#active-patients-table_filter").find("input[type=search]").val();
		            searchText += checkboxSearch;
		            var order = activePatientsTable.order();
		            orderColumn = activePatientsTable.settings().init().columns[order[0][0]]['data'];
		            direction = order[0][1] == "asc" ? true : false;
		        } else if (activeTab == "#Discharged-Patients") {
		            patientStatus = 2;
		            searchText = $("#discharged-patients-table_filter").find("input[type=search]").val();
		            var order = dischargedPatientsTable.order();
		            orderColumn = dischargedPatientsTable.settings().init().columns[order[0][0]]['data'];
		            direction = order[0][1] == "asc" ? true : false;
		        }
		        window.location = contextPath + "/diet/export/pdf/patient-details?patientStatus=" + patientStatus + "&searchText=" + searchText + "&orderColumn=" + orderColumn + "&direction=" + direction;
		    });

		    $("#Export-Excel").bind("click", function() {
		        var activeTab = $(".customtab").find(".active").attr("href");
		        var patientStatus = 0;
		        var searchText = "";
		        var checkboxSearch = "&nbm=" + getCheckboxValue("nbm") + "&extraLiquid=" + getCheckboxValue("extraLiquid") + "&startServiceImmediately=" + getCheckboxValue("startServiceImmediately") + "&isVip=" + getCheckboxValue("isVip");
		        var orderColumn = "";
		        var direction = "";
		        if (activeTab == "#New-Patients") {
		            patientStatus = 0;
		            searchText = $("#new-patients-table_filter").find("input[type=search]").val();
		            var order = newPatientsTable.order();
		            orderColumn = newPatientsTable.settings().init().columns[order[0][0]]['data'];
		            direction = order[0][1] == "asc" ? true : false;
		        } else if (activeTab == "#Active-Patients") {
		            patientStatus = 1;
		            searchText = $("#active-patients-table_filter").find("input[type=search]").val();
		            searchText += checkboxSearch;
		            var order = activePatientsTable.order();
		            orderColumn = activePatientsTable.settings().init().columns[order[0][0]]['data'];
		            direction = order[0][1] == "asc" ? true : false;
		        } else if (activeTab == "#Discharged-Patients") {
		            patientStatus = 2;
		            searchText = $("#discharged-patients-table_filter").find("input[type=search]").val();
		            var order = dischargedPatientsTable.order();
		            orderColumn = dischargedPatientsTable.settings().init().columns[order[0][0]]['data'];
		            direction = order[0][1] == "asc" ? true : false;
		        }
		        window.location = contextPath + "/diet/export/excel/patient-details?patientStatus=" + patientStatus + "&searchText=" + searchText + "&orderColumn=" + orderColumn + "&direction=" + direction;
		    });
		    
        	$('#new-patients-table').on('click', 'tbody .fa-trash-o', function(e) {
		        var data_row = newPatientsTable.row($(this).closest('tr')).data();
        		var patientId = data_row["patientId"];
        		var title_Text = "Deleted patient cannot be retrieved. Are you sure you want to delete the patient? <br>" + data_row["patientName"];
        		var confirmButtonText_Text = "Yes, delete it!";
        		Swal.fire({
        			title: title_Text,	
        			icon: 'warning',
        			showCancelButton: true,
        			confirmButtonColor: '#3085d6',
        			cancelButtonColor: '#d33',
        			confirmButtonText: confirmButtonText_Text
        		}).then((result) => {
        			if (result.isConfirmed) {
        				var formData = "patientId=" + patientId;
        				$.ajax({
        					url: contextPath + "/diet/delete-patient",
        					type: "DELETE",
        					data: formData,
        					success: function(data) {
        						if (data == "Patient has been deleted") {
            						Swal.fire({
            							icon: 'success',
            							title: "Patient has been deleted",
            							showConfirmButton: false,
            							timer: 1500
            						}).then((result) => {
            							newPatientsTable.ajax.reload();
            						})
        						} else {
            						Swal.fire({
            							icon: 'warning',
            							title: "The patient can not be deleted",
            							showConfirmButton: false,
            							timer: 1500
            						})
        						}        						
        					},
        					error: function() {
        						Swal.fire({
        							icon: 'warning',
        							title: "Something went wrong",
        							showConfirmButton: false,
        							timer: 1500
        						})
        					}
        				});

        			}
        		});
        	});
        	
        	$('#active-patients-table').on('click', 'tbody .fa-trash-o', function(e) {
		        var data_row = activePatientsTable.row($(this).closest('tr')).data();
        		var patientId = data_row["patientId"];
        		var title_Text = "Deleted patient cannot be retrieved. Are you sure you want to delete the patient? <br>" + data_row["patientName"];
        		var confirmButtonText_Text = "Yes, delete it!";
        		Swal.fire({
        			title: title_Text,	
        			icon: 'warning',
        			showCancelButton: true,
        			confirmButtonColor: '#3085d6',
        			cancelButtonColor: '#d33',
        			confirmButtonText: confirmButtonText_Text
        		}).then((result) => {
        			if (result.isConfirmed) {
        				var formData = "patientId=" + patientId;
        				$.ajax({
        					url: contextPath + "/diet/delete-patient",
        					type: "DELETE",
        					data: formData,
        					success: function(data) {
        						if (data == "Patient has been deleted") {
            						Swal.fire({
            							icon: 'success',
            							title: "Patient has been deleted",
            							showConfirmButton: false,
            							timer: 1500
            						}).then((result) => {
            							activePatientsTable.ajax.reload();
            						})
        						} else {
            						Swal.fire({
            							icon: 'warning',
            							title: "The patient can not be deleted",
            							showConfirmButton: false,
            							timer: 1500
            						})
        						}        						
        					},
        					error: function() {
        						Swal.fire({
        							icon: 'warning',
        							title: "Something went wrong",
        							showConfirmButton: false,
        							timer: 1500
        						})
        					}
        				});

        			}
        		});
        	});
        	
		    $(".refreshTableActive").bind("click", function() {
		    	activePatientsTable.ajax.reload();
		    });
		    
		    $(".refreshTableDischarged").bind("click", function() {
		    	dischargedPatientsTable.ajax.reload();
		    });
		    
		    function refreshPatientsTable() {
		    	newPatientsTable.ajax.reload();
		    	activePatientsTable.ajax.reload();
		    	dischargedPatientsTable.ajax.reload();
		    }
		    window.setInterval(refreshPatientsTable, setIntervalTime); 
		});
		</script>  
	</body>
</html>