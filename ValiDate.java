
package com.mycompany.mainpanel;
class Validate {
public boolean isAccountNumber(String accNo) {
return accNo != null && accNo.matches("\\d{5}");
}
public boolean isAmount(String amount) {
try {
double val = Double.parseDouble(amount);
return val > 0;
} catch (NumberFormatException e) {
return false;
}
}
}


