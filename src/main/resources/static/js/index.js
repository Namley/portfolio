// Call the function when the DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    const tabs = {
        posts: document.getElementById('posts-tab'),
        musics: document.getElementById('musics-tab'),
        videos: document.getElementById('videos-tab'),
        messages: document.getElementById('messages-tab')
    };

    const tabButtons = {
        posts: document.getElementById('posts-tab-button'),
        musics: document.getElementById('musics-tab-button'),
        videos: document.getElementById('videos-tab-button'),
        messages: document.getElementById('messages-tab-button')
    };

    // Function to show the selected tab and hide the others
    function switchTab(activeTab) {
        // Loop through all the tabs and hide them
        for (const tab in tabs) {
            tabs[tab].style.display = 'none';  // Hide all tabs
            tabButtons[tab].classList.remove('is-active');  // Remove the is-active class from all tab buttons
        }
        // Show the active tab
        tabButtons[activeTab].classList.add('is-active');  // Show the selected tab
        tabs[activeTab].style.display = 'block';  // Show the selected tab
    }

    // Add click event listeners to each tab button
    tabButtons.posts.addEventListener('click', () => switchTab('posts'));
    tabButtons.musics.addEventListener('click', () => switchTab('musics'));
    tabButtons.videos.addEventListener('click', () => switchTab('videos'));
    tabButtons.messages.addEventListener('click', () => switchTab('messages'));

    // Initially, only the posts tab is visible
    switchTab('posts');
});

document.addEventListener('DOMContentLoaded', () => {
    (document.querySelectorAll('.notification .delete') || []).forEach(($delete) => {
        const $notification = $delete.parentNode;

        $delete.addEventListener('click', () => {
            $notification.parentNode.removeChild($notification);
        });
    });
});
document.addEventListener('DOMContentLoaded', () => {
    fetch('/plane/plane.html')
        .then(response => response.text())
        .then(data => {
            document.getElementsByClassName('planeFrame').innerHTML = data;
        });
});

document.getElementById('formSubmit').addEventListener('click', event => {
    document.getElementById('contentText').value = document.querySelector('.tweetArea').innerHTML;
});


document.addEventListener('DOMContentLoaded', () => {
    const image = document.getElementById('spinImage');
    const image2 = document.getElementById('spinImage2');

    image.addEventListener('click', () => {
        image.classList.add('spin');
        setTimeout(() => {
            if (image.src.includes("avatar1")) {
                image.src = '/img/avatar2.JPG'
            } else {
                image.src = '/img/avatar1.jpg';
            }
        }, 500);
    });


    image.addEventListener('animationend', () => {
        image.classList.remove('spin');
    });

    image2.addEventListener('click', () => {
        image2.classList.add('spin');
        setTimeout(() => {
            if (image2.src.includes("avatar1")) {
                image2.src = '/img/avatar2.JPG'
            } else {
                image2.src = '/img/avatar1.png';
            }
        }, 500);
    });


    image2.addEventListener('animationend', () => {
        image2.classList.remove('spin');
    });
});

document.querySelector("#imageUploader").addEventListener("change", event => {
    document.querySelector("#uploadedImage").src = URL.createObjectURL(event.target.files[0]);
    document.querySelector("#removeImage").hidden = false;
})

document.querySelector("#removeImage").addEventListener("click", event => {
    document.getElementById('imageUploader').value = "";
    document.querySelector("#uploadedImage").src = "";
    document.querySelector("#removeImage").hidden = true;

});
document.querySelector(".tweetArea").addEventListener("input", event => {
    if (document.querySelector(".tweetArea").innerText !== "") {
        document.querySelector(".whatsNew").hidden = true;
    } else {
        document.querySelector(".whatsNew").hidden = false;
    }
});

document.addEventListener('DOMContentLoaded', () => {
    // Functions to open and close a modal
    function openModal($el) {
        $el.classList.add('is-active');
    }

    function closeModal($el) {
        document.querySelector(".tweetArea").innerText = "";
        document.getElementById('imageUploader').value = "";
        document.querySelector("#uploadedImage").src = "";
        document.querySelector(".whatsNew").hidden = false;

        $el.classList.remove('is-active');
    }

    function closeAllModals() {
        (document.querySelectorAll('.modal') || []).forEach(($modal) => {
            closeModal($modal);
        });
    }

    // Add a click event on buttons to open a specific modal
    (document.querySelectorAll('.postModalTrigger') || []).forEach(($trigger) => {
        const modal = $trigger.dataset.target;
        const $target = document.getElementById(modal);

        $trigger.addEventListener('click', () => {
            openModal($target);
        });
    });

    // Add a click event on various child elements to close the parent modal
    (document.querySelectorAll('.modal-background, .modal-close, .modal-card-head .delete') || []).forEach(($close) => {
        const $target = $close.closest('.modal');

        $close.addEventListener('click', () => {
            closeModal($target);
        });
    });

    // Add a keyboard event to close all modals
    document.addEventListener('keydown', (event) => {
        if (event.key === "Escape") {
            closeAllModals();
        }
    });
});

