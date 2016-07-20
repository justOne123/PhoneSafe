package com.example.phonesafe1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class RocaketActivity extends Activity {
	private ImageView mRocketAnimation;
	private Matrix startMatrix = new Matrix(); 
	private Matrix matrix = new Matrix();
	private float dx;
	private float dy;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				matrix.set(startMatrix);
				matrix.postTranslate(dx, dy);
				mRocketAnimation.setImageMatrix(matrix);

			}else {
				matrix.postTranslate(0, -15);
				mRocketAnimation.setImageMatrix(matrix);
			}
		}
	};
	private Vibrator mVibrator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rocket);
		mVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		//��С����������м���
		initRocketAnimation();
	}
	private void initRocketAnimation() {
		mRocketAnimation = (ImageView)findViewById(R.id.rocket_iv);
		//���ô�������
		mRocketAnimation.setOnTouchListener(new RocketTouchListener());
	}
	
	private class RocketTouchListener implements View.OnTouchListener{
		private PointF startF;
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN://��ָ����
				mVibrator.vibrate(200);
				//1.������ʼ��
				startF = new PointF(event.getX(), event.getY());
				//2.������ʼ����
				startMatrix.set(mRocketAnimation.getImageMatrix());
				break;
			case MotionEvent.ACTION_MOVE://�ƶ�
				mVibrator.vibrate(200);
				dx = event.getX() - startF.x;
				dy = event.getY() - startF.y;
				matrix.set(startMatrix);
				matrix.postTranslate(dx, dy);
				mRocketAnimation.setImageMatrix(matrix);
				break;
			case MotionEvent.ACTION_UP://��ָ�뿪
				mVibrator.cancel();
				sendRocket();
				break;

			default:
				break;
			}
			return true;
		}
		//��ָ�ɿ�����С�������
		private void sendRocket() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 110; i++) {
						SystemClock.sleep(10);
						handler.sendEmptyMessage(0);
					}
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
				}
			}).start();
		}
		
	}

}
