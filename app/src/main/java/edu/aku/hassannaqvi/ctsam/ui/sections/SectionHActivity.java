package edu.aku.hassannaqvi.ctsam.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.aku.hassannaqvi.ctsam.R;
import edu.aku.hassannaqvi.ctsam.contracts.FormsContract;
import edu.aku.hassannaqvi.ctsam.core.DatabaseHelper;
import edu.aku.hassannaqvi.ctsam.core.MainApp;
import edu.aku.hassannaqvi.ctsam.databinding.ActivitySectionHBinding;
import edu.aku.hassannaqvi.ctsam.ui.other.EndingActivity;
import edu.aku.hassannaqvi.ctsam.utils.Util;

public class SectionHActivity extends AppCompatActivity {

    ActivitySectionHBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_h);
        bi.setCallback(this);

        setlistener();

    }

    private void setlistener() {

        bi.s8q1.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.s8q102.getId()) {
                Clear.clearAllFields(bi.fldGrpCVs8q1a1);
            }

        }));

        bi.s8q2.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.s8q202.getId()) {
                Clear.clearAllFields(bi.fldGrpCVs8q2a1);
            }

        }));

        bi.s8q3.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.s8q302.getId()) {
                Clear.clearAllFields(bi.fldGrpCVs8q3a1);
            }

        }));

        bi.s8q4.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.s8q402.getId()) {
                Clear.clearAllFields(bi.fldGrpCVs8q4a1);
            }

        }));

        bi.s8q5.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.s8q502.getId()) {
                Clear.clearAllFields(bi.fldGrpCVs8q5a1);
            }

        }));

        bi.s8q6.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.s8q602.getId()) {
                Clear.clearAllFields(bi.fldGrpCVs8q6a1);
            }

        }));

        bi.s8q7.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.s8q702.getId()) {
                Clear.clearAllFields(bi.fldGrpCVs8q7a1);
            }

        }));

        bi.s8q8.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.s8q802.getId()) {
                Clear.clearAllFields(bi.fldGrpCVs8q8a1);
            }

        }));

        bi.s8q9.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.s8q902.getId()) {
                Clear.clearAllFields(bi.fldGrpCVs8q9a1);
            }

        }));

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
                startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
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

        json.put("s8q1",
                bi.s8q101.isChecked() ? "1" :
                        bi.s8q102.isChecked() ? "2" :
                                "0");

        json.put("s8q1a1",
                bi.s8q1a101.isChecked() ? "1" :
                        bi.s8q1a102.isChecked() ? "2" :
                                bi.s8q1a103.isChecked() ? "3" :
                                        "0");

        json.put("s8q2",
                bi.s8q201.isChecked() ? "1" :
                        bi.s8q202.isChecked() ? "2" :
                                "0");

        json.put("s8q2a1",
                bi.s8q2a101.isChecked() ? "1" :
                        bi.s8q2a102.isChecked() ? "2" :
                                bi.s8q2a103.isChecked() ? "3" :
                                        "0");

        json.put("s8q3",
                bi.s8q301.isChecked() ? "1" :
                        bi.s8q302.isChecked() ? "2" :
                                "0");

        json.put("s8q3a1",
                bi.s8q3a101.isChecked() ? "1" :
                        bi.s8q3a102.isChecked() ? "2" :
                                bi.s8q3a103.isChecked() ? "3" :
                                        "0");

        json.put("s8q4",
                bi.s8q401.isChecked() ? "1" :
                        bi.s8q402.isChecked() ? "2" :
                                "0");

        json.put("s8q4a1",
                bi.s8q4a101.isChecked() ? "1" :
                        bi.s8q4a102.isChecked() ? "2" :
                                bi.s8q4a103.isChecked() ? "3" :
                                        "0");

        json.put("s8q5",
                bi.s8q501.isChecked() ? "1" :
                        bi.s8q502.isChecked() ? "2" :
                                "0");

        json.put("s8q5a1",
                bi.s8q5a101.isChecked() ? "1" :
                        bi.s8q5a102.isChecked() ? "2" :
                                bi.s8q5a103.isChecked() ? "3" :
                                        "0");

        json.put("s8q6",
                bi.s8q601.isChecked() ? "1" :
                        bi.s8q602.isChecked() ? "2" :
                                "0");

        json.put("s8q6a1",
                bi.s8q6a101.isChecked() ? "1" :
                        bi.s8q6a102.isChecked() ? "2" :
                                bi.s8q6a103.isChecked() ? "3" :
                                        "0");

        json.put("s8q7",
                bi.s8q701.isChecked() ? "1" :
                        bi.s8q702.isChecked() ? "2" :
                                "0");

        json.put("s8q7a1",
                bi.s8q7a101.isChecked() ? "1" :
                        bi.s8q7a102.isChecked() ? "2" :
                                bi.s8q7a103.isChecked() ? "3" :
                                        "0");

        json.put("s8q8",
                bi.s8q801.isChecked() ? "1" :
                        bi.s8q802.isChecked() ? "2" :
                                "0");

        json.put("s8q8a1",
                bi.s8q8a101.isChecked() ? "1" :
                        bi.s8q8a102.isChecked() ? "2" :
                                bi.s8q8a103.isChecked() ? "3" :
                                        "0");

        json.put("s8q9",
                bi.s8q901.isChecked() ? "1" :
                        bi.s8q902.isChecked() ? "2" :
                                "0");

        json.put("s8q9a1",
                bi.s8q9a101.isChecked() ? "1" :
                        bi.s8q9a102.isChecked() ? "2" :
                                bi.s8q9a103.isChecked() ? "3" :
                                        "0");

        MainApp.fc.setsA3(String.valueOf(json));


    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionH);
    }


    public void BtnEnd() {
        if (formValidation()) {
            Util.contextEndActivity(this);
        }
    }


}
