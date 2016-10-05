var speechkit = {};

/************
 * Bootstrap
 ************/

// Configure TTS
speechkit.configure = function (url, apiKey, successCallback, failureCallback) {
    cordova.exec( successCallback,
        failureCallback,
        'SpeechKit',
        'configure',
        [url, apiKey]
    );
};

speechkit.tts = function (text, successCallback, failureCallback) {
    cordova.exec( successCallback,
        failureCallback,
        'SpeechKit',
        'startTTS',
        [text]
    );
};

module.exports = speechkit;