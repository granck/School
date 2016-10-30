;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Adding 5 values and displaying them to screen
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

.data
values BYTE 1, 2, 3, 4, 5

.code
main PROC
   mov esi, OFFSET values 
   mov ecx, LENGTHOF values
   mov eax, 0

L1:
   add eax, [esi]
   INC esi
   loop L1
   exit
   
call DumpRegs

main ENDP
END main


