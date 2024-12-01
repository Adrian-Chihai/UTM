#ifndef SRV_SERIAL_STDIO
#define SRV_SERIAL_STDIO

void srv_serial_setup();
char srv_serial_get_char(void);
void srv_serial_put_char(char c);

#endif