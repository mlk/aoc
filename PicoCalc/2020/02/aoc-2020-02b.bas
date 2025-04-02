Open "b:\input.txt" For input As #1
total = 0

Do
  Line Input #1, c$

  Rem Print c$
  dash = Instr(c$,"-")
  space = Instr(c$, " ")
  colon = Instr(c$, ":")
  p1 = Val(Mid$(c$, 1,dash - 1))
  endloc = space - dash
  p2 = Val(Mid$(c$, dash + 1,endloc))
  scan$ = Mid$(c$,colon - 1, 1)
  pss$ = Mid$(c$, colon + 2)
  v1$ = Mid$(pss$, p1, 1)
  v2$ = Mid$(pss$, p2, 1)
  Print v1$ + " " v2$
  If v1$ <> v2$ Then
    If v1$ = scan$ Or v2$ = scan$ Then
      total = total + 1
    End If
  End If
Loop Until Eof(#1)
Close #1
Print total
