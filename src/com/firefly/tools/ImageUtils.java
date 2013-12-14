package com.firefly.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtils {

	/**
	 * * 对图片进行放大 *
	 * 
	 * @param originalImage
	 *            原始图片 *
	 * @param times
	 *            放大倍数 *
	 * @return
	 */
	public static BufferedImage zoomInImage(BufferedImage originalImage,
			Integer times) {
		int width = originalImage.getWidth() * times;
		int height = originalImage.getHeight() * times;
		BufferedImage newImage = new BufferedImage(width, height, originalImage
				.getType());
		Graphics g = newImage.getGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return newImage;
	}

	/**
	 * 对图片进行放大
	 * 
	 * @param srcPath
	 *            原始图片路径(绝对路径)
	 * @param newPath
	 *            放大后图片路径（绝对路径）
	 * @param times
	 *            放大倍数
	 * @return 是否放大成功
	 */
	public static boolean zoomInImage(String srcPath, String newPath,
			Integer times) {
		BufferedImage bufferedImage = null;
		try {
			File of = new File(srcPath);
			if (of.canRead()) {
				bufferedImage = ImageIO.read(of);
			}
		} catch (IOException e) {
			// TODO: 打印日志
			return false;
		}
		if (bufferedImage != null) {
			bufferedImage = zoomInImage(bufferedImage, times);
			try {
				// TODO: 这个保存路径需要配置下子好一点
				ImageIO.write(bufferedImage, "JPG", new File(newPath)); // 保存修改后的图像,全部保存为JPG格式
			} catch (IOException e) {
				// TODO 打印错误信息
				return false;
			}
		}
		return true;
	}

	/**
	 * 对图片进行缩小
	 * 
	 * @param originalImage
	 *            原始图片
	 * @param times
	 *            缩小倍数
	 * @return 缩小后的Image
	 */
	public static BufferedImage zoomOutImage(BufferedImage originalImage,
			Integer times) {
		int width = originalImage.getWidth() / times;
		int height = originalImage.getHeight() / times;
		BufferedImage newImage = new BufferedImage(width, height, originalImage
				.getType());
		Graphics g = newImage.getGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return newImage;
	}

	/**
	 * 对图片进行缩小
	 * 
	 * @param srcPath
	 *            源图片路径（绝对路径）
	 * @param newPath
	 *            新图片路径（绝对路径）
	 * @param times
	 *            缩小倍数
	 * @return 保存是否成功
	 */
	public static boolean zoomOutImage(String srcPath, String newPath,
			Integer times) {
		BufferedImage bufferedImage = null;
		try {
			File of = new File(srcPath);
			if (of.canRead()) {
				bufferedImage = ImageIO.read(of);
			}
		} catch (IOException e) {
			// TODO: 打印日志
			return false;
		}
		if (bufferedImage != null) {
			bufferedImage = zoomOutImage(bufferedImage, times);
			try {
				// TODO: 这个保存路径需要配置下子好一点
				ImageIO.write(bufferedImage, "JPG", new File(newPath)); // 保存修改后的图像,全部保存为JPG格式
			} catch (IOException e) {
				// TODO 打印错误信息
				return false;
			}
		}
		return true;
	}
	
	
	/**
     * public final static String getPressImgPath() { return ApplicationContext
     * .getRealPath("/template/data/util/shuiyin.gif"); }
     */

    /**
     * 把图片印刷到图片上
     * 
     * @param pressImg --
     *            水印文件
     * @param targetImg --
     *            目标文件
     * @param x
     *            --x坐标
     * @param y
     *            --y坐标
     */
    public final static void pressImage(String pressImg, String targetImg,
            int x, int y) {
        try {
            //目标文件
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);

            //水印文件
            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                    (height - height_biao) / 2, wideth_biao, height_biao, null);
            //水印文件结束
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印文字水印图片
     * 
     * @param pressText
     *            --文字
     * @param targetImg --
     *            目标图片
     * @param fontName --
     *            字体名
     * @param fontStyle --
     *            字体样式
     * @param color --
     *            字体颜色
     * @param fontSize --
     *            字体大小
     * @param x --
     *            偏移量
     * @param y
     */

    public static void pressText(String pressText, String targetImg,
            String fontName, int fontStyle, int color, int fontSize, int x,
            int y) {
        try {
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            
            g.setColor(Color.RED);
            g.setFont(new Font(fontName, fontStyle, fontSize));

            g.drawString(pressText, wideth - fontSize - x, height - fontSize
                    / 2 - y);
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

	
	

	public static void main(String[] args) {
		//boolean testIn = zoomInImage("D:/MyWorkspace/workspace/fftest/page/photo/header_DSC_0362.JPG", "D:/MyWorkspace/workspace/fftest/page/photo/in_header_DSC_0362.JPG", 4);
		//if (testIn) {
		//	System.out.println("in ok");
		//}
		boolean testOut = zoomOutImage("D:/MyWorkspace/workspace/fftest/page/photo/DSC_0346.JPG", "D:/MyWorkspace/workspace/fftest/page/photo/out_DSC_0346.JPG", 2);
		if (testOut) {
			pressText("iyoutianxia\r\n 爱游天下 ", "D:/MyWorkspace/workspace/fftest/page/photo/out_DSC_0346.JPG", "宋体", Font.BOLD, Color.RED.getRed() ,45, 400, 100);
			System.out.println("out ok");
		}
		

		
		//boolean testOut = zoomOutImage("D:/MyWorkspace/workspace/fftest/page/photo/header_DSC_0362.JPG", "D:/MyWorkspace/workspace/fftest/page/photo/out_header_DSC_0362.JPG", 8);
		//if (testOut) {
		//	System.out.println("out ok");
		//}
		
		
	}
	
	
	
	
	

}