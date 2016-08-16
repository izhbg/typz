package com.izhbg.typz.shop.common.util;

import org.hibernate.annotations.common.util.StringHelper;
import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Title:ImageUtil Description: 用im4java对图片进行处理
 * 
 * @Create_by:wangcs
 */
public class ImageUtil {

  /**
   * ImageMagick的路径
   */
  public static String imageMagickPath = "";

  private static Logger log = LoggerFactory.getLogger(ImageUtil.class);


  /**
   * 根据坐标裁剪默认尺寸的图片
   * 
   * @Create_by:wangcs
   * @param fromPath 要裁剪图片的路径
   * @param toPath 裁剪图片后的路径
   * @param x 起始横坐标
   * @param y 起始纵坐标
   * @param w 宽度
   * @param h 高度
   * @throws Exception
   */
  public static boolean cutImage(String fromPath, String toPath, int x, int y, int w, int h){
    return cutImage(fromPath, toPath, x, y, w, h, 0, 0);
  }

  /**
   * 根据坐标裁剪图片
   * 
   * @Create_by:wangcs
   * @param fromPath 要裁剪图片的路径
   * @param toPath 裁剪图片后的路径
   * @param x 起始横坐标
   * @param y 起始纵坐标
   * @param w 宽度
   * @param h 高度
   * @param _width 剪裁的宽度
   * @param _hight 剪裁的高度
   * @throws Exception
   */
  public static boolean cutImage(String fromPath, String toPath,int x, int y, int w, int h,
      int _width, int _hight){
    IMOperation op = new IMOperation();
    op.addImage(fromPath);
    // 图片裁剪
    op.crop(w, h, x, y);
    op.p_repage(); // gif清空图片以外的空白
    if (_width > 0) {
      op.resize(_width, _hight);
    }
    op.addImage(toPath);
    ConvertCmd convert = new ConvertCmd();
    // linux下不要设置此值，不然会报错
    if (StringHelper.isNotEmpty(imageMagickPath)) {
      convert.setSearchPath(imageMagickPath);
    }

    boolean result = true;
    try {
      convert.run(op);
    } catch (Exception e) {
      log.error("使用imageMagick处理图片失败");
      result = false;
    }

    modifyImageDisplayStyle(toPath,toPath);
    return result;
  }

  /**
   * 缩放图片
   * 
   * @Create_by:wangcs
   * @param fromPath 源图片路径
   * @param toPath 缩放后图片的路径
   * @param width 缩放后的图片宽度
   * @param height 缩放后的图片高度
   * @param flag true：按原图比例缩放，false：按指定大小缩放
   * @throws Exception
   */
  public static boolean cutImage(String fromPath, String toPath, int width, int height, boolean flag){
    IMOperation op = new IMOperation();
    op.addImage(fromPath);
    op.coalesce(); // gif图片需要
    if (width == 0) { // 根据高度缩放图片
      op.resize(null, height);
    } else if (height == 0) { // 根据宽度缩放图片
      op.resize(width, null);
    } else {
      if (flag) {
        op.resize(width, height);
      } else {
        op.resize(width, height, "!");
      }
    }
    op.addImage(toPath);
    ConvertCmd convert = new ConvertCmd();
    // linux下不要设置此值，不然会报错
    if (StringHelper.isNotEmpty(imageMagickPath)) {
      convert.setSearchPath(imageMagickPath);
    }

    boolean result = true;
    try {
      convert.run(op);
    } catch (Exception e) {
      log.error("使用imageMagick处理图片失败");
      result = false;
    }
    return result;
  }

  /**
   * 根据指定尺寸缩放图片
   * 
   * @Create_by:wangcs
   * @param fromPath 源图片路径
   * @param toPath 缩放后图片的路径
   * @param width 缩放后的图片宽度
   * @param height 缩放后的图片高度
   * @throws Exception
   */
  public static boolean cutImage(String fromPath, String toPath, int width, int height){
    return cutImage(fromPath, toPath, width, height, true);
  }

  /**
   * 图片水印
   *
   * @Create_by:wangcs
   * @param srcImagePath   源图片
   * @param waterImagePath 水印
   * @param destImagePath  生成图片
   */
  public static boolean waterMark(String waterImagePath, String srcImagePath, String destImagePath){
    BufferedImage bi ; // 读入文件
    try {
      bi = ImageIO.read(new File(srcImagePath));
    } catch (IOException e) {
      log.error("读取图片源文件失败。");
      return false;
    }
    int width = bi.getWidth(); // 得到源图宽
    int height = bi.getHeight();

    IMOperation op = new IMOperation();
    //设置坐标系中心点
    op.gravity("north");
    //设置偏移坐标
    op.geometry(0,0,0,height*5/6);
    //设置水印图片透明度
    op.dissolve(90);
    op.addImage(waterImagePath);
    op.addImage(srcImagePath);
    op.addImage(destImagePath);
    CompositeCmd cmd = new CompositeCmd();
    // linux下不要设置此值，不然会报错
    if (StringHelper.isNotEmpty(imageMagickPath)) {
      cmd.setSearchPath(imageMagickPath);
    }
    boolean result = true;
    try {
      cmd.run(op);
    } catch (Exception e) {
      result = false;
    }
    modifyImageDisplayStyle(destImagePath,destImagePath);
    return result;
  }

  /**
   * 修改图片显示方式为  由模糊到清晰逐渐显示
   * @param fromPath
   * @param toPath
   * @return
   */
  private static boolean modifyImageDisplayStyle(String fromPath,String toPath){
    IMOperation op = new IMOperation();
    op.interlace("plane");
    op.quality(85d);
    op.define("filter:blur=0.88549061701764");
    op.strip();
    //op.samplingFactor(4d,1d);
    op.addImage(fromPath);
    op.addImage(toPath);
    ConvertCmd cc = new ConvertCmd();
    cc.setSearchPath(imageMagickPath);

    boolean flag = true;
    try {
      cc.run(op);
    } catch (Exception e) {
      flag = false;
    }
    return flag;
  }

  /**
   * 给图片填充空白为正方形
   *
   * @param srcImage  源图
   * @param destImage 处理后图
   * @param x800      填充的边长
   */
  public static void fillWhite(String srcImage, String destImage, int width) throws Exception {

    //String formatName = srcImage.substring(srcImage.lastIndexOf('.') + 1);
    BufferedImage image = ImageIO.read(new File(srcImage));

    int left;
    int top;
    int dWidth;

    int maxWidth = image.getWidth() > image.getHeight() ? image.getWidth() : image.getHeight();

    if (width <= maxWidth) {
      dWidth = maxWidth;
      left = (maxWidth - image.getWidth()) / 2;
      top = (maxWidth - image.getHeight()) / 2;
    } else {
      left = (width - image.getWidth()) / 2;
      top = (width - image.getHeight()) / 2;
      dWidth = width;
    }

    BufferedImage subImage = new BufferedImage(dWidth, dWidth, 1);
    Graphics g = subImage.getGraphics();
    g.setColor(Color.white);
    g.fillRect(0, 0, dWidth, dWidth);
    g.drawImage(image, left, top, null);
    g.dispose();
    ImageIO.write(subImage, "jpg", new File(destImage));
  }

  public static void main(String[] args) throws Exception {
    //ImageMarkUtils.imageMark("d:/test/watermark-logo.png","d:/test/test1.jpg",0, 0);
    //cutImage("d:/test/test.jpg", "d:/test/test1.jpg",800,800,true);
    // cutImage("d:/test/test.jpg", "d:/test/test1.jpg","800","800","800","800",1700,1700);
    //    waterMark("d:/test/watermark-logo.png","d:/test/test1.jpg","d:/test/test2.jpg");

    //    Thumbnails.of("d:/test/test3.png").size(500,500).keepAspectRatio(true).toFile("d:/test/test4.jpg");
    //ImageIO.write(bi, "jpg", new File("d:/test/test4.jpg"));


    /*String cmd = String
        .format("convert %s -interlace plane -quality 85 -define filter:blur=0.88549061701764 -strip -scale %dx %s",
            "d:/test/test.jpg", 800, "d:/test/test7.jpg");
    IMOperation op = new IMOperation();
    op.crop(1000, 1000, 200, 200);
    op.p_repage(); // gif清空图片以外的空白
    op.resize(800, 800);
    op.interlace("plane");
    op.quality(85d);
    op.define("filter:blur=0.88549061701764");
    op.strip();
    //op.samplingFactor(4d,1d);
    op.addImage("d:/test/test.jpg");
    op.addImage("d:/test/test7.jpg");
    ConvertCmd cc = new ConvertCmd();
    cc.setSearchPath(imageMagickPath);
    //cc.setCommand(cmd);
    cc.run(op);*/

   /* Thumbnails.of("d:/test/test.jpg").sourceRegion(200, 200, 300, 500).size(500, 500)
        .toFile("d:/test/test4.jpg");*/

    fillWhite("d:/test/test4.jpg", "d:/test/test4.jpg",800);
  }

}
