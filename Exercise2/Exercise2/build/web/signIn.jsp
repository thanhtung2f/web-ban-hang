<%@include  file="template/header.jsp" %>
<c:if test="${not empty AccSession}">
    <c:redirect url="/home"><c:param name="req" value="home"/></c:redirect> 
</c:if>
<c:if test="${empty AccSession && empty check}">
    <c:redirect url="/SignIn"></c:redirect> 
</c:if>
<div id="form">
    <div id="form-title">
        <span><a href="<%=request.getContextPath()%>/SignUp">SIGN UP</a></span>
        <span><a href="<%=request.getContextPath()%>/SignIn" style="color: red;">SIGN IN</a></span>
    </div>
    <div id="form-content">
        <form action="<c:url value="/SignIn"/>" method="post">
            <c:if test="${not empty msgSuccess}">
                <div style="text-align: center; color: red;"><strong><c:out value="${msgSuccess}"></c:out> </strong></div>
            </c:if>
            <label>Email<span style="color: red;">*</span></label><br/>
            <input type="text" name="txtEmail" value="${Email}"/><br/>
            <c:if test="${not empty msgEmail}">
                <span class="msg-error"><c:out value="${msgEmail}"/></span><br/>
            </c:if>
            <label>Password<span style="color: red;">*</span></label><br/>
            <input type="password" name="txtPass" s/><br/>
            <c:if test="${not empty msgPass}">
                <span class="msg-error"><c:out value="${msgPass}"/></span><br/>
            </c:if>
            <div class="msg" style="color:red; text-align: center; padding: 5px 0px;">
                <c:if test="${empty msgPass && empty msgEmail && not empty msg}">
                    <c:out value="${msg}"/>
                </c:if>
            </div>
                <div><a href="<c:url value="/ForgotPass"><c:param name="req" value="goToForgotPassword"/></c:url>">Forgot password?</a></div>
            <div style="color:red">
            </div>
            <input type="submit" value="SIGN IN"/><br/>
            <input type="button" value="FACEBOOK LOGIN" style="background-color: #3b5998;"/><br/>
            <input type="button" value="ZALO LOGIN" style="background-color: #009dff;margin-bottom: 30px;"/>
        </form>
    </div>
</div>
<%@include  file="template/footer.jsp" %>
