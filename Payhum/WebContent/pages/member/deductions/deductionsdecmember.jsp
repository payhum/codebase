
<style scoped>

            


           
            

                .required {
                    font-weight: bold;
                }

                .accept, .status {
                    padding-left: 90px;
                }

                .valid {
                    color: green;
                }

                .invalid {
                    color: red;
                }
                span.k-tooltip {
                    margin-left: 6px;
                }
            </style>

	
	
	 	<div id="wholeDeduc">
 	
 			<div class="legend">
 				<div style="float:right" >
					<input type="submit" class="applyDeduc" value="New"/>
					<input type="submit" value="Delete" class="deleteDeduc"/>
					
				</div>
 				<div style="float:left">
					<p>Deductions Declaration</p>
				</div>
				<div style="clear:both"></div><br/>
			</div><br/>
 			<div id="addDeducDiv" >
 	<span id="tickets">
 		<form>
 					<span><label>Deduction Type : &nbsp;&nbsp;</label></span>
 					
					<span><select id="multiselect23"  ></select></span>&nbsp;&nbsp;
 					<span><label for="dedcamnt" >Deduction Amount: &nbsp;&nbsp;</label></span>
 				
					<span><input type="text" class="k-textbox"   id="dedcamnt" required /></span><br/><br/>
			
					<span><label>Deduction Description :&nbsp;&nbsp; </label></span>
 						
 					<span><input type="text" class="k-textbox"  id="dedcDesc" /></span>
						</form>
  					<div style="float:right;">
	  					<span><input type="submit" class="addDeduc" value="Apply"/></span>&nbsp;&nbsp;
						<span><input type="button" class="canceDeduc" value="Cancel"/></span>
				
               
               
 					</div>
 					
 				</span>
  			</div>

  	<div id="grid3"> </div>
 		</div>

				
	
	  		


<script>



var empDataSource3;


var flagSelct=false;
var wholeDeduc;
    $(document).ready(function(e){	
  
    	 
    
  wholeDeduc=$("#wholeDeduc").html();
     	//alert(wholeDedu);
    	function start()
    	
    	{
    		
    	  	
   			$("#wholeDeduc").html(wholeDeduc);
   			
	$("#addDeducDiv").hide();
    
	        
	       empDataSource3 = new kendo.data.DataSource({
	        	transport : {
	           		read : {
	           			url : "<%=request.getContextPath() + "/do/ReadDeductionDeclAction?parameter=getAllDeduction"%>",
	           			dataType : "json",
	           			cache : false
	           		}
	           	},
	           	pageSize: 4,
	           	autoSync : true,
	           	batch : true 
	        });
	        
	  
	       var grid33=$("#grid3").kendoGrid({
	            dataSource : empDataSource3, 
	            columns : [{hidden:true, field : "id", title : "id", },
	                       {field : "type", title : "Name",template:'#=type ? type.name: ""#', width : 50 },
	                       
	                      {field : "amount", title : "Amount", width : 50},
	                       {field : "description", title : "Description", width : 50}
	                ],             
	        
	            
	                sortable: true,

	               
	                selectable : "row",
	                 pageable : true,
	                 height:200,
	        }).data('kendoGrid');
	         

	     


	        
	                
   
    
   
	$("#multiselect23").kendoDropDownList({
		dataTextField : "name",
		dataValueField : "id",
		optionLabel: "Select Deduction",
		optionValue: "-1",
		dataSource : {
			type : "json",
			transport : {
				read : "<%=request.getContextPath()+ "/do/ReadDeductionAction"%>"
			}
		}
    }); 
	
		
	 $("#dedcamnt").kendoNumericTextBox();
		
		
	
    $(".canceDeduc").bind("click",function(){
    	//alert("");
    	//$("form")[0].reset();
    //$("#multiselect23").removeAttr('selected');
   // alert($("#multiselect23").val());
    

 $("form")[0].reset();
	$("#addDeducDiv").slideToggle();
    });
    $(".applyDeduc").bind("click",function(){
       	//$("form")[0].reset();
 	//alert("applyDeduc");
$("form")[0].reset();

    	$("#addDeducDiv").slideToggle();
    	
    });
    $("#multiselect23").bind("change",function(){
    	checkValue=$(this).val();
    	var dedDelete = JSON.stringify({
   			"ids"	  : checkValue,
   			
    	}); 
    	$.ajax({
       		url 		: "<%=request.getContextPath() + "/do/ReadDeductionDeclAction?parameter=checkDeduction"%>",
       		type 		: 'POST',
    		dataType 	: 'json',
    		contentType : 'application/json; charset=utf-8',
    		data 		: dedDelete,
    		success     : function(data){
    			//$("#addOverTimeDiv").addClass("displayClass");
    		    
    			//alert(data);
    			if(data)
    				{
    				alert("alredy Deduction Declare");
    				$("#multiselect23").focus();
    				flagSelct=true;
    				}
    			else
    			{
    				flagSelct=false;
    			}
    		  	//alert(wholeDedu);
    			
    			
    		}
        });
    });
    
    $(".deleteDeduc").bind("click",function(){
    	
		 ids  = $(".k-state-selected").find('td').eq(0).text();
		// alert(ids);
    	var dedDelete = JSON.stringify({
   			"ids"	  : ids,
   			
    	}); 
    	$.ajax({
       		url 		: "<%=request.getContextPath() + "/do/ReadDeductionDeclAction?parameter=deleteDeduction"%>",
       		type 		: 'POST',
    		dataType 	: 'json',
    		contentType : 'application/json; charset=utf-8',
    		data 		: dedDelete,
    		success     : function(data){
    			//$("#addOverTimeDiv").addClass("displayClass");
    		    
    			alert(data);
    			
    		  	//alert(wholeDedu);
    			
    			start();
    		}
        });
    	
    });
    

$(".addDeduc").bind("click",function(){
    var multiselect23=$("#multiselect23").val();
	var dedcDesc=$("#dedcDesc").val();
	
	var dedcamnt=$("#dedcamnt").val();
	if(multiselect23==''||dedcDesc==''||dedcamnt=='')
		{
		
		alert("fill data");
		return false;
		}
	
	//alert(flagSelct);
    if(flagSelct)
    	{
    	alert("alredy Deduction Declare");
    	return false;
    	}
	
	var deds = JSON.stringify([{
		"description"	  :dedcDesc,
			"type"	      :multiselect23,
			"amount" 	  :dedcamnt
	
	 }]);
	
	$.ajax({
   		url 		: "<%=request.getContextPath() + "/do/ReadDeductionDeclAction?parameter=saveDeduction"%>",
   		type 		: 'POST',
		dataType 	: 'json',
		contentType : 'application/json; charset=utf-8',
		data 		: deds,
		success     : function(data){
			//$("#addOverTimeDiv").addClass("displayClass");
		    
			alert(data);
			
		  	//alert(wholeDedu);
			
			start();
		}
    });
	
});
   	
  
        
    
    	}
    	
    	start();
});
</script>


