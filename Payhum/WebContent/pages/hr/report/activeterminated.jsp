<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">Active vs Terminated</h2> 
<div id="grid">
<a href="#">
<span id="rpsm"></span>
<span id="rpsm1">Export PDF Report</span></a>
	</div>
<div id="empForm">
</div>


<script> 


var taxDataSource;



	
    $(document).ready(function(e){	

	
	        
    	taxDataSource = new kendo.data.DataSource({
	        	transport : {
	           		read : {
	           			url : "<%=request.getContextPath() + "/do/ReadActiveAction"%>",
	           			dataType : "json",
	           			cache : false
	           		},
	            
	                parameterMap : function(options, operation){
	           			if(operation !== "read" && options.models){	           				           				
	           				return JSON.stringify(options.models);
	           			}
	           		}
	           	},
	          
	           	autoSync : true,
	           	batch : true 
	        });
	        
	        
$("#grid").kendoGrid({
	            dataSource : taxDataSource, 
	            columns : [
					
	                
					{ field : "employeeId", title : "Id", width : 100 },
	                { field : "firstname", title : "Name", width : 100 },
	                
	              
	                { field : "active", title : "Active", width : 100 },
	                
	                { field : "terminated", title : "Terminated", width : 100 }
	                ],             
	        
	            
	            sortable: true,  
	            scrollable : true,
	            height : 500,
	            filterable: true,
	            groupable : true,
	            resizeable : true,
	            reorderable: true,
	            selectable : "row", 
	            editable : "popup" 
	        });
	         
	  });    
    
    </script>  




