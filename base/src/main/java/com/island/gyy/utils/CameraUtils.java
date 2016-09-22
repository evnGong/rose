package com.island.gyy.utils;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 相机工具类
 * 
 * @author BD
 * 可能所需要权限 : <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 */
public class CameraUtils {

	/** 从相册裁剪小图片请求码 */
	public static final byte PHOTO_ALBUM_BITMAP_REQEST_CODE = 0x001;

	/** 从相册裁剪大图片请求码 */
	public static final byte PHOTO_ALBUM_BIG_REQEST_CODE = 0x002;

	/** 调用相机拍照请求码 */
	public static final byte PHOTOGRAPHIC_REQEST_CODE = 0x003;

	/** 拍照后裁剪图片请求码 */
	public static final byte PHOTO_ZOOM_IMAGE_REQEST_CODE = 0x004;

	public static final int outputX = 800;      // 拍照或相册选择后输出宽高
	public static final int outputY = 500;
	public static final int compressSize = 500; // 图片压缩后的大小

	/**
	 * 从相册截小图
	 * 
	 * @param fragment
	 * @param outputX
	 * @param outputY
	 * @param requestCode : 请求码
	 */
	public static void getPhotoAlbumSmallBitmap(Fragment fragment, int outputX, int outputY) {

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 3);
		intent.putExtra("aspectY", 2);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", "PNG");
		intent.putExtra("noFaceDetection", true);
		fragment.startActivityForResult(intent, PHOTO_ALBUM_BITMAP_REQEST_CODE);
		// 使用
		// switch (requestCode) {
		// case CameraUtils.PHOTO_ALBUM_BITMAP_REQEST_CODE: // 相册
		// Bitmap bitmap = data.getParcelableExtra("data");
		// ImageView.setImageBitmap(bitmap);
		// break;
	}

	/**
	 * 从相册截大图
	 */
	public static void getPhotoAlbumBigBitmap(Fragment fragment, Uri imageUri, int outputX, int outputY) {

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setDataAndType(imageUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 3);
		intent.putExtra("aspectY", 2);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", "PNG");
		intent.putExtra("noFaceDetection", true);
		fragment.startActivityForResult(intent, PHOTO_ALBUM_BIG_REQEST_CODE);
		// 使用
		// case CameraUtils.PHOTO_ALBUM_BIG_REQEST_CODE: // 相册
		// if(imageUri != null){
		// bitmap = CameraUtils.decodeUriAsBitmap(activity, imageUri);
		// idCardBgIb.setImageBitmap(bitmap);
		// }
	}

	/**
	 * 从相册截大图
	 */
	public static void getPhotoAlbumBigBitmap(Fragment fragment, Uri imageUri, int aspectX, int aspectY, int outputX, int outputY) {

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setDataAndType(imageUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", "PNG");
		intent.putExtra("noFaceDetection", true);
		fragment.startActivityForResult(intent, PHOTO_ALBUM_BIG_REQEST_CODE);
	}

	/**
	 * 调用系统相机拍照
	 * 
	 * @param activity
	 * @param uri
	 *            : URI
	 * @param reqestCode
	 *            : 请求码
	 */
	public static void photographic(Fragment fragment, Uri uri) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(ImageColumns.ORIENTATION, 0);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		fragment.startActivityForResult(intent, PHOTOGRAPHIC_REQEST_CODE);

		// 使用
		// case CameraUtils.PHOTOGRAPHIC_REQEST_CODE: // 拍照后 ————> 裁剪
		// CameraUtils.startPhotoZoomImage(this, imageUri, 1000, 500);
		// break;
		//
		// case CameraUtils.PHOTO_ZOOM_IMAGE_REQEST_CODE: // 裁剪后显示
		// lincenseIb.setImageBitmap(CameraUtils.saveAndCompressImage(file,
		// 500));
		// break;
	}

	/**
	 * 裁剪图片
	 * 
	 * @param fragment : 片段
	 * @param uri      : 输出地址
	 * @param outputX  : 输出宽度(证件照推存 1000)
	 * @param outputY  : 输出高度(证件照推存 500)
	 * @param requestCode  : 裁剪请求码
	 */
	public static void startPhotoZoomImage(Fragment fragment, Uri uri, int outputX, int outputY) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true"); // crop=true 有这句才能出来最后的裁剪页面.
		intent.putExtra("aspectX", 3); // 这两项为裁剪框的比例
		intent.putExtra("aspectY", 2); // x:y=1:1
		intent.putExtra("outputX", outputX); // 图片输出大小
		intent.putExtra("outputY", outputY);
		intent.putExtra("output", uri); // 输出地址
		intent.putExtra("outputFormat", "PNG"); // 返回格式
		fragment.startActivityForResult(intent, PHOTO_ZOOM_IMAGE_REQEST_CODE);
	}

	/**
	 * 裁剪图片并指定裁剪的宽高比
	 * 
	 * @param fragment  : 片段
	 * @param uri       : 输出地址
	 * @param aspectX   : 裁剪的 X 比值
	 * @param aspectY   : 裁剪的 Y 比值
	 * @param outputX   : 输出宽度(证件照推存 1000)
	 * @param outputY   : 输出高度(证件照推存 500)
	 * @param requestCode : 裁剪请求码
	 */
	public static void startPhotoZoomImage(Fragment fragment, Uri uri, int aspectX, int aspectY, int outputX, int outputY) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true"); // crop=true 有这句才能出来最后的裁剪页面.
		intent.putExtra("aspectX", aspectX); // 这两项为裁剪框的比例
		intent.putExtra("aspectY", aspectY); // x:y=1:1
		intent.putExtra("outputX", outputX); // 图片输出大小
		intent.putExtra("outputY", outputY);
		intent.putExtra("output", uri); // 输出地址
		intent.putExtra("outputFormat", "PNG"); // 返回格式
		fragment.startActivityForResult(intent, PHOTO_ZOOM_IMAGE_REQEST_CODE);
	}

	/**
	 * 保存和压缩图片
	 * @param file          : 文件保存路径
	 * @param compressSize  : 图片压缩后的大小
	 * @return
	 */
	public static Bitmap saveAndCompressImage(File file, int compressSize) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;
			Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), options);
			// 压缩图片
			if (compressSize >= 0) {
				bitmap = compressImage(bitmap, compressSize);
			}

			if (bitmap != null) {
				// 保存图片
				FileOutputStream fos = null;
				fos = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();
				return bitmap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 压缩图片到指定大小
	 * 
	 * @param image  图片资源
	 * @param size   图片大小(单位KB)
	 * @return Bitmap
	 */
	public static Bitmap compressImage(Bitmap image, int size) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		image.compress(Bitmap.CompressFormat.PNG, 100, baos);
		int options = 100;
		// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
		while (baos.toByteArray().length / 1024 > size) {
			baos.reset(); // 重置baos即清空baos
			options -= 10; // 每次都减少10
			image.compress(Bitmap.CompressFormat.PNG, options, baos); // 压缩options%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray()); // 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null); // 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 从指定的 URI 中解码图片
	 * 
	 * @param mContext
	 * @param uri
	 * @param options
	 * @return
	 */
	public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
		if (context == null || uri == null)
			return null;
		
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

}
