<style type="text/css">
.k-grouping-header {
	display: none;
}
</style>
<%@include file="../../common/jspHeader.jsp"%>
<h2 class="legend">Employee Form</h2>

<div id="depdentsWinId" style="display: none">

	<div>
		<div id="gridDepdent"></div>

		<div id="left-col">
			<div class="label">Name</div>
			<div class="field">
				<input type="text" required="required" class="k-input k-textbox"
					id="depdentName" value="" />
			</div>
			<div class="clear"></div>
			<div class="label">Age</div>
			<div class="field">


				<input id="depdentAge" type="number" class="k-input k-textbox"
					step="2" value="" min="1" max="90" />
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div id="right-col">
		<div class="label">Occupation</div>
		<div class="field">
			<input id="occupationTypeDepdent" class="occupationTypeDepdent" />
		</div>
		<div class="clear"></div>
		<div class="label">Relationship</div>
		<div class="field">
			<input id="depdentTypeDepdent" class="depdentTypeDepdent" />
		</div>
		<div class="clear"></div>
	</div>

	<div>
		<div class="field">
			<a class="k-button k-icontext" id="addDepdent"><span
				class="k-add k-icon"></span>Save</a> <a class="k-button k-icontext"
				id="cancellAll"><span class="k-cancel k-icon"></span>Cancel</a>
		</div>
		<div class="clear"></div>

	</div>

</div>
<div id="changeSalryWinId" style="display: none">

	<div>
		<div class="label">Current Salary</div>
		<div class="field">
			<input type="text" class="k-input k-textbox" id="curSalary" readonly />
		</div>
		<div class="clear"></div>
	</div>
	<div>


		<div class="label">Effective Salary</div>
		<div class="field">
			<input type="text" required="required" class="k-input k-textbox"
				id="chageSal" value="" />
		</div>
		<div class="clear"></div>
	</div>
	<div>
		<div class="label">Effective Date</div>
		<div class="field">
			<input id="changeSalaryDate" />
		</div>
		<div class="clear"></div>
	</div>
	<div>
		<div class="field">
			<a class="k-button k-icontext" id="addchangeSal"><span
				class="k-add k-icon"></span>Save</a> <a class="k-button k-icontext"
				id="cancellAll"><span class="k-cancel k-icon"></span>Cancel</a>
		</div>
		<div class="clear"></div>

	</div>

</div>
<div id="giveBonusWinId" style="display: none">

	<div>
		<div class="label">Amount</div>
		<div class="field">
			<input type="text" required="required" class="k-input k-textbox"
				id="bonusAmont" value="" />
		</div>
		<div class="clear"></div>
	</div>
	<div>
		<div class="label">Given Date</div>
		<div class="field">
			<input id="bonusDate" />
		</div>
		<div class="clear"></div>
	</div>
	<div>
		<div class="field">
			<a class="k-button k-icontext" id="addBonus"><span
				class="k-add k-icon"></span>Save</a> <a class="k-button k-icontext"
				id="cancellAll"><span class="k-cancel k-icon"></span>Cancel</a>
		</div>
		<div class="clear"></div>

	</div>
</div>
<div id="bankAccountWinId" style="display: none">

	<div>
		<div class="label">Bank Name</div>
		<div class="field">
			<input type="text" required="required" class="k-input k-textbox"
				id="bankName" value="" />
		</div>
		<div class="clear"></div>
	</div>

	<div>
		<div class="label">Bank Branch</div>
		<div class="field">
			<input type="text" required="required" class="k-input k-textbox"
				id="bankBranch" value="" />
		</div>
		<div class="clear"></div>
	</div>


	<div>
		<div class="label">Account No</div>
		<div class="field">
			<input type="text" required="required" class="k-input k-textbox"
				id="accNo" value="" />
		</div>
		<div class="clear"></div>
	</div>

	<div>
		<div class="field">
			<a class="k-button k-icontext" id="addBank"><span
				class="k-add k-icon"></span>Save</a> <a class="k-button k-icontext"
				id="cancellAll"><span class="k-cancel k-icon"></span>Cancel</a>
		</div>
		<div class="clear"></div>

	</div>
</div>

<div id="grid">

	<span id="rpsm1"> <a class="k-button" id="depdentsId" href='#'><span
			class="k-icon "></span>Dependents</a> <a class="k-button" href='#'
		id="changeSalryId"><span class="k-icon "></span>Change Salary</a> <a
		class="k-button" href='#' id="giveBonusId"><span class="k-icon"></span>Give
			Bonus</a> <a class="k-button" href='#' id="bankAccountId"><span
			class="k-icon "></span>Bank Account</a> <a class="k-button" href='#'
		id="editEmp1"><span class="k-icon "></span>Edit</a>
		
	</span>

</div>
<div id="empForm"></div>
<script type="text/javascript"> 
var createNewEmpForm;
var dropDownURL = "<%=request.getContextPath()%>" + "/do/ReadPositionAction"; 
var empDataSource;
var empWindow;
var wndDepdent;
var wndBankAccount;
var wndChangeSalary;
var wndBonus;
var dependWindow;
var empFormPath = "<%=request.getContextPath()%>"+ "/pages/hr/employee/employeeForm.jsp";
var tempEmpid;
var croppingData;
var depdentAge;
 var depdentName;
 var depdentTypeDepdent;
 var prvsId;
 var prvsSal;
 var bankId;
 
var postDropDownList;
 var occupationTypeDepdent=  $("#occupationTypeDepdent").val();
    $(document).ready(function(e){	
    	
 		var today = new Date();
 		 $("#changeSalaryDate").kendoDatePicker({value : today});
 		 $("#bonusDate").kendoDatePicker({value : today});
 		 $("#passNo").kendoNumericTextBox();
 		$("#passExpDate").kendoDatePicker({value : today
		});
    	$("#rpsm1").hide();
	        var employeeModel = kendo.data.Model.define({
	        	
	        	fields :{        		
	        		employeeId : {
	        			type : "string",
	        			validation : {
	        				required : true
	        			}
	        		},
	        		firstname : {
	        			type : "string",
	        			validation : {
	        				required : true
	        			}
	        		},
	        		middlename : {
	        			type : "string",
	        			validation : {
	        				required : true
	        			}
	        		},
	        		lastname : {
	        			type : "string",
	        			validation : {
	        				required : true
	        			}
	        		},
	        		sex : {
	        			type : "string",
	        			validation : {
	        				required : true
	        			},
	        			defaultValue :  "Male" 
	        		},
	        		birthdate : {
	        		
	        			validation : {
	        				required : true
	        			}
	        		},
	        		hiredate : {
	        			
	        			validation : {
	        				required : true
	        			}
	        		},
	        		positionId :{
	        			defaultValue : {
	        				id : 0,
	        				name : "",
	        				salary : 0,
	        				raisePerYear : 0
	        			}
	        		},
	        		photo: {
	        			type : "string",
	        			validation : {
	        				required : true
	        			}
	        		},
	        		status: {
	        			type : "string",
	        			validation : {
	        				required : true
	        			},
	        			defaultValue : "ACTIVE"
	        		}
	        	}
	        });
	
	        
	       empDataSource = new kendo.data.DataSource({
	    	   pageSize:3,
	        	transport : {
	           		read : {
	           			url : "<%=request.getContextPath() + "/do/ReadEmployeeAction"%>",
	           			dataType : "json",
	           			cache : false
	           		},
	                destroy : {
	                    url : "<%=request.getContextPath() + "/do/DeleteEmployeeAction"%>",
	                    dataType : 'json',
	                    contentType : 'application/json; charset=utf-8',
	                    type : 'POST',
	                    complete : function(jqXHR, textStatus){
	                    	alert(textStatus);
	                    }
	                },
	                parameterMap : function(options, operation){
	           			if(operation !== "read" && options.models){	           				           				
	           				return JSON.stringify(options.models);
	           			}
	           		}
	           	},
	           	schema : {
	           		model : employeeModel
	           	},
	           	autoSync : true,
	           	batch : true 
	        });
	        
	        
	         var grid = $("#grid").kendoGrid({
	            dataSource : empDataSource, 
	            columns : [
					{hidden:true ,command : [{"name" : "edit", className : "editEmp"}], width : 40, filterable :false },
	               
					 { hidden:true,field : "id", title : "id"},
					
					{ field : "photo", title : "Photo" ,template : "<img width=67 height=50 src='#='/OpenHR'+photo#'/>", width : 40},
					{field : "employeeId",  title:"Employee ID",width : 50 },
	                { field : "firstname", title : "First Name", width : 50 },
	                { hidden :true,field : "middlename", title : "Middle Name", width : 50 },
	                {field : "payrol",  title:"Accomodation Type", template: '#=payrol? payrol.accomodationType.name: ""#',width : 50 },
	                { field : "residentType", title : "Resident Type",  template: '#=residentType ? residentType.name: ""#', width : 40 },
	                { hidden :true,field :"empNationalID", title : "empNationalID",  width : 20 },
	                { hidden :true,field : "married", title : "married",  width : 20 },
	               
	                { field : "empsal", title : "Salary", template: '#=empsal?empsal.basesalary: ""#', width : 30 },
	                { field : "sex", title : "Sex",  width : 30 },
	                { field : "positionIds", title : "Position",  template: '#=positionIds ? positionIds.name: ""#', width : 40  },
	                { hidden:true, field : "birthdate", title : "Birth date", template : "#= kendo.toString(new Date(birthdate), 'MMM, dd yyyy') #" },
	                {hidden:true,  field : "hiredate", title : "Hired date", template : "#= kendo.toString(new Date(hiredate) , 'MMM, dd yyyy') #" },
	                { field : "deptId", title : "Branch", template: '#=deptId? deptId.branchId.name: ""#',  width : 40 },
	                
	                { field : "status", title : "Status", width : 30 }
	            ],             
	            dataBound : function(){
	            	$.each(empDataSource.data(), function(){  
	            		this.birthdate = new Date(this.birthdate);
	            		this.hiredate = new Date(this.hiredate);	            		
	            	});
	            },
	            
	            toolbar : [{"name" : "create", className : "newEmp", text : "Add New Employee" }, {"name" : "upload", className : "upldEmpFile", text : "Upload Employee Data File" }],
	            sortable: true,  
	            scrollable : true,
	            height : 400,
	            pageable:true,
	           
	            resizeable : true,
	            reorderable: true,
	            selectable : "row"
	            
	        }).data("kendoGrid");
	    
	         
	         function reuseDepGrid()
	         {
	        	 
	        	 
	        	 var BrachData = JSON.stringify({
		 				
		   				
		 				"ID":tempEmpid
		 			 });
		       	var  depdentDataSource = new kendo.data.DataSource({
		       	 pageSize:3,
		         	 transport : {
		             		read : {
		             			type: 'POST',
		             			url : "<%=request.getContextPath()
					+ "/do/EmployeeCommanAction?parameter=getEmpDepdents"%>",
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
		         	 
		         	 
		         	// alert(depdentDataSource);
		 	        var dedd= $("#gridDepdent").kendoGrid({ 
		 	        	
		 	        	 dataSource : depdentDataSource, 
		 		            columns : [
		 						
		 		               { field : "name", title :"Name" ,width : 50},
		 						{field : "age",  title:"Age",width : 50 },
		 		              {field : "occupationType",  template: '#=occupationType ? occupationType.name: ""#', title:"Occupation", width : 50 },
		 		             {field : "depType",  template: '#=depType ? depType.name: ""#', title:"Relationship", width : 50 },
		 		              
		 						{ command: [ {"name" :"destroy", text:"", className:"delTax"}], title: "", width: "50px" }],
		 	        	 
		 	        	 
		 						 sortable: true,  
		 			            scrollable : true,
		 			            
		 			            pageable:true,
		 			           
		 			            resizeable : true,
		 			            reorderable: true,
		 			          
		 		           
		 		         
		 		            selectable : "row"}).data("kendoGrid");
	         }
	         $("#depdentsId").bind("click", function (e) {        
              e.preventDefault();
              $("#depdentsWinId").css("display",
				"block");
            // alert(tempEmpid);
      
		         createWndDepdent();
	        	 wndDepdent.open();
	        	 wndDepdent.center();
	        	 
	$("#depdentAge").kendoNumericTextBox();
	        	 
	        	 reuseDepGrid();
	
	        	 
	        	 
		 	        
		 	  	 var occupationTypeDepdentSource= new kendo.data.DataSource({
					  
					  transport : {
				  
	        		read : {
	      			type: 'POST',
	      			 url:'<%=request.getContextPath()
					+ "/do/CommantypesAction?parameter=getOccupationType"%>',
	  				 dataType : 'json',
	  				 contentType : 'application/json; charset=utf-8',
	  				 cache: false
	  				 
	      		}
				  
					  },
	      		autoSync : true,
	             	batch : true 
	             	
				  });
		 	        
		 	  	$(".occupationTypeDepdent").html('');
		 	        
		 	       $(".occupationTypeDepdent").kendoDropDownList({
						  
						  dataTextField : "name",
							dataValueField : "id",
							optionLabel: "Select Occupation",
							dataSource :occupationTypeDepdentSource
							
				       }).data("kendoDropDownList");
		 	      
			 	  	 var depdentTypeDepdentSource= new kendo.data.DataSource({
						  
						  transport : {
					  
		        		read : {
		      			type: 'POST',
		      			 url:'<%=request.getContextPath()
					+ "/do/CommantypesAction?parameter=getdepdentType"%>',
		  				 dataType : 'json',
		  				 contentType : 'application/json; charset=utf-8',
		  				 cache: false
		  				 
		      		}
					  
						  },
		      		autoSync : true,
		             	batch : true 
		             	
					  });
			 	        
				 	  	$(".depdentTypeDepdent").html('');
			 	        
			 	       $(".depdentTypeDepdent").kendoDropDownList({
							  
							  dataTextField : "name",
								dataValueField : "id",
								optionLabel: "Select Relationship",
								dataSource :depdentTypeDepdentSource
								
					       }).data("kendoDropDownList");
			 	       
			 	       
	         });
	             
	         
	         $("#addDepdent").bind("click", function () { 
	 	    	  
	 	    	  
	        	 resueDep();
	        	 
		 	    
		 	      });
	         function resueDep()
	 	       {
	 	    	 //  alert("hello");
depdentAge=$("#depdentAge").val();
	 	    	 depdentName=  $("#depdentName").val();
	 	  depdentTypeDepdent=$("#depdentTypeDepdent").val();
	 	      
	 	   occupationTypeDepdent=  $("#occupationTypeDepdent").val();
	 	      
	 	    croppingData = JSON.stringify([{
	 	    		"id":tempEmpid,
				"age" : depdentAge,
				"name" : depdentName,
				"depType" : depdentTypeDepdent,
				"occupationType" : occupationTypeDepdent
			}]);
	 	       
	 		$.ajax({
	    		url:'<%=request.getContextPath()
					+ "/do/EmployeeCommanAction?parameter=saveDepdents"%>',
			type : 'POST',
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			data : croppingData,
			success : function(data){
				//alert("cropping dimension successfully uploaded");
				reuseDepGrid();
				$("form")[0].reset();
			},
			failure : function(e){
				alert(e.responseText);
			}
		});  
	 	       }
	         
	         
	         
	         
	         createWndDepdent = function (){        	         	 
		 	        //alert("in window");
		        		if(wndDepdent)
		        			{wndDepdent.content('');}
		         wndDepdent = $("#depdentsWinId").kendoWindow({
						modal : true,
						resizable : false,
						width : 500,
						height : 400,
						title : "Dependents"
					}).data("kendoWindow");
		         
		         wndDepdent.open();
	        	 wndDepdent.center();
	        	 
	 	    
	    };
	         
	         
	    
	      
        $("#changeSalryId").bind("click", function (e) {   
       	 e.preventDefault();
       		$("#changeSalryWinId").css("display",
				"block");
       		// $("form")[0].reset();
       		
       		
      
		
		
       		 
       		
       		
       		  getSal();
              $("#changeSal").val();
       	 createWndChangeSal();
       	 wndChangeSalary.open();
       	 wndChangeSalary.center();
        //alert("hello changeSalryId");
        
      
        
        });
    
        
        createWndChangeSal = function (){     
       	 
	 	      //  alert("in changeSalry window");
	        		if(wndChangeSalary)
	        			{wndChangeSalary.content("");}
	        		var wndChangeSalary = $("#changeSalryWinId").kendoWindow({
					modal : true,
					resizable : false,
					width : 400,
					height : 250,
					title : "Change Salary"
				}).data("kendoWindow");
	        		wndChangeSalary.open();
		        	 wndChangeSalary.center();
			
   
   };
   
   
   
function getSal()
{
	
	// alert("hello true");
	var editData = JSON.stringify({
		"id":tempEmpid,
			
		 }); 
	
	 
	  
	  $.ajax({
				 type : "POST",
					url:'<%=request.getContextPath()
					+ "/do/EmployeeCommanAction?parameter=getCurrentSalry"%>',
				 dataType : 'json',
				 contentType : 'application/json; charset=utf-8',
				 data : editData,
				 success : function(datas){ 
					 //alert(datas.basesalary);
					 
					 prvsId=datas.id;
					 prvsSal=datas.basesalary;
					// alert("GSDSA"+$("#curSalary"));
					 $("#curSalary").val(prvsSal);
					//wnd2.close();
					
					//$("#employeeTemplate").html('');
					
				 }	        				
			});	
	
	
	
	}
$("#addchangeSal").bind("click", function () {     
	

	
	var chageSal	=$("#chageSal").val();
	var curSalary	=$("#curSalary").val(); 
	
		var changeSalaryDate = new Date($("#changeSalaryDate").val());
		changeSalaryDate= changeSalaryDate.getTime();
		
	var editData = JSON.stringify([{
	
			"curSalry":chageSal,
				"fromDate":changeSalaryDate,
					"empId":tempEmpid,
				"toDate":changeSalaryDate,
				"prvsId":prvsId
				
		 }]); 
	
	
	  
	  $.ajax({
				 type : "POST",
					url:'<%=request.getContextPath()
					+ "/do/EmployeeCommanAction?parameter=saveCurrentSalry"%>',
				 dataType : 'json',
				 contentType : 'application/json; charset=utf-8',
				 data : editData,
				 success : function(datas){ 
					 //alert(datas.id);
					 
					
            	 
					//wnd2.close();
					
					//$("#employeeTemplate").html('');
					
				 }	        				
			});	
	
});

	    
	         
	         $("#giveBonusId").bind("click", function (e) {        
	            // alert("hello giveBonusId");
e.preventDefault();
$("#giveBonusWinId").css("display",
"block"); 
//$("form")[1].reset();
             createWndBonus();
	        	 wndBonus.open();
	        	 wndBonus.center();
	        	
	        	 var bonusDate = new Date($("#bonusDate").val());
	        	 bonusDate= bonusDate.getTime();
	     		
	         });
	          
	         
	         createWndBonus = function (){        	         	 
		 	      //  alert("in window");
		        		if(wndBonus)
		        			{wndBonus.content('');}
		        		wndBonus = $("#giveBonusWinId").kendoWindow({
						modal : true,
						resizable : false,
						width : 300,
						height : 200,
						title : "Bonus"
					}).data("kendoWindow");
		        		wndBonus.open();
			        	 wndBonus.center();
				
	    
	    };
	    
	    
	    
	    
	    
	    $("#addBonus").bind("click", function () {     
	    	

	    
	    	var bonusAmont	=$("#bonusAmont").val();
	    	var bonusDate = new Date($("#bonusDate").val());
       	 bonusDate= bonusDate.getTime();
	    	
	    		
	    		
	    	var editData = JSON.stringify([{
	    	
	    			"bonusAmont":bonusAmont,
	    				"bonusDate":bonusDate,
	    					"empId":tempEmpid,
	    				
	    				
	    		 }]); 
	    	
	    	
	    	  
	    	  $.ajax({
	    				 type : "POST",
	    					url:'<%=request.getContextPath()
					+ "/do/EmployeeCommanAction?parameter=saveBonus"%>',
	    				 dataType : 'json',
	    				 contentType : 'application/json; charset=utf-8',
	    				 data : editData,
	    				 success : function(datas){ 
	    					 //alert(datas.id);
	    					
	    					wndBonus.content('');
	                	 wndBonus.close();
	    					//wnd2.close();
	    					
	    					//$("#employeeTemplate").html('');
	    					
	    				 }	        				
	    			});	
	    	
	    });

	    
	    
	    
	    //alert(document.forms.length);
	         $("#bankAccountId").bind("click", function (e) {        
	             //alert("hello bankAccountId");
	             
	             
e.preventDefault();

	        	 createWndBankAccount();
	        	 wndBankAccount.open();
	        	 wndBankAccount.center();
	        	 
	        	 getBankDetails();
	         });
	         
	         createWndBankAccount = function (){        	         	 
		 	       // alert("in window");
		        		if(wndBankAccount)
		        			{
		        			
		        			wndBankAccount.content('');
		        			}
		         wndBankAccount = $("#bankAccountWinId").kendoWindow({
						modal : true,
						resizable : false,
						width : 300,
						height : 200,
						title : "Bank Details"
					}).data("kendoWindow");
		         wndBankAccount.open();
	        	 wndBankAccount.center();
				
	    
	    };
	         
	    function getBankDetails()
	    
	    {
	    	
	    	  var editData = JSON.stringify({
	    		  "id":tempEmpid,
					
				 }); 
	    	
	    	 $.ajax({
				 type : "POST",
					url:'<%=request.getContextPath()
					+ "/do/EmployeeCommanAction?parameter=getBankDetails"%>',
				 dataType : 'json',
				 contentType : 'application/json; charset=utf-8',
				 data : editData,
				 success : function(data){ 
					 //alert(datas.id);
					bankId=data.id;
					//alert(bankId);
					if(bankId!=null)
						{
						$("#addBank").text("Update");
					$("#bankBranch").val(data.bankBranch);
            	$("#bankName").val(data.bankName);
            	$("#accNo").val(data.accountNo);
						}
					
            	
            	
            	
					//wnd2.close();
					
					//$("#employeeTemplate").html('');
					
				 }	        				
			});	
	
	    	
	    	
	    }
	    
    
	    
	    $("#addBank").bind("click", function () {     
	    	//alert("hello");
	    var bankBranch=	$("#bankBranch").val();
        	var bankName=$("#bankName").val();
        	var accNo=$("#accNo").val();
	    		
	    		
	    	var editData = JSON.stringify([{
	    	id:bankId,
	    			"bankBranch":bankBranch,
	    				"bankName":bankName,
	    					"accNo":accNo,
	    					"empId":tempEmpid
	    				
	    		 }]); 
	    	
	    	
	    	  
	    	  $.ajax({
	    				 type : "POST",
	    					url:'<%=request.getContextPath()
					+ "/do/EmployeeCommanAction?parameter=saveBank"%>',
	    				 dataType : 'json',
	    				 contentType : 'application/json; charset=utf-8',
	    				 data : editData,
	    				 success : function(datas){ 
	    					 //alert(datas.id);
	    					 
	    					
	                	 
	    					//wnd2.close();
	    					
	    					//$("#employeeTemplate").html('');
	    					
	    				 }	        				
	    			});	
	    	
	    });
	         
	         $("#grid tbody").bind("click", function (e) {
	        	 //alert("ee"+event.button);
	        	 ///e.stopPropagation();
	    		tempEmpid  = $(".k-state-selected").find('td').eq(1).text();
	    		 //alert(ids);
	    		 
	    		 $("#rpsm1").show("slow");
	         });
         
	         $(".k-grid-pager").bind("click", function (e) {
	        	 //alert("ee"+event.button);
	        	 ///e.stopPropagation();
	    		//tempEmpid  = $(".k-state-selected").find('td').eq(1).text();
	    		 //alert(ids);
	    		 
	    		 $("#rpsm1").hide("slow");
	         });
	         //grid.hideColumn("photo");
	       // grid.hideColumn(2);
	        // alert( "heeloo"+grid.hideColumn(2));
	         $("#grid").delegate(".newEmp", "click", function(e) {
	        	 e.preventDefault();	        	 
        		 createNewEmpForm();
        		 empWindow.open();
        		 empWindow.center();        	 
	         });
	         $("#grid").delegate(".delTax", "click", function(e) {
	        	 e.preventDefault();
				 var dataItem = grid.dataItem($(this).closest("tr"));
				 
				 //alert(dataItem.id);
				 
				  var  id = dataItem.id; 
            	 
            	  
            	  var editData = JSON.stringify({
  					"id" : id,
  					
  				 }); 
				 var r=confirm("Are you sure you to delete this record!");
				 if (r==true)
				   {
				 // alert("hello true");
				  
				  $.ajax({
	      				 type : "POST",
	      				 url : "<%=request.getContextPath() + "/do/DeleteEmployeeAction"%>",
	     				 dataType : 'json',
	     				 contentType : 'application/json; charset=utf-8',
	     				 data : editData,
	     				 success : function(datas){ 
	     					 alert(datas);
	     					 
	     					
	                    	 
	     					//wnd2.close();
	     					
	     					//$("#employeeTemplate").html('');
	     					
	     				 }	        				
	     			});	
				  
				   }
				 else
				   {
					 alert("hello false");
				   }
				 
				 
				 
	         });
	        createNewEmpForm = function (){        	         	 
	        
	        		if(empWindow)
	        			{empWindow.content("");}
	        		
	        	 	empWindow = $("#empForm").kendoWindow({
	                 title: "EmployeeForm",
	                 content: empFormPath,
	                 modal : true,
	                 resizable: true,
	                 width : 800,
	 				height : 650,
	             }).data("kendoWindow");

	        	 empWindow.open();
	        	 empWindow.center();
	        	 
	         };
	         $("#editEmp1").bind("click", function () {    
				// alert("hello");
	        // $("#grid").delegate(".editEmp", "click", function(e) {
	        	 	 //e.preventDefault();
		        	 var dataItem = grid.dataItem($(".k-state-selected").closest("tr"));
		        	 
		        	// alert(dataItem);
		        	 var empTemplate= kendo.template($("#employeeTemplate").html());              
                 
                 
	                var wnd = $("#empForm").kendoWindow({
	                     title: "Employee Details",
	                     modal: true, 
	                     resizable: false,
		                 width : 700
	                 }).data("kendoWindow");          
                 
					  
	                wnd.content(empTemplate(dataItem));
	                $("#cancelEmp").click(function() { 
	                	 wnd.content('');
	                	 wnd.close();	                	 
	                }); 
	                
	                var selectedSex = dataItem.sex; 
	                var selectedStatus = dataItem.payrol.taxPaidByEmployer;
	                
	                var selectedFamily= dataItem.married;
	            //  alert("selectedStatus"+selectedStatus);
	            	var viewModel1 = kendo.observable({
	            		selectedSex: selectedSex,
	            		selectedStatus: selectedStatus,
	            		selectedFamily:selectedFamily
	    			});
	    			kendo.bind($("input"), viewModel1);
	    			
	    			
	    			
	    			paidTax=$('input:radio[name=status]:checked').val();
         			status="Active";
	    			
	    			
	    			
	    			
	    			
	               // var selectedStatus = dataItem.status;
	                
	       //  var d=10.33;
	        
	          
	        //alert(selectedPos);
	              //  alert(dataItem.empNationalID);
	     			 $("#positions").kendoDropDownList({
	     				
	     				dataTextField: "name",
	     				dataValueField : "coantcatVal",
	     			   	dataSource : {
		         			   	type: "json",
		         			   	transport: {
		                            read: "<%=request.getContextPath() + "/do/ReadPositionAction"%>"
	                    	}
	     				} 
	     			});    
	     			 
	     			 var branchDataSource1= new kendo.data.DataSource({
	   				  
	   				  transport : {
	   			  
	            		read : {
	          			type: 'POST',
	          			 url:'<%=request.getContextPath()
					+ "/do/CommantypesAction?parameter=getBranch"%>',
	      				 dataType : 'json',
	      				 contentType : 'application/json; charset=utf-8',
	      				 cache: false
	      				 
	          		}
	   			  
	   				  },
	          		autoSync : true,
	                 	batch : true 
	                 	
	   			  });

	   			 
	   			 var residentDataSource1= new kendo.data.DataSource({
					  
					  transport : {
				  
	        		read : {
	      			type: 'POST',
	      			 url:'<%=request.getContextPath()
					+ "/do/CommantypesAction?parameter=getResident"%>',
	  				 dataType : 'json',
	  				 contentType : 'application/json; charset=utf-8',
	  				 cache: false
	  				 
	      		}
				  
					  },
	      		autoSync : true,
	             	batch : true 
	             	
				  });
				 
				 var accommodationDataSource1= new kendo.data.DataSource({
					  
					  transport : {
				  
	        		read : {
	      			type: 'POST',
	      			 url:'<%=request.getContextPath()
					+ "/do/CommantypesAction?parameter=getAccommodation"%>',
	  				 dataType : 'json',
	  				 contentType : 'application/json; charset=utf-8',
	  				 cache: false
	  				 
	      		}
				  
					  },
	      		autoSync : true,
	             	batch : true 
	             	
				  });
				 
				  $(".residentDropDownList").kendoDropDownList({
					  
					  dataTextField : "name",
						dataValueField : "id",
						optionLabel: "Select Resident",
						dataSource :residentDataSource1
						
			       }).data("kendoDropDownList"); 
				 
				  var selectedResident = JSON.stringify(dataItem.residentType.id); 
				  //alert("residentVal"+selectedResident);
					 $("#residentVal").data("kendoDropDownList").value(selectedResident);
					 
					 
				  $("#brachDropDownList").kendoDropDownList({
					  
					  dataTextField : "name",
						dataValueField : "id",
						optionLabel: "Select Brach",
						dataSource :branchDataSource1
						
				  }).data("kendoDropDownList");
							  
				  var selectedBranch = JSON.stringify(dataItem.deptId.branchId.id); 
				 // alert("residentVal"+selectedResident);
					 $("#brachDropDownList").data("kendoDropDownList").value(selectedBranch);
					 
					 
					 $("#brachDropDownList").change(function() {
							$(".departDropDownList").html('');
							
							//alert("value"+$(this).val());
							var dId = $(this).val();
							
							   
							
							//alert(dId);
							var BrachData = JSON.stringify({
							
						
								"Id":dId
							 });  
							branDep(BrachData);
							
						});
					 
					 
					 
					 function branDep (BrachData){
						 
						 var 	branchDepartDataSource1 = new kendo.data.DataSource({
		                	 transport : {
		                    		read : {
		                    			type: 'POST',
		                    			 url:'<%=request.getContextPath()
					+ "/do/CommantypesAction?parameter=getAllBrachDepart"%>',
				        				 dataType : 'json',
				        				 contentType : 'application/json; charset=utf-8',
				        				 cache: false,
				        				 
				        				 
		                    		},
		                     
		                    		parameterMap: function (data, type) {
		                    			if(type = "read"){
		                    				//alert(BrachData+"hello updateData");
		                    				return BrachData;
		                    			}
		                    			
		                    		}
		                    	},
		                   
		                      
		                       	autoSync : true,
		                       	batch : true 
		                    });
					
					 
					  
					$(".departDropDownList").kendoDropDownList({
						dataTextField : "deptname",
						dataValueField : "id",
						optionLabel: "Select Depart",
						dataSource :branchDepartDataSource1
					
				       }).data("kendoDropDownList");
					
						 
					 }
					var bid=dataItem.deptId.branchId.id;
					var BrachData = JSON.stringify({
						
						
						"Id":bid
					 });  
					 branDep(BrachData);
					 
					 var selectedDepart = JSON.stringify(dataItem.deptId.id); 
					 // alert("residentVal"+selectedResident);
						 $("#departVal").data("kendoDropDownList").value(selectedDepart);
					 
	 $(".accommodationDropDownList").kendoDropDownList({
					  
					  dataTextField : "name",
						dataValueField : "id",
						optionLabel: "Select Accommodation",
						dataSource :accommodationDataSource1
						
			       }).data("kendoDropDownList");
	 

			 $("#accommodationVal").data("kendoDropDownList").value(JSON.stringify(dataItem.payrol.accomodationType.id));
	// var selectedPos =JSON.stringify(dataItem.positionIds.id)+"-"+JSON.stringify(dataItem.positionIds.lowSal)+"-"+JSON.stringify(dataItem.positionIds.highSal);
	// alert("jfadfj"+(dataItem.positionIds.lowsal).toString);
  var selectedPos =JSON.stringify(dataItem.positionIds.id)+"-"+kendo.parseFloat(JSON.stringify(dataItem.positionIds.lowSal),1).toFixed(1)+"-"+kendo.parseFloat(JSON.stringify(dataItem.positionIds.highSal),1).toFixed(1);
	     			//$("#status1").data("kendoDropDownList").value(selectedStatus);
	     			//alert("dd"+selectedPos);
					$("#positions").data("kendoDropDownList").value(selectedPos);	                 	 
	     			$("#birthdate").kendoDatePicker();
	        		$("#hiredate").kendoDatePicker();
	        		$("#passExpDate").kendoDatePicker();
	                $("#positions").change(function() {
	   				 
		 //alert("this"+$(this).val());
					 var tempPos=$(this).val();
					 
					 var costpart = String(tempPos).split("-");
					 postDropDownList=costpart[0];
					 
					 lowsal=kendo.parseFloat(costpart[1],1).toFixed(1);
					 highsal=kendo.parseFloat(costpart[2],1).toFixed(1);
					 alert(costpart[0]+"-"+lowsal+"-"+highsal);
					 }); 
					 
					 $("#numericSal").blur(function(){
						 //   alert("This input field has lost its focus.");
						    
						    numericSal=parseFloat($(this).val());
						//    alert(lowsal+"-----"+highsal);
						    if((numericSal<lowsal)||(numericSal>highsal))
						    {
						    	
						    	rangeError=false;
						    	 $("#s").show().text("no"+lowsal+"to"+highsal).fadeOut(5000);
						    }
						    else
						    	{
						    	rangeError=true;
						    	 $("#s").show().text("ok"+lowsal+"to"+highsal).fadeOut(5000);
						    	}
						    
						  });
	                 wnd.open(); 
	                 wnd.center();
	                
	                 
	                  
	                 
	                 
	                $("#saveEmp").bind("click", function() {  
	                	//crop variables
	         			var x, y, width, height;
	         			x = $('#x').val();
	    				y = $('#y').val();
	    				width = $('#w').val();
	    				height = $('#h').val();
	    				
	    				var croppingData = JSON.stringify({
	    					"x" : x,
	    					"y" : y,
	    					"width" : width,
	    					"height" : height,
	    					"employeeId" : $("#employeeId").val()
	    				});
	    				
	    				
	    				
	    				
	    				var id,status,famly,nationID,contName,contNumber,
	                	
	                	sex,accommodationVal,residentVal,employeeId, departId,passNo, passExpDate,passPlace,
	                	
	                	firstname, middlename, lastname,  empNation, empAddrss, paidTax, phNo,
	                	
	                	birthdate, hiredate,  photo, numericSal1;
	    				
         				
	                	
	                	
	                	
	                	
	                	
	                	
	                	id = $("#id").val(); 
	         			employeeId = $("#employeeId").val(); 
	         			firstname = $("#firstname").val(); 
	         			middlename = $("#middlename").val(); 
	         			lastname = $("#lastname").val(); 
	         			numericSal1=$("#numericSal").val();
	         			
	       residentVal=$("#residentVal").val();
	         			
	         			accommodationVal=$("#accommodationVal").val();
	         			departId=$("#departVal").val();
	         			sex=$('input:radio[name=sex]:checked').val();
	         			
	         			status="Active";
	         			famly=$('input:radio[name=family]:checked').val();
	         			paidTax=$('input:radio[name=status]:checked').val();
	         			contName=$("#contName").val();
	         			contNumber=$("#contNumber").val();
	         		nationID=$("#nationID").val();
	         		
	         		
	         		phNo="534534534";
	         		
	         		passNo=$("#passNo").val();
	         		
	         		empNation=$("#empNation").val();
	         		
	         		passPlace=$("#passPlace").val();
	         		
	         		
	         		
	         		empAddrss=$("#empAddrss").val();
	         		
	         		
	         		//alert(empAddrss);
	         		var post=$("#positions").val();
	         		
	         		
	         		 var costpart1 = String(post).split("-");
					 postDropDownList=costpart1[0];
	         	
	         		
	         	
	         			
	         	        var bdate = new Date($("#birthdate").val());
	         			var hdate = new Date($("#hiredate").val());
	         			
	         			birthdate = bdate.getTime();
	         			
	         			hiredate = hdate.getTime();
	         			
	         			var passExpDate1=new Date($("#passExpDate").val());
	         			
	         			passExpDate=passExpDate1.getTime();
	         			var yrDifference = parseInt(hdate.getFullYear()) - parseInt(bdate.getFullYear());
	         			//alert(yrDifference);
	         			if(yrDifference <= 0){
	         				alert('Invalid date entry, please enter valid dates!');
	         				return;
	         			}else if(yrDifference < 14){
	         				alert('The system cannot save profile for person under the age of 14 \n'+
     						'\n Contact Administrator for support');
		     				return;
	         			}
	         			
	         			
	         			if($("#profilePicUploader").val()){
	         				photo = "/data/photo/" + $("#profilePicUploader").val();	         				
	         				$.ajax({
	         					url : "<%=request.getContextPath() + "/do/PhotoCropAction"%>",
		    					type : 'POST',
		    					dataType : 'json',
		    					contentType : 'application/json; charset=utf-8',
		    					data : croppingData,
		    					success : function(){
		    						//alert("cropping dimension successfully uploaded");
		    				},
		    				failure : function(e){
		    						alert(e.responseText);
		    					}
		    				});
	         				
	         				
	         				$.ajaxFileUpload({
	                            url:"<%=request.getContextPath() + "/do/EmployeePhotoAction"%>",
	                            secureuri:false,
	                            fileElementId: 'profilePicUploader',
	                            dataType: 'json',
	                            success: function (data, status)
	                            {
	                            	if(typeof(data.error) != 'undefined')
	                                {
	                                    if(data.error != '')
	                                    {
	                                        alert(data.error);
	                                    }else
	                                    {
	                                        alert(data.msg);
	                                    }
	                                }
	                            },
	                            error: function (data, status, e)
	                            {
	                                alert(e);
	                            }
	                        });
	         			}else{	         				
	         				photo = $("#photo").val(); 
	         			}        
	         			
	         			alert("diasud");
	         			var updateDataEmp1 = JSON.stringify([{
	    					"id" : id,
	    					"employeeId" : employeeId,
	    					"firstname" : firstname,
	    					"middlename" : middlename,
	    					"lastname" : lastname,
	    					"sex" : sex,
	    					"birthdate" : birthdate,
	    					"hiredate" : hiredate,
	    					"positionId" : postDropDownList,
	    					"photo" : photo,
	    					"status" : status,
	    					"famly":famly,
	    					"accommodationVal":accommodationVal,
	    					"nationID":nationID,
	    					"residentVal":residentVal,
	    					"contNumber":contNumber,
	    					"contName":contName,
	    					"deptIdVal":departId,
	    					"baseSalry":numericSal1,
	    					"empAddrss":empAddrss,
	    					"passExpDate":passExpDate,
	    					"passPlace":passPlace,
	    					"empNation":empNation,
	    					"passNo":passNo,
	    					"paidTax":paidTax,
	    					"phNo":phNo
	    				 }]);  
	         	
	         			$.ajax({
	         				 type : "POST",
	         				 url:'<%=request.getContextPath() + "/do/UpdateEmployeeAction"%>',
																			dataType : 'json',
																			contentType : 'application/json; charset=utf-8',
																			data :updateDataEmp1, 
																			success : function() {

																				 empDataSource.read();
													        					 wnd.close();	
																			}
																		});
															});
										});

					});

</script>

<script type="text/x-kendo-template" id="employeeTemplate">
<div id="employeeForm">
	<div class="clear"></div>
 <fieldset>
  <legend>Personal Details</legend>
	<div style="float:left;height: 380px;">
	

	<div id="left-col">
		<div>
			<div class="label">Employee ID</div>
			<div class="field">
				<input type="hidden" id="id" value="#=id#"/>
                <input type="hidden" id="photo" value="#=photo#"/>
				<input type="text" disabled=disabled class="k-input k-textbox" id="employeeId"  value="#=employeeId#" />
			</div>
			<div class="clear"></div>
		</div>
<div>
			<div class="label">National ID</div>
			<div class="field">
						<input type="text"  class="k-input k-textbox" id="nationID"  value="#=empNationalID#" />
			</div>
			<div class="clear"></div>
		</div>

		<div>
			<div class="label">First Name</div>
			<div class="field">
								<input type="text" required=required class="k-input k-textbox" id="firstname"  value="#=firstname#"/>
			</div>
			<div class="clear"></div>
		</div>



		<div>
			<div class="label">Middle Name</div>
			<div class="field">
						<input type="text" required=required class="k-input k-textbox" id="middlename"  value="#=middlename#"/>
			</div>
			<div class="clear"></div>
		</div>



		<div>
			<div class="label">Last Name</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="lastname" value="#=lastname#" />
			</div>
			<div class="clear"></div>
		</div>



	<div>
	<div class="label">Photo</div>
		<div class="field">
		<div >
			<img id="preview" class="k-image j-cropview"
				height=100 width=100 src="#='/OpenHR'+photo#"/>
	
		</div>
		
</div>




	<html:form action="/EmployeePhotoAction" styleClass="fileForm" method="post" enctype="multipart/form-data">
				<html:file property="photoFile" styleId="profilePicUploader"
					onchange="readURL(this)" /> </html:form>
		</div>
		
	<div>
			<div class="label">Birth Date</div>
			<div class="field">
				<input type="text" class="k-input k-textbox" 
					id="birthdate" value="#= kendo.toString(new Date(birthdate), 'MMM, dd yyyy')#" />
			</div>
			<div class="clear"></div>
		</div>	
				<div>
			<div class="label">Address</div>
			<div class="field">
<textarea class="k-textbox" id="empAddrss">

#=empAddrss#
</textarea>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div id="right-col">
		<div>
			<div class="label">Sex</div>
			<div class="field" >
					<label for="sexmale">
				<input  type="radio" id="sexm" value="Male" name="sex" data-bind="checked: selectedSex"/>
			Male
			</label>
				<label for="sexfemale">
				<input  type="radio" id="inactive" value="Female" name="sex"  data-bind="checked: selectedSex"/>
			Female
			</label>
			
			
			</div>
			<div class="clear"></div>
		</div>

			
<div>
<div class="label">Marital Status</div>
			<label for="family_single">
				<input  type="radio" id="family_single" value="false" name="family" data-bind="checked: selectedFamily" />
				Single
			</label>
			<label for="family_married">
				<input  type="radio" id="family_married" value="true" name="family"  data-bind="checked: selectedFamily" />
				Married
			</label>
		<div class="clear"></div>	
	</div>	

	
	
			
		
		

	
<div>
			<div class="label">Emergency Contact Name</div>
			<div class="field">
					<input type="text" required=required class="k-input k-textbox" id="contName"  value="#=emerContactName#" />
			</div>
			<div class="clear"></div>
		</div>
		<div>
<div class="label">Emergency Contact No</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="contNumber"  value="#=emerContactNo#" />
			</div>
			<div class="clear"></div>
		</div>
		
		
		
		
		
		<div>
<div class="label">Passport No</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="passNo" value="#=passNo#" />
			</div>
			<div class="clear"></div>
		</div>
		
		<div>
			<div class="label">Passport Expiry Date</div>
			<div class="field">
				<input type="text" class="k-input k-textbox" 
					id="passExpDate"   value="#= kendo.toString(new Date(passExpDate), 'MMM, dd yyyy')#"  />
			</div>
			<div class="clear"></div>
		</div>
		
		<div>
			<div class="label">Passport Place of Issue</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="passPlace"  value="#=passPlace#" />
			</div>
			<div class="clear"></div>
		</div>
		<div>
<div class="label">Nationality</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="empNation"  value="#=empNationalID#" />
			</div>
			<div class="clear"></div>
		</div>
		
		
		
		

		
	</div>
</div>
 </fieldset>
<fieldset>
  <legend>Hiring Details</legend>
	<div style="float:left;height: 180px">
	



	<div id="right-col">
		
	

		
		
		
		
		<div>
			<div class="label">Hired Date</div>
			<div class="field">
				<input type="text" class="k-input k-textbox" 
					id="hiredate" value="#= kendo.toString(new Date(hiredate), 'MMM, dd yyyy')#" />
			</div>
			<div class="clear"></div>
		</div>
		
		<div>
			<div class="label">Position</div>
			<div class="field">
			<input id="positions" value="#=positionIds ? positionIds.name : ''#"/>
			</div>
			<div class="clear"></div>
		</div>
		<div>
			<div class="label">Basic Salary</div>
			<div class="field">
		
		  <input id="numericSal" type="number"  class="k-input k-textbox"   min="0" value="#=empsal?empsal.basesalary : ''#"   />
		    <span id="s"></span>
		   	</div>
			<div class="clear"></div>
		</div> 
		    
		<div> <div class="label">Branch</div>
			<div class="field">
		<input id="brachDropDownList" value="#=deptId ? deptId.branchId.name: ''#"/>
			</div>
			<div class="clear"></div></div>
		
		
		



		<div id="dp1">
			<div class="label">Department</div>
			<div class="field">
	<input class="departDropDownList" id="departVal" value="#=deptId ? deptId.name: ''#"/>
			</div>
			<div class="clear"></div>
		</div>

		
		
		
		
		
		
		
		
		
		</div>
	
	<div id="left-col">
	<div>
			<div class="label">Accommodation Type</div>
			<div class="field">
			<input class="accommodationDropDownList" id="accommodationVal"/>
			</div>
			<div class="clear"></div>
		</div>
<div >
			<div class="label">Resident Type</div>
			<div class="field">
<input class="residentDropDownList" id="residentVal" value="#=residentType ? residentType.name: ''#"/>
			</div>
			<div class="clear"></div>
		</div>
	
		
			<div>
	
			<div class="label">Tax Paid By</div>
			
			<label for="statusactive">
				<input  type="radio" id="active" value="1" name="status" data-bind="checked: selectedStatus" />
			Employee
			</label>
				<label for="statusInactive">
				<input  type="radio" id="inactive" value="-1" name="status"  data-bind="checked: selectedStatus" />
				Employer
			</label>
			<div class="clear"></div>
		</div>
		
		
	</div>


</div>
 </fieldset>
<div id="left-col">
<div>
			<div class="field">
				<a class="k-button k-icontext" id="saveEmp"><span class="k-add k-icon"></span>Save</a> <a
					class="k-button k-icontext" id="cancelEmp"><span class="k-cancel k-icon"></span>Cancel</a>
			</div>
			<div class="clear"></div>
		</div>
</div>
</div>
</div>
<div></div>
<div id="imageCropper" style="display:none">	
	<a id="cropImage" class="k-button">Done</a>
<input type=hidden id="x"/>
	<input type=hidden id="y"/>
	<input type=hidden id="w"/>
	<input type=hidden id="h"/>
	<img  id="target" src="" style="display:none"/>	
</div>

</script>

<script type="text/javascript">
	var cropperWindow;
	var jcrop_api, boundx, boundy;

	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e) {

				$("#preview").attr('src', e.target.result);
				$("#target").attr('src', e.target.result);
			};

			reader.readAsDataURL(input.files[0]);
		}
	}
</script>

<script>
    var empWindow;
    $("#grid").delegate(".upldEmpFile", "click", function(e) {
		e.preventDefault();	        
		createNewUpdEmpForm();
  		 empWindow.open();
  		 empWindow.center();
	});
  		 
  		 createNewUpdEmpForm = function (){        	         	 
	        	if(empWindow)
	        		empWindow.content("");
	        	 	empWindow = $("#upldEmpFileWnd").kendoWindow({
	                 title: "",
 	                 modal : true,
	                 resizable: false,
	                 width : 650
	             }).data("kendoWindow");
	        	 
	        	 empWindow.open();
	        	 empWindow.center();
	        	 
	         }
</script>

<div class="k-content" id="upldEmpFileWnd" style="display:none">
	<div>
		<div >
			<div id="form">
				<div>
					<span style="display : none;"><a class="k-button k-icontext" id="cancelCmp" style="display:none !important;"><span class="k-cancel k-icon"></span>Cancel</a></span>
					<div  style="width: 630px;">
			<fieldset>
				<legend>Upload New Employee Details File</legend>
		
		<div>
			<form method="post" action="<%=request.getContextPath() + "/do/UploadEmployeeDataFile"%>" enctype="multipart/form-data">
			Upload the file containing the list of employees and their details to be added into this application.
			<br><br>Select the file to be uploaded and click on <b>Upload</b> button.<br><br>
			    New Employee Details file to upload: <input type="file" name="uploadFile" />
	            <br/><br/> 
	            <input class="k-button k-icontext" type="submit" value="Upload" />
	        </form>
		</div>
		</fieldset>
	</div>
				</div>

			</div>
		</div>
		
	</div>
</div>