<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">Department Form</h2> 


<script type="text/x-kendo-template" id="toolbarTemplate">
<div class="roleToolbar">
	<input type="checkbox" value="administrator"/>Administration
	<input type="checkbox" value="financeUser"/>Finance
	<input type="checkbox" value="employeeUser"/>Employee User
</div>
</script>
<div id="grid">
</div>
<script> 
    $(document).ready(function(e){        	
    	var usersModel = kendo.data.Model.define({
        	id : "id",
        	fields :{   
        			name : {
        			type : "string",
        			validation : {
        				required : true
        			}
        		} 
        	}
        });
        
        var roleDataSource = new kendo.data.DataSource({
        	transport : {
           		read : {
           			url : "<%=request.getContextPath() + "/do/ReadRoleAction"%>",
           			dataType : "json",
           			cache : false
           		},
                create : {
                    url : "<%=request.getContextPath() + "/do/RoleAction"%>",
                    dataType : 'json',
                    contentType : 'application/json; charset=utf-8',
                    type : 'POST'
                },
                update : {
                    url : "<%=request.getContextPath() + "/do/UpdateRoleAction"%>",
                    dataType : 'json',
                    contentType : 'application/json; charset=utf-8',
                    type : 'POST'
                },
                destroy : {
                    url : "<%=request.getContextPath() + "/do/DeleteRoleAction"%>",
                    dataType : 'json',
                    contentType : 'application/json; charset=utf-8',
                    type : 'POST'
                },
                parameterMap : function(options, operation){
           			if(operation !== "read" && options.models){  
           				return JSON.stringify(options.models);
           			}
           		}
           	},
           	schema : {
           		model : usersModel
           	},
           	batch : true,
           	pageSize : 10
        });
        
        
        $("#grid").kendoGrid({
            dataSource : roleDataSource, 
            columns : [
				{ field : "name", title : "Department Name", width : 500 },
                { command : ["edit", "destroy"], width : 210,
					filterable: false}
            ], 
            toolbar : [{"name" : "create",text : "Add New Department"}],  
            sortable: true,
            scrollable: true,
            filterable : true, 
            columnMenu : true, 
            pageable : true,
            selectable : "row", 
            editable : "popup" 
        });       
        
    });    
    
     
</script>  
<style scoped>
.roleToolbar{
	float:right;
}
</style>
