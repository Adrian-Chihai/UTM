#include "lab_2_app.h"
#include "srv_serial_stdio/srv_serial_stdio.h"

#include <Arduino.h>
#include <stdio.h>

void lab_2_app_setup(){
    srv_serial_setup();
    printf("Hello World");
}

void lab_2_app_loop(){
    printf("Add a value: ");
    int val;
    
    scanf("%d", &val);
    printf("Entered - %d\n", val);
    delay(200);
}
