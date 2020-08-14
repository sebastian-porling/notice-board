/* When ready document */
window.addEventListener("load", () => {
    initializeGlobalVariables();
    checkLoginState();
    fetchAll();

    /**
     * Takes care of the form submission for adding notice
     */
    addForm.addEventListener('submit', (event) => {
        event.preventDefault();
        add(addText.value, addDate.value);
        resetFields(addText, addDate);
        $('#addModal').modal('hide');
    });

    /**
     * Takes care of the form submission for editing notice
     */
    editForm.addEventListener('submit', (event) => {
        event.preventDefault();
        edit(editId.value, editText.value, editDate.value);
        resetFields(editId, editText, editDate);
        $('#editModal').modal('hide');
    });

    /**
     * Takes care of the form submission for logging in user
     */
    loginForm.addEventListener('submit', (event) => {
        event.preventDefault();
        $('#loginModal').modal('hide');
        login(loginUsername.value, loginPassword.value);
        resetFields(loginUsername, loginPassword);
    });

    /**
     * Takes care of the form submission for register user
     */
    registerForm.addEventListener('submit', (event) => {
        event.preventDefault();
        if(registerPassword1.value == registerPassword2.value) {
            register(registerUsername.value, registerPassword1.value);
            resetFields(registerUsername, registerPassword1, registerPassword2);
            $('#registerModal').modal('hide');
        }
    });

    /**
     * Takes care of form submissions for adding comments
     */
    commentForm.addEventListener('submit', (event) => {
        event.preventDefault();
        comment(commentNoticeId.value, commentText.value)
        resetFields(commentNoticeId, commentText);
        $('#commentModal').modal('hide');
    });
});

/**
 * Resets a input field
 * @param args input fields
 */
function resetFields(...args) {
    args.forEach(argument => argument.value="");
}

/**
 * Checks if any of the arguments are empty
 * @param args any number of strings
 * @returns {boolean} true if one is empty
 */
function checkEmptyField(...args) {
    for (let i = 0; i < args.length; i++)
        if(args[i] === "") return true;
    return false;
}

/**
 * Displays the add modal!
 * @param id id of notice
 */
function addComment(id) {
    $('#commentModal').modal('show');
    commentNoticeId.value = id;
}

/**
 * Sets the edit fields and shows the edit modal
 * @param element edit button
 */
function showEditNotice(id) {
    fetchNoticeForEdit(id);
    $('#editModal').modal('show');
}

/**
 * Sets all the fields for the edit modal
 * @param element edit button
 */
function setEditFields(notice) {
    editDate.value = notice.date;
    editId.value = notice.id;
    editText.value = notice.content;
    console.log(notice);
}

/**
 * Checks if the user is logged in through cookie
 * And sets the buttons accordingly
 */
function checkLoginState() {
    if (Cookies.get('username') !== undefined) {
        setHidden(true, loginBtn, registerBtn);
        setHidden(false, logoutBtn, addBtn);
    } else {
        setHidden(false, loginBtn, registerBtn);
        setHidden(true, logoutBtn, addBtn);
    }
}

/**
 * Changes the state of html elements
 * @param hidden if it should be hidden or not
 * @param args elements
 */
function setHidden(hidden, ...args) {
    args.forEach(argument => argument.hidden=hidden);
}

/**
 * Signs in user
 * @param username string
 * @param password string
 */
function login(username, password) {
    if(checkEmptyField(username, password)) return;
    let dataObject = {username: username, password: password};
    $.ajax({
        url: BASE_URL + "login",
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(dataObject),
        success: function(data) {
            checkLoginState();
            fetchAll();
        },
        fail: (err) => console.log("Couldn't login " + dataObject, err)
    });
}

/**
 * Signs out user
 */
function logout() {
    $.ajax({
        url: BASE_URL + "logout",
        type: 'POST',
        contentType: "application/json",
        success: function(data) {
            document.cookie = "username" + '=; Max-Age=0';
            document.cookie = "id" + '=; Max-Age=0'
            fetchAll();
        },
        fail: (err) => console.log("Couldn't logout ", err)
    });
}

/**
 * Registers user
 * @param username string
 * @param password string
 */
function register(username, password) {
    if(checkEmptyField(username, password)) return;
    let dataObject = {username: username, password: password};
    $.ajax({
        url: BASE_URL + "register",
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(dataObject),
        success: function(data) {
            fetchAll();
        },
        fail: (err) => console.log("Couldn't register " + dataObject, err)
    });
}

/**
 * Fetches all notices
 */
function fetchAll() {
    $.ajax({
        url: BASE_URL + "notice/all",
        type: 'GET',
        contentType: "application/json",
        success: function(data) {
            checkLoginState();
            generateNotices(data.data);
        },
        fail: (err) => console.log("Couldn't fetch all " , err)
    });
}

/**
 * Fetches one notice
 * @param id notice id
 */
function fetchNoticeForEdit(id) {
    if (checkEmptyField(id)) return;
    checkLoginState();
    $.ajax({
        url: BASE_URL + "notice/"+id,
        type: 'GET',
        contentType: "application/json",
        success: function(data) {
            setEditFields(data.data);
            checkLoginState();
        },
        fail: (err) => console.log("Couldn't fetch notice ", err)
    });
}

/**
 * Creates a new notice
 * @param text notice content
 * @param date notice date
 */
function add(text, date) {
    if(checkEmptyField(text, date)) return;
    checkLoginState();
    let dataObject = {content: text, date: date};
    $.ajax({
        url: BASE_URL + "notice/create",
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(dataObject),
        success: function(data) {
            fetchAll();
        },
        fail: (err) => console.log("Couldn't add notice " + dataObject, err)
    });
}

/**
 * Modifies a notice
 * @param id notice id
 * @param text notice content
 * @param date notice date
 */
function edit(id, text, date) {
    if (checkEmptyField(id, text, date)) return;
    checkLoginState();
    let dataObject = {id: id, content: text, date: date};
    $.ajax({
        url: BASE_URL + "notice/update",
        type: 'PATCH',
        contentType: "application/json",
        data: JSON.stringify(dataObject),
        success: function(data) {
            fetchAll();
        },
        fail: (err) => console.log("Couldn't update notice " + dataObject, err)
    });
}

/**
 * Deletes a notice from the database
 * @param id notice id
 */
function deleteNotice(id) {
    checkLoginState();
    fetch(BASE_URL + "notice/"+id, {
        method: 'DELETE', // or 'PUT'
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(Cookies.get('id'));
            fetchAll();
        })
        .catch((error) => {
            fetchAll();
        });
}

/**
 * Adds the comment to the database
 * @param id notice id
 * @param text comment text
 */
function comment(id, text) {
    checkEmptyField(text, date);
    checkLoginState();
    let dataObject = {text: text};
    $.ajax({
        url: BASE_URL + "notice/comment/"+id,
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(dataObject),
        success: function(data) {
            fetchAll();
            $('#commentModal').modal('hide');
        },
        fail: (err) => console.log("Couldn't delete ", err)
    });
}


