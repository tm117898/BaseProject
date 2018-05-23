package cesc.shang.baselib.support.manager;

import android.os.Build;
import android.util.ArrayMap;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.manager.base.BaseManager;
import cesc.shang.utilslib.utils.debug.DebugUtils;
import cesc.shang.utilslib.utils.debug.LogUtils;
import cesc.shang.utilslib.utils.debug.ProcessUtils;
import cesc.shang.utilslib.utils.debug.ReflectUtils;
import cesc.shang.utilslib.utils.debug.StrictModeUtils;
import cesc.shang.utilslib.utils.device.AlarmUtils;
import cesc.shang.utilslib.utils.device.AppUtils;
import cesc.shang.utilslib.utils.device.BatteryUtils;
import cesc.shang.utilslib.utils.device.DeviceUtils;
import cesc.shang.utilslib.utils.device.DualSimUtils;
import cesc.shang.utilslib.utils.device.LocationUtils;
import cesc.shang.utilslib.utils.device.MemoryUtils;
import cesc.shang.utilslib.utils.device.NetWorkUtils;
import cesc.shang.utilslib.utils.device.SensorUtils;
import cesc.shang.utilslib.utils.file.FileUtils;
import cesc.shang.utilslib.utils.file.LruCatchUtils;
import cesc.shang.utilslib.utils.file.SerializableUtils;
import cesc.shang.utilslib.utils.file.XmlUtils;
import cesc.shang.utilslib.utils.okhttp.OkHttpUtils;
import cesc.shang.utilslib.utils.util.DateTimeUtils;
import cesc.shang.utilslib.utils.util.MapSetUtils;
import cesc.shang.utilslib.utils.util.MessageUtils;
import cesc.shang.utilslib.utils.util.PinYinUtils;
import cesc.shang.utilslib.utils.util.ThreadUtils;
import cesc.shang.utilslib.utils.util.TimeUtils;
import cesc.shang.utilslib.utils.widget.ActivityUtils;
import cesc.shang.utilslib.utils.widget.AnimUtils;
import cesc.shang.utilslib.utils.widget.BitmapUtils;
import cesc.shang.utilslib.utils.widget.NotifyUtils;
import cesc.shang.utilslib.utils.widget.ViewUtils;

/**
 * Created by shanghaolongteng on 2017/5/6.
 * 管理
 */

public class UtilsManager extends BaseManager {
    protected SoftReference<DebugUtils> mDebugUtils;
    protected Map<String, LogUtils> mLogUtils;
    protected SoftReference<ProcessUtils> mProcessUtils;
    protected SoftReference<StrictModeUtils> mStrictModeUtils;
    protected ReflectUtils mReflect;

    protected AlarmUtils mAlarmUtils;
    protected AppUtils mAppUtils;
    protected BatteryUtils mBatteryUtils;
    protected DeviceUtils mDeviceUtils;
    protected DualSimUtils mDualSimUtils;
    protected LocationUtils mLocationUtils;
    protected MemoryUtils mMemoryUtils;
    protected NetWorkUtils mNetWorkUtils;
    protected SensorUtils mSensorUtils;

    protected FileUtils mFileUtils;
    protected LruCatchUtils mLruCatchUtils;
    protected SerializableUtils mSerializableUtils;
    protected XmlUtils mXmlUtils;

    protected DateTimeUtils mDateTimeUtils;
    protected MapSetUtils mMapSetUtils;
    protected MessageUtils mMessageUtils;
    protected PinYinUtils mPinYinUtils;
    protected ThreadUtils mThreadUtils;
    protected TimeUtils mTimeUtils;

    protected ActivityUtils mActivityUtils;
    protected AnimUtils mAnimUtils;
    protected BitmapUtils mBitmapUtils;
    protected NotifyUtils mNotifyUtils;
    protected ViewUtils mViewUtils;

    protected OkHttpUtils mOkHttpUtils;

    public UtilsManager(BaseApplication app) {
        super(app);
    }

    @Override
    public void create(BaseApplication app) {
        super.create(app);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mLogUtils = new ArrayMap<>();
        } else {
            mLogUtils = new HashMap<>();
        }
    }

    @Override
    public void onDestroy() {
        mLogUtils.clear();
    }


    public synchronized DebugUtils getDebugUtils() {
        if (mDebugUtils == null || mDebugUtils.get() == null) {
            mDebugUtils = new SoftReference<>(new DebugUtils());
        }
        return mDebugUtils.get();
    }

    public synchronized LogUtils getLogUtils(String tag) {
        LogUtils logUtils;
        if (mLogUtils.containsKey(tag)) {
            logUtils = mLogUtils.get(tag);
        } else {
            logUtils = LogUtils.newInstance(tag);
            mLogUtils.put(tag, logUtils);
        }
        return logUtils;
    }

    public synchronized ReflectUtils getReflectUtils() {
        if (mReflect == null) {
            mReflect = new ReflectUtils();
        }
        return mReflect;
    }

    public synchronized ProcessUtils getProcessUtils() {
        if (mProcessUtils == null || mProcessUtils.get() == null) {
            mProcessUtils = new SoftReference<>(new ProcessUtils());
        }
        return mProcessUtils.get();
    }

    public synchronized StrictModeUtils getStrictModeUtils() {
        if (mStrictModeUtils == null || mStrictModeUtils.get() == null) {
            mStrictModeUtils = new SoftReference<>(new StrictModeUtils());
        }
        return mStrictModeUtils.get();
    }

    public synchronized AlarmUtils getAlarmUtils() {
        if (mAlarmUtils == null) {
            mAlarmUtils = new AlarmUtils();
        }
        return mAlarmUtils;
    }

    public synchronized AppUtils getAppUtils() {
        if (mAppUtils == null) {
            mAppUtils = new AppUtils();
        }
        return mAppUtils;
    }

    public synchronized BatteryUtils getBatteryUtils() {
        if (mBatteryUtils == null) {
            mBatteryUtils = new BatteryUtils();
        }
        return mBatteryUtils;
    }

    public synchronized DeviceUtils getDeviceUtils() {
        if (mDeviceUtils == null) {
            mDeviceUtils = new DeviceUtils();
        }
        return mDeviceUtils;
    }

    public synchronized DualSimUtils getDualSimUtils() {
        if (mDualSimUtils == null) {
            mDualSimUtils = new DualSimUtils();
        }
        return mDualSimUtils;
    }

    public synchronized LocationUtils getLocationUtils() {
        if (mLocationUtils == null) {
            mLocationUtils = new LocationUtils();
        }
        return mLocationUtils;
    }

    public synchronized MemoryUtils getMemoryUtils() {
        if (mMemoryUtils == null) {
            mMemoryUtils = new MemoryUtils();
        }
        return mMemoryUtils;
    }

    public synchronized NetWorkUtils getNetWorkUtils() {
        if (mNetWorkUtils == null) {
            mNetWorkUtils = new NetWorkUtils();
        }
        return mNetWorkUtils;
    }

    public synchronized SensorUtils getSensorUtils() {
        if (mSensorUtils == null) {
            mSensorUtils = new SensorUtils();
        }
        return mSensorUtils;
    }

    public synchronized FileUtils getFileUtils() {
        if (mFileUtils == null) {
            mFileUtils = new FileUtils();
        }
        return mFileUtils;
    }

    public synchronized LruCatchUtils getLruCatchUtils() {
        if (mLruCatchUtils == null) {
            mLruCatchUtils = new LruCatchUtils();
        }
        return mLruCatchUtils;
    }

    public synchronized SerializableUtils getSerializableUtils() {
        if (mSerializableUtils == null) {
            mSerializableUtils = new SerializableUtils();
        }
        return mSerializableUtils;
    }

    public synchronized XmlUtils getXmlUtils() {
        if (mXmlUtils == null) {
            mXmlUtils = new XmlUtils();
        }
        return mXmlUtils;
    }

    public synchronized DateTimeUtils getDateTimeUtils() {
        if (mDateTimeUtils == null) {
            mDateTimeUtils = new DateTimeUtils();
        }
        return mDateTimeUtils;
    }

    public synchronized MapSetUtils getMapSetUtils() {
        if (mMapSetUtils == null) {
            mMapSetUtils = new MapSetUtils();
        }
        return mMapSetUtils;
    }

    public synchronized MessageUtils getMessageUtils() {
        if (mMessageUtils == null) {
            mMessageUtils = new MessageUtils();
        }
        return mMessageUtils;
    }

    public synchronized PinYinUtils getPinYinUtils() {
        if (mPinYinUtils == null) {
            mPinYinUtils = new PinYinUtils();
        }
        return mPinYinUtils;
    }

    public synchronized ThreadUtils getThreadUtils() {
        if (mThreadUtils == null) {
            mThreadUtils = new ThreadUtils();
        }
        return mThreadUtils;
    }

    public synchronized TimeUtils getTimeUtils() {
        if (mTimeUtils == null) {
            mTimeUtils = new TimeUtils();
        }
        return mTimeUtils;
    }

    public synchronized ActivityUtils getActivityUtils() {
        if (mActivityUtils == null) {
            mActivityUtils = new ActivityUtils();
        }
        return mActivityUtils;
    }

    public synchronized AnimUtils getAnimUtils() {
        if (mAnimUtils == null) {
            mAnimUtils = new AnimUtils();
        }
        return mAnimUtils;
    }

    public synchronized BitmapUtils getBitmapUtils() {
        if (mBitmapUtils == null) {
            mBitmapUtils = new BitmapUtils();
        }
        return mBitmapUtils;
    }

    public synchronized NotifyUtils getNotifyUtils() {
        if (mNotifyUtils == null) {
            mNotifyUtils = new NotifyUtils();
        }
        return mNotifyUtils;
    }

    public synchronized ViewUtils getViewUtils() {
        if (mViewUtils == null) {
            mViewUtils = new ViewUtils();
        }
        return mViewUtils;
    }

    public synchronized OkHttpUtils getOkHttpUtils() {
        if (mOkHttpUtils == null) {
            mOkHttpUtils = new OkHttpUtils();
        }
        return mOkHttpUtils;
    }
}
