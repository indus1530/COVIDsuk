package edu.aku.hassannaqvi.COVIDsuk.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.aku.hassannaqvi.COVIDsuk.R;
import edu.aku.hassannaqvi.COVIDsuk.contracts.FormsContract;
import edu.aku.hassannaqvi.COVIDsuk.core.DatabaseHelper;
import edu.aku.hassannaqvi.COVIDsuk.core.MainApp;
import edu.aku.hassannaqvi.COVIDsuk.databinding.ActivitySectionEBinding;
import edu.aku.hassannaqvi.COVIDsuk.ui.other.EndingActivity;
import edu.aku.hassannaqvi.COVIDsuk.utils.Util;

public class SectionEActivity extends AppCompatActivity {

    ActivitySectionEBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_e);
        bi.setCallback(this);


        //s5q197
        bi.s5q197.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Clear.clearAllFields(bi.s5q1all);
                Clear.clearAllFields(bi.fldGrpCVs5q2);
                Clear.clearAllFields(bi.fldGrpCVs5q3);
                Clear.clearAllFields(bi.fldGrpCVs5q4);
                Clear.clearAllFields(bi.fldGrpCVs5q5);
                bi.s5q1all.setVisibility(View.GONE);
                bi.fldGrpCVs5q2.setVisibility(View.GONE);
                bi.fldGrpCVs5q3.setVisibility(View.GONE);
                bi.fldGrpCVs5q4.setVisibility(View.GONE);
                bi.fldGrpCVs5q5.setVisibility(View.GONE);
            } else {
                Clear.clearAllFields(bi.s5q1all);
                bi.s5q1all.setVisibility(View.VISIBLE);
                bi.fldGrpCVs5q2.setVisibility(View.VISIBLE);
                bi.fldGrpCVs5q3.setVisibility(View.VISIBLE);
                bi.fldGrpCVs5q4.setVisibility(View.VISIBLE);
                bi.fldGrpCVs5q5.setVisibility(View.VISIBLE);
            }
        });


        bi.s5q2.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.s5q2a.getId()) {
                bi.fldGrpCVs5q3.setVisibility(View.VISIBLE);
                bi.fldGrpCVs5q4.setVisibility(View.VISIBLE);
                bi.fldGrpCVs5q5.setVisibility(View.VISIBLE);
            } else {
                Clear.clearAllFields(bi.fldGrpCVs5q3);
                Clear.clearAllFields(bi.fldGrpCVs5q4);
                Clear.clearAllFields(bi.fldGrpCVs5q5);
                bi.fldGrpCVs5q3.setVisibility(View.GONE);
                bi.fldGrpCVs5q4.setVisibility(View.GONE);
                bi.fldGrpCVs5q5.setVisibility(View.GONE);
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

        json.put("s5q101",
                bi.s5q101a.isChecked() ? "1" :
                        bi.s5q101b.isChecked() ? "2" :
                                "0");
        json.put("s5q102",
                bi.s5q102a.isChecked() ? "1" :
                        bi.s5q102b.isChecked() ? "2" :
                                "0");
        json.put("s5q103",
                bi.s5q103a.isChecked() ? "1" :
                        bi.s5q103b.isChecked() ? "2" :
                                "0");
        json.put("s5q104",
                bi.s5q104a.isChecked() ? "1" :
                        bi.s5q104b.isChecked() ? "2" :
                                "0");
        json.put("s5q105",
                bi.s5q105a.isChecked() ? "1" :
                        bi.s5q105b.isChecked() ? "2" :
                                "0");
        json.put("s5q106",
                bi.s5q106a.isChecked() ? "1" :
                        bi.ss5q106b.isChecked() ? "2" :
                                "0");
        json.put("s5q107",
                bi.s5q107a.isChecked() ? "1" :
                        bi.s5q107b.isChecked() ? "2" :
                                "0");
        json.put("s5q108",
                bi.s5q108a.isChecked() ? "1" :
                        bi.s5q108b.isChecked() ? "2" :
                                "0");
        json.put("s5q196",
                bi.s5q196a.isChecked() ? "1" :
                        bi.s5q196b.isChecked() ? "2" :
                                "0");

        json.put("s5q196x", bi.s5q196x.getText().toString());

        json.put("s5q2",
                bi.s5q2a.isChecked() ? "1" :
                        bi.s5q2b.isChecked() ? "2" :
                                "0");

        json.put("s5q197", bi.s5q197.isChecked() ? "97" : "0");

        //json.put("s5q3_subtitlea",bi.s5q3_subtitlea.isChecked() ?"" :"0");
        json.put("s5q301", bi.s5q301.isChecked() ? "1" : "0");
        json.put("s5q302", bi.s5q302.isChecked() ? "2" : "0");
        json.put("s5q303", bi.s5q303.isChecked() ? "3" : "0");
        //json.put("s5q3_subtitleb",bi.s5q3_subtitleb.isChecked() ?"" :"0");
        //json.put("s5q304",bi.s5q304.isChecked() ?"4" :"0");
        json.put("s5q305", bi.s5q305.isChecked() ? "5" : "0");
        json.put("s5q306", bi.s5q306.isChecked() ? "6" : "0");
        json.put("s5q307", bi.s5q307.isChecked() ? "7" : "0");
        json.put("s5q308", bi.s5q308.isChecked() ? "8" : "0");
        json.put("s5q3096", bi.s5q3096.isChecked() ? "96" : "0");
        json.put("s5q3096x", bi.s5q3096x.getText().toString());

        json.put("s5q4",
                bi.s5q4a.isChecked() ? "1" :
                        bi.s5q4b.isChecked() ? "2" :
                                "0");

        json.put("s5q5", bi.s5q5.getText().toString());

        MainApp.fc.setsA3(String.valueOf(json));


    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionE);
    }


    public void BtnEnd() {
        if (formValidation()) {
            Util.contextEndActivity(this);
        }
    }


}
