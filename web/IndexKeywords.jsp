<%-- 
    Document   : IndexKeywords
    Created on : Oct 19, 2011, 8:40:11 AM
    Author     : robik
--%>

<jsp:include page="header.jsp" />
<jsp:include page="leftMenu.jsp" />
<div class="right_content" />

        <h1>Index Keywords</h1>
        <jsp:useBean class="TextAnalysis.SubCategorizer" id="sc" />
        <% sc.createIndexOfKeywords("National"); %>
        <a href="index.jsp">Return To The Home Page</a>
                  
    </body>
</html>
