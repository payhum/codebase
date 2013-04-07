<%@include file="../common/jspHeader.jsp"%>
<div class="master_form">

	<div  style="width: 800px;">
			<fieldset>
				<legend>Upload Client Company File</legend>
		
		<div>
			<form method="post" action="<%=request.getContextPath() + "/do/UploadFile"%>" enctype="multipart/form-data">
			Upload the Payroll file for the current month from a client company here. <br>Select the file to be uploaded and click on <b>Upload</b> button.<br><br>
			    Select Client Company file to upload: <input type="file" name="uploadFile" />
	            <br/><br/> 
	            <input type="submit" value="Upload" />
	        </form>
		</div>
		</fieldset>
	</div>

	<div  style="width: 800px;">
			<fieldset>
				<legend>Generate Files for Government and Bank</legend>
			Select the Client Company from the list here and click on the below buttons to generate the files to be sent to <b>Bank</b> and <b>Government</b>.
		<br><br>
		<div>
			<a href="<%=request.getContextPath() + "/do/GenerateBankFile"%>">
				<input type="button" value="Generate Bank File"/>
			</a> to be sent to Bank for crediting the Salary to Employees and Tax to Government.<br>
			<br><br>
		</div>
		
		<div>
			<a href="<%=request.getContextPath() + "/do/GenerateGovtFile"%>">
				<input type="button" value="Generate Government File"/>
			</a>
			 to be sent to Government to inform the Tax being credited on behalf of the Employees of the Company.
			 <br>
		</div>
		
</fieldset>
</div>			
</div>