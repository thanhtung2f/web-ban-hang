<%@include  file="template/header.jsp" %>
<div id="content-detail">
    <c:set var="productCategory" value="${ProductCate}"/>
    <c:set var="product" value="${ProductCate.product}"/>
    <c:set var="category" value="${ProductCate.category}"/>
    <c:if test="${empty ProductCate}">
        <c:redirect url="/home"><c:param name="req" value="home"/></c:redirect>
    </c:if>
    <div id="content-title">
        <a href="index.jsp">Home</a> >
        <a href="<c:url value="/home">
                <c:param name="req" value="category"/>
                <c:param name="typeCate" value="${category.categoryName}"/>
                </c:url>">${category.categoryName}</a> >
        Model: SP1
    </div>
    <form action="<c:url value="/Order"><c:param name="productID" value="${product.productID}"/></c:url>" method="post">
    <div id="product">
        <div id="product-name">
            <h2>${product.productName}</h2>
            <div id="product-detail">
                <div id="product-detail-left">
                    <div id="product-img">
                        <img src="img/1.jpg"/>
                    </div>
                    <div id="product-img-items">
                        <div><a href="#"><img src="img/1.jpg"/></a></div>
                        <div><a href="#"><img src="img/1.jpg"/></a></div>
                        <div><a href="#"><img src="img/1.jpg"/></a></div>
                        <div><a href="#"><img src="img/1.jpg"/></a></div>
                    </div>
                </div>
                <div id="product-detail-right">
                    <div id="product-detail-right-content">
                        <div id="product-price">$${product.unitPrice}</div>
                        <div id="product-status">In stock: ${product.unitsInStock}</div>
                        <div id="product-detail-buttons">
                            <c:if test="${empty adminAccount}">
                            <div id="product-detail-button">
                                <button type="submit" style="background-color: #be4d25; color:white;border: 1px solid gray; padding: 10px 0px" value="byNow" name="order">BY NOW</button>
                                <button type="submit" style="background-color: #fff; color:#be4d25;border: 1px solid gray; padding: 10px 0px" value="addToCart" name="order">ADD TO CART</button>
                            </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </form>
    <div id="info-detail">
        <div id="info-detail-title">
            <h2>Information deltail</h2>
            <div style="margin:10px auto;">
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Illum, debitis. Asperiores soluta eveniet eos accusantium doloremque cum suscipit ducimus enim at sapiente mollitia consequuntur dicta quaerat, sunt voluptates autem. Quam!
                Lorem ipsum dolor, sit amet consectetur adipisicing elit. Rem illum autem veritatis maxime corporis quod quibusdam nostrum eaque laborum numquam quos unde eveniet aut, exercitationem voluptatum veniam fugiat, debitis esse?
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Distinctio eligendi ratione vitae nobis numquam dolorum assumenda saepe enim cumque blanditiis, deleniti neque voluptate vel ducimus in omnis harum aut nisi.
            </div>
        </div>
    </div>
</div>
<%@include  file="template/footer.jsp" %>
