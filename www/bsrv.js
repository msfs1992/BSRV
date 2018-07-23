var BSRV = {
    initserv: function(successCallback, failureCallback) {
        cordova.exec(successCallback, failureCallback, 'BSRV',
            'echo', ["Marcello Fabbiani"]);
    }
};

module.exports = BSRV;