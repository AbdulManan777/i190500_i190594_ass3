package com.ass3.i190500_i190594;



import android.content.Intent;
import android.content.pm.PackageManager;
        import android.media.MediaPlayer;
        import android.media.MediaRecorder;
        import android.os.Bundle;
        import android.os.Environment;
        import android.util.Log;
        import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;

        import java.io.File;
        import java.io.IOException;

        import static android.Manifest.permission.RECORD_AUDIO;
        import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class voice_recorder extends AppCompatActivity {

    private TextView bt_record, bt_stoprecord, bt_play, bt_stop, status_TV;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static String mFileName = null;
    ImageView back;

    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recorder);

        // initialize all variables with their layout items.
        status_TV = findViewById(R.id.working_status);
        bt_record = findViewById(R.id.btn_record);
        bt_stoprecord = findViewById(R.id.btn_stop);
        bt_play = findViewById(R.id.btn_play);
        bt_stop = findViewById(R.id.btn_stopplay);
        back=findViewById(R.id.back);

        // back button implementaion
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(voice_recorder.this, ChatBox_Activity.class);
                startActivity(i);
                finish();
            }
        });


        bt_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start recording method will
                // start the recording of audio.
                start_Recording();
            }
        });
        bt_stoprecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pause Recording method will
                // pause the recording of audio.
                pause_Recording();

            }
        });
        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // play audio method will play
                // the audio which we have recorded
                play_Audio();
            }
        });
        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pause play method will
                // pause the play of audio
                pause_Playing();
            }
        });
    }

    private void start_Recording() {
        // check permission method is used to check
        // that the user has granted permission
        // to record nd store the audio.
        if (CheckPermissions()) {

//        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
//        mFileName += "/AudioRecording.3gp";

            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            File audioFile = new File(getExternalCacheDir(),
                    "audio_test4.mp3");
            mFileName = audioFile.getAbsolutePath();
            mRecorder.setOutputFile(mFileName);
            try {
                mRecorder.prepare();
            } catch (IOException e) {
                Log.e("TAG", "prepare() failed");
            }
            mRecorder.start();
            status_TV.setText("Recording Started");
        } else {
            // if audio recording permissions are
            // not granted by user below method will
            // ask for runtime permission for mic and storage.
            RequestPermissions();
        }
    }

    private void RequestPermissions() {
        // this method is used to request the
        // permission for audio recording and storage.
        ActivityCompat.requestPermissions(voice_recorder.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // this method is called when user will
        // grant the permission for audio recording.
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean CheckPermissions() {
        // this method is used to check permission
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }




    public void play_Audio() {

        mPlayer = new MediaPlayer();
        try {

            mPlayer.setDataSource(mFileName);

            mPlayer.prepare();

            mPlayer.start();
            status_TV.setText("Recording Started Playing");
        } catch (IOException e) {
            Log.e("TAG", "prepare() failed");
        }
    }

    public void pause_Recording() {
        // the audio recording will stop.
        if(mRecorder != null) {
            mRecorder.stop();

            mRecorder.release();
            mRecorder = null;
            status_TV.setText("Recording Stopped");
        } else
            Toast.makeText(this, "No audio recording", Toast.LENGTH_SHORT).show();
    }

    public void pause_Playing() {

        if(mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
            status_TV.setText("Recording Play Stopped");
        }
        else
            Toast.makeText(this, "audio not running", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        if (mRecorder != null) {
//            mRecorder.stop();
//            mRecorder.release();
//            mRecorder = null;
//        }
//    }
}