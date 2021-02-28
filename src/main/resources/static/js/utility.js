/* 유틸리티 */
// 메시지 전송을 위한 현재 시간을 반환합니다.
function getNowIsoTime() {
    let now = new Date();
    return now.toISOString();
}
// HTML Element를 생성합니다.
function makeHTMLElement(tag_name, attributes) {
    let element = document.createElement(tag_name);
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
    name = v.padLeft(name, 6, '　');
    return v.sprintf("[%s] %s", name, text);
}
// 모든 form 버튼을 활성화/비활성화합니다.
function controlFormTagSubmit(flag){
    let form_tags = document.querySelectorAll("form");
    form_tags.forEach(form_tag=>{
        form_tag.disabled = flag;
    });
}

