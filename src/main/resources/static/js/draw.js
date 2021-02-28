/* 페이지 표현 */
// 홈 페이지의 태그를 표현합니다.
function createHomePageTags(div_content_container){
    // 이름을 입력받기 위한 태그를 생성합니다.
    var header_name_input = makeHTMLElement("h3");

    var form_name_input =  makeHTMLElement("form", {"id":"form_name_input"});
    var input_name_text =  makeHTMLElement("input", {"id":"input_name_text", "type":"text", "autocomplete":"off"});
    var input_name_submit = makeHTMLElement("input", {"id":"input_name_submit", "type":"submit", "value":"입장"});

    // 생성된 태그를 페이지에 추가합니다.
    addDOMElement(header_name_input, [document.createTextNode("이름 입력")]);
    addDOMElement(form_name_input, [input_name_text, input_name_submit]);

    // 페이지에 구성 요소를 추가합니다.
    addDOMElement(div_content_container, [header_name_input, form_name_input]);
}

// 로비 페이지의 태그를 표현합니다.
function createLobbyPageTags(div_content_container){
    // 채팅방에 참가하기 위한 태그를 생성합니다.
    var header_room_join = makeHTMLElement("h3");
    var text_room_join = document.createTextNode("채팅방 참가");

    var form_room_join =  makeHTMLElement("form", {"id":"form_room_join"});
    var input_room_join_text =  makeHTMLElement("input", {"id":"input_room_join_text", "type":"text", "placeholder":"room key", "autocomplete":"off"});
    var input_room_join_submit = makeHTMLElement("input", {"id":"input_room_join_submit", "type":"submit", "value":"참가"});

    // 채팅방을 생성하기 위한 태그를 생성합니다.
    var header_room_create = makeHTMLElement("h3");
    var text_room_create = document.createTextNode("채팅방 생성");

    var form_room_create =  makeHTMLElement("form", {"id":"form_room_create"});
    var input_room_create_submit = makeHTMLElement("input", {"id":"input_room_create_submit", "type":"submit", "value":"생성"});

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
    var header_room_key =  makeHTMLElement("h3", );
    var text_room_key = document.createTextNode("room key : " + info_box.room_key);

    // 채팅을 표현하기 위한 태그를 생성합니다.
    var div_chat_list =  makeHTMLElement("div", {"id":"div_chat_list"});

    // 채팅 입력을 생성하기 위한 태그를 생성합니다.
    var form_chat_create =  makeHTMLElement("form", {"id":"form_chat_create"});
    var input_chat_text =  makeHTMLElement("input", {"id":"input_chat_text", "type":"text", "autocomplete":"off"});
    var input_chat_submit = makeHTMLElement("input", {"id":"input_chat_submit", "type":"submit", "value":"전송"});

    addDOMElement(header_room_key, [text_room_key]);
    addDOMElement(form_chat_create, [input_chat_text, input_chat_submit]);

    // 생성된 태그를 페이지에 추가합니다.
    addDOMElement(div_content_container, [header_room_key, div_chat_list, form_chat_create]);
}