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

    if ($('#notesOk').length > 0) {
        $.notify({
            icon: 'ti-info-alt',
            message: "Enregistrement des <strong>notes</strong> réussi"

        },{
            type: 'success',
            timer: 4000
        });
    }

    if ($('#paiementOk').length > 0) {
        $.notify({
            icon: 'ti-info-alt',
            message: "Enregistrement du <strong>paiement</strong> réussi"

        },{
            type: 'success',
            timer: 4000
        });
    }
    
    $('#annulerRdv').on('click', function(event) {
    	var delOk = confirm('Voulez-vous vraiment supprimer ce rendez-vous ?');
    	
    	event.preventDefault();
    	
    	if (delOk) {
    		window.location = location.pathname + '/cancel';
    	}
    })
    
    $('#annulerRdv').on('click', function(event) {
    	var delOk = confirm('Voulez-vous vraiment supprimer ce patient ?');
    	
    	event.preventDefault();
    	
    	if (delOk) {
    		window.location = location.pathname + '/cancel';
    	}
    })
});