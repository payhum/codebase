<%@include file="../../common/jspHeader.jsp"%>
<h2 class="legend">Tax Monthly</h2>
<div id="grid"><a href="#">
<span id="rpsm"></span>
<span id="rpsm1">Export PDF Report</span></a></div>
<div id="empForm"></div>


<script> 


var empDataSource;



	
    $(document).ready(function(e){	

	
	        
	       empDataSource = new kendo.data.DataSource({
	        	transport : {
	           		read : {
	           			url : "<%=request.getContextPath() + "/do/ReadTaxMonthlyAction"%>",
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
	                { field : "firstname", title : "First Name", width : 100 },
	                
	                { field : "positionId", title : "Desgniation",  groupable : false, template: '#=positionId ? positionId.name: ""#', width : 100  },
	                { field : "salary", title : "Salary",   groupable : false, template: '#=positionId ? positionId.salary: ""#', width : 100  },
	                 {field : "taxdec", title : " Income Tax deducted", width : 100 },
	                {field : "remarks", title : " Remarks", width : 100 }
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


