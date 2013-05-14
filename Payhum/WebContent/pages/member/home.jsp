<%@include file="../common/jspHeader.jsp"%>
<div id="tabs">
	<ul>

		<li class="k-state-active">Notices</li>
		<li>Holidays</li>
		<li>Leave</li>
		<li>Overtime</li>
		<li>Deductions Declaration</li>	
		<li>Income Declaration</li>	
	    
	</ul>
	
	<div id="noticeBoard">
		<tiles:insert page="../../pages/member/notices/index.jsp" />
	</div>
	
	<div id="loan">
		<tiles:insert page="../../pages/member/benefit/loan/index.jsp" />
	</div>
	
	<div id="leave">
		<tiles:insert page="../../pages/member/leave/index.jsp" />
	</div>

	<div id="benefitPack">
		<tiles:insert page="../../pages/member/benefit/overtime/index.jsp" />
	</div>
		
	<div id="deductions">
		<tiles:insert page="../../pages/member/deductions/deductionsdecmember.jsp" />
	</div>	
	
	<div id="loan">
		<tiles:insert page="../../pages/member/incomeDeclaration.jsp" />
	</div>

		
</div>


<!-- Account Setting Starts here -->
<div class="k-content" id="accountSettingsWnd" style="display:none">
	<div>
		<div >
			<div id="form">
				<div>
					<input type="button" id="makeEditable" value="Edit"/>
					<span style="display : none;"><a class="k-button k-icontext" id="cancelCmp" style="display:none !important;"><span class="k-cancel k-icon"></span>Cancel</a></span>
					
 					
					
					<div>
						<div class="form-label">
							<label>Username</label>
						</div>
						<div class="form-field">
							<input class="k-input k-textbox" type="text" id="newUsername"
								disabled="disabled"
								value="<%=request.getSession().getAttribute("loggedUser")
					.toString()%>" />
						</div>
						<div style="clear: both"></div>
					</div>

					<div>
						<div class="form-label">
							<label>Old Password</label>
						</div>
						<div class="form-field">
							<input class="k-input k-textbox" type="password" id="oldPassword"
								disabled="disabled" value="" />
						</div>
						<div style="clear: both"></div>
					</div>

					<div>
						<div class="form-label">
							<label>New Password</label>
						</div>
						<div class="form-field">
							<input class="k-input k-textbox" type="password" id="newPassword"
								disabled="disabled" value="" />
						</div>
						<div style="clear: both"></div>
					</div>

					<div>
						<div class="form-label">
							<label>Confirm Password</label>
						</div>
						<div class="form-field">
							<input class="k-input k-textbox" type="password"
								id="confirmPassword" disabled="disabled" value="" />
						</div>
						<div style="clear: both"></div>
					</div>

					<input type="hidden" id="formIsActive" value="false"/>
				</div>

			</div>
		</div>
		
	</div>
</div>
<!-- end of Account setting -->
<script>
	var messagesWindow = "";	
	var empWindow;
	$(document).ready(function() {
		$("#tabs").kendoTabStrip({
			animation : {
				open : {
					effects : "fadeIn"
				}
			}
		});
		
		$("#cancelCmp").bind("click", function() { 
 			empWindow.content('');
			empWindow.close();	                	 
   		}); 
			 
		
		$("#hideNotificaiton").bind("click", function(e) {
			$("#notificaitonBar").hide(function() {
				$("#showNotificaitonBar").show();
			});
		});

		$("#showNotification").bind("click", function(e) {
			$("#showNotificaitonBar").hide();
			$("#notificaitonBar").show();
		});
			
		
		$("#makeEditable").click( function(e) {
			var formIsActive = $("#formIsActive").val();
			
			if (formIsActive=='false') {
				$("#oldPassword").removeAttr('disabled');
				$("#newPassword").removeAttr('disabled');
				$("#confirmPassword").removeAttr('disabled');
				$("#saveChanges").removeAttr('disabled');
				$("#formIsActive").val('true');
				$("#makeEditable").val('Done');
				
			} else {
				//call form data validator
				
				//validation succeeded
				
				//collect form data and prepare for sever call in json format
				var oldUserName ='<%=request.getSession().getAttribute("loggedUser").toString()%>' ;
				var oldPassword =$("#oldPassword").val() ;
				var newPassword=$("#newPassword").val();
				var confirmPassword=$("#confirmPassword").val();
				
				var userUpdateDataJSON = JSON.stringify([{"oldUserName" : oldUserName,
					"oldPassword" : oldPassword, "newPassword" : newPassword,
					"confirmPassword" : confirmPassword}]);
				
				$.ajax({
					url : "<%=request.getContextPath() + "/do/UpdateUserAction"%>",
                    dataType : 'json',
                    contentType : 'application/json; charset=utf-8',
                    type : 'POST',
                    data : userUpdateDataJSON,
					success : function(data){
 						if(data[0] == 0){
							alert('Passwords do not match.');
							$("#cancelCmp").click();
						}
					    if(data[1] == 1){
  							alert('Old Password is incorrect.');
 							$("#cancelCmp").click();
							
						}
					    if(data[0] != 0 && data[1] != 1){
					    	alert('Account settings saved successfully');
					    	$("#cancelCmp").click();
					    }
						
					} 
                    
				});		
				
				$("#oldPassword").attr('disabled', 'disabled');
				$("#newPassword").attr('disabled', 'disabled');
				$("#confirmPassword").attr('disabled', 'disabled');
				$("#saveChanges").attr('disabled', 'disabled');
				$("#formIsActive").val('false');
				$("#makeEditable").val('Edit');
			}
		});

	});
</script>