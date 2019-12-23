package com.example.contactsapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;


public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> implements FastScrollRecyclerViewInterface {
    private Context context;
    private ArrayList<ContactList> contactlists;
    private HashMap<String, Integer> mMapIndex;



    public ContactListAdapter(ArrayList<ContactList> contactLists, HashMap<String, Integer> mapIndex, Context context){

       this.context = context;
        this.contactlists=contactLists;
        this.mMapIndex = mapIndex;

    }
    @Override
    public ContactListAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contact,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ContactListAdapter.ViewHolder viewHolder, int i) {

        final ContactList contactLists = contactlists.get(i);

        viewHolder.tv_fname.setText(contactLists.fname + "");
        viewHolder.tv_num.setText(contactLists.num + "");



      //  viewHolder.tv_fname.setText(contactLists.fname+contactLists.getFname());
        if(i!=0){
            if(contactLists.getFname() == contactLists.getFname()){
                viewHolder.mAlpha.setVisibility(View.VISIBLE);
                viewHolder.mAlpha.setText(contactLists.getFname().substring(0,1));
            }
            else{
                viewHolder.mAlpha.setVisibility(View.INVISIBLE);
            }

        }else{
            viewHolder.mAlpha.setVisibility(View.VISIBLE);
            viewHolder.mAlpha.setText(contactLists.getFname().substring(0,1));
        }

        viewHolder.ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactUpdateActivity.class);
                intent.putExtra("contactList", contactLists);
                context.startActivity(intent);
            }

        });


        viewHolder.btn_calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent intent = new Intent(context, CallUpdateActivity.class);
                    intent.putExtra("contactList", contactLists);

                    context.startActivity(intent);

                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return contactlists.size();
    }

    @Override
    public HashMap<String, Integer> getMapIndex() {
        return this.mMapIndex;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_fname;
        private TextView tv_num;
        private LinearLayout ll2;
        private LinearLayout ll_call;
        private Button btn_calling;
        public TextView mAlpha;

        public ViewHolder( View itemView) {
            super(itemView);

            tv_fname = itemView.findViewById(R.id.tv_fname);
            tv_num = itemView.findViewById(R.id.tv_num);
            ll2 = itemView.findViewById(R.id.ll2);
            ll_call = itemView.findViewById(R.id.ll_call);
            btn_calling = itemView.findViewById(R.id.btn_calling);
            mAlpha = itemView.findViewById(R.id.mAlpha);

        }

    }

}