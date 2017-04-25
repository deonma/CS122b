<HTML>
<HEAD> <TITLE> JSP Calling Servlet Demo </TITLE> </HEAD>
<BODY>
<!-- Forward processing to a servlet -->
<% request.setAttribute("empid", '1234"); %>
<jsp:include page="/servlet/MyServlet?user=Smith" flush="trye"/>
</BODY>
</HTML>
