Function between$(t$, s$, e$)
  Local st, ed
  st = Instr(t$, s$)
  If Not st Then
    between$ = ""
    Exit Function
  End If
  st = st + Len(s$)
  Ed = Instr(st, t$, e$)
  If Not ed Then
    Ed = Len(t$) + 1
  End If
  between$ = Mid$(t$, st, ed - st)
End Function

Open "b:\input.txt" For input As #1

Dim passport(7)
Dim fields$(7)
fields$(0) = "byr"
fields$(1) = "iyr"
fields$(2) = "eyr"
fields$(3) = "hgt"
fields$(4) = "hcl"
fields$(5) = "ecl"
fields$(6) = "pid"

Sub datecheck(min,max)
  Local v
  If Len(u$) = 4 Then
    v = Val(u$)
    If v >= min And v <= max Then
      passport(checki) = 1
    End If
  End If
End Sub

Sub byr
  datecheck(1920, 2002)
End Sub
Sub iyr
  datecheck(2010, 2020)
End Sub
Sub eyr
  datecheck(2020, 2030)
End Sub
Sub hgt
  Local l, v
  l = Len(u$)
  t$ = Mid$(u$, l - 1)
  v = Val(Mid$(u$, 1, l-2))
  If t$="cm" And v>=150 And v<=193 Then
    passport(checki) = 1
  End If
  If t$="in" And v>=59 And v<=76 Then
    passport(checki) = 1
  End If
End Sub
Sub hcl
  Local l
  l = Len(u$)
  If l <> 7 Then
    Return
  End If

  If Val(("&H" + Mid$(u$, 2))) Then
    passport(checki) = 1
  End If
End Sub
Sub ecl
  If u$ = "amb" Or u$ = "blu" Then
    passport(checki) = 1
  End If
  If u$ = "brn" Or u$ = "gry" Then
    passport(checki) = 1
  End If
  If u$ = "hzl" Or u$ = "oth" Then
    passport(checki) = 1
  End If
  If u$ = "grn" Then
    passport(checki) = 1
  End If

End Sub
Sub pid
  Local l
  l = Len(u$)
  If l = 9 And Val(u$) <> 0  Then
    passport(checki) = 1
  End If
End Sub

Sub check(l$)
  For checki = 0 To 6
    n$ = fields$(checki) + ":"
    If Instr(l$, n$) Then
       u$=between$(l$,n$," ")
       Execute fields$(checki)
       Rem If passport(checki) <> 1 Then
         Rem Print fields$(checki)
         Rem Print u$
       Rem End If
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
