<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <title>Diet Plan</title>
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
               <h1>Diet Plan</h1>
            </div>
            <!-- Main content -->
            <div class="content">
               <div class="card">
					<div class="card-header">
					<div style="float: right; margin-right: -0.625rem;">
						<ul class="nav nav-pills ml-auto">
							<li class="nav-item px-2">
								<button id="btn-show-all-children" class="btn btn-outline-primary btn-sm" type="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Expand All</button>
							</li>
							<li class="nav-item px-2">
								<button id="btn-hide-all-children" class="btn btn-outline-primary btn-sm" type="button"><i class="fa fa-minus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Collapse All</button>
							</li>
						</ul>
					</div>
					</div>               
                  <div class="card-body">
                     <div class="table-responsive">
                        <table id="patients-table" class="table table-bordered table-striped">
                           <thead>
								<tr>
									<th></th>
									<th>Patient Name</th>
									<th>IP number</th>
									<th>Admission Date</th>
									<th>Ward/Floor/Bed Cd</th>
									<th>Diet Type Solid</th>
									<th>Extra Liquid</th>
									<th>Diet Type Liquid</th>
									<th>Diet Sub Type</th>
									<th>Co-morbities</th>
									<th>Special Notes by Nurising</th>
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
         $("#DietPlan").addClass("active");
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
      function format(d) {
    	    var returnStr = "No Record Found";
    	    if (Array.isArray(d.dietPlans)) {
    	        returnStr = "<div class='card-body' style='padding: 5px 0px 0px 5px;'><table class='table table-bordered table-striped' style='width: auto;margin-left: 1%;'><thead><tr style='background: white;'><th>Service</th><th>Time</th><th>Diet Instruction</th><th>Item</th><th>Action</th></thead></tr><tbody>";

    	        for (let i = 0; i < d.dietPlans.length; i++) {
    	            returnStr += "<td>" + d.dietPlans[i].serviceMaster.service + "</td>";
    	            returnStr += "<td>" + d.dietPlans[i].serviceMaster.timeStr + "</td>";
    	            returnStr += "<td>";
    	            if (typeof d.dietPlans[i].dietInstructions !== 'undefined' && d.dietPlans[i].dietInstructions.length > 0) {
    	            	var dietInstructionsLength = d.dietPlans[i].dietInstructions.length;
        	            for (let j = 0; j < dietInstructionsLength; j++) {
        	            	 returnStr += ((dietInstructionsLength > 1) ? (j + 1) + ": " : "") + d.dietPlans[i].dietInstructions[j].instruction;
        	            	 if (j != d.dietPlans[i].dietInstructions.length - 1) {
        	            		 returnStr += "<Br>";
        	            	 }
        	            }
    	            } else {
    	            	returnStr += "-";
    	            }
    	            returnStr += "</td>";
    	            if (d.dietPlans[i].serviceMaster.serviceItemsColumnName != null) {
    	            	returnStr += "<td>" + '<input type="text" class="form-control" id="item_' + d.dietPlans[i].dietPlanId + '" value="' + (d.dietPlans[i].item == null ? "" : d.dietPlans[i].item) + '" size="70">' + "</td>";
    	            	returnStr += "<td>" + '<button type="submit" id="saveItem_' + d.dietPlans[i].dietPlanId +'" class="btn btn-success waves-effect waves-light btn-sm">Save</button>' + "</td>";
    	            } else {
    	            	returnStr += "<td>" + "-" + "</td>";
    	            	returnStr += "<td>" + "-" + "</td>";
    	            }
    	            returnStr += "</tr>";
    	        }
    	        returnStr += "</tbody></table></div>";
    	    }
    	    return returnStr;
    	}      
      
      $(document).ready(function() {
    	  $("#DietPlan").addClass("active");
		    var patientsTable = $('#patients-table').DataTable({
		        "ajax": {
		            'contentType': 'application/json',
		            'url': contextPath + "/diet/diet-plan-data?extraLiquid=" + getCheckboxValue("extraLiquid") + "&isVip=" + getCheckboxValue("isVip") + "&dateSelection=" + getValue("dateSelection"),
		            'method': "POST",
		            'data': function(d) {
		                return JSON.stringify(d);
		            },
		            'dataSrc': function(json) {
		                json.draw = json.data.draw;
		                json.recordsTotal = json.count;
		                json.recordsFiltered = json.data.recordsFiltered;
		                return json.data.data;
		            }
		        },
		        "columns": [{
	                "className": 'dt-control border-right-0',
	                "orderable": false,
	                "searchable": false,
	                "data": "",
	                "defaultContent": ''
	            }, {
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
		            "data": "ipNumber"
		        }, {
		            "data": "admittedDate",
		            "defaultContent": "-"
		        }, {
		            "data": "bedString",
		            "orderable": false,
		            "searchable": false
		        }, {
		            "data": "dietTypeOralSolid.value",
		            "defaultContent": "-",
		            "searchable": false
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
		            "data": "dietTypeOralLiquidTF.value",
		            "defaultContent": "-",
		            "searchable": false
		        }, {
        			"data": "dietSubType.value",
        			"defaultContent": "-"
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
		                    var aTag = '<a href="#" style="color: #666f73;" title = "' + data + '">' + data.substring(0, 50) + '...</a>';
		                    return aTag;
		                }
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
		            [2, 'asc']
		        ],		        
		        'info': true,
		        'autoWidth': false,
		        "serverSide": true,
		        "lengthMenu": [25, 50, 100, 200],
		        "pageLength": 200,
		        "fnDrawCallback": function() {},
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
		    // Add event listener for opening and closing details
		    $('#patients-table tbody').on('click', 'td.dt-control', function() {
		        var tr = $(this).closest('tr');
		        var row = patientsTable.row(tr);

		        if (row.child.isShown()) {
		            // This row is already open - close it
		            row.child.hide();
		            tr.removeClass('dt-hasChild');
		            tr.removeClass('shown');
		        } else {
		            // Open this row
		            row.child(format(row.data())).show();
		            tr.addClass('dt-hasChild');
		            tr.addClass('shown');
		        }
		    });

		    // Handle click on "Expand All" button
		    $('#btn-show-all-children').on('click', function() {
		        // Enumerate all rows
		        patientsTable.rows().every(function() {
		            // If row has details collapsed
		            if (!this.child.isShown()) {
		                // Open this row
		                this.child(format(this.data())).show();
		                $(this.node()).addClass('shown');
		                $(this.node()).addClass('dt-hasChild');
		            }
		        });
		    });

		    // Handle click on "Collapse All" button
		    $('#btn-hide-all-children').on('click', function() {
		        // Enumerate all rows
		        patientsTable.rows().every(function() {
		            // If row has details expanded
		            if (this.child.isShown()) {
		                // Collapse row details
		                this.child.hide();
		                $(this.node()).removeClass('shown');
		                $(this.node()).removeClass('dt-hasChild');
		            }
		        });
		    });
		    
		    
        	$('#patients-table').on('click', 'tbody .btn-success', function(e) {
        		var dietPlanId = $(this).attr("id").split("_")[1];
        		var item = $("#item_" + dietPlanId).val();
        		var title_Text = "Are you sure, you want to update the item?";
        		var confirmButtonText_Text = "Yes, update it!";
        		Swal.fire({
        			title: title_Text,	
        			icon: 'warning',
        			showCancelButton: true,
        			confirmButtonColor: '#3085d6',
        			cancelButtonColor: '#d33',
        			confirmButtonText: confirmButtonText_Text
        		}).then((result) => {
        			if (result.isConfirmed) {
        				var formData = "dietPlanId=" + dietPlanId + "&item=" + item; //Name value Pair             
        				$.ajax({
        					url: contextPath + "/diet/update-diet-plan-item",
        					type: "POST",
        					data: formData,
        					success: function(data) {
        						if (data == "Item has been updated") {
            						Swal.fire({
            							icon: 'success',
            							title: "Item has been updated",
            							showConfirmButton: false,
            							timer: 1500
            						}).then((result) => {
//             							patientsTable.ajax.reload();
            						})
        						} else {
            						Swal.fire({
            							icon: 'warning',
            							title: "You can not update the Item whose Delivery Date Time lapsed",
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
        });
      </script>  
   </body>
</html>