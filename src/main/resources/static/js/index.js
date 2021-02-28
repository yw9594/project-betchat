/* 전역 변수 */
let div_head_container;     // 페이지의 헤더를 표현하는 HTML Element입니다.
let div_content_container;  // 페이지의 내용을 표현하는 HTML Element입니다.

// 홈 페이지의 로직입니다.
function homePageLogic(info_box){
    console.log("createMainPage : createMainPage called.");
    clearHTMLElement(div_content_container);

    // 홈 페이지의 HTML 태그들을 생성합니다.
    createHomePageTags(div_content_container);

    // 유저 이름을 전달받아 서버에 전송하는 이벤트 리스너를 정의 및 등록합니다.
    let getNameEventListener = function (event){
        // submit 기능을 일시정지합니다.
        controlFormTagSubmit(false);

        // Ajax를 사용해 서버에 이름을 전달합니다.
        let user_name = input_name_text.value;

        let xhr = makeXHRObj(address.home.login);
        let data = makeXHRJsonBody(result_state.OK, {"user_name":user_name});

        // 유저 이름을 서버에 전송합니다.
        xhr.onreadystatechange = function(){
            if(xhr.readyState===4 && xhr.status===200){
                let response = JSON.parse(xhr.response);

                // 정상적으로 응답을 받은 경우, 로비 페이지를 표현합니다.
                if(response.result_state===result_state.OK){

                    info_box["user_name"] = user_name;
                    info_box["user_key"] = response.data.user_key;

                    lobbyPageLogic(info_box);
                }
                // 오류가 발생했을 경우, 현재 페이지에 머뭅니다.
                else
                    alert("이름은 2~6글자, 한글, 영어, _만 가능합니다.");
            }

            // submit 기능을 활성화합니다.
            controlFormTagSubmit(true);
        }
        xhr.send(JSON.stringify(data));

        // form 태그의 디폴트 이벤트 리스너를 취소합니다.
        event.preventDefault();
    }

    // 이름 전송 이벤트 리스너를 등록합니다.
    document.getElementById("form_name_input").addEventListener("submit", getNameEventListener,true);
}

// 로비 페이지의 로직입니다.
function lobbyPageLogic(info_box){
    console.log("createLobbyPage : createLobbyPage called.");
    clearHTMLElement(div_content_container);

    // 로비 페이지의 HTML 태그들을 생성합니다.
    createLobbyPageTags(div_content_container);

    // 채팅방 참가를 처리하는 이벤트 리스너를 정의 및 등록합니다.
    let joinRoomEventListener = function (event){
        // submit 기능을 일시정지합니다.
        controlFormTagSubmit(false);

        let room_key = input_room_join_text.value;

        // Ajax를 사용해 서버에 room key를 전달합니다
        let xhr = makeXHRObj(address.lobby.join);
        let data = makeXHRJsonBody(result_state.OK, {"room_key":room_key});

        // 응답을 받은 경우, 채팅방을 보여줍니다.
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                let response = JSON.parse(xhr.response);

                // 정상적으로 응답을 받은 경우, 로비 페이지를 표현합니다.
                if (response.result_state === result_state.OK) {
                    info_box["room_key"] = room_key;

                    onConnectRoomPage(info_box);
                }
                // 오류가 발생했을 경우, 현재 페이지에 머뭅니다.
                else
                    alert("유효하지 않은 접근입니다.");
            }
            // submit 기능을 활성화합니다.
            controlFormTagSubmit(true);
        }
        xhr.send(JSON.stringify(data));

        // form 태그의 디폴트 이벤트 리스너를 취소합니다.
        event.preventDefault();
    }

    // 채팅방 생성 요청을 처리하는 이벤트 리스너를 정의 및 등록합니다.
    let createRoomEventListener = function (event){
        // submit 기능을 일시정지합니다.
        controlFormTagSubmit(false);

        // Ajax를 사용해 서버에 user key를 전달합니다
        let xhr = makeXHRObj(address.lobby.create);
        let data = makeXHRJsonBody(result_state.OK, {"user_key":info_box.user_key});

        // 응답을 받은 경우, 채팅방을 보여줍니다.
        xhr.onreadystatechange = function(){
            if(xhr.readyState===4 && xhr.status===200){
                let response = JSON.parse(xhr.response);

                // 정상적으로 응답을 받은 경우, 로비 페이지를 표현합니다.
                if(response.result_state===result_state.OK){
                    info_box["room_key"]=response.data.room_key;

                    onConnectRoomPage(info_box);
                }
                // 오류가 발생했을 경우, 현재 페이지에 머뭅니다.
                else
                    alert("유효하지 않은 접근입니다.");
            }

            // submit 기능을 활성화합니다.
            controlFormTagSubmit(true);
        }
        xhr.send(JSON.stringify(data));

        // form 태그의 디폴트 이벤트 리스너를 취소합니다.
        event.preventDefault();
    }

    // 채팅방 이벤트 리스너를 등록합니다.
    document.getElementById("form_room_join").addEventListener("submit", joinRoomEventListener,true);
    document.getElementById("form_room_create").addEventListener("submit", createRoomEventListener,true);
}

// 채팅방 연결을 처리합니다.
function onConnectRoomPage(info_box){
    console.log("onConnectRoomPage : onConnectRoomPage called.");
    clearHTMLElement(div_content_container);

    // stomp hand shaking을 수행합니다.
    let socket = new SockJS(address.ws);
    let stomp_client = Stomp.over(socket);

    stomp_client.connect({},
        // connection 성공 시 채팅방 페이지로 이동합니다.
        ()=>{
            console.log("onConnectRoomPage.stompClient.connect : connection success.");

            roomPageLogic(info_box, stomp_client);
        },
        // connection 실패 이전 페이지로 되돌아갑니다.
        ()=>{
            console.log("onConnectRoomPage.stompClient.connect : connection failed.");
            alert("서버와의 연결이 종료되었습니다.");
            lobbyPageLogic(info_box);
        }
    );
}

// 채팅방 페이지를 생성합니다.
function roomPageLogic(info_box, stomp_client){
    console.log("createRoomPage : createRoomPage called.");
    clearHTMLElement(div_content_container);

    // 채팅방 페이지의 HTML 태그들을 생성합니다.
    createRoomPageTags(div_content_container, info_box);

    // room_key를 기반으로 채팅방을 구독합니다.
    stomp_client.subscribe(address.chat.subscribe + info_box.room_key,
        // 채팅 수신을 처리하는 이벤트 리스너를 정의합니다.
        (message)=>{
            console.log("createRoomPage.stompClient.subscribe : message received.");
            // 채팅 메시지를 받아 채팅방에 추가합니다.
            let message_body = JSON.parse(message.body);
            let div_chat_text =  makeHTMLElement("div", {"class":"div_chat_text"});
            let text_chat_text = document.createTextNode(makeChatMessageText(message_body.data.user_name, message_body.data.text));

            let div_chat_list = document.getElementById("div_chat_list");

            addDOMElement(div_chat_text, [text_chat_text]);
            addDOMElement(div_chat_list, [div_chat_text]);

            // 스크롤바를 아래로 내립니다.
            div_chat_list.scrollTop = div_chat_list.scrollHeight;
        },
        // 누가 어느 채팅방에 등록할 지 정보를 서버에 전달합니다.
        {"user_key":info_box.user_key, "room_key":info_box.room_key}
        );

    // 채팅 송신을 처리하는 이벤트 리스너를 정의 및 등록합니다.
    let chat_data = JSON.parse(JSON.stringify(info_box)); // info_box의 내용을 복사합니다.
    chat_data["text"] = null;

    let sendChatting = function (event){
        console.log("createRoomPage.sendChatting : message sent.");
        let input_chat_text = document.getElementById("input_chat_text");
        chat_data["text"] =input_chat_text.value;
        let message = makeXHRJsonBody(result_state.OK, chat_data);

        stomp_client.send(address.chat.publish, {}, JSON.stringify(message));
        input_chat_text.value="";

        // form 태그의 디폴트 이벤트 리스너를 취소합니다.
        event.preventDefault();
    }

    // 채팅 전송 이벤트 리스너를 등록합니다.
    document.getElementById("form_chat_create").addEventListener("submit", sendChatting, true);
}

/* 메인 페이지 초기화 */
window.onload = ()=>{
    console.log("window.onload : window.onload called.");

    // 내용을 표현하는 HTML element를 가져옵니다.
    div_head_container = document.getElementById("head_container");
    div_content_container = document.getElementById("content_container");

    // 페이지의 헤더를 표현합니다.
    createPageHeaderTag(div_head_container);

    // 페이지 이동 간 필요한 값을 저장하는 객체를 생성합니다.
    let info_box = {};

    // 메인 페이지를 표현합니다.
    homePageLogic(info_box);
}