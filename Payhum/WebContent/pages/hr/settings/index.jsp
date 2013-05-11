<%@page import="com.openhr.Config"%>
<%@include file="../../common/jspHeader.jsp"%>
<%
	Config.readConfig();
%>
<div style="float: left; padding-top: 50px; padding-right: 30px;">
	<img alt=""
		src="<%=request.getContextPath()
					+ "/css/images/settings-banner.png"%>" />
</div>
<div style="margin-left: 200px;">
	<h2 style="font-size: 20px">Global System Settings</h2>
	<span style="padding: 2px;"><input type="checkbox"
		style="font-size: 13px;" id="makeEditable" value="edit" />Edit</span><br />

	<label>Employee Id Prefix</label><br /> <input
		class="k-input k-textbox" type="text" id="idPattern"
		disabled="disabled" value="<%=Config.employeeIdPattern + "-0000"%>" /><br />
	<br /> <%-- <label>Employee Promotion Strategy</label><br /> <input
		type="text" id="promotionStrategy" disabled="disabled"
		value="<%=Config.employeePromotionStrategy%>" /><br /> <br /> <select
		id="promotionStrategyDrl" style="display: none;">
		<option value="0">Select</option>
		<option value="Automatic">Automatic</option>
		<option value="User defined">User defined</option>
	</select> <label>Payroll Generation Strategy</label><br /> <input type="text"
		id="payrollStrategy" disabled="disabled"
		value="<%=Config.payrollStrategy%>" /><br /> <br /> <select
		id="payrollStrategyDrl" style="display: none;">
		<option value="0">Select</option>
		<option value="Automatic">Automatic</option>
		<option value="User defined">User defined</option>
	</select> <a id="saveChanges" class="k-button" href="#">Save</a> --%>
</div>
<div style="clear: both"></div>
<script>

	$(document).ready(function(){ 
		$("#promotionStrategy").kendoDropDownList({
			dataTextField: "text",
	        dataValueField: "value",
	        dataSource: [
	            { text: "Automatic", value: "Automatic" },
	            { text: "User defined", value: "User defined" }
	        ],
	        enabled : false
		});
		
		$("#promotionStrategy").data("kendoDropDownList").value("<%=Config.employeePromotionStrategy%>");
		$("#payrollStrategy").kendoDropDownList({
			dataTextField: "text",
	        dataValueField: "value",
	        dataSource: [
	            { text: "Automatic", value: "Automatic" },
	            { text: "User defined", value: "User defined" }
	        ],
	        enabled : false
		});
		$("#payrollStrategy").data("kendoDropDownList").value("<%=Config.payrollStrategy%>");
		
		
		//sends an ajax request to the specified url
		$("#saveChanges").click(function(){
			if($("#makeEditable").attr('checked')){
				var newPattern = $("#idPattern").val();
				var newPromotionStrategy = $("#promotionStrategy").val();
				var newPayrollStrategy = $("#payrollStrategy").val();
				
				
				newPattern = newPattern.replace('-0000', '');
				
				var settingData = JSON.stringify({"idPattern" : newPattern, "promotionStrategy": newPromotionStrategy, "payrollStrategy" : newPayrollStrategy});
				$.ajax({
					url : "<%=request.getContextPath() + "/do/SettingsAction"%>", 
															type : 'POST',
															dataType : 'json',
															contentType : 'application/json; charset=utf-8',
															data : settingData,
															success : function() {
																$(
																		"#makeEditable")
																		.removeAttr(
																				'checked');
																$("#idPattern")
																		.val(
																				$(
																						"#idPattern")
																						.val()
																						+ "-0000");
																$("#idPattern")
																		.attr(
																				'disabled',
																				'disabled');
																$(
																		"#promotionStrategy")
																		.data(
																				"kendoDropDownList")
																		.enable(
																				false);
																$(
																		"#payrollStrategy")
																		.data(
																				"kendoDropDownList")
																		.enable(
																				false);
																alert("Settings saved successfully!");
															},
															failure : function(
																	e) {
																alert(e.responseText);
															}
														});
											} else {
												alert("You have not made any changes to the settings! \n Make sure you have checked on the Edit checkbox before saving");
											}
										});

						//
						$("#makeEditable").bind(
								"click",
								function(e) {
									if ($(this).attr('checked')) {
										$("#idPattern").val(
												$("#idPattern").val().replace(
														'-0000', ''));
										$("#idPattern").removeAttr('disabled');
										$("#promotionStrategy").data(
												"kendoDropDownList").enable(
												true);
										$("#payrollStrategy").data(
												"kendoDropDownList").enable(
												true);

									} else {
										$("#idPattern")
												.val(
														$("#idPattern").val()
																+ "-0000");
										$("#idPattern").attr('disabled',
												'disabled');
										$("#promotionStrategy").data(
												"kendoDropDownList").enable(
												false);
										$("#payrollStrategy").data(
												"kendoDropDownList").enable(
												false);
									}
								});

					});
</script>