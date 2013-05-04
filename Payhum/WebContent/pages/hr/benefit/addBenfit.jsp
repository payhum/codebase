<%@page import="com.openhr.employee.EmployeeIdUtility"%>
<%@page import="com.openhr.Config"%>
<%Config.readConfig();%>
<div id="employeeForm">
	<div class="clear"></div>
	<div style = "font-size : 30px !important;">Add Benfit  </div><br/>
	<div class="clear"></div>
	<div id="left-col">
 		<div>
			<div class="label" style="width : 135px !important;">Employee Id</div>
			<div class="field">
				<select id="employeeId" onchange="setEmpName(this);"></select>
 			</div>
			<div class="clear"></div>
		</div>


		<div>
			<div class="label" style="width : 135px !important;">Employee Name</div>
			<div class="field">
				<span name="empName" id="employeeName"/>
			</div>
			<div class="clear"></div>
		</div>
 
		<div>
			<div class="label" style="width : 135px !important;">Benfit Type</div>
			<div class="field">
				<select id="benfitType"></select>
 			</div>
			<div class="clear"></div>
		</div>
		
		<div>
			<div class="label" style="width : 135px !important;">Amount</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="amount"  value="" />
			</div>
			<div class="clear"></div>
		</div><br/>
    
		<div>
			<div class="field">
				<a class="k-button k-icontext" id="saveBenfit"><span class="k-add k-icon"></span>Save</a> 
				<a class="k-button k-icontext" id="cancelCmp"><span class="k-cancel k-icon"></span>Cancel</a>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</div>
<div></div> 


<script> 
	
		$(document).ready(function(){
			$("#employeeId").kendoDropDownList({
				dataTextField : "employeeId",
				dataValueField : "firstname",
 				optionLabel: "Select employee",
				dataSource : {
					type : "json",
					transport : {
						read : "<%=request.getContextPath() + "/do/ReadEmployeeDetails"%>"
					}
				}
		    });
			
			$("#benfitType").kendoDropDownList({
				dataTextField : "name",
				dataValueField : "id",
				optionLabel: "Select type",
				dataSource : {
					type : "json",
					transport : {
						read : "<%=request.getContextPath() + "/do/ReadBenefitTypeAction"%>"
					}
				}
		    })
		});
		
		
		function displayBenfits(){
			 var benefitModel = kendo.data.Model.define({
		            id: "id",
		            fields: {
		            	employeeId:{
		        			defaultValue : {
		        				id : 0,
		        				employeeId : "",
		        				firstname : "",
		        				middlename : "",
		        				lastname : "",        				
		        				sex : "",
		        				hiredate : "",
		        				birthdate : "",
		        				positionId : {
		        					defaultValue : {
		        						id : 0,
		        						name : "",
		        						salary : 0,
		        						raisePerYear : 0
		        					}
		        				},
		        				photo : "",
		        			}
		        		},
		            	typeId : {
		        			defaultValue : {
		        				id : 0,
		        				name : "",
		        				cap : 0
		        			}        		
		        		},
		            	amount : { 
		            		type: "number"/*,
		            		validation: { required: true} */
		        		}       		
		            }
		        });

			 benefitGrid = $("#grid").kendoGrid({
		            dataSource : {
		                transport:{
		                    read : {
		                        url : "<%=request.getContextPath() + "/do/ReadBenefitAction"%>",
		                        dataType : 'json',
		                        cache : false
		                    },
		                    
		                    parameterMap: function(options, operation) {
		                        if (operation !== "read" && options.models) {
		                            return JSON.stringify(options.models);
		                        }
		                    }
		                },
		                schema: {
		                    model: benefitModel
		                },
		                batch: true,
		                pageSize : 10
		            }, 
		            columns: [
						{
						    command: ["edit","destroy"], title: "&nbsp;", width: 70, filterable :false
						},
						{ title : "Photo", template: '<img  width=70 height=60 src="#=employeeId ? "/OpenHR" + employeeId.photo: ""#"/>',
								width : 60, filterable : false, editable : false, sortable : false },
		                { field : "employeeId", title : "Employee Id",  editor : employeeDropDownEditor, template: '#=employeeId ? employeeId.employeeId: ""#', width : 120 },
		                { title : "Full name",  template: '#=employeeId ? employeeId.firstname +" "+employeeId.middlename +" "+employeeId.lastname: ""#', width : 180 },               
		                { field : "typeId" , title : "Benefit Type",  editor : benefitTypeDropDownEditor, template: '#=typeId ? typeId.name: ""#', width : 120 },
		                { field: "amount", title:"Amount", width: 100}
		                
		            ], 
		            toolbar : [{"name" : "AddAssignBenfit",text : "Add Asign Benefit", className : "AddBenfit"}], 
		           editable: "popup", 
		            sortable: true,
		            scrollable: true,
		            filterable : true,
		            selectable : "row",
		            pageable : true
		            
		        }).data('kendoGrid'); 
			 

		        function employeeDropDownEditor(container, options) {
		            $('<input data-text-field="employeeId" data-value-field="id" data-bind="value:' + options.field + '"/>').appendTo(container).kendoDropDownList({
		                autoBind: false,
		                dataSource: {
		                    type: "json",
		                    transport: {
		                        read: "<%=request.getContextPath() + "/do/ReadEmployeeAction"%>"
		                    }
		                }
		            }); 
		        }
		        
		        
		        
		       
		        function benefitTypeDropDownEditor(container, options) {        	
		        	$('<input data-text-field="name" data-value-field="id" data-bind="value:' + options.field + '"/>').appendTo(container).kendoDropDownList({
		                autoBind: false,
		                dataSource: {
		                    type: "json",
		                    transport: {
		                        read: "<%=request.getContextPath() + "/do/ReadBenefitTypeAction"%>"
		                    }
		                }
		            }).data('kendoDropDownList'); 
		        }       
		        
		        
		};

		 
		$("#cancelCmp").bind("click", function() { 
 			empWindow.content('');
			empWindow.close();	                	 
   		}); 
			 
		$("#saveBenfit").bind("click", function() {    				
              		var empId      = $('#employeeId option:selected').text();
             		var benfitType = $("#benfitType").val();
         			var amount     =   $("#amount").val();   
         		 
         			var benfit = JSON.stringify({
         	   			"empId"	  	  : empId,
         	   			"benfitType"  : benfitType,
         	   			"amount"	  : amount
          			 });   
     				
    				$.ajax({
    					url 		: "<%=request.getContextPath() + "/do/BenefitAction"%>",
    					type 		: 'POST',
    					dataType 	: 'json',
    					contentType : 'application/json; charset=utf-8',
    					data 		:  benfit,
    					success 	:  function(){
    						 $("#grid").empty();
    						 displayBenfits();
    						 $("#cancelCmp").click();
    					},
    					failure : function(e){
    						alert(e.responseText);
    					}
    				});
      });	
		
		function setEmpName(empName){
			//empName.options[empName.selectedIndex].text;
			$("#employeeName").text($(empName).val());
		}
		
		
		
</script>
 
 