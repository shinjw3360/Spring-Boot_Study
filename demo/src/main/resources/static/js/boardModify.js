document.getElementById('modBtn').addEventListener('click', (e) => {
    document.getElementById('t').readOnly = false;
    document.getElementById('c').readOnly = false;

    //form 태그의 submit 역할을 하는 버튼 생성
    //
    let regBTN = document.createElement("button");
    regBTN.setAttribute('type','submit');
    regBTN.setAttribute('id','regbtn');
    regBTN.classList.add('btn', 'btn-success');
    regBTN.innerText = 'Submit';

    document.getElementById('modForm').appendChild(regBTN);
    //뒤에 있는 modBtn, delBtn 삭제
    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();
    document.getElementById('listBtn').remove();
    document.getElementById('comment').remove();

})