package datamanagement;

public class HeadOfHouseholdTaxpayer extends Taxpayer {

  public HeadOfHouseholdTaxpayer(String fullname, int taxRegistrationNumber, float income) {
    super(fullname, taxRegistrationNumber, income);
	arrayWithConstantComparators = new double[]{30390, 90000, 122110, 203390};
	arrayWithConstants = new double[]{
		  0.0535 * income, 
	      1625.87 + 0.0705 * (income - 30390), 
	      5828.38 + 0.0705 * (income - 90000), 
	      8092.13 + 0.0785 * (income - 122110), 
	      14472.61 + 0.0985 * (income - 203390)
	};
  }

}
