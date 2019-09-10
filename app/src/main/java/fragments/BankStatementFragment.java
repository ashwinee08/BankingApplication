package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import adapters.RecyclerViewAdapter;
import models.TransactionInfo;

import com.example.joker.bankingapplication.R;

import static adapters.ViewPagerAdapter.getTransactionInfoList;
import static android.view.View.GONE;



public class BankStatementFragment extends Fragment {


    private Context context;
    private TextView defaultTextView;
    private RecyclerView debitsRecyclerView;
    private List<TransactionInfo> transactionInfoList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        transactionInfoList = getTransactionInfoList();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Collections.sort(transactionInfoList, TransactionInfo.compareDate);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_credits,container,false);
        getHold(view);
        if(transactionInfoList!=null){
            defaultTextView.setVisibility(GONE);
            debitsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            debitsRecyclerView.setAdapter(new RecyclerViewAdapter(context,transactionInfoList));
            debitsRecyclerView.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void getHold(View view){
        defaultTextView = view.findViewById(R.id.default_msg_credits);
        debitsRecyclerView = view.findViewById(R.id.debits_recycler_view_credits);
    }
}
