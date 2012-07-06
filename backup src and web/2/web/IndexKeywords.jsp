<%-- 
    Document   : IndexKeywords
    Created on : Oct 19, 2011, 8:40:11 AM
    Author     : robik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="header.jsp" />

        <h1>Index Keywords</h1>
        <jsp:useBean class="TextAnalysis.SubCategorizer" id="sc" />
        <% sc.createIndexOfKeywords("National"); %>
        <a href="index.jsp">Return To The Home Page</a>
                  
    </body>
</html>
