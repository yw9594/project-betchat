/* 네트워크 */
// 서버 주소 관련 변수입니다.
const host_address = "http://localhost:8080";  // 서버 주소를 정의합니다.
const ws_url = "/ws";
const chat_pub_url = "/pub/chat/";
const chat_sub_url = "/sub/chat/";

// 요청/응답의 상태를 전달하기 위한 enum입니다.
const result_state = Object.freeze({"OK":"OK", "ERROR":"ERROR"});

// JSON으로 전송하기 위한 XMLHttpRequest 객체를 생성합니다.
function makeXHRObj(url){
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url);
    xhr.setRequestHeader("Content-Type", "application/json");
    return xhr;
}
// 서버와 통신하기 위한 JSON 형식을 정의합니다.
function makeXHRJsonBody(result_state, data){
    return {
        "result_state":result_state,
        "transaction_time": getNowIsoTime(),
        "data": data
    }
};