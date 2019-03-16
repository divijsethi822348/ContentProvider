package com.example.contentprovider.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.contentprovider.ModelClasses.ContactsModel;
import com.example.contentprovider.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.contentprovider.R.id.contact_image;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    List<ContactsModel> list;
    Context context;

    public ContactsAdapter(List<ContactsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_list_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ContactsModel model=list.get(i);
        myViewHolder.name.setText(model.getName());
        myViewHolder.number.setText(model.getNumber());
        Picasso.get().load(model.getPhoto_uri()).into(myViewHolder.contacts_image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, number;
        CircleImageView contacts_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.name);
            number=(TextView) itemView.findViewById(R.id.number);
            contacts_image=(CircleImageView) itemView.findViewById(contact_image);
        }
    }
}
