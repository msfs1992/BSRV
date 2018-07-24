var BSRV = {
    initserv: function(successCallback, failureCallback) {
        cordova.exec(successCallback, failureCallback, 'BackgroundSV',
            'echo', ["Marcello Fabbiani"]);
    }
};

module.exports = BSRV;