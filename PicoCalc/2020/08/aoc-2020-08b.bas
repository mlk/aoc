Option explicit

Const acc = 1
Const jmp = 2
Const nop = 3
Const ext = 4

Dim op(623)
Dim org_op(623)
Dim arg(623)

Dim progLen = 0
Dim d$, m, cop$, carg

Sub clone_array s(), t()
  Local i
  For i = 0 To 623
    t(i) = s(i)
  Next
End Sub

Open "b:\input.txt" For input As #1
Do
  Line Input #1, d$
  m = Instr(d$, " ")
  cop$ = Mid$(d$, 1, m - 1)
  carg = Val(Mid$(d$, m))
  If cop$ = "acc" Then
    org_op(progLen) = acc
  Else If cop$ = "jmp" Then
    org_op(progLen) = jmp
  Else
    org_op(progLen) = nop
  End If
  arg(progLen) = carg
  progLen = progLen + 1
Loop Until Eof(#1)
Close #1

Dim cacc = 0
Dim pc = 0
Dim old_pc = 0
Dim chg

For chg = 0 To progLen
  cacc = 0
  pc = 0
  old_pc = 0
  clone_array org_op(), op()

  If op(chg) = jmp Then
    op(chg) = nop
  Else If op(chg) = nop Then
    op(chg) = jmp
  End If

  Do
    old_pc = pc
    If op(pc) = acc Then
      cacc = cacc + arg(pc)
      pc = pc + 1
    Else If op(pc) = jmp Then
      pc = pc + arg(pc)
    Else If op(pc) = nop Then
      pc = pc + 1
    End If
    op(old_pc) = ext
  Loop Until op(pc)=ext Or pc>=progLen


  If op(pc) <> ext Then
    Print cacc
    End
  End If
Next
