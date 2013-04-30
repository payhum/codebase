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
		    <form method="post" action="<%=request.getContextPath() + "/do/GenerateBankFile"%>" enctype="multipart/form-data">
		    	<input type="submit" value="Generate Bank File"/> to be sent to Bank for crediting the Salary to Employees.<br>
	        </form> <br>
		</div>
		
		<div>
			<form method="post" action="<%=request.getContextPath() + "/do/GenerateGovtFile"%>" enctype="multipart/form-data">
		    	<input type="submit" value="Generate Tax File"/> to be sent to IRD office to inform the Tax being credited on behalf of the Employees of the Company.
	        </form> <br>
		</div>

		<div>
			<form method="post" action="<%=request.getContextPath() + "/do/GenerateSSGovtFile"%>" enctype="multipart/form-data">
		    	<input type="submit" value="Generate Social Security File"/> to be sent to Social Security office to inform the Social Security amount being credited on behalf of the Employees of the Company.
	        </form> <br>
		</div>
		
</fieldset>
</div>			
</div>