from lib.display import Display
from lib.hydra import popup
import sys

display = Display()
overlay = popup.UIOverlay()

overlay.popup("HI")

total = 0

def is_valid(p):
	inc = int(p[0]) < int(p[-1])
	prev = int(p[0])
	for i in p[1:]:
		n = int(i)
		if inc != (n > prev):
			return False
		dist = abs(n - prev)
		if dist < 1 or dist > 3:
			return False
		prev = n
	
	return True


with open("/sd/input.txt") as file_in:
	for line in file_in:
		#overlay.popup(line)
		p = line.split(' ')
		valid = is_valid(p)
		if not valid:
			for x in range(len(p)):
				b = p.copy()
				b.pop(x)
				if is_valid(b):
					valid = True
		
		if valid:
			total = total + 1

overlay.popup(str(total))

