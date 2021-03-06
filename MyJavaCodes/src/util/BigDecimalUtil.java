package util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * https://docs.oracle.com/javase/7/docs/api/java/math/RoundingMode.html
 * @author ��ȿ��
 *
 */
public class BigDecimalUtil {

	public static void main(String[] args) {
		BigDecimal serviceTax = new BigDecimal("56.0084578639");
		serviceTax = serviceTax.setScale(2, RoundingMode.CEILING);
		 
		BigDecimal entertainmentTax = new BigDecimal("23.00689");
		entertainmentTax = entertainmentTax.setScale(2,RoundingMode.FLOOR);
		
		BigDecimal totalTax = serviceTax.add(entertainmentTax);
	}
}
