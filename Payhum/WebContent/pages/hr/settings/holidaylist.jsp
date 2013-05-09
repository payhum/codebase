<style>
.displayClass {
	display: none;
}
</style>

<div id="benefitTabs">

	<div>

		<div class="k-content">
			<div class="legend">
				<div style="float: right">
					<input type="submit" value="New" id="addHolidayDiv" /> <input
						type="submit" id="deleteHoliday" value="Delete" class="displayClass"/> <input
						type="submit" value="Edit" style="display: none !important;" />
				</div>

				<div style="float: left">
					<p>Holidays History</p>
				</div>
				<div style="clear: both"></div>
			</div>
			<br />
			<br />

			<div id="holidaysDiv" class="displayClass">
				<span><label>Holiday Date : &nbsp;&nbsp;</label></span> <span><input
					type="text" name="holidayDate" id="holidayDate" /></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span><label>Holiday Name : &nbsp;&nbsp;</label></span> <span><input
					type="text" name="holidayName" id="holidayName" /></span><br />
				<br />
				<div style="float: right;">
					<span><input type="submit" id="addHoliday" value="Apply" /></span>&nbsp;&nbsp;
					<span><input type="button" id="cancelHoliday" value="Cancel" /></span>
				</div>
			</div>
			<br />
			<br />
			<div id="holidayListGrid"></div>
		</div>
	</div>
</div>
<script>

	$(document).ready(function() {
 		$("#holidayDate").kendoDatePicker();
 		getHolidays();
 		
 		$("#holidayListGrid").delegate(".k-grid-content", "click", function(e){
			var containClass = $("#deleteHoliday").hasClass("displayClass");
			if(containClass){
				$("#deleteHoliday").removeClass("displayClass");
			}
			else{
				$("#deleteHoliday").addClass("displayClass");
			}
			
	 	});
 	}); 
		
	$("#addHolidayDiv, #cancelHoliday").click(function(){
		isDisplayed = $("#holidaysDiv").hasClass("displayClass");
		if(isDisplayed){
			 $("#holidayDate").val('');
			 $("#holidayName").val('');
			 $("#holidaysDiv").removeClass("displayClass");
		}
		else{
			$("#holidayDate").val('');
			$("#holidayName").val('');
			$("#holidaysDiv").addClass("displayClass");
		}
	});
	
	$("#addHoliday").click(function(){
		holidayDate   = $("#holidayDate").val();
		holidayName   = $("#holidayName").val();
 		 
		addHoliday = JSON.stringify({
				"holidayDate"	  : holidayDate,
				"holidayName" 	  : holidayName
 		}); 
		
			$.ajax({
	   		url 		: "<%=request.getContextPath() + "/do/addHoliday"%>",
	   		type 		: 'POST',
			dataType 	: 'json',
			contentType : 'application/json; charset=utf-8',
			data 		: addHoliday,
			success     : function(){
				$("#holidaysDiv").addClass("displayClass");
 			    $("#holidayListGrid").empty();
				getHolidays();    
			}
	    });  
	});

	
	$("#deleteHoliday").click(function(){
 		 date  = $(".k-state-selected").find('td').eq(0).text();
  	  	 deleteHoliday = JSON.stringify({
 	   		"date"   : date 
	 	 }); 
  	  	 
  	  	 var deleteStatus = confirm('Are you sure want to Delete ?');
	    	
  	  	 if(deleteStatus){
		  	 $.ajax({
		   		url 		: "<%=request.getContextPath() + "/do/DeleteHoliday"%>",
		    	type 		: 'POST',
				dataType 	: 'json',
				contentType : 'application/json; charset=utf-8',
				data 		: deleteHoliday,
				success     : function(){
					$("#holidayListGrid").empty();
					getHolidays();
				}
		 	 });  
  	  	 }
 	}); 

	
	function getHolidays(){
 			 
			
			$("#holidayListGrid").kendoGrid({
				dataSource : {
					transport : {
						read : {
	                      url : "<%=request.getContextPath() + "/do/ReadHolidays"%>",
	                      dataType : 'json',
	                      cache : false
	                  },
	                  parameterMap: function(options, operation) {
	                      if (operation !== "read" && options.models) {
	                      	$.each(options.models, function(){
	                      		/*this.leaveDate = new Date(this.leaveDate);
	                      		this.returnDate = new Date(this.returnDate);*/
	                      	});
	                          return JSON.stringify(options.models);
	                      }
	                  }
					},
					 
					batch : true,
	              pageSize : 10
				},
				columns: [
	 				{ field : "date", title : "Holiday Date", template : "#= kendo.toString(new Date(date), 'MMM, dd yyyy') #", width : 50  },
	 	            { field : "name", title : "Holiday Name", width : 50 },
 	             ], 
	          sortable: true,
	          scrollable: true,
	          filterable : true,
	          selectable : "row",
	           pageable : true
			});	
		}
 
</script>

<script id="searchByNameAutoComplete" type="text/x-kendo-tmpl">
 	<div class="autoCompleteDIV">
		<img width=50 height=50 class='image_preview'  src="#='/OpenHR'+photo#"/> 
		<b>#=employeeId+' - '+firstname+' '+middlename+' '+lastname#</b>
	</div>
</script>
<style>
div.autoCompleteDIV {
	vertical-align: middle;
	display: block;
}
</style>