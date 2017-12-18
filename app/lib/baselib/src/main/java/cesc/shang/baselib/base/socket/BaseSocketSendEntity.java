package cesc.shang.baselib.base.socket;

import cesc.shang.baselib.base.entity.BaseEntity;

/**
 * Created by shanghaolongteng on 2016/8/3.
 */
public abstract class BaseSocketSendEntity extends BaseEntity {
    /**
     * 将对象数据转换为String
     *
     * @return 数据字符串
     */
    public abstract String convertString();
}
