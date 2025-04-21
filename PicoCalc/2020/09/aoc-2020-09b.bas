Dim content(1000)
Dim idx = 0
Dim d$
Rem This is from part one.
Dim target_line = 500
Dim target_value
Open "b:\input.txt" For input As #1
Do
  Line Input #1, d$
  content(idx) = Val(d$)
  idx = idx + 1
Loop Until Eof(#1)
Close #1
target_value = content(target_line)

Dim preamble = 25
Dim size = idx - 1

Function find(v, s)
  Local x, y, total, sm, la
  find = 0
  total = content(s)
  sm = total
  la = total

  For x = s + 1 To size
      If content(x) < sm Then
       sm = content(x)
      End If
      If content(x) > la Then
        la = content(x)
      End If
      total = content(x) + total
      If total = v Then
        Print s sm la x
        find = sm + la
        Exit Function
      Else If total > v Then
        find = 0
        Exit Function
      End If
  Next
End Function

For idx = 0 To size
  t = find(target_value, idx)
  If t > 1 Then
    Print Format$(t, "%9.1f")
    End
  End If
Next
