<%-- 
    Document   : IndexArticles1
    Created on : Oct 22, 2011, 10:45:44 AM
    Author     : ramsharan
--%>

<jsp:include page="header.jsp" />
<jsp:include page="leftMenu.jsp" />
<div class="right_content" />

<h4>Create index files for the articles</h4>

<form action="IndexArticles.jsp">
    The news articles will be indexed under suitable sub-categories. Then, those index files will be used to query the seed articles to form the groups of related articles. <br/><br/>
    <table>
        <tr><th>Specify the number of days to index the articles of: </th>
            <th><input class="grid_1" type="text" name="days" value="0" /></th></tr>
    </table>
    
    <br/>
    <input class="NFButton" onclick="return showMessage('Indexing Articles...')" type="submit" value="CLICK HERE TO CREATE THE INDEX FILES." />
</form><br/><br/>

<jsp:include page="footer.jsp" />