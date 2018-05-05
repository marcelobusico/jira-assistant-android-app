package ar.com.simbya.jiraassistant.features.sprintplanning;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.simbya.jiraassistant.R;
import ar.com.simbya.jiraassistant.serviceAdapters.PicassoFactory;

public class SprintPlanningTableAdapter extends RecyclerView.Adapter<SprintPlanningPersonViewHolder> {

    @NonNull
    private final SprintPlanningPresenter presenter;

    @NonNull
    private final PicassoFactory picassoFactory;

    public SprintPlanningTableAdapter(SprintPlanningPresenter presenter, PicassoFactory picassoFactory) {
        this.presenter = presenter;
        this.picassoFactory = picassoFactory;
    }

    @Override
    public SprintPlanningPersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_person_planning_card, parent, false);
        return new SprintPlanningPersonViewHolder(view, parent.getContext(), picassoFactory);
    }

    @Override
    public void onBindViewHolder(final SprintPlanningPersonViewHolder holder, int position) {
        SprintPlanningPersonViewModel viewModel = presenter.elementViewModel(position);
        holder.refreshWithViewModel(viewModel);
    }

    @Override
    public int getItemCount() {
        return presenter.numberOfElements();
    }
}
