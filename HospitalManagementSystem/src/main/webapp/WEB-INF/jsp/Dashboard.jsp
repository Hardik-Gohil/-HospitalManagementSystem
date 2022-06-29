<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <title>Dashboard</title>
      <%@include file="includes/HeadScript.jsp"%>
   </head>
   <body class="skin-blue sidebar-mini">
      <div class="wrapper boxed-wrapper">
         <%@include file="includes/Header.jsp"%>
         <%@include file="includes/Sidebar.jsp"%>
         <!-- Content Wrapper. Contains page content -->
         <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <div class="content-header sty-one">
               <h1>Dashboard</h1>
            </div>
            <!-- Main content -->
            <div class="content">
               <div class="row">
                  <div class="col-lg-3">
                     <div class="tile-progress tile-pink">
                        <div class="tile-header">
                           <h5>New Orders</h5>
                           <h3>1250</h3>
                        </div>
                        <div class="tile-progressbar"> <span data-fill="65.5%" style="width: 65.5%;"></span> </div>
                        <div class="tile-footer">
                           <h4> <span class="pct-counter">65.5</span>% increase </h4>
                        </div>
                     </div>
                  </div>
                  <div class="col-lg-3">
                     <div class="tile-progress tile-red">
                        <div class="tile-header">
                           <h5>Monthly Sales</h5>
                           <h3>850</h3>
                        </div>
                        <div class="tile-progressbar"> <span data-fill="70%" style="width: 70%;"></span> </div>
                        <div class="tile-footer">
                           <h4> <span class="pct-counter">70</span>% increase </h4>
                        </div>
                     </div>
                  </div>
                  <div class="col-lg-3">
                     <div class="tile-progress tile-cyan">
                        <div class="tile-header">
                           <h5>New Users</h5>
                           <h3>2250</h3>
                        </div>
                        <div class="tile-progressbar"> <span data-fill="50%" style="width: 50%;"></span> </div>
                        <div class="tile-footer">
                           <h4> <span class="pct-counter">50</span>% increase </h4>
                        </div>
                     </div>
                  </div>
                  <div class="col-lg-3">
                     <div class="tile-progress tile-aqua">
                        <div class="tile-header">
                           <h5>Feedbacks</h5>
                           <h3>530</h3>
                        </div>
                        <div class="tile-progressbar"> <span data-fill="75.5%" style="width: 75.5%;"></span> </div>
                        <div class="tile-footer">
                           <h4> <span class="pct-counter">75.5</span>% increase </h4>
                        </div>
                     </div>
                  </div>
               </div>
               <!-- /.row -->
            </div>
            <!-- /.content --> 
         </div>
         <!-- /.content-wrapper -->
         <%@include file="includes/Footer.jsp"%>
      </div>
      <!-- ./wrapper --> 
      <%@include file="includes/FooterScript.jsp"%>
      <script type="text/javascript">
         $("#Dashboard").addClass("active");
      </script>
   </body>
</html>