<%-- 
    Document   : IndexArticles1
    Created on : Oct 22, 2011, 10:45:44 AM
    Author     : ramsharan
--%>

<jsp:include page="header.jsp" />

<h5>Create index files for the articles</h5>

<form action="IndexArticles.jsp">
    These articles will be indexed under suitable sub-categories. Then, those index files will be used to query the seed articles to form the groups of related articles. For more detailed description please refer to <a href="Help.jsp" >Help</a>.<br/><br/><hr/>
    <table>
        <tr><th>Specify the number of days to index the articles of: </th>
        <th><input class="grid_1" type="text" name="days" value="0" /></th></tr>
        <tr><th colspan="2"><input class="blueButton" onclick="return showMessage('Indexing Articles...')" type="submit" value="Click on this button to create the index files." /></th></tr>
    </table>

</form><br/><br/><hr/>
<a href="index.jsp">Return To The Home Page</a>


<jsp:include page="rightColumn.jsp" />
<jsp:include page="footer.jsp" />