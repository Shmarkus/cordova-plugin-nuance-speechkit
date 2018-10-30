//
//  CDVSpeechKit.m
//
//  Created by Adam on 10/3/12.
//  Updated by Markus Karileet on 10/7/16
//  Updated by Markus Karileet on 17/9/18
//  Updated by Markus Karileet on 30/10/18
//
//

#import "CDVSpeechKit.h"
#import <SpeechKit/SpeechKit.h>

@implementation CDVSpeechKit
/*
 * Initializes speech kit
 */
- (void) pluginInitialize {
    NSLog(@"CDVSpeechKit.initSpeechKit: Entered method.");
    NSBundle* mainBundle = [NSBundle mainBundle];
    NSString *url = [mainBundle objectForInfoDictionaryKey:@"sk_url"];
    NSString *appKey = [mainBundle objectForInfoDictionaryKey:@"sk_app_key"];
    session = [[SKSession alloc] initWithURL:[NSURL URLWithString:url] appToken:appKey];
    NSLog(@"CDVSpeechKit.initSpeechKit: Leaving method.");
}

/*
 * Start text to speech with the parameters passed
 */
- (void) startTTS:(CDVInvokedUrlCommand*)command{
    NSLog(@"CDVSpeechKit.startTTS: Entered method.");
    // If the voice is nil, use language default voice. If the voice is set, ignore language and use voices default lang.
    if ([command.arguments objectAtIndex:2] == nil) {
        SKTransaction* transaction = [session speakString:[command.arguments objectAtIndex:0]
                                             withLanguage:[command.arguments objectAtIndex:1]
                                             delegate:self];
    } else {
        SKTransaction* transaction = [session speakString:[command.arguments objectAtIndex:0]
                                             withVoice:[command.arguments objectAtIndex:2]
                                             delegate:self];
    }
    NSLog(@"CDVSpeechKit.startTTS: Leaving method.");
}

/*
 * Stop audio
 */
- (void) stop:(CDVInvokedUrlCommand*)command{
    NSLog(@"CDVSpeechKit.stop: Entered method.");
    SKAudioPlayer* audioPlayer = [session audioPlayer];
    [audioPlayer stop]
    NSLog(@"CDVSpeechKit.stop: Leaving method.");
}
@end
