/**
 * Generates all notices
 * @param notices notice data
 */
function generateNotices(notices) {
    noticesHtml.innerHTML = "";
    notices.forEach(notice => {
        noticesHtml.appendChild(generateNotice(notice));
    });
}

/**
 * Generates a notice element
 * @param notice Notice data
 * @returns {HTMLDivElement} Notice element
 */
function generateNotice(notice) {
    const noticeHtml = `<div class="card">
                <div class="card-header">
                    <h2 hidden>${notice.id}</h2>
                    <div class="entry-date"><h3 class="comment-date">${notice.date}</h3></div>
                    ${notice.author.id == Cookies.get('id') ? `
                    <div class="btn-group">
                        <button class="btn btn-sm" onclick="showEditNotice(${notice.id});">
                            <span class="oi oi-pencil" title="pencil" aria-hidden="true"></span>
                        </button>
                        <button class="close" onclick='deleteNotice(${notice.id});'>x</button>
                    </div>` : ``}
                </div>
                <div class="card-body">
                    <h4>${notice.author.username}</h4>
                    <p>${notice.content}</p>
                    <div class="comments collapse" id="comments-${notice.id}">
                        <hr>
                         ${Object.keys(notice.comments).map(function (key) {
        return `<div>
                                            <h5>${notice.comments[key].author.username}</h5>
                                               <p>${notice.comments[key].text}</p><hr>
                                        </div>`; }).join('')}
                    </div>
                </div>
                <div class="card-footer">
                    ${Cookies.get('id') !== undefined ?
        `<button class="btn btn-sm add-comment-btn" onclick='addComment(${notice.id});'>Add comment</button>` :
        ``}
                    ${notice.comments.length > 0 ?
        `<button class="btn btn-sm" type="button" data-toggle="collapse" data-target='#comments-${notice.id}' aria-expanded="false" aria-controls="collapseExample">
                        Show comments
                    </button>` : ``}
                </div>
            </div>`;
    let div = document.createElement('div');
    div.setAttribute('class', 'col-sm-6 col-lg-4');
    div.innerHTML = noticeHtml;
    return div;
}