package solutions.alterego.android.unisannio.cercapersone;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaCercapersoneDetailActivity;
import timber.log.Timber;

public class CercapersoneAdapter extends RecyclerView.Adapter<CercapersoneAdapter.ViewHolder> {

    private List<Person> mPersons;
    private int mRowLayout;
    private View v;

    public CercapersoneAdapter(List<Person> persons, int rowLayout) {
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
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(mRowLayout, viewGroup, false);
        ViewHolder vh = new ViewHolder(v,mPersons);
        return new ViewHolder(v,mPersons);
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        ArrayList<Person> pers;

        public ViewHolder(View itemView, List<Person> persons) {
            super(itemView);
            pers = (ArrayList<Person>) persons;
            name = (TextView) itemView.findViewById(R.id.cercapersone_ingegneria_name);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            Timber.e("The Item Clicked position: " + pers.get(getPosition()).getPerson().getNome());
            Person prs = pers.get(getPosition()).getPerson();
            Intent intent = new Intent(v.getContext(), IngegneriaCercapersoneDetailActivity.class);
            intent.putExtra("PERSON", prs);
            v.getContext().startActivity(intent);
        }
    }
}
