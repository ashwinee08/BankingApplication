package adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import fragments.BankStatementFragment;
import fragments.CreditsFragment;
import fragments.DebitsFragment;
import models.TransactionInfo;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static List<TransactionInfo> transactionInfoList;

    public ViewPagerAdapter(FragmentManager fm,final List<TransactionInfo> transactionInfoListLocal) {
        super(fm);
        transactionInfoList=transactionInfoListLocal;
    }

    public static List<TransactionInfo> getTransactionInfoList(){
        return transactionInfoList;
    }


    @Override
    public Fragment getItem(int i) {
        Fragment toBeReturned;
        switch(i+1){
            case 2:
                toBeReturned=new CreditsFragment();
                break;
            case 3:
                toBeReturned=new BankStatementFragment();
                break;
            case 1:
            default:
                toBeReturned=new DebitsFragment();
        }
        return toBeReturned;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String tabTitle;
        switch(position+1){
            case 2:
                tabTitle="Credits".toUpperCase();
                break;
            case 3:
                tabTitle="Statement".toUpperCase();
                break;
            case 1:
            default:
                tabTitle="Debits".toUpperCase();
        }
        return tabTitle;
    }
}
