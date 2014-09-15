<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">GLReports Employee View </h2> 

<style type="text/css">
.k-grouping-header {
	display: none;
}
</style>
<div id="grid">

	
	<a href='<%=request.getContextPath() + "/do/PDFActions?parameter=employeeViewPDF"%>'> <span id="rpsm"></span> <span id="rpsm1">Export
				PDF Report</span></a>
	
	</div>
<div id="empForm">
</div>
<script> 

var createNewEmpForm;
var pdfOBjt;

var empWindow;

var taxDataSource;

var empFormPath = "<%=request.getContextPath()%>"+ "/pages/hr/report/employeeviewform.jsp";

	
    $(document).ready(function(e){	

	
	        
    	taxDataSource = new kendo.data.DataSource({
	        	transport : {
	           		read : {
	           			url : "<%=request.getContextPath() + "/do/ReadEmployeeViewAction"%>",
	           			dataType : "json",
	           			cache : false,
	           			complete: function (jqXHR, responseText) {pdfOBjt=jqXHR.responseText;}
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
	        
    	$("#grid").delegate(".newEmp", "click", function(e) {
       	 e.preventDefault();	        	 
   		 createNewEmpForm();
   		 empWindow.open();
   		 empWindow.center();        	 
        });
        
        
       createNewEmpForm = function (){        	         	 
       	if(empWindow)
       		empWindow.content("");
       	 	empWindow = $("#empForm").kendoWindow({
                title: "General Ledger Employee View",
                content: empFormPath,
                modal : true,
                resizable: false,
                width : 650
            }).data("kendoWindow");
       	 
       	 empWindow.open();
       	 empWindow.center();
       	 
        };
$("#grid").kendoGrid({
	            dataSource : taxDataSource, 
	            columns : [
					
	                
					
	                { field : "empName", title : "Employee Name", width : 150 },
	                { field : "ename", title : "Name", width : 120 },
	               
	                
	                { field : "debit", title : "Debit", width : 50 },
	                { field : "credt", title : "Credit", width : 50 }
	              
	                	               
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
    $("#pdfEmp1").bind("click", function(){
    	
    	
    	
    	
    	
    	
    
    	
    	
    	
    	
    	
    	
    }); 
    </script>  


