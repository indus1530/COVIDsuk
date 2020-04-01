package edu.aku.hassannaqvi.ctsam.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.aku.hassannaqvi.ctsam.R;
import edu.aku.hassannaqvi.ctsam.contracts.FormsContract;
import edu.aku.hassannaqvi.ctsam.core.DatabaseHelper;
import edu.aku.hassannaqvi.ctsam.core.MainApp;
import edu.aku.hassannaqvi.ctsam.databinding.ActivitySectionABinding;
import edu.aku.hassannaqvi.ctsam.ui.other.EndingActivity;
import edu.aku.hassannaqvi.ctsam.utils.Util;

public class SectionAActivity extends AppCompatActivity {

    ActivitySectionABinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_a);
        bi.setCallback(this);
        setupSkips();

    }


    private void setupSkips() {

        /*bi.s1q8.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == bi.s1q8b.getId()) {
                bi.fldGrpCVs1q8r.setVisibility(View.VISIBLE);
            } else {
                Clear.clearAllFields(bi.fldGrpCVs1q8r);
                bi.fldGrpCVs1q8r.setVisibility(View.GONE);
            }
        });*/

    }


    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            if (UpdateDB()) {
                finish();
                if (bi.s1q8b.isChecked()) {
                    startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));
                } else {
                    startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
                }
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean UpdateDB() {

        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        long updcount = db.addForm(MainApp.fc);
        MainApp.fc.set_ID(String.valueOf(updcount));
        if (updcount > 0) {
            MainApp.fc.set_UID(MainApp.fc.getDeviceID() + MainApp.fc.get_ID());
            db.updatesFormColumn(FormsContract.FormsTable.COLUMN_UID, MainApp.fc.get_UID());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void SaveDraft() throws JSONException {

        MainApp.fc = new FormsContract();
        MainApp.fc.setFormDate(new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime()));
        MainApp.fc.setUser(MainApp.userName);
        MainApp.fc.setDeviceID(MainApp.appInfo.getDeviceID());
        MainApp.fc.setDevicetagID(MainApp.appInfo.getTagName());
        MainApp.fc.setAppversion(MainApp.appInfo.getAppVersion());
        //MainApp.fc.setClusterCode(bi.a101.getText().toString());
        //MainApp.fc.setHhno(bi.a112.getText().toString());
        // MainApp.fc.setLuid(bl.getLUID());
        MainApp.setGPS(this); // Set GPS

        JSONObject json = new JSONObject();

        json.put("s1q1", bi.s1q1.getChildCount() == 0 ? "0" : bi.s1q1.getSelectedItem().toString());

        json.put("s1q2", bi.s1q2.getChildCount() == 0 ? "0" : bi.s1q2.getSelectedItem().toString());

        json.put("s1q3", bi.s1q3.getChildCount() == 0 ? "0" : bi.s1q3.getSelectedItem().toString());

        json.put("s1q4", bi.s1q4.getText().toString());

        json.put("s1q5", bi.s1q5.getText().toString());

        json.put("s1q6", bi.s1q6.getText().toString());

        json.put("s1q7", bi.s1q7.getText().toString());

        json.put("s1q8", bi.s1q8a.isChecked() ? "1"
                : bi.s1q8b.isChecked() ? "2"
                : "0");

        json.put("s1q8r", bi.s1q8r.getText().toString());

        MainApp.fc.setsInfo(String.valueOf(json));


    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }


    public void BtnEnd() {
        if (formValidation()) {
            Util.contextEndActivity(this);
        }
    }


}
