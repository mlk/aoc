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
    total = 50
    cnt = 0
    For n = 0 To UBound(lines)
        Rem Print lines(n)
        d = Mid$(lines(n), 1, 1)
        a = Val(Mid$(lines(n), 2))
        For i = 1 To a
            If d = "L" Then
                total = total - 1
            End If
            If d = "R" Then
                total = total + 1
            End If
            If total = -1 Then
                total = 99
            End If
            If total = 100 Then
                total = 0
            End If
        Next i
        If total = 0 Then
            cnt = cnt + 1
        End If
    Next n
    Print cnt
End Sub


