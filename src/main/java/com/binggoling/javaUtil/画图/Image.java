package com.binggoling.javaUtil.画图;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Image {
	public static void main(String[] args) {
		int width = 1024;
		int height = 640;
		int yTop = 50;
		Color color = new Color(0, 0, 0);
		Font font = new Font("宋体", Font.PLAIN, 20);
		String str[] = {"四川省气候预估图","2016年01月-2017年01月"};
		String path = "C:/Users/quanxun/Desktop/quanxun/test1.png";

		long startTime = System.currentTimeMillis();
		createImage(width, height, yTop, color, font, str, path);
		System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
	}

	/**
	 * java画图，背景为透明，文字居中
	 * 
	 * @param width
	 *            图片的宽
	 * @param height
	 *            图片的高
	 * @param yTop
	 *            文字距顶的像素
	 * @param color
	 *            字体颜色
	 * @param font
	 *            字体样式设置
	 * @param str
	 *            画图的文字
	 * @param path
	 *            出图地址
	 * @return
	 */
	public static boolean createImage(int width, int height, int yTop, Color color, Font font, String str[],
			String path) {
		try {
			// 创建BufferedImage对象
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 获取Graphics2D
			Graphics2D g2d = image.createGraphics();

			// ---------- 增加下面的代码使得背景透明 -----------------
			image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
			g2d.dispose();
			g2d = image.createGraphics();
			// ---------- 背景透明代码结束 -----------------

			// 画图
			g2d.setColor(color);
			g2d.setStroke(new BasicStroke(1));
			g2d.setFont(font);
			
			for (int i = 0; i < str.length; i++) {
				// 获取字符的长度
				int strWidth = g2d.getFontMetrics().stringWidth(str[i]);
				g2d.drawString(str[i], (width - strWidth) / 2, yTop);
			}

			// 释放对象
			g2d.dispose();
			// 保存文件
			String fileSuffixName = path.split("\\.")[1];
			ImageIO.write(image, fileSuffixName, new File(path));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	  public static void main(String[] args) throws Exception {  
//	        createImage("请A1003到3号窗口", new Font("宋体", Font.BOLD, 30), new File(  
//	                "e:/a.png"), 4096, 64);  
//	        createImage("请A1002到2号窗口", new Font("黑体", Font.BOLD, 35), new File(  
//	                "e:/a1.png"), 4096, 64);  
//	        createImage("请A1001到1号窗口", new Font("黑体", Font.PLAIN, 40), new File(  
//	                "e:/a2.png"), 4096, 64);  
//	    } 

	/**
	 * 根据str,font的样式以及输出文件目录
	 * 
	 * @param str
	 * @param font
	 * @param outFile
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	public static void createImage(String str, Font font, File outFile, Integer width, Integer height)
			throws Exception {
		// 创建图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();
		g.setClip(0, 0, width, height);
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景
		g.setColor(Color.red);// 在换成黑色
		g.setFont(font);// 设置画笔字体
		/** 用于获得垂直居中y */
		Rectangle clip = g.getClipBounds();
		FontMetrics fm = g.getFontMetrics(font);
		int ascent = fm.getAscent();
		int descent = fm.getDescent();
		int y = (clip.height - (ascent + descent)) / 2 + ascent;
		for (int i = 0; i < 6; i++) {// 256 340 0 680
			g.drawString(str, i * 680, y);// 画出字符串
		}
		g.dispose();
		ImageIO.write(image, "png", outFile);// 输出png图片
	}

}
