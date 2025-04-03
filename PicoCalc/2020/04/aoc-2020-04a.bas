Open "b:\input.txt" For input As #1

Dim passport(7)
Dim fields$(7)
fields$(0) = "byr:"
fields$(1) = "iyr:"
fields$(2) = "eyr:"
fields$(3) = "hgt:"
fields$(4) = "hcl:"
fields$(5) = "ecl:"
fields$(6) = "pid:"

Sub check(l$)
  Local i
  For i = 0 To 6
    If Instr(l$, fields$(i)) Then
       passport(i) = 1
    End If
  Next
End Sub

Sub reset()
  Local i
  For i = 0 To 6
   passport(i) = 0
  Next
End Sub

Function validPassport()
  Local i
  For i = 0 To 6
    If passport(i) = 0 Then
      validPassport = 0
      Exit Function
    End If
  Next
  validPassport = 1
End Function


reset()

v = 0

Do
  Line Input #1, cl$
  check(cl$)
  If Len(cl$) = 0 Then
    If validPassport() Then
      v = v + 1
    End If
    reset()
  End If
Loop Until Eof(#1)

If validPassport() Then
  v = v + 1
End If
Print v

Close #1
