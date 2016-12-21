package custom.bwie.com.demo;

import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements OnClickListener,Callback {

    private Button start;
    private Button stop;
    //录视频的类
    private MediaRecorder mediaRecorder;
    //显示视频的控件
    private SurfaceView surfaceView;
    //用来显示视频的一个接口，用来看mediaRecorder录制的视频，必须有
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start= (Button) findViewById(R.id.start);
        stop= (Button) findViewById(R.id.stop);
        surfaceView= (SurfaceView) findViewById(R.id.surface_view);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        SurfaceHolder holder=surfaceView.getHolder();
        //holder加入回调接口
        holder.addCallback(this);
        //setType必须设置否则会报错
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //这个holder是开始在oncreate里边取得的holder，将它赋给surfaceHolder
        surfaceHolder=holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //这个holder是开始在oncreate里边取得的holder，将它赋给surfaceHolder
        surfaceHolder=holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //置空
        mediaRecorder=null;
        surfaceHolder=null;
        surfaceView=null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                //开始
                startEvent();
                break;
            case R.id.stop:
                //结束
                stopEvent();
                break;
            default:
                break;
        }
    }

    private void startEvent() {
        //创建mediarecorder对象
        mediaRecorder=new MediaRecorder();
        //设置录制视频为相机
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //将设置完成后的视频封装格式THREE_GPP为3gp,MPEG_4为mp4
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //设置录制视频编码h263 h264
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        //设置录制视频的分辨率，必须放置在视频编码和格式之后，否则报错
        mediaRecorder.setVideoSize(176,144);
        //设置录制视频的帧率，必须放在设置编号和搁置之后，否则会报错
        mediaRecorder.setVideoFrameRate(20);
        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        //设置路径
        mediaRecorder.setOutputFile("sdcard/love.3gp");
        try {
            //准备录制
            mediaRecorder.prepare();
            //开始录制
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //结束方法
    private void stopEvent() {
        if(mediaRecorder!=null){
            //结束
            mediaRecorder.stop();
            //释放资源
            mediaRecorder.release();;
            mediaRecorder=null;
        }
    }
}
