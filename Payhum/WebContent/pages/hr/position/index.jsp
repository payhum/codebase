<%@include file="../../common/jspHeader.jsp"%>
<h2 class="legend">Position Form</h2>

<div id="grid"></div>
<script type="text/javascript">
	var isCreating = false;
	
	var usrDataSource;
    $(document).ready(function(e){
		
        var positionModel = kendo.data.Model.define({
            id: "id",
            fields: {
                name: { type: "string", validation: { required: true } },
                lowSal: { type: "number", validation: { required: true, min : 500} },
                    highSal: { type: "number", validation: { required: true, min : 100 } }
            }
        });

        usrDataSource = new kendo.data.DataSource({
        	transport : {
           		read : {
           		 url : "<%=request.getContextPath() + "/do/ReadJobTitleAction"%>",
           			dataType : "json",
           			cache : false
           		},
                create : {
                    url : "<%=request.getContextPath() + "/do/PositionAction"%>",
                    dataType : 'json',
                    contentType : 'application/json; charset=utf-8',
                    type : 'POST',
                    	cache : false,
                    	complete: function (jqXHR, responseText) {usrDataSource.read();}
                },
                update : {
                	url : "<%=request.getContextPath() + "/do/UpdatePositionAction"%>",
                    dataType : 'json',
                    contentType : 'application/json; charset=utf-8',
                    type : 'POST',
                	cache : false,
                	complete: function (jqXHR, responseText) {usrDataSource.read();}
                },
                destroy : {
                	 url : "<%=request.getContextPath() + "/do/DeletePositionAction"%>",
											dataType : 'json',
											contentType : 'application/json; charset=utf-8',
											type : 'POST',
											cache : false,
											complete : function(data,
													responseText) {

												if (responseText == "success") {
													alert("Cannot be deleted as there are employee records referring this title.")
												} else if (responseText == "parsererror") {

												}

												usrDataSource.read();

											}
										},
										parameterMap : function(options,
												operation) {
											if (operation !== "read"
													&& options.models) {
												return JSON
														.stringify(options.models);
											}
										}
									},
									schema : {
										model : positionModel
									},
									batch : true,
									pageSize : 10
								});

						$("#grid").kendoGrid({
							dataSource : usrDataSource,
							columns : [ {
								field : "name",
								title : "Name",
								width : "150px"
							}, {
								field : "lowSal",
								title : "LowSalary",
								format : "{0:n2}",
								width : "100px"
							}, {
								field : "highSal",
								title : "HighSalary",
								format : "{0:n2}",
								width : "100px"
							}, {
								command : [ "edit", "destroy" ],
								title : "&nbsp;",
								width : "210px",
								filterable : false
							} ],

							toolbar : [ {
								"name" : "create",
								text : "Add New Role"
							} ],
							editable : "popup",
							sortable : true,
							scrollable : true,
							filterable : true,
							selectable : "row",
							pageable : true

						});
					});
</script>
