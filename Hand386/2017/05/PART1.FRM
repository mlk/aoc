VERSION 5.00
Begin VB.Form Part1 
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
Attribute VB_Name = "Part1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub form_activate()
    Print "Hello"

    Dim handle As Integer
    handle = FreeFile
    Open "c:\aoc\2017\05\input.txt" For Input As #handle
    filetext = Input$(LOF(handle), handle)
    Close #handle
    
    Dim lines() As String
    lines = Split(filetext, Chr$(10))
    Dim program() As Integer
    ReDim program(UBound(lines) - 1)
    
    For i = 0 To UBound(lines) - 1
        program(i) = Val(lines(i))
    Next

    Dim pc As Integer
    Dim step As Long
    
    While pc >= 0 And pc <= UBound(program)
        Dim prev As Integer
        prev = pc
        pc = pc + program(pc)
        program(prev) = program(prev) + 1
        step = step + 1
    Wend
    Print step

End Sub


