package virtualmind.prettifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.pow;
import static java.lang.Math.abs;
/**
 * Number prettifier (without using any API) 
 * Uses Short Scale (US, Canada) nomenclature for big numbers
 * (See https://en.wikipedia.org/wiki/Names_of_large_numbers)
 * The prettifyNumber method accepts a numeric type and returns a truncated, 
 * "prettified" string version. The prettified version should include one number after the 
 * decimal when the truncated number is not an integer. It should prettify numbers greater 
 * than 6 digits and support millions, billions and trillions.
 *
 * Examples:
 *   input: 1000000
 *   output: 1M
 *
 *   input: 2500000.34
 *   output: 2.5M
 *
 *   input: 532
 *   output: 532
 *
 *   input: 1123456789
 *   output: 1.1B
 * 
 * @author nachhh
 *
 */
public class NumberPrettifier {
	
	public static final Double MAX_NUMBER = 999_999_999_999_999.9D;
	public static final Double MIN_NUMBER = -999_999_999_999_999.9D;
	public static final String MAX_NUMBER_PRETTIFIED = "999.9T";
	public static final String MIN_NUMBER_PRETTIFIED = "-999.9T";
	/**
	 * Prettifies a given number, using short scale (US, Canada) for the nomenclature of numbers
	 * @param number to prettify (handles both integer or floating point stuff)
	 * @return prettified version of number as a string, or null if number exceeds 
	 * 9.999.999.999.999.9 or is less than -9.999.999.999.999.9 or is NaN or infinite
	 */
	public static String prettifyNumber(Double number) {
		Map<Long, String> units = new HashMap<>();//maps thousand counts to units
		units.put(2L, "M"); //Million 10^6
		units.put(3L, "B"); //Billion 10^9
		units.put(4L, "T"); //Trillion 10^12
		units.put(7L, "V"); //Trillion 10^12
	
		if(number > MAX_NUMBER || number < MIN_NUMBER || number.isNaN() 
				|| number.isInfinite()) // limit cases
			return null;
		String numAstr = longToDecString(number.longValue());//to string without external api
		Long thousandCnt = numAstr.chars().filter(c -> c == ',').count();//for adding unit
		StringBuilder result = new StringBuilder();
		if(thousandCnt > 1) //only extract last power of ten part
			result.append(numAstr.split(",")[0]);
		else //leave number as returned by longToDecString w/ pretty format
			result.append(numAstr);
		// handling of less than 1M floating point numbers:
		BigDecimal fraction = new BigDecimal(number % 1);//for precision
		fraction = fraction.compareTo(BigDecimal.ZERO) == 1 ? 
			fraction.setScale(2, BigDecimal.ROUND_CEILING) :
			fraction.setScale(2, BigDecimal.ROUND_FLOOR);
		if(number < 0 && number > -1)
			result.insert(0, "-");//limit case, i.e. -0.5 we need to add - sign
		if(fraction.abs().compareTo(BigDecimal.ZERO) == 1 
				&& thousandCnt < 2)//add fraction to floating point numbers less than 1M
			result.append(".").append(fraction.abs().multiply(new BigDecimal(10)).intValue());
		
		if(thousandCnt > 1 && abs(number/pow(10, thousandCnt*3)) != 1 
				&& numAstr.split(",")[1].charAt(0) != '0')//append next truncated power of ten
			result.append(".").append(numAstr.charAt(numAstr.indexOf(',')+1));
		if(thousandCnt > 1) //append unit
			result.append(units.get(thousandCnt));
		return result.toString();
	}
	
	/**
	 * Just an overloaded version that accepts longs
	 * @param number
	 * @return pettrified version of number as a string
	 */
	public static String prettifyNumber(Long number) {
		return NumberPrettifier.prettifyNumber(number.doubleValue());
	}
	
	/**
	 * Yet another overloaded version that accepts ints
	 * @param number
	 * @return prettified version of number as a string
	 */
	public static String prettifyNumber(Integer number) {
		return NumberPrettifier.prettifyNumber(number.doubleValue());
	}
	
	/**
	 * Given a numeric long value, returns its string decimal 
	 * representation (without using any built-in API)
	 * @param n input number as a long value
	 * @return String representation of n, with comma (",") as separator 
	 * of thousands (pretty format) 
	 */
	private static String longToDecString(long n) {
		StringBuilder decimal = new StringBuilder();
		int rem, sepCount=0;
		if(n==0) return decimal.append("0").toString();
		long m = Math.abs(n);
		while(m!= 0) {
			rem =  (int )(m % 10);
			if((decimal.length()+1-sepCount)%3 == 1 && decimal.length() != 0) {
				decimal.append(",");
				++sepCount;
			}
			decimal.append(rem);
			m = m/10L;
		}
		if(n < 0) decimal.append("-");
		
		return decimal.reverse().toString();
	}
	
	/**
	 * Runs the pettrifyNumber() method to show some results
	 * @param arg
	 */
	public static void main(String [] arg) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter a number (i.e. 591444894 or 45000.6) to prettify: ");
        try{
            Double input = Double.parseDouble(br.readLine());
            long startTime = System.currentTimeMillis();
    		String prettifiedNum = prettifyNumber(input);
    		long stopTime = System.currentTimeMillis();
    		long elapsedTime = stopTime - startTime;
    		StringBuilder output = new StringBuilder();
    		output.append("Prettified version of the given number is: ").
    			append(prettifiedNum).append("\n Running time: ").append(elapsedTime).append(" ms");
    		System.out.println(output.toString());
        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        }
	}
}
