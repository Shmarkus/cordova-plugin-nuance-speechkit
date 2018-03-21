# cordova-plugin-nuance-speechkit
This is an implementation of [Nuance SpeechKit](https://developer.nuance.com) (v2.1+) SDK in Cordova (6.2+).

## Installation
First create an account in the [Nuance Developers](https://developer.nuance.com/public/index.php?task=register) site. Then open your account and find the **URL** and **App Key** for your account. Include the plugin to your project using standard plugin add commands like
```Bash
cordova plugin add cordova-plugin-nuance-speechkit --variable URL=[your URL here] --variable APP_KEY=[your App Key here]
```
> IMPORTANT: It is not possible to use the plugin without these variables! 

Next, add Ionic native binding
```Bash
npm install --save @ionic-native/speechkit
```
Add SpeechKit to your module **providers** section and you are ready

## Sample
[Sample project of Text to speech (Ionic 2)](https://github.com/Shmarkus/cordova-plugin-nuance-speechkit-sample)

[Find out which languages are supported and what language codes are used](https://developer.nuance.com/public/index.php?task=supportedLanguages)

## Quirks
### Building for iOS
This plugin uses *cordova-plugin-cocoapod-support* to download the SpeechKit SDK. When adding plugin outside Apple ecosystem, you have to include the SpeechKit manually.
 
 * Add cordova-plugin-nuance-speechkit as described in Installation chapter
 * Add iOS platform (it will warn you about missing cocoapod binary)
 * Copy iOS platform to a Mac device and open Xcode
 * Download iOS SDK from Nuance Developer website
 * Move the SpeechKit.framework from extracted archive to Xcode Frameworks section
 * Profit!

## TO-DO
 * Speech to text impl (ASR)