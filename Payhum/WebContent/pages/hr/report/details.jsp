
<%@include file="../../common/jspHeader.jsp"%>
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
	<div id="grid"></div>


    <script type="text/x-kendo-template" id="template">
                <div class="tabstrip">
                    <ul>
                        <li class="k-state-active">
                             Deductions 
                        </li>
                        <li>
                    Exemptions
                        </li>
                    </ul>
                    <div>
                                  <div class="orders"></div>
                    </div>
                    <div>
                        <div class='employee-details'>
                       <div class="orders1"></div>
                        </div>
                    </div>
                </div>

            </script>
	<script type="text/javascript">
                $(document).ready(function() {
    
                    //alert(detailInit);
                	var empDataSource;
                	
                	empDataSource = new kendo.data.DataSource({
                    	transport : {
                       		read : {
                       			url : "<%=request.getContextPath() + "/do/ReadPayrollSummary"%>",
                       			dataType : "json",
                       			cache : false
                       		}
                       	},
                      
                       	autoSync : true,
                       	batch : true 
                    });
                	var grid = $("#grid").kendoGrid({
            	 
            	 
                        dataSource:  empDataSource,
                        height: 430,
                        sortable: true,
                        pageable: true,
                        detailTemplate: kendo.template($("#template").html()),
                        detailInit: detailInit,
               
                        columns : [

{ template:'#=employeeId ? employeeId.id: ""#', width : 1},  
{ field : "employeeId", title : "Id",template:'#=employeeId ? employeeId.employeeId: ""#', width : 120 },
{ field : "employeeId", title : "Name", template:'#=employeeId ? employeeId.firstname+" "+employeeId.middlename: ""#', width : 100 },




{ field : "grossSalary", title : "Gross Pay",    width : 100  },

{ field : "baseSalary", title : "Tax Withheld",    width : 100  },

{ field : "totalDeductions", title : "Deductions",    width : 100  },
{ field : "netPay", title : "Net Pay",    width : 100  },

{ field : "baseSalary", title : "BaseSalary",    width : 100  }
                                    
                                   ]          
                           
                    });
                	
                	
             
                
                });
                
                function detailInit(e) {
                	//var a=$("#template").html();
                	//alert(a);
                	var updateData = JSON.stringify([{
        				
            			
    					"id":e.data.employeeId.id
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
		         				url : "<%=request.getContextPath() + "/do/TestPayDeatils?parameter=create"%>",
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

                
                
                var 	empDataSource111 = new kendo.data.DataSource({
               	 transport : {
                   		read : {
                   			type: 'POST',
		         				url : "<%=request.getContextPath() + "/do/TestPayDeatils?parameter=getExption"%>",
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
			detailRow
					.find(".orders")
					.kendoGrid(
							{

								dataSource : empDataSource1,

								scrollable : false,
								sortable : true,
								pageable : true,
								columns : [



{
	field : "type",

	title : "Name",
	width : 120,
	template:'#=type ? type.name: ""#',
},{
	field : "amount",

	title : "Amount",
	width : 120
}
								]
							});
			
	 		detailRow
			.find(".orders1")
			.kendoGrid(
					{

						dataSource : empDataSource111,

						scrollable : false,
						sortable : true,
						pageable : true,
						columns : [{
							field : "type",

							title : "Name",
							width : 120,
							template:'#=type ? type.name: ""#',
						},

{
field : "amount",

title : "Expetion amount",
width : 120
}

						]
					}); 
			
			
		}
	</script>

</div>



