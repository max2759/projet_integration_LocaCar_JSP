<%@ page pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>

<div class="container-fluid">
    <h1>Ajouter une annonce</h1>

    <form method="post" action="ajouter-annonce" enctype="multipart/form-data">
        <div class="form-group">
            <label>Titre de l'annonce</label>
            <input type="text" class="form-control col-sm-4" id="labelAd" name="labelAd"
                   placeholder="Titre de l'annonce"
                   required>
        </div>
        <div class="form-group">
            <label>Type d'annonce</label>
            <select id="adType" name="adType" class="form-control col-sm-3" required>
                <c:forEach var="enumTypesAds" items="${enumTypesAds}">

                    <c:choose>
                        <c:when test="${enumTypesAds eq 'LOCATIONCD'}">
                            <option value="${enumTypesAds}">Location</option>
                        </c:when>
                        <c:when test="${enumTypesAds eq 'VENTE'}">
                            <option value="${enumTypesAds}">Vente</option>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Prix fixe ou prix par jour</label>
            <input type="number" class="form-control col-sm-4" min="100" step="any" name="adPrice" id="adPrice"
                   placeholder="1000€"
                   required="required">
        </div>
        <div class="form-group">
            <label>Couleur</label>
            <input type="text" class="form-control col-sm-4" id="color" name="color" placeholder="Couleur du véhicule"
                   required>
        </div>
        <div class="form-group">
            <label>Année de fabrication</label>
            <input type="date" class="form-control col-sm-4" id="ReleaseYear" name="ReleaseYear"
                   required>
        </div>
        <div class="form-group">
            <label>Kilométrage</label>
            <input type="number" class="form-control col-sm-4" min="100" step="any" id="kilometer" name="kilometer"
                   placeholder="230000km"
                   required>
        </div>
        <div class="form-group">
            <label>Chevaux</label>
            <input type="number" class="form-control col-sm-4" step="any" id="horsePower" name="horsePower"
                   placeholder="150 CV"
                   required>
        </div>

        <div class="form-group">
            <label>Type de voiture</label>
            <select id="carTypes" name="carTypes" class="form-control col-sm-2">
                <c:forEach var="categoryType" items="${category}">
                    <option value="${categoryType.id}">${categoryType.label}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Modèle</label>
            <select id="models" name="models" class="form-control col-sm-2">
                <c:forEach var="brands" items="${brands}">
                    <optgroup label="${brands.label}">
                        <c:forEach var="models" items="${models}">
                            <c:if test="${brands.id == models.brandsByIdBrands.id}">
                                <option value="${models.id}">${models.label}</option>
                            </c:if>
                        </c:forEach>
                    </optgroup>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Carburant</label>
            <select id="fuel" name="fuel" class="form-control col-sm-2">
                <c:forEach var="enumFuel" items="${enumFuel}">
                    <option value="${enumFuel}">${enumFuel}</option>
                </c:forEach>
            </select>
        </div>

        <!--<div class="form-group">
            <label>Photo</label>
            <input type="file" name="fileToUpload" id="fileToUpload" class="form-control">
        </div>-->

        <input name="idUser" id="idUser" type="hidden" value="${sessionScope.User}"/>

        <button type="submit" class="btn btn-warning">Ajouter</button>
    </form>
</div>

<jsp:include page="footer.jsp"/>