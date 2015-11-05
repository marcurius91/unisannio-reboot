package solutions.alterego.android.unisannio.cercapersone;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import solutions.alterego.android.unisannio.R;

public class CercapersoneAdapter extends RecyclerView.Adapter<CercapersoneAdapter.ViewHolder>{

    private List<Person> mPersons;
    private int mRowLayout;

    public CercapersoneAdapter(List<Person> persons, int rowLayout){
        mPersons = persons;
        mRowLayout = rowLayout;
    }
    public void addPersons(List<Person> persons) {
        mPersons.clear();
        mPersons.addAll(persons);
        notifyDataSetChanged();
    }

    public void addPerson(int position, Person person) {
        if (position < 0) {
            position = 0;
        }
        mPersons.add(position, person);
        notifyItemInserted(position);
    }

    @Override
    public CercapersoneAdapter.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(mRowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Person person = mPersons.get(i);
        viewHolder.name.setText(person.getNome());
    }

    @Override
    public int getItemCount() {
        return mPersons == null ? 0 : mPersons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.cercapersone_ingegneria_name);
        }
    }
}
