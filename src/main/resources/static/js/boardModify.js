console.log("boardModify.js in");

// modBtn 버튼을 클릭하면 title, content만 readOlny = false 풀기
document.getElementById('modBtn').addEventListener('click',()=>{
    document.getElementById('t').readOnly = false;
    document.getElementById('c').readOnly = false;

    // Form 태그의 submit 역할을 하는 버튼 생성
    // <button type="submit" class="btn btn-success" id="regBtn">submit</button>
    let regBtn = document.createElement("button"); //<button></button>
    regBtn.setAttribute('type','submit');
    regBtn.setAttribute('id','regBtn');
    regBtn.classList.add('btn', 'btn-success');
    regBtn.innerText = "Submit";

    // Form의 가장 마지막 요소로 추가
    document.getElementById('modForm').appendChild(regBtn);
    
    // modBtn, delBtn 삭제
    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();
    document.getElementById('listBtn').remove();
    // 댓글 라인도 삭제
    document.getElementById('comment').remove();

    // file upload 버튼 설정(표시)
    document.getElementById('trigger').style.display="block";

    // file-x 버튼 표시
    // style="visibility: hidden; (여러개 => 배열)
    let fileDelBtn = document.querySelectorAll(".file-x");
    console.log(fileDelBtn);
    //fileDelBtn.style.visibility="visible";

    fileDelBtn.forEach(btn=>{
        btn.style.visibility = "visible";
        // btn 버튼을 클릭하면 비동기로 uuid를 보내서 DB상에서 파일을 삭제
        btn.addEventListener('click',(e)=>{
            let uuid = e.target.dataset.uuid;

            //비동기 전송 호출
            fileRemoveToServer(uuid).then(result =>{
                if(result == "1"){
                    // 지워진 그림 화면에서 제거
                    e.target.closest('li').remove();
                }
            })
        })
    })
}); 

async function fileRemoveToServer(uuid) {
    try {
        const url = '/board/file/'+uuid;
        const config = {
            method: 'delete',
        }
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

// list 버튼을 클릭하면 /board/list로 이동
// document.getElementById('listBtn').addEventListener('click', ()=>{
//     window.location.href = "/board/list"
// })

