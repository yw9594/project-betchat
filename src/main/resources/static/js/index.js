/* 서버 정보 */
// 서버 주소를 정의합니다.
const host_address = "http://localhost:8080/";

/* 네트워크 */
// 서버와 통신하기 위한 JSON 형식을 정의합니다.
function makeJsonXHRBody(data){
    return {
        "transaction_time": getNowIsoTime(),
        "data": data
    }
};

// JSON으로 전송하기 위한 XMLHttpRequest 객체를 생성합니다.
function makeJsonXHRObj(url){
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url);
    xhr.setRequestHeader("Content-Type", "application/json");
    return xhr;
}

/* 유틸리티 */
// 메시지 전송을 위한 현재 시간을 반환합니다.
function getNowIsoTime() {
    var now = new Date();
    return now.toISOString();
}

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

    // 유저 이름을 전달받아 서버에 전송하는 이벤트 리스너를 정의 및 등록합니다.
    var getNameEventListener = function (event){
        // Ajax를 사용해 서버에 이름을 전달한다.
        var user_name = input_name_text.value;

        var xhr = makeJsonXHRObj(host_address+"/home/submit");
        var data = makeJsonXHRBody({"user_name":user_name});

        xhr.onreadystatechange = function(){
            if(xhr.readyState===4 && xhr.status===200)
                console.log(xhr.response);
        }
        xhr.send(JSON.stringify(data));

        // form 태그의 디폴트 이벤트 리스너를 취소합니다.
        event.preventDefault();
    }

    // 이름 전송 이벤트 리스너를 등록합니다.
    form_name_input.addEventListener("submit", getNameEventListener,true);
}

/* 메인 페이지 초기화 */
window.onload = ()=>{
    console.log("window-onload called.");
    createMainPage();
}