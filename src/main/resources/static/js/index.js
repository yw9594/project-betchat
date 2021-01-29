/* 메인 페이지 */
// 인덱스 페이지의 HTML 태그를 생성합니다.
function create_main_page(){
    console.log("create-main-page called.");

    // 이름을 입력받기 위한 input 태그를 생성합니다.
    var input_name_text = document.createElement("input");
    var input_name_submit = document.createElement("input");

    input_name_text.setAttribute("id", "input_name_text");
    input_name_text.setAttribute("type", "text");
    input_name_text.setAttribute("placeholder", "이름을 입력하세요.");

    input_name_submit.setAttribute("id", "input_name_submit");
    input_name_submit.setAttribute("type", "submit");
    input_name_submit.setAttribute("value", "입장!");

    // 생성된 input 태그를 DOM에 추가합니다.
    var div_content_container = document.getElementById("content-container");
    div_content_container.appendChild(input_name_text);
    div_content_container.appendChild(input_name_submit);
}

/* 웹 페이지 생성 후 첫 화면과 이벤트들을 등록합니다. */
window.onload = ()=>{
    console.log("window-onload called.");
    create_main_page();
}