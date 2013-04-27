<style>

.k-grouping-header

{display:none;}
</style>
<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">Employee Form</h2> 
<div id="grid">
	</div>
<div id="empForm">
</div>
<script> 
var createNewEmpForm;
var dropDownURL = "<%=request.getContextPath()%>" + "/do/ReadPositionAction"; 
var empDataSource;
var empWindow;
var empFormPath = "<%=request.getContextPath()%>"+ "/pages/hr/employee/employeeForm.jsp";


	
    $(document).ready(function(e){	
    	
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
					{command : [{"name" : "edit", className : "editEmp"},{ text:"AddDep", className:"delTax"}], width : 40, filterable :false },
	               
					 { hidden:true,field : "id", title : "id"},
					
					{ field : "photo", title : "Photo" ,template : "<img width=67 height=50 src='#='/OpenHR'+photo#'/>", width : 40},
					{field : "employeeId",  title:"Id",width : 50 },
	                { field : "firstname", title : "First Name", width : 50 },
	                { hidden :true,field : "middlename", title : "Middle Name", width : 50 },
	               
	                { hidden :true,field :"empNationalID", title : "empNationalID",  width : 20 },
	                { hidden :true,field : "married", title : "married",  width : 20 },
	                
	                { hidden :true,field : "married", title : "married",  width : 20 },
	                { field : "sex", title : "Sex",  width : 30 },
	                { field : "positionId", title : "Title",  template: '#=positionId ? positionId.name: ""#', width : 40  },
	                { hidden:true, field : "birthdate", title : "Birth date", template : "#= kendo.toString(new Date(birthdate), 'MMM, dd yyyy') #" },
	                {hidden:true,  field : "hiredate", title : "Hired date", template : "#= kendo.toString(new Date(hiredate) , 'MMM, dd yyyy') #" },
	                { field : "companyId", title : "Branch", template: '#=companyId ? companyId.brchId.name: ""#',  width : 40 },
	                
	                { field : "status", title : "Status", width : 30 },
	                { field : "residentType", title : "residentType",  template: '#=residentType ? residentType.name: ""#', width : 40 }
	            ],             
	            dataBound : function(){
	            	$.each(empDataSource.data(), function(){  
	            		this.birthdate = new Date(this.birthdate);
	            		this.hiredate = new Date(this.hiredate);	            		
	            	});
	            },
	            
	            toolbar : [{"name" : "create", className : "newEmp", text : "Add New Employee" }],
	            sortable: true,  
	            scrollable : true,
	            height : 400,
	            pageable:true,
	           
	            resizeable : true,
	            reorderable: true,
	            selectable : "row"
	            
	        }).data("kendoGrid");
	         
	        
	        
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
				 
				 alert(dataItem.id);
				 
				  var  id = dataItem.id; 
            	 
            	  
            	  var editData = JSON.stringify({
  					"id" : id,
  					
  				 }); 
				 var r=confirm("Are you sure you to delete this record!");
				 if (r==true)
				   {
				  alert("hello true");
				  
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
	        		empWindow.content("");
	        	 	empWindow = $("#empForm").kendoWindow({
	                 title: "EmployeeForm",
	                 content: empFormPath,
	                 modal : true,
	                 resizable: false,
	                 width : 650
	             }).data("kendoWindow");
	        	 
	        	 empWindow.open();
	        	 empWindow.center();
	        	 
	         };
	         
	         $("#grid").delegate(".editEmp", "click", function(e) {
	        	 	 e.preventDefault();
		        	 var dataItem = grid.dataItem($(this).closest("tr"));
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
	                var selectedStatus = dataItem.status;
	                
	                var selectedFamily= dataItem.married;
	                //alert("selectedSex"+selectedSex);
	            	var viewModel1 = kendo.observable({
	            		selectedSex: selectedSex,
	            		selectedStatus: selectedStatus,
	            		selectedFamily:selectedFamily
	    			});
	    			kendo.bind($("input"), viewModel1);
	               // var selectedStatus = dataItem.status;
	                
	         
	                
	                var selectedPos = JSON.stringify(dataItem.positionId.id); 
	              //  alert(dataItem.empNationalID);
	     			 $("#positions").kendoDropDownList({
	     				
	     				dataTextField: "name",
	     			    dataValueField: "id",
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
	          			 url:'<%=request.getContextPath()+ "/do/CommantypesAction?parameter=getBranch"%>',
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
	      			 url:'<%=request.getContextPath()+ "/do/CommantypesAction?parameter=getResident"%>',
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
	      			 url:'<%=request.getContextPath()+ "/do/CommantypesAction?parameter=getAccommodation"%>',
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
							  
				  var selectedBranch = JSON.stringify(dataItem.companyId.brchId.id); 
				 // alert("residentVal"+selectedResident);
					 $("#brachDropDownList").data("kendoDropDownList").value(selectedBranch);
					 
					 
					 $("#brachDropDownList").change(function() {
							$(".departDropDownList").html('');
							
							//alert("value"+$(this).val());
							var dId = $(this).val();
							
							   
							
							alert(dId);
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
		                    			 url:'<%=request.getContextPath()+ "/do/CommantypesAction?parameter=getAllBrachDepart"%>',
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
						dataTextField : "name",
						dataValueField : "id",
						optionLabel: "Select Depart",
						dataSource :branchDepartDataSource1
					
				       }).data("kendoDropDownList");
					
						 
					 }
					var bid=dataItem.companyId.brchId.id;
					var BrachData = JSON.stringify({
						
						
						"Id":bid
					 });  
					 branDep(BrachData);
					 
					 var selectedDepart = JSON.stringify(dataItem.companyId.id); 
					 // alert("residentVal"+selectedResident);
						 $("#departVal").data("kendoDropDownList").value(selectedDepart);
					 
	 $(".accommodationDropDownList").kendoDropDownList({
					  
					  dataTextField : "name",
						dataValueField : "id",
						optionLabel: "Select Accommodation",
						dataSource :accommodationDataSource1
						
			       }).data("kendoDropDownList");
   
	     			
	     			//$("#status1").data("kendoDropDownList").value(selectedStatus);
	     			
					$("#positions").data("kendoDropDownList").value(selectedPos);	                 	 
	     			$("#birthdate").kendoDatePicker();
	        		$("#hiredate").kendoDatePicker();
	                
	        		
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
	    				
	    				
	    				
	    				
	    				var id, employeeId, firstname, middlename, lastname, sex, birthdate, hiredate, positionId, photo;
	    				
         				
	                	
	                	
	                	
	                	
	                	
	                	
	                	id = $("#id").val(); 
	         			employeeId = $("#employeeId").val(); 
	         			firstname = $("#firstname").val(); 
	         			middlename = $("#middlename").val(); 
	         			lastname = $("#lastname").val(); 
	         			sex = $("#sex").val(); 
	         			
	         			positionId = $("#positions").val();
	         			status = $("#status1").val();
	         			
	         			
	         			var bdate = new Date($("#birthdate").val());
	         			var hdate = new Date($("#hiredate").val());
	         			birthdate = bdate.getTime();
	         			hiredate = hdate.getTime();
	         			
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
	         			
	         			var updateData = JSON.stringify([{
        					"id" : id,
        					"employeeId" : employeeId,
        					"firstname" : firstname,
        					"middlename" : middlename,
        					"lastname" : lastname,
        					"sex" : sex,
        					"birthdate" : birthdate,
        					"hiredate" : hiredate,
        					"positionId" : positionId,
        					"photo" : photo,
        					"status": status
        				 }]);
	         			
	         			$.ajax({
	         				 type : "POST",
	        				 url:'<%=request.getContextPath()+ "/do/UpdateEmployeeAction"%>',
	        				 dataType : 'json',
	        				 contentType : 'application/json; charset=utf-8',
	        				 data : updateData,
	        				 success : function(){ 
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
	<div id="left-col">
		<div>
			<div class="label">EMP-ID</div>
			<div class="field">
				<input type="hidden" id="id" value="#=id#"/>
                <input type="hidden" id="photo" value="#=photo#"/>
				<input type="text" disabled=disabled class="k-input k-textbox" id="employeeId"  value="#=employeeId#" />
			</div>
			<div class="clear"></div>
		</div>


		<div>
			<div class="label">First name</div>
			<div class="field">
				<input type="text" class="k-input k-textbox" id="firstname"  value="#=firstname#" />
			</div>
			<div class="clear"></div>
		</div>



		<div>
			<div class="label">Middle name</div>
			<div class="field">
				<input type="text" class="k-input k-textbox"  id="middlename" value="#=middlename#" />
			</div>
			<div class="clear"></div>
		</div>



		<div>
			<div class="label">Last name</div>
			<div class="field">
				<input type="text" class="k-input k-textbox" id="lastname"  value="#=lastname#" />
			</div>
			<div class="clear"></div>
		</div>
	
			


		<div>
			<div class="label">Sex</div>
			<div class="field">
				

	<label for="sexmale">
				<input  type="radio" id="sexm" value="Male" name="sex" data-bind="checked: selectedSex" />
			Male
			</label>
				<label for="sexfemale">
				<input  type="radio" id="inactive" value="Female" name="sex"   data-bind="checked: selectedSex"/>
			Female
			</label>
			</div>
			<div class="clear"></div>
		</div>



		<div>
			<div class="label">Birth date</div>
			<div class="field">
				<input type="text" data-type="date" data-role="datepicker" class="k-input k-textbox" 
					id="birthdate" value="#= kendo.toString(new Date(birthdate), 'MMM, dd yyyy')#" />
				</div>
			<div class="clear"></div>
		</div>



		<div>
			<div class="label">Hire date</div>
			<div class="field">
				<input type="text" data-type="date" data-role="datepicker" class="k-input k-textbox" 
					id="hiredate" value="#= kendo.toString(new Date(hiredate), 'MMM, dd yyyy')#" />
			</div>
			<div class="clear"></div>
		</div>



		<div>
			<div class="label">Position/ Title</div>
			<div class="field">
				<input id="positions" value="#=positionId ? positionId.name : ''#"/>
			</div>
			<div class="clear"></div>
		</div>


		<div> <div class="label">Branch</div>
			<div class="field">
				<input id="brachDropDownList" value="#=companyId ? companyId.brchId.name: ''#"/>
			</div>
			<div class="clear"></div></div>
		
		
		



		<div id="dp1">
			<div class="label">Department</div>
			<div class="field">
				<input class="departDropDownList" id="departVal" value="#=companyId ? companyId.name: ''#"/>
			</div>
			<div class="clear"></div>
		</div>
<div >
			<div class="label">Resident Type </div>
			<div class="field">
				<input class="residentDropDownList" id="residentVal" value="#=residentType ? residentType.name: ''#"/>
			</div>
			<div class="clear"></div>
		</div>
		<div>
			<div class="field">
				<a class="k-button k-icontext" id="saveEmp"><span class="k-icon k-update"></span>Update</a> <a
					class="k-button k-icontext" id="cancelEmp"><span class="k-icon k-cancel"></span>Cancel</a>
			</div>
			<div class="clear"></div>
		</div>
	</div>


	<div id="right-col">
		<div style="height:150px;width:200px;overflow:hidden">
			<img id="preview" class="k-image j-cropview"
				height=150 width=200 src="#='/OpenHR'+photo#"/>
			<div class="clear"></div>
		</div>

		<div>
			<div class="field">
<html:form action="/EmployeePhotoAction" styleClass="fileForm" method="post" enctype="multipart/form-data">
				<html:file property="photoFile" styleId="profilePicUploader"
					onchange="readURL(this)" /> </html:form>
			</div>
			<div class="clear"></div>
		</div>
<div>
<div class="label">Family</div>
			<label for="family_single">
				<input  type="radio" id="family_single" value="false" name="family" data-bind="checked: selectedFamily" />
				Single
			</label>
			<label for="family_married">
				<input  type="radio" id="family_married" value="true" name="family" data-bind="checked: selectedFamily" />
				Married
			</label>
		<div class="clear"></div>	
	</div>	


<div>
			<div class="label">Employee National ID</div> 
			<div class="field">
				<input type="text"  class="k-input k-textbox" id="nationID"  value="#=empNationalID#" />
			</div>
			<div class="clear"></div>
		</div>
		
		<div>
<div class="label">Emergency Contact Number</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="contNumber"  value="" />
			</div>
			<div class="clear"></div>
		</div>
<div>
			<div class="label">Status</div>
			<div class="field">
				<label for="statusactive">
				<input  type="radio" id="active" value="ACTIVE" name="status" data-bind="checked: selectedStatus" />
			ACTIVE
			</label>
				<label for="statusInactive">
				<input  type="radio" id="inactive" value="IN ACTIVE" name="status"  data-bind="checked: selectedStatus" />
				IN ACTIVE
			</label>
			</div>
			<div class="clear"></div>
		</div>

		<div >
			<div class="label">AccommodationType </div>
			<div class="field">
				<input class="accommodationDropDownList" id="accommodationVal"/>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div class="clear"></div>

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

<script>
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