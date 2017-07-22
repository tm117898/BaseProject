package cesc.shang.baselib.support.utils;

import android.content.Context;
import android.os.Build;
import android.util.ArrayMap;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.BaseManager;
import cesc.shang.utilslib.utils.debug.DebugUtils;
import cesc.shang.utilslib.utils.debug.LogUtils;
import cesc.shang.utilslib.utils.debug.ProcessUtils;
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
import cesc.shang.utilslib.utils.widget.ViewTouchUtils;
import cesc.shang.utilslib.utils.widget.ViewUtils;

/**
 * Created by shanghaolongteng on 2017/5/6.
 */

public class UtilsManager extends BaseManager {
    protected SoftReference<DebugUtils> mDebugUtils;
    protected Map<String, LogUtils> mLogUtils;
    protected SoftReference<ProcessUtils> mProcessUtils;
    protected SoftReference<StrictModeUtils> mStrictModeUtils;

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
    protected ViewTouchUtils mViewTouchUtils;
    protected ViewUtils mViewUtils;

    protected OkHttpUtils mOkhttpUtils;

    public UtilsManager(BaseApplication app) {
        super(app);
    }

    @Override
    public void onCreate(BaseApplication app) {
        super.onCreate(app);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mLogUtils = new ArrayMap<>();
        } else {
            mLogUtils = new HashMap<>();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mLogUtils.clear();
    }

    public DebugUtils getDebugUtils() {
        if (mDebugUtils == null || mDebugUtils.get() == null) {
            mDebugUtils = new SoftReference<>(new DebugUtils());
        }
        return mDebugUtils.get();
    }

    public LogUtils getLogUtils(String tag) {
        LogUtils logUtils;
        if (mLogUtils.containsKey(tag)) {
            logUtils = mLogUtils.get(tag);
        } else {
            logUtils = LogUtils.newInstance(tag);
            mLogUtils.put(tag, logUtils);
        }
        return logUtils;
    }

    public ProcessUtils getProcessUtils() {
        if (mProcessUtils == null || mProcessUtils.get() == null) {
            mProcessUtils = new SoftReference<>(new ProcessUtils());
        }
        return mProcessUtils.get();
    }

    public StrictModeUtils getStrictModeUtils() {
        if (mStrictModeUtils == null || mStrictModeUtils.get() == null) {
            mStrictModeUtils = new SoftReference<>(new StrictModeUtils());
        }
        return mStrictModeUtils.get();
    }

    public AlarmUtils getAlarmUtils() {
        if (mAlarmUtils == null) {
            mAlarmUtils = new AlarmUtils();
        }
        return mAlarmUtils;
    }

    public AppUtils getAppUtils() {
        if (mAppUtils == null) {
            mAppUtils = new AppUtils();
        }
        return mAppUtils;
    }

    public BatteryUtils getBatteryUtils() {
        if (mBatteryUtils == null) {
            mBatteryUtils = new BatteryUtils();
        }
        return mBatteryUtils;
    }

    public DeviceUtils getDeviceUtils() {
        if (mDeviceUtils == null) {
            mDeviceUtils = new DeviceUtils();
        }
        return mDeviceUtils;
    }

    public DualSimUtils getDualSimUtils() {
        if (mDualSimUtils == null) {
            mDualSimUtils = new DualSimUtils();
        }
        return mDualSimUtils;
    }

    public LocationUtils getLocationUtils() {
        if (mLocationUtils == null) {
            mLocationUtils = new LocationUtils();
        }
        return mLocationUtils;
    }

    public MemoryUtils getMemoryUtils() {
        if (mMemoryUtils == null) {
            mMemoryUtils = new MemoryUtils();
        }
        return mMemoryUtils;
    }

    public NetWorkUtils getNetWorkUtils() {
        if (mNetWorkUtils == null) {
            mNetWorkUtils = new NetWorkUtils();
        }
        return mNetWorkUtils;
    }

    public SensorUtils getSensorUtils() {
        if (mSensorUtils == null) {
            mSensorUtils = new SensorUtils();
        }
        return mSensorUtils;
    }

    public FileUtils getFileUtils() {
        if (mFileUtils == null) {
            mFileUtils = new FileUtils();
        }
        return mFileUtils;
    }

    public LruCatchUtils getLruCatchUtils() {
        if (mLruCatchUtils == null) {
            mLruCatchUtils = new LruCatchUtils();
        }
        return mLruCatchUtils;
    }

    public SerializableUtils getSerializableUtils() {
        if (mSerializableUtils == null) {
            mSerializableUtils = new SerializableUtils();
        }
        return mSerializableUtils;
    }

    public XmlUtils getXmlUtils() {
        if (mXmlUtils == null) {
            mXmlUtils = new XmlUtils();
        }
        return mXmlUtils;
    }

    public DateTimeUtils getDateTimeUtils() {
        if (mDateTimeUtils == null) {
            mDateTimeUtils = new DateTimeUtils();
        }
        return mDateTimeUtils;
    }

    public MapSetUtils getMapSetUtils() {
        if (mMapSetUtils == null) {
            mMapSetUtils = new MapSetUtils();
        }
        return mMapSetUtils;
    }

    public MessageUtils getMessageUtils() {
        if (mMessageUtils == null) {
            mMessageUtils = new MessageUtils();
        }
        return mMessageUtils;
    }

    public PinYinUtils getPinYinUtils() {
        if (mPinYinUtils == null) {
            mPinYinUtils = new PinYinUtils();
        }
        return mPinYinUtils;
    }

    public ThreadUtils getThreadUtils() {
        if (mThreadUtils == null) {
            mThreadUtils = new ThreadUtils();
        }
        return mThreadUtils;
    }

    public TimeUtils getTimeUtils() {
        if (mTimeUtils == null) {
            mTimeUtils = new TimeUtils();
        }
        return mTimeUtils;
    }

    public ActivityUtils getActivityUtils() {
        if (mActivityUtils == null) {
            mActivityUtils = new ActivityUtils();
        }
        return mActivityUtils;
    }

    public AnimUtils getAnimUtils() {
        if (mAnimUtils == null) {
            mAnimUtils = new AnimUtils();
        }
        return mAnimUtils;
    }

    public BitmapUtils getBitmapUtils() {
        if (mBitmapUtils == null) {
            mBitmapUtils = new BitmapUtils();
        }
        return mBitmapUtils;
    }

    public NotifyUtils getNotifyUtils() {
        if (mNotifyUtils == null) {
            mNotifyUtils = new NotifyUtils();
        }
        return mNotifyUtils;
    }

    public ViewTouchUtils getViewTouchUtils(Context context) {
        if (mViewTouchUtils == null) {
            mViewTouchUtils = new ViewTouchUtils(context);
        }
        return mViewTouchUtils;
    }

    public ViewUtils getViewUtils() {
        if (mViewUtils == null) {
            mViewUtils = new ViewUtils();
        }
        return mViewUtils;
    }

    public OkHttpUtils getOkHttpUtils() {
        if (mOkhttpUtils == null) {
            mOkhttpUtils = new OkHttpUtils();
        }
        return mOkhttpUtils;
    }
}
