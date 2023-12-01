VERSION 5.00
Begin VB.Form Form1 
   Caption         =   "Form1"
   ClientHeight    =   3195
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   4680
   LinkTopic       =   "Form1"
   ScaleHeight     =   3195
   ScaleWidth      =   4680
   StartUpPosition =   3  'Windows Default
End
Attribute VB_Name = "Form1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub form_activate()
    Dim handle As Integer
    handle = FreeFile
    Open "d:\input.txt" For Input As #handle
    filetext = Input$(LOF(handle), handle)
    Close handle
    
    Dim lines() As String
    lines = Split(filetext, Chr$(10))
    total = 0
    For n = 0 To UBound(lines)
        If Len(lines(n)) = 0 Then
            Print "boop"
            Exit For
        End If
        f = "0"
        For i = 1 To Len(lines(n))
            If IsNumeric(Mid(lines(n), i, 1)) Then
                f = Mid(lines(n), i, 1)
                Exit For
            End If
        Next i
        s = "0"
        For i = Len(lines(n)) To 0 Step -1
            If IsNumeric(Mid(lines(n), i, 1)) Then
                s = Mid(lines(n), i, 1)
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
