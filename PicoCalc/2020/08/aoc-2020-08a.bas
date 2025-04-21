Option explicit

Const acc = 1
Const jmp = 2
Const nop = 3
Const ext = 4

Dim op(623)
Dim arg(623)

Dim progLen = 0
Dim d$, m, cop$, carg

Open "b:\input.txt" For input As #1
Do
  Line Input #1, d$
  m = Instr(d$, " ")
  cop$ = Mid$(d$, 1, m - 1)
  carg = Val(Mid$(d$, m))
  If cop$ = "acc" Then
    op(progLen) = acc
  Else If cop$ = "jmp" Then
    op(progLen) = jmp
  Else
    op(progLen) = nop
  End If
  arg(progLen) = carg
  progLen = progLen + 1
Loop Until Eof(#1)
Close #1

Dim cacc = 0
Dim pc = 0
Dim old_pc = 0
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
Loop Until op(pc) = ext

Print cacc
