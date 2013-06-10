<style type="text/css">
.k-grouping-header {
	display: none;
}
</style>

<%@include file="../../common/jspHeader.jsp"%>
<h2 class="legend">Tax Monthly</h2>
<div id="grid"><a href='<%=request.getContextPath() + "/do/PDFActions?parameter=taxMonthPdf"%>'>
<span id="rpsm"></span>
</a>

<span id="rpsm1">

<span id="selectval"></span>
		
		<span id="deparSpan">
		SelectDates<input class="payRollDatesDropDownList" id="payRollDates"/></span>
		
		<a class="k-button k-icontext" id="saveEmp"><span class="k-add k-icon"></span>Search</a> <a
					class="k-button k-icontext" id="cancelEmp"><span class="k-cancel k-icon"></span>Cancel</a>



</span>



</div>
<div id="empForm"></div>


<script> 


var empDataSource;

var croppingData;
var taxGrid;
var val=0;
croppingData = JSON.stringify({
		"id":val,

});

    $(document).ready(function(e){	

 
	       empDataSource = new kendo.data.DataSource({
	        	transport : {
	           		read : {
	           			type: 'POST',
	           			url : "<%=request.getContextPath() + "/do/ReadTaxMonthlyAction"%>",
	           			dataType : "json",
	           			contentType : 'application/json; charset=utf-8',
	           			cache : false
	           		},
	            
	           	   parameterMap: function (data, type) {
              			if(type = "read"){
              				
              				//alert(JSON.stringify(croppingData));
              			//alert(croppingData+"hello updateData"+type);
              				return croppingData;
              			}
                   }
	           	},
	          
	           	autoSync : true,
	           	batch : true 
	        });
	        
	        
 taxGrid=$("#grid").kendoGrid({
	            dataSource : empDataSource, 
	            columns : [
					
	                
					{ field : "emppayId", title : "Id", template: '#=emppayId? emppayId.employeeId.employeeId: ""#',width : 120 },
					{ field : "emppayId", title : "Name", template: '#=emppayId? emppayId.employeeId.firstname: ""#',width : 120 },
	                
					{ field : "emppayId", title : "Designation", template: '#=emppayId? emppayId.employeeId.positionId.name: ""#',width : 120 },
	                { field : "netPay", title : "Salary",    width : 100  },

	                {field : "taxAmount", title : "Income Tax Deducted", width : 100 }
	                ],             
	        
	                      
	                toolbar : [{text : " SelectType"}],
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
	         
	         
	
var payRollDatesDataSource1= new kendo.data.DataSource({
	  
	  transport : {

	read : {
	type: 'POST',
	 url:'<%=request.getContextPath()+ "/do/CommantypesAction?parameter=getPayRollDates"%>',
	 dataType : 'json',
	 contentType : 'application/json; charset=utf-8',
	 cache: false,
	 complete : function(jqXHR, textStatus){
		 // alert(jqXHR.responseText);
     }
}

	  },
autoSync : true,
	batch : true 
	
});
$(".payRollDatesDropDownList").kendoDropDownList({
	  
	  dataTextField : "runDate",
		dataValueField : "id",
		 optionLabel:"Select An Option",
		dataSource :payRollDatesDataSource1,
		
		
 }).data("kendoDropDownList");

$("#saveEmp").bind("click", function() { 
var payRollDates=$("#payRollDates").val();
 croppingData = JSON.stringify({
 		"id":payRollDates,
	
});
 empDataSource.read();
	
	        
});               
    });    
    
    

    
   
</script>


