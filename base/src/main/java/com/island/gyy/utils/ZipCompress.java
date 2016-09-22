package com.island.gyy.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class ZipCompress {

	
//	public static String inputToZipString(InputStream is) {
//		if (is == null) return null;
//		
//		try(GZIPInputStream gzipInputStream = new GZIPInputStream(is);
//			ByteArrayOutputStream baos      = new ByteArrayOutputStream();
//			ReadableByteChannel readChannel = Channels.newChannel(gzipInputStream);) {
//			
//			ByteBuffer buffer = ByteBuffer.allocate(1024);
//			while(readChannel.read(buffer) > 0) {
//				buffer.flip();
//				for (int i = 0, len = buffer.remaining(); i < len; i++) {
//					baos.write(buffer.get());
//				}
//				buffer.clear();
//			}
//			return baos.toString();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	
	/**
	 * GZIP压缩文件
	 * @param file   : 目标文件
	 * @param outDir : 输出目录, 为空时默认与
	 * @return
	 */
	public static String compress(final File file, final File outDir) {
		
		if(file != null && file.exists() && file.isFile()) {
			String fileName = file.getName();
			
//			目标文件不为压缩文件
			if(!fileName.endsWith(".zip") && !fileName.endsWith(".gz")) {
				File outFile = new File(outDir == null ? file.getParentFile() : outDir, fileName + ".gz");
				
				if(!outFile.exists()) {     // 不覆盖已存在的压缩文件
					InputStream in = null;
					OutputStream out = null;
					try {
						in  = new BufferedInputStream(new FileInputStream(file));
						out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(outFile)));
						int len = 0;
						while((len = in.read()) != -1) out.write(len);
						out.flush();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}finally {
						closeStream(in);
						closeStream(out);
					}
				}
				return outFile.getAbsolutePath();
			}
		}
		return null;
	}
	
	
	
	/**
	 * 解压GZIP压缩文件
	 * @param zipFile : gzip 压缩文件
	 * @param outDir  : 解压目录
	 * @return
	 */
	public static String decompression(final File zipFile, final File outDir) {
		if(zipFile != null && zipFile.exists() && zipFile.isFile()) {
			String fileName = zipFile.getName();
			File outFile = new File(outDir == null ? zipFile.getParentFile() : outDir, fileName.substring(0, fileName.lastIndexOf(".")));
//			解压文件已存在则不
			if(!outFile.exists()) {
				InputStream in = null;
				OutputStream out = null;
				try {
					in  = new BufferedInputStream(new GZIPInputStream(new FileInputStream(zipFile)));
					out = new BufferedOutputStream(new FileOutputStream(outFile));
					int len = 0;
					while((len = in.read()) != -1) out.write(len);
					out.flush();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					closeStream(in);
					closeStream(out);
				}
			}
			return outFile.getAbsolutePath();
		}
		return null;
	}
	
	
	
	
	
	public static void closeStream(Closeable stream) {
		try {
			if(stream != null) stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
