package models;

import java.util.Comparator;

public class TransactionInfo {
    String date;
    String AmountDeductedOrCredited;
    BankingType type;


    public static Comparator<TransactionInfo> compareDate=new Comparator<TransactionInfo>() {
        @Override
        public int compare(TransactionInfo o1, TransactionInfo o2) {
            String date1,date2, month1,month2, year1,year2;
            String[] breakDate1=o1.date.split("-");
            String[] breakDate2=o2.date.split("-");
            date1=breakDate1[0];
            month1=breakDate1[1];
            year1=breakDate1[2];

            date2=breakDate2[0];
            month2=breakDate2[1];
            year2=breakDate2[2];

            int diff=year1.compareTo(year2)==0?
                    month1.compareTo(month2)==0?
                        date1.compareTo(date2)==0? 1 :  date1.compareTo(date2)
                        :   month1.compareTo(month2)
                    : year1.compareTo(year2);

            return diff;
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmountDeductedOrCredited() {
        return AmountDeductedOrCredited;
    }

    public void setAmountDeductedOrCredited(String amountDeducted) {
        this.AmountDeductedOrCredited = amountDeducted;
    }

    public BankingType getType() {
        return type;
    }

    public void setType(BankingType type) {
        this.type = type;
    }

    public enum BankingType{
        credited, debited
    }


    public String allTogether(){
        return date + "     " + AmountDeductedOrCredited+"    " + type;
    }

    public String allTogetherExceptType(){
        return date + "        " + AmountDeductedOrCredited;
    }
}
