var speechkit = {};

/************
 * Bootstrap
 ************/

speechkit.tts = function (text, language, successCallback, failureCallback) {
    cordova.exec( successCallback,
        failureCallback,
        'speechkit',
        'startTTS',
        [text, language]
    );
};

module.exports = speechkit;