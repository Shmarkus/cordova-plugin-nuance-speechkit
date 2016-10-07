//
//  CDVSpeechKit.m
//
//  Created by Adam on 10/3/12.
//  Updated by Markus Karileet 10/7/16
//
//

#import "CDVSpeechKit.h"
#import <SpeechKit/SpeechKit.h>

@implementation CDVSpeechKit
/*
 * Initializes speech kit
 */
- (void) initSpeechKit:(CDVInvokedUrlCommand*)command{
    NSLog(@"CDVSpeechKit.initSpeechKit: Entered method.");
    NSString *path = [[NSBundle mainBundle] pathForResource: @"CFBundleURLTypes" ofType: @"plist"];
    NSDictionary *dict = [NSDictionary dictionaryWithContentsOfFile: path];
    SKSession* session = [[SKSession alloc] initWithURL:[NSURL URLWithString:[dict objectForKey: @"sk_url"]] appToken:[dict objectForKey: @"sk_app_key"]];
    NSLog(@"CDVSpeechKit.initSpeechKit: Leaving method.");
}

/*
 * Start text to speech with the parameters passed
 */
- (void) startTTS:(CDVInvokedUrlCommand*)command{

    NSLog(@"CDVSpeechKit.startTTS: Entered method.");
    SKTransaction* transaction = [session speakString:[command.arguments objectAtIndex:0]
                                         withLanguage:[command.arguments objectAtIndex:1]
                                             delegate:self];
    NSLog(@"CDVSpeechKit.startTTS: Leaving method.");
}
@end
