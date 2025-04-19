Open "b:\input.txt" For input As #1

Dim letter(26)

Sub reset()
  Local i
  For i = 0 To 26
    letter(i) = 0
  Next
End Sub

Function cnt()
  Local i, c
  For i = 0 To 26
    c = c + letter(i)
  Next
  cnt = c
End Function

total = 0

Do
  Line Input #1, d$
  If d$ = "" Then
    total = total + cnt()
    reset()
  End If
  For x = 1 To Len(d$)
    y$ = Mid$(d$, x, 1)
    letter(Asc(y$) - Asc("a")) = 1
  Next
Loop Until Eof(#1)

total = total+cnt()

Print total

Close #1
