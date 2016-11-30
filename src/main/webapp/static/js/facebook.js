// TODO ne pas faire les requêtes à chaque vers la servlet si l'utilisateur est déjà connecté

// TODO gérer les erreurs de connexion avec facebook par un message

//charge le SDK
(function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));


window.fbAsyncInit = function () {
    FB.init({
        appId : '1654332561514237',
        cookie: true,
        xfbml: true,
        version: 'v2.5'
    });
};

function checkLoginState() {
    FB.getLoginStatus(function (response) {
        changeStatus(response);
    });
}

function changeStatus(response) {

    if (response.status === 'connected') {
        var userId = response.authResponse.userID;
        var accessToken = response.authResponse.accessToken;
        var urlRequest = 'connexion/facebook';

        $.post(urlRequest, {
                accessToken: accessToken
            },
            function(data) {
                location.assign(data);
            }
        );
    }
}

function linkInfos(response) {

    FB.getLoginStatus(function (response) {
        getInfos(response);
    });
}


function getInfos(response) {

    if (response.status === 'connected') {
        var userId = response.authResponse.userID;
        var accessToken = response.authResponse.accessToken;
        var urlRequest = 'connexion/facebook/link';

        $.post(urlRequest, {
                accessToken: accessToken
            },
            function(data) {
                location.assign(data);
            }
        );
    }
}

