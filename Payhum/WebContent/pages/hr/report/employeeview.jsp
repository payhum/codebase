<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">GLReports Employee View </h2> 
<div id="grid">
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

