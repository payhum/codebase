<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">Employees By Dept</h2> 
<style type="text/css">
.k-grouping-header {
	display: none;
}
</style>

<style scoped="scoped" type="text/css">
.k-detail-cell .k-tabstrip .k-content {
	padding: 0.2em;
}

.employee-details ul {
	list-style: none;
	font-style: italic;
	margin: 15px;
	padding: 0;
}

.employee-details ul li {
	margin: 0;
	line-height: 1.7em;
}

.employee-details label {
	display: inline-block;
	width: 90px;
	padding-right: 10px;
	text-align: right;
	font-style: normal;
	font-weight: bold;
}
</style>
<div id="example" class="k-content">
<div id="grid"><a href="#">
<span id="rpsm"></span>
<span id="rpsm1">Export PDF Report</span></a>
	</div>
<div id="empForm">
</div>

<script type="text/x-kendo-template" id="template">
                <div class="tabstrip">
                    <ul>
                        <li class="k-state-active">
                          More Info
                        </li>
                       
                    </ul>
                    <div>
                        <div class="orders"></div>
                    </div>
                    <div>
                      
                    </div>
                </div>

            </script>
<script> 


var empDataSource11;



	
    $(document).ready(function(e){	

	
	        
	       empDataSource11 = new kendo.data.DataSource({
	        	transport : {
	           		read : {
	           			url : "<%=request.getContextPath() + "/do/EmployeeDepartAction"%>",
	           			dataType : "json",
	           			cache : false
	           		}
	            
	             
	           	},
	          
	           	autoSync : true,
	           	batch : true 
	        });
	        
	        
$("#grid").kendoGrid({
	            dataSource : empDataSource11, 
	            detailTemplate: kendo.template($("#template").html()),
                detailInit: detailInit,
	            columns : [
					
	                
					{hidden:true, field : "id", title : "Id", width : 120 },
	                { field : "depname", title : "DepartName", width : 100 },
	                

	                {field : "total", title : " Total No Of Employee", width : 100 }
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
    
    function detailInit(e) {
    	//var a=$("#template").html();
    	//alert(a);
    	var updateData = JSON.stringify([{
			
			
			"id":e.data.id
		 }]);  
    	//alert(e+"helloo"+e.data.employeeId.employeeId);
        var detailRow = e.detailRow;

        detailRow.find(".tabstrip").kendoTabStrip({
            animation: {
                open: { effects: "fadeIn" }
            }
        });

        
    var 	empDataSource1 = new kendo.data.DataSource({
    	 transport : {
        		read : {
        			type: 'POST',
     				url : "<%=request.getContextPath() + "/do/EmployeeCommanAction?parameter=create"%>",
					dataType : 'json',
					contentType : 'application/json; charset=utf-8',
					cache : false

				},

				parameterMap : function(data, type) {
					if (type = "read") {

						return updateData;
					}

				}
			},

			autoSync : true,
			batch : true
		});

detailRow.find(".orders").kendoGrid(
				{

					dataSource : empDataSource1,

					scrollable : false,
					sortable : true,
					pageable : true,
					columns : [

							{
								
								field : "employeeId",
								title : "Id",
								template : '#=employeeId ? employeeId.employeeId: ""#',
								width : 120
							},
							{
								
								field : "employeeId",
								title : "Name",
								template : '#=employeeId ? employeeId.firstname+" "+employeeId.middlename: ""#',
								width : 100
							}

					]
				});
}

    
   
</script>
</div>