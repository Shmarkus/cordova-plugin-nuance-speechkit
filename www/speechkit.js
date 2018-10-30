"use strict";
function SpeechKit() {
}

SpeechKit.prototype.tts = function (text, language, voice, successCallback, failureCallback) {
    cordova.exec( successCallback,
        failureCallback,
        'speechkit',
        'startTTS',
        [text, language, voice]
    );
};

SpeechKit.prototype.asr = function (language, successCallback, failureCallback) {
    cordova.exec( successCallback,
        failureCallback,
        'speechkit',
        'startASR',
        [language]
    );
};

SpeechKit.prototype.stop = function (successCallback, failureCallback) {
    cordova.exec( successCallback,
        failureCallback,
        'speechkit',
        'stop',
        []
    );
};

SpeechKit.install = function () {
    if (!window.plugins) {
        window.plugins = {};
    }

    window.plugins.speechkit = new SpeechKit();
    return window.plugins.speechkit;
};

cordova.addConstructor(SpeechKit.install);
