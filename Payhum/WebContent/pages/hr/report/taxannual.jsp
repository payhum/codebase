<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">Tax Annual</h2> 
<style type="text/css">
.k-grouping-header {
	display: none;
}
</style>

<div id="grid">


<a href='<%=request.getContextPath() + "/do/PDFActions?parameter=taxAnnualPdf"%>'> <span id="rpsm"></span> <span id="rpsm1">Export
				PDF Report</span></a>

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
					
	                
					
	                
	                { field : "epay", title : "Employee Id",   groupable : false, template: '#=epay? epay.employeeId.employeeId: ""#', width : 100  },
	                { field : "epay", title : "Name",   groupable : false, template: '#=epay? epay.fullName: ""#', width : 100  },
	                //{ field : "epay", title : "Designation",   groupable : false, template: '#=epay? epay.employeeId.positionId.name: ""#', width : 100  },
	              
	                { field : "epay", title : "Base Salary",   groupable : false, template: '#=epay? epay.baseSalary: ""#', width : 50  },
	                
	                //{ field : "epay", title : "Accomodation Amount",   groupable : false, template: '#=epay? epay.accomodationAmount: ""#', width : 100  },
	               
	                //{ field : "epay", title : " Others",   groupable : false, template: '#=epay? epay.bonus: ""#', width : 50  },
	                
	                { field : "epay", title : "Total Income",   groupable : false, template: '#=epay? epay.totalIncome: ""#', width : 50  },
	                { field : "epay", title : "Taxable Income",   groupable : false, template: '#=epay? epay.taxableIncome: ""#', width : 50  },
	                //{ field : "lifeinsurance", title : "Life Insuranse premium Paid", width : 100 },
	                
	                
	                //{field : "spouse", title : "Spouse", width : 50 },
	                //{field : "childeren", title : "Children", width : 50 },
	                { field : "epay", title : "Tax Amount",   groupable : false, template: '#=epay? epay.taxAmount: ""#', width : 50  },

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




