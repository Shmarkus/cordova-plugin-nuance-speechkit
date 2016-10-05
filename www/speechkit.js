var speechkit = {};

/************
 * Bootstrap
 ************/

// Configure TTS
speechkit.configure = function (url, apiKey, successCallback, failureCallback) {
    cordova.exec( successCallback,
        failureCallback,
        'speechkit',
        'configure',
        [url, apiKey]
    );
};

speechkit.tts = function (text, language, successCallback, failureCallback) {
    cordova.exec( successCallback,
        failureCallback,
        'speechkit',
        'startTTS',
        [text, language]
    );
};

module.exports = speechkit;