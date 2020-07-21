//define our local namespace List
let github = (function () {
    // the function that triggers an Ajax call
    let html = ""
    var check = function(response) {
        if (response.status >= 200 && response.status < 300) {
            return Promise.resolve(response)
        } else if(response.status === 404) {
            document.querySelector("#error").innerHTML = "User Name not exist.";
            document.getElementById("error").style.display = "block";
        } else {
            return Promise.reject(new Error(response.statusText))
        }
    }

    function searchName() {
        let t = name();
        html = ""
        const j = {"name": t};
        fetch('http://localhost:8080/search', {method: 'POST',
            headers:{"Content-Type": "application/json"},body: JSON.stringify(j)})
            .then(check)
            .then(function(response){
                response.json().then(function(data) {
                    console.log(data)
                    if(data[data.length-1].status === "notExist")
                    {
                        error("User Name not exist")
                        return;
                    }
                    if(data[data.length-1].status === "noSession")
                    {
                        let err = "no session access denied -> ";
                        err += '<a href="/">Home</a>';
                        document.querySelector("#error").innerHTML = err;
                        document.getElementById("error").style.display = "block";
                        html = "";
                        document.querySelector("#data").innerHTML = html;
                        return;
                    }
                    if(data[data.length-1].status === "empty")
                    {
                        error("please select a no empty Usher Name")
                        return;
                    }
                    if(data.length === 1 && data[data.length-1].status !== "empty")
                    {
                        error("Followers not exist")
                        return;
                    }
                    html += '<div id="data" class="box">';
                    html += '<p><a href="https://github.com/'+t+'/" class="loginName">'+t+'</a></p>';
                    html += '<ol>';
                    html += '<h2>Followers</h2>';
                    let i = 0
                    for (i; i <data.length-1;i++) {
                        html += '<li><a href="' + data[i].html_url + '">'+ data[i].login+'</a></li>';
                    }
                    html += '</ol> </div>';
                    // display the HTML
                    document.querySelector("#data").innerHTML = html;
            })
        }).catch(function (err) {
            console.log('Fetch Error :', err);
        })
    }

    function error (err) {
        document.querySelector("#error").innerHTML = err;
        document.getElementById("error").style.display = "block";
        html = "";
        document.querySelector("#data").innerHTML = html;
    }

    function name () {
        document.getElementById("error").style.display = "none";
        return (document.getElementById("input").value.trim());
    }

    // wait for the DOM before reaching elements
    window.addEventListener("DOMContentLoaded", (event) => {
        //add listeners
        document.getElementById("search").addEventListener('click', function(){searchName();});
        document.getElementById("input").addEventListener("keyup", function(event) {
            if (event.keyCode === 13) {
                event.preventDefault();
                document.getElementById("search").click();
            }
        });
    }, false);

})();   // end of local namespace