package yalantis.com.sidemenu.foodies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import yalantis.com.sidemenu.foodies.model.AppConstants;


/**
 * Created by palak on 22-02-2015.
 */
public class ConnectionChecker {

    public static int getConnectionInfo(Context context){

        int deviceNetworkType = AppConstants.TYPE_NOT_CONNECTED;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if(activeNetwork != null){

            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){

                deviceNetworkType = AppConstants.TYPE_WIFI;
            }

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){

                /** Means, Device is not Tablet. It is having mobile connections. So check for connection type.**/
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                int networkType = telephonyManager.getNetworkType();

                switch (networkType) {

                    case TelephonyManager.NETWORK_TYPE_GPRS:

                    case TelephonyManager.NETWORK_TYPE_EDGE:

                    case TelephonyManager.NETWORK_TYPE_CDMA:

                    case TelephonyManager.NETWORK_TYPE_1xRTT:

                    case TelephonyManager.NETWORK_TYPE_IDEN:

                        deviceNetworkType = AppConstants.TYPE_2G;
                        break;

                    case TelephonyManager.NETWORK_TYPE_UMTS:

                    case TelephonyManager.NETWORK_TYPE_EVDO_0:

                    case TelephonyManager.NETWORK_TYPE_EVDO_A:

                    case TelephonyManager.NETWORK_TYPE_HSDPA:

                    case TelephonyManager.NETWORK_TYPE_HSUPA:

                    case TelephonyManager.NETWORK_TYPE_HSPA:

                    case TelephonyManager.NETWORK_TYPE_EVDO_B:

                    case TelephonyManager.NETWORK_TYPE_EHRPD:

                    case TelephonyManager.NETWORK_TYPE_HSPAP:

                        deviceNetworkType = AppConstants.TYPE_3G;
                        break;

                    case TelephonyManager.NETWORK_TYPE_LTE:

                        deviceNetworkType = AppConstants.TYPE_4G;
                        break;

                    default:

                        deviceNetworkType = AppConstants.TYPE_NOT_CONNECTED;
                        break;
                }
            }
        }

        return deviceNetworkType;
    }
}
