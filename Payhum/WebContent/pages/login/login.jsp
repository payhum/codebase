

<%@include file="../common/jspHeader.jsp" %>
<style>
.displayClass{
	display : none !important;
}
</style>
<div id="loginFormContainer">
    <html:form action="/LoginAction" styleClass="loginForm">
    	<h2 class="login-legend">MPTS Login
    	<span style="float:right;padding-right:40px;padding-top:15px">
    		<span><img src="<%=request.getContextPath() + "/css/images/mm-flag.png"%>"></span>
    		<!--a class="link" href="<%=request.getContextPath() + "/do/ChangeLanguage?method=amharic" %>">
	    		<span><img src="<%=request.getContextPath() + "/css/images/mm-flag.png"%>"></span>
	    	</a-->
			<!-- a class="link" href="<%=request.getContextPath() + "/do/ChangeLanguage?method=english" %>">
				<span><img src="<%=request.getContextPath() + "/css/images/us_flag.png"%>"</span>
			</a--> 
    	</span>
    	
    	
    	</h2>
		<div>
			
			<label><bean:message key="userName"/></label><br />
		   	<html:text property="username" styleId="userName" value="" onblur="getRoles()" /><br />
       
           	<label><bean:message key="password"/></label><br />
           	<html:password property="password" styleId="passWord" value="" /><br /><br />
       
          	<a id="forgotPassword"  class="k-link" style="cursor:pointer;"> <label>Forgot Password</label>&nbsp; ?</a><br /><br />
          	
          	<div class="roleSelectorWindow" style="display:block;padding:0;">
				<label><bean:message key="loginAs"/></label>
				<span id="master" class="displayClass">
					<select id="loginAs">
						<option value="Employee"><bean:message key="employee"/></option>
						<option value="HumanResource">Human Resource</option>
						<option value="Accountant">Accountant</option>
						<option value="PageAdmin"><bean:message key="pageAdmin"/></option>
						<option value="MasterAdmin">Master Admin</option>
					</select>
				</span>
				<span id="client">
					<select id="loginAs1">
						<option value="Employee"><bean:message key="employee"/></option>
						<option value="HumanResource">Human Resource</option>
						<option value="Accountant">Accountant</option>
						<option value="PageAdmin"><bean:message key="pageAdmin"/></option>
	 				</select>
 				</span>
				<input type="button" id="setLoginAs" class="k-button" value="Login"></input>
			</div>
        		<html:hidden property="role" styleId="isAdmin" value="false"/> 
	        <a id="submitForm" href="#" class="k-button" style="display : none;">
	        	<bean:message key="login"/>
	        </a>
			<input type="hidden" id="roleId" value=""/>
	        
       </div>
    </html:form>
</div>

<div class="k-content" id="accountSettingsWnd" style="display:none">
<div>
		<div >
			<div id="form">
				<div>
 					<div>
						<div class="form-label">
							<label>Username</label>
						</div>
						<div class="form-field">
							<input class="k-input k-textbox" type="text" id="changeUserName" value="" />
						</div>
						<div style="clear: both"></div>
					</div>
 
					<div>
						<div class="form-label">
							<label>New Password</label>
						</div>
						<div class="form-field">
							<input class="k-input k-textbox" type="password" id="changeNewPassword" value="" />
						</div>
						<div style="clear: both"></div>
					</div>

					<div>
						<div class="form-label">
							<label>Confirm Password</label>
						</div>
						<div class="form-field">
							<input class="k-input k-textbox" type="password"
								id="changeConfirmPassword" value="" />
						</div>
						<div style="clear: both"></div>
					</div>
					
					<div>
						<div class="form-label">
							<label>DateOfBirth</label>
						</div>
						<div class="form-field">
							<span><input type="text" name="dob" id="dob" /></span>
						</div>
						<div style="clear: both"></div>
					</div>
					
					<div style = "float : rught;">
						<a class="k-button" id="changeForgetPassword">Save</a>
					</div>

 				</div>

			</div>
		</div>
		
	</div>

</div>



<script>

	function getRoles(){
		userInfor = JSON.stringify({
   			"userName"	: $("#userName").val(),
     	}); 
	
   		$.ajax({
       		url 		: "<%=request.getContextPath() + "/do/getUserRoles"%>",
       		type 		: 'POST',
			dataType 	: 'json',
			contentType : 'application/json; charset=utf-8',
			data 		: userInfor,
			success     : function(data2){
				$("#roleId").val(data2[0]);
			   if(data2[0] == 1){
 				   $("#master").removeClass("displayClass");
 				  $("#client").addClass("displayClass");
 				   
			   }
			    

			},
			 
        });  
	}
	 
	
	function validateUser(){
		var loginas;
		if($("#roleId").val() == 1 ){
			loginas = $("#loginAs").val();
		}
		else{
			loginas = $("#loginAs1").val();
		}
 		userDetails = JSON.stringify({
   			"userName"	: $("#userName").val(),
   			"passWord" 	: $("#passWord").val(), 
   			"loginAs"   : loginas
    	}); 
	
   		var result;
   		$.ajax({
       		url 		: "<%=request.getContextPath() + "/do/validateUser"%>",
       		type 		: 'POST',
			dataType 	: 'json',
			contentType : 'application/json; charset=utf-8',
			data 		: userDetails,
			success     : function(data){
			   if(data[0] == 0){
				   alert('Enter Valid Username');
			   }
			   else if(data[0] == 1){
				   alert('Enter Valid  Password');
			   }
			   else if(data[0] == 2){
				   alert('Select Valid Role to which you belong to.');
			   }
			   else {
			   	   $(".loginForm").submit();
			   }
			    
			},
			 
        });  
   		
	}


	$(document).ready(function(){
		$("#dob").kendoDatePicker();
		$("#loginAs").kendoDropDownList();
		$("#loginAs1").kendoDropDownList();
		$("#submitForm").bind("click", function(e){
			 $("#submitForm").css("display", "none");
			e.preventDefault();
			$(".roleSelectorWindow").slideToggle("fast");
			$(".loginAs").kendoDropDownList(); 	
		});
		$("#setLoginAs").bind("click", "keypress" , function(){
			
			var loginas;
			if($("#roleId").val() == 1 ){
				loginas = $("#loginAs").val();
			}
			else{
				loginas = $("#loginAs1").val();
			}
			
  			var loginAs = loginas;
			$("#isAdmin").val(loginAs);
 			validateUser();
			 
		});		
 
		$("#passWord").keypress(function(e){
			if(e.which == 13){
				$("#setLoginAs").click();
			}
		});
		
		
		$("#forgotPassword").click(function(e){
			$("#accountSettingsWnd").css("display","block");
			var accountSettingWnd = $("#accountSettingsWnd").kendoWindow({
				modal : true,
				title : "Account Settings" 
			}).data("kendoWindow");
			accountSettingWnd.center();
			accountSettingWnd.open();
		});	
		
		
		$("#changeForgetPassword").click(function(){
			changeUserName    = $("#changeUserName").val();
			changeNewPassword = $("#changeNewPassword").val();
			changeConfirm     = $("#changeConfirmPassword").val();
			dob               = $("#dob").val();
			var flag = 0;
			if(changeNewPassword != changeConfirm){
				flag = 1;
				alert('New Password and Confirm Password should be same');
			}
			if(changeUserName == ""){
				flag =1;
			}
			
			var forgotPassword = JSON.stringify({
				"changeUserName"    : changeUserName,
				"changeNewPassword" : changeNewPassword,
				"changeConfirm"     : changeConfirm,
				"dob"				: dob
	   			
	    	}); 
		
			
			if(flag == 0){
	 			$.ajax({
		       		url 		: "<%=request.getContextPath() + "/do/forgotPassword"%>",
		       		type 		: 'POST',
					dataType 	: 'json',
					contentType : 'application/json; charset=utf-8',
					data 		: forgotPassword,
					success     : function(data){
	 					if(data[0] == 0){
							alert('Username is Incorrect');
						}
						if(data[1] == 1){
							alert('Incorrect Date of Birth for the given username');
						}
						if(data[1] !=1 && data[0] != 0){
							alert('Password is changed successfully');
	  					}
					  
					},
					 
		        });  
		   		
			}
			
			
	 		 
			
	 	});
		

		
		

		
	});
	
</script>
<style scoped>
	a.link{
		text-decoration: none;
	}
</style>