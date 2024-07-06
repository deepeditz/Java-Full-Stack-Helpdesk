<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hospital-SupportDesk-Solutions</title>
</head>
<body>
<%@ include file="header.jsp" %>
<!-- Background image -->
  <div
          class="p-5 text-center bg-image"
          style= "
          background: url('./image/bg-login.jpg');
          background-repeat: no-repeat;
          height: 738px; 
       " id="backgroundChangeEffect"
  >
    <div class="mask" style="background-color: rgba(0, 0, 0, 0.6)">
      <div class="d-flex justify-content-center align-items-center h-100">
        <div class="text-white">
          <h1 class="mb-3">Welcome To <span id="dynamicText"></span></h1>
        </div>
      </div>
    </div>
  </div>
  <!-- Background image -->

<%@ include file="footer.jsp" %>

<script>

<!-- Background image - Dynamic Effect -->
const images = [
	'./image/bg-login.jpg',
	'./image/bg2.jpg',
	'./image/bg3.jpg'
];

let currentImage = 0;

function changeBackground() {
	const bgEffect = document.getElementById("backgroundChangeEffect");
	const image = images[currentImage];
	bgEffect.style.backgroundImage = `url(${image})`;
	currentImage++;
	if (currentImage >= images.length) {
		currentImage = 0;
	}
}

setInterval(changeBackground, 5000);

<!-- Background image - Dynamic Effect -->

<!-- Dynamic text Effect -->

const dynamicText = document.getElementById("dynamicText");
let requiredString = "Hospital-SupportDesk-Solutions";

let indexNo = 0;
function fxn1() {
    dynamicText.innerText += requiredString.charAt(indexNo);
    indexNo++;
    fxn2();
}

function fxn2() {
    if (dynamicText.innerText == requiredString) {
        setTimeout(() => {
            dynamicText.innerText = "";
            indexNo = 0;
            fxn1();
        }, 4000);
    } else {
        setTimeout(fxn1, 100);
    }
}
setTimeout(fxn1, 3000);

<!-- Dynamic text Effect -->

</script>
</body>
</html>