package datamanagement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import parser.WrongReceiptKindException;

public abstract class Taxpayer {

  protected final String fullname;
  protected final int taxRegistrationNumber;
  protected final float income;
  private float amountPerReceiptsKind[] = new float[5];
  private int totalReceiptsGathered = 0;
  private HashMap<Integer, Receipt> receiptHashMap = new HashMap<Integer, Receipt>(0);
  private String arrayWithKinds[] = {"Entertainment", "Basic", "Travel", "Health", "Other"};
  private double arrayWithIfMultipliers[] = {0.2, 0.4, 0.6};
  private double arrayWithReturnMultipliers[] = {0.08, 0.04, -0.15};
  protected double arrayWithConstantComparators[];
  protected double arrayWithConstants[];
  
  public Taxpayer(String fullname, int taxRegistrationNumber, float income) {
    this.fullname = fullname;
    this.taxRegistrationNumber = taxRegistrationNumber;
    this.income = income;
  }

  public double calculateBasicTax(){
	    for (int i = 0; i < arrayWithConstantComparators.length; i++){
	      if (income < arrayWithConstantComparators[i]){
	        return arrayWithConstants[i];
	      }
	    }
	    return arrayWithConstants[arrayWithConstants.length-1];
  }

  public void addReceipt(Receipt receipt) throws WrongReceiptKindException {
	for (int i = 0; i < amountPerReceiptsKind.length; i++){
		if (receipt.getKind().equals(arrayWithKinds[i])){
			amountPerReceiptsKind[i] += receipt.getAmount();
			break;
	    }
	}
    receiptHashMap.put(receipt.getId(), receipt);
    totalReceiptsGathered++;
  }

  public void removeReceipt(int receiptId) throws WrongReceiptKindException {
	    Receipt receipt = receiptHashMap.get(receiptId);
	    for (int i = 0; i < amountPerReceiptsKind.length; i++){
	      if (receipt.getKind().equals(arrayWithKinds[i])){
	        amountPerReceiptsKind[i] -= receipt.getAmount();
	        break;
	      }
	    }
	    totalReceiptsGathered--;
	    receiptHashMap.remove(receiptId);
  }

  public String getFullname() {
    return fullname;
  }

  public int getTaxRegistrationNumber() {
    return taxRegistrationNumber;
  }

  public float getIncome() {
    return income;
  }

  public HashMap<Integer, Receipt> getReceiptHashMap() {
    return receiptHashMap;
  }

  public double getVariationTaxOnReceipts() {
	    float totalAmountOfReceipts = getTotalAmountOfReceipts();
	    for (int i = 0; i < arrayWithIfMultipliers.length; i++){
	      if (totalAmountOfReceipts < arrayWithIfMultipliers[i] * income){
	        return calculateBasicTax() * arrayWithReturnMultipliers[i];
	      }
	    }
	    return -calculateBasicTax() * 0.3;
  }

  private float getTotalAmountOfReceipts() {
    int sum = 0;
    for (int i = 0; i < 5; i++) {
      sum += amountPerReceiptsKind[i];
    }
    return sum;
  }

  public int getTotalReceiptsGathered() {
    return totalReceiptsGathered;
  }

  public float getAmountOfReceiptKind(short kind) {
    return amountPerReceiptsKind[kind];
  }

  public double getTotalTax() {
    return calculateBasicTax() + getVariationTaxOnReceipts();
  }

  public double getBasicTax() {
    return calculateBasicTax();
  }

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + Arrays.hashCode(amountPerReceiptsKind);
	result = prime * result + Arrays.hashCode(arrayWithConstantComparators);
	result = prime * result + Arrays.hashCode(arrayWithConstants);
	result = prime * result + Arrays.hashCode(arrayWithIfMultipliers);
	result = prime * result + Arrays.hashCode(arrayWithKinds);
	result = prime * result + Arrays.hashCode(arrayWithReturnMultipliers);
	result = prime * result
			+ Objects.hash(fullname, income, receiptHashMap, taxRegistrationNumber, totalReceiptsGathered);
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Taxpayer other = (Taxpayer) obj;
	return Arrays.equals(amountPerReceiptsKind, other.amountPerReceiptsKind)
			&& Arrays.equals(arrayWithConstantComparators, other.arrayWithConstantComparators)
			&& Arrays.equals(arrayWithConstants, other.arrayWithConstants)
			&& Arrays.equals(arrayWithIfMultipliers, other.arrayWithIfMultipliers)
			&& Arrays.equals(arrayWithKinds, other.arrayWithKinds)
			&& Arrays.equals(arrayWithReturnMultipliers, other.arrayWithReturnMultipliers)
			&& Objects.equals(fullname, other.fullname)
			&& Float.floatToIntBits(income) == Float.floatToIntBits(other.income)
			&& Objects.equals(receiptHashMap, other.receiptHashMap)
			&& taxRegistrationNumber == other.taxRegistrationNumber
			&& totalReceiptsGathered == other.totalReceiptsGathered;
}

}
