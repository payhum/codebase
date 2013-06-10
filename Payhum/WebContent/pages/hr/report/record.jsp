<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">Employee Accumulators</h2> 
<div id="grid">


<a href='#' id="pdfEmp1"> <span id="rpsm"></span> </a>
<span id="rpsm1">

<span id="selectval"></span>
		
		<span id="deparSpan">
		SelectDepartment<input class="departDropDownList" id="departVal"/></span>
		
		<a class="k-button k-icontext" id="saveEmp"><span class="k-add k-icon"></span>Search</a> <a
					class="k-button k-icontext" id="cancelEmp"><span class="k-cancel k-icon"></span>Cancel</a>


</span>

				

</div>

			


<script>
var empWindow;
var departDrp, selectDepar;
var depClass;
    $(document).ready(function(e){
	$("#deparSpan").hide();
    	 var payPerdData = [
    	                    {text: "Select", value:"-1"},
    	                    {text: "All", value:"1"},
    	                    {text: "Department", value:"2"}
    	                ];
         var 	 empAll= new kendo.data.DataSource( {
             transport:{
                 read : {
                	 type: 'POST',
                     url : "<%=request.getContextPath() + "/do/ReadEmployeeAccumulators?parameter=readAccumulators"%>",
                    		 dataType : 'json',
                    		 contentType : 'application/json; charset=utf-8',
                    		
                     cache : false
                 },
                 
                 parameterMap: function(options, operation) {
                     if (operation !== "read" && options.models) {
                         return JSON.stringify(options.models);
                     }
                 }
             },
             
             failure : function(reponse){
             	alert(response.responseText());
             },
             batch: true,
             pageSize : 10
         });
        $("#grid").kendoGrid({
            dataSource :empAll, 
            columns: [
{ field:"employeeId", title: "EmpID",  width: "100px" },
{ field:"firstname", title: "Name",  width: "100px" },        
                { field:"count", title: "LeavesDays",  width: "100px" },
                {field : "payrol",  title:"tilldate", template: '#=payrol? payrol.paidNetPay: ""#',width : 50 },
                {field : "payrol",  title:"pending", template: '#=payrol? payrol.netPay-payrol.paidNetPay: ""#',width : 50 },
                { field : "deptId", title : "Department", template: '#=deptId? deptId.deptname: ""#',   width: "100px"},
            ], 
          
            toolbar : [{text : " SelectType"}],
            editable: "popup",
            sortable: true,
            scrollable: true,
            filterable : true,
            selectable : "row",
            pageable : true
            
        }); 
      function branchDropDown ()
      {
    	   
    	  selectDepart=$("#selectval").kendoDropDownList({
              dataTextField: "text",
              dataValueField: "value",
              dataSource: payPerdData
          });
          
      }
        
      branchDropDown();
      
      function departDropDown()
      {
    	
    	var BrachData = JSON.stringify({
			
			
			"Id":0
		 }); 
        var 	branchDepartDataSource1 = new kendo.data.DataSource({
       	 transport : {
           		read : {
           			type: 'POST',
           			 url:'<%=request.getContextPath()+ "/do/CommantypesAction?parameter=getAllBrachDepart"%>',
       				 dataType : 'json',
       				 contentType : 'application/json; charset=utf-8',
       				 cache: false,
       				 
       		},
            
           		parameterMap: function (data, type) {
           			if(type = "read"){
           			//	alert(BrachData+"hello updateData");
           				return BrachData;
           			}
           			
           		}
           	},
          
             
              	autoSync : true,
              	batch : true 
           });
		
        
		departDrp=$(".departDropDownList").kendoDropDownList({
			dataTextField : "deptname",
			dataValueField : "id",
			optionLabel: "Select Depart",
			dataSource :branchDepartDataSource1
		
	       }).data("kendoDropDownList");
	
	
		
      }		
      departDropDown();
      
      
      $("#selectval").bind("change",function(){
        	
	
    		 
    	
        	var val=$(this).val();
        	//alert(val);
          	if(val==2)
    		{
    		$("#deparSpan").show();
    		
    		}
    	else
    		{
       		$("#deparSpan").hide();
    
    		}
        	
        });
        $("#cancelEmp").bind("click", function() { 
        	
      	$("#deparSpan").hide();  	 
        }); 
        $("#saveEmp").bind("click", function() { 
        	
        	//alert("herllo");   
        	
        	var val=$("#selectval").val();
        	if(val==2)
    		{
    		
    		var dep=$("#departVal").val();
    		grid2Clal(dep);
    		
    		}
    	else
    		{
    		
			 empAll.read();
    		}
        
        });
        
     function  grid2Clal(dep)
        {
        	alert(dep);
         	var DepartData = JSON.stringify({
    			
    			
    			"Id":dep
    		 });
            $(document).ready(function(e){
        	
            	
                $("#grid").kendoGrid({
                    dataSource : {
                        transport:{
                            read : {
                            	type: 'POST',
                            	
                            	url : "<%=request.getContextPath() + "/do/ReadEmployeeAccumulators?parameter=getDepartAllAcum"%>",
                            	
                                dataType : 'json',
                                contentType : 'application/json; charset=utf-8',
                  				 cache: false,
                            },
                            
                            parameterMap: function (data, type) {
                       			if(type = "read"){
                       			//	alert(BrachData+"hello updateData");
                       				return DepartData;
                       			}
                            }
                        },
                       
                        failure : function(reponse){
                        	alert(response.responseText());
                        },
                        batch: true,
                        pageSize : 5
                    }, 
                    columns: [
{ field:"employeeId", title: "EmpID",  width: "100px" },
{ field:"firstname", title: "Name",  width: "100px" },        
                { field:"count", title: "LeavesDays",  width: "100px" },
                {field : "payrol",  title:"tilldate", template: '#=payrol? payrol.paidNetPay: ""#',width : 50 },
                {field : "payrol",  title:"pending", template: '#=payrol? payrol.netPay-payrol.paidNetPay: ""#',width : 50 },
                { field : "deptId", title : "Department", template: '#=deptId? deptId.deptname: ""#',   width: "100px"},
            ], 
                  
                    toolbar : [{"name" : "create",className : "searchBox", text : "Search"}],
                    editable: "popup",
                    sortable: true,
                    scrollable: true,
                    filterable : true,
                    selectable : "row",
                    pageable : true
                    
                }); 
            });
        	
        }
     $("#pdfEmp1").bind("click", function(){
    	 
         
    	 window.location.href='<%=request.getContextPath() + "/do/PDFActions?parameter=empAcumPDF"%>';
    		

     
     });
 });

</script>
