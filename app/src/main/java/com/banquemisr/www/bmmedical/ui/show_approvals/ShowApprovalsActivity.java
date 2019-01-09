package com.banquemisr.www.bmmedical.ui.show_approvals;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.banquemisr.www.bmmedical.Adapters.ApprovalsAdapter;
import com.banquemisr.www.bmmedical.Adapters.ImagedAttachedAdapter;
import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.ActivityShowApprovalBinding;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModel;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModelFactory;
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


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_approval);
        binding.setLifecycleOwner(this);

        Intent oldIntent = getIntent();
        if(null != oldIntent){
            if(oldIntent.hasExtra(APPROVAL_TYPE)){


                Toolbar toolbar = binding.toolbar;
                toolbar.setTitleTextColor(getResources().getColor(R.color.da));
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle(oldIntent.getStringExtra(APPROVAL_TYPE));


                ImageView noApprovalsImageview = binding.approvalImage;
                TextView noApprovalsTV = binding.noApprovalTv;



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
                            attachmentGridView.setNumColumns(3);
                        });


                        showApprovalsVM.getStartAddRequstEvent().observe(this,added->{

                            if(null != showApprovalsVM.getUris().getValue()){
                                showApprovalsVM.startAddApprovalRequest(
                                        oldIntent.getStringExtra(APPROVAL_TYPE),
                                        this
                                );

                                showApprovalsVM.getUris().getValue().clear();
                            }else {
                                Toast.makeText(this, "You must choose your attachment image first", Toast.LENGTH_SHORT).show();
                            }

                        });



                        showApprovalsVM.getApprovals().observe(this, approvals -> {
                            if(approvals.size() < 1){
                                noApprovalsImageview.setVisibility(View.VISIBLE);
                                noApprovalsTV.setVisibility(View.VISIBLE);
                            }else{
                                noApprovalsImageview.setVisibility(View.INVISIBLE);
                                noApprovalsTV.setVisibility(View.INVISIBLE);
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

            InjectorUtils.provideAppExecuter().diskIO().execute(()->{

                if(data.getClipData() != null){
                    ClipData clipData = data.getClipData();
                    int totalItemSelected = clipData.getItemCount();
                    if(totalItemSelected >6){
                        Toast.makeText(this, "The Maximum Number of Image is 6", Toast.LENGTH_SHORT).show();
                        return;
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
            });


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
