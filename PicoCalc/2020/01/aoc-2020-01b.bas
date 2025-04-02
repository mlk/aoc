Open "b:\input.txt" For input As #1
Dim count = 0
Dim c(5000)
Do
  Line Input #1, a$
  c(count) = Val(a$)
  count = count + 1
Loop Until Eof(#1)
Close #1
Print count
Dim i As integer
Dim x As integer
Dim y As integer
For i = 0 To count - 1
  Rem Print c(i)
  For x = i To count - 1
    For y = x To count - 1
       If (c(i) + c(x) + c(y)) = 2020 Then
          v = (c(i) * c(x) * c(y))
          Print Format$(v, "%f")
       End If
    Next
  Next

Next
