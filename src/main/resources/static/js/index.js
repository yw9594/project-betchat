/* 서버 정보 */
const host_address = "http://localhost:8080";  // 서버 주소를 정의합니다.
const ws_url = "/ws";
const chat_pub_url = "/pub/chat/";
const chat_sub_url = "/sub/chat/";

/* 페이지 전역 변수 */
let div_head_container;     // 페이지의 헤더를 표현하는 HTML Element입니다.
let div_content_container;  // 페이지의 내용을 표현하는 HTML Element입니다.

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

// 페이지에 표현할 채팅 메시지를 생성합니다.
function makeChatMessageText(name, text){
    return name + " : " + text;
}

/* 메인 페이지 */
// 메인 페이지의 HTML 태그를 생성합니다.
function createMainPage(info_box){
    console.log("createMainPage : createMainPage called.");
    clearHTMLElement(div_content_container);

    // 이름을 입력받기 위한 태그를 생성합니다.
    var header_name_input = makeHTMLElement("h3");
    var text_name_input  = document.createTextNode("이름 입력");

    var form_name_input =  makeHTMLElement("form", {"id":"form_name_input"});
    var input_name_text =  makeHTMLElement("input", {"id":"input_name_text", "type":"text", "placeholder":"이름을 입력하세요."});
    var input_name_submit = makeHTMLElement("input", {"id":"input_name_submit", "type":"submit", "value":"입장!"});

    // 생성된 태그를 페이지에 추가합니다.
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

    // 채팅방에 참가하기 위한 태그를 생성합니다.
    var header_room_join = makeHTMLElement("h3");
    var text_room_join = document.createTextNode("채팅방 참가");

    var form_room_join =  makeHTMLElement("form", {"id":"form_room_join"});
    var input_room_join_text =  makeHTMLElement("input", {"id":"input_room_join_text", "type":"text", "placeholder":"room key"});
    var input_room_join_submit = makeHTMLElement("input", {"id":"input_room_join_submit", "type":"submit", "value":"참가!"});


    // 채팅방을 생성하기 위한 태그를 생성합니다.
    var header_room_create = makeHTMLElement("h3");
    var text_room_create = document.createTextNode("채팅방 생성");

    var form_room_create =  makeHTMLElement("form", {"id":"form_room_create"});
    var input_room_create_submit = makeHTMLElement("input", {"id":"input_room_create_submit", "type":"submit", "value":"생성!"});

    // 생성된 태그를 페이지에 추가합니다.
    addDOMElement(header_room_join, [text_room_join]);
    addDOMElement(form_room_join, [input_room_join_text, input_room_join_submit]);


    addDOMElement(header_room_create, [text_room_create]);
    addDOMElement(form_room_create, [input_room_create_submit]);
    addDOMElement(div_content_container, [header_room_join, form_room_join, header_room_create, form_room_create]);

    // 채팅방 참가를 처리하는 이벤트 리스너를 정의 및 등록합니다.
    var joinRoomEventListener = function (event){
        // 유저가 가지고 있는 room key를 기반으로 채팅방에 참가합니다.
        info_box["room_key"]=input_room_join_text.value;
        onConnectRoomPage(info_box);

        // form 태그의 디폴트 이벤트 리스너를 취소합니다.
        event.preventDefault();
    }

    // 채팅방 생성 요청을 처리하는 이벤트 리스너를 정의 및 등록합니다.
    var createRoomEventListener = function (event){
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

    // 채팅방 이벤트 리스너를 등록합니다.
    form_room_join.addEventListener("submit", joinRoomEventListener,true);
    form_room_create.addEventListener("submit", createRoomEventListener,true);
}

// 채팅방 로딩 중간 과정을 처리합니다.
function onConnectRoomPage(info_box){
    console.log("onConnectRoomPage : onConnectRoomPage called.");
    clearHTMLElement(div_content_container);

    // stomp hand shaking을 수행합니다.
    var socket = new SockJS(ws_url);
    var stompClient = Stomp.over(socket);

    stompClient.connect({},
        // connection 성공 시 채팅방 페이지로 이동합니다.
        ()=>{
            console.log("onConnectRoomPage.stompClient.connect : connection success.");

            createRoomPage(info_box, stompClient);
        },
        // connection 실패 이전 페이지로 되돌아갑니다.
        ()=>{
            console.log("onConnectRoomPage.stompClient.connect : connection failed.");
            createLobbyPage(info_box);
        }
    );
}

// 채팅방 페이지를 생성합니다.
function createRoomPage(info_box, stompClient){
    console.log("createRoomPage : createRoomPage called.");
    clearHTMLElement(div_content_container);

    // room_key를 표현하기 위한 태그를 생성합니다.
    var header_room_key =  makeHTMLElement("h3", );
    var text_room_key = document.createTextNode("room key : " + info_box.room_key);

    // 채팅을 표현하기 위한 태그를 생성합니다.
    var ul_chat_list =  makeHTMLElement("ul", {"id":"ul_chat_list"});

    // 채팅 입력을 생성하기 위한 태그를 생성합니다.
    var form_chat_create =  makeHTMLElement("form", {"id":"form_chat_create"});
    var input_chat_text =  makeHTMLElement("input", {"id":"input_chat_text", "type":"text"});
    var input_chat_submit = makeHTMLElement("input", {"id":"input_chat_submit", "type":"submit", "value":"전송"});

    addDOMElement(header_room_key, [text_room_key]);
    addDOMElement(form_chat_create, [input_chat_text, input_chat_submit]);

    // 생성된 태그를 페이지에 추가합니다.
    addDOMElement(div_content_container, [header_room_key, ul_chat_list, form_chat_create]);

    // room_key를 기반으로 채팅방을 구독합니다.
    stompClient.subscribe(chat_sub_url + info_box.room_key,
        // 채팅 수신을 처리하는 이벤트 리스너를 정의합니다.
        (message)=>{
            console.log("createRoomPage.stompClient.subscribe : message received.");
            var message_body = JSON.parse(message.body);
            var li_chat_text =  makeHTMLElement("li");
            var text_chat_text = document.createTextNode(makeChatMessageText(message_body.data.user_name, message_body.data.text));

            addDOMElement(li_chat_text, [text_chat_text]);
            addDOMElement(ul_chat_list, [li_chat_text]);
        });

    // 채팅 송신을 처리하는 이벤트 리스너를 정의 및 등록합니다.
    var chat_data = JSON.parse(JSON.stringify(info_box));
    chat_data["text"] = null;

    var sendChatting = function (event){
        console.log("createRoomPage.sendChatting : message sent.");
        chat_data["text"] =input_chat_text.value;
        var message = makeXHRJsonBody(messageStatus.OK, chat_data);

        stompClient.send(chat_pub_url, {}, JSON.stringify(message));
        input_chat_text.value="";

        // form 태그의 디폴트 이벤트 리스너를 취소합니다.
        event.preventDefault();
    }

    // 채팅 전송 이벤트 리스너를 등록합니다.
    form_chat_create.addEventListener("submit", sendChatting, true);
}

// 페이지의 헤더를 생성한다.
function createHeader(){
    // 헤더를 표현하기 위한 태그를 생성합니다.
    var header_title =  makeHTMLElement("h2", );
    var text_title = document.createTextNode("Betting Chatting");

    // 생성된 태그를 페이지에 추가합니다.
    addDOMElement(header_title, [text_title]);
    addDOMElement(div_head_container, [header_title]);
}

/* 메인 페이지 초기화 */
window.onload = ()=>{
    console.log("window.onload : window.onload called.");

    // 내용을 표현하는 HTML Element를 가져옵니다.
    div_head_container = document.getElementById("head-container");
    div_content_container = document.getElementById("content-container");


    // 페이지의 헤더를 표현합니다.
    createHeader();

    // 페이지 이동 간 필요한 값을 저장하는 객체를 생성합니다.
    let info_box = {};

    // 메인 페이지를 표현합니다.
    createMainPage(info_box);
}