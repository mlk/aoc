# This does not work on the device.
# I think because it does not have enough memory to store the list.
# I need to switch to a boolean array or something.

from lib import st7789py, keyboard
from machine import Pin, SPI
import time
from font import vga1_8x16 as font

tft = st7789py.ST7789(
	SPI(1, baudrate=40000000, sck=Pin(36), mosi=Pin(35), miso=None),
	135,
	240,
	reset=Pin(33, Pin.OUT),
	cs=Pin(37, Pin.OUT),
	dc=Pin(34, Pin.OUT),
	backlight=None,
	rotation=1,
	color_order=st7789py.BGR
	)

def main():
	f=open('/sd/input.txt')
	data=f.read()
	f.close()
	v=data.split()
	value=0
	prev = []
	try:
		while True:
			for i in v:
				value+=int(i)
				if value in prev:
					raise Exception("")
				else:
					prev.append(value)
	except Exception:
		tft.fill(25)
		tft.text(font, str(value), 80, 80, 0, 25)
		time.sleep_ms(5000)

main()
