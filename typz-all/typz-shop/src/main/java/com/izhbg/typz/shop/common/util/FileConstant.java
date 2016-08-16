/**   
 * @Title: FileConstant.java 
 * @Package com.zgxcw.store.web.controller.goods 
 * @Description: TODO 
 * @author weiming
 * @date 2015年9月29日 下午3:24:42   
 */ 
package com.izhbg.typz.shop.common.util;

/** 
 * @ClassName: FileConstant 
 * @Description: 图片上传常量属性
 * @author weiming
 * @date 2015年9月29日 下午3:24:42   
 */
public class FileConstant {
  
  /** 上传目录名 */
  public static final String uploadFolderName = "uploadFiles";

  /** 上传临时文件存储目录 */
  public static final String tempFolderName = "tempFiles";

  /** 上传文件最大为1M */
  public static final Long fileMaxSize = 1000000L;
  
  /** 允许上传的图片的宽度、高度*/
  public static final int IMAGE_SIZE = 800;

  /** 上传文件统一的编码格式 */
  public static final String encode = "UTF-8";

  /** 图片水印路径*/
  public static final String Logo = "images/watermark-logo.png";
  /**
   * 允许上传的图片格式
   * @ClassName: FileExtension 
   * @author weiming
   * @date 2015年10月16日 上午10:52:37
   */
  public enum FileExtension{
    /**
     * JPEG
     */
    JPEG("FFD8FF"),
    /**
     * PNG
     */
    PNG("89504E47");
    
    public final String value;
    
    FileExtension(String value) {
         this.value = value;
     }
     public String getValue() {
         return value;
     }
  }
  //各类型文件参数
  public enum FileType{
    /**
     * JPEG
     */
    JPEG("FFD8FF"),
    /**
     * PNG
     */
    PNG("89504E47"),
    /**
     * GIF
     */
    GIF("47494638"),
    /**
     * TIFF
     */
    TIFF("49492A00"),
    /**
     * Windows Bitmap
     */
    BMP("424D"),
    /**
     * CAD
     */
    DWG("41433130"),
    /**
     * Adobe Photoshop
     */
    PSD("38425053"),
    /**
     * XML
     */
    XML("3C3F786D6C"),
    /**
     * HTML
     */
    HTML("68746D6C3E"),
    /**
     * Adobe Acrobat
     */
    PDF("255044462D312E"),
    /**
     * ZIP
     */
    ZIP("504B0304"),
    /**
     * RAR
     */
    RAR("52617221"),
    /**
     * Wave
     */
    WAV("57415645"),
    /**
     * AVI
     */
    AVI("41564920");
    
    private String value = "";
    
    private FileType(String value){
      this.value = value;
    }

    /**
     * @return the value
     */
    public String getValue() {
      return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
      this.value = value;
    }
    
  }
}
