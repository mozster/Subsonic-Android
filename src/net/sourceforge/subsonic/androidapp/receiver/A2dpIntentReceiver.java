package net.sourceforge.subsonic.androidapp.receiver;

import net.sourceforge.subsonic.androidapp.domain.MusicDirectory.Entry;
import net.sourceforge.subsonic.androidapp.service.DownloadService;
import net.sourceforge.subsonic.androidapp.service.DownloadServiceImpl;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class A2dpIntentReceiver extends BroadcastReceiver {
	
    private static final String PLAYSTATUS_REQUEST = "com.android.music.playstatusrequest";
    private static final String PLAYSTATUS_RESPONSE = "com.android.music.playstatusresponse";

	@Override
	public void onReceive(Context context, Intent intent) {
		
		DownloadService downloadService = DownloadServiceImpl.getInstance();
		
		if (downloadService != null){
			
			Intent avrcpIntent = new Intent(PLAYSTATUS_RESPONSE);
			
			Log.d("moz", "playstatusresponse broadcast");
			
            avrcpIntent.putExtra("duration", (long) downloadService.getPlayerDuration());
			avrcpIntent.putExtra("position", (long) downloadService.getPlayerPosition());
			avrcpIntent.putExtra("ListSize", (long) downloadService.getDownloads().size());
						
			switch (downloadService.getPlayerState()){
				case STARTED:
	            	avrcpIntent.putExtra("playing", true);
	                break;
	            case STOPPED:
	            	avrcpIntent.putExtra("playing", false);
	                break;
	            case PAUSED:
	            	avrcpIntent.putExtra("playing", false);
	                break;
	            case COMPLETED:
	            	avrcpIntent.putExtra("playing", false);
	                break;
	            default:
	                return;
			}			
		
			context.sendBroadcast(avrcpIntent);
			
		}
	}
}
