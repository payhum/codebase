<%@include file="../common/jspHeader.jsp"%>
<div style="padding-top: 5px;">	
	<div style="float:right">
		<input id="accountSettings" type="button" value="Account"/>
	</div>	
</div> 
<script>
	$("#accountSettings").click(function(e){
		$("#accountSettingsWnd").css("display","block");
		var accountSettingWnd = $("#accountSettingsWnd").kendoWindow({
			modal : true,
			title : "Account Settings" 
		}).data("kendoWindow");
		accountSettingWnd.center();
		accountSettingWnd.open();
	});	
</script>