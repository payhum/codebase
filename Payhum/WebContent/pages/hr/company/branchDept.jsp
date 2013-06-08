
<%-- <%@include file="../../common/jspHeader.jsp"%>
 --%>
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

.displayClass{
	display : none !important;
}

.currentCompany{
	background-color : orange;
}

</style>

<script>

var createNewCmpForm;
var dropDownURL = "<%=request.getContextPath()%>" + "/do/ReadPositionAction"; 
var empDataSource;
var empWindow,vk;
var cmpFormPath = "<%=request.getContextPath()%>"+ "/pages/masteradmin/companyForm.jsp";
var branchFormPath = "<%=request.getContextPath()%>"+ "/pages/masteradmin/branchForm.jsp";
var deptFormPath = "<%=request.getContextPath()%>"+ "/pages/masteradmin/deptForm.jsp";


</script>



<div id="example" class="k-content">
	<div id="grid"></div>
	<div id="cmpForm"> </div>
  	<script type="text/x-kendo-template" id="template">
	
                <div class="tabstrip">
                    <ul> 
						<li class="k-state-active"> Branches</li>
                        <li> Departments </li>
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
                	
                 	var empDataSource;
                	
                	empDataSource = new kendo.data.DataSource({
                    	transport : {
                       		read : {
                       			url : "<%=request.getContextPath() + "/do/ReadCompanyAction"%>",
                       			dataType : "json",
                       			cache : false,
                        		}
                       	},
                        autoSync : true,
                       	batch : true 
                    });
                	
                	var grid = $("#grid").kendoGrid({            	             	 
                        dataSource		:  empDataSource,
                        height			: 430,
                        sortable		: true,
                        pageable		: true,
                        detailTemplate	: kendo.template($("#template").html()),
                        detailInit		: detailInit,       
        	            toolbar : [{"name" : "addBranch", className : "addBranch displayClass", text : "Add Branch" }, {"name" : "addDept", className : "addDept displayClass", text : "Add Department" }],

                         columns : [
 									{ template:'#=companyId.companyId ? companyId.id: ""#', width : 1},  								
									{ field : "companyId.companyId", title : " Company Id",    width : 100  },
									{ field : "companyId.name", title : "Company Name",    width : 100  },
				  		            { field : "active", title : "Status", template : "#= active != 0 ? 'Active' : 'Inactive' #", width : 100 },

 								  ]     
                	
                     });
                
                
                $("#grid").delegate(".newCmp", "click", function(e) {
   	        	 e.preventDefault();	        
   	        	 createNewCmpForm();
           		 empWindow.open();
           		 empWindow.center();       
   	           });
                

                $("#grid").delegate(".k-master-row", "click", function(e) {
                	$("#grid").find(".k-master-row").removeClass("currentCompany");
                	$(this).addClass('currentCompany');
                	$("#grid .k-grid-toolbar").find("a").each(function(){
                  		$(this).removeClass("displayClass");
                	}); 
    	         });
                
                $("#grid").delegate(".deleteCmp", "click", function(e) {
                    companyId = $(".currentCompany").find('td').eq(2).text();
                       var cmpl = JSON.stringify({
        	   			"compId"	  : companyId
          			 });   
                      
                     deleteStatus = confirm('Are you sure you want to delete ? ');
                     if(deleteStatus){
                  
	        			$.ajax({
	        				url 		: "<%=request.getContextPath() + "/do/DeleteCompany"%>",
	        				type 		: 'POST',
	        				dataType 	: 'json',
	        				contentType : 'application/json; charset=utf-8',
	        				data 		:  cmpl,
	        				success 	:  function(){
	        					empDataSource.read();
	        				},
	        				failure : function(e){
	        					alert(e.responseText);
	        				}
	                	});  
                     }
        	});
                
                $("#grid").delegate(".addBranch", "click", function(e) {
   	        	 e.preventDefault();	
   	        	 var cId = $("#grid").find(".currentCompany").find('td').eq(2).text();
   	        	  
   	        	  vk = cId;
	        	 
   	        	 createBranch();
   	        	 empWindow.open();
           		 empWindow.center();       
           		 empDataSource.read();
   	         });
   	         
   	         
   	         createBranch = function (){   
    	        	if(empWindow)
   	        		empWindow.content("");
   	        	 	empWindow = $("#cmpForm").kendoWindow({
   	                 title: vk,
   	                 content: branchFormPath,
   	                 modal : true,
   	                 resizable: false,
   	                 width : 650
   	             }).data("kendoWindow");
   	        	 
   	        	 empWindow.open();
   	        	$(".k-window-title").attr('style', "display:none !important;");
   	        	 
    	        	 empWindow.center();
    	         };
    	       
    	         $("#grid").delegate(".addDept", "click", function(e) {
    	        	 e.preventDefault();	        
    	        	 createDepartment();
            		 empWindow.open();
            		 empWindow.center();       
            		 empDataSource.read();
    	         });
    	         
    	         
    	         createDepartment = function (){        	         	 
    	        	if(empWindow)
    	        		empWindow.content("");
    	        	 	empWindow = $("#cmpForm").kendoWindow({
    	                 title: "",
    	                 content: deptFormPath,
    	                 modal : true,
    	                 resizable: false,
    	                 width : 650
    	             }).data("kendoWindow");
    	        	 
    	        	 empWindow.open();
    	        	 empWindow.center();
    	        	 
    	         };
                
                
            $("#grid").delegate(".editCmp", "click", function(e) {
            	 $("#companyForm").find("#from").kendoDatePicker();
            	 $("#companyForm").find("#to").kendoDatePicker();
	        	 	 e.preventDefault();
	        	 	 var dataItem = $("#grid").find(".currentCompany");
		        	 var cmpTemplate= kendo.template($("#companyTemplate").html());     
					 var cId = $("#grid").find(".currentCompany").find('td').eq(2).text();   
					 var name      = $("#grid").find(".currentCompany").find('td').eq(3).text();   
					  
		        	 $("#companyForm").find("#companyId").val(cId);
		        	 $("#companyForm").find("#companyName").val(name);
	                 var wnd = $("#cmpForm").kendoWindow({
	                     title: "",
	                     modal: true, 
	                     resizable: false,
		                 width : 700
	                 }).data("kendoWindow");          
                
					  
	                wnd.content(cmpTemplate(dataItem));
	                $("#companyForm").find("#companyId").val(cId);
		        	 $("#companyForm").find("#companyName").val(name);
		        	 $("#companyForm").find("#from").kendoDatePicker();
	            	 $("#companyForm").find("#to").kendoDatePicker();
	                $("#cancelCmp").click(function() { 
	                	 wnd.content('');
	                	 wnd.close();	                	 
	                }); 
	                 wnd.open(); 
	                 wnd.center();
	            
	                $("#updateEmp").bind("click", function() { 
	                	companyId = cId;
	         			name      = $("#companyName").val(); 
 	         			address   = "test";
 	         			var editComapany = JSON.stringify({
	         	   			"companyId"	  : companyId,
	         	   			"name" 	      : name, 
	         	   			"address"     : address
	         			 });   
	     				
	    				$.ajax({
	    					url 		: "<%=request.getContextPath() + "/do/EditCompany"%>",
	    					type 		: 'POST',
	    					dataType 	: 'json',
	    					contentType : 'application/json; charset=utf-8',
	    					data 		:  editComapany,
	    					success 	:  function(){
	    						$("#cancelCmp").click();
	    						empDataSource.read();
	    					},
	    					failure : function(e){
	    						alert(e.responseText);
	    					}
	    				});
	                });
            });

            $("#grid").delegate(".editLicense", "click", function(e) {
       	 	 e.preventDefault();
       	 	 var dataItem = $("#grid").find(".currentCompany");
	        	 var cmpTemplate= kendo.template($("#LicenseTemplate").html());     
				 var cId = $("#grid").find(".currentCompany").find('td').eq(2).text();   
				 var name      = $("#grid").find(".currentCompany").find('td').eq(3).text();
				 var company = JSON.stringify({
     	   			"companyId"	  : cId 
      			 });   
				 
				  
	        	 $("#LicenseForm").find("#companyId").val(cId);
	        	 $("#LicenseForm").find("#companyName").val(name);
	        	 
	        	 $("#LicenseForm").find("#fromDate").kendoDatePicker();
	        	 $("#LicenseForm").find("#toDate").kendoDatePicker();
	        	 
                var wnd = $("#cmpForm").kendoWindow({
                    title: "",
                    modal: true, 
                    resizable: false,
	                 width : 700
                }).data("kendoWindow");          
           
				  
               wnd.content(cmpTemplate(dataItem));
               $("#LicenseForm").find("#companyId").val(cId);
	        	 $("#LicenseForm").find("#companyName").val(name);
	        	 $("#LicenseForm").find("#fromDate").kendoDatePicker();
	        	 $("#LicenseForm").find("#toDate").kendoDatePicker();
               
               $("#cancelCmp").click(function() { 
               	 wnd.content('');
               	 wnd.close();	                	 
               }); 
                wnd.open(); 
                wnd.center();
           
               $("#updateLicense").bind("click", function() { 
               		companyId = cId;
        			from = $("#fromDate").val();
        			to  = $("#toDate").val();
         			 
        			 
         			var editComapany = JSON.stringify({
        	   			"companyId"	  : companyId,
        	   			 "fromdate"   : from,
  						 "todate"     : to		 
        			 });   
    				
   				$.ajax({
   					url 		: "<%=request.getContextPath() + "/do/EditLicense"%>",
   					type 		: 'POST',
   					dataType 	: 'json',
   					contentType : 'application/json; charset=utf-8',
   					data 		:  editComapany,
   					success 	:  function(){
   						$("#cancelCmp").click();
   						empDataSource.read();
   					},
   					failure : function(e){
   						alert(e.responseText);
   					}
   				});
               });
       });


            $("#grid").delegate(".dwndLicense", "click", function(e) {
            	var cId = $("#grid").find(".currentCompany").find('td').eq(2).text();
            	
            	$.ajax({
                    type: "POST",
                    url: "<%=request.getContextPath() + "/do/GenLicenseFile"%>",
                    data: {cId : cId},
                    success: function(data) {                
                        alert(data);
                    }
                });
            });
            
            $("#grid").delegate(".editBranch", "click", function(e) {
       	 		 var v= $(this).closest("tr");
       	 		 var branchId   = $(v).find('td').eq(0).text();
       	 		 var branchName = $(v).find('td').eq(1).text();
       	 	     var branchAdd  = $(v).find('td').eq(2).text();
             	 e.preventDefault();
       	 	     var dataItem = $("#grid").find(".currentCompany");
	        	 var cmpTemplate= kendo.template($("#branchTemplate").html());     
	        	 $("#branchForm").find("#bname").val(branchName);
	        	 $("#branchForm").find("#baddr").val(branchAdd);
                var wnd = $("#cmpForm").kendoWindow({
                    title: "Company Details",
                    modal: true, 
                    resizable: false,
	                 width : 700
                }).data("kendoWindow");          
           
				  
               wnd.content(cmpTemplate(dataItem));
               $("#branchForm").find("#bname").val(branchName);
	        	 $("#branchForm").find("#baddr").val(branchAdd);
               $("#cancelCmp").click(function() { 
               	 wnd.content('');
               	 wnd.close();	                	 
               }); 
                wnd.open(); 
                wnd.center();
           
               $("#updateBranch").bind("click", function() { 
               	    bid = branchId;
        			name      = $("#bname").val(); 
         			address   =  $("#baddr").val();
         			var editComapany = JSON.stringify({
        	   			"companyId"	  : bid,
        	   			"name" 	      : name, 
        	   			"address"     : address
        			 });   
    				
   				$.ajax({
   					url 		: "<%=request.getContextPath() + "/do/EditBranch"%>",
   					type 		: 'POST',
   					dataType 	: 'json',
   					contentType : 'application/json; charset=utf-8',
   					data 		:  editComapany,
   					success 	:  function(){
   						$("#cancelCmp").click();
   						empDataSource.read();
   					},
   					failure : function(e){
   						alert(e.responseText);
   					}
   				});
               });  
       });
            
            
            $("#grid").delegate(".editDept", "click", function(e) {
      	 		 var v= $(this).closest("tr");
      	 		 var deptId   = $(v).find('td').eq(0).text();
      	 		 var deptName = $(v).find('td').eq(1).text();
             	 e.preventDefault();
      	 	     var dataItem = $("#grid").find(".currentCompany");
	        	 var cmpTemplate= kendo.template($("#deptTemplate").html());     
	        	 $("#deptForm").find("#dname").val(deptName);
                 var wnd = $("#cmpForm").kendoWindow({
                   title: "",
                   modal: true, 
                   resizable: false,
	                 width : 700
               }).data("kendoWindow");          
          
				  
              wnd.content(cmpTemplate(dataItem));
              $("#deptForm").find("#dname").val(deptName);
              $("#cancelCmp").click(function() { 
              	 wnd.content('');
              	 wnd.close();	                	 
              }); 
               wnd.open(); 
               wnd.center();
          
              $("#updatedept").bind("click", function() { 
              	    did  = deptId;
       			    name = $("#dname").val(); 
         			var editComapany = JSON.stringify({
       	   			"companyId"	  : did,
       	   			"name" 	      : name, 
        			});   
   				
  				$.ajax({
  					url 		: "<%=request.getContextPath() + "/do/UpdateDept"%>",
  					type 		: 'POST',
  					dataType 	: 'json',
  					contentType : 'application/json; charset=utf-8',
  					data 		:  editComapany,
  					success 	:  function(){
  						$("#cancelCmp").click();
  						empDataSource.read();
  					},
  					failure : function(e){
  						alert(e.responseText);
  					}
  				});
              });  
      });
            
            $("#grid").delegate(".deleteDept", "click", function(e) {
         	   var v = $(this).closest("tr");
                var deptId   = $(v).find('td').eq(0).text();
 
                var cmpl = JSON.stringify({
 	   			"compId"	  : deptId
   			 }); 
             
                var deleteStatus = confirm('Are you sure you want to Delete ?');
           
            if(deleteStatus){
	 			$.ajax({
	 				url 		: "<%=request.getContextPath() + "/do/DeleteDept"%>",
	 				type 		: 'POST',
	 				dataType 	: 'json',
	 				contentType : 'application/json; charset=utf-8',
	 				data 		:  cmpl,
	 				success 	:  function(){
	 					empDataSource.read();
	 				},
	 				failure : function(e){
	 					alert(e.responseText);
	 				}
	         	});  
            }
 	});

            
            
            $("#grid").delegate(".deleteBranch", "click", function(e) {
            	   var v = $(this).closest("tr");
                   var branchId   = $(v).find('td').eq(0).text();
                    var cmpl = JSON.stringify({
    	   			"compId"	  : branchId
      		});   
            
            deleteStatus = confirm('Are you sure you want to Delete ?');
            if(deleteStatus){
    			$.ajax({
    				url 		: "<%=request.getContextPath() + "/do/DeleteBranch"%>",
    				type 		: 'POST',
    				dataType 	: 'json',
    				contentType : 'application/json; charset=utf-8',
    				data 		:  cmpl,
    				success 	:  function(){
    					empDataSource.read();
    				},
    				failure : function(e){
    					alert(e.responseText);
    				}
            	});  
            }
            });
            
   	         
   	         createNewCmpForm = function (){        	         	 
   	        	if(empWindow)
   	        		empWindow.content("");
   	        	 	empWindow = $("#cmpForm").kendoWindow({
   	                 title: "",
   	                 content: cmpFormPath,
   	                 modal : true,
   	                 resizable: false,
   	                 width : 650
   	             }).data("kendoWindow");
   	        	 
   	        	 empWindow.open();
   	        	 empWindow.center();
   	        	 
   	         };
   	         
                });
                
                 
                function detailInit(e) {
                  	var updateData  = JSON.stringify({
     					"id" : e.data.companyId.id
    				 });  
                     var detailRow = e.detailRow;

                    detailRow.find(".tabstrip").kendoTabStrip({
                        animation: {
                            open: { effects: "fadeIn" }
                        }
                    });

                    
                var empDataSource1 = new kendo.data.DataSource({
                	 transport : {
                    		read : {
                    			type		: 'POST',
		         				url 		: "<%=request.getContextPath() + "/do/BranchDetails"%>",
								dataType 	: 'json',
								contentType : 'application/json; charset=utf-8'
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

                
                
                var empDataSource111 = new kendo.data.DataSource({
               	 transport : {
                   		read : {
                   			type: 'POST',
		         				url : "<%=request.getContextPath() + "/do/DepartmentDetails"%>",
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
			detailRow.find(".orders").kendoGrid({
 								dataSource : empDataSource1,
 								scrollable : false,
								sortable : true,
								pageable : true,
								columns : [
										{ field : "id", title : "Branch	Id",template: '#=id ? "B-"+id : "" #', width : 120}, 
										{ field : "name", title : "Branch Name", width : 120},
										{ field : "address", title : "Branch Address", width : 120 },
										{
						                    command: [{name : "edit", text: "Edit", className: "editBranch"}, {name : "delete", text : "Delete", className: "deleteBranch" }], width:200, filterable:false
						                }

								]
							});
			
	 		detailRow.find(".orders1").kendoGrid({
 						dataSource : empDataSource111,
 						scrollable : false,
						sortable : true,
						pageable : true,
						columns : [ 
							{ field : "id", title : "Department	Id",template: '#=id ? "D-"+id : "" #', width : 120}, 
							{ field : "deptname", title : "Department Name", width : 120 },
							{ field : "branchId.name", title : "Branch Name", width : 120 },
							{
			                    command: [{name : "edit", text: "Edit", className: "editDept"}, {name : "delete", text : "Delete", className: "deleteDept" }], width:200, filterable:false
			                }
						 ]
					}); 
			
			
		}
	</script>
	
<script type="text/x-kendo-template" id="companyTemplate">

<div id="companyForm">
	<div class="clear"></div>
	<div style = "font-size : 30px !important;">Edit Company  </div><br/>
	<div class="clear"></div>
	<div id="left-col">
		 
		<div>
			<div class="label" style="width : 135px !important;">CompanyId</div>
			<div class="field">
				<input type="text" readonly="readonly" class="k-input k-textbox" id="companyId"  
				value=" ">
			</div>
			<div class="clear"></div>
		</div>


		<div>
			<div class="label" style="width : 135px !important;">Name</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="companyName"  value=" " />
			</div>
			<div class="clear"></div>
		</div>
 
		<br/>
		 
		<div>
			<div class="field">
			<a class="k-button k-icontext" id="updateEmp"><span class="k-icon k-update"></span>Update</a> 				
			<a class="k-button k-icontext" id="cancelCmp"><span class="k-cancel k-icon"></span>Cancel</a>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</div>
<div>
</div>

</script>

<script type="text/x-kendo-template" id="LicenseTemplate">
 

 <div id="LicenseForm">
	<div class="clear"></div>
<div style = "font-size : 30px !important;">Edit License  </div><br/>
	<div class="clear"></div>
	<div id="left-col">
		 
		<div>
			<div class="label" style="width : 135px !important;">CompanyId</div>
			<div class="field">
				<input type="text" class="k-input k-textbox" readonly="readonly" id="companyId" value="">
			</div>
			<div class="clear"></div>
		</div>


		<div>
			<div class="label" style="width : 135px !important;">Name</div>
			<div class="field">
				<input type="text" required=required readonly="readonly" class="k-input k-textbox" id="companyName"  value=" " />
			</div>
			<div class="clear"></div>
		</div> <br/>

		<div>
			<div class="label" style="width : 135px !important;">License Valid From</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="fromDate"  value="" />
			</div>
			<div class="clear"></div>
		</div><br/>
		
		<div>
			<div class="label" style="width : 135px !important;">License Valid To</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="toDate"  value="" />
			</div>
			<div class="clear"></div>
		</div><br/>	
		 
		<div>
			<div class="field">
			<a class="k-button k-icontext" id="updateLicense"><span class="k-icon k-update"></span>Update</a> 				
			<a class="k-button k-icontext" id="cancelCmp"><span class="k-cancel k-icon"></span>Cancel</a>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</div>
<div>
</div>

</script>




<script type="text/x-kendo-template" id="branchTemplate">

<div id="branchForm">
	<div class="clear"></div>
<div style = "font-size : 30px !important;">Edit Branch  </div><br/>
	<div class="clear"></div>
	<div id="left-col">
		 
		<div>
			<div class="label" style="width : 135px !important;">Branch Name</div>
			<div class="field">
				<input type="text" class="k-input k-textbox" id="bname"  
				value=" ">
			</div>
			<div class="clear"></div>
		</div>


		<div>
			<div class="label" style="width : 135px !important;">Branch Address</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="baddr"  value=" " />
			</div>
			<div class="clear"></div>
		</div>
 
		<br/>
		 
		<div>
			<div class="field">
			<a class="k-button k-icontext" id="updateBranch"><span class="k-icon k-update"></span>Update</a> 				
			<a class="k-button k-icontext" id="cancelCmp"><span class="k-cancel k-icon"></span>Cancel</a>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</div>
<div>
</div>

</script>

<script type="text/x-kendo-template" id="deptTemplate">

<div id="deptForm">
	<div class="clear"></div>
<div style = "font-size : 30px !important;">Edit Department</div><br/>
	<div class="clear"></div>
	<div id="left-col">
		 
		<div>
			<div class="label" style="width : 135px !important;">Department Name</div>
			<div class="field">
				<input type="text" class="k-input k-textbox" id="dname"  
				value=" ">
			</div>
			<div class="clear"></div>
		</div>
 
		<br/>
		 
		<div>
			<div class="field">
			<a class="k-button k-icontext" id="updatedept"><span class="k-icon k-update"></span>Update</a> 				
			<a class="k-button k-icontext" id="cancelCmp"><span class="k-cancel k-icon"></span>Cancel</a>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</div>
<div>
</div>

</script>

 

</div>

 



