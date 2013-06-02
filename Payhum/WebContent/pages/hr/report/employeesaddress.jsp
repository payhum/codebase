<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">Employees Address</h2> 
<style type="text/css">
.k-grouping-header {
	display: none;
}
</style>
<div id="grid">
		<a href='<%=request.getContextPath() + "/do/PDFActions?parameter=empAdressPdf"%>'> <span id="rpsm"></span> <span id="rpsm1">Export
				PDF Report</span></a>
	</div>
<div id="empForm">
</div>


<script>



var empDataSource;



	
    $(document).ready(function(e){	

	
	        
	       empDataSource = new kendo.data.DataSource({
	        	transport : {
	           		read : {
	           			url : "<%=request.getContextPath() + "/do/EmployeeAdressAction"%>",
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
	            dataSource : empDataSource, 
	            columns : [
					
	                
					{ field : "employeeId", title : "Id", width : 120 },
	                { field : "firstname", title : "Name", template: '#=firstname+"."+middlename#', width : 100 },
	                
	             {field : "address", title : "Address", width : 100 }
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


