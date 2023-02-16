package com.example.mycontactlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class ContactAdapter extends RecyclerView.Adapter {
    private ArrayList<Contact> contactData;
    private static View.OnClickListener mOnItemClickListener;
    private boolean isDeleting;
    private Context parentContext;


    public class ContactViewHolder extends  RecyclerView.ViewHolder{
        public TextView textViewContact;
        public TextView textPhone;
        public TextView textCell;  //ADD THIS
        public TextView textStreet;
        public TextView textCity;
        public TextView textState;
        public TextView textZipcode;
        public Button deleteButton;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContact = itemView.findViewById(R.id.textContactName);
            textPhone = itemView.findViewById(R.id.textPhoneNumber);
            textCell = itemView.findViewById(R.id.textCellNumber);
            textStreet = itemView.findViewById(R.id.textStreetView);
            textCity = itemView.findViewById(R.id.textCityView);
            textState = itemView.findViewById(R.id.textStateView);
            textZipcode = itemView.findViewById(R.id.textZipcodeView);
            deleteButton = itemView.findViewById(R.id.buttonDeleteContact);
            itemView.setTag(this); //to identify which item was clicked
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getContactTextView(){
            return textViewContact;
        }
        public TextView getPhoneTextView(){
            return textPhone;
        }
        public TextView getCellTextView() { return textCell; }
        public TextView getStreetTextView() {return textStreet; }
        public TextView getCityTextView() {return textCity;}
        public TextView getStateTextView() {return textState;}
        public TextView getZipcodeTextView() {return textZipcode;}
        public TextView getDeleteButton(){
            return deleteButton;
        }
    }

   public ContactAdapter(ArrayList<Contact> arrayList){
        contactData = arrayList;
   }

   public static void setOnItemClickListener(View.OnClickListener itemClickListener){
        mOnItemClickListener =  itemClickListener;
   }

    public ContactAdapter (ArrayList<Contact> arrayList, Context context){
        contactData = arrayList;
        parentContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);

        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ContactViewHolder cvh = (ContactViewHolder) holder;

        cvh.getContactTextView().setText(contactData.get(position).getContactName());
        cvh.getPhoneTextView().setText(contactData.get(position).getPhoneNumber());
        cvh.getCellTextView().setText(contactData.get(position).getCellNumber()); //ADD THIS
        cvh.getStreetTextView().setText(contactData.get(position).getStreetAddress());
        cvh.getCityTextView().setText(contactData.get(position).getCity());
        cvh.getStateTextView().setText(contactData.get(position).getState());
        cvh.getZipcodeTextView().setText(contactData.get(position).getZipCode());
        if(isDeleting){
            cvh.getDeleteButton().setVisibility(View.VISIBLE);
            cvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(position);
                }
            });
        }
        else{
            cvh.getDeleteButton().setVisibility(View.INVISIBLE);
        }

        if(position % 2 == 0) {
            cvh.getContactTextView().setTextColor(Color.parseColor("#0000ff"));
        }
        else{
            cvh.getContactTextView().setTextColor(Color.parseColor("#ff0000"));
        }


    }

    public void setDelete (boolean b){
       isDeleting = b;
    }

    public void deleteItem(int position){
       Contact contact = contactData.get(position);
       ContactDataSource ds = new ContactDataSource(parentContext);

       try{
           ds.open();
           boolean didDelete = ds.deleteContact(contact.getContactID());
           ds.close();

           if(didDelete){
               contactData.remove(position);
               notifyDataSetChanged();
           }
           else{
               Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
           }
       }catch(Exception ex){
           Toast.makeText(parentContext, "Delete Failed", Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    public int getItemCount() {
        return contactData.size();
    }



}
