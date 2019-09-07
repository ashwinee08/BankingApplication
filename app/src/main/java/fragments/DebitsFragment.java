package fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joker.bankingapplication.R;

import java.util.List;
import java.util.zip.Inflater;

import adapters.ViewPagerAdapter;
import models.TransactionInfo;

public class DebitsFragment extends Fragment {

    private final List<TransactionInfo> transactionInfoList= ViewPagerAdapter.getTransactionInfoList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_debits,container,false);
    }
}
