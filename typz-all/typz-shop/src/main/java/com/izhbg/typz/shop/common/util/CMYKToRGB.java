/**
 * @Title: CMYKToRGB.java
 * @Package com.zgxcw.service.infrastructure.goods.utils
 * @Description: TODO
 * @author weiming
 * @date 2015年12月1日 上午3:18:13
 */
package com.izhbg.typz.shop.common.util;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.NodeList;

/**
 * 
 * @ClassName: CMYKToRGB
 * @Description: TODO
 * @date 2015年12月1日 上午3:18:13
 */
public class CMYKToRGB {

  private static Logger log = Logger.getLogger(ImageMarkUtils.class);
  // 大部分情况都转换没问题，有极个别的转换不成功。现在想，是不是调用convert.exe 更方便 呵呵
  
  /**
   * 判断是RGB还是CMYK色系的图片
   * @Description: 
   * @param filename
   * @return
   * @throws IOException    
   * boolean
   */
  public static boolean isRgbOrCmyk(String filename) throws IOException {
    File file = new File(filename);
    boolean isRgb = true;// true是Rgb否则是Cmyk
    // 创建输入流
    ImageInputStream input = ImageIO.createImageInputStream(file);
    Iterator readers = ImageIO.getImageReaders(input);
    if (readers == null || !readers.hasNext()) {
      throw new RuntimeException("No ImageReaders found");
    }
    ImageReader reader = (ImageReader) readers.next();
    reader.setInput(input);
    // 获取文件格式
    BufferedImage image;
    try {
      // 尝试读取图片 (包括颜色的转换).
      reader.read(0); // RGB
      isRgb = true;
    } catch (IIOException e) {
      // 读取Raster (没有颜色的转换).
      reader.readRaster(0, null);// CMYK
      isRgb = false;
    }
    return isRgb;
  }
  
  /** 
   * 判断，true是RGB色彩模式的图片，false是CMYK色彩模式的图片。
   */
  public static boolean isRgbOrCmyk(MultipartFile file) throws IOException {
    boolean isRgb = true;// true是Rgb否则是Cmyk
    // 创建输入流
    ImageInputStream input = ImageIO.createImageInputStream(file.getInputStream());
    Iterator readers = ImageIO.getImageReaders(input);
    if (readers == null || !readers.hasNext()) {
      throw new RuntimeException("No ImageReaders found");
    }
    ImageReader reader = (ImageReader) readers.next();
    reader.setInput(input);
    // 获取文件格式
    BufferedImage image;
    try {
      // 尝试读取图片 (包括颜色的转换).
      reader.read(0); // RGB
      isRgb = true;
    } catch (IIOException e) {
      // 读取Raster (没有颜色的转换).
      reader.readRaster(0, null);// CMYK
      isRgb = false;
    }
    return isRgb;
  }
  
  // extract metadata
  public static BufferedImage readImage(File file) throws IOException {
    // Get an ImageReader.
    try {
      ImageInputStream input = ImageIO.createImageInputStream(file);
      Iterator readers = ImageIO.getImageReaders(input);
      if (readers == null || !readers.hasNext()) {
        return null;
      }

      ImageReader reader = (ImageReader) readers.next();
      reader.setInput(input);
      String format = reader.getFormatName();
      
      if ("JPEG".equalsIgnoreCase(format) || "JPG".equalsIgnoreCase(format)) {
        try {
          IIOMetadata metadata = reader.getImageMetadata(0);
          String metadataFormat = metadata.getNativeMetadataFormatName();
          IIOMetadataNode iioNode = (IIOMetadataNode) metadata.getAsTree(metadataFormat);
          
          NodeList children = iioNode.getElementsByTagName("app14Adobe");
          if (children.getLength() > 0) {
            try {
              iioNode = (IIOMetadataNode) children.item(0);
              int transform = Integer.parseInt(iioNode.getAttribute("transform"));
              Raster raster = reader.readRaster(0, reader.getDefaultReadParam());
              
              if (input != null) {
                input.close();
              }
              reader.dispose();

              return createJPEG4(raster, transform);
            } catch (Exception e) {
              log.error(e);
            }
          }
        } catch (Exception e) {
          log.error(e);
        }
      }
    } catch (NumberFormatException e) {
      return null;
    }
    return null;
  }

  /**
   * 
   * Java's ImageIO can't process 4-component images
   * 
   * and Java2D can't apply AffineTransformOp either,
   * 
   * so convert raster data to RGB.
   * 
   * Technique due to MArk Stephens.
   * 
   * Free for any use.
   * 
   */
  private static BufferedImage createJPEG4(Raster raster, int xform) {
    try {
      int w = raster.getWidth();
      int h = raster.getHeight();
      byte[] rgb = new byte[w * h * 3];

      // if (Adobe_APP14 and transform==2) then YCCK else
      // CMYK

      if (xform == 2) { // YCCK --
        // Adobe

        float[] Y = raster.getSamples(0, 0, w, h, 0, (float[]) null);
        float[] Cb = raster.getSamples(0, 0, w, h, 1, (float[]) null);
        float[] Cr = raster.getSamples(0, 0, w, h, 2, (float[]) null);
        float[] K = raster.getSamples(0, 0, w, h, 3, (float[]) null);

        for (int i = 0, imax = Y.length, base = 0; i < imax; i++, base += 3) {
          float k = 220 - K[i], y = 255 - Y[i], cb = 255 - Cb[i], cr = 255 - Cr[i];

          double val = y + 1.402 * (cr - 128) - k;
          val = (val - 128) * .65f + 128;
          rgb[base] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff : (byte) (val + 0.5);

          val = y - 0.34414 * (cb - 128) - 0.71414 * (cr - 128) - k;
          val = (val - 128) * .65f + 128;
          rgb[base + 1] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff : (byte) (val + 0.5);

          val = y + 1.772 * (cb - 128) - k;
          val = (val - 128) * .65f + 128;
          rgb[base + 2] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff : (byte) (val + 0.5);
        }

      } else {
        // assert xform==0: xform;
        // CMYK

        int[] C = raster.getSamples(0, 0, w, h, 0, (int[]) null);
        int[] M = raster.getSamples(0, 0, w, h, 1, (int[]) null);
        int[] Y = raster.getSamples(0, 0, w, h, 2, (int[]) null);
        int[] K = raster.getSamples(0, 0, w, h, 3, (int[]) null);

        for (int i = 0, imax = C.length, base = 0; i < imax; i++, base += 3) {
          int c = 255 - C[i];
          int m = 255 - M[i];
          int y = 255 - Y[i];
          int k = 255 - K[i];
          float kk = k / 255f;

          rgb[base] = (byte) (255 - Math.min(255f, c * kk + k));
          rgb[base + 1] = (byte) (255 - Math.min(255f, m * kk + k));
          rgb[base + 2] = (byte) (255 - Math.min(255f, y * kk + k));
        }
      }

      // from other image types we know InterleavedRaster's can be
      // manipulated by AffineTransformOp, so create one of those.
      raster = Raster.createInterleavedRaster(new DataBufferByte(rgb, rgb.length), w, h, w * 3, 3,
          new int[] {0, 1, 2}, null);

      ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
      ColorModel cm =
          new ComponentColorModel(cs, false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
      return new BufferedImage(cm, (WritableRaster) raster, true, null);
    } catch (Exception e) {
      log.error(e);
      return null;
    }
  }


  public static void main(String[] args) {

      try {
        System.out.println(isRgbOrCmyk("O:/2.jpg"));
        System.out.println(isRgbOrCmyk("O:/3.jpeg"));
        System.out.println(isRgbOrCmyk("O:/4.jpg"));
        System.out.println(isRgbOrCmyk("O:/5.jpg"));
        System.out.println(isRgbOrCmyk("O:/6.jpeg"));
      } catch (IOException e) {
        e.printStackTrace();
      }
     
  }

}
