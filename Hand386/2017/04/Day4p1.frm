VERSION 5.00
Begin VB.Form Part1 
   Caption         =   "Day4p1"
   ClientHeight    =   3195
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   4680
   LinkTopic       =   "Form1"
   ScaleHeight     =   3195
   ScaleWidth      =   4680
   StartUpPosition =   3  'Windows Default
End
Attribute VB_Name = "Part1"
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

Function validatePassword(pwd As String) As Boolean
    Dim chk As Collection
    
    Set chk = New Collection
    
    words = Split(pwd, " ")
    validatePassword = True
        
    On Error GoTo nuts
    For i = 0 To UBound(words)
    
        chk.Add False, words(i)
    Next
    
    GoTo theend
nuts:
    validatePassword = False
    
theend:
    
End Function

