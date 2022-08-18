let myList = document.querySelector(".myList");
let toggle_line = document.querySelector(".toggle-line");
let user = document.querySelector(".user");

// 마이 메뉴 토글

user.addEventListener("click", function() {
    if(!myList.classList.contains("toggle")) {
        myList.classList.remove("toggleOut");
        myList.classList.remove("display");
        myList.classList.add("toggle");
    } else {
        myList.classList.remove("toggle");
        myList.classList.add("toggleOut")
    }

});