<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">Earnings Per Department</h2> 
<div id="grid">
</div>
<div id="searchIteam1">
<div id="searchIteam">
	<div class="clear"></div>
	<div id="left-col">
	<form>
<div>
			<div class="label">SelectType</div>
			<div class="field">
				<div id="selectval"></div>
			</div>
			<div class="clear"></div>
		</div>
			</form>
<span id="deparSpan">


<div>
			<div class="label">SelectDepartment</div>
			<div class="field">
	<input class="departDropDownList" id="departVal"/>
			</div>
			<div class="clear"></div>
		</div>
</span>

	<div>
			<div class="field">
				<a class="k-button k-icontext" id="saveEmp"><span class="k-add k-icon"></span>Search</a> <a
					class="k-button k-icontext" id="cancelEmp"><span class="k-cancel k-icon"></span>Cancel</a>
			</div>
			<div class="clear"></div>
		</div>

</div>
</div>
</div>

<script>
var empWindow;
var departDrp, selectDepar;
    $(document).ready(function(e){
		$("#searchIteam1").hide();
    	 var payPerdData = [
    	                    {text: "Select", value:"-1"},
    	                    {text: "All", value:"1"},
    	                    {text: "Department", value:"2"}
    	                ];
         var 	 empAll= new kendo.data.DataSource( {
             transport:{
                 read : {
                     url : "<%=request.getContextPath() + "/do/ReadEmployeEarnsreport?parameter=readEarns"%>",
                     dataType : 'json',
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
                      { field: "employeeId", title: "fullName", template: '#=employeeId ? employeeId.employeeId: ""#',  width: "100px" },
                
                { field: "employeeId", title: "fullName", template: '#=employeeId ? employeeId.firstname+"."+employeeId.middlename: ""#',  width: "100px" },
                { field: "employeeId", title: "Department", template: '#=employeeId ? employeeId.deptId.deptname: ""#',  width: "100px" },
                { field: "paidNetPay", title: "Amount Earned",  width: "100px" }
               
            ], 
          
            toolbar : [{"name" : "create",className : "searchBox", text : "Search"}],
            editable: "popup",
            sortable: true,
            scrollable: true,
            filterable : true,
            selectable : "row",
            pageable : true
            
        }); 
        $("#cancelEmp").bind("click", function() { 
			 empWindow.content('');
			 empWindow.close();	                	 
        }); 
        
      selectDepart=$("#selectval").kendoDropDownList({
            dataTextField: "text",
            dataValueField: "value",
            dataSource: payPerdData
        });
        
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
		
	
        $("#selectval").bind("change",function(){
        	
        	var val=$(this).val();
        	
          	if(val==2)
    		{
    		$("#deparSpan").show();
    		
    		}
    	else
    		{
       		$("#deparSpan").hide();
    
    		}
        	
        });
        $("#grid").delegate(".searchBox", "click", function(e) {
       	 e.preventDefault();	        	 
   		 createNewEmpForm();
   		
   		 empWindow.open();
   		 empWindow.center();
   		 

   		$("#deparSpan").hide();
   		
        });
        $("#saveEmp").bind("click", function() { 
        	
        	//alert("herllo");   
        	
        	var val=$("#selectval").val();
        	if(val==2)
    		{
    		
    		var dep=$("#departVal").val();
    		grid2Clal(dep);
    		 empWindow.content('');
			 empWindow.close();	
    		}
    	else
    		{
    		 empWindow.content('');
			 empWindow.close();	
		
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
                            	
                            	url : "<%=request.getContextPath() + "/do/ReadEmployeEarnsreport?parameter=getDepartAllEmp"%>",
                            	
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
                              { field: "employeeId", title: "EmpID",   width: "100px" },
                        
                        { field: "firstname", title: "fullName",   width: "100px" },
                        { field : "deptId", title : "Department", template: '#=deptId? deptId.deptname: ""#',   width: "100px"},
                     
                        {field : "payrol",  title: "Amount Earned", template: '#=payrol? payrol.paidNetPay: ""#',width : 50 },
                       
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
        createNewEmpForm = function (){        	         	 
	        
    		if(empWindow)
    			{empWindow.content("");}
    		
    	 	empWindow = $("#searchIteam").kendoWindow({
             title: "SearchForm",
           
             modal : true,
             resizable: true,
             width : 300,
				height : 150,
         }).data("kendoWindow");

    	 empWindow.open();
    	 empWindow.center();
    	 
     };
        
 });

</script>
