<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">TaxAnnual</h2> 
<div id="grid">
	</div>
<div id="empForm">
</div>

<script> 


var empDataSource;



	
    $(document).ready(function(e){	

	
	        
	       empDataSource = new kendo.data.DataSource({
	        	transport : {
	           		read : {
	           			url : "<%=request.getContextPath() + "/do/ReadTaxAnnualAction"%>",
	           			dataType : "json",
	           			cache : false
	           		},
	                destroy : {
	                    url : "<%=request.getContextPath() + "/do/DeleteEmployeeAction"%>",
	                    dataType : 'json',
	                    contentType : 'application/json; charset=utf-8',
	                    type : 'POST',
	                    complete : function(jqXHR, textStatus){
	                    	alert(textStatus);
	                    }
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
	                
	                { field : "positionId", title : "Desgniation",  editor : positionDropDownEditor, groupable : false, template: '#=positionId ? positionId.name: ""#', width : 100  },
	                { field : "positionId", title : "AnnualSalary",  editor : positionDropDownEditor, groupable : false, template: '#=positionId ? positionId.salary: ""#', width : 100  }
	                
	                
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
    
    

    
    
    function positionDropDownEditor(container, options) {
        $('<input data-text-field="name" data-value-field="id" data-bind="value:' + options.field + '"/>').appendTo(container).kendoDropDownList({
            autoBind: false,
            dataSource: {
                type: "json",
                transport: {
                    read: "<%=request.getContextPath() + "/do/ReadPositionAction"%>"
                }
            }
        }); 
    } 
     
</script>  




