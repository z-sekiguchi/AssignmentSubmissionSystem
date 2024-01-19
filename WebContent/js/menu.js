document.addEventListener("DOMContentLoaded", function() {
	var toggleBtn = document.getElementById("toggleBtn");
	var usuMenu = document.getElementsByClassName("usuMenu");
	var redMenu = document.getElementsByClassName("redMenu");
	var columns = document.getElementsByClassName("columns");

	toggleBtn.addEventListener("click", function() {
		var state = document.getElementById("menuStatus");
		if(state.checked) {
			state.checked = false;
			for(var i = 0; i < usuMenu.length; i++) {
				usuMenu[i].style.display = "none";
			}
			for(var i = 0; i < redMenu.length; i++) {
				redMenu[i].style.display = "";
			}
			columns[0].style.gridTemplateColumns = "1fr calc(100vw - 50px)";
		} else {
			state.checked = true;
			for(var i = 0; i < usuMenu.length; i++) {
				usuMenu[i].style.display = "";
			}
			for(var i = 0; i < redMenu.length; i++) {
				redMenu[i].style.display = "none";
			}
			columns[0].style.gridTemplateColumns = "1fr calc(100vw - 200px)";
		}
	});
});