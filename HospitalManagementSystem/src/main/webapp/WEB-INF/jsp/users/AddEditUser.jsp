<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <title>Users</title>
      <%@include file="../includes/HeadScript.jsp"%>
   </head>
   <body class="skin-blue sidebar-mini">
      <div class="wrapper boxed-wrapper">
         <%@include file="../includes/Header.jsp"%>
         <%@include file="../includes/Sidebar.jsp"%>
         <!-- Content Wrapper. Contains page content -->
         <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <div class="content-header sty-one">
               <c:if test="${empty userDto.userId}">
                  <h1>Add User</h1>
               </c:if>
               <c:if test="${userDto.userId ge 0}">
                  <h1>Edit User</h1>
               </c:if>
            </div>
            <!-- Main content -->
            <div class="content">
               <div class="card">
                  <div class="card-body">
                     <form:form method="POST" action="${contextPath}/add-edit-user" modelAttribute="userDto" onsubmit="return Validation();" id="addEditUserForm">
                        <form:hidden path="userId" id="userId"/>
                        <div class="row">
                           <div class="col-lg-4">
                              <fieldset class="form-group">
                                 <label for="username">Username</label><span class="text-danger">*</span>
                                 <form:input cssClass="form-control" id="username" path="username" disabled="${not empty userDto.username}"
                                    placeholder="Username"></form:input>
                              </fieldset>
                           </div>
                           <div class="col-lg-4">
                              <fieldset class="form-group">
                                 <label for="name">Name</label><span class="text-danger">*</span>
                                 <form:input cssClass="form-control" id="name" path="name"
                                    placeholder="Name"></form:input>
                              </fieldset>
                           </div>
                           <div class="col-lg-4">
                              <fieldset class="form-group">
                                 <label for="designation">Designation</label>
                                 <form:input cssClass="form-control" id="designation"
                                    placeholder="Designation" path="designation"></form:input>
                              </fieldset>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-lg-4">
                              <fieldset class="form-group">
                                 <label for="employeeNumber">Employee Number</label>
                                 <form:input cssClass="form-control" id="employeeNumber"
                                    placeholder="Employee Number" path="employeeNumber"></form:input>
                              </fieldset>
                           </div>
                           <div class="col-lg-4">
                              <fieldset class="form-group">
                                 <label for="department">Department</label><span
                                    class="text-danger">*</span> 
                                 <form:select cssClass="form-control"
                                    id="department" path="department.departmentId" onchange="departmentChange();" disabled="${!isAdmin}">
                                    <form:option value="">Please Select Department</form:option>
                                    <c:forEach items="${departmentList}" var="department">
                                       <form:option value="${department.departmentId}">${department.departmentName}</form:option>
                                    </c:forEach>
                                 </form:select>
                              </fieldset>
                           </div>
                           <div class="col-lg-4">
                              <fieldset class="form-group">
                                 <label for="emailId">Email ID</label><span class="text-danger">*</span>
                                 <form:input cssClass="form-control" id="emailId"
                                    placeholder="Email ID" path="emailId"></form:input>
                              </fieldset>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-lg-4">
                              <fieldset class="form-group">
                                 <label for="mobileNumber">Mobile Number</label><span
                                    class="text-danger">*</span> 
                                 <form:input type="text"
                                    cssClass="form-control" id="mobileNumber"
                                    placeholder="Mobile Number" path="mobileNumber"></form:input>
                              </fieldset>
                           </div>
                           <div class="col-lg-4" id="floorRow">
                              <fieldset class="form-group">
                                 <label for="floor">Floor</label><span
                                    class="text-danger">*</span> 
                                 <form:select cssClass="form-control"
                                    id="floor" path="floor.floorId">
                                    <form:option value="">Please Select Floor</form:option>
                                    <c:forEach items="${floorList}" var="floor">
                                       <form:option value="${floor.floorId}">${floor.floorName}</form:option>
                                    </c:forEach>
                                 </form:select>
                              </fieldset>
                           </div>
                        </div>
                        <button type="submit" class="btn btn-success">Submit</button>
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
      <script type="text/javascript">
      jQuery.validator.addMethod("usernameRegex", function(value, element) {
    	    $(element).val((this.elementValue(element).replace(/\s+/g, ' ')).trim());
    	    this.value = $(element).val();
    	    if (/^[a-zA-Z0-9-_]*$/.test(value)) {
    	        return true;
    	    } else {
    	        return false;
    	    };
    	});

    	jQuery.validator.addMethod("alphanumericWithSpace", function(value, element) {
    	    $(element).val((this.elementValue(element).replace(/\s+/g, ' ')).trim());
    	    this.value = $(element).val();
    	    if (/^[a-zA-Z0-9 ]*$/.test(value)) {
    	        return true;
    	    } else {
    	        return false;
    	    };
    	});

    	jQuery.validator.addMethod("emailRegex", function(value, element) {
    	    $(element).val((this.elementValue(element).replace(/\s+/g, ' ')).trim());
    	    this.value = $(element).val();
    	    if (/^(\w\.?)+@[\w\.-]+\.\w{2,}$/.test(value)) {
    	        return true;
    	    } else {
    	        return false;
    	    };
    	});

    	jQuery.validator.addMethod("mobileNumberRegex", function(value, element) {
    	    $(element).val((this.elementValue(element).replace(/\s+/g, ' ')).trim());
    	    this.value = $(element).val();
    	    if (/^\d{10}$/.test(value)) {
    	        return true;
    	    } else {
    	        return false;
    	    };
    	});
      </script>  
      <script type="text/javascript">
      function departmentChange() {
        	if ($("#department").val() == "1") {
        		$("#floorRow").show();
        	} else {
        		$("#floorRow").hide();
        	}
        }

        function Validation() {
        	$("#username").attr("disabled", false);
        	$("#department").attr("disabled", false);
        }


        $(document).ready(function() {
        	$("#Users").addClass("active");
        	departmentChange();

        	$("#addEditUserForm").validate({
        		// in 'rules' user have to specify all the constraints for respective fields
        		rules: {
        			username: {
        				required: true,
        				minlength: 2,
        				maxlength: 24,
        				usernameRegex: true,
        				remote: {
        					url: contextPath + "/check-unique-username",
        					type: "POST",
        					async: false,
        					data: {
        						username: function() {
        							return $("#username").val();
        						},
        						userId: function() {
        							return $("#userId").val();
        						}
        					}
        				}
        			},
        			name: {
        				required: true,
        				minlength: 2,
        				maxlength: 50,
        				alphanumericWithSpace: true
        			},
        			designation: {
        				minlength: 2,
        				maxlength: 50,
        				alphanumericWithSpace: true
        			},
        			employeeNumber: {
        				minlength: 2,
        				maxlength: 50,
        				alphanumericWithSpace: true
        			},
        			"department.departmentId": {
        				required: true
        			},
        			emailId: {
        				required: true,
        				minlength: 6,
        				maxlength: 50,
        				emailRegex: true
        			},
        			mobileNumber: {
        				required: true,
        				mobileNumberRegex: true
        			},
        			"floor.floorId": {
        				required: {
        					depends: function(element) {
        						return ($("#department").val() == "1");
        					}
        				}
        			}
        		},
        		// in 'messages' user have to specify message as per rules
        		messages: {
        			username: {
        				required: "Please enter Username",
        				minlength: "At least 2 characters required",
        				maxlength: "Max 24 characters allowed",
        				usernameRegex: "Only Alphanumeric characters with -_ are allowed",
        				remote: "Username already taken"
        			},
        			name: {
        				required: "Please enter Name",
        				minlength: "At least 2 characters required",
        				maxlength: "Max 50 characters allowed",
        				alphanumericWithSpace: "Only Alphanumeric characters are allowed"
        			},
        			designation: {
        				minlength: "At least 2 characters required",
        				maxlength: "Max 50 characters allowed",
        				alphanumericWithSpace: "Only Alphanumeric characters are allowed"
        			},
        			employeeNumber: {
        				minlength: "At least 2 characters required",
        				maxlength: "Max 50 characters allowed",
        				alphanumericWithSpace: "Only Alphanumeric characters are allowed"
        			},
        			"department.departmentId": {
        				required: "Please Select Department"
        			},
        			emailId: {
        				required: "Please enter Email ID",
        				minlength: "At least 6 characters required",
        				maxlength: "Max 50 characters allowed",
        				emailRegex: "Please enter valid Email ID"
        			},
        			mobileNumber: {
        				required: "Please enter Mobile Number",
        				mobileNumberRegex: "Please enter valid Mobile Number"
        			},
        			"floor.floorId": {
        				required: "Please Select Floor"
        			}
        		}
        	});
        });
      </script>    	
   </body>
</html>