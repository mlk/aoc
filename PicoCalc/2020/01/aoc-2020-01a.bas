Print "Hello"
Open "b:\input.txt" For input As #1
Dim count = 0
Dim content(5000)
Do
  Line Input #1, a$
  content(count) = Val(a$)
  count = count + 1
Loop Until Eof(#1)
Close #1
Print count
For i = 0 To count - 1
  Rem Print content(i)
  For x = i To count - 1
    If (content(i) + content(x)) = 2020 Then
       Print content(i) * content(x)
    End If
  Next

Next
