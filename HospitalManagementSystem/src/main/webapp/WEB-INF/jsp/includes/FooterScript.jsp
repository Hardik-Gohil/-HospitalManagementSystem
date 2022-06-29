<!-- jQuery 3 --> 
<script src="${contextPath}/resources/dist/js/jquery.min.js"></script> 
<!-- v4.0.0-alpha.6 --> 
<script src="${contextPath}/resources/dist/bootstrap/js/bootstrap.min.js"></script> 
<!-- template --> 
<script src="${contextPath}/resources/dist/js/adminkit.js"></script> 
<!-- Morris JavaScript --> 
<%-- <script src="${contextPath}/resources/dist/plugins/raphael/raphael-min.js"></script>  --%>
<%-- <script src="${contextPath}/resources/dist/plugins/morris/morris.js"></script>  --%>
<%-- <script src="${contextPath}/resources/dist/plugins/functions/dashboard1.js"></script>  --%>
<!-- Chart Peity JavaScript --> 
<%-- <script src="${contextPath}/resources/dist/plugins/peity/jquery.peity.min.js"></script>  --%>
<%-- <script src="${contextPath}/resources/dist/plugins/functions/jquery.peity.init.js"></script> --%>
<script type="text/javascript">
   $("ul.sidebar-menu > li").each(function() {
   	$(this).removeClass("active");
   });
   $("body").addClass("sidebar-collapse");
</script>