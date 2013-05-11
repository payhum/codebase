<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">Paid Check History</h2> 
<style type="text/css">
.k-grouping-header {
	display: none;
}
</style>
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


