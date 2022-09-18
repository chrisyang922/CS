#include <stdio.h>

unsigned replace_byte(unsigned x, int i, unsigned char b)
{
	//In order to make the bytes of x that will take the value of b,
	//we need to set those places of x that b will come to be all 0s
	//and or it with the values of b 

	//Therefore, we first create a max number of 11111111 into hexcode 
	//#which is 0xFF
	unsigned int makeZero = 0xFF;

	//Then we shift it to the right by i * 8 since it is converting from
	//bytecode to hex code.
	//Since it is a left shift, there will be 0s following the 1s
	makeZero = makeZero << i * 8;

	//We have to negate them to make the values where b will go in to be 0
	//and rest to be 1 so that we can preserve all the remaining values of x
	//while copy value the values of b into a given spot by using or.
	makeZero = ~makeZero;

	//Then we right shift b by a factor of 8 and i for similar bytecode 
	//conversion to hexcode as we done before. Since it is right shift,
	//all the remaining bytes following b will be 0 and if we or it,
	//the value of the original spots will all be preserved
	unsigned int temp = b << i * 8;

	//By performing and between the given value x and makeZero variable 
	//created, we make the spot that b will come in to be 0 and 
	//by performing or between that value and temp variable created,
	//we reach the final answer of b at the right spot of the original
	//variable x.
	unsigned int answer = (x & makeZero) | temp;
	return answer;
}

int questionTwoPartA(int x)
{
	//If any bit of x equals 1, this function will evaluate to True.
	//This means that it will provide False only if all bits of x are 
	//equal to 0, which means that x has to eqaul 0. 
	//In C, any value other than 0 is equivalent to True in boolean.
	//If we put a boolean expression not(!) in front of integer x, it will
	//give True if x is 0 and False if x is not 0. If we put another not,
	//it will give True if x is False and False if x is True.
	//Therefore, if x is not 0, !x will equal False and !!x will equal True
	return (!!x);
}

int questionTwoPartB(int x)
{
	//If any bit of x equals 0, this function will evaluate to True.
	//This means that it will provide False only if all bits of x are
	//equal to 1.
	//This is similar to the question above. If we negate all the bits
	//(change 1 to 0 and 0 to 1), it is same as solving part A
	//since if all bits are 1, bitwise NOT of it will convert it to 0 
	//and if you conduct not(!) 0, it is True and another not True
	//becomes False. Since all bits are 1 and there exists no 0, the answer
	//has to be False, which is correct. It will produce True for 
	//any other values. If at least one of the bits are 0, bitwise NOT
	//will convert it to 1, which will prevent the value from being
	//0 and not(!)any number is False and another not False is True.
	//Since at least one existence of 0 produces True, this is correct.
	
	return (!!(~x));
}

int questionTwoPartC(int x)
{
	//If any bit in the least significant byte of x equals 1, this
	//function will return True. In order to find this out, we can
	//conduct and operator with 255, which is 0xFF. If at least one
	//bit in the least significant byte of x is 1, there will be at least
	//one 1 as a result. Therefore, it will prevent the value of that 
	//integer from becoming 0. Then, it becomes same as part A, which 
	//is looking for any bit that equals 1.
	return !!(x & 0xFF);
}	

int questionTwoPartD(int x)
{
	//If any bit in the most significant byte of x equals 0, this function
	//will return True. In order to find this out, we can conduct and
	//operator with 255, which is 0xFF with the bitwise NOT converted
	//most significant bits. Since int has 32 bits, we can right shift the
	//value 24bits and bitwise NOT it so that all the 1s become 0s and 0s
	//to become 1s. Then, we can conduct and operator with 0xFF and the
	//rest of the question is same as part b.
	int mostSignificant = x>>(3*8);
	mostSignificant = ~mostSignificant;
	return !!(mostSignificant & 0xFF);

}

int any_odd_one(unsigned x)
{
	//This function asks to return 1 when any odd bit of x equals 1.
	//We create a hex code of value that has 1 in every odd bit and
	//0 in every even bit. 
	unsigned int oddBitOne = 0xAAAAAAAA;
	//If we conduct and operator with the original x that was given and
	//once there is at least one 1 in every odd bit, it will produce the
	//value of 1. The even digits of x do not matter because once you
	//conduct and operator with 0, it becomes 0. This means that if 
	//there is at least one 1 in the answer between the and operator of
	//oddBitOne and x, it is because there exists at least one 1 in odd
	//bit of x.
	unsigned int value = oddBitOne & x;
	return !!value;
}

int xbyte(packed_t word, int bytenum)
{
	return (word >> (bytenum << 3)) & 0xFF;
}

//4A. There are few errors in this code. The question states that we need the
//bytes to be signed instead of being unsigned. However, if we put an and operator
//at the end to 0xFF to a 32 bit(4 byte) integer, the first 24 values of the returned
//value will automatically be 0 since 0xFF has 24 bits of 0 prior to 8 bits of 1.
//This is because you are conducting an and operator to 0s, which automatically makes
//the value to 0. Therefore, the first digit is always going to 0, preventing it from
//becoming negative when the value has to be and preventing the data to be signed.

int xbyte(packed_t word, int bytenum)
{
	typedef unsigned packed_t;
	//In order to extract the byte of word, we need to remove the left and right
	//bytes and leave the byte that we want to extract at the least significant byte.
	//To do so, we can remove all bytes to left by leftShift and remove all
	//bytes to right by rightShift. 
	//Depending on the byte we want to extract, we will have to conduct differernt
	//leftShift at beginning, which is (3-bytenum)<<3 of word.
	int rightShift = word <<((3-bytenum)<<3);
	//Then, since all the values at the right after the byte that we want to extract
	//will be 0, we can then rightShift it by 24bits to the least significant byte
	int leftShift = rightShift >>(3*8);
	return leftShift;
}
int tsub_ok(int x, int y)
{
	int value = ~y+0x01;
	int one = !(x & INT_MIN) && !(value & INT_MIN) && ((x + value) & INT_MIN);
	int two = (x & INT_MIN) && (value & INT_MIN) && !((x + value) & INT_MIN);
	int answer = !one && !two;
	return answer;
}
		
	




