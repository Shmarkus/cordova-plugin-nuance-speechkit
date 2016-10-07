# cordova-plugin-nuance-speechkit
This is an implementation of [Nuance SpeechKit](https://developer.nuance.com) (v2.1.3) SDK in Cordova.

## Installation
First create an account in the [Nuance Developers](https://developer.nuance.com/public/index.php?task=register) site. Then open your account and find the **URL** and **App Key** for your account. Include the plugin to your project using standard plugin add commands like
```Bash
phonegap plugin add cordova-plugin-nuance-tts --variable URL=[your URL here] --variable APP_KEY=[your App Key here]
```

> It is not possible to use the plugin without these variables! 

## Usage
Sample call to Text to speech (Ionic 2):
```TypeScript
import {Component} from "@angular/core";
import {Platform} from "ionic-angular";
declare var speechkit:any;

@Component({
  templateUrl: 'something.html',
})
export class SpeechKitExample {
  constructor(private _platform: Platform) { }

  speak(text: string) {
    this._platform.ready().then(() => {
      speechkit.tts(text, "ENG-GBR");
    });
  }
}
```

[Find out which languages are supported and what language codes are used](https://developer.nuance.com/public/index.php?task=supportedLanguages)

## TO-DO
 * Speech to text impl (ASR)