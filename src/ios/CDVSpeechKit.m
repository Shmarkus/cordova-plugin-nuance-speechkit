//
//  CDVSpeechKit.m
//
//  Created by Adam on 10/3/12.
//  Updated by Markus Karileet on 10/7/16
//  Updated by Markus Karileet on 17/9/18
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
    SKTransaction* transaction = [session speakString:[command.arguments objectAtIndex:0]
                                         withLanguage:[command.arguments objectAtIndex:1]
                                         withVoice:[command.arguments objectAtIndex:2]
                                             delegate:self];
    NSLog(@"CDVSpeechKit.startTTS: Leaving method.");
}
@end
