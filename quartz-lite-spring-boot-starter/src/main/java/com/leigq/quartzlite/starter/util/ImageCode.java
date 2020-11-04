package com.leigq.quartzlite.starter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 图形验证码
 * <br/>
 * 参考：<a href='https://blog.csdn.net/jiahao791869610/article/details/79175268'>前后端分离情况下的图片验证码验证问题</a>
 *
 * @author leiguoqing
 * @date 2020 -08-11 14:07:54
 */
public final class ImageCode {

	/**
	 * 验证码字符个数
	 */
	private static final int VALID_CODE_COUNT = 4;
	/**
	 * 验证码图片的宽
	 */
	private static final int WEIGHT = 70;
	/**
	 * 验证码图片的高
	 */
	private static final int HEIGHT = 30;
	/**
	 * 干扰线数量
	 */
	private static final int INTERFERENCE_LINE_COUNT = 155;
	/**
	 * 字体数组.
	 */
	private static final String[] FONT_NAMES = {"宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};
	/**
	 * 字体风格, 0是无样式，1是加粗，2是斜体，3是加粗加斜体
	 */
	private static final int[] FONT_STYLES = {Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD + Font.ITALIC};
	/**
	 * 用来生成验证码字符集，已去除 1、l、0、i、I、L、O 不容易区分的字符
	 */
	private static final String CODES = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
	private final Logger log = LoggerFactory.getLogger(ImageCode.class);
	/**
	 * 用来保存验证码的文本内容
	 */
	private String validCodeText;


	/**
	 * 随机数对象
	 */
	private Random random;

	public ImageCode() {
		try {
			random = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			log.error("获取随机数对象异常", e);
		}
	}

	/**
	 * 获取验证码图片的方法
	 *
	 * @return the image
	 */
	public BufferedImage getImage() {
		// 画背景图片
		BufferedImage image = createBackgroundImage();
		//获取画笔
		Graphics2D g = (Graphics2D) image.getGraphics();
		StringBuilder sb = new StringBuilder();
		// 画干扰线
		drawInterferenceLine(image);
		// 画验证码字符
		for (int i = 0; i < VALID_CODE_COUNT; i++) {
			// 随机生成字符，因为只有画字符串的方法，没有画字符的方法，所以需要将字符变成字符串再画
			String s = randomChar() + "";
			// 添加到StringBuilder里面
			sb.append(s);
			// 定义字符的x坐标
			float x = i * 1.0F * WEIGHT / 4;
			// 设置字体，随机
			g.setFont(randomFont());
			// 设置颜色，随机 这里为什么是150，因为当r，g，b都为255时，即为白色，为了好辨认，需要颜色深一点。
			g.setColor(getRandColor(0, 150));
			g.drawString(s, x, HEIGHT - 5f);
		}
		this.validCodeText = sb.toString();
		return image;
	}

	/**
	 * 获取验证码文本的方法
	 *
	 * @return the text
	 */
	public String getValidCodeText() {
		return validCodeText;
	}


	/**
	 * 获取随机字体
	 *
	 * @return the font
	 */
	private Font randomFont() {
		//获取随机的字体
		int fontNameIndex = random.nextInt(FONT_NAMES.length);
		String fontName = FONT_NAMES[fontNameIndex];
		//随机获取字体的样式，0是无样式，1是加粗，2是斜体，3是加粗加斜体
		int fontStyleIndex = random.nextInt(FONT_STYLES.length);
		int style = FONT_STYLES[fontStyleIndex];
		//随机获取字体的大小
		int size = random.nextInt(5) + 24;
		//返回一个随机的字体
		return new Font(fontName, style, size);
	}

	/**
	 * 获取随机字符
	 *
	 * @return the char
	 */
	private char randomChar() {
		int index = random.nextInt(CODES.length());
		return CODES.charAt(index);
	}

	/**
	 * 画干扰线，验证码干扰线用来防止计算机解析图片
	 *
	 * @param image the image
	 */
	private void drawInterferenceLine(BufferedImage image) {
		Graphics2D g = (Graphics2D) image.getGraphics();
		for (int i = 0; i < INTERFERENCE_LINE_COUNT; i++) {
			int x = random.nextInt(WEIGHT);
			int y = random.nextInt(HEIGHT);
			int xl = random.nextInt(WEIGHT);
			int yl = random.nextInt(HEIGHT);
			g.setColor(getRandColor(160, 200));
			g.drawLine(x, y, x + xl, y + yl);
		}
	}

	/**
	 * 创建背景图片
	 *
	 * @return the buffered image
	 */
	private BufferedImage createBackgroundImage() {
		//创建图片缓冲区
		BufferedImage image = new BufferedImage(WEIGHT, HEIGHT, BufferedImage.TYPE_INT_RGB);
		//获取画笔
		Graphics2D g = (Graphics2D) image.getGraphics();
		// 设定图像背景色(因为是做背景，所以偏淡)
		g.setColor(getRandColor(200, 250));
		// 设置填充矩形范围
		g.fillRect(0, 0, WEIGHT, HEIGHT);
		//返回一个图片
		return image;
	}


	/**
	 * 给定范围获得随机颜色
	 *
	 * @param fc the fc
	 * @param bc the bc
	 * @return the rand color
	 */
	private Color getRandColor(int fc, int bc) {
		fc = Math.min(fc, 255);
		bc = Math.min(bc, 255);
		int r = fc + this.random.nextInt(bc - fc);
		int g = fc + this.random.nextInt(bc - fc);
		int b = fc + this.random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
