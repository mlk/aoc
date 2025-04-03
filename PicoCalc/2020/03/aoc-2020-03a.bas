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

currentx = 0
currenty = 0
treesHit = 0
Do
  currentx = currentx + 3
  currenty = currenty + 1
  p$ = Str$(currentx)+"x"+Str$(currenty)
  loc = toloc(currentx, currenty)
  Seek #1, loc
  itm$ = Input$(1, #1)
  v$ = Str$(Asc(itm$))

  p$ = p$ + "[" + v$ + "]" + Str$(loc)

  Print p$

  If itm$ = "#" Then
    treesHit = treesHit +1
  End If
Loop Until Not (itm$ = "." Or itm$ ="#")

Print treesHit
Close #1
