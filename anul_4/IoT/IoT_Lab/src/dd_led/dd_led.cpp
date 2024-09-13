#include "dd_led.h"
#include "Arduino.h"

void dd_led_setup()
{
    pinMode(DD_LED_PIN, OUTPUT);
}
void dd_led_turn_on()
{
    digitalWrite(DD_LED_PIN, HIGH);
}
void dd_led_turn_off()
{
    digitalWrite(DD_LED_PIN, LOW);
}
void dd_led_is_on()
{
    return (digitalRead(DD_LED_PIN)==HIGH);
}
void dd_led_toggle()
{
    if(dd_led_is_on()) {
        dd_led_turn_off();
    } else {
        dd_led_turn_on();
    }
}