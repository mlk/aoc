from lib.display import Display
from lib.hydra import popup
import sys

display = Display()
overlay = popup.UIOverlay()

overlay.popup("HI")

total = 0

with open("/sd/input.txt") as file_in:
	for line in file_in:
		p = line.split(' ')
		valid = True
		inc = int(p[0]) < int(p[-1])
		prev = int(p[0])
		for i in p[1:]:
			n = int(i)
			if inc != (n > prev):
				valid = False
			dist = abs(n - prev)
			if dist < 1 or dist > 3:
				valid = False
			prev = n
		
		if valid:
			total = total + 1

overlay.popup(str(total))
