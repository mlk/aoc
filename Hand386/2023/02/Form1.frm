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
    maxRed = 12
    maxGreen = 13
    maxBlue = 14
    
    Dim lines() As String
    lines = Split(filetext, Chr$(10))
    total = 0
    For n = 0 To UBound(lines)
        If Len(lines(n)) = 0 Then
            Print "boop"
            Exit For
        End If
        lineRed = 0
        lineGreen = 0
        lineBlue = 0
        
        Dim parts() As String
        parts = Split(lines(n), ":")
        Dim pulls() As String
        pulls = Split(parts(1), ";")
        For pidx = 0 To UBound(pulls)
            Dim colours() As String
            colours = Split(pulls(pidx), ",")
            For cidx = 0 To UBound(colours)
                c = Right$(colours(cidx), 1)
                num = Val(colours(cidx))
                If c = "d" And num > lineRed Then
                    lineRed = num
                ElseIf c = "n" And num > lineGreen Then
                    lineGreen = num
                ElseIf c = "e" And num > lineBlue Then
                    lineBlue = num
                End If
            Next cidx
        Next pidx
        If lineRed <= maxRed And lineGreen <= maxGreen And lineBlue <= maxBlue Then
            Rem Print parts(0) & " " & lineRed & " " & lineGreen & " " & lineBlue
            Dim gameNum() As String
            gameNum = Split(parts(0), " ")
            total = total + Val(gameNum(1))
        End If
    Next n
    Print total
    
End Sub
