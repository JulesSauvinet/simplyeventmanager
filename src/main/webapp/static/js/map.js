var map;
function initMap() {

    if (document.getElementById("latitude").value == '' && document.getElementById("longitude").value == '') {
        var maLatlng = new google.maps.LatLng(48.8534100, 2.3488000);
    }
    else {
        var maLatlng = new google.maps.LatLng(document.getElementById("latitude").value, document.getElementById("longitude").value);
    }

        var map = new google.maps.Map(document.getElementById('map'), {
        center: maLatlng,
        zoom: 12,
        zoomControl : true,
        zoomControlOpt: {
            style : 'SMALL',
            position: 'TOP_LEFT'
        },
        panControl : false
    });

    var marker;

    if (document.getElementById("latitude").value == '' || document.getElementById("longitude").value == '') {

        // On teste tout d'abord si le navigateur prend en charge la geolocalisation
        if (navigator.geolocation) {
            var watchId = navigator.geolocation.watchPosition(
                geolocalisation,
                null,
                {enableHighAccuracy: true}
            );
        }

        function geolocalisation(position) {
            map.panTo(new google.maps.LatLng(position.coords.latitude, position.coords.longitude));
            //var marker = new google.maps.Marker({
            //    position: new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
            //    map: map,
            //    draggable: true,
            //    animation: google.maps.Animation.DROP
            //});
            //
            //marker.addListener('click', toggleBounce);
        }
    }
    else {

        marker = new google.maps.Marker({
                position: maLatlng,
                map: map,
                draggable: true,
                animation: google.maps.Animation.DROP
            });

        marker.addListener('click', toggleBounce);
    }



    google.maps.event.addListener(map, 'click', function(event) {
        placeMarker(event.latLng);
    });


    function placeMarker(location) {
        if(marker){ //on verifie si le marqueur existe
            marker.setPosition(location); //on change sa position
        }else{
            marker = new google.maps.Marker({ //on cree le marqueur
                position: location,
                map: map,
                draggable: true,
                animation: google.maps.Animation.DROP
            });

            marker.addListener('click', toggleBounce);
        }
        document.getElementById("latitude").value = location.lat();
        document.getElementById("longitude").value =location.lng();
    }
}

function toggleBounce() {
    if (marker.getAnimation() !== null) {
        marker.setAnimation(null);
    } else {
        marker.setAnimation(google.maps.Animation.BOUNCE);
    }
}