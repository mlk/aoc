Open "b:\input.txt" For random As #1
Print Lof(#1)
lineLength = 0
For lineLength = 1 To Lof(#1)
  Seek #1, lineLength
  chr$ = Input$(1, #1)
  If chr$ = Chr$(13) Then
    Exit For
  End If
Next

Function toloc(x,y)
  Local loc
  loc = x Mod (lineLength-1)
  loc = loc + (y * (lineLength + 1))
  loc = loc + 1
  toloc = loc
End Function

Function tryslope(dx, dy)
  Local currentx = 0
  Local currenty = 0
  Local treesHit = 0
  Do
    currentx = currentx + dx
    currenty = currenty + dy
    Rem p$ = Str$(currentx)+"x"+Str$(currenty)
    loc = toloc(currentx, currenty)
    Seek #1, loc
    itm$ = Input$(1, #1)
    Rem v$ = Str$(Asc(itm$))

    Rem p$ = p$ + "[" + v$ + "]" + Str$(loc)

    Rem Print p$

    If itm$ = "#" Then
      treesHit = treesHit +1
    End If
  Loop Until Not (itm$ = "." Or itm$ ="#")

  tryslope = treesHit
End Function

s1 = tryslope(1,1)
s2 = tryslope(3,1)
s3 = tryslope(5,1)
s4 = tryslope(7,1)
s5 = tryslope(1,2)
Print s1
Print s2
Print s3
Print s4
Print s5
Print Format$(s1*s2*s3*s4*s5, "%f")


Close #1
