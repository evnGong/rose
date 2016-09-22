package com.island.gyy.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * IO流工具类
 * @author BD 获取存储信息可能需要这些权限 
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 * <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>  // SDCard 中创建与删除文件权限
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 */
public abstract class FileUtil {
	
	/**
	 * 检查是否有外部存储器。
	 * @return
	 */
	public static boolean hasExternalStorage() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
	
	/**
	 * 判断是否为外置SD卡
	 * @return
	 */
	@SuppressLint("NewApi")
	public static boolean isExternalStorageRemovable() {
		 return Environment.isExternalStorageRemovable();
    }
	
	
	/**
	 * 获取指定路径下可用空间字节数
	 * @param path
	 * @return 
	 */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static long getUsableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }
	
    
    /**
     * 获取外部SD卡应用数据缓存目录
     * @param mContext
     * @return
     */
    public static File getExternalCacheDir(Context context) {
		return context.getExternalCacheDir();
	}
	
    /**
     * 获取外部SD卡应用数据缓存目录(兼容低版本)
     * @param 
     * @return 
     */
    public static File getExternalStorageCacheDir(Context context) {
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }
    
    
    /**
     * 获取系统缓存目录 : SD卡 或 应用 cache 目录下
     * @param mContext 
     * @return
     */
    public static File getSystemCacheDir(Context context) {
        return FileUtil.hasExternalStorage() || !FileUtil.isExternalStorageRemovable() ?
        		FileUtil.getExternalCacheDir(context) : context.getCacheDir();
    }
    
    
    
	/**
	 * 递归创建目录
	 * @param file
	 * @return 是否创建成功
	 */
	public static boolean createDir(File file) {
		return file.exists() ? true : file.mkdirs();
	}
	
	
	

	/**
	 * 读取文件到内存
	 * @param file  : 文件路径
	 * @return
	 */
	public static byte[] readFile(File file) {
		
		if(file == null || !file.exists() || file.isDirectory()) {
			return null;
		}
		
		try(FileInputStream in = new FileInputStream(file);
			FileChannel readChannel = in.getChannel();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();){
			
			ByteBuffer buffer = ByteBuffer.allocate(1024 << 3);
			while (readChannel.read(buffer) > 0) {
				buffer.flip();
				for (int i = 0, len = buffer.remaining(); i < len; i++) {
					baos.write(buffer.get()); // 将数据写到内存中
				}
				buffer.clear();
			}
			return baos.toByteArray();        // 将内存中的数据转换成字节数组
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 将输入流转换成字符串(UTF-8)
	 * @param is       : 输入流
	 * @param charset  : 数组原有编码，不指定则默认为UTF-8解码
	 * @return UTF-8   编码的字符串
	 */
	public static byte[] inputToByteArray(InputStream is) {
		if (is == null) return null;
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ReadableByteChannel readChannel = Channels.newChannel(is);) {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			
			while(readChannel.read(buffer) > 0) {
				buffer.flip();
				for (int i = 0, len = buffer.remaining(); i < len; i++) {
					baos.write(buffer.get());
				}
				buffer.clear();
			}
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 将输入流转换成字符串(UTF-8)
	 * @param is       : 输入流
	 * @param charset  : 数组原有编码，不指定则默认为UTF-8解码
	 * @return UTF-8   编码的字符串
	 */
	public static String inputToString(InputStream is, String charset) {
		if (is == null) return null;
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ReadableByteChannel readChannel = Channels.newChannel(is);) {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			
			while(readChannel.read(buffer) > 0) {
				buffer.flip();
				for (int i = 0, len = buffer.remaining(); i < len; i++) {
					baos.write(buffer.get());
				}
				buffer.clear();
			}
			return charset == null ? baos.toString() : new String(baos.toString().getBytes(charset), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将内存中的字节数组输出到文件
	 * @param buf  : 输出的字节数组
	 * @param file : 输出的位置
	 */
	public static void byteOutFile(byte[] buf, File file) {
		if(buf == null || buf.length > 0 || file == null || !file.exists() || !file.isFile()) {
			// throw new IllegalArgumentException("将内存中的字节数组输出到文件参数非法");
			return;
		}
		
		try(FileChannel channel = new FileOutputStream(file).getChannel()){
			channel.write(ByteBuffer.wrap(buf));
			channel.force(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将输入流输出到指定路径
	 * @param is   : 输入流
	 * @param outFile : 输出路径
	 */
	public static void outputFile(InputStream is, File outFile) {
		if (is == null || outFile == null) {
			throw new IllegalArgumentException("将输入流输出到指定路径参数非法");
		}
		
		try(ReadableByteChannel readChannel = Channels.newChannel(is);
			FileChannel outChannel = new FileOutputStream(outFile).getChannel()) {
			outChannel.transferFrom(readChannel, 0, is.available());
		}  catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭IO流
	 * @param stream
	 */
	public static void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * 比较小文件内容和字符串是否相等
	 * @param file
	 * @param str
	 * @return 
	 */
	public static boolean compareFileAndString(File file, String str) {
		if(file.length() == str.getBytes().length) {
			return new String(readFile(file)).equals(str);
		}
		return false;
	}
	
	/**
	 * 删除目录
	 * @param dirFile
	 */
	public static void deleteDir(File dirFile) {
		if (dirFile == null || !dirFile.exists() || !dirFile.isDirectory()) {
			return;
		}
	}
	
	
	/**
	 * 删除文件
	 * @param file
	 */
	public static void deleteFile(File file) {
		if(file.exists() && file.isFile()) {
			file.delete();
		}
	}

	 /**
     * 格式化URL, 去除URL中的\等, 防止以为是文件系统的分隔符
     * @param url
     * @return
     */
    public static String formattingUrl(String url) {
		return url.replaceAll("[^\\w]", "");
    }
}
