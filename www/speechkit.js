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

speechkit.asr = function (language, successCallback, failureCallback) {
    cordova.exec( successCallback,
        failureCallback,
        'speechkit',
        'startASR',
        [language]
    );
};

module.exports = speechkit;