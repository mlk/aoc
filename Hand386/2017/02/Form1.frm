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
  Open "c:\aoc\2017\02\input.txt" For Input As #handle
  filetext = Input$(LOF(handle), handle)
  Close #handle

  Dim total As Long
  total = 0

  Dim lines() As String
  lines = Split(filetext, Chr$(10))
  For i = 0 To UBound(lines)
    Dim min As Long
    Dim max As Long
    min = 99999
    max = 0
    Dim item() As String
    item = Split(lines(i), Chr$(9))
    If UBound(item) > 0 Then
        For n = 0 To UBound(item)
            Dim v As Long
            v = val(item(n))
            If v > max Then
                max = v
            End If
            If v < min Then
                min = v
            End If
        Next n
        total = total + (max - min)
    End If
  Next i

  Print total
End Sub
