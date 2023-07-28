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
Dim mya(1001, 1001) As Long
Dim target As Long

Private Sub Form_Activate()
    Print "hello"
    target = 289326
    mya(500, 500) = 1
    Dim x As Integer
    Dim y As Integer
    x = 500
    y = 501
    mya(x, y) = acount(x, y)
    
    Dim square As Long
    square = 3
    For ring = 1 To 250
        Rem Print "Up"
        For li = 1 To square - 2
            x = x + 1
            mya(x, y) = acount(x, y)
        If mya(x, y) = 0 Then
            Exit Sub
        End If
        
        Next
        Rem Print "Left"
        For li = 1 To square - 1
            y = y - 1
            mya(x, y) = acount(x, y)
        If mya(x, y) = 0 Then
            Exit Sub
        End If
        
        Next
        Rem Print "down"
        For li = 1 To square - 1
            x = x - 1
            mya(x, y) = acount(x, y)
        If mya(x, y) = 0 Then
            Exit Sub
        End If
        
        Next
        Rem Print "right"
        For li = 1 To square
            y = y + 1
            mya(x, y) = acount(x, y)
        If mya(x, y) = 0 Then
            Exit Sub
        End If
        
        Next
        
        square = square + 2
    Next
End Sub


Function acount(x As Integer, y As Integer) As Long
        
    For xi = x - 1 To x + 1
        For yi = y - 1 To y + 1
            acount = acount + mya(xi, yi)
        Next yi
    Next xi
    Rem Print x; "x"; y; "="; acount
    If acount > target Then
        Print x; "x"; y; "="; acount
        acount = 0
    End If
End Function

