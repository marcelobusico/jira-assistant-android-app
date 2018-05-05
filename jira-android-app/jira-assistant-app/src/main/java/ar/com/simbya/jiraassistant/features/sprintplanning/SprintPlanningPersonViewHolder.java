package ar.com.simbya.jiraassistant.features.sprintplanning;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;

import java.util.Locale;

import ar.com.simbya.jiraassistant.R;
import ar.com.simbya.jiraassistant.serviceAdapters.PicassoFactory;

public class SprintPlanningPersonViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = SprintPlanningPersonViewHolder.class.getName();
    private final Context context;
    private final PicassoFactory picassoFactory;
    public ImageView personImageView;
    public TextView personNameTextView;
    public TextView currentlyAssignedTasksValueTextView;
    public TextView totalAssignedTimeValueTextView;
    public TextView totalWorkedTimeValueTextView;
    public TextView remainingAssignedTasksValueTextView;
    public TextView remainingCapacityValueTextView;

    public SprintPlanningPersonViewHolder(View itemView, Context context, PicassoFactory picassoFactory) {
        super(itemView);
        this.context = context;
        this.picassoFactory = picassoFactory;

        personImageView = itemView.findViewById(R.id.personImageView);
        personNameTextView = itemView.findViewById(R.id.personNameTextView);
        currentlyAssignedTasksValueTextView = itemView.findViewById(R.id.currentlyAssignedTasksValueTextView);
        totalAssignedTimeValueTextView = itemView.findViewById(R.id.totalAssignedTimeValueTextView);
        totalWorkedTimeValueTextView = itemView.findViewById(R.id.totalWorkedTimeValueTextView);
        remainingAssignedTasksValueTextView = itemView.findViewById(R.id.remainingAssignedTasksValueTextView);
        remainingCapacityValueTextView = itemView.findViewById(R.id.remainingCapacityValueTextView);
    }

    public void refreshWithViewModel(final SprintPlanningPersonViewModel viewModel) {
        personImageView.setImageResource(R.mipmap.ic_launcher_round);
        personNameTextView.setText("");
        currentlyAssignedTasksValueTextView.setText("");
        totalAssignedTimeValueTextView.setText("");
        totalWorkedTimeValueTextView.setText("");
        remainingAssignedTasksValueTextView.setText("");
        remainingCapacityValueTextView.setText("");

        if (viewModel == null) {
            return;
        }

        try {
            picassoFactory.getPicasso()
                    .load(viewModel.getPersonImageUrl())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(personImageView);
        } catch (Exception ex) {
            Log.e(TAG, "Could not load image avatar", ex);
        }

        String issuesSuffix = context.getResources().getQuantityString(R.plurals.suffix_issues, viewModel.getCurrentlyAssignedTasks());
        String hoursSuffix = context.getString(R.string.suffix_hours);

        personNameTextView.setText(viewModel.getPersonName());

        currentlyAssignedTasksValueTextView.setText(String.format(Locale.getDefault(), "%d %s",
                viewModel.getCurrentlyAssignedTasks(), issuesSuffix));

        totalAssignedTimeValueTextView.setText(String.format(Locale.getDefault(), "%d %s",
                viewModel.getTotalAssignedTime(), hoursSuffix));

        totalWorkedTimeValueTextView.setText(String.format(Locale.getDefault(), "%d %s",
                viewModel.getTotalWorkedTime(), hoursSuffix));

        remainingAssignedTasksValueTextView.setText(String.format(Locale.getDefault(), "%d %s",
                viewModel.getRemainingAssignedTasks(), hoursSuffix));

        remainingCapacityValueTextView.setText(String.format(Locale.getDefault(), "%d %s",
                viewModel.getRemainingCapacity(), hoursSuffix));
    }
}
