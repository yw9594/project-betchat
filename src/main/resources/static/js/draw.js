/* 페이지 표현 */
// 홈 페이지의 태그를 표현합니다.
function createHomePageTags(div_content_container){
    // 이름을 입력받기 위한 태그를 생성합니다.
    let header_name_input = makeHTMLElement("h3");

    let form_name_input =  makeHTMLElement("form", {"id":"form_name_input"});
    let input_name_text =  makeHTMLElement("input", {"id":"input_name_text", "type":"text", "autocomplete":"off"});
    let input_name_submit = makeHTMLElement("input", {"id":"input_name_submit", "type":"submit", "value":"입장"});

    // 생성된 태그를 페이지에 추가합니다.
    addDOMElement(header_name_input, [document.createTextNode("이름 입력")]);
    addDOMElement(form_name_input, [input_name_text, input_name_submit]);

    // 페이지에 구성 요소를 추가합니다.
    addDOMElement(div_content_container, [header_name_input, form_name_input]);
}

// 로비 페이지의 태그를 표현합니다.
function createLobbyPageTags(div_content_container){
    // 채팅방에 참가하기 위한 태그를 생성합니다.
    let header_room_join = makeHTMLElement("h3");
    let text_room_join = document.createTextNode("채팅방 참가");

    let form_room_join =  makeHTMLElement("form", {"id":"form_room_join"});
    let input_room_join_text =  makeHTMLElement("input", {"id":"input_room_join_text", "type":"text", "placeholder":"room key", "autocomplete":"off"});
    let input_room_join_submit = makeHTMLElement("input", {"id":"input_room_join_submit", "type":"submit", "value":"참가"});

    // 채팅방을 생성하기 위한 태그를 생성합니다.
    let header_room_create = makeHTMLElement("h3");
    let text_room_create = document.createTextNode("채팅방 생성");

    let form_room_create =  makeHTMLElement("form", {"id":"form_room_create"});
    let input_room_create_submit = makeHTMLElement("input", {"id":"input_room_create_submit", "type":"submit", "value":"생성"});

    // 생성된 태그를 페이지에 추가합니다.
    addDOMElement(header_room_join, [text_room_join]);
    addDOMElement(form_room_join, [input_room_join_text, input_room_join_submit]);

    addDOMElement(header_room_create, [text_room_create]);
    addDOMElement(form_room_create, [input_room_create_submit]);
    addDOMElement(div_content_container, [header_room_create, form_room_create, header_room_join, form_room_join]);
}

// 채팅방 페이지의 태그를 생성합니다.
function createRoomPageTags(div_content_container, info_box){
    // room_key를 표현하기 위한 태그를 생성합니다.
    let header_room_key =  makeHTMLElement("h3", );
    let text_room_key = document.createTextNode("room key : " + info_box.room_key);

    // 채팅을 표현하기 위한 태그를 생성합니다.
    let div_chat_list =  makeHTMLElement("div", {"id":"div_chat_list"});

    // 채팅 입력을 생성하기 위한 태그를 생성합니다.
    let form_chat_create =  makeHTMLElement("form", {"id":"form_chat_create"});
    let input_chat_text =  makeHTMLElement("input", {"id":"input_chat_text", "type":"text", "autocomplete":"off"});
    let input_chat_submit = makeHTMLElement("input", {"id":"input_chat_submit", "type":"submit", "value":"전송"});

    addDOMElement(header_room_key, [text_room_key]);
    addDOMElement(form_chat_create, [input_chat_text, input_chat_submit]);

    // 생성된 태그를 페이지에 추가합니다.
    addDOMElement(div_content_container, [header_room_key, div_chat_list, form_chat_create]);
}

// 페이지 헤더를 생성합니다.
function createPageHeaderTag(div_head_container){
    // 헤더를 표현하기 위한 태그를 생성합니다.
    let header_title =  makeHTMLElement("h2", );
    let text_title = document.createTextNode("Betting Chatting");

    // 생성된 태그를 페이지에 추가합니다.
    addDOMElement(header_title, [text_title]);
    addDOMElement(div_head_container, [header_title]);
}

// 채팅을 표시하는 태그를 생성합니다.
function createChattingTag(message_body) {
    // 채팅 태그를 생성합니다.
    let div_chat =  makeHTMLElement("div", {"class":"div_chat"});
    let div_chat_name =  makeHTMLElement("div", {"class":"div_chat_name"});
    let div_chat_text =  makeHTMLElement("div", {"class":"div_chat_text"});

    let text_chat_name = document.createTextNode(message_body.data.user_name);
    let text_chat_text = document.createTextNode(message_body.data.text);

    // 채팅 리스트 태그를 가져옵니다.
    let div_chat_list = document.getElementById("div_chat_list");

    addDOMElement(div_chat_name, [text_chat_name]);
    addDOMElement(div_chat_text, [text_chat_text]);
    addDOMElement(div_chat, [div_chat_name, div_chat_text]);
    addDOMElement(div_chat_list, [div_chat]);

    // 스크롤바를 아래로 내립니다.
    div_chat_list.scrollTop = div_chat_list.scrollHeight;
}