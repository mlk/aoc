VERSION 5.00
Begin VB.Form form1 
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
Attribute VB_Name = "form1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub Form_Activate()
  Dim handle As Integer
  handle = FreeFile
  Open "c:\aoc\2017\01\input.txt" For Input As #handle
  filetext = Input$(LOF(handle), handle)
  Close #handle
  
  Print "processing"
  
  Dim c As Long
  c = 0
  If Mid(filetext, 1, 1) = Mid(filetext, Len(filetext), 1) Then
    c = Val(Mid(filetext, 1, 1))
  End If
  For i = 1 To Len(filetext) - 1
    If Mid(filetext, i, 1) = Mid(filetext, i + 1, 1) Then
        c = c + Val(Mid(filetext, i, 1))
    End If
    
  Next
  Print c
  
  c = 0
  Dim half As Integer
  half = Len(filetext) / 2
  For i = 1 To Len(filetext)
    Dim cur
    cur = ((i + half))
    If cur > Len(filetext) Then
        cur = cur - Len(filetext)
    End If
    
    If Mid(filetext, i, 1) = Mid(filetext, cur, 1) Then
        c = c + Val(Mid(filetext, i, 1))
    End If
  
  Next
  
  Print c
    
End Sub
