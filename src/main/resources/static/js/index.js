/* 메인 페이지 */
// 인덱스 페이지의 HTML 태그를 생성합니다.
function createMainPage(){
    console.log("create-main-page called.");

    // 이름을 입력받기 위한 form 태그를 생성합니다.
    var form_name_input = document.createElement("form");
    var input_name_text = document.createElement("input");
    var input_name_submit = document.createElement("input");

    form_name_input.setAttribute("id", "form_name_input");

    input_name_text.setAttribute("id", "input_name_text");
    input_name_text.setAttribute("type", "text");
    input_name_text.setAttribute("placeholder", "이름을 입력하세요.");

    input_name_submit.setAttribute("id", "input_name_submit");
    input_name_submit.setAttribute("type", "submit");
    input_name_submit.setAttribute("value", "입장!");

    // 생성된 form 태그를 페이지에 추가합니다.
    form_name_input.appendChild(input_name_text);
    form_name_input.appendChild(input_name_submit);

    var div_content_container = document.getElementById("content-container");
    div_content_container.appendChild(form_name_input);

    // 이름을 서버로 전송하는 이벤트 리스너를 정의합니다.
    // var listner_
    // form_name_input.addEventListener("submit", ,true);

}
/* 메인 페이지 초기화 */
window.onload = ()=>{
    console.log("window-onload called.");
    createMainPage();
}