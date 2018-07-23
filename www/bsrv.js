var BSRV = {
    initserv: function(successCallback, failureCallback) {
    	console.log("hola");
        cordova.exec(successCallback, failureCallback, 'BSRV',
            'echo', ["Marcello Fabbiani"]);
    }
};

module.exports = BSRV;