<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <title>Users</title>
      <%@include file="../includes/HeadScript.jsp"%>
      <!-- DataTables -->
      <link rel="stylesheet" href="${contextPath}/resources/dist/plugins/datatables/css/dataTables.bootstrap.min.css">
      <!-- bootstrap-switch -->
      <link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
      <!-- SweetAlert2 -->
      <link rel="stylesheet" href="${contextPath}/resources/dist/plugins/sweetalert2-theme-bootstrap-4/bootstrap-4.min.css">
      <style type="text/css">
         .custom-control-label::before ,.custom-control-label::after{width:20px; height:20px}
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
               <h1>Users</h1>
               <a href="${contextPath}/add-edit-user">
               <button type="button" class="btn btn-outline-primary" style="padding: 7px 5px;position: absolute;top: 11px;right: 10px;"><i class="fa fa-plus"></i>&nbsp;ADD&nbsp;</button>
               </a>
            </div>
            <!-- Main content -->
            <div class="content">
               <div class="card">
                  <div class="card-body">
                     <div class="table-responsive">
                        <table id="user-table" class="table table-bordered table-striped">
                           <thead style="background-color: #3dc9df!important;">
                              <tr>
                                 <th>Name</th>
                                 <th>Designation</th>
                                 <th>Employee Number</th>
                                 <th>Department</th>
                                 <th>Email ID</th>
                                 <th>Mobile Number</th>
                                 <th>Floor</th>
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
         $("#Users").addClass("active");
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
        	var table = $('#user-table').DataTable({
        		"ajax": {
        			'url': contextPath + "/user-data",
        			'method': "GET",
        			"dataSrc": ""
        		},
        		"columns": [{
        			"data": "name"
        		}, {
        			"data": "designation"
        		}, {
        			"data": "employeeNumber"
        		}, {
        			"data": "department.departmentName",
        			"defaultContent": "NA"
        		}, {
        			"data": "emailId"
        		}, {
        			"data": "mobileNumber"
        		}, {
        			"data": "floor.floorName",
        			"defaultContent": "NA"
        		}, {
        			"data": "isActive",
        			"render": function(data, type, row) {
        				if (type === "sort" || type === "filter" || type === 'type') {
        					return (data ? 'Active' : 'Inactive');
        				} else {
        					var checkbox = '<input type="checkbox" class="status_switch" ' + (data ? 'checked="checked"' : '') + ' data-toggle="toggle" data-size="xs" data-on="Active" data-off="Inactive" data-onstyle="success" data-offstyle="danger">';
        					return checkbox;
        				}
        			}
        		}, {
        			"orderable": false,
        			"searchable": false,
        			"data": "",
        			"defaultContent": '<i class="fa fa-edit fa-lg" title="Edit"></i>&nbsp;&nbsp;<i class="fa fa-key fa-lg" title="Reset Password"></i></div>'

        		}],
        		"order": [
        			[0, 'asc']
        		],
        		'paging': true,
        		'lengthChange': false,
        		'ordering': true,
        		'info': true,
        		'autoWidth': false,
        		"fnDrawCallback": function() {
        			$(".status_switch").bootstrapToggle();
        		},
        	});


        	$('#user-table').on('change', 'tbody .status_switch', function(e) {
        		if ($(this).is(":checked")) {
        			$(this).bootstrapToggle('off', true);
        		} else {
        			$(this).bootstrapToggle('on', true);
        		}
        		var data_row = table.row($(this).closest('tr')).data();
        		var userId = data_row["userId"];
        		var name = data_row["name"];
        		var isActive = data_row["isActive"]
        		var title_Text = isActive ? "Are you sure, you want to Inactive " + name + "'s account ?" : "Are you sure, you want to Active " + name + "'s account ?";
        		var confirmButtonText_Text = isActive ? "Yes, Inactive it!" : "Yes, Active it!";
        		Swal.fire({
        			title: title_Text,
        			icon: 'warning',
        			showCancelButton: true,
        			confirmButtonColor: '#3085d6',
        			cancelButtonColor: '#d33',
        			confirmButtonText: confirmButtonText_Text
        		}).then((result) => {
        			if (result.isConfirmed) {
        				var formData = "userId=" + userId + "&isActive=" + !(isActive); //Name value Pair             
        				$.ajax({
        					url: contextPath + "/change-user-status",
        					type: "POST",
        					data: formData,
        					success: function(data) {
        						Swal.fire({
        							icon: 'success',
        							title: "User has been " + (isActive ? "Inactiveted" : "Actived"),
        							showConfirmButton: false,
        							timer: 1500
        						}).then((result) => {
        							table.ajax.reload();
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

        	$('#user-table').on('click', 'tbody .fa-edit', function() {
        		var data_row = table.row($(this).closest('tr')).data();
        		window.location = contextPath + "/add-edit-user?userId=" + data_row["userId"];
        	})

        	$('#user-table').on('click', 'tbody .fa-key', function(e, data) {
        		var data_row = table.row($(this).closest('tr')).data();
        		var userId = data_row["userId"];
        		var name = data_row["name"];
        		var title_Text = "Are you sure, you want to Reset Password of " + name + "?";
        		var confirmButtonText_Text = "Yes, Reset it!";
        		Swal.fire({
        			title: title_Text,
        			icon: 'warning',
        			showCancelButton: true,
        			confirmButtonColor: '#3085d6',
        			cancelButtonColor: '#d33',
        			confirmButtonText: confirmButtonText_Text
        		}).then((result) => {
        			if (result.isConfirmed) {
        				var formData = "userId=" + userId; //Name value Pair             
        				$.ajax({
        					url: contextPath + "/reset-password",
        					type: "POST",
        					data: formData,
        					success: function(data) {
        						Swal.fire({
        							icon: 'success',
        							title: "User's password has been reset",
        							showConfirmButton: false,
        							timer: 1500
        						}).then((result) => {
        							table.ajax.reload();
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
        });
      </script>  
   </body>
</html>