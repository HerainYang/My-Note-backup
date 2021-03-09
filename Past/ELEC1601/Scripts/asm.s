;;; Data definitions go here
.section .data
data: .byte 15, 25, 36, 12, 50
result: .space 1, 0
count: .byte 0
;;; Code definition goes here
.section .text
.global asm_function
asm_function:
           LDI R28, lo8(data)
           LDI R29, hi8(data) ; In Y, wouldn't change in subroutine
loop_start:LDS R18, count
           CPI R18, 5
           BRSH done
           MOV R19, R18
           INC R19
           STS count, R19 ; count + 1
           LDS R19, result
           LD R20, Y+
           push R19
           push R20
           call my_function
           pop R0
           pop R0
           STS result, R24 ; Result is inside R25:R24, but 1 byte so just R24
           

           JMP loop_start
done:
           ret
ppp:
           CALL printf
           pop R0
           pop R0
           LDS R24, i
           INC R24
           STS i, R24
           JMP loop_start
.end
