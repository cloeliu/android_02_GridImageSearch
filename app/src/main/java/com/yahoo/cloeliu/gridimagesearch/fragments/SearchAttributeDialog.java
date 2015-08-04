package com.yahoo.cloeliu.gridimagesearch.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.yahoo.cloeliu.gridimagesearch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchAttributeDialog extends DialogFragment {

    private Button btnDone;
    private EditText etSite;
    private Spinner spType;
    private Spinner spColor;
    private Spinner spSize;
    private ArrayAdapter<CharSequence> aImageTypes;
    private ArrayAdapter<CharSequence> aImageColors;
    private ArrayAdapter<CharSequence> aImageSizes;

    private String imgType = "";
    private String imgColor = "";
    private String imgSize = "";
    private String imgSite = "";

    public SearchAttributeDialog() {
        // Required empty public constructor
    }

    public interface SearchAttributeDialogListener {
        void onFinishSearchAttributeDialog(String type, String color, String size, String site);
    }

    public static SearchAttributeDialog newInstance(String title) {
        SearchAttributeDialog frag = new SearchAttributeDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_attribute_dialog, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set type spinner
        spType = (Spinner) view.findViewById(R.id.spType);
        aImageTypes = ArrayAdapter.createFromResource(getActivity(), R.array.sp_type, android.R.layout.simple_spinner_item);
        aImageTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(aImageTypes);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imgType = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // set color spinner
        spColor = (Spinner) view.findViewById(R.id.spColor);
        aImageColors = ArrayAdapter.createFromResource(getActivity(), R.array.sp_color, android.R.layout.simple_spinner_item);
        aImageColors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColor.setAdapter(aImageColors);
        spColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imgColor = parent.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // set size spinner
        spSize = (Spinner) view.findViewById(R.id.spSize);
        aImageSizes = ArrayAdapter.createFromResource(getActivity(), R.array.sp_size, android.R.layout.simple_spinner_item);
        aImageSizes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSize.setAdapter(aImageSizes);
        spSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imgSize = parent.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        // set site edittext
        etSite = (EditText) view.findViewById(R.id.etSite);

        // set Done
        btnDone = (Button) view.findViewById(R.id.btn_filter);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSite = etSite.getText().toString();
                SearchAttributeDialogListener listener = (SearchAttributeDialogListener) getActivity();
                listener.onFinishSearchAttributeDialog(imgType, imgColor, imgSize, imgSite);
                dismiss();
            }
        });

        return view;
    }

}
