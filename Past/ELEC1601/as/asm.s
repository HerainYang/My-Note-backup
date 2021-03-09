;;; Data definitions go here
.section .data
pair11:  .byte 12
pair12:  .byte 23
pair21:  .byte 55
pair22:  .byte 122
;;; Code definition goes here
.section .text
	.global asm_function

asm_function:
        lds r18, pair11
        lds r19, pair12
        lds r20, pair21
        lds r21, pair22
        cp r18, r20
        breq equal
        ldi r24, 1
        clr r25, 0

equal:
        cp r19, r21
        brne noequal
        clr r24
        clr r25
        ret
noequal:
       ldi r24, 1
       clr r25, 0
       ret
