VERSION 5.00
Begin VB.Form Form2 
   Caption         =   "Form2"
   ClientHeight    =   3195
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   4680
   LinkTopic       =   "Form2"
   ScaleHeight     =   3195
   ScaleWidth      =   4680
   StartUpPosition =   3  'Windows Default
End
Attribute VB_Name = "Form2"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Dim numbers(10) As String

Private Sub form_activate()
    numbers(1) = "one"
    numbers(2) = "two"
    numbers(3) = "three"
    numbers(4) = "four"
    numbers(5) = "five"
    numbers(6) = "six"
    numbers(7) = "seven"
    numbers(8) = "eight"
    numbers(9) = "nine"
    
    Dim handle As Integer
    handle = FreeFile
    Open "d:\input.txt" For Input As #handle
    filetext = Input$(LOF(handle), handle)
    Close handle
        
    
    Dim lines() As String
    lines = Split(filetext, Chr$(10))
    total = 0
    Dim i As Integer
    
    For n = 0 To UBound(lines)
        If Len(lines(n)) = 0 Then
            Print "boop"
            Exit For
        End If
        f = "0"
        For i = 1 To Len(lines(n))
            c = numberaAt(lines(n), i)
            If Len(c) > 0 Then
                f = c
                Exit For
            End If
        Next i
        s = "0"
        For i = Len(lines(n)) To 0 Step -1
            c = numberaAt(lines(n), i)
            
            If Len(c) > 0 Then
                s = c
                Exit For
            End If
        Next i
        If s = "0" Then
            Print "s>" & lines(n)
        End If
        If f = "0" Then
            Print "f>" & lines(n)
        End If
        total = total + (Val(f & s))
    Next n
    Print total
    
End Sub

Function numberaAt(line As String, i As Integer) As String
    If IsNumeric(Mid(line, i, 1)) Then
        numberaAt = Mid(line, i, 1)
        Exit Function
    End If
    For num = 1 To 9
        cur = numbers(num)
        If Mid(line, i, Len(cur)) = cur Then
            numberaAt = CStr(num)
            Exit Function
        End If
    Next
    numberaAt = ""
End Function



