<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">Payroll Details </h2> 
<div id="grid"><a href="#">
<span id="rpsm"></span>
<span id="rpsm1">Export PDF Report</span></a>
	</div>
<div id="empForm">
</div>

<script>
	$(document).ready(function(e) {

		$("#grid").kendoGrid({

			height : 500,
			filterable : true,
			groupable : true,
			resizeable : true,
			reorderable : true

		});

	});
</script>
