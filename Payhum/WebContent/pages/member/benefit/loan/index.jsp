<div id="loanTabs">
	 
	<div>

		<div class="k-content">
	
			<div class="legend"> 
				<div style="float:left">
					<p>Holidays List</p>
				</div>
				<div style="clear:both"></div>
			</div><br/><br/>
			
			<div id="holidayListGrid"> </div>
		</div>
	</div>
</div>
<script>
	function getHolidays(){
			
		var holidayModal = kendo.data.Model.define({
	  	id: "id",            
	      fields: {
	      	date : {
	      		type : "date"
	      	},
	        
	      	name : {
	  			type : "string"
	  		},
	  	 
	      }
	  });		
		
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
				schema : {
					model :holidayModal
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


	$(document).ready(function() {
 			getHolidays();
	}); 
</script>