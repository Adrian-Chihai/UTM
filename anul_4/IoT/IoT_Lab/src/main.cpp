#include <Arduino.h>

#include "lab_1_app/lab_1_app.h"
#include "lab_2_app/lab_2_app.h"

// #define APP_NAME LAB_1

#define APP_NAME LAB_2

void setup()
{
// put your setup code here, to run once:
#if APP_NAME == LAB_1
  lab_1_app_setup();
#endif

#if APP_NAME == LAB_2
  lab_2_app_setup();
#endif
}

void loop()
{
// put your main code here, to run repeatedly:F
#if APP_NAME == LAB_1
  lab_1_app_loop();
#endif

#if APP_NAME == LAB_2
  lab_2_app_loop();
#endif
}
