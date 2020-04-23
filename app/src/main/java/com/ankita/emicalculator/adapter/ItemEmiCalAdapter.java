package com.ankita.emicalculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ankita.emicalculator.R;
import com.ankita.emicalculator.modal.EmiEntitty;

import java.util.ArrayList;

public class ItemEmiCalAdapter extends RecyclerView.Adapter<ItemEmiCalAdapter.EmiCalViewHolder> {

    private Context context;
    private ArrayList<EmiEntitty> arrayList;

    public ItemEmiCalAdapter(Context context, ArrayList<EmiEntitty> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public EmiCalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emical,parent,false);
        return new EmiCalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmiCalViewHolder holder, int position) {
        EmiEntitty data=arrayList.get(position);
        holder.tvInstallmentNo.setText("installment no is".concat(String.valueOf(data.getInstNo())));
        holder.tvDate.setText("date is"+data.getDate());
        holder.tvLoanAmount.setText("Loan amount is"+data.getLoanAmount());
        holder.tvEmi.setText("emi is"+data.getEmi());
        holder.tvInterest.setText("interest is"+data.getInterest());
        holder.tvPrinciple.setText("principle is"+data.getPrinciple());
        holder.tvPrePaid.setText("prepaid is"+data.isWantPrePayment());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class EmiCalViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvInstallmentNo,tvInterest,tvEmi,tvPrePaid,tvLoanAmount,tvPrinciple;
        public EmiCalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tvDate);
            tvInstallmentNo=itemView.findViewById(R.id.tvInstallmentNo);
            tvInterest=itemView.findViewById(R.id.tvInterest);
            tvEmi=itemView.findViewById(R.id.tvEMI);
            tvPrePaid=itemView.findViewById(R.id.tvPrePayment);
            tvLoanAmount=itemView.findViewById(R.id.tvLoanAmount);
            tvPrinciple=itemView.findViewById(R.id.tvPrinciple);

        }
    }
}
