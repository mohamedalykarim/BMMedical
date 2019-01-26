package com.banquemisr.www.bmmedical.ui.show_approvals;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.banquemisr.www.bmmedical.Adapters.ApprovalsAdapter;
import com.banquemisr.www.bmmedical.Adapters.ImagedAttachedAdapter;
import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.ActivityShowApprovalBinding;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModel;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModelFactory;
import com.banquemisr.www.bmmedical.utilities.ApprovalUtils;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

import java.util.ArrayList;




public class ShowApprovalsActivity extends AppCompatActivity {
    private static final String APPROVAL_TYPE = "approval_type";
    private static final int CHOOSE_IMAGE_CODE = 1;

    ActivityShowApprovalBinding binding;
    private ShowApprovalsVH showApprovalsVM;
    GridView attachmentGridView;
    ListView approvalListView;
    private LoginViewModel loginViewModel;
    View defaultAddRequestContainer;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_approval);
        binding.setLifecycleOwner(this);

        Intent oldIntent = getIntent();
        if(null != oldIntent){
            if(oldIntent.hasExtra(APPROVAL_TYPE)){


                String approvalType = oldIntent.getStringExtra(APPROVAL_TYPE);
                chooseEmptyImage(approvalType);
                binding.approvalEmptyText.setText(approvalType +"...");







                /**
                 *  Login View Model
                 */

                LoginViewModelFactory loginFactory = InjectorUtils.provideLoginViewModelFactory(this);
                loginViewModel = ViewModelProviders.of(this, loginFactory).get(LoginViewModel.class);


                loginViewModel.getUser().observe(this, user -> {

                    if(null != user){


                        ShowApprovalsVHFactory factory = InjectorUtils.provideShowApprovalVMFactory(this, user.getOracle()+"");
                        showApprovalsVM = ViewModelProviders.of(this, factory).get(ShowApprovalsVH.class);
                        binding.setShowApprovalsVM(showApprovalsVM);

                        approvalListView = binding.approvalRequest;
                        attachmentGridView = binding.imagesAttached;


                        showApprovalsVM.startAttachEvent.observe(this, started->{
                            Intent intent =  new Intent();
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent,"Choose Images"), CHOOSE_IMAGE_CODE);

                        });


                        showApprovalsVM.getUris().observe(this, uris -> {
                            ImagedAttachedAdapter attachedAdapter = new ImagedAttachedAdapter(this,uris);
                            attachmentGridView.setAdapter(attachedAdapter);
                            attachmentGridView.setNumColumns(4);
                        });


                        showApprovalsVM.getStartAddRequstEvent().observe(this,added->{

                            if(null != showApprovalsVM.getUris().getValue()){
                                showApprovalsVM.startAddApprovalRequest(
                                        oldIntent.getStringExtra(APPROVAL_TYPE),
                                        this
                                );

                                showApprovalsVM.getUris().getValue().clear();



                            }else {
                                Toast.makeText(this, R.string.choose_images_text, Toast.LENGTH_SHORT).show();
                            }

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                            binding.addRequestContainer.setLayoutParams(layoutParams);



                        });



                        showApprovalsVM.getApprovals(ApprovalUtils.getMedicalTypeByResource(approvalType,this)).observe(this, approvals -> {
                            if(approvals.size() < 1){
                                binding.approvalEmptyText.setVisibility(View.VISIBLE);
                            }else{
                                binding.approvalEmptyText.setVisibility(View.INVISIBLE);
                            }

                            ApprovalsAdapter approvalsAdapter = new ApprovalsAdapter(this,approvals);
                            approvalListView.setAdapter(approvalsAdapter);
                        });


                    }

                });




            }
        }else{
            finish();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE_CODE && resultCode == RESULT_OK){
            ArrayList<Uri> uris = new ArrayList<>();


                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {


                        if(data.getClipData() != null){
                            ClipData clipData = data.getClipData();
                            int totalItemSelected = clipData.getItemCount();
                            if(totalItemSelected >4){
                                InjectorUtils.provideAppExecuter().mainThread().execute(()->{
                                    Toast.makeText(getApplicationContext(), R.string.attachment_max_4, Toast.LENGTH_SHORT).show();

                                });
                                return null;
                            }

                            for (int i=0; i < totalItemSelected; i++){
                                ClipData.Item item = clipData.getItemAt(i);
                                Uri uri = item.getUri();
                                uris.add(uri);
                            }



                        }else if(data.getData() != null){
                            uris.add(data.getData());
                        }


                        showApprovalsVM.postUris(uris);


                        return null;
                    }
                }.execute();


        }
    }

    public void chooseEmptyImage(String type){
        if(type.equals(getResources().getString(R.string.medical_analysis_title))){
            binding.approvaltypeImage.setImageResource(R.drawable.medical_analysis_icon);
        }else if(type.equals(getResources().getString(R.string.medical_rays_title))){
            binding.approvaltypeImage.setImageResource(R.drawable.medical_rays_icon);
        }else if(type.equals(getResources().getString(R.string.physical_therapy_title))){
            binding.approvaltypeImage.setImageResource(R.drawable.physical_therapy_icon);
        }else if(type.equals(getResources().getString(R.string.dental_approval_title))){
            binding.approvaltypeImage.setImageResource(R.drawable.dental);
        }else if(type.equals(getResources().getString(R.string.hospitalization_non_title))){
            binding.approvaltypeImage.setImageResource(R.drawable.hospitalization_icon);
        }else if(type.equals(getResources().getString(R.string.hospitalization_title))){
            binding.approvaltypeImage.setImageResource(R.drawable.hospitalizaton2_icon);
        }else if(type.equals(getResources().getString(R.string.chemotherapy_title))){
            binding.approvaltypeImage.setImageResource(R.drawable.chemotherapy_icon);
        }else if(type.equals(getResources().getString(R.string.others_approval_title))){
            binding.approvaltypeImage.setImageResource(R.drawable.other_medical_icon);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(null == FirebaseUtils.provideFirebaseAuth().getCurrentUser()){
            loginViewModel.login.setLogged(false);
            loginViewModel.login.setLoginPressedEvent(false);
        }else{
            loginViewModel.login.setLogged(true);
            loginViewModel.login.setLoginPressedEvent(false);
        }
    }


}
