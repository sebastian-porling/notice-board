/* Global variables */
let addForm, addDate, addText, addBtn;
let editForm, editDate, editText, editId;
let loginForm, loginUsername, loginPassword, loginBtn, logoutBtn;
let registerForm, registerUsername, registerPassword1, registerPassword2, registerBtn;
let commentForm, commentText, commentNoticeId;
let noticesHtml;

const BASE_URL = location.protocol + '//' + location.host + "/";

/**
 * Initializes all global variables
 */
function initializeGlobalVariables() {
    addForm = document.getElementById('addForm');
    addDate = document.getElementById('date');
    addText = document.getElementById('text');
    addBtn = document.getElementById('add-button');
    editForm = document.getElementById('editForm');
    editDate = document.getElementById('edit-date');
    editText = document.getElementById('edit-text');
    editId = document.getElementById('edit-id');
    loginForm = document.getElementById('loginForm');
    loginUsername = document.getElementById('username');
    loginPassword = document.getElementById('password');
    loginBtn = document.getElementById('login-button');
    logoutBtn = document.getElementById('logout-button');
    registerForm = document.getElementById('registerForm');
    registerUsername = document.getElementById('register-username');
    registerPassword1 = document.getElementById('register-password1');
    registerPassword2 = document.getElementById('register-password2');
    registerBtn = document.getElementById('register-button');
    commentForm = document.getElementById('commentForm');
    commentText = document.getElementById('comment');
    commentNoticeId = document.getElementById('comment-notice-id');
    noticesHtml = document.getElementById('notice-board');
}