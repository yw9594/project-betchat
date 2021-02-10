/* 서버 정보 */
const host_address = "http://localhost:8080";  // 서버 주소를 정의합니다.
const ws_url = "/ws";
const chat_pub_url = "/pub/chat/";
const chat_sub_url = "/sub/chat/";

/* 페이지 전역 변수 */
let div_content_container;                      // 페이지의 내용을 표현하는 HTML Element입니다.

/* 네트워크 */
// JSON으로 전송하기 위한 XMLHttpRequest 객체를 생성합니다.
function makeXHRObj(url){
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url);
    xhr.setRequestHeader("Content-Type", "application/json");
    return xhr;
}
// 서버와 통신하기 위한 JSON 형식을 정의합니다.
function makeXHRJsonBody(messageStatus, data){
    return {
        "messageStatus":messageStatus,
        "transaction_time": getNowIsoTime(),
        "data": data
    }
};
// 요청/응답의 상태를 전달하기 위한 enum입니다.
const messageStatus = Object.freeze({"OK":"OK", "ERROR":"ERROR"});

/* 유틸리티 */
// 메시지 전송을 위한 현재 시간을 반환합니다.
function getNowIsoTime() {
    var now = new Date();
    return now.toISOString();
}
// HTML Element를 생성합니다.
function makeHTMLElement(tag_name, attributes) {
    var element = document.createElement(tag_name);
    for(const key in attributes)
        element.setAttribute(key, attributes[key]);

    return element;
}
// parent HTML Element에 child를 추가합니다.
function addDOMElement(parent, child) {
    for(const k in child)
        parent.appendChild(child[k]);
}
// HTMLElement 내 모든 내용을 삭제합니다.
function clearHTMLElement(element){
    element.innerHTML = "";
}

/* 메인 페이지 */
// 메인 페이지의 HTML 태그를 생성합니다.
function createMainPage(info_box){
    console.log("createMainPage : createMainPage called.");
    clearHTMLElement(div_content_container);

    // 이름을 입력받기 위한 태그를 생성합니다.
    var form_name_input =  makeHTMLElement("form", {"id":"form_name_input"});
    var input_name_text =  makeHTMLElement("input", {"id":"input_name_text", "type":"text", "placeholder":"이름을 입력하세요."});
    var input_name_submit = makeHTMLElement("input", {"id":"input_name_submit", "type":"submit", "value":"입장!"});
    var header_name_input = makeHTMLElement("h2");

    var text_name_input  = document.createTextNode("이름 입력");

    // 생성된 form 태그를 페이지에 추가합니다.
    addDOMElement(header_name_input, [text_name_input]);
    addDOMElement(form_name_input, [input_name_text, input_name_submit]);

    // 페이지에 구성 요소를 추가합니다.
    addDOMElement(div_content_container, [header_name_input, form_name_input]);

    // 유저 이름을 전달받아 서버에 전송하는 이벤트 리스너를 정의 및 등록합니다.
    var getNameEventListener = function (event){
        // Ajax를 사용해 서버에 이름을 전달합니다.
        var user_name = input_name_text.value;

        var xhr = makeXHRObj(host_address+"/home/submit");
        var data = makeXHRJsonBody(messageStatus.OK, {"user_name":user_name});

        // 응답을 받은 경우, 로비를 보여줍니다.
        xhr.onreadystatechange = function(){
            if(xhr.readyState===4 && xhr.status===200){
                var response = JSON.parse(xhr.response);
                info_box["user_name"] = user_name;
                info_box["user_key"] = response.data.user_key;

                createLobbyPage(info_box);
            }
        }
        xhr.send(JSON.stringify(data));

        // form 태그의 디폴트 이벤트 리스너를 취소합니다.
        event.preventDefault();
    }

    // 이름 전송 이벤트 리스너를 등록합니다.
    form_name_input.addEventListener("submit", getNameEventListener,true);
}


// 로비 페이지를 생성합니다.
function createLobbyPage(info_box){
    console.log("createLobbyPage : createLobbyPage called.");
    clearHTMLElement(div_content_container);

    // 채팅방을 생성하기 위한 태그를 생성합니다.
    var form_room_create =  makeHTMLElement("form", {"id":"form_room_create"});
    var header_room_create = makeHTMLElement("h2");
    var input_room_create_submit = makeHTMLElement("input", {"id":"input_room_create_submit", "type":"submit", "value":"생성!"});

    var text_room_create = document.createTextNode("채팅방 생성");

    // 생성된 form 태그를 페이지에 추가합니다.
    addDOMElement(form_room_create, [input_room_create_submit]);
    addDOMElement(header_room_create, [text_room_create]);
    addDOMElement(div_content_container, [header_room_create, form_room_create]);

    // 채팅방 생성 요청을 처리하는 이벤트 리스너를 정의 및 등록합니다.
    var createLobbyEventListener = function (event){
        // Ajax를 사용해 서버에 이름을 전달합니다
        var xhr = makeXHRObj(host_address +"/lobby/create");
        var data = makeXHRJsonBody(messageStatus.OK, {"user_key":info_box.user_key});

        // 응답을 받은 경우, 로비를 보여줍니다.
        xhr.onreadystatechange = function(){
            if(xhr.readyState===4 && xhr.status===200){
                var response = JSON.parse(xhr.response);
                info_box["room_key"]=response.data.room_key;

                onConnectRoomPage(info_box);
            }
        }
        xhr.send(JSON.stringify(data));

        // form 태그의 디폴트 이벤트 리스너를 취소합니다.
        event.preventDefault();
    }

    // 채팅방 생성 이벤트 리스너를 등록합니다.
    form_room_create.addEventListener("submit", createLobbyEventListener,true);
}

// 채팅방 로딩 중간 과정을 처리합니다.
function onConnectRoomPage(info_box){
    console.log("onConnectRoomPage : onConnectRoomPage called.");
    clearHTMLElement(div_content_container);

    // stomp handshaking을 수행합니다.
    var socket = new SockJS(ws_url);
    var stompClient = Stomp.over(socket);

    stompClient.connect({},
        // connection 성공 시 채팅방 페이지로 이동합니다.
        ()=>{
            console.log("onConnectRoomPage.stompClient.connect : connection success.");

            // room_key를 기반으로 채팅방을 구독합니다.
            stompClient.subscribe(chat_sub_url + info_box.room_key,
                (message)=>{
                    console.log("onConnectRoomPage.stompClient.subscribe : subscribe called.");

                    console.log(message);


                    createRoomPage(info_box, stompClient);
                });
        },
        // connection 실패 이전 페이지로 되돌아갑니다.
        ()=>{
            console.log("onConnectRoomPage.stompClient.connect : connection failed.");
        }
    );
}

// 채팅방 페이지를 생성합니다.
function createRoomPage(info_box, stompClient){
    console.log("createRoomPage : createRoomPage called.");
    clearHTMLElement(div_content_container);

}


/* 메인 페이지 초기화 */
window.onload = ()=>{
    console.log("window.onload : window.onload called.");

    // 내용을 표현하는 HTML Element를 가져옵니다.
    div_content_container = document.getElementById("content-container");

    // 페이지 이동 간 필요한 값을 저장하는 객체를 생성합니다.
    let info_box = {};

    // 메인 페이지를 표현합니다.
    createMainPage(info_box);
}