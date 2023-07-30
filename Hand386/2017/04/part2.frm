VERSION 5.00
Begin VB.Form part2 
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
Attribute VB_Name = "part2"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub Form_Activate()
    Dim handle As Integer
    handle = FreeFile
    Open "c:\aoc\2017\04\input.txt" For Input As #handle
    filetext = Input$(LOF(handle), handle)
    Close #handle
        
    Dim cnt As Integer
    cnt = 0
    Dim lines() As String
    lines = Split(filetext, Chr$(10))
    For i = 0 To UBound(lines) - 1
        If validatePassword(lines(i)) Then
            cnt = cnt + 1
        End If
    Next
    Print "woot"
    Print cnt
End Sub

Function sort(data As String) As String

    Dim c(26) As Integer
    
    For i = 1 To Len(data)
        Dim idx As Integer
        
        idx = Asc(Mid(data, i, 1)) - 97
        c(idx) = c(idx) + 1
    Next
    
    For i = 0 To 26
        sort = sort + String(c(i), 97 + i)
    Next
End Function


Function validatePassword(pwd As String) As Boolean
    Dim chk As Collection
    
    Set chk = New Collection
    Dim words() As String
    
    words = Split(pwd, " ")
    validatePassword = True
        
    On Error GoTo nuts
    For i = 0 To UBound(words)
    
        chk.Add False, sort(words(i))
    Next
    
    GoTo theend
nuts:
    validatePassword = False
    
theend:
    
End Function


