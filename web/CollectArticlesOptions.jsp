<%-- 
    Document   : CollectArticles
    Created on : Oct 16, 2011, 8:21:57 AM
    Author     : robik
--%>

<jsp:include page="header.jsp" />
<jsp:include page="leftMenu.jsp" />
<div class="right_content" />
<jsp:useBean class="ArticleCollection.Controller" id="Controller" />
<jsp:useBean class="DbInformation.HTMLConverter" id="Informer" />

<form action="CollectArticles.jsp">
    <input type="submit" class="NFButton" onclick="return showMessage('Collecting Articles..... (Please Wait)')" value="CLICK HERE TO COLLECT THE NEWS ARTICLES" />
</form>

        <% 
                out.println("<table>");
                out.println(Informer.getActiveChannelTable()+"<br/>");
                out.println("<table>");
                out.println(Informer.getActiveRssTable()+"<br/>");
                out.println("</table>");
        %>
        <br>
        <h4>If you want to <font color="red"><b>ADD, DELETE, ACTIVATE or DE-ACTIVATE</b></font> any channel or link, please do so using <a href="http://localhost/phpmyadmin" target="0" >phpmyadmin</a>.
            
          <br/><br/>
            <form action="CollectArticles.jsp">
    <input type="submit" class="NFButton" onclick="return showMessage('Collecting Articles..... (Please Wait)')" value="CLICK HERE TO COLLECT THE NEWS ARTICLES" />
</form>
<%--    which which channel
        which which category
        which link is not working notify
        which which sub category, for certain channels,
        
--%>
<jsp:include page="footer.jsp" />