var BSRV = {
    initserv: function(successCallback, failureCallback) {
        cordova.exec(successCallback, failureCallback, 'BackgroundSV',
            'echo', ["Marcello Fabbiani Service"]);
    }
};

module.exports = BSRV;