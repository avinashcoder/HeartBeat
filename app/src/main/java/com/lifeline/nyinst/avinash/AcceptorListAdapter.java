package com.lifeline.nyinst.avinash;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class AcceptorListAdapter extends RecyclerView.Adapter<AcceptorListAdapter.Viewholder> {

    private List<AcceptorListModelClass> acceptorModelClassList;

    public AcceptorListAdapter(List<AcceptorListModelClass> acceptorModelClassList){
        this.acceptorModelClassList=acceptorModelClassList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.acceptor_item_layout,viewGroup,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int position) {

        int id=acceptorModelClassList.get(position).getId();
        String profileUrl=acceptorModelClassList.get(position).getProfileUrl();
        String name=acceptorModelClassList.get(position).getName();
        String city=acceptorModelClassList.get(position).getCity();
        Double distance=acceptorModelClassList.get(position).getDistance();
        String contactNo=acceptorModelClassList.get(position).getContact();
        String bloodGroup=acceptorModelClassList.get(position).getBloodGroup();

        viewholder.setData(id,profileUrl,name,city,distance,contactNo,bloodGroup);

    }

    @Override
    public int getItemCount() {
        return acceptorModelClassList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        private SimpleDraweeView profilepic;
        private ImageView bloodGroupImageView;
        private TextView tvName,tvCity,tvDistance,tvContactNo;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            profilepic=itemView.findViewById(R.id.acceptor_item_layout_profile_image);
            tvName=itemView.findViewById(R.id.acceptor_item_layout_donar_name);
            tvCity=itemView.findViewById(R.id.acceptor_item_layout_donar_city);
            tvDistance=itemView.findViewById(R.id.acceptor_item_layout_donar_distance);
            tvContactNo=itemView.findViewById(R.id.acceptor_item_layout_donar_contact);
            bloodGroupImageView=itemView.findViewById(R.id.acceptor_item_layout_donar_bloodgroup);
        }

        private void setData(int id, String profileUrl, String name, String city,Double distance, String contactNo, String bloodGroup ){
            if(profileUrl.equals("default")){
                profilepic.setImageResource(R.drawable.profile_pic_default);
            }
            else{
                Uri uri=Uri.parse(profileUrl);
                profilepic.setImageURI(uri);
            }
            tvName.setText(name);
            tvCity.setText(city);
            tvDistance.setText(String.valueOf(distance));
            tvContactNo.setText(contactNo);

            switch (bloodGroup){
                case "A+":
                    bloodGroupImageView.setImageResource(R.drawable.a_positive);
                    break;
                case "B+":
                    bloodGroupImageView.setImageResource(R.drawable.b_positive);
                    break;
                case "AB+":
                    bloodGroupImageView.setImageResource(R.drawable.ab_positive);
                    break;
                case "O+":
                    bloodGroupImageView.setImageResource(R.drawable.o_positive);
                    break;
                case "A-":
                    bloodGroupImageView.setImageResource(R.drawable.a_negative);
                    break;
                case "B-":
                    bloodGroupImageView.setImageResource(R.drawable.b_negative);
                    break;
                case "AB-":
                    bloodGroupImageView.setImageResource(R.drawable.ab_negative);
                    break;
                case "O-":
                    bloodGroupImageView.setImageResource(R.drawable.o_negative);
                    break;
            }
        }
    }
}
