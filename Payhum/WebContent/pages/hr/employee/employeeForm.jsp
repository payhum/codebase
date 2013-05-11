<%@page import="com.openhr.employee.EmployeeIdUtility"%>
<%@page import="com.openhr.Config"%>
<%Config.readConfig();%>
<div id="employeeForm">
	<div class="clear"></div>
	<div style="border:1px solid #666;float:left;height: 380px;">
	
	<div id="sec1"  style="float:left;margin-top: -20px">Personal Details</div>
	<div id="left-col">
		<div>
			<div class="label">EMP-ID</div>
			<div class="field">
				<input type="text" readonly="readonly" class="k-input k-textbox" id="employeeId"  
				value="<%=Config.employeeIdPattern + "-" + EmployeeIdUtility.nextId()%>" />
			</div>
			<div class="clear"></div>
		</div>

<div>
			<div class="label">EMP-NationalID</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="nationID"  value="" />
			</div>
			<div class="clear"></div>
		</div>

		<div>
			<div class="label">First name</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="firstname"  value="" />
			</div>
			<div class="clear"></div>
		</div>



		<div>
			<div class="label">Middle name</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="middlename"  value="" />
			</div>
			<div class="clear"></div>
		</div>



		<div>
			<div class="label">Last name</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="lastname"  value="" />
			</div>
			<div class="clear"></div>
		</div>



	<div>
	<div class="label">Photo</div>
		<div class="field">
		<div >
			<img id="preview" style="border:2px solid #999;" class="k-image j-cropview"
				height=100 width=150 src="http://localhost:8080/OpenHR/data/photo/placeholder-pic.png"/>
	
		</div>
		
</div>

	<input type="file" required=required id="profilePicUploader"
					onchange="readURL(this)" />
		</div>
		
	<div>
			<div class="label">Birth date</div>
			<div class="field">
				<input type="text" class="k-input k-textbox" 
					id="birthdate" value="" />
			</div>
			<div class="clear"></div>
		</div>	
				<div>
			<div class="label">Address</div>
			<div class="field">
<textarea class="k-textbox" id="empAddrss">


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
				<input  type="radio" id="sexm" value="Male" name="sex" />
			Male
			</label>
				<label for="sexfemale">
				<input  type="radio" id="inactive" value="Female" name="sex"  />
			Female
			</label>
			
			
			</div>
			<div class="clear"></div>
		</div>

			
<div>
<div class="label">Family</div>
			<label for="family_single">
				<input  type="radio" id="family_single" value="false" name="family"  />
				Single
			</label>
			<label for="family_married">
				<input  type="radio" id="family_married" value="true" name="family" />
				Married
			</label>
		<div class="clear"></div>	
	</div>	

	
	
			
		
		

	
<div>
			<div class="label">Emergency Contact Name</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="contName"  value="" />
			</div>
			<div class="clear"></div>
		</div>
		<div>
<div class="label">Emergency Contact Num</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="contNumber"  value="" />
			</div>
			<div class="clear"></div>
		</div>
		
		
		
		
		
		<div>
<div class="label">Passport No</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="passNo"  value="" />
			</div>
			<div class="clear"></div>
		</div>
		
		<div>
			<div class="label">Passport Exp Date</div>
			<div class="field">
				<input type="text" class="k-input k-textbox" 
					id="passExpDate" value="" />
			</div>
			<div class="clear"></div>
		</div>
		
		<div>
			<div class="label">Passport Issue place</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="passPlace"  value="" />
			</div>
			<div class="clear"></div>
		</div>
		<div>
<div class="label">Nationality</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="empNation"  value="" />
			</div>
			<div class="clear"></div>
		</div>
		
		
		
		

		
	</div>
</div>

	<div style="border:1px solid #666;float:left;margin-top: 30px;height: 150px">
	
	<div id="sec1"  style="float:left;margin-top: -20px">Hiring Details</div>


	<div id="right-col">
		
	

		
		
		
		
		<div>
			<div class="label">Hire date</div>
			<div class="field">
				<input type="text" class="k-input k-textbox" 
					id="hiredate" value="" />
			</div>
			<div class="clear"></div>
		</div>
		
		<div>
			<div class="label">Position</div>
			<div class="field">
				<input id="postDropDownList"/>
			</div>
			<div class="clear"></div>
		</div>
		<div>
			<div class="label">Basic Salary</div>
			<div class="field">
		
		    <input id="numericSal" type="number" value="" min="0"   />
		    <span id="s"></span>
		   	</div>
			<div class="clear"></div>
		</div> 
		    
		<div> <div class="label">Branch</div>
			<div class="field">
				<input id="brachDropDownList"/>
			</div>
			<div class="clear"></div></div>
		
		
		



		<div id="dp1">
			<div class="label">Department</div>
			<div class="field">
				<input class="departDropDownList" id="departVal"/>
			</div>
			<div class="clear"></div>
		</div>

		
		
		
		
		
		
		
		
		
		</div>
	
	<div id="left-col">
	<div>
			<div class="label">AccommodationType </div>
			<div class="field">
				<input class="accommodationDropDownList" required=required class="k-input k-textbox"  id="accommodationVal"/>
			</div>
			<div class="clear"></div>
		</div>
<div >
			<div class="label">Resident Type </div>
			<div class="field">
				<input class="residentDropDownList" id="residentVal"/>
			</div>
			<div class="clear"></div>
		</div>
	
		
			<div>
	
			<div class="label">Tax Paid By</div>
			
			<label for="statusactive">
				<input  type="radio" id="active" value="1" name="status" />
			Employee
			</label>
				<label for="statusInactive">
				<input  type="radio" id="inactive" value="-1" name="status"  />
				Employer
			</label>
			<div class="clear"></div>
		</div>
		
		
	</div>


</div>
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

<script> 


		$(document).ready(function(){
			var lowsal;
			var rangeError;
			var highsal;
			
			var postDropDownList;
			
			var numericSal;
			  $("#numericSal").kendoNumericTextBox();
			//$('input:radio[name=family]').val('false');
			
			 $("#passNo").kendoNumericTextBox();
			
			$("#dp1").hide();
			var today = new Date();
		
			//$("#employeeId").mask("aaaaaa-9999" , {placeholder:"-"});
			$("#passExpDate").kendoDatePicker({value : today
			});
			
			$("#hiredate").kendoDatePicker({
				value : today
			});
			
			$("#birthdate").kendoDatePicker();
			
			$("#birthdate").data('kendoDatePicker').value(new Date('1960/01/01'));
			
			 $("#cancelEmp").bind("click", function() { 
				 empWindow.content('');
				 empWindow.close();	                	 
             }); 
			 
			 $("#postDropDownList").change(function() {
				 
				// alert("this"+$(this).val());
			 var tempPos=$(this).val();
			 
			 var costpart = String(tempPos).split("-");
			 postDropDownList=costpart[0];
			 
			 lowsal=parseFloat(costpart[1]);
			 highsal=parseFloat(costpart[2]);
			 //alert(costpart[0]+"-"+highsal+"-"+lowsal);
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
			 
			 
			 
			 
			 $("#brachDropDownList").change(function() {
					$(".departDropDownList").html('');
					$("#dp1").show();
					//alert("value"+$(this).val());
					var dId = $(this).val();
					
					   
					
					//alert(dId);
					var BrachData = JSON.stringify({
					
				
						"Id":dId
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
					
					 
					  
					$(".departDropDownList").kendoDropDownList({
						dataTextField : "deptname",
						dataValueField : "id",
						optionLabel: "Select Department",
						dataSource :branchDepartDataSource1
					
				       }).data("kendoDropDownList");
					
					
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
			 
			 
			  
			  
 $(".accommodationDropDownList").kendoDropDownList({
				  
				  dataTextField : "name",
					dataValueField : "id",
					optionLabel: "Select Accommodation",
					dataSource :accommodationDataSource1
					
		       }).data("kendoDropDownList");
 
 
 $("#brachDropDownList").kendoDropDownList({
	  
	  dataTextField : "name",
		dataValueField : "id",
		optionLabel: "Select Branch",
		dataSource :branchDataSource1
		
  }).data("kendoDropDownList");
			  
			  
			  
			 
			$("#saveEmp").bind("click", function() {   
				
				
     				//alert("hello");
                	var id='',status,famly,nationID,contName,contNumber,
                	
                	sex,accommodationVal,residentVal,employeeId, departId,
                	
                	firstname, middlename, lastname, passNo, passExpDate,passPlace,
                	
                	birthdate, hiredate, empNation, empAddrss, photo,paidTax, phNo;
         			//crop variables
         			var x, y, width, height;
                	//id = $("#id").val(); 
                	
                	
                	
                	
                	
         			employeeId = $("#employeeId").val(); 
         			firstname = $("#firstname").val(); 
         			middlename = $("#middlename").val(); 
         			lastname = $("#lastname").val(); 
         			//sex = $("#sex").val();
         			residentVal=$("#residentVal").val();
         			
         			accommodationVal=$("#accommodationVal").val();
         			departId=$("#departVal").val();
         			sex=$('input:radio[name=sex]:checked').val();
         			paidTax=$('input:radio[name=status]:checked').val();
         			status="Active";
         			famly=$('input:radio[name=family]:checked').val();
         			
         			contName=$("#contName").val();
         			contNumber=$("#contNumber").val();
         		nationID=$("#nationID").val();
         		
         		phNo="534534534";
         		
         		passNo=$("#passNo").val();
         		
         		empNation=$("#empNation").val();
         		
         		passPlace=$("#passPlace").val();
         		
         		passExpDate=$("#passExpDate").val();
         		
         		empAddrss=$("#empAddrss").val();
         		
         		postDropDownList=postDropDownList;
         		
         		alert("employeeId"+employeeId+"-----"+"firstname"+firstname
         				
         			+"middlename"+middlename+"lastname"	+lastname+"residentVal"+residentVal
         			+"accommodationVal"+accommodationVal+"postDropDownList"+postDropDownList
         			+"sex"+sex+"status"+status+"famly"+famly+"nationID"+nationID
         		 +"contName"+contName+"contNumber"+contNumber+"empAddrss"+empAddrss
         		 +"passExpDate"+passExpDate+"empNation"+empNation+"passNo"+passNo+"passPlace"+passPlace
         		
         		);
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
 						'\n\n Contact Administrator for support');
	     				return;
         			}
         			
         			
         			
         			//status = $("#status").val();
         			
         			x = $('#x').val();
    				y = $('#y').val();
    				width = $('#w').val();
    				height = $('#h').val();
         			
    				var croppingData = JSON.stringify({
    					"x" : x,
    					"y" : y,
    					"width" : width,
    					"height" : height,
    					"employeeId" : employeeId
    				});
    				
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
    				
         			
         			if($("#profilePicUploader").val()){
         				photo = $("#profilePicUploader").val();  
         				
         				$.ajaxFileUpload({                	
                            url:'<%=request.getContextPath()+ "/do/EmployeePhotoAction"%>',
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
         				photo = "/data/photo/placeholder-pic.png";        				
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
    					"baseSalry":numericSal,
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
         				 url:'<%=request.getContextPath()+ "/do/EmployeeAction"%>',
        				 dataType : 'json',
        				 contentType : 'application/json; charset=utf-8',
        				 data : updateData,
        				 success : function(data){
        					 alert(data);
        					 empWindow.close(); 
        					 empDataSource.read();	
        				 }
        				
        			}); 
			});
		
			

			
			
		
			$("#postDropDownList").kendoDropDownList({
				dataTextField : "name",
				dataValueField : "coantcatVal",
				optionLabel: "Select Designation",
				dataSource : {
					type : "json",
					transport : {
						read : "<%=request.getContextPath()+ "/do/ReadPositionAction"%>"
					}
				}
			});
		});		 
</script>

<div id="imageCropper" style="display:none">	
	<a id="cropImage" class="k-button">OK</a>
	<input type=hidden id="x"/>
	<input type=hidden id="y"/>
	<input type=hidden id="w"/>
	<input type=hidden id="h"/>
	<img  alt="" id="target" src=""/>	
</div>
<script>
var cropperWindow;

var jcrop_api, boundx, boundy;
function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		
		reader.onload = function(e) {
			$("#preview").attr('src', e.target.result);
			/*$("#target").attr('src', e.target.result);
			jcrop_api = $('#target').Jcrop({
				 aspectRatio : 1, 
		         onChange: updatePreview,
		         onSelect: updatePreview		         
			 },function(){
		        // Use the API to get the real image size
		        var bounds = this.getBounds();
		        boundx = bounds[0];
		        boundy = bounds[1];
		        // Store the API in the jcrop_api variable
		        jcrop_api = this;
		      });
			$("#imageCropper").css("display" , "block");
			$("#imageCropper").css("width","400px");
			$("#imageCropper").css("height","400px")
			cropperWindow = $("#imageCropper").kendoWindow({
				title : "Employee Photo Editor",
				width : "auto",
				modal : true
			}).data("kendoWindow");
			
			cropperWindow.center();
			cropperWindow.open();
			$("#cropImage").bind("click", function(){
				cropperWindow.empty();
				cropperWindow.close();
			});
			function updatePreview(c){ 
				updateCoords(c);
		        if (parseInt(c.w) > 0)
		        {
		          var rx = 200 / c.w;
		          var ry = 200 / c.h; 
		          $('#preview').css({
		            width: Math.round(rx * boundx) + 'px',
		            height: Math.round(ry * boundy) + 'px',
		            marginLeft: '-' + Math.round(rx * c.x) + 'px',
		            marginTop: '-' + Math.round(ry * c.y) + 'px'
		          });
		        }
		      }
			*/
			/*function updateCoords(c)
			{
				$('#x').val(c.x);
				$('#y').val(c.y);
				$('#w').val(c.w);
				$('#h').val(c.h);				
			};*/		
		}

		reader.readAsDataURL(input.files[0]);
	}
}
</script>