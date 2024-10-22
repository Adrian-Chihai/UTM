#include "srv_serial_stdio.h"

#include<Arduino.h>

FILE* srv_serial_stdio_stream;

void srv_serial_setup() {
  Serial.begin(9600);
  //pentru esp32 nu e nevoie de asta
  srv_serial_stdio_stream = fdevopen(srv_serial_put_char, srv_serial_get_char);
  stdin = stdout = stderr = srv_serial_stdio_stream;

}


char srv_serial_get_char(void) {
    char c;

    while (!Serial.available());

    c = Serial.read();

    return c;
}

void srv_serial_put_char(char c) {
    Serial.write(c);
}