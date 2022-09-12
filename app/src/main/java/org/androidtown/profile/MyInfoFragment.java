package org.androidtown.alcohol;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.alcohol.SearchClasses.DBIDSearch;
import org.androidtown.alcohol.LoginAndJoin.LoginActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment implements View.OnClickListener, OnBackPressedListener {
    DrinkInfo drinkInfo;
    //TextView rate, name, type;
    TextView MyRate;
    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    DBIDSearch dbidSearch;
    ListView listView;
    //view objects
    private TextView textViewUserEmail;   // 아이디(닉네임)
    private Button buttonLogout;
    private TextView textivewDelete;
    private Context context;
    MyInfoAdapter myInfoAdapter;
    ArrayList<String> drinkInfos;//댓글
    ArrayList<Integer> ratings;
    FirebaseUser user;
    ImageView photoVIew;
    int clicked = 0; // 0이면 listview보이고 1이면 안보이게
    Uri mImageCaptureUri;
    private String absolutePath;
    private static final int PICK_FROM_CAMERA = 0;
    static final int IMAGE_CAPTURE = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE = 1000;//권한 상수
    private File file; //갤러리에서 불러올 파일
    private final int GET_GALLERY_IMAGE = 200;
    String[] descriptions = {"good", "great", "wonderful", "It's a great application", "저가 써 본 술 관련 어플중에서 가장 좋아요"};
    int[] UserRatings = {1, 2, 3, 4, 5};

    public MyInfoFragment() {
        // Required empty public constructor
    }

    public static MyInfoFragment newInstance() {
        Bundle args = new Bundle();
        MyInfoFragment myInfoFragment = new MyInfoFragment();

        return myInfoFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);
        context = container.getContext();
        //rate = view.findViewById(R.id.rate_info);
        //name = view.findViewById(R.id.drink_name);
        // type = view.findViewById(R.id.drink_type);
        MyRate = view.findViewById(R.id.myrate);
        photoVIew = view.findViewById(R.id.profile_image);
        //initializing views
        textViewUserEmail = view.findViewById(R.id.id_and_nickname);
        buttonLogout = view.findViewById(R.id.buttonLogout);
        textivewDelete = view.findViewById(R.id.textviewDelete);
        listView = view.findViewById(R.id.list_view);
        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
        if (firebaseAuth.getCurrentUser() == null) {
            ////finish();
            startActivity(new Intent(context, LoginActivity.class));
        }

        //유저가 있다면, null이 아니면 계속 진행
        user = firebaseAuth.getCurrentUser();
        //////아이디(닉네임) 보여주기//////
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dbidSearch = new DBIDSearch(user.getEmail());
        //textViewUserEmail의 내용을 변경해 준다.
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textViewUserEmail.setText(user.getEmail() + "(" + dataSnapshot.child("Info").child(dbidSearch.getID()).child("NickName").getValue() + ")");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //////아이디(닉네임) 보여주기 끝//////
        ///권한요청///
        //requestReadExternalStoragePermission();
        ////권한요청////
        /////// ===사진첨버튼== ///////
        photoVIew.setOnClickListener(this);
        /////내가 쓴 편점 버튼 ////
        MyRate.setOnClickListener(this);
        //logout button event
        buttonLogout.setOnClickListener(this);
        textivewDelete.setOnClickListener(this);
        drinkInfos = new ArrayList<>();
        ratings = new ArrayList<>();
        for (int i = 0; i < descriptions.length; i++) {
            drinkInfos.add(descriptions[i]);
        }
        for (int i = 0; i < UserRatings.length; i++) {
            ratings.add(UserRatings[i]);
        }
        listView.setAdapter(new MyInfoAdapter(context, drinkInfos, ratings));
        return view;
    }

    //뒤로가기
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("종료창")
                .setMessage("종료하시겠습니까?")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onClick(View view) {
        if (view == MyRate) {
            if (clicked == 0) {
                listView.setVisibility(View.VISIBLE);
                clicked = 1;
            } else if (clicked == 1) {
                listView.setVisibility(View.GONE);
                clicked = 0;
            }

        }//내가쓴 평점-> 술 이름 클릭시 이벤트
        if (view == photoVIew) {

            DialogInterface.OnClickListener cameraListner = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    doTakePhoto();
                }
            };//사진촬영
            DialogInterface.OnClickListener albumListner = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    doTakeAlbum();
                }
            };/////앨범선택
            DialogInterface.OnClickListener cancelListner = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            };//취소
            new AlertDialog.Builder(context)
                    .setTitle("업로드할 이미지 선택")
                    .setNeutralButton("앨범선택", albumListner)
                    .setNegativeButton("취소", cancelListner)
                    .show();
        }///이미지뷰 클릭
        if (view == buttonLogout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("로그아웃 하시겠습니까?").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    firebaseAuth.signOut();
                    getActivity().finish();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(context, "취소", Toast.LENGTH_LONG).show();
                }
            });
            builder.show();
        }//로그아웃 버튼
        //회원탈퇴를 클릭하면 회원정보를 삭제한다. 삭제전에 컨펌창을 하나 띄워야 겠다.
        if (view == textivewDelete) {
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(context);
            alert_confirm.setMessage("정말 계정을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "계정이 삭제 되었습니다.", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(context, MainActivity.class));
                                }
                            });
                            dbidSearch = new DBIDSearch(user.getEmail());
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("Info").child(dbidSearch.getID()).child("ID").removeValue();
                                    mDatabase.child("Info").child(dbidSearch.getID()).child("password").removeValue();
                                    mDatabase.child("Info").child(dbidSearch.getID()).child("name").removeValue();
                                    mDatabase.child("Info").child(dbidSearch.getID()).child("nickName").removeValue();
                                    //mDatabase.child("Info").child(Email.getText().toString().trim()).child("Email").setValue(Email.getText().toString().trim());
                                    mDatabase.child("Info").child(dbidSearch.getID()).child("Phone").removeValue();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
            );
            alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(context, "취소", Toast.LENGTH_LONG).show();
                }
            });
            alert_confirm.show();
        }//회원탈퇴버튼

    }//onclick  메소드

    String currentPhotoPath;

    // 사진촬영 액션 시작//
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void doTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "org.androidtown.alcolhol.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, IMAGE_CAPTURE);
            }
        }//사진촬영 액션 끝//

    }


    public void doTakeAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);
    }


    //onActivityResult//
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode != Activity.RESULT_OK) {
            Toast.makeText(context, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if (file != null) {
                if (file.exists()) {
                    if (file.delete()) {
                        Log.e("ALBUM", file.getAbsolutePath() + " 삭제 성공");
                        file = null;
                    }
                }
            }
            return;
        }//예외처리*/
        if (requestCode == GET_GALLERY_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            photoVIew.setImageURI(selectedImageUri);

        } else if (requestCode == IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            Uri uri = data.getData();
            if (bitmap != null) {
                photoVIew.setImageBitmap(bitmap);
            }
            photoVIew.setImageURI(uri);
        }

    }//onActivityResult//
}
