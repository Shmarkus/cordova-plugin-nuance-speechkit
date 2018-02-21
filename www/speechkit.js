"use strict";
function SpeechKit() {
}

SpeechKit.prototype.tts = function (text, language, successCallback, failureCallback) {
    cordova.exec( successCallback,
        failureCallback,
        'speechkit',
        'startTTS',
        [text, language]
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

SpeechKit.install = function () {
    if (!window.plugins) {
        window.plugins = {};
    }

    window.plugins.speechkit = new SpeechKit();
    return window.plugins.speechkit;
};

cordova.addConstructor(SpeechKit.install);
