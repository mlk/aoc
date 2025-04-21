Dim content(1000)
Dim idx = 0
Dim d$

Open "b:\input.txt" For input As #1
Do
  Line Input #1, d$
  content(idx) = Val(d$)
  idx = idx + 1
Loop Until Eof(#1)
Close #1

Dim preamble = 25
Dim size = idx - 1

Function find(v, max)
  Local x, y
  find = 0
  For x = max - preamble To max
    For y = max - preamble To max
      cv = content(x) + content(y)
      If x <> y And cv = v Then
        find = 1
      End If
    Next
  Next
End Function

For idx = preamble +1 To size
  If find(content(idx), idx -1) = 0 Then
    Print idx content(idx)
    End
  End If
Next
