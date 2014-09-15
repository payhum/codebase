<%@include file="../common/jspHeader.jsp"%>


<ul id="mbmcpebul_table" class="mbmcpebul_menulist css_menu"
	style="width: 100%; height: 27px;">
		<div style="float:right;padding-right: 15px;">
			<a id="launchSettingsWindow">
			<input type="button" value="Settings"/></a>
		</div>
	
	
	<li class="topitem"><div
			class="buttonbg gradient_button gradient27" style="width: 73px;">
			<a href="<%=request.getContextPath() + "/do/Payroll"%>">
					Payroll</a>
		</div></li>
		<li class="topitem"><div
			class="buttonbg gradient_button gradient27" style="width: 135px;">
			<a href="<%=request.getContextPath() + "/do/YearEndTaxReport"%>">
					Year End Tax Report</a>
		</div></li>
	<!-- li class="topitem"><div
			class="buttonbg gradient_button gradient27" style="width: 73px;">
			<a href="<%=request.getContextPath() + "/do/Report"%>">
					Report</a>
		</div></li-->		
	
</ul>