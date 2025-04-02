Open "b:\input.txt" For input As #1
total = 0

Function count(str$, chr$)
  Local c = 0
  Local i
  For i = 1 To Len(str$)
    If Mid$(str$, i, 1) = chr$ Then
      c = c + 1
    End If
  Next
  count = c
End Function

Do
  Line Input #1, c$

  Rem Print c$
  dash = Instr(c$,"-")
  space = Instr(c$, " ")
  colon = Instr(c$, ":")
  min = Val(Mid$(c$, 1,dash - 1))
  Rem Print min
  Endloc = space - dash
  max = Val(Mid$(c$, dash + 1,endloc))
  scan$ = Mid$(c$,colon - 1, 1)
  pss$ = Mid$(c$, colon + 2)
  Rem Print max
  Rem Print scan$
  Rem Print pss$
  cur = count(pss$, scan$)
  If cur >= min And cur <= max Then
    total = total + 1
  End If
Loop Until Eof(#1)
Close #1
Print total
