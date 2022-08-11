package com.example.ruleteacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class TaskHistory extends AppCompatActivity {

    ImageView imgDOC;
    TextView tvStudentName, tvTaskName, tvDOTG, tvPOT, tvNoteToStudent,tvDOTU;
    FirebaseFirestore fStore;
    String task, studentID, dotg, pot, dotu, docid, pic, taskDetail, type, result ;
    StorageReference storageReference, taskRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_history);
        task = getIntent().getStringExtra("TASK");
        studentID = getIntent().getStringExtra("STUDENT");
        dotg = getIntent().getStringExtra("DOTG");
        pot = getIntent().getStringExtra("POT");
        dotu = getIntent().getStringExtra("DOTU");
        docid = getIntent().getStringExtra("DOCID");
        pic = getIntent().getStringExtra("PICUID"); //namacikgu
        taskDetail = getIntent().getStringExtra("TASKDETAIL");
        type = getIntent().getStringExtra("TYPE");
        result = getIntent().getStringExtra("RESULT");

        imgDOC=findViewById(R.id.img_DOC);
        tvStudentName=findViewById(R.id.tv_student_name);
        tvTaskName=findViewById(R.id.tv_taskName);
        tvDOTG=findViewById(R.id.tv_dotg);
        tvPOT=findViewById(R.id.tv_pot);
        tvNoteToStudent=findViewById(R.id.tv_pot);
        tvDOTU=findViewById(R.id.tv_dotu);
        taskRef=storageReference.child("task/"+docid+".jpg");


        fStore=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();

        taskRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(imgDOC);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });

        fStore.collection("users").document(studentID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                tvStudentName.setText(value.getString("name"));
            }
        });

        tvTaskName.setText(task);
        tvDOTG.setText(dotg);
        tvPOT.setText(pot);
        tvNoteToStudent.setText(taskDetail);
        tvDOTU.setText(dotu);

    }
}