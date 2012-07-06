<%-- 
    Document   : CollectArticles
    Created on : Oct 16, 2011, 8:21:57 AM
    Author     : robik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="header.jsp" />
<jsp:useBean class="ArticleCollection.Controller" id="Controller" />
<jsp:useBean class="DbInformation.HTMLConverter" id="Informer" />
 <a href="CollectArticles.jsp"><input type="button" onclick="return showMessage('Collecting Articles.....')" class="blueButton" value="Click on this button to collect articles" /></a><br/><br/>
        <% 
                out.println("<table class=\"box\">");
                out.println(Informer.getActiveChannelTable()+"<br/>");
                out.println("<table class=\"box\">");
                out.println(Informer.getActiveRssTable()+"<br/>");
                out.println("</table>");
        %>
        <br>
        <h6>If you want to <font color="red"><b>ADD, DELETE, ACTIVATE or DE-ACTIVATE</b></font> any channel or link, please do so using <a href="http://localhost/phpmyadmin" target="0" >phpmyadmin</a>.
            
          <br/>
          <h6>For more information on how to use phpmyadmin, please refer to the link: <a href="Help.jsp">This link should directly take us to the part where there is tutorial about link/channel using phpmyadmin</a></h6>
       <br/><br/>
            <a href="CollectArticles.jsp"><input type="button" onclick="return showMessage('Collecting Articles.....')" class="blueButton" value="Click on this button to collect articles" /></a><br/><br/>
<a href="index.jsp">Return To The Home Page</a>
<br/><br/><br/><br/><br/><br/>

<%--    which which channel
        which which category
        which link is not working notify
        which which sub category, for certain channels,
        
--%>
<jsp:include page="rightColumn.jsp" />
<jsp:include page="footer.jsp" />