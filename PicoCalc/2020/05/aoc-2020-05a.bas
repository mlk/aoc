Function seat(min,max,mins$,maxs$, s$)
  Local mid
  For i = 1 To Len(s$)
    mid = min + ((max-min+1)/2)
    c$ = Mid$(s$, i, 1)
    If c$ = mins$ Then
      max = mid -1
    End If
    If c$ = maxs$ Then
      min = mid
    End If
  Next i
  seat = min
End Function

Function seatNo(st$)
  r = seat(0, 127, "F", "B", st$)
  col = seat(0, 7, "L", "R", st$)
  seatNo = r * 8 + col
End Function

Open "b:\input.txt" For input As #1

big = -1

Do
  Line Input #1, in$
  sn = seatNo(in$)
  If sn > big Then
    big = sn
  End If
Loop Until Eof(#1)

Print big
