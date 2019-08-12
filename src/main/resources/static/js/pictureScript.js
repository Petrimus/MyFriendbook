function init() {
    const buttons = document.querySelectorAll(".show-button");
    
    for (var i = 0; i < buttons.length; i++) {
        let button = buttons[i];
        // console.log(link);
        button.id = i;
        // button.addEventListener('click', toggle);
    }
    const comments = document.querySelectorAll(".comment-display");
    // console.log(comments);
    for (var i = 0; i < comments.length; i++) {
        comments[i].style.display = "none";
    }
}

const toggle = (id) => {
    const origin = document.getElementById(id);
    const divs = document.querySelectorAll(".comment-display");    
    divs[id].style.display = (divs[id].style.display === 'none') ?  "" : "none";    
    origin.innerHTML = origin.innerHTML === 'show comments' ? "hide comments" : "show comments";
};

const handleShowNewPicture = () => {
    // event.preventDefault();
    const div = document.querySelector(".new-message-container");
    console.log(div);
    div.style.display = "block";
};