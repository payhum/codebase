<%@include file="../common/jspHeader.jsp" %>
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
		   	<html:text property="username" styleId="userName" value=""  /><br />
       
           	<label><bean:message key="password"/></label><br />
           	<html:password property="password" styleId="passWord" value="" /><br /><br />
       
          	<a id="forgotPassword" class="k-link">
          	<bean:message key="resetPassword"/>?</a><br /><br />
          	
          	<div class="roleSelectorWindow" style="display:none;padding:0;">
				<label><bean:message key="loginAs"/></label>
				<select id="loginAs">
					<option value="Employee"><bean:message key="employee"/></option>
					<option value="HR">Human Resource</option>
					<option value="Accountant">Accountant</option>
					<option value="Administrator"><bean:message key="pageAdmin"/></option>
					
					<!-- comment out the below line in client mode of deployment -->
					<option value="MasterAdmin">Master Admin</option>
				</select>
				<a id="setLoginAs" class="k-button"><bean:message key="ok"/></a>
			</div>
        	<html:hidden property="role" styleId="isAdmin" value="false"/> 
	        <a id="submitForm" href="#" class="k-button" >
	        	<bean:message key="login"/>
	        </a>
       </div>
    </html:form>
</div>


<script>

	function validateUser(){
 		userDetails = JSON.stringify({
   			"userName"	: $("#userName").val(),
   			"passWord" 	: $("#passWord").val(), 
   			"loginAs"   : $("#loginAs").val()
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
				   alert('Enter Valid UserName');
			   }
			   else if(data[0] == 1){
				   alert('Enter Valid  PassWord');
			   }
			   else if(data[0] == 2){
				   alert('Enter Valid Role');
			   }
			   
			   $(".loginForm").submit();
			    
			},
			 
        });  
   		
	}


	$(document).ready(function(){
		
		$("#submitForm").bind("click", function(e){
			 $("#submitForm").css("display", "none");
			e.preventDefault();
			$(".roleSelectorWindow").slideToggle("fast");
			$("#loginAs").kendoDropDownList(); 	
		});
		$("#setLoginAs").bind("click", function(){
			//validate roleSelector3		   
			var loginAs = $("#loginAs").val();
			$("#isAdmin").val(loginAs);
			validateUser();
			 
		});		
	});
	
</script>
<style scoped>
	a.link{
		text-decoration: none;
	}
</style>