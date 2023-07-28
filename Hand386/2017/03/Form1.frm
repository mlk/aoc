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
Private Sub Form_Activate()
    Dim after As Long
    after = 1024
    Dim ringConter As Integer
    ringCounter = 1
    Dim prev As Long
    prev = 1
    Dim i As Long
    For i = 3 To 1024 Step 2
        Dim corner As Long
        corner = i * i
        
        If after <= corner Then
            Dim x As Long
            For x = corner - (i - 1) To (prev * prev) Step -(i - 1)
                If after >= x Then
                    midpoint = (Int(i / 2) + 1)
                    loconline = (after - (x - 1))
                    temp = Abs(loconline - midpoint)
                    Print temp; "x"; ringCounter
                    Print (ringCounter) + temp
                    Exit For
                End If
            Next
            Exit For
        End If
        prev = i
        ringCounter = ringCounter + 1
    Next
End Sub

