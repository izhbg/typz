package com.izhbg.typz.shop.common.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片打水印工具类
 * @ClassName: ImageMarkUtils 
 * @date 2015年10月3日 下午6:15:03
 */
public final class ImageMarkUtils {
  
  private static Logger log = LoggerFactory.getLogger(ImageMarkUtils.class);
  
  /**图片宽的二分之一*/
  private static int xPoint = 2;
  /**图片高的六分之一*/
  private static int yPoint = 6;
  
  public ImageMarkUtils() {}
  /**
   * 把图片印刷到图片上
   * 
   * @param pressImg -- 水印文件
   * @param targetImg -- 目标文件
   */
  public final static void imageMark(String pressImg, String targetImg, int x, int y) {
    try {
      x = xPoint;
      y = yPoint;
      // 目标文件
      File _file = new File(targetImg);
      Image src = ImageIO.read(_file);
      int width = src.getWidth(null);
      int height = src.getHeight(null);
      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      Graphics2D g = image.createGraphics();     
      g.drawImage(src, 0, 0, width, height, null);
      // 水印文件
      File _filebiao = new File(pressImg);
      Image src_biao = ImageIO.read(_filebiao);
      int wideth_biao = src_biao.getWidth(null);
      int height_biao = src_biao.getHeight(null);
      float alpha = 0.9f; // 透明度    
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));  
      /**
       * 水印文件加在横向1/2，纵向5/6的位置
       */
      g.drawImage(src_biao, width/x, height/y*5, wideth_biao, height_biao, null);
      // 水印文件结束
      g.dispose();
     /* FileOutputStream out = new FileOutputStream(targetImg);
      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
      encoder.encode(image);*/
      String formatName = targetImg.substring(targetImg.lastIndexOf(".") + 1);
      ImageIO.write(image, /*"GIF"*/ formatName /* format desired */ , new File(targetImg) /* target */ );
      //out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 打印文字水印图片
   * 
   * @param pressText --文字
   * @param targetImg -- 目标图片
   * @param fontName -- 字体名
   * @param fontStyle -- 字体样式
   * @param color -- 字体颜色
   * @param fontSize -- 字体大小
   * @param x -- 偏移量
   * @param y
   */
  public static void imageMark(String pressText, String targetImg, String fontName, int fontStyle,
      int color, int fontSize, int x, int y) {
    try {
      File _file = new File(targetImg);
      Image src = ImageIO.read(_file);
      int wideth = src.getWidth(null);
      int height = src.getHeight(null);
      BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
      Graphics g = image.createGraphics();
      g.drawImage(src, 0, 0, wideth, height, null);
      g.setColor(Color.RED);
      g.setFont(new Font(fontName, fontStyle, fontSize));
      g.drawString(pressText, wideth - fontSize - x, height - fontSize / 2 - y);
      g.dispose();
      //FileOutputStream out = new FileOutputStream(targetImg);
      String formatName = targetImg.substring(targetImg.lastIndexOf(".") + 1);
     // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
      //encoder.encode(image);
      ImageIO.write(image, /*"GIF"*/ formatName /* format desired */ , new File(targetImg) /* target */ );
     // out.close();
    } catch (Exception e) {
      log.error("ImageMarkUtils imageMark Exception : " + e.getMessage());
    }
  }
  

  
  public static void main(String[] args) {
    imageMark("O:/logo.png", "O:/111.jpg", 1, 1);
  }
}

