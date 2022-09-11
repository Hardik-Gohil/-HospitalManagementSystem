<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <title>Notifications</title>
      <%@include file="includes/HeadScript.jsp"%>
      <!-- DataTables -->
      <link rel="stylesheet" href="${contextPath}/resources/dist/plugins/datatables/css/dataTables.bootstrap.min.css">
      <!-- bootstrap-switch -->
      <link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
      <!-- SweetAlert2 -->
      <link rel="stylesheet" href="${contextPath}/resources/dist/plugins/sweetalert2-theme-bootstrap-4/bootstrap-4.min.css">
      <style type="text/css">
		.isRead {
		    background-color: rgba(0,0,0,.05);
		}
      </style>
   </head>
   <body class="skin-blue sidebar-mini">
      <div class="wrapper boxed-wrapper">
         <%@include file="includes/Header.jsp"%>
         <%@include file="includes/Sidebar.jsp"%>
         <!-- Content Wrapper. Contains page content -->
         <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <div class="content-header sty-one">
               <h1>Notifications&nbsp;&nbsp;&nbsp;<span class="badge bg-blue" id="Notifications-Count"></span></h1>
            </div>
            <!-- Main content -->
            <div class="content">
               <div class="card">
                  <div class="card-body">
                     <div class="table-responsive">
                        <table id="notifications-table" class="table table-bordered">
                           <thead>
                              <tr>
                                 <th>Notifications</th>
                                 <th>Go</th>
                                 <th>Created On</th>
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
         <%@include file="includes/Footer.jsp"%>
      </div>
      <!-- ./wrapper --> 
      <%@include file="includes/FooterScript.jsp"%>	
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
      
      function updateReadNotifications(notificationsId, tr) {
    	$(tr).addClass("isRead");
    	var formData = "notificationsId=" + notificationsId; //Name value Pair           
		$.ajax({
			url: contextPath + "/update-read-notifications",
			type: "POST",
			data: formData,
			success: function(data) {
				updateNotificationsCount();
			},
			error: function() {
			}
		});
      }
      
      $(document).ready(function() {
        	var table = $('#notifications-table').DataTable({
		        "ajax": {
		            'contentType': 'application/json',
		            'url': contextPath + "/notifications-data",
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
        			"data": "templates"
        		}, {
		            "orderable": false,
		            "searchable": false,
		            "data": "",
		            "defaultContent": '<i class="fa fa-external-link fa-lg" title="Go"></i>'

		        }, {
        			"data": "createdOn"
        		}],
		        "createdRow": function(row, data, dataIndex) {
		            if (data['isRead']) {
		                $(row).addClass('isRead');
		            }
		        },
        		"order": [
        			[2, 'desc']
        		],
		        'paging': true,
		        'ordering': true,
		        'info': true,
		        'autoWidth': false,
		        "serverSide": true,
		        "pageLength": 25,
        		"fnDrawCallback": function() {
//         			$(".status_switch").bootstrapToggle();
        		}
        	});
        	
        	$('#notifications-table').on('click', 'tbody tr', function(e) {
            	var data_row = table.row($(this)).data();
            	var notificationsId = data_row["notificationsId"];
            	var isRead = data_row["isRead"];
            	if (!isRead) {
            		updateReadNotifications(notificationsId, $(this));
            	}
            });


        	$('#notifications-table').on('click', 'tbody .fa-external-link', function(e) {
		        var data_row = table.row($(this).closest('tr')).data();
		        var code = data_row["code"];
		        var objectId = data_row["objectId"];
		        var notificationsId = data_row["notificationsId"];
		        var url = "";
		        switch(code) {
		        case "ADD_PATIENT":
		        	url = "/diet/patients";
		          break;
		        case "UPDATE_PATIENT":
		        	url = "/diet/patients";
			      break;
		        case "DIET_INSTRUCTION":
		        	url = "/diet/diet-instruction?patientId=" + objectId;
			      break;
			    case "ADHOC_ORDER":
			    	url = "/diet/adhoc-order?patientId=" + objectId;
				  break;	
		        case "PATIENT_DISCHARGE":
		        	url = "/diet/patients";
				  break;
			    case "PATIENT_TRANSFERRED":
		        	url = "/diet/patients";
				  break;				  
		      }
		       
		      if (url != "") {
			     window.open(contextPath + url, "_blank");
		      }  
        	})
        });
      </script>  
   </body>
</html>