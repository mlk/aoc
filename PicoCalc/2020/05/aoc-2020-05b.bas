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

Dim s(944)

Do
  Line Input #1, in$
  sn = seatNo(in$)
  s(sn) = 1
Loop Until Eof(#1)

For i = 1 To 943
  If s(i-1)=1 And s(i+1)=1 Then
    If s(i) = 0 Then
      Print i
    End If
  End If
Next
