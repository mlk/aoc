from lib.display import Display
from lib.hydra import popup
import sys

display = Display()
overlay = popup.UIOverlay()
demo = overlay.popup("HELLO")
l = []
r = []
with open("/sd/input.txt", 'r') as file_in:
	for line in file_in:
		parts = line.split()
		l.append(int(parts[0]))
		r.append(int(parts[1]))

total = 0

for t in l:
	total = total + (t * r.count(t))
	
overlay.popup(str(total))
