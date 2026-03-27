<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>
<!DOCTYPE html>
<html lang="ar" dir="rtl">
<head>
  <meta charset="UTF-8">
  <title>أمر مهمة رقم ${ordre.numero}</title>
  <style>
    * { box-sizing: border-box; margin: 0; padding: 0; }
    body {
      font-family: 'Segoe UI', Tahoma, Arial, sans-serif;
      background: white; padding: 30px; direction: rtl;
    }
    .header {
      display: flex; justify-content: space-between; align-items: center;
      border-bottom: 3px solid #0d2b52; padding-bottom: 16px; margin-bottom: 24px;
    }
    .header img { width: 80px; height: 80px; object-fit: contain; }
    .header-center { text-align: center; }
    .header-center h1 { font-size: 18px; color: #0d2b52; font-weight: 700; }
    .header-center h2 { font-size: 15px; color: #333; margin-top: 6px; }

    .section { border: 1px solid #ccc; border-radius: 6px; padding: 14px 18px; margin-bottom: 16px; }
    .section-title {
      font-size: 14px; font-weight: 700; color: #0d2b52;
      margin-bottom: 12px; border-bottom: 1px solid #ddd; padding-bottom: 6px;
    }
    .row  { display: flex; gap: 24px; margin-bottom: 10px; flex-wrap: wrap; }
    .field { display: flex; gap: 8px; min-width: 200px; font-size: 13px; align-items: baseline; }
    .label { font-weight: 700; color: #333; white-space: nowrap; }
    .value { color: #111; border-bottom: 1px dotted #999; min-width: 120px; padding-bottom: 2px; }

    /* Tableau membres */
    table { width: 100%; border-collapse: collapse; font-size: 13px; margin-top: 8px; }
    thead tr { background: #d6e4f0; }
    thead th { padding: 8px 12px; font-weight: 600; color: #0d2b52; text-align: right; border: 1px solid #aac4dc; }
    tbody td { padding: 8px 12px; border: 1px solid #dde3ec; }
    tbody tr:nth-child(even) { background: #f8fafc; }

    .footer { display: flex; justify-content: space-around; margin-top: 50px; }
    .sign   { text-align: center; font-size: 13px; }
    .sign-label { margin-bottom: 50px; font-weight: 600; color: #333; }
    .sign-line  { border-top: 1px solid #333; width: 160px; margin: 0 auto; }

    @media print {
      body { padding: 15px; }
      .no-print { display: none !important; }
    }
  </style>
</head>
<body onload="window.print()">

  <div class="no-print" style="text-align:left; margin-bottom:16px;">
    <button onclick="window.close()"
      style="padding:8px 16px; background:#e74c3c; color:white;
             border:none; border-radius:6px; cursor:pointer; font-size:13px;">
      ✕ إغلاق
    </button>
  </div>

  <!-- En-tête -->
  <div class="header">
    <img src="/logo_commune.png" alt="بلدية صفاقس">
    <div class="header-center">
      <h1>بلدية صفاقس — خدمة الحالة المدنية</h1>
      <h2>أمر مهمة رقم : ${ordre.numero}</h2>
    </div>
    <img src="/logo_commune.png" alt="بلدية صفاقس">
  </div>

  <!-- Section 1 : Infos mission -->
  <div class="section">
    <div class="section-title">معلومات المهمة</div>
    <div class="row">
      <div class="field">
        <span class="label">تاريخ الانطلاق :</span>
        <span class="value">
          <c:choose>
            <c:when test="${ordre.dateDepart != null}">
              <fmt:formatDate value="${ordre.dateDepart}" pattern="dd/MM/yyyy"/>
            </c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
      <div class="field">
        <span class="label">ساعة الانطلاق :</span>
        <span class="value">
          <c:choose>
            <c:when test="${ordre.heureDepart != null}">${ordre.heureDepart}</c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
      <div class="field">
        <span class="label">رقم العربة :</span>
        <span class="value">
          <c:choose>
            <c:when test="${not empty ordre.vehicule}">${ordre.vehicule}</c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
    </div>
    <div class="row">
      <div class="field">
        <span class="label">مكان الانطلاق :</span>
        <span class="value">
          <c:choose>
            <c:when test="${not empty ordre.lieuDepart}">${ordre.lieuDepart}</c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
      <div class="field">
        <span class="label">مكان الوصول :</span>
        <span class="value">
          <c:choose>
            <c:when test="${not empty ordre.lieuArrivee}">${ordre.lieuArrivee}</c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
    </div>
    <div class="row">
      <div class="field">
        <span class="label">تاريخ العودة :</span>
        <span class="value">
          <c:choose>
            <c:when test="${ordre.dateRetour != null}">
              <fmt:formatDate value="${ordre.dateRetour}" pattern="dd/MM/yyyy"/>
            </c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
      <div class="field">
        <span class="label">ساعة العودة :</span>
        <span class="value">
          <c:choose>
            <c:when test="${ordre.heureRetour != null}">${ordre.heureRetour}</c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
    </div>
  </div>

  <!-- Section 2 : Demande liée -->
  <div class="section">
    <div class="section-title">طلب النقل المرتبط</div>
    <div class="row">
      <div class="field">
        <span class="label">رقم الطلب :</span>
        <span class="value">${demande.id}</span>
      </div>
      <div class="field">
        <span class="label">التاريخ :</span>
        <span class="value">
          <fmt:formatDate value="${demande.date}" pattern="dd/MM/yyyy"/>
        </span>
      </div>
      <div class="field">
        <span class="label">مكان الاسترداد :</span>
        <span class="value">${demande.lieuRecuperation}</span>
      </div>
    </div>
    <div class="row">
      <div class="field">
        <span class="label">قوات الأمن :</span>
        <span class="value">
          <c:choose>
            <c:when test="${demande.forceSecurite != null}">${demande.forceSecurite.nomUnite}</c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
      <div class="field">
        <span class="label">المنطقة :</span>
        <span class="value">
          <c:choose>
            <c:when test="${demande.zone != null}">${demande.zone.nom}</c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
    </div>
  </div>

  <!-- Section 3 : Membres équipe -->
  <c:if test="${membres != null and not empty membres}">
    <div class="section">
      <div class="section-title">أعضاء الفريق</div>
      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>الاسم واللقب</th>
            <th>رقم بطاقة الهوية</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="m" items="${membres}" varStatus="s">
            <tr>
              <td>${s.index + 1}</td>
              <td>${m.nom}</td>
              <td>
                <c:choose>
                  <c:when test="${not empty m.cin}">${m.cin}</c:when>
                  <c:otherwise>—</c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </c:if>

  <!-- Signatures -->
  <div class="footer">
    <div class="sign">
      <p class="sign-label">إمضاء العون</p>
      <div class="sign-line"></div>
    </div>
    <div class="sign">
      <p class="sign-label">ختم البلدية</p>
      <div class="sign-line"></div>
    </div>
  </div>

</body>
</html>
