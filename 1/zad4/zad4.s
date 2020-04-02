	.file	"zad4.cpp"
	.intel_syntax noprefix
	.text
	.section	.text$_ZN9CoolClass3setEi,"x"
	.linkonce discard
	.align 2
	.globl	__ZN9CoolClass3setEi
	.def	__ZN9CoolClass3setEi;	.scl	2;	.type	32;	.endef
__ZN9CoolClass3setEi:
LFB0:
	.cfi_startproc
	push	ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	mov	ebp, esp
	.cfi_def_cfa_register 5
	sub	esp, 4
	mov	DWORD PTR [ebp-4], ecx
	mov	eax, DWORD PTR [ebp-4]
	mov	edx, DWORD PTR [ebp+8]
	mov	DWORD PTR [eax+4], edx
	nop
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret	4
	.cfi_endproc
LFE0:
	.section	.text$_ZN9CoolClass3getEv,"x"
	.linkonce discard
	.align 2
	.globl	__ZN9CoolClass3getEv
	.def	__ZN9CoolClass3getEv;	.scl	2;	.type	32;	.endef
__ZN9CoolClass3getEv:
LFB1:
	.cfi_startproc
	push	ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	mov	ebp, esp
	.cfi_def_cfa_register 5
	sub	esp, 4
	mov	DWORD PTR [ebp-4], ecx
	mov	eax, DWORD PTR [ebp-4]
	mov	eax, DWORD PTR [eax+4]
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE1:
	.section	.text$_ZN13PlainOldClass3setEi,"x"
	.linkonce discard
	.align 2
	.globl	__ZN13PlainOldClass3setEi
	.def	__ZN13PlainOldClass3setEi;	.scl	2;	.type	32;	.endef
__ZN13PlainOldClass3setEi:
LFB2:
	.cfi_startproc
	push	ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	mov	ebp, esp
	.cfi_def_cfa_register 5
	sub	esp, 4
	mov	DWORD PTR [ebp-4], ecx
	mov	eax, DWORD PTR [ebp-4]
	mov	edx, DWORD PTR [ebp+8]
	mov	DWORD PTR [eax], edx
	nop
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret	4
	.cfi_endproc
LFE2:
	.section	.text$_ZN4BaseC2Ev,"x"
	.linkonce discard
	.align 2
	.globl	__ZN4BaseC2Ev
	.def	__ZN4BaseC2Ev;	.scl	2;	.type	32;	.endef
__ZN4BaseC2Ev:
LFB7:
	.cfi_startproc
	push	ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	mov	ebp, esp
	.cfi_def_cfa_register 5
	sub	esp, 4
	mov	DWORD PTR [ebp-4], ecx
	mov	edx, OFFSET FLAT:__ZTV4Base+8
	mov	eax, DWORD PTR [ebp-4]
	mov	DWORD PTR [eax], edx
	nop
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE7:
	.section	.text$_ZN9CoolClassC1Ev,"x"
	.linkonce discard
	.align 2
	.globl	__ZN9CoolClassC1Ev
	.def	__ZN9CoolClassC1Ev;	.scl	2;	.type	32;	.endef
__ZN9CoolClassC1Ev:		;_CoolClass::CoolClass()
LFB10:
	.cfi_startproc
	push	ebp 		;pushaj stack-frame pozivajuće fje(main)
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	mov	ebp, esp 		;omogućeno manipuliranje stogom za trenutni stack-frame
	.cfi_def_cfa_register 5
	sub	esp, 24
	mov	DWORD PTR [ebp-12], ecx ;u ecx se nalazi referenca na alocirani objekt, spremi ju na [ebp-12]
	mov	eax, DWORD PTR [ebp-12] ;postavi referencu objeka u eax
	mov	ecx, eax	;kopiraj u ecx jer se ecx šalje kao argument funkcije
	call	__ZN4BaseC2Ev	;poziv Base() konstruktora
	mov	edx, OFFSET FLAT:__ZTV9CoolClass+8 ;pokazivač na vtable CoolClass objekta postavi u edx
	mov	eax, DWORD PTR [ebp-12]	;referencu objekta postavi u eax
	mov	DWORD PTR [eax], edx	;pokazivač na vtable postavi na početak objekta i time završava konstrukcija objekta
	nop
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE10:
	.def	___main;	.scl	2;	.type	32;	.endef
	.text
	.globl	_main
	.def	_main;	.scl	2;	.type	32;	.endef
_main:
LFB4:
	.cfi_startproc
	lea	ecx, [esp+4]
	.cfi_def_cfa 1, 0
	and	esp, -16
	push	DWORD PTR [ecx-4]
	push	ebp
	.cfi_escape 0x10,0x5,0x2,0x75,0
	mov	ebp, esp
	push	ebx
	push	ecx
	.cfi_escape 0xf,0x3,0x75,0x78,0x6
	.cfi_escape 0x10,0x3,0x2,0x75,0x7c
	sub	esp, 32
	call	___main
	mov	DWORD PTR [esp], 8
	call	__Znwj
	mov	ebx, eax
	mov	ecx, ebx
	call	__ZN9CoolClassC1Ev
	mov	DWORD PTR [ebp-12], ebx
	lea	eax, [ebp-16]
	mov	DWORD PTR [esp], 42
	mov	ecx, eax
	call	__ZN13PlainOldClass3setEi
	sub	esp, 4
	mov	eax, DWORD PTR [ebp-12]
	mov	eax, DWORD PTR [eax]
	mov	edx, DWORD PTR [eax]
	mov	eax, DWORD PTR [ebp-12]
	mov	DWORD PTR [esp], 42
	mov	ecx, eax
	call	edx
	sub	esp, 4
	mov	eax, 0
	lea	esp, [ebp-8]
	pop	ecx
	.cfi_restore 1
	.cfi_def_cfa 1, 0
	pop	ebx
	.cfi_restore 3
	pop	ebp
	.cfi_restore 5
	lea	esp, [ecx-4]
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE4:
	.globl	__ZTV9CoolClass
	.section	.rdata$_ZTV9CoolClass,"dr"
	.linkonce same_size
	.align 4
__ZTV9CoolClass:
	.long	0
	.long	__ZTI9CoolClass
	.long	__ZN9CoolClass3setEi
	.long	__ZN9CoolClass3getEv
	.globl	__ZTV4Base
	.section	.rdata$_ZTV4Base,"dr"
	.linkonce same_size
	.align 4
__ZTV4Base:
	.long	0
	.long	__ZTI4Base
	.long	___cxa_pure_virtual
	.long	___cxa_pure_virtual
	.globl	__ZTI9CoolClass
	.section	.rdata$_ZTI9CoolClass,"dr"
	.linkonce same_size
	.align 4
__ZTI9CoolClass:
	.long	__ZTVN10__cxxabiv120__si_class_type_infoE+8
	.long	__ZTS9CoolClass
	.long	__ZTI4Base
	.globl	__ZTS9CoolClass
	.section	.rdata$_ZTS9CoolClass,"dr"
	.linkonce same_size
	.align 4
__ZTS9CoolClass:
	.ascii "9CoolClass\0"
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
	.def	__Znwj;	.scl	2;	.type	32;	.endef
	.def	___cxa_pure_virtual;	.scl	2;	.type	32;	.endef
