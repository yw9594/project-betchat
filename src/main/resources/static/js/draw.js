/* 페이지 표현 */
/* 홈 페이지 */
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
