<%-- 
    Document   : index
    Created on : Oct 14, 2011, 7:46:17 AM
    Author     : robik
--%>

<jsp:include page="header.jsp" />
<jsp:include page="leftMenu.jsp" />
<div class="right_content" />
<h2>WELCOME</h2>
<h4> Our minor project is entitled "Smart News Aggregation". This web application collects 
    news articles from various Nepali news sites. It places those articles 
    under suitable categories. Then, it groups the related articles together and 
    presents them.
</h4>


<form action="defaultRun.jsp">
    <input class="NFButton" type="submit" value="CLICK HERE TO RUN THE ENTIRE APPLICATION WITH DEFAULT SETTINGS" />
</form>
<br/><br/>
<form action="IndexKeywords.jsp">
    <input class="NFButton" type="submit" value="CLICK HERE TO INDEX KEYWORDS">
    <br/>
    <font color="blue">
    (YOU NEED TO RUN THIS ONLY WHEN THE WEB APPLICATION IS INSTALLED OR WHENEVER THE KEYWORDS ARE UPDATED)
    </font>
</form>
<jsp:include page="footer.jsp" />
