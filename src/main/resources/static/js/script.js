
function init() {
    const links = document.querySelectorAll(".commentlink");
    // console.log(links);
    for (var i = 0; i < links.length; i++) {
        let link = links[i];
        // console.log(link);
        link.id = i;
        link.addEventListener('click', handleClick, false);
    }
    const comments = document.querySelectorAll(".comment-display");
    // console.log(comments);
    for (var i = 0; i < comments.length; i++) {
        comments[i].style.display = "none";
    }
}

const handleShowNewMessage = () => {
    // event.preventDefault();
    const div = document.querySelector(".new-message-container");
    console.log(div);
    div.style.display = "block";
};

function handleClick(eventInformation) {
    // console.log(eventInformation.target);
    eventInformation.preventDefault();
    const origin = eventInformation.target;

    const show = eventInformation.target.innerHTML === 'show comments';
    // console.log(origin.id);
    eventInformation.target.innerHTML =
            eventInformation.target.innerHTML === 'show comments' ? 'hide comments' : 'show comments';
    displayArticle(origin.id, show);
}

function displayArticle(index, show) {
    const comments = document.querySelectorAll(".comment-display");
    // console.log("index ", index);
    // console.log(comments);

    for (var i = 0; i < comments.length; i++) {

        if (index == i) {
            if (show) {
                comments[i].style.display = "";
            } else {
                comments[i].style.display = "none";
            }
        }
        /*
         else {
         comments[i].style.display = "none";
         }
         */
    }
}