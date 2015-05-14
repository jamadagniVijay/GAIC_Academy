	
<script type="text/javascript" src="libraries/js/jquery.min.js"></script>
<script type="text/javascript" src="libraries/js/jquery.placeholder.js"></script>
<script type="text/javascript" src="libraries/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="libraries/js/jquery.jeditable.js"></script>
<script type="text/javascript" src="libraries/js/jquery.dataTables.editable.js"></script>
<script type="text/javascript" src="libraries/js/jquery.validate.js"></script>
<script type="text/javascript" src="libraries/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="libraries/js/dataTables.tableTools.min.js"></script>
<script type="text/javascript" src="libraries/js/dataTables.editor.min.js"></script>
<script type="text/javascript" src="libraries/js/jquery.datetimepicker.js"></script>
<script type="text/javascript" src="libraries/js/sweetalert2.min.js"></script>
<script type="text/javascript" src="libraries/js/jquery-ui.js"></script>



<script type="text/javascript" src="js/script.js"></script>
<!-- <script type="text/javascript" src="js/testScript.js"></script> -->



<!--[if lt IE 9]>
  <script src="libraries/js/html5shiv.min.js"></script>
  <script src="libraries/js/respond.min.js"></script>
<![endif]-->
<script type="text/javascript">
	window.onclose = function(event){
	alert("closing");
	    if (window.XMLHttpRequest)
	    {// code for IE7+, Firefox, Chrome, Opera, Safari
	        xmlhttp=new XMLHttpRequest();
	    }
	    else
	    {// code for IE6, IE5
	        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	    }
	    xmlhttp.open("GET","first.jsp?key=0",false);
	    xmlhttp.send();
	}
</script>