package com.davidmiguel.gobees.addedithive;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidmiguel.gobees.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Display add hive form.
 */
public class AddEditHiveFragment extends Fragment implements AddEditHiveContract.View {

    public static final String ARGUMENT_EDIT_APIARY_ID = "EDIT_APAIRY_ID";
    public static final String ARGUMENT_EDIT_HIVE_ID = "EDIT_HIVE_ID";

    private AddEditHiveContract.Presenter presenter;

    private TextView nameTextView;

    private TextView notesTextView;

    public AddEditHiveFragment() {
        // Requires empty public constructor
    }

    public static AddEditHiveFragment newInstance() {
        return new AddEditHiveFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.addedithive_frag, container, false);
        nameTextView = (TextView) root.findViewById(R.id.add_hive_name);
        notesTextView = (TextView) root.findViewById(R.id.add_hive_notes);

        setHasOptionsMenu(true);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configure floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_hive);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveHive(nameTextView.getText().toString(),
                        notesTextView.getText().toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void showEmptyHiveError() {
        showMessage(nameTextView, getString(R.string.empty_hive_message));
    }

    @Override
    public void showSaveHiveError() {
        showMessage(getView(), getString(R.string.save_hive_error_message));
    }

    @Override
    public void showHivesList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void setName(String name) {
        nameTextView.setText(name);
    }

    @Override
    public void setNotes(String notes) {
        nameTextView.setText(notes);
    }

    @Override
    public void setPresenter(@NonNull AddEditHiveContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    /**
     * Shows a snackbar with the given message.
     * @param view view.
     * @param message message to show.
     */
    @SuppressWarnings("ConstantConditions")
    private void showMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}
