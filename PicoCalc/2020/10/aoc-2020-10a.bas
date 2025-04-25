Dim dat(500)

Sub sort_array a(), s
  Local cur, l, t
  For cur = 0 To s
    For l = 0 To s - 1
      If a(l) > a(l+1) Then
        t = a(l)
        a(l) = a(l+1)
        a(l+1) = t
      End If
    Next l
  Next cur
End Sub

Sub print_array a(), s
  Local idx
  For idx = 0 To s
    Print a(idx)
  Next
End Sub

Sub load_array f$, a(), rs
  Local cline$
  rs = - 1
  Open f$ For input As #1
  Do
    rs = rs + 1
    Line Input #1, cline$
    a(rs) = Val(cline$)
  Loop Until Eof(#1)
  Close #1
End Sub


Dim size
load_array "b:\input.txt", dat(), size

sort_array dat(), size

Dim i, cj
Dim jump(10)
For i = 1 To size
  cj = dat(i) - dat(i-1)
  jump(cj) = jump(cj) + 1
Next

Print (1+jump(1)) * (1+jump(3))
