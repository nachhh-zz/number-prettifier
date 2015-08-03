package virtualmind.prettifier;

import org.junit.Test;

import virtualmind.prettifier.NumberPrettifier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
/**
 * Unit Test class for the NumberPettrifier.prettifyNumber() method
 * with exhaustive tests including limit cases and subtle ones 
 * @author nachhh
 *
 */
public class NumberPrettifierTest {
	@Test
	public void testPettrifier() {
		
		//invalid input is not possible because of type safety
		//so no need to test that.
		
		// 0 input
		assertEquals(NumberPrettifier.prettifyNumber(0), "0");	
		assertEquals(NumberPrettifier.prettifyNumber(0.0), "0");
		
		// less than thousand integer
		assertEquals(NumberPrettifier.prettifyNumber(666), "666");
		//negative
		assertEquals(NumberPrettifier.prettifyNumber(-666), "-666");
		
		// less than thousand float
		assertEquals(NumberPrettifier.prettifyNumber(66.6), "66.6");
		// less than thousand float negative
		assertEquals(NumberPrettifier.prettifyNumber(-66.6), "-66.6");
		
		// less than 1 fraction
		assertEquals(NumberPrettifier.prettifyNumber(0.5),"0.5");
		// less than 1 fraction negative
		assertEquals(NumberPrettifier.prettifyNumber(-0.5),"-0.5");
		
		//upper bound of 999_999_999_999_999.9D
		assertEquals(NumberPrettifier.prettifyNumber(NumberPrettifier.MAX_NUMBER),
			NumberPrettifier.MAX_NUMBER_PRETTIFIED);
		
		//lower bound of -999_999_999_999_999.9D
		assertEquals(NumberPrettifier.prettifyNumber(NumberPrettifier.MIN_NUMBER),
			NumberPrettifier.MIN_NUMBER_PRETTIFIED);
		
		// numbers that exceed the limit of 999_999_999_999_999.9D
		assertNull(NumberPrettifier.prettifyNumber(NumberPrettifier.MAX_NUMBER + 0.1D));
		
		// numbers that are less than the lower bound of -999_999_999_999_999.9D
		assertNull(NumberPrettifier.prettifyNumber(NumberPrettifier.MIN_NUMBER - 0.1D));
		
		// the following stuff is for an extended version of the pettrifier that
		// would accept numbers larger than abs(999,999,999,999,999.9)
		//extremely big integer (MAX long)
		//assertEquals(NumberPettrifier.prettifyNumber(Long.MAX_VALUE), "9.2V");
		
		//extremely small integer (MIN long)
		//assertEquals(NumberPettrifier.prettifyNumber(Long.MIN_VALUE), "-9.2V");;
		
		// extremely small positive floating number (MIN double)
		//NumberPettrifier.prettifyNumber(Double.MIN_VALUE);
		
		// extremely small negative floating number (MIN double)
		//NumberPettrifier.prettifyNumber(-Double.MIN_VALUE);
		
		// extremely big floating number (MAX double)
		//NumberPettrifier.prettifyNumber(Double.MAX_VALUE);
		
		// extremely big negative number (MAX double)
		//NumberPettrifier.prettifyNumber(-Double.MAX_VALUE);
		
		//infinity stuff (we don't accept these)
		assertNull(NumberPrettifier.prettifyNumber(Double.POSITIVE_INFINITY));
		assertNull(NumberPrettifier.prettifyNumber(Double.NEGATIVE_INFINITY));
		assertNull(NumberPrettifier.prettifyNumber(Double.NaN));
		
		// Thousand exact power of ten
		assertEquals(NumberPrettifier.prettifyNumber(1_000), "1,000");
		// Thousand exact power of ten negative
		assertEquals(NumberPrettifier.prettifyNumber(-1_000), "-1,000");
		// Thousand non exact power of ten
		assertEquals(NumberPrettifier.prettifyNumber(1_666), "1,666");
		// Thousand non exact power of ten negative
		assertEquals(NumberPrettifier.prettifyNumber(-1_666), "-1,666");
		// Thousand with fraction
		assertEquals(NumberPrettifier.prettifyNumber(1_000.1), "1,000.1");
		// Thousand with fraction negative
		assertEquals(NumberPrettifier.prettifyNumber(-1_000.1), "-1,000.1");
		// 10 thousand
		assertEquals(NumberPrettifier.prettifyNumber(10_500), "10,500");
		// 100 million
		assertEquals(NumberPrettifier.prettifyNumber(190_000), "190,000");
		
		// Million exact power of ten
		assertEquals(NumberPrettifier.prettifyNumber(1_000_000), "1M");
		// Million exact power of ten negative
		assertEquals(NumberPrettifier.prettifyNumber(-1_000_000), "-1M");
		// Million non exact power of ten
		assertEquals(NumberPrettifier.prettifyNumber(1_123_456), "1.1M");
		// Million non exact power of ten negative
		assertEquals(NumberPrettifier.prettifyNumber(-1_123_456), "-1.1M");
		// Million with fraction
		assertEquals(NumberPrettifier.prettifyNumber(1_666_000.123), "1.6M");
		// Million with fraction negative
		assertEquals(NumberPrettifier.prettifyNumber(-1_666_000.123), "-1.6M");
		// 10 million
		assertEquals(NumberPrettifier.prettifyNumber(10_000_000), "10M");
		// 100 million
		assertEquals(NumberPrettifier.prettifyNumber(100_000_000), "100M");
		//don't include .0 test
		assertEquals(NumberPrettifier.prettifyNumber(133_099_123), "133M");
		
		// Billion exact power of ten
		assertEquals(NumberPrettifier.prettifyNumber(1_000_000_000), "1B");
		// Billion exact power of ten negative
		assertEquals(NumberPrettifier.prettifyNumber(-1_000_000_000), "-1B");
		// Billion non exact power of ten
		assertEquals(NumberPrettifier.prettifyNumber(1_460_200_300), "1.4B");
		// Billion non exact power of ten negative
		assertEquals(NumberPrettifier.prettifyNumber(-1_460_200_300), "-1.4B");
		// Billion with fraction
		assertEquals(NumberPrettifier.prettifyNumber(1_000_100_000.5), "1B");
		// Billion with fraction negative
		assertEquals(NumberPrettifier.prettifyNumber(-1_000_100_000.5), "-1B");
		// 10 Billion +
		assertEquals(NumberPrettifier.prettifyNumber(15_000_000_000L), "15B");
		// 100 Billion +
		assertEquals(NumberPrettifier.prettifyNumber(199_700_000_000L), "199.7B");
		//don't include .0 test
		assertEquals(NumberPrettifier.prettifyNumber(199_001_000_000L), "199B");
		
		// Trillion exact power of ten
		assertEquals(NumberPrettifier.prettifyNumber(1_000_000_000_000L), "1T");
		// Trillion exact power of ten negative
		assertEquals(NumberPrettifier.prettifyNumber(-1_000_000_000_000L), "-1T");
		// Trillion non power of ten
		assertEquals(NumberPrettifier.prettifyNumber(1_900_000_000_000L), "1.9T");
		// Trillion non power of ten negative
		assertEquals(NumberPrettifier.prettifyNumber(-1_900_000_000_000L), "-1.9T");
		// Trillion with fraction negative
		assertEquals(NumberPrettifier.prettifyNumber(-1_200_000_000_000.9), "-1.2T");
		// 10 Trillion +
		assertEquals(NumberPrettifier.prettifyNumber(10_000_000_000_000L), "10T");
		// 100 Trillion
		assertEquals(NumberPrettifier.prettifyNumber(105_990_000_000_000L), "105.9T");
		// because the billion part starts with 0, we shouldn't add the .0
		assertEquals(NumberPrettifier.prettifyNumber(105_090_000_000_000L), "105T");
		
	}
	
}
