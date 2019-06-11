/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function init() {
    var links = document.querySelectorAll("#commentlink");
    console.log(links);
    for (var i = 0; i < links.length; i++) {
        var link = links[i];
        console.log(link);
        link.id = i;

        link.addEventListener('click', handleClick, false);
    }
}

function handleClick(eventInformation) {
    console.log(eventInformation);
    const origin = eventInformation.target;
    console.log(origin.id);
    displayArticle(origin.id);

    eventInformation.preventDefault();
}

function displayArticle(index) {
    var articles = document.querySelectorAll("#display");
    console.log(articles);

    for (var i = 0; i < articles.length; i++) {
        console.log(index);
        if (index == i) {
            articles[i].className = '';
        }
    }
}