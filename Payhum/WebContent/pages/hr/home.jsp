<%@include file="../common/jspHeader.jsp"%>
<div id="dashboard" class="k-content"> 
	<div class="chart-wrapper" >
		<div id="chart" style="float: left; width:400px"></div>

		<div id="revenueChart" style="float: right; width:500px;padding-right:30px;"></div>
		<div style="clear: both"></div>
	</div>
	<script>
		function createChart(data) {
			
			
	
		
			
			$("#chart").kendoChart({
				theme : $(document).data("kendoSkin") || "default",
				title : {
					text : "Employee break up per department"
				},
				legend : {
					position : "bottom",
					labels : {
						template : "#= category # (#= value #)"
					}
				},
				seriesDefaults : {
					labels : {
						visible : true,
						format : "{0}"
					}
				},
				series : [ {
					type : "pie",
					data :data
				} ],
				tooltip : {
					visible : true,
					format : "{0}"
				}
			});
			
			
		}

		$(document).ready(function() {
			var d=null;
			setTimeout(function() {
				// Initialize the chart with a delay to make sure
				// the initial animation is visible
				
				$.ajax({
					 type : "POST",
					 url:'<%=request.getContextPath()+ "/do/PieChartCommanActions?parameter=getPieChart"%>',
					 dataType : 'json',
					 contentType : 'application/json; charset=utf-8',
					 
					 success : function(data){ 
						 d=data;
					//alert(d);
						 createChart(data);     					 	        					 
					 }	        				
				});
				$("#dashboard").bind("kendo:skinChange", function(e) {
					
					$.ajax({
						 type : "POST",
						 url:'<%=request.getContextPath()+ "/do/PieChartCommanActions?parameter=getPieChart"%>',
						 dataType : 'json',
						 contentType : 'application/json; charset=utf-8',
						 
						 success : function(data){ 
							 d=data;
							// alert(d);
							 createChart(data);     					 	        					 
						 }	        				
					});
				});
			}, 400);
		});

		function createRevenueBarChart(data1) {
			$("#revenueChart").kendoChart({
				theme : $(document).data("kendoSkin") || "default",
				title : {
					text : "Departments break up per Branch"
				},
				legend : {
					position : "bottom",
					labels : {
						template : "#= category # (#= value #)"
					}
				},
				seriesDefaults : {
					labels : {
						visible : true,
						format : "{0}"
					}
				},
				series : [ {
					type : "pie",
					data :data1
				} ],
				tooltip : {
					visible : true,
					format : "{0}"
				}
			});
			
		}

		$(document).ready(function() {
			
			setTimeout(function() {
				
				
				
				$.ajax({
					 type : "POST",
					 url:'<%=request.getContextPath()+ "/do/PieChartCommanActions?parameter=getPieChartBranch"%>',
					 dataType : 'json',
					 contentType : 'application/json; charset=utf-8',
					 
					 success : function(data1){ 
						
					
					createRevenueBarChart(data1);    					 	        					 
					 }	        				
				});
				
				
				
				// Initialize the chart with a delay to make sure
				// the initial animation is visible
			}, 400);

			$(document).bind("kendo:skinChange", function(e) {
			
				
				
				$.ajax({
					 type : "POST",
					 url:'<%=request.getContextPath()+ "/do/PieChartCommanActions?parameter=getPieChartBranch"%>',
					 dataType : 'json',
					 contentType : 'application/json; charset=utf-8',
					 
					 success : function(data1){ 
						
						// alert(d);
							createRevenueBarChart(data1);  					 	        					 
					 }	        				
				});
				
				
			});
		});
	</script>
</div>

<div style="font-family:arial;
		  font-size:12px;
		  text-align:justify;
		  width:900px;
		  padding:10px; ">
		  <h2 style="font-size:16px;">Summary Note:</h2>
		  Something about the data visualization is to go here
		  Any one who wants to can do what he wants too if it is
		  true. Something about the data visualization is to go here
		  Any one who wants to can do what he wants too if it is
		  true.Something about the data visualization is to go here
		  Any one who wants to can do what he wants too if it is
		  true.Something about the data visualization is to go here
		  Any one who wants to can do what he wants too if it is
		  true.
</div>



