$(document).ready(function() {

    if ($('#logged-in').length > 0) {
        $.notify({
            icon: 'ti-info-alt',
            message: "Vous êtes maintenant <strong>connecté(e)</strong>"

        },{
            type: 'success',
            timer: 4000
        });
    }

    if ($('#signed-in').length > 0) {
        $.notify({
            icon: 'ti-info-alt',
            message: "Vous êtes maintenant <strong>inscrit(e)</strong> et <strong>connecté(e)</strong>"

        },{
            type: 'success',
            timer: 4000
        });
    }
});