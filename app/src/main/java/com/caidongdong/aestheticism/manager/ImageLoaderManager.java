package com.caidongdong.aestheticism.manager;

import android.content.Context;

import com.caidongdong.aestheticism.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * 初始化ImageLoader
 * Aestheticism
 * 作者：caidongdong on 2015/12/23 15:20
 * 邮箱：mircaidong@163.com
 */
public class ImageLoaderManager {

    public ImageLoaderManager() {
    }

    /**
     * ImageLoader 图片组件初始化
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageLoader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileCount(100)
                .discCache(new UnlimitedDiscCache(cacheDir))
                .defaultDisplayImageOptions(initDisplayImageOptions())
                .imageDownloader(new BaseImageDownloader(context,5 * 1000,20 * 1000 ))
                .writeDebugLogs()
                        // Remove
                        // for
                        // release
                        // app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    /**
     * 自定义DisplayImageOptions
     * @return
     */
    public static DisplayImageOptions initDisplayImageOptions() {
        DisplayImageOptions options = new DisplayImageOptions
                .Builder()
                .showImageOnLoading(R.mipmap.beauty)
                .showImageForEmptyUri(R.mipmap.picture_not_found)
                .showImageOnFail(R.mipmap.picture_not_found)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
        return options;
    }

}
