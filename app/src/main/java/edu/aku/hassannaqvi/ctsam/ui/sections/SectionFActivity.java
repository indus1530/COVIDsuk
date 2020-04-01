package edu.aku.hassannaqvi.ctsam.ui.sections;

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

import edu.aku.hassannaqvi.ctsam.R;
import edu.aku.hassannaqvi.ctsam.contracts.FormsContract;
import edu.aku.hassannaqvi.ctsam.core.DatabaseHelper;
import edu.aku.hassannaqvi.ctsam.core.MainApp;
import edu.aku.hassannaqvi.ctsam.databinding.ActivitySectionFBinding;
import edu.aku.hassannaqvi.ctsam.ui.other.EndingActivity;
import edu.aku.hassannaqvi.ctsam.utils.Util;

public class SectionFActivity extends AppCompatActivity {

    ActivitySectionFBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_f);
        bi.setCallback(this);
        setupSkips();

    }

    private void setupSkips() {

        bi.s6q3.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.s6q302.getId()) {
                bi.fldGrpCVs6q4.setVisibility(View.GONE);
                Clear.clearAllFields(bi.fldGrpCVs6q4);
            } else {
                bi.fldGrpCVs6q4.setVisibility(View.VISIBLE);
            }

        }));

        bi.s6q6.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.s6q602.getId()) {
                bi.fldGrpCVs6q7.setVisibility(View.GONE);
                Clear.clearAllFields(bi.fldGrpCVs6q7);
            } else {
                bi.fldGrpCVs6q7.setVisibility(View.VISIBLE);
            }

        }));

        bi.s6q9.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.s6q910.getId()) {
                bi.fldGrpSectionf01.setVisibility(View.GONE);
                Clear.clearAllFields(bi.fldGrpSectionf01);
            } else {
                bi.fldGrpSectionf01.setVisibility(View.VISIBLE);
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

        json.put("s6q1",
                bi.s6q101.isChecked() ? "1" :
                        bi.s6q102.isChecked() ? "2" :
                                bi.s6q103.isChecked() ? "3" :
                                        bi.s6q104.isChecked() ? "4" :
                                                bi.s6q105.isChecked() ? "5" :
                                                        bi.s6q106.isChecked() ? "6" :
                                                                bi.s6q107.isChecked() ? "7" :
                                                                        bi.s6q196.isChecked() ? "96" :
                                                                                "0");
        json.put("s6q196x", bi.s6q196x.getText().toString());

        json.put("s6q2",
                bi.s6q201.isChecked() ? "1" :
                        bi.s6q202.isChecked() ? "2" :
                                bi.s6q203.isChecked() ? "3" :
                                        bi.s6q204.isChecked() ? "4" :
                                                bi.s6q205.isChecked() ? "5" :
                                                        bi.s6q206.isChecked() ? "6" :
                                                                bi.s6q207.isChecked() ? "7" :
                                                                        bi.s6q208.isChecked() ? "8" :
                                                                                bi.s6q209.isChecked() ? "9" :
                                                                                        bi.s6q210.isChecked() ? "10" :
                                                                                                bi.s6q211.isChecked() ? "11" :
                                                                                                        bi.s6q212.isChecked() ? "12" :
                                                                                                                bi.s6q213.isChecked() ? "13" :
                                                                                                                        bi.s6q214.isChecked() ? "14" :
                                                                                                                                bi.s6q215.isChecked() ? "15" :
                                                                                                                                        bi.s6q216.isChecked() ? "16" :
                                                                                                                                                bi.s6q296.isChecked() ? "96" :
                                                                                                                                                        "0");
        json.put("s6q296x", bi.s6q296x.getText().toString());

        json.put("s6q3",
                bi.s6q301.isChecked() ? "1" :
                        bi.s6q302.isChecked() ? "2" :
                                "0");

        json.put("s6q4",
                bi.s6q401.isChecked() ? "1" :
                        bi.s6q402.isChecked() ? "2" :
                                bi.s6q403.isChecked() ? "3" :
                                        bi.s6q404.isChecked() ? "4" :
                                                bi.s6q405.isChecked() ? "5" :
                                                        bi.s6q406.isChecked() ? "6" :
                                                                bi.s6q496.isChecked() ? "96" :
                                                                        "0");
        json.put("s6q496x", bi.s6q496x.getText().toString());

        json.put("s6q5",
                bi.s6q501.isChecked() ? "1" :
                        bi.s6q502.isChecked() ? "2" :
                                bi.s6q503.isChecked() ? "3" :
                                        bi.s6q504.isChecked() ? "4" :
                                                bi.s6q505.isChecked() ? "5" :
                                                        bi.s6q506.isChecked() ? "6" :
                                                                bi.s6q507.isChecked() ? "7" :
                                                                        bi.s6q508.isChecked() ? "8" :
                                                                                bi.s6q509.isChecked() ? "9" :
                                                                                        bi.s6q596.isChecked() ? "96" :
                                                                                                "0");
        json.put("s6q596x", bi.s6q596x.getText().toString());

        json.put("s6q6",
                bi.s6q601.isChecked() ? "1" :
                        bi.s6q602.isChecked() ? "2" :
                                "0");

        //f1.put("s6q7", bi.s6q7.getText().toString());
        json.put("s6q701", bi.s6q701.getText().toString());
        json.put("s6q8a", bi.s6q8a.isChecked() ? "1" : "0");
        json.put("s6q8b", bi.s6q8b.isChecked() ? "2" : "0");
        json.put("s6q8c", bi.s6q8c.isChecked() ? "3" : "0");
        json.put("s6q8d", bi.s6q8d.isChecked() ? "4" : "0");
        json.put("s6q8e", bi.s6q8e.isChecked() ? "5" : "0");
        json.put("s6q8f", bi.s6q8f.isChecked() ? "6" : "0");
        json.put("s6q8g", bi.s6q8g.isChecked() ? "7" : "0");
        json.put("s6q8h", bi.s6q8h.isChecked() ? "8" : "0");
        json.put("s6q8i", bi.s6q8i.isChecked() ? "9" : "0");
        json.put("s6q8j", bi.s6q8j.isChecked() ? "10" : "0");
        json.put("s6q8k", bi.s6q8k.isChecked() ? "11" : "0");
        json.put("s6q8l", bi.s6q8l.isChecked() ? "12" : "0");
        json.put("s6q8m", bi.s6q8m.isChecked() ? "13" : "0");
        json.put("s6q8n", bi.s6q8n.isChecked() ? "14" : "0");
        json.put("s6q8o", bi.s6q8o.isChecked() ? "15" : "0");
        json.put("s6q8p", bi.s6q8p.isChecked() ? "16" : "0");
        json.put("s6q8q", bi.s6q8q.isChecked() ? "17" : "0");
        json.put("s6q8r", bi.s6q8r.isChecked() ? "18" : "0");
        json.put("s6q8s", bi.s6q8s.isChecked() ? "19" : "0");

        json.put("s6q9",
                bi.s6q901.isChecked() ? "1" :
                        bi.s6q902.isChecked() ? "2" :
                                bi.s6q903.isChecked() ? "3" :
                                        bi.s6q904.isChecked() ? "4" :
                                                bi.s6q905.isChecked() ? "5" :
                                                        bi.s6q906.isChecked() ? "6" :
                                                                bi.s6q907.isChecked() ? "7" :
                                                                        bi.s6q908.isChecked() ? "8" :
                                                                                bi.s6q909.isChecked() ? "9" :
                                                                                        bi.s6q910.isChecked() ? "10" :
                                                                                                bi.s6q996.isChecked() ? "96" :
                                                                                                        "0");
        json.put("s6q996x", bi.s6q996x.getText().toString());

        json.put("s6q10",
                bi.s6q1001.isChecked() ? "1" :
                        bi.s6q1002.isChecked() ? "2" :
                                bi.s6q1003.isChecked() ? "3" :
                                        bi.s6q1096.isChecked() ? "96" :
                                                "0");
        json.put("s6q1096x", bi.s6q1096x.getText().toString());

        json.put("s6q11",
                bi.s6q1101.isChecked() ? "1" :
                        bi.s6q1102.isChecked() ? "2" :
                                "0");

        json.put("s6q12",
                bi.s6q1201.isChecked() ? "1" :
                        bi.s6q1202.isChecked() ? "2" :
                                bi.s6q1203.isChecked() ? "3" :
                                        bi.s6q1204.isChecked() ? "4" :
                                                bi.s6q1205.isChecked() ? "5" :
                                                        bi.s6q1206.isChecked() ? "6" :
                                                                bi.s6q1207.isChecked() ? "7" :
                                                                        bi.s6q1208.isChecked() ? "8" :
                                                                                bi.s6q1209.isChecked() ? "9" :
                                                                                        bi.s6q1210.isChecked() ? "10" :
                                                                                                bi.s6q1211.isChecked() ? "11" :
                                                                                                        bi.s6q1296.isChecked() ? "96" :
                                                                                                                "0");
        json.put("s6q1296x", bi.s6q1296x.getText().toString());

        json.put("s6q13",
                bi.s6q1301.isChecked() ? "1" :
                        bi.s6q1302.isChecked() ? "2" :
                                bi.s6q1303.isChecked() ? "3" :
                                        bi.s6q1304.isChecked() ? "4" :
                                                bi.s6q1305.isChecked() ? "5" :
                                                        bi.s6q1306.isChecked() ? "6" :
                                                                bi.s6q1307.isChecked() ? "7" :
                                                                        bi.s6q1308.isChecked() ? "8" :
                                                                                bi.s6q1309.isChecked() ? "9" :
                                                                                        bi.s6q1310.isChecked() ? "10" :
                                                                                                bi.s6q1311.isChecked() ? "11" :
                                                                                                        bi.s6q1312.isChecked() ? "12" :
                                                                                                                bi.s6q1313.isChecked() ? "13" :
                                                                                                                        bi.s6q1396.isChecked() ? "96" :
                                                                                                                                "0");
        json.put("s6q1396x", bi.s6q1396x.getText().toString());

        json.put("s6q14",
                bi.s6q1401.isChecked() ? "1" :
                        bi.s6q1402.isChecked() ? "2" :
                                bi.s6q1403.isChecked() ? "3" :
                                        bi.s6q1404.isChecked() ? "4" :
                                                bi.s6q1405.isChecked() ? "5" :
                                                        bi.s6q1406.isChecked() ? "6" :
                                                                bi.s6q1407.isChecked() ? "7" :
                                                                        bi.s6q1408.isChecked() ? "8" :
                                                                                bi.s6q1409.isChecked() ? "9" :
                                                                                        bi.s6q1410.isChecked() ? "10" :
                                                                                                bi.s6q1411.isChecked() ? "11" :
                                                                                                        bi.s6q1496.isChecked() ? "96" :
                                                                                                                "0");
        json.put("s6q1496x", bi.s6q1496x.getText().toString());

        json.put("s6q15", bi.s6q15.getText().toString());

        json.put("s6q16a",
                bi.s6q16a01.isChecked() ? "1" :
                        bi.s6q16a02.isChecked() ? "2" :
                                "0");

        json.put("s6q16b",
                bi.s6q16b01.isChecked() ? "1" :
                        bi.s6q16b02.isChecked() ? "2" :
                                "0");

        json.put("s6q16c",
                bi.s6q16c01.isChecked() ? "1" :
                        bi.s6q16c02.isChecked() ? "2" :
                                "0");

        json.put("s6q16d",
                bi.s6q16d01.isChecked() ? "1" :
                        bi.s6q16d02.isChecked() ? "2" :
                                "0");

        json.put("s6q16e",
                bi.s6q16e01.isChecked() ? "1" :
                        bi.s6q16e02.isChecked() ? "2" :
                                "0");

        json.put("s6q16f",
                bi.s6q16f01.isChecked() ? "1" :
                        bi.s6q16f02.isChecked() ? "2" :
                                "0");

        json.put("s6q16g",
                bi.s6q16g01.isChecked() ? "1" :
                        bi.s6q16g02.isChecked() ? "2" :
                                "0");

        json.put("s6q16h",
                bi.s6q16h01.isChecked() ? "1" :
                        bi.s6q16h02.isChecked() ? "1" :
                                "0");

        json.put("s6q16i",
                bi.s6q16i01.isChecked() ? "1" :
                        bi.s6q16i02.isChecked() ? "2" :
                                "0");

        json.put("s6q16j",
                bi.s6q16j01.isChecked() ? "1" :
                        bi.s6q16j02.isChecked() ? "2" :
                                "0");

        json.put("s6q17",
                bi.s6q1701.isChecked() ? "1" :
                        bi.s6q1702.isChecked() ? "2" :
                                "0");

        json.put("s6q18",
                bi.s6q1801.isChecked() ? "" :
                        bi.s6q1802.isChecked() ? "" :
                                bi.s6q1899.isChecked() ? "98" :
                                        "0");
        json.put("s6q1801x", bi.s6q1801x.getText().toString());
        json.put("s6q1802x", bi.s6q1802x.getText().toString());

        json.put("s6q19",
                bi.s6q1901.isChecked() ? "1" :
                        bi.s6q1902.isChecked() ? "2" :
                                "0");

        json.put("s6q20",
                bi.s6q20a.isChecked() ? "" :
                        bi.s6q20b.isChecked() ? "" :
                                bi.s6q20c.isChecked() ? "" :
                                        bi.s6q20d.isChecked() ? "" :
                                                bi.s6q20e.isChecked() ? "" :
                                                        bi.s6q20f.isChecked() ? "" :
                                                                bi.s6q20g.isChecked() ? "" :
                                                                        "0");

        json.put("s6q20ax", bi.s6q20ax.getText().toString());
        json.put("s6q20bx", bi.s6q20bx.getText().toString());
        json.put("s6q20cx", bi.s6q20cx.getText().toString());
        json.put("s6q20dx", bi.s6q20dx.getText().toString());
        json.put("s6q20ex", bi.s6q20ex.getText().toString());
        json.put("s6q20fx", bi.s6q20fx.getText().toString());
        json.put("s6q20gx", bi.s6q20gx.getText().toString());

        MainApp.fc.setsA3(String.valueOf(json));


    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionF);
    }


    public void BtnEnd() {
        if (formValidation()) {
            Util.contextEndActivity(this);
        }
    }

}
