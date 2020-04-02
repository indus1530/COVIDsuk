package edu.aku.hassannaqvi.COVIDsuk.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import edu.aku.hassannaqvi.COVIDsuk.R;
import edu.aku.hassannaqvi.COVIDsuk.contracts.FormsContract;
import edu.aku.hassannaqvi.COVIDsuk.core.DatabaseHelper;
import edu.aku.hassannaqvi.COVIDsuk.core.MainApp;
import edu.aku.hassannaqvi.COVIDsuk.databinding.ActivitySectionAaBinding;
import edu.aku.hassannaqvi.COVIDsuk.ui.other.EndingActivity;
import edu.aku.hassannaqvi.COVIDsuk.utils.Util;

public class SectionAAActivity extends AppCompatActivity {

    ActivitySectionAaBinding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_aa);
        bi.setCallback(this);
        db = MainApp.appInfo.getDbHelper();

    }


    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(SectionAAActivity.this, EndingActivity.class).putExtra("complete", true));
            }
        }
    }


    private boolean UpdateDB() {
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
        /*MainApp.fc.setClusterCode(bi.a101.getText().toString());
        MainApp.fc.setHhno(bi.a112.getText().toString());
        MainApp.fc.setLuid(bl.getLUID());*/
        MainApp.setGPS(this); // Set GPS

        JSONObject json = new JSONObject();

        /*json.put("imei", MainApp.IMEI);
        json.put("rndid", bl.get_ID());
        json.put("luid", bl.getLUID());
        json.put("randDT", bl.getRandomDT());
        json.put("hh03", bl.getStructure());
        json.put("hh07", bl.getExtension());
        json.put("hhhead", bl.getHhhead());
        json.put("hh09", bl.getContact());
        json.put("hhss", bl.getSelStructure());
        json.put("hhheadpresent", bi.checkHHHeadpresent.isChecked() ? "1" : "2");
        json.put("hhheadpresentnew", bi.newHHheadname.getText().toString());*/

        json.put("A1", bi.a1.getText().toString());

        json.put("A2", bi.a2a.isChecked() ? "1"
                : bi.a2b.isChecked() ? "2"
                : "0");

        json.put("A3", bi.a3.getText().toString());

        json.put("A4", bi.a4.getText().toString());

        json.put("A5", bi.a5a.isChecked() ? "1"
                : bi.a5b.isChecked() ? "2"
                : "0");

        json.put("A6", bi.a6a.isChecked() ? "1"
                : bi.a6b.isChecked() ? "2"
                : "0");

        json.put("A7", bi.a7a.isChecked() ? "1"
                : bi.a7b.isChecked() ? "2"
                : "0");

        json.put("A81", bi.a81a.isChecked() ? "1"
                : bi.a81b.isChecked() ? "2"
                : "0");

        json.put("A82", bi.a82a.isChecked() ? "1"
                : bi.a82b.isChecked() ? "2"
                : "0");

        json.put("A83", bi.a83a.isChecked() ? "1"
                : bi.a83b.isChecked() ? "2"
                : "0");

        json.put("A84", bi.a84a.isChecked() ? "1"
                : bi.a84b.isChecked() ? "2"
                : "0");

        json.put("A85", bi.a85a.isChecked() ? "1"
                : bi.a85b.isChecked() ? "2"
                : "0");

        json.put("A86", bi.a86a.isChecked() ? "1"
                : bi.a86b.isChecked() ? "2"
                : "0");

        json.put("A87", bi.a87a.isChecked() ? "1"
                : bi.a87b.isChecked() ? "2"
                : "0");

        json.put("A88", bi.a88a.isChecked() ? "1"
                : bi.a88b.isChecked() ? "2"
                : "0");

        json.put("A89", bi.a89a.isChecked() ? "1"
                : bi.a89b.isChecked() ? "2"
                : "0");

        json.put("A810", bi.a810a.isChecked() ? "1"
                : bi.a810b.isChecked() ? "2"
                : "0");

        json.put("A811", bi.a811a.isChecked() ? "1"
                : bi.a811b.isChecked() ? "2"
                : "0");

        json.put("A812", bi.a812a.isChecked() ? "1"
                : bi.a812b.isChecked() ? "2"
                : "0");

        json.put("A813", bi.a813a.isChecked() ? "1"
                : bi.a813b.isChecked() ? "2"
                : "0");

        json.put("A814", bi.a814a.isChecked() ? "1"
                : bi.a814b.isChecked() ? "2"
                : "0");

        json.put("A91", bi.a91a.isChecked() ? "1"
                : bi.a91b.isChecked() ? "2"
                : "0");

        json.put("A92", bi.a92a.isChecked() ? "1"
                : bi.a92b.isChecked() ? "2"
                : "0");

        json.put("A93", bi.a93a.isChecked() ? "1"
                : bi.a93b.isChecked() ? "2"
                : "0");

        json.put("A94", bi.a94a.isChecked() ? "1"
                : bi.a94b.isChecked() ? "2"
                : "0");

        json.put("A95", bi.a95a.isChecked() ? "1"
                : bi.a95b.isChecked() ? "2"
                : "0");

        MainApp.fc.setsInfo(String.valueOf(json));

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionA);
    }

    public void BtnEnd() {
        if (formValidation()) {
            Util.contextEndActivity(this);
        }
    }

}
