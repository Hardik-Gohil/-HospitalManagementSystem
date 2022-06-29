<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <title>Login</title>
      <!-- Tell the browser to be responsive to screen width -->
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <!-- v4.0.0-alpha.6 -->
      <link rel="stylesheet" href="${contextPath}/resources/dist/bootstrap/css/bootstrap.min.css">
      <!-- Favicon -->
      <link rel="icon" type="image/png" sizes="16x16" href="${contextPath}/resources/dist/img/favicon.ico">
      <!-- Google Font -->
      <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" rel="stylesheet">
      <!-- Theme style -->
      <link rel="stylesheet" href="${contextPath}/resources/dist/css/style.css">
      <link rel="stylesheet" href="${contextPath}/resources/dist/css/font-awesome/css/font-awesome.min.css">
      <link rel="stylesheet" href="${contextPath}/resources/dist/css/et-line-font/et-line-font.css">
      <link rel="stylesheet" href="${contextPath}/resources/dist/css/themify-icons/themify-icons.css">
      <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
      <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
      <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
      <![endif]-->
   </head>
   <body class="hold-transition login-page">
      <div class="login-box" style="width: 471px">
         <div class="login-logo">
            <h4>
               <b>NUTRIMENT (NUTRITION + TREATMENT)</b>
            </h4>
         </div>
         <div class="login-box-body">
            <h3 class="login-box-msg">Sign In</h3>
            <form method="POST" action="${contextPath}/login">
               <div class="form-group has-feedback">
                  <input name="username" type="text" class="form-control" placeholder="User" required="required">
               </div>
               <div class="form-group has-feedback">
                  <input name="password" type="password" class="form-control" placeholder="Password" required="required">
               </div>
               <div>
                  <!--         <div class="col-xs-8"> -->
                  <!--           <div class="checkbox icheck"> -->
                  <!--             <label> -->
                  <!--               <input type="checkbox"> -->
                  <!--               Remember Me </label> -->
                  <!--             <a href="pages-recover-password.html" class="pull-right"><i class="fa fa-lock"></i> Forgot pwd?</a> </div> -->
                  <!--         </div> -->
                  <!-- /.col -->
                  <c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
                     <div class="error">
                        Your login attempt was not successful, try again.<br />
                        Reason: ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
                     </div>
                  </c:if>
                  <div class="col-xs-4 m-t-1">
                     <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
                  </div>
                  <!-- /.col --> 
               </div>
            </form>
            <!--     <div class="social-auth-links text-center"> -->
            <!--       <p>- OR -</p> -->
            <!--       <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign in using -->
            <!--       Facebook</a> <a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i> Sign in using -->
            <!--       Google+</a> </div> -->
            <!-- /.social-auth-links -->
            <!--     <div class="m-t-2">Don't have an account? <a href="pages-register.html" class="text-center">Sign Up</a></div> -->
         </div>
         <!-- /.login-box-body --> 
      </div>
      <!-- /.login-box --> 
      <!-- jQuery 3 --> 
      <script src="${contextPath}/resources/dist/js/jquery.min.js"></script> 
      <!-- v4.0.0-alpha.6 --> 
      <script src="${contextPath}/resources/dist/bootstrap/js/bootstrap.min.js"></script> 
      <!-- template --> 
      <script src="${contextPath}/resources/dist/js/niche.js"></script>
   </body>
</html>