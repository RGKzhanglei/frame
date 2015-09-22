package com.example.business.mybusiness.page.controller;

import android.support.v4.util.ArrayMap;

import com.example.business.mybusiness.page.model.CacheBean;
import com.example.business.mybusiness.utils.StringUtil;

/**
 * Created by zhang.la on 2015/9/16.
 */
public class CacheBeanController {
    private static ArrayMap<String, CacheBean> pageCacheBeanMap = new ArrayMap<>();

    /**
     * 保存cacheBean
     * @param cacheBean
     */
    public static void saveCacheBean(CacheBean cacheBean) {
        if (null != cacheBean) {
            String key = cacheBean.hashCode() + "+" + cacheBean.getClass().getName();
            pageCacheBeanMap.put(key, cacheBean);
        }
    }

    /**
     * 删除缓存的cacheBean
     * @param cacheBean
     */
    public static void removeCacheBean(CacheBean cacheBean) {
        if (null != cacheBean) {
            String key = cacheBean.hashCode() + "+" + cacheBean.getClass().getName();
            pageCacheBeanMap.remove(key);
        }
    }

    /**
     * 获取缓存的cacheBean
     * @param key
     */
    public static CacheBean getCacheBean(String key) {
        if (!StringUtil.isEmpty(key)) {
            return pageCacheBeanMap.get(key);
        }
        return null;
    }

}
