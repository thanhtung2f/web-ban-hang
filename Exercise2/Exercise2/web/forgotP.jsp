<%@include  file="template/header.jsp" %>
<div id="form">
    <h3 style="padding: 20px;">Forgot your account password?</h3>
    <div style="padding: 0px 20px 10px;">
        Please enter the email address registered with us to create a new password. We will send an email to the email address provided and require verification before we can generate a new password
    </div>
    <div id="form-content">
        <form action="<c:url value="/ForgotPass"><c:param name="req" value="forgotPassword"/></c:url>" method="post">
            <div style="text-align: left; margin: 5px 0px; color: #ff3333"><c:out value="${msg}"/></div>
            <label>Enter your registered email address<span style="color: red;">*</span></label><br/>
            <input type="text" name="txtForgotEmail"/><br/>
            <input type="submit" value="GET PASSWORD" style="margin-bottom: 30px;" name="txtForgotPassword"/><br/>
        </form>
    </div>
</div>
<%@include  file="template/footer.jsp" %>
