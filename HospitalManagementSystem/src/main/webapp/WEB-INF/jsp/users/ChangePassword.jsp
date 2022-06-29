<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <title>Change Password</title>
      <%@include file="../includes/HeadScript.jsp"%>
   </head>
   <body class="skin-blue sidebar-mini">
      <div class="wrapper boxed-wrapper">
         <%@include file="../includes/Header.jsp"%>
         <c:if test="${changePassword.passwordResetType eq 0}">
            <%@include file="../includes/Sidebar.jsp"%>
         </c:if>
         <!-- Content Wrapper. Contains page content -->
         <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <div class="content-header sty-one">
               <h1>Change Password</h1>
            </div>
            <!-- Main content -->
            <div class="content">
               <div class="card">
                  <c:if test="${not empty changePasswordError}">
                     <div class="alert alert-danger" role="alert"> ${changePasswordError} </div>
                  </c:if>
                  <div class="card-body">
                     <form:form method="POST" action="${contextPath}/update-password" modelAttribute="changePassword" id="updatePasswordFrom">
                        <form:hidden path="userId"/>
                        <form:hidden path="passwordResetType" id="passwordResetType"/>
                        <div class="row" id="oldPasswordRow">
                           <div class="col-lg-4">
                              <fieldset class="form-group">
                                 <label for="name">Old Password</label><span class="text-danger">*</span>
                                 <form:password cssClass="form-control" id="oldPassword" path="oldPassword"
                                    placeholder="Old Password"></form:password>
                              </fieldset>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-lg-4">
                              <fieldset class="form-group">
                                 <label for="name">Password</label><span class="text-danger">*</span>
                                 <form:password cssClass="form-control" id="password" path="password"
                                    placeholder="Password"></form:password>
                              </fieldset>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-lg-4">
                              <fieldset class="form-group">
                                 <label for="name">Confirm Password</label><span class="text-danger">*</span>
                                 <form:password cssClass="form-control" id="confirmPassword" path="confirmPassword"
                                    placeholder="Confirm Password"></form:password>
                              </fieldset>
                           </div>
                        </div>
                        <button type="submit" class="btn btn-success">Submit</button>
                        <p><br>
                           Note: Valid Password must match below criteria<br>
                           at least 1 numeric character<br>
                           at least 1 lowercase letter<br>
                           at least 1 uppercase letter<br>
                           at least 1 special character<br>							
                        </p>
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
      jQuery.validator.addMethod("passwordRegex", function(value, element) {
    	    $(element).val((this.elementValue(element).replace(/\s+/g, ' ')).trim());
    	    this.value = $(element).val();
    	    if (/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^\w\s]).{8,}$/.test(value)) {
    	        return true;
    	    } else {
    	        return false;
    	    };
    	});
      </script>    
      <script type="text/javascript">
      function passwordResetTypeChanges() {
        	if ($("#passwordResetType").val() == "0") {
        		$("#oldPasswordRow").show();
        	} else {
        		$("#oldPasswordRow").hide();
        		$("#updateProfileLink").hide();
        		$("#changePasswordLink").hide();
        	}
        }

        $(document).ready(function() {
        	$("#Users").addClass("active");
        	passwordResetTypeChanges();

        	$("#updatePasswordFrom").validate({
        		// in 'rules' user have to specify all the constraints for respective fields
        		rules: {
        			oldPassword: {
        				required: {
        					depends: function(element) {
        						return ($("#passwordResetType").val() == "0");
        					}
        				},
        				minlength: 8,
        				maxlength: 50,
        				passwordRegex: true
        			},
        			password: {
        				required: true,
        				minlength: 8,
        				maxlength: 50,
        				passwordRegex: true
        			},
        			confirmPassword: {
        				minlength: 8,
        				maxlength: 50,
        				passwordRegex: true,
        				equalTo: "#password"
        			}
        		},
        		// in 'messages' user have to specify message as per rules
        		messages: {
        			oldPassword: {
        				required: "Please enter Old Password",
        				minlength: "At least 8 characters required",
        				maxlength: "Max 50 characters allowed",
        				passwordRegex: "Please enter valid Old Password"
        			},
        			password: {
        				required: "Please enter Password",
        				minlength: "At least 8 characters required",
        				maxlength: "Max 50 characters allowed",
        				passwordRegex: "Please enter valid Password"
        			},
        			confirmPassword: {
        				required: "Please enter Confirm Password",
        				minlength: "At least 8 characters required",
        				maxlength: "Max 50 characters allowed",
        				passwordRegex: "Please enter valid Confirm Password",
        				equalTo: "Please enter the same password as above"
        			}
        		}
        	});
        });
      </script>    	
   </body>
</html>