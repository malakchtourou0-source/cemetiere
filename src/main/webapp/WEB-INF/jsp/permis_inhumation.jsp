<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>
<!DOCTYPE html>
<html lang="ar" dir="rtl">
<head>
  <meta charset="UTF-8">
  <title>رخصة دفن رقم ${permis.numPermis}</title>
  <style>
    * { box-sizing: border-box; margin: 0; padding: 0; }

    body {
      font-family: 'Segoe UI', Tahoma, Arial, sans-serif;
      background: white;
      padding: 30px;
      direction: rtl;
    }

    /* ── En-tête ── */
    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      border-bottom: 3px solid #0d2b52;
      padding-bottom: 16px;
      margin-bottom: 24px;
    }
    .header img  { width: 80px; height: 80px; object-fit: contain; }
    .header-center { text-align: center; }
    .header-center h1 { font-size: 18px; color: #0d2b52; font-weight: 700; }
    .header-center h2 { font-size: 15px; color: #333; margin-top: 6px; }
    .header-center p  { font-size: 12px; color: #666; margin-top: 4px; }

    /* ── Sections ── */
    .section {
      border: 1px solid #ccc;
      border-radius: 6px;
      padding: 14px 18px;
      margin-bottom: 16px;
    }
    .section-title {
      font-size: 14px;
      font-weight: 700;
      color: #0d2b52;
      margin-bottom: 12px;
      border-bottom: 1px solid #ddd;
      padding-bottom: 6px;
    }

    /* ── Lignes de données ── */
    .row {
      display: flex;
      gap: 24px;
      margin-bottom: 10px;
      flex-wrap: wrap;
    }
    .field {
      display: flex;
      gap: 8px;
      min-width: 200px;
      font-size: 13px;
      align-items: baseline;
    }
    .label {
      font-weight: 700;
      color: #333;
      white-space: nowrap;
    }
    .value {
      color: #111;
      border-bottom: 1px dotted #999;
      min-width: 120px;
      padding-bottom: 2px;
    }

    /* ── Pied de page signatures ── */
    .footer {
      display: flex;
      justify-content: space-around;
      margin-top: 50px;
    }
    .sign { text-align: center; font-size: 13px; }
    .sign-label { margin-bottom: 50px; font-weight: 600; color: #333; }
    .sign-line  { border-top: 1px solid #333; width: 160px; margin: 0 auto; }

    /* ── Impression auto ── */
    @media print {
      body { padding: 15px; }
      .no-print { display: none !important; }
    }
  </style>
</head>
<body onload="window.print()">

  <!-- Bouton fermer (caché à l'impression) -->
  <div class="no-print" style="text-align:left; margin-bottom:16px;">
    <button onclick="window.close()"
      style="padding:8px 16px; background:#e74c3c; color:white;
             border:none; border-radius:6px; cursor:pointer; font-size:13px;">
      ✕ إغلاق
    </button>
  </div>

  <!-- ── En-tête ── -->
  <div class="header">
    <img src="/logo_commune.png" alt="بلدية صفاقس">
    <div class="header-center">
      <h1>بلدية صفاقس — خدمة الحالة المدنية</h1>
      <h2>رخصة دفن رقم : ${permis.numPermis}</h2>
      <p>تاريخ التسليم : <fmt:formatDate value="${permis.dateDelivrance}" pattern="dd/MM/yyyy"/></p>
    </div>
    <img src="/logo_commune.png" alt="بلدية صفاقس">
  </div>

  <!-- ── Section 1 : Défunt ── -->
  <div class="section">
    <div class="section-title">بيانات المتوفي</div>
    <div class="row">
      <div class="field">
        <span class="label">الاسم واللقب :</span>
        <span class="value">${nomComplet}</span>
      </div>
      <div class="field">
        <span class="label">تاريخ الوفاة :</span>
        <span class="value">
          <c:choose>
            <c:when test="${cadavre.dateDeces != null}">
              <fmt:formatDate value="${cadavre.dateDeces}" pattern="dd/MM/yyyy"/>
            </c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
      <div class="field">
        <span class="label">ساعة الوفاة :</span>
        <span class="value">
          <c:choose>
            <c:when test="${permis.heureDeces != null}">${permis.heureDeces}</c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
    </div>
    <div class="row">
      <div class="field">
        <span class="label">مكان الوفاة :</span>
        <span class="value">
          <c:choose>
            <c:when test="${not empty permis.placeDeces}">${permis.placeDeces}</c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
      <div class="field">
        <span class="label">الجنسية :</span>
        <span class="value">
          <c:choose>
            <c:when test="${cadavre.nationalite == 'TUNISIEN'}">تونسي</c:when>
            <c:when test="${cadavre.nationalite == 'SUD_SAHARIEN'}">جنوب الصحراء</c:when>
            <c:when test="${cadavre.nationalite == 'ETRANGER'}">أجنبي</c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
    </div>
    <div class="row">
      <div class="field">
        <span class="label">اسم الأب :</span>
        <span class="value">
          <c:choose>
            <c:when test="${not empty permis.nomPere}">${permis.nomPere}</c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
      <div class="field">
        <span class="label">اسم الأم :</span>
        <span class="value">
          <c:choose>
            <c:when test="${not empty permis.nomMere}">${permis.nomMere}</c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
    </div>
  </div>

  <!-- ── Section 2 : Tombe ── -->
  <div class="section">
    <div class="section-title">معلومات القبر</div>
    <div class="row">
      <div class="field">
        <span class="label">المقبرة :</span>
        <span class="value">
          <c:choose>
            <c:when test="${permis.cimetiere != null}">${permis.cimetiere.nom}</c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
      <div class="field">
        <span class="label">رقم القبر :</span>
        <span class="value">
          <c:choose>
            <c:when test="${permis.element.tombe != null}">
              ${permis.element.tombe.numero}
            </c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
      <div class="field">
        <span class="label">الحجم :</span>
        <span class="value">
          <c:choose>
            <c:when test="${permis.element.tombe.taille == 'GRAND'}">كبير</c:when>
            <c:when test="${permis.element.tombe.taille == 'PETIT'}">صغير</c:when>
            <c:otherwise>—</c:otherwise>
          </c:choose>
        </span>
      </div>
    </div>
  </div>

  <!-- ── Section 3 : Quittance ── -->
  <c:if test="${quittance != null}">
    <div class="section">
      <div class="section-title">وصل الدفع</div>
      <div class="row">
        <div class="field">
          <span class="label">نوع المعلوم :</span>
          <span class="value">
            <c:choose>
              <c:when test="${quittance.motif == 'PERMIS_INHUMATION'}">رخصة دفن</c:when>
              <c:when test="${quittance.motif == 'TRANSPORT'}">نقل جثة</c:when>
              <c:when test="${quittance.motif == 'ENLEVEMENT'}">إزالة مشيمة</c:when>
              <c:when test="${quittance.motif == 'SUBVENTION_TERRE'}">إعانة قطعة أرض</c:when>
            </c:choose>
          </span>
        </div>
        <div class="field">
          <span class="label">رقم الوصل :</span>
          <span class="value">${quittance.numero}</span>
        </div>
        <div class="field">
          <span class="label">دفع بواسطة :</span>
          <span class="value">
            <c:choose>
              <c:when test="${not empty quittance.personnePayeur}">${quittance.personnePayeur}</c:when>
              <c:otherwise>—</c:otherwise>
            </c:choose>
          </span>
        </div>
      </div>
    </div>
  </c:if>

  <!-- ── Signatures ── -->
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
