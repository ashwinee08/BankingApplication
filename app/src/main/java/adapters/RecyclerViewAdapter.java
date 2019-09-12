package adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import models.TransactionInfo;

import com.example.joker.bankingapplication.R;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder>{

    private Context context;
    private List<TransactionInfo> transactionInfoList;
    public RecyclerViewAdapter(Context context, List<TransactionInfo> transactionInfoList) {
        this.context=context;
        this.transactionInfoList=transactionInfoList;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_element,viewGroup,false);
        return new RecyclerViewViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder recyclerViewViewHolder, int i) {
        TransactionInfo transactionInfo=transactionInfoList.get(i);
        recyclerViewViewHolder.setDateTextView(transactionInfo.getDate());
        recyclerViewViewHolder.setAmountTextView(transactionInfo.getAmountDeductedOrCredited());
        final String TYPE_DEBITED = "debited" ;
        final String TYPE_CREDITED = "credited" ;
        if(transactionInfo.getType()== TransactionInfo.BankingType.debited){
            recyclerViewViewHolder.setTypeTextView(TYPE_DEBITED);
        }else{
            recyclerViewViewHolder.setTypeTextView(TYPE_CREDITED);
        }

    }

    @Override
    public int getItemCount() {
        return transactionInfoList.size();
    }

    public class RecyclerViewViewHolder extends RecyclerView.ViewHolder{

        private  TextView dateTextView, amountTextView,typeTextView;
        RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            getHold(itemView);
        }
        private void getHold(View view){
            dateTextView=view.findViewById(R.id.transaction_date);
            amountTextView=view.findViewById(R.id.transaction__amount);
            typeTextView=view.findViewById(R.id.transaction_type);
        }

        public void setDateTextView(String data){
            dateTextView.setText(data);
        }
        public void setAmountTextView(String data){
            amountTextView.setText(data);
        }
        public void setTypeTextView(String data){
            typeTextView.setText(data);
        }
    }
}
