<%@page import="com.openhr.employee.EmployeeIdUtility"%>


<div id="employeeForm">
	<div class="clear"></div>
	<div id="left-col">
	


		<div>
			<div class="label">Year:</div>
			<div class="field">
				<select>
  <option value="2013">2013</option>
  <option value="2012">2012</option>
  
</select>
			</div>
			<div class="clear"></div>
		</div>



		<div>
			<div class="label">Payrolls</div>
			<div class="field">
						<select multiple>
  <option value="2013">03/29/2013-Payroll1</option>
  <option value="2012">03/29/2013-Payroll1</option>
  
</select>
			</div>
			<div class="clear"></div>
		</div>





		




<div>
			<div class="label">Department</div>
			<div class="field">
				<input id="deprtDropDownList"/>
			</div>
			<div class="clear"></div>
		</div>
<div id="dp1">
			<div class="label">Employee</div>
			<div class="field">
				<input class="deprtDropDownList1"/>
			</div>
			<div class="clear"></div>
		</div>


		<div>
			<div class="field">
				<a class="k-button k-icontext" id="saveEmp"><span class="k-add k-icon"></span>Search</a> <a
					class="k-button k-icontext" id="cancelEmp"><span class="k-cancel k-icon"></span>Cancel</a>
			</div>
			<div class="clear"></div>
		</div>
	</div>



	<div class="clear"></div>

</div>
<div></div> 


<script> 


		$(document).ready(function(){
			
			
			
			$("#dp1").hide();
			
			
			
			 $("#cancelEmp").bind("click", function() { 
				 empWindow.content('');
				 empWindow.close();	                	 
             }); 
			$("#saveEmp").bind("click", function() {    
				
				alert($("#deprtDropDownList").val());
     				
			}); 	
			
		

			
		
			$("#deprtDropDownList").kendoDropDownList({
				dataTextField : "name",
				dataValueField : "id",
		
				dataSource : {
					type : "json",
					transport : {
						read : "<%=request.getContextPath()+ "/do/ReadRoleAction"%>"
					}
				}
			
		       }).data("kendoDropDownList");
			
			
			
	
			
			$("#deprtDropDownList").change(function() {
				$(".deprtDropDownList1").html('');
				$("#dp1").show();
				alert("value"+$(this).val());
				var dId = $("#deprtDropDownList").val();
				var id='';
				   
				
				alert(dId);
				var updateData = JSON.stringify([{
				
			
					"employeeId":dId
				 }]);  
				  var 	empDataSource1 = new kendo.data.DataSource({
	                	 transport : {
	                    		read : {
	                    			type: 'POST',
	                    			 url:'<%=request.getContextPath()+ "/do/GetEmpAllAction"%>',
			        				 dataType : 'json',
			        				 contentType : 'application/json; charset=utf-8',
			        				 cache: false
			        				 
	                    		},
	                     
	                    		parameterMap: function (data, type) {
	                    			if(type = "read"){
	                    				
	                    				return updateData;
	                    			}
	                    			
	                    		}
	                    	},
	                   
	                      
	                       	autoSync : true,
	                       	batch : true 
	                    });
				
				$(".deprtDropDownList1").kendoDropDownList({
					dataTextField : "username",
					dataValueField : "id",
			
					dataSource :empDataSource1
				
			       }).data("kendoDropDownList");
				
				
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
		},

		reader.readAsDataURL(input.files[0]);
	}
}
</script>