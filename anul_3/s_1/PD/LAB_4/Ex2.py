import math
import random
import time

print("EX: 2")

nr = random.randint(0, 10000)
print(f"nr generat -> {nr}")
timeConvertedLib = time.strftime("%H:%M:%S", time.gmtime(nr))
print("library method -> ", timeConvertedLib)

minutes = nr / 60
if minutes >= 60:
    hours = math.floor(minutes / 60)
minutes = math.floor(minutes - hours * 60)
seconds = nr - (((hours * 60) * 60) + (minutes * 60))

time_formatted = f"{hours:02d}:{minutes:02d}:{seconds:02d}"
print(f"manual -> {time_formatted}")



