document.getElementById('trigger').addEventListener('click', () => {
    document.getElementById('file').click();
});

// 실행파일 막고, 10MB 이상 사이즈는 제한걸기
const regExp = /\.(exe|sh|bat|jar|dll|msi)$/i; // ✅ 안전
const maxSize = 1024 * 1024 * 10;

function fileValid(fileName, fileSize) {
    if (regExp.test(fileName)) return 0;
    if (fileSize > maxSize) return 0;
    return 1;
}

document.getElementById('file').addEventListener('change', (e) => {
    const fileObject = e.target.files;

    const div = document.getElementById('fileZone');
    div.innerHTML = '';

    let ul = `<ul class="list-group">`;
    let isOk = 1;

    for (let file of fileObject) {
        const valid = fileValid(file.name, file.size);
        isOk *= valid;

        ul += `<li class="list-group-item">`;
        ul += `<div class="mb-3">`;
        ul += valid
            ? `<div class="fw-bold mb-1">업로드 가능</div>`
            : `<div class="fw-bold text-danger mb-1">업로드 불가</div>`;
        ul += `${file.name} `;
        ul += `<span class="badge text-bg-${valid ? 'success' : 'danger'}">${file.size} Bytes</span>`;
        ul += `</div></li>`;
    }

    ul += `</ul>`;
    div.innerHTML = ul;

    // ✅ isOk는 여기에서 판단해야 함 (스코프 문제 해결)
    document.getElementById('regBtn').disabled = (isOk === 0);
});
