<%@page import="com.openhr.data.LeaveRequest"%>
 
<div id="leaveTabs">
	<div>
		<div class="k-content">
			<div class="legend">
				 
				<div style="float: left">
					<p>Other Income Declaration</p>
				</div>
				<div style="clear: both"></div>
			</div>
			<div id="addLeaveDiv"><br/><br/>
				<span><label>Overseas Taxable Income : &nbsp;&nbsp;</label></span>
				 <span><input type="text" name="leaveDate" id="overseas" /></span><br/>
				 <span><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other Taxable Income : &nbsp;&nbsp;</label></span> 
				 <span><input type="text" name="returnDate" id="other" /></span><br /> <br />  
				<div style="float: left;">
					<span><input type="submit" id="save" value="Save" /></span>&nbsp;&nbsp;
 				</div>
			</div>
			<br /> <br />
			<div id="grid"></div>
		</div>
	</div>
</div>

<script>
function getIncome(){
	$.ajax({
   		url 		: "<%=request.getContextPath() + "/do/income"%>",
   		type 		: 'POST',
		dataType 	: 'json',
		contentType : 'application/json; charset=utf-8',
		success     : function(data){
			$("#overseas").val(data[0]);
			$("#other").val(data[1]);
  		}
    }) ;  
}

$(document).ready(function() {
	getIncome();
	
});

$("#save").click(function(){
		
		income = JSON.stringify({
			"overseas"	  : $("#overseas").val(),
			"other" 	  : $("#other").val() 
		}); 
		
 
   		$.ajax({
       		url 		: "<%=request.getContextPath() + "/do/IncomeAction"%>",
       		type 		: 'POST',
			dataType 	: 'json',
			contentType : 'application/json; charset=utf-8',
			data 		: income,
			success     : function(){
				alert("Changes are saved successfully.")
			    getIncome();
			}
        });  
});
</script>

