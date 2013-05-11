<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">Payroll Summary </h2> 
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
var empDataSource;




$(document).ready(function(e){	


        
       empDataSource = new kendo.data.DataSource({
        	transport : {
           		read : {
           			url : "<%=request.getContextPath() + "/do/ReadPayrollSummary"%>",
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
				
                
				{ field : "employeeId", title : "Id",template:'#=employeeId ? employeeId.employeeId: ""#', width : 120 },
                { field : "employeeId", title : "Name", template:'#=employeeId ? employeeId.firstname+" "+employeeId.middlename: ""#', width : 100 },
                
                
                
                { field : "baseSalary", title : " Hours",    width : 100  },
                { field : "grossSalary", title : " Gross Pay",    width : 100  },
                
                { field : "baseSalary", title : "Tax Withheld",    width : 100  },
                
                { field : "totalDeductions", title : "Deductions",    width : 100  },
                { field : "netPay", title : "Net Pay",    width : 100  },

                { field : "baseSalary", title : "BaseSalary",    width : 100  }
                 
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
