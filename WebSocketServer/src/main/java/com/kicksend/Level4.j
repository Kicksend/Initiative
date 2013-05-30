.version 49 0
.source Level4.java
.class super public Level4
.super Level


.method public <init> : ()V
	.limit stack 1
	.limit locals 1
	aload_0
	invokespecial Level <init> ()V
	return
.end method

.method  run : (ILBitBuffer;)I
	.limit stack 4
	.limit locals 8
	aload_2
	bipush 16
	invokevirtual BitBuffer readInt (I)I
	istore_3
	invokestatic java/lang/System currentTimeMillis ()J
	ldc2_w 10000L
	ldiv
	lstore 4
	iconst_0
	istore 6
L19:
	iload 6
	sipush 1337
	if_icmpge L52
	new java/util/Random
	dup
	lload 4
	invokespecial java/util/Random <init> (J)V
	astore 7
	aload 7
	invokevirtual java/util/Random nextInt ()I
	i2l
	lstore 4
	iinc 6 1
	goto L19
L52:
	lload 4
	bipush 16
	lshr
	iload_3
	i2l
	lcmp
	ifne L74
	ldc2_w 4.0
	ldc2_w 2.0
	invokestatic java/lang/Math pow (DD)D
	d2i
	ireturn
L74:
	iconst_0
	ireturn
.end method
