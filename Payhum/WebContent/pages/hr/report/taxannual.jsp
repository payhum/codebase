<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">Tax Annual</h2> 
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


var taxDataSource;



	
    $(document).ready(function(e){	

	
	        
    	taxDataSource = new kendo.data.DataSource({
	        	transport : {
	           		read : {
	           			url : "<%=request.getContextPath() + "/do/ReadTaxAnnualAction"%>",
	           			dataType : "json",
	           			cache : false
	           		},
	           		group: {
                        field: "list", aggregates: [
                           { field: "ProductName", aggregate: "count" },
                           { field: "UnitPrice", aggregate: "sum"},
                           { field: "UnitsOnOrder", aggregate: "average" },
                           { field: "UnitsInStock", aggregate: "count" }
                        ]
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
					
	                
					{ field : "employeeId", title : "Id", width : 120 },
	                { field : "firstname", title : "Name", width : 150 },
	                
	                { field : "positionId", title : "Desgniation",   groupable : false, template: '#=positionId ? positionId.name: ""#', width : 100  },
	                { field : "positionId", title : "AnnualSalary",   groupable : false, template: '#=positionId ? positionId.salary: ""#', width : 100  },
	                
	                { field : "freeacom", title : "Value of free accomdation", width : 50 },
	                { field : "disburse", title : "Other disbursements", width : 50 },
	                { field : "sumsalary", title : "Total", width : 100 },
	                
	                { field : "sumGPF", title : "Sums  contributed to G.P.F", width : 50},
	                { field : "lifeinsurance", title : "Life Insuranse premium Paid", width : 50 },
	                
	                { field : "svaingsGovt", title : "Other savings recognized by Govt", width : 50 },
	                
	                { field : "sumGovt", title : "Total", width : 50 },
	                { field : "wifetax", title : "Wife earned Taxable income with in that Year", width : 50 },
	                {field : "childeren", title : "Children", width : 50 },
	                {field : "incometaxdec", title : "Amount Income Tax deducted", width : 50 }
	                ],             
	        
	                pageable: true,
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




