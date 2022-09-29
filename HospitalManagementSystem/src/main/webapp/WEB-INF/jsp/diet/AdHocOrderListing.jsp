<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <title>AdHoc Oders</title>
      <%@include file="../includes/HeadScript.jsp"%>
      <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
      <!-- DataTables -->
      <link rel="stylesheet" href="${contextPath}/resources/dist/plugins/datatables/css/dataTables.bootstrap.min.css">
      <!-- bootstrap-switch -->
      <link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
      <!-- SweetAlert2 -->
      <link rel="stylesheet" href="${contextPath}/resources/dist/plugins/sweetalert2-theme-bootstrap-4/bootstrap-4.min.css">
      <style type="text/css">
         .custom-control-label::before ,.custom-control-label::after{width:20px; height:20px}
		table.dataTable td.dt-control {
		    text-align: center;
		    cursor: pointer
		}
		
		table.dataTable td.dt-control:before {
		    height: 1em;
		    width: 1em;
		    margin-top: -9px;
		    display: inline-block;
		    color: white;
		    border: .15em solid white;
		    border-radius: 1em;
		    box-shadow: 0 0 .2em #444;
		    box-sizing: content-box;
		    text-align: center;
		    text-indent: 0 !important;
		    font-family: "Courier New",Courier,monospace;
		    line-height: 1em;
		    content: "+";
		    background-color: #31b131
		}
		
		table.dataTable tr.dt-hasChild td.dt-control:before {
		    content: "-";
		    background-color: #d33333
		}         
		
/* 		table.dataTable td, table.dataTable th { */
/* 		     padding-top: 5px !important; */
/* 		     padding-bottom: 5px !important; */
/* 		} */
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
               <h1>AdHoc Oders</h1>
               <c:if test="${isDietitian || isKitchen || isAdmin}">
					<div style="padding: 7px 5px;position: absolute;top: 11px;right: 10px;">
						<button id="Export-PDF" class="btn btn-outline-primary btn-sm" type="button"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;&nbsp;&nbsp;PDF</button>
						<button id="Export-Excel" class="btn btn-outline-primary btn-sm" type="button"><i class="fa fa-file-excel-o"></i>&nbsp;&nbsp;&nbsp;&nbsp;Excel</button>
					</div>      
			   </c:if>         
            </div>
            <!-- Main content -->
            <div class="content">
               <div class="card">              
                  <div class="card-body">
                     <div class="table-responsive">
                        <table id="adhoc-oder-table" class="table table-bordered table-striped">
                           <thead>
								<tr>
									<th>Order ID</th>
									<th>Patient name</th>
									<th>Service Type</th>
									<th>IP Number</th>
									<th>Diet Type Solid/Liquid orally/TF/Quantity/Frequency</th>
									<th>Ward/Floor/Bed Cd</th>
									<th>Co-mordibities Diagnosis-Item (Quantity)</th>
									<th>Doctor Name</th>
									<th>Nursing name</th>
									<th>Order Placed on</th>
									<th>Delivery Time</th>
									<th>Chargable?</th>
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
      <script type="text/javascript">
     	 $("#AdHocOder").addClass("active");
      </script>  
      <!-- DataTable --> 
      <script src="${contextPath}/resources/dist/plugins/datatables/jquery.dataTables.min.js"></script> 
      <script src="${contextPath}/resources/dist/plugins/datatables/dataTables.bootstrap.min.js"></script> 
      <!-- bootstrap-switch --> 
      <script src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>
      <!-- SweetAlert2 -->
      <script src="${contextPath}/resources/dist/plugins/sweetalert2/sweetalert2.min.js"></script>	
      
      <script src="${contextPath}/resources/dist/plugins/moment/min/moment.min.js"></script>
      <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
      <script>
      var dateFormat = "MM/DD/YYYY h:mm:ss a";
      $(document).ready(function() {
		    var adHocOderTable = $('#adhoc-oder-table').DataTable({
		        "ajax": {
		            'contentType': 'application/json',
		            'url': contextPath + "/diet/adhoc-order-listing-data",
		            'method': "POST",
		            'data': function(d) {
		                return JSON.stringify(d);
		            }
// 		    		,
// 		            'dataSrc': function(json) {
// 		                json.draw = json.data.draw;
// 		                json.recordsTotal = json.count;
// 		                json.recordsFiltered = json.data.recordsFiltered;
// 		                return json.data.data;
// 		            }
		        },
		        "columns": [{
		            "data": "orderId"
	            }, {
		            "data": "patient.patientName",
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
        			"data": "serviceType",
		            "orderable": false,
        			"render": function(data, type, row) {
        				return (data == "1" ? 'Immediate Service' : 'AdHoc Service');
        			}
        		}, {
		            "data": "patient.ipNumber"
		        }, {
		            "data": "patient.dietTypeSolidLiquidQuantityFrequencyString",
		            "orderable": false,
		            "searchable": false
		        }, {
		            "data": "patient.bedString",
		            "orderable": false,
		            "searchable": false
		        }, {
        			"data": "adHocOrderItems",
        			"defaultContent": "-",
		            "orderable": false,
		            "searchable": false,
        			"render": function(data, type, row) {
        				var returnStr = row['patient']['medicalComorbiditiesString'];
        				if (Array.isArray(data)) {
        					returnStr += "<Br>";
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
		            "data": "patient.doctor"
        		}, {
		            "data": "patient.nursingName"
		        }, {
        			"data": "createdOn",
        			"defaultContent": "-"
        		}, {
        			"data": "serviceDeliveryDateTime",
        			"defaultContent": "-"
		        }, {
        			"data": "chargable",
        			"render": function(data, type, row) {
        				if (type === "sort" || type === "filter" || type === 'type') {
        					return (data ? 'Yes' : 'No');
        				} else {
        					var checkbox = '<input type="checkbox" class="chargable_switch" ' + (data ? 'checked="checked"' : '') + ' data-toggle="toggle" data-size="sm" data-on="Yes" data-off="No" data-onstyle="success" data-offstyle="danger">';
        					return checkbox;
        				}
        			}
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
        				var action = '';
        				if (isDietitian || isKitchen || isAdmin) {
            				action = '<i class="fa fa-file-invoice fa-lg" title="KOT"></i>&nbsp;&nbsp;<i class="fa fa-tags fa-lg" title="Sticker"></i>';
            				if (row['orderStatus'] == 1 && row['serviceDeliveryDateTime'] > moment().format(dateFormat)) {
            					action += '&nbsp;&nbsp;<i class="fa fa-times fa-lg" title="Cancel"></i>';
            				}
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
		        "order": [
		            [0, 'desc']
		        ],		        
		        'info': true,
		        'autoWidth': false,
		        "serverSide": true,
		        "lengthMenu": [25, 50, 100, 200],
		        "pageLength": 200,
        		"fnDrawCallback": function() {
        			$(".chargable_switch").bootstrapToggle();
        		}
		    });
		    
		    var filters = '<div class="pull-left">';
		    filters += '<label for="isVip">Date:</label><input class="form-control daterange-single" id="dateSelection" name="dateSelection"></input>';		    
		    filters += '<input id="extraLiquid" type="checkbox" class="refreshTable"><label for="extraLiquid">&nbsp;Extra Liquid</label>';
		    filters += '<input id="isVip" type="checkbox" class="refreshTable"><label for="isVip">&nbsp;Is VIP</label>';
		    filters += '</div>';
		    $('#patients-table_filter').html(filters + $('#patients-table_filter').html());
		    $('#dateSelection').daterangepicker({
		        alwaysShowCalendars: true,
		        singleDatePicker: true,
		        locale: {
		            format: 'DD/MM/YYYY'
		        }
		    });
		    $("#dateSelection").bind("change", function() {
		    	patientsTable.ajax.url(contextPath + "/diet/diet-plan-data?extraLiquid=" + getCheckboxValue("extraLiquid") + "&isVip=" + getCheckboxValue("isVip") + "&dateSelection=" + getValue("dateSelection"));
		    	patientsTable.ajax.reload();
		    });
		    $(".refreshTable").bind("click", function() {
		    	patientsTable.ajax.url(contextPath + "/diet/diet-plan-data?extraLiquid=" + getCheckboxValue("extraLiquid") + "&isVip=" + getCheckboxValue("isVip") + "&dateSelection=" + getValue("dateSelection"));
		    	patientsTable.ajax.reload();
		    });
		    
		    function getCheckboxValue(id) {
		        return $('#' + id).is(":checked");
		    }

		    function getValue(id) {
		    	var value = $('#' + id).val();
		        return (value != "null" && value != "undefined"  && value != undefined) ? value : "";
		    }

        	$('#adhoc-oder-table').on('change', 'tbody .chargable_switch', function(e) {
        		if ($(this).is(":checked")) {
        			$(this).bootstrapToggle('off', true);
        		} else {
        			$(this).bootstrapToggle('on', true);
        		}
        		var data_row = adHocOderTable.row($(this).closest('tr')).data();
        		var adHocOrderId = data_row["adHocOrderId"];
        		var chargable = data_row["chargable"]
        		var title_Text = "Are you sure, you want to change the chargable status of the order?";
        		var confirmButtonText_Text = "Yes!";
        		Swal.fire({
        			title: title_Text,
        			icon: 'warning',
        			showCancelButton: true,
        			confirmButtonColor: '#3085d6',
        			cancelButtonColor: '#d33',
        			confirmButtonText: confirmButtonText_Text
        		}).then((result) => {
        			if (result.isConfirmed) {
        				var formData = "adHocOrderId=" + adHocOrderId + "&chargable=" + !(chargable); //Name value Pair             
        				$.ajax({
        					url: contextPath + "/diet/chargable-adhoc-order",
        					type: "POST",
        					data: formData,
        					success: function(data) {
        						Swal.fire({
        							icon: 'success',
        							title: "Order has been to" + (chargable ? "non chargable" : "chargable"),
        							showConfirmButton: false,
        							timer: 1500
        						}).then((result) => {
        							adHocOderTable.ajax.reload();
        						})
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
        	})

        	$('#adhoc-oder-table').on('click', 'tbody .fa-times', function(e) {
        		var data_row = adHocOderTable.row($(this).closest('tr')).data();
        		var orderId = data_row["orderId"];
        		var serviceDeliveryDateTime = data_row["serviceDeliveryDateTime"];
        		var adHocOrderId = data_row["adHocOrderId"];
        		var title_Text = "Are you sure, you want to cancle AdHoc Oder '" + orderId + "'?";
        		var confirmButtonText_Text = "Yes, cancle it!";
        		
    	        if (serviceDeliveryDateTime < moment().format(dateFormat)) {
    	        	Swal.fire('You can not cancle the Oder whose Service Delivery Date Time lapsed');
    	        	adHocOderTable.ajax.reload();
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
        				var formData = "adHocOrderId=" + adHocOrderId; //Name value Pair             
        				$.ajax({
        					url: contextPath + "/diet/cancel-adhoc-order",
        					type: "POST",
        					data: formData,
        					success: function(data) {
        						if (data == "Order has been updated") {
            						Swal.fire({
            							icon: 'success',
            							title: "Order has been Cancelled",
            							showConfirmButton: false,
            							timer: 1500
            						}).then((result) => {
            							adHocOderTable.ajax.reload();
            						})
        						} else {
            						Swal.fire({
            							icon: 'warning',
            							title: "You can not cancelled the Oder whose Service Delivery Date Time lapsed",
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
        	})
        	
            $('#adhoc-oder-table').on('click', 'tbody .fa-file-invoice', function() {
                var data_row = adHocOderTable.row($(this).closest('tr')).data();
                window.open(contextPath + "/diet/adhoc-order-kot?adHocOrderId=" + data_row["adHocOrderId"], "_blank");
            })
            
            $('#adhoc-oder-table').on('click', 'tbody .fa-tags', function() {
                var data_row = adHocOderTable.row($(this).closest('tr')).data();
                window.open(contextPath + "/diet/generate-adhoc-stickers?adHocOrderId=" + data_row["adHocOrderId"], "_blank");
            })
            
            
		    $("#Export-PDF").bind("click", function() {
		        window.location = contextPath + "/diet/export/pdf/adhoc-order";
		    });

		    $("#Export-Excel").bind("click", function() {
		    	 window.location = contextPath + "/diet/export/excel/adhoc-order";
		    });
		    
		    function refreshAdHocOderTable() {
		    	adHocOderTable.ajax.reload();
		    }
		    window.setInterval(refreshAdHocOderTable, setIntervalTime); 
        });
      </script>  
   </body>
</html>