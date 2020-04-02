	.file	"zad6.cpp"
	.intel_syntax noprefix
	.text
	.section	.text$_ZN4BaseC2Ev,"x"
	.linkonce discard
	.align 2
	.globl	__ZN4BaseC2Ev
	.def	__ZN4BaseC2Ev;	.scl	2;	.type	32;	.endef
__ZN4BaseC2Ev: 		; _Base::Base()
LFB16:
	.cfi_startproc
	push	ebp 	;spremi stack-frame pozivajuće funkcije
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	mov	ebp, esp 	;slobodna manipulacija stogom
	.cfi_def_cfa_register 5
	sub	esp, 24		;alociranje 24 bajta
	mov	DWORD PTR [ebp-12], ecx 	;spremanje reference objekta na [ebp-12]
	mov	edx, OFFSET FLAT:__ZTV4Base+8	;edx pokazuje na vtable Base-a
	mov	eax, DWORD PTR [ebp-12]		;referenca objekta u eax
	mov	DWORD PTR [eax], edx	;postavi pointer vtable na prvo mjesto
	mov	eax, DWORD PTR [ebp-12]		;obnovi eax referencu
	mov	ecx, eax	;spremi referencu objekta u ecx koji se šalje u _Base::metoda()
	call	__ZN4Base6metodaEv		;pozovi _Base::metoda()
	nop
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE16:
	.section .rdata,"dr"
LC0:
	.ascii "ja sam bazna implementacija!\0"
	.section	.text$_ZN4Base15virtualnaMetodaEv,"x"
	.linkonce discard
	.align 2
	.globl	__ZN4Base15virtualnaMetodaEv
	.def	__ZN4Base15virtualnaMetodaEv;	.scl	2;	.type	32;	.endef
__ZN4Base15virtualnaMetodaEv:		;_Base::virtualnaMetoda()
LFB18:
	.cfi_startproc
	push	ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	mov	ebp, esp
	.cfi_def_cfa_register 5
	sub	esp, 40
	mov	DWORD PTR [ebp-12], ecx
	mov	DWORD PTR [esp], OFFSET FLAT:LC0 ;"ja sam bazna implementacija"
	call	_puts		;printaj "ja sam bazna implementacija!"
	nop
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret 				;vrati se u Derived konstruktor, __ZN7DerivedC1Ev
	.cfi_endproc
LFE18:
	.section .rdata,"dr"
LC1:
	.ascii "Metoda kaze: \0"
	.section	.text$_ZN4Base6metodaEv,"x"
	.linkonce discard
	.align 2
	.globl	__ZN4Base6metodaEv
	.def	__ZN4Base6metodaEv;	.scl	2;	.type	32;	.endef
__ZN4Base6metodaEv:	;_Base::metoda()
LFB19:
	.cfi_startproc
	push	ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	mov	ebp, esp
	.cfi_def_cfa_register 5
	sub	esp, 40		;alociraj 40 bajtova
	mov	DWORD PTR [ebp-12], ecx 	;spremi referencu objekta
	mov	DWORD PTR [esp], OFFSET FLAT:LC1 	;"Metoda kaze: \0"
	call	_printf		;pozovi printf metodu
	mov	eax, DWORD PTR [ebp-12]		;u eax referenca na objekt
	mov	eax, DWORD PTR [eax]		;u eax pointer na vtable
	mov	edx, DWORD PTR [eax]		;u edx adresa virtualnaMetoda()
	mov	eax, DWORD PTR [ebp-12]		;u eax referenca na objekt
	mov	ecx, eax					;spremi referencu na objekt u ecx
	call	edx						;pozovi virtualnu metodu __ZN4Base15virtualnaMetodaEv ili __ZN7Derived15virtualnaMetodaEv
	nop
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE19:
	.section	.text$_ZN7DerivedC1Ev,"x"
	.linkonce discard
	.align 2
	.globl	__ZN7DerivedC1Ev
	.def	__ZN7DerivedC1Ev;	.scl	2;	.type	32;	.endef
__ZN7DerivedC1Ev:	;Derived kontruktor
LFB22:
	.cfi_startproc
	push	ebp 	;spremi stack frame pozivajuće funkcije
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	mov	ebp, esp 	;slobodna manipulacija stogom
	.cfi_def_cfa_register 5
	sub	esp, 24		;alociraj 24 bajta
	mov	DWORD PTR [ebp-12], ecx 	;spremi instancu objekta na [ebp-12], ecx je ulazni parametar funkcije
	mov	eax, DWORD PTR [ebp-12]		;u eax kopiraj referencu objekta
	mov	ecx, eax
	call	__ZN4BaseC2Ev	;poziv baznog konstruktora
	mov	edx, OFFSET FLAT:__ZTV7Derived+8 	;pokazivač na vtable iz razreda Derived
	mov	eax, DWORD PTR [ebp-12]		;referenca na objekt
	mov	DWORD PTR [eax], edx		;postavi vtable na početak objekta
	mov	eax, DWORD PTR [ebp-12]		;referenca na objekt
	mov	ecx, eax					;referenca na objekt
	call	__ZN4Base6metodaEv		;poziv metoda()
	nop
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE22:
	.section .rdata,"dr"
	.align 4
LC2:
	.ascii "ja sam izvedena implementacija!\0"
	.section	.text$_ZN7Derived15virtualnaMetodaEv,"x"
	.linkonce discard
	.align 2
	.globl	__ZN7Derived15virtualnaMetodaEv
	.def	__ZN7Derived15virtualnaMetodaEv;	.scl	2;	.type	32;	.endef
__ZN7Derived15virtualnaMetodaEv:
LFB23:
	.cfi_startproc
	push	ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	mov	ebp, esp
	.cfi_def_cfa_register 5
	sub	esp, 40
	mov	DWORD PTR [ebp-12], ecx
	mov	DWORD PTR [esp], OFFSET FLAT:LC2 	;"ja sam izvedena implementacija"
	call	_puts 	 ;printaj "ja sam izvedena implementacija"
	nop
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE23:
	.def	___main;	.scl	2;	.type	32;	.endef
	.text
	.globl	_main
	.def	_main;	.scl	2;	.type	32;	.endef
_main:
LFB24:
	.cfi_startproc
	.cfi_personality 0,___gxx_personality_v0
	.cfi_lsda 0,LLSDA24
	push	ebp     ;spremi stack-frame pozivne fje
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	mov	ebp, esp	;slobodno možemo manipulirati esp-om
	.cfi_def_cfa_register 5
	push	esi
	push	ebx
	and	esp, -16
	sub	esp, 32
	.cfi_offset 6, -12
	.cfi_offset 3, -16
	call	___main
	mov	DWORD PTR [esp], 4
LEHB0:
	call	__Znwj	;pozovi _operator new(unsigned int), eax -> alocirani objekt
LEHE0:
	mov	ebx, eax	;spremi pointer na alocirani objekt u ebx
	mov	ecx, ebx	;spremi ebx u ecx
LEHB1:
	call	__ZN7DerivedC1Ev	;poziv Derived konstruktora
LEHE1:
	mov	DWORD PTR [esp+28], ebx
	mov	eax, DWORD PTR [esp+28]
	mov	ecx, eax
LEHB2:
	call	__ZN4Base6metodaEv
	mov	eax, 0
	jmp	L10
L9:
	mov	esi, eax
	mov	DWORD PTR [esp+4], 4
	mov	DWORD PTR [esp], ebx
	call	__ZdlPvj 	;poziv _operator delete(void*, unsigned int)
	mov	eax, esi
	mov	DWORD PTR [esp], eax
	call	__Unwind_Resume
LEHE2:
L10: 		;resetiraj registre
	lea	esp, [ebp-8]
	pop	ebx
	.cfi_restore 3
	pop	esi
	.cfi_restore 6
	pop	ebp
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE24:
	.def	___gxx_personality_v0;	.scl	2;	.type	32;	.endef
	.section	.gcc_except_table,"w"
LLSDA24:
	.byte	0xff
	.byte	0xff
	.byte	0x1
	.uleb128 LLSDACSE24-LLSDACSB24
LLSDACSB24:
	.uleb128 LEHB0-LFB24
	.uleb128 LEHE0-LEHB0
	.uleb128 0
	.uleb128 0
	.uleb128 LEHB1-LFB24
	.uleb128 LEHE1-LEHB1
	.uleb128 L9-LFB24
	.uleb128 0
	.uleb128 LEHB2-LFB24
	.uleb128 LEHE2-LEHB2
	.uleb128 0
	.uleb128 0
LLSDACSE24:
	.text
	.globl	__ZTV7Derived
	.section	.rdata$_ZTV7Derived,"dr"
	.linkonce same_size
	.align 4
__ZTV7Derived:
	.long	0
	.long	__ZTI7Derived
	.long	__ZN7Derived15virtualnaMetodaEv
	.globl	__ZTV4Base
	.section	.rdata$_ZTV4Base,"dr"
	.linkonce same_size
	.align 4
__ZTV4Base:
	.long	0
	.long	__ZTI4Base
	.long	__ZN4Base15virtualnaMetodaEv
	.globl	__ZTI7Derived
	.section	.rdata$_ZTI7Derived,"dr"
	.linkonce same_size
	.align 4
__ZTI7Derived:
	.long	__ZTVN10__cxxabiv120__si_class_type_infoE+8
	.long	__ZTS7Derived
	.long	__ZTI4Base
	.globl	__ZTS7Derived
	.section	.rdata$_ZTS7Derived,"dr"
	.linkonce same_size
	.align 4
__ZTS7Derived:
	.ascii "7Derived\0"
	.globl	__ZTI4Base
	.section	.rdata$_ZTI4Base,"dr"
	.linkonce same_size
	.align 4
__ZTI4Base:
	.long	__ZTVN10__cxxabiv117__class_type_infoE+8
	.long	__ZTS4Base
	.globl	__ZTS4Base
	.section	.rdata$_ZTS4Base,"dr"
	.linkonce same_size
	.align 4
__ZTS4Base:
	.ascii "4Base\0"
	.ident	"GCC: (MinGW.org GCC Build-20200227-1) 9.2.0"
	.def	_puts;	.scl	2;	.type	32;	.endef
	.def	_printf;	.scl	2;	.type	32;	.endef
	.def	__Znwj;	.scl	2;	.type	32;	.endef
	.def	__ZdlPvj;	.scl	2;	.type	32;	.endef
	.def	__Unwind_Resume;	.scl	2;	.type	32;	.endef
