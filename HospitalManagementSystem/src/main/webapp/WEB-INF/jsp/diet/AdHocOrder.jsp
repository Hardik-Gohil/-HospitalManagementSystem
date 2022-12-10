<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>AdHoc Oder</title>
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
					<h1>AdHoc Oder</h1>
				</div>
				<!-- Main content -->
				<div class="content">
					<div class="card">
						<c:if test="${not empty errorMsg}">					
							<div class="alert alert-danger alert-dismissible fade show" role="alert">
								${errorMsg}
								<button type="button" class="close" data-dismiss="alert" aria-label="Close">
									<span aria-hidden="true">X</span>
								</button>
							</div>
						</c:if>						
						<div class="card-body">
							<%@include file="IncludePatientDetails.jsp"%>
							<Br>
							<form:form method="POST" action="${contextPath}/diet/adhoc-order" modelAttribute="adHocOrderDto" id="adHocOrderForm">
								<form:hidden path="patient.patientId" id="patientId"/>
								<div class="row">
									<div class="col-lg-12">
										<fieldset class="form-group">
											<label for="serviceType">Service Type:</label><span class="text-danger">*</span>
											<div class="radio">
											<label><input type="radio" name="serviceType" id="serviceType1" value="1" <c:if test="${patient.nbm}">disabled="disabled"</c:if>> Immediate Service </label>
											<label><input type="radio" name="serviceType" id="serviceType2" value="2"> AdHoc Service </label>
											</div>
										</fieldset>	
									</div>
								</div>
								<div class="row" id="Items_row">
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="itemsSelection">Items:</label><span class="text-danger">*</span>
											<select class="form-control selectpicker" id="itemsSelection" name="itemsSelection" data-live-search="true" data-size="10">
												<c:forEach items="${adHocItemsList}" var="adHocItems">
													<option value="${adHocItems.adHocItemsId}">${adHocItems.itemName}</option>
												</c:forEach>
											</select>
										</fieldset>	
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="quantitySelection">Quantity:</label><span class="text-danger">*</span>
											<select class="form-control selectpicker" id="quantitySelection" name="quantitySelection" data-live-search="true" data-size="10">
												<c:forEach begin="1" end="4" varStatus="loop">
													<option value="${loop.index}">${loop.index}</option>
												</c:forEach>
											</select>
										</fieldset>	
									</div>		
									<div class="col-lg-4">
										<fieldset class="form-group">
											<button type="button" class="btn btn-outline-primary" style="margin-top: 1.8rem!important;" onclick="addItem();"><i class="fa fa-plus"></i>&nbsp;ADD&nbsp;</button>
										</fieldset>	
									</div>			
									<div class="col-lg-8">
				                        <table class="table table-bordered table-striped table-sm">
				                           <thead>
				                              <tr>
				                                 <th width="50%" class="text-center">Items</th>
				                                 <th width="40%" class="text-center">Quantity</th>
				                                 <th width="10%" class="text-center">Action</th>
				                              </tr>
				                           </thead>
				                           <tbody id="itemTable">
				                           </tbody>
				                        </table>									
									</div>																							
								</div>								
								<div class="row">
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="serviceDeliveryDateTime">Service Delivery date and Time</label><span class="text-danger">*</span>
											<form:input cssClass="form-control daterange-single" id="serviceDeliveryDateTime" path="serviceDeliveryDateTime"></form:input>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="remarks">Remarks</label>
											<input class="form-control daterange-single" id="remarks" name="remarks"></input>
										</fieldset>
									</div>
									<div class="col-lg-4" id="dateSelection_div">
										<fieldset class="form-group">
											<label for="nursingName">Nursing Name</label><span class="text-danger">*</span>
											<input class="form-control" id="nursingName" name="nursingName"></input>
										</fieldset>
									</div>
								</div>
								<c:if test="${patient.patientStatus ne 2}">
									<c:if test="${isNursing || isDietitian || isAdmin}">
										<button type="submit" class="submitBtn btn btn-success waves-effect waves-light">Submit</button>
									</c:if>
									<a href="${contextPath}/diet/adhoc-order?patientId=${adHocOrderDto.patient.patientId}">
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
		                        <table id="adhoc-order-table" class="table table-bordered table-striped">
		                           <thead>
		                              <tr>
		                                 <th>Order ID</th>
		                                 <th>Service Type</th>
		                                 <th>Item (Quantity)</th>
		                                 <th>Order Placed on</th>
		                                 <th>Delivery Time</th>
		                                 <th>Status</th>
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

		jQuery.validator.addMethod("validateDate", function(value, element) {
	        if ($('#serviceDeliveryDateTime').data('daterangepicker').startDate.format(dateFormat) < moment().add(30, 'minutes').format(dateFormat)) {
	            return false;
	        }		    
		    return true;
		});
		
		</script>  
		<script type="text/javascript">
		var dateFormat = "MM/DD/YYYY h:mm:ss a";
	    $("input[name=serviceType]:radio").change(function () {
	    	serviceTypeChange();
	    })
		
		function serviceTypeChange() {
		    if ($('input[name="serviceType"]:checked').val() == 2) {
		    	$("#Items_row").show();
		    } else {
		        $("#Items_row").hide();
		    }
		}

		function Validation() {
		    if ($('input[name="serviceType"]:checked').val() == 2  && $('#itemTable tr').length == 0) {
		    	Swal.fire('Pleae add Items');
		    	return false;
		    }
		    return true;
		}
		
		function addItem() {
			var itemTr = '<tr>';
				itemTr += '<td class="">{adHocItems}<input type="hidden" name="adHocItemsIds" value="{adHocItemsId}"></input></td>';
				itemTr += '<td class="">{quantity}<input type="hidden" name="quantities" value="{quantity}"></input></td>';
				itemTr += '<td class="text-center"><i class="fa fa-trash-o fa-lg" title="Delete"></i></td>';
				itemTr += '</tr>'
			var adHocItems = $("#itemsSelection option:selected").text();
			var adHocItemsId = $("#itemsSelection").val();
			var quantity = $("#quantitySelection").val();
			var appendHtml = itemTr.replace("{adHocItems}", adHocItems).replace("{adHocItemsId}", adHocItemsId).replace("{quantity}", quantity).replace("{quantity}", quantity);
			$("#itemTable").append(appendHtml);			
		}
		
		$(document).ready(function() {
		    $("#Patients").addClass("active");
		    $('.selectpicker').selectpicker(); 
		    
		    $("#itemTable").on('click','.fa-trash-o', function() {
		        $(this).parent().parent().remove();
		    });
		    
		    $('#serviceDeliveryDateTime').daterangepicker({
		        alwaysShowCalendars: true,
		        singleDatePicker: true,
		        timePicker: true,
		        locale: {
		            format: dateFormat
		        },
	        "isInvalidDate" : function(date) {
	        	    if (date.format(dateFormat) < moment().add(30, 'minutes').format(dateFormat)){
	        	      return true;
	        	    }
	        	}
		    });
		    
		    $('#serviceDeliveryDateTime').data('daterangepicker').setStartDate(moment().add(35, 'minutes').format(dateFormat));
		    <c:if test="${immediateService eq true}">
		  		$("input[name=serviceType][value='1']").prop('checked', true);
		  		$("#serviceType2").attr("disabled", true);
   			</c:if> 		    
	    	<c:if test="${immediateService eq false}">
	    		$("input[name=serviceType][value='2']").prop('checked', true);
			</c:if> 		    
		    serviceTypeChange();
	    	<c:if test="${empty adHocOrderDto.adHocOrderId or adHocOrderDto.adHocOrderId eq 0}">
    			$("#nursingName").val('${patient.nursingName}');
			</c:if> 		    

		    $("#adHocOrderForm").validate({
		        // in 'rules' user have to specify all the constraints for respective fields
		        rules: {
		        	serviceDeliveryDateTime: {
		                required: true,
		                validateDate: true
		            },
		            remarks: {
		                minlength: 2,
		                maxlength: 150,
		                alphanumericWithSpeCharValidator: true
		            },
		            nursingName: {
		                required: true,
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
		        	serviceDeliveryDateTime: {
		                required: "Please enter Service Delivery Date Time",
		                validateDate: "Must be 30 or minutes more than the current Date Time"
		            },
		            remarks: {
		                minlength: "At least 2 characters required",
		                maxlength: "Max 150 characters allowed",
		                alphanumericWithSpeCharValidator: "Only Alphanumeric characters and " + allowsChars + " are allowed"
		            },
		            nursingName: {
		                required: "Please enter Nursing Name",
			            minlength: "At least 2 characters required",
			            maxlength: "Max 150 characters allowed",
			            alphanumericWithSpeCharValidator: "Only Alphanumeric characters and " + allowsChars + " are allowed"		                
		            }
		        },
		        submitHandler: function(form) { // <- pass 'form' argument in
		        	if (Validation()) {
			            $(".submitBtn").attr("disabled", "disabled");
			            form.submit(); // <- use 'form' argument here.
		        	}
		        }
		    });
		    
        	var table = $('#adhoc-order-table').DataTable({
        		"ajax": {
        			'url': contextPath + "/diet/adhoc-order-data?patientId=${adHocOrderDto.patient.patientId}",
        			'method': "POST",
        			"dataSrc": ""
        		},
        		"columns": [{
        			"data": "orderId"
        		}, {
        			"data": "serviceType",
        			"render": function(data, type, row) {
        				return (data == "1" ? 'Immediate Service' : 'AdHoc Service');
        			}
        		}, {
        			"data": "adHocOrderItems",
        			"defaultContent": "-",
        			"render": function(data, type, row) {
        				var returnStr = "-";
        				if (Array.isArray(data)) {
        					returnStr = "";
	            	        for (let i = 0; i < data.length; i++) {
	            	            returnStr += data[i].adHocItems.itemName + " (" + data[i].quantity+ ")";
	        	            	 if (i != data.length - 1) {
	        	            		 returnStr += "<Br>";
	        	            	 }
	            	        }
        				}
        				return returnStr;
        			}
        		}, {
        			"data": "createdOn",
        			"defaultContent": "-"
        		}, {
        			"data": "serviceDeliveryDateTime",
        			"defaultContent": "-"
        		}, {
        			"data": "orderStatus",
        			"render": function(data, type, row) {
        				return (data == "1" ? 'Active' : data == "2" ? 'Cancel' : 'Deleted');
        			}
        		}, {
        			"orderable": false,
        			"searchable": false,
        			"data": "",
        			"render": function(data, type, row) {
        				var action = '<i class="fa fa-file-invoice fa-lg" title="KOT"></i>';
        				if ((isNursing || isDietitian || isAdmin)) {
        					action += '&nbsp;&nbsp;<i class="fa fa-tags fa-lg" title="Sticker"></i>';
        				}
        				if ((isDietitian || isKitchen || isAdmin) && row['orderStatus'] == 1 && row['serviceDeliveryDateTime'] > moment().format(dateFormat)) {
        					action += '&nbsp;&nbsp;<i class="fa fa-trash-o fa-lg" title="Delete"></i>';
        				}
        				return action;
        			}
        		}],
		        "createdRow": function(row, data, dataIndex) {
		            if (data['serviceType'] == "1") {
		                $(row).addClass('showUpdated');
		            }
		        },        		
        		"order": [
        			[0, 'desc']
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
        	
            $('#adhoc-order-table').on('click', 'tbody .fa-file-invoice', function() {
                var data_row = table.row($(this).closest('tr')).data();
                window.open(contextPath + "/diet/adhoc-order-kot?adHocOrderId=" + data_row["adHocOrderId"], "_blank");
            })
            
            $('#adhoc-order-table').on('click', 'tbody .fa-tags', function() {
                var data_row = table.row($(this).closest('tr')).data();
                window.open(contextPath + "/diet/generate-adhoc-stickers?adHocOrderId=" + data_row["adHocOrderId"], "_blank");
            })            
        	
        	$('#adhoc-order-table').on('click', 'tbody .fa-trash-o', function(e, data) {
        		var data_row = table.row($(this).closest('tr')).data();
        		var orderId = data_row["orderId"];
        		var serviceDeliveryDateTime = data_row["serviceDeliveryDateTime"];
        		var adHocOrderId = data_row["adHocOrderId"];
        		var title_Text = "Are you sure, you want to Delete AdHoc Oder '" + orderId + "'?";
        		var confirmButtonText_Text = "Yes, Delete it!";
        		
    	        if (serviceDeliveryDateTime < moment().format(dateFormat)) {
    	        	Swal.fire('You can not Delete the Oder whose Service Delivery Date Time lapsed');
    	        	table.ajax.reload();
    	            return false;
    	        }	
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
        			    var adHocOrderIdElement= document.createElement("input"); 

        			    form.method = "POST";
        			    form.action = "/diet/delete-adhoc-order";   

        			    adHocOrderIdElement.value= adHocOrderId;
        			    adHocOrderIdElement.name = "adHocOrderId";
        			    form.appendChild(adHocOrderIdElement);  

        			    document.body.appendChild(form);
        			    form.submit();
        			}
        		});
        	})        	
		});
		</script>    	
	</body>
</html>